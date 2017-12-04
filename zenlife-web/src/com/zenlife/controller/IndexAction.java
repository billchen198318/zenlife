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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.sys.ZenLifeShiroLoginSupport;

@EnableWebMvc
@Controller
public class IndexAction extends BaseController {
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_0001Q")
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			viewName = "index";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}
	
	/**
	 * 使用者要求不要有會圓了, 只提供看課程的功能, 其他的都不要, 包括會員登入&註冊
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_0001Q")
	@RequestMapping(value = "/index-sm.do", method = RequestMethod.GET)
	public ModelAndView indexSimpleMode(HttpServletRequest request, HttpServletResponse response) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			/**
			 * 使用者不要用會員了, 直接強制都用A123456789登入
			 */
			if ("".equals(super.defaultString(this.getAccountId()).trim())) {
				ZenLifeShiroLoginSupport loginSupport = new ZenLifeShiroLoginSupport();
				loginSupport.forceCreateLoginSubject(request, response, "A123456789", "1111");				
			}
			c = request.getParameter("c");
			viewName = "index-sm";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}	
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_0001Q")
	@RequestMapping(value = "/about.do", method = RequestMethod.GET)
	public ModelAndView about(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			viewName = "about";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}	
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_0001Q")
	@RequestMapping(value = "/about-sm.do", method = RequestMethod.GET)
	public ModelAndView aboutSimpleMode(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			viewName = "about-sm";
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}	
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_0001Q")
	@RequestMapping(value = "/doTestJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doTest() {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("ZENLIFE_FE_0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			result.setSuccess( YES );
			result.setMessage( System.currentTimeMillis()+"" );
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
