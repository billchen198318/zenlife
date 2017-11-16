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
package com.zenlife.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.YesNo;
import org.qifu.po.ZlPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.base.ZenLifeConstants;
import com.zenlife.service.logic.IFePersonLogicService;
import com.zenlife.sys.ZenLifeShiroLoginSupport;

@EnableWebMvc
@Controller
public class RegisterAction extends BaseController {
	
	private IFePersonLogicService fePersonLogicService;
	
	public IFePersonLogicService getFePersonLogicService() {
		return fePersonLogicService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.logic.FePersonLogicService")
	@Required	
	public void setFePersonLogicService(IFePersonLogicService fePersonLogicService) {
		this.fePersonLogicService = fePersonLogicService;
	}
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_9998Q")
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			
			viewName = "register";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;
	}
	
	private void checkFieldsForParam(DefaultControllerJsonResultObj<ZlPerson> result, ZlPerson person, String sessVCode, String vcode, String retyPwd) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("vcode", StringUtils.isBlank(vcode), "驗證碼必須填寫")
		.testField("vcode", !super.defaultString(sessVCode).equals(vcode), "驗證碼錯誤")
		.testField("id", person, "@org.apache.commons.lang3.StringUtils@isBlank(id)", "身份證字號或自然人號必須填寫")
		.testField("id", person, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09(id)", "身份證字號或自然人號必須是一般字元A~Z,a~z,0~9")
		.testField("id", person, "@org.apache.commons.lang3.StringUtils@defaultString(id).trim().length()<8", "身份證字號或自然人號長度必須大於8碼")
		.testField("password", person, "@org.apache.commons.lang3.StringUtils@isBlank(password)", "密碼必須填寫")
		.testField("password", person, "!@org.qifu.util.SimpleUtils@checkBeTrueOf_azAZ09(password)", "密碼必須是一般字元A~Z,a~z,0~9")
		.testField("password", !super.defaultString(person.getPassword()).equals(retyPwd), "密碼與密碼(驗證)必須一樣")
		.testField("password", person, "@org.apache.commons.lang3.StringUtils@defaultString(password).trim().length()<4", "密碼長度必須大於4碼")
		.testField("mail", person, "@org.apache.commons.lang3.StringUtils@isBlank(mail)", "電子信箱必須填寫")
		.testField("name", person, "@org.apache.commons.lang3.StringUtils@isBlank(name)", "名稱必須填寫")
		.testField("phone", person, "@org.apache.commons.lang3.StringUtils@isBlank(phone)", "手機號碼必須填寫")
		.throwMessage();
	}	
	
	private void save(DefaultControllerJsonResultObj<ZlPerson> result, HttpServletRequest request, HttpServletResponse response, ZlPerson person, String sessVCode, String vcode, String retyPwd) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForParam(result, person, sessVCode, vcode, retyPwd);
		DefaultResult<ZlPerson> cResult = this.fePersonLogicService.create(person);
		if ( cResult.getValue() != null ) {
			/**
			 * 這邊因為有開 openView Session 所以回傳obj 拿新new出來的, 因為如果拿 result.value回傳, 要把 person.password 設為空白才回傳(不因該把password又傳到前端), 
			 * 如果把 cResult.getValue.password設為空白, 這樣在創建完帳戶後自動登入的時後 ZenLifeBaseAuthorizingRealm 抓出的 ZlPerson -> personService.findByEntityUK 它的 password 會為空值
			 */
			ZlPerson resObj = new ZlPerson();
			resObj.setId( cResult.getValue().getId() );
			resObj.setName( cResult.getValue().getName() );
			resObj.setMail( cResult.getValue().getMail() );
			result.setValue( resObj );
			result.setSuccess( YES );			
			ZenLifeShiroLoginSupport loginSupport = new ZenLifeShiroLoginSupport();
			loginSupport.forceCreateLoginSubject(request, response, cResult.getValue().getId(), "1111");
		}
		result.setMessage( cResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_9997Q")
	@RequestMapping(value = "/registerSaveJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlPerson> doSave(HttpServletRequest request, HttpServletResponse response, ZlPerson person) {
		DefaultControllerJsonResultObj<ZlPerson> result = this.getDefaultJsonResult("ZENLIFE_FE_9997Q");
		// 因為是 register所以不要check login/authorize了
		/*
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		*/
		// 因為是 register所以不要check login/authorize了
		result.setLogin( YesNo.YES );
		result.setIsAuthorize( YesNo.YES );
		result.setMessage("請重新操作");
		try {
			this.save(
					result, 
					request,
					response,
					person, 
					(String)request.getSession().getAttribute( ZenLifeConstants.SESS_VCODE ), 
					request.getParameter("vcode"), 
					request.getParameter("pwd2"));
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
}
