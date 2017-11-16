/* 
 * Copyright 2012-2017 ZenLife of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.zenlife.sys;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.qifu.base.AppContext;
import org.qifu.base.Constants;
import org.qifu.base.model.YesNo;
import org.qifu.base.sys.UserAccountHttpSessionSupport;
import org.qifu.po.ZlPerson;
import org.qifu.sys.GreenStepBaseUsernamePasswordToken;
import org.qifu.sys.IncorrectCaptchaException;
import org.qifu.sys.ShiroLoginSupport;

import com.zenlife.service.IPersonService;

public class ZenLifeBaseFormAuthenticationFilter extends FormAuthenticationFilter {
	protected static Logger logger = Logger.getLogger(ZenLifeBaseFormAuthenticationFilter.class);	
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	
	public ZenLifeBaseFormAuthenticationFilter() {
		super();
	}
	
	protected String getCaptcha(ServletRequest request) {		
        return WebUtils.getCleanParam(request, this.getCaptchaParam());
    }
	
	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = StringUtils.defaultString(this.getUsername(request));
		String password = StringUtils.defaultString(this.getPassword(request));
		String captcha = StringUtils.defaultString(this.getCaptcha(request));
		//boolean rememberMe = StringUtils.defaultString(isRememberMe(request));
		boolean rememberMe = true;
		String host = StringUtils.defaultString(getHost(request));
		char pwd[] = null;
		try {
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			pwd = loginSupport.getAccountService().tranPassword(password).toCharArray(); // 只用來取轉過的密碼值
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GreenStepBaseUsernamePasswordToken(
				username, 
				pwd, 
				rememberMe, 
				host, 
				captcha);
	}
	
	protected void doCaptchaValidate(HttpServletRequest request, GreenStepBaseUsernamePasswordToken token) {
		if (!YesNo.YES.equals(Constants.getLoginCaptchaCodeEnable())) { // 2015-12-18 add https://github.com/billchen198318/bamboobsc/issues/5
			return;
		}
		Object sessCaptcha = SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String inputCaptcha = token.getCaptcha();
		if (!(sessCaptcha instanceof String) || StringUtils.isBlank(inputCaptcha) ) {
			throw new IncorrectCaptchaException("驗證碼錯誤!");
		}
		if (!inputCaptcha.equals(sessCaptcha)) {
			throw new IncorrectCaptchaException("驗證碼錯誤!");
		}				
	}
	
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		GreenStepBaseUsernamePasswordToken token = 
				(GreenStepBaseUsernamePasswordToken) this.createToken(request, response);
		try {
			this.doCaptchaValidate((HttpServletRequest)request, token);
			
			if (StringUtils.isBlank(token.getUsername())) {
				throw new AuthenticationException("請輸入帳戶!");
			}
			if (token.getPassword() == null || token.getPassword().length < 1) {
				throw new AuthenticationException("請輸入密碼!");
			}			
			@SuppressWarnings("unchecked")
			IPersonService<ZlPerson, String> personService = (IPersonService<ZlPerson, String>) AppContext.getBean("zenlife.service.PersonService");
			ZlPerson person = new ZlPerson();
			person.setId( token.getUsername() );
			person = personService.findByEntityUK( person );
			if (null == person || !YesNo.YES.equals(person.getValidFlag())) {
				throw new AuthenticationException("無效的帳戶!");
			}
			
			Subject subject = this.getSubject(request, response);
			subject.login(token);
			
			// set session
			this.setUserSession((HttpServletRequest)request, (HttpServletResponse)response, token.getUsername());
			return this.onLoginSuccess(token, subject, request, response);			
		} catch (AuthenticationException e) {
			// clear session	
			logger.warn( e.getMessage().toString() );			
			UserAccountHttpSessionSupport.remove( (HttpServletRequest)request );
			this.getSubject(request, response).logout();
			return this.onLoginFailure(token, e, request, response);
		}		
	} 
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, 
			ServletRequest request, ServletResponse response) throws Exception {
		
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (!this.isAjaxRequest(httpServletRequest)) {
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
        } else {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_AUTHZ_JSON_DATA);
        }
		return false;
	}
	
	private void setUserSession(HttpServletRequest request, HttpServletResponse response, String personId) throws Exception {		
		request.getSession().setAttribute(Constants.SESS_ACCOUNT, personId);
		request.getSession().setAttribute(Constants.SESS_LANG, "en");		
	}
	
    protected boolean isAjaxRequest(HttpServletRequest request) {
    	return "XMLHttpRequest".equalsIgnoreCase( request.getHeader("X-Requested-With") );
    }
    
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	Subject subject = this.getSubject(request, response);
    	if (subject.isRemembered()) {
    		ZenLifeShiroLoginSupport loginSupport = new ZenLifeShiroLoginSupport();
    		try {
				loginSupport.forceCreateLoginSubject((HttpServletRequest)request, (HttpServletResponse)response, (String)subject.getPrincipal(), "1111");
				WebUtils.issueRedirect(request, response, "/index.do");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	if (isAjaxRequest((HttpServletRequest)request)) {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_LOGIN_JSON_DATA);
    		return;
    	}
    	WebUtils.issueRedirect(request, response, getLoginUrl());
    }	
    
}
