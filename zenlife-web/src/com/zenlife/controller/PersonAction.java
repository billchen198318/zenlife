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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.po.ZlChronic;
import org.qifu.po.ZlPersonChronic;
import org.qifu.po.ZlPersonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.IChronicService;
import com.zenlife.service.IPersonChronicService;

@EnableWebMvc
@Controller
public class PersonAction extends BaseController {
	
	private IChronicService<ZlChronic, String> chronicService;
	private IPersonChronicService<ZlPersonChronic, String> personChronicService;
	
	public IChronicService<ZlChronic, String> getChronicService() {
		return chronicService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.ChronicService")
	@Required	
	public void setChronicService(IChronicService<ZlChronic, String> chronicService) {
		this.chronicService = chronicService;
	}
	
	public IPersonChronicService<ZlPersonChronic, String> getPersonChronicService() {
		return personChronicService;
	}

	@Autowired
	@Resource(name="zenlife.service.PersonChronicService")
	@Required	
	public void setPersonChronicService(IPersonChronicService<ZlPersonChronic, String> personChronicService) {
		this.personChronicService = personChronicService;
	}
	
	private List<ZlPersonChronic> findPersonChronicList() throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", this.getAccountId());
		return this.personChronicService.findListByParams(paramMap);
	}

	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personProfileEdit.do", method = RequestMethod.GET)
	public ModelAndView personProfileEdit(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("chronicList", this.chronicService.findListByParams(null));
			mv.addObject("personChronicList", this.findPersonChronicList());
			viewName = "person/person-edit";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}		
	
	private void update(DefaultControllerJsonResultObj<ZlPersonProfile> result, HttpServletRequest request) throws AuthorityException, ControllerException, ServiceException, Exception {
		
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personProfileUpdateJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlPersonProfile> doUpdate(HttpServletRequest request) {
		DefaultControllerJsonResultObj<ZlPersonProfile> result = this.getDefaultJsonResult("ZENLIFE_FE_0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, request);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	

}
