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

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryControllerJsonResultObj;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.po.ZlPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.IPersonService;
import com.zenlife.service.logic.IPersonLogicService;

@EnableWebMvc
@Controller
public class PersonAction extends BaseController {
	
	private IPersonService<ZlPerson, String> personService;
	private IPersonLogicService personLogicService;
	
	public IPersonService<ZlPerson, String> getPersonService() {
		return personService;
	}

	@Autowired
	@Resource(name="zenlife.service.PersonService")
	@Required	
	public void setPersonService(IPersonService<ZlPerson, String> personService) {
		this.personService = personService;
	}
	
	public IPersonLogicService getPersonLogicService() {
		return personLogicService;
	}

	@Autowired
	@Resource(name="zenlife.service.logic.PersonLogicService")
	@Required	
	public void setPersonLogicService(IPersonLogicService personLogicService) {
		this.personLogicService = personLogicService;
	}

	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0001Q")
	@RequestMapping(value = "/zenlife.personManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("ZENLIFE_PROG001D0001Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "zl-person/person-management";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.setViewName(viewName);
		return mv;		
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0001Q")
	@RequestMapping(value = "/zenlife.personQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj<List<ZlPerson>> queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj<List<ZlPerson>> result = this.getQueryJsonResult("ZENLIFE_PROG001D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult<List<ZlPerson>> queryResult = this.personService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, ZlPerson person) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.personLogicService.delete(person);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );
	}	
	
	private void updateCancelFlag(DefaultControllerJsonResultObj<Boolean> result, ZlPerson person) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.personLogicService.changeValidFlag(person);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( Boolean.TRUE );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0001Q")
	@RequestMapping(value = "/zenlife.personDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(ZlPerson person) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("ZENLIFE_PROG001D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, person);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0001Q")
	@RequestMapping(value = "/zenlife.personUpdateCancelFlagJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doUpdateCancelFlag(ZlPerson person) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("ZENLIFE_PROG001D0001Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.updateCancelFlag(result, person);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
