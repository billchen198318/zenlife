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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qifu.base.AppContext;
import org.qifu.base.Constants;
import org.qifu.base.exception.ServiceException;
import org.qifu.po.ZlPerson;
import org.qifu.sys.GreenStepBaseFormAuthenticationFilter;
import org.qifu.sys.GreenStepBaseUsernamePasswordToken;

import com.zenlife.service.IPersonService;

public class ZenLifeShiroLoginSupport {
	protected Logger logger = Logger.getLogger(ZenLifeShiroLoginSupport.class);
	IPersonService<ZlPerson, String> personService;
	
	public ZenLifeShiroLoginSupport() {
		this.initBeans();
	}
	
	@SuppressWarnings("unchecked")
	private void initBeans() {
		try {
			personService = (IPersonService<ZlPerson, String>) AppContext.getBean("zenlife.service.PersonService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Subject forceCreateLoginSubject(HttpServletRequest request, HttpServletResponse response, String accountId, String captchaStr) throws Exception {
		ZlPerson person = new ZlPerson();
		person.setId(accountId);
		person = personService.findByEntityUK(person);
		if (null == person) {
			throw new ServiceException("no found person: " + accountId);
		}		
		request.getSession().setAttribute(GreenStepBaseFormAuthenticationFilter.DEFAULT_CAPTCHA_PARAM, captchaStr);
		GreenStepBaseUsernamePasswordToken token = new GreenStepBaseUsernamePasswordToken();
		token.setCaptcha( captchaStr );
		token.setUsername( person.getId() );
		token.setPassword( person.getPassword().toCharArray() );
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		request.getSession().setAttribute(Constants.SESS_ACCOUNT, person.getId());
		request.getSession().setAttribute(Constants.SESS_LANG, "en");	
		return subject;
	}
	
}
