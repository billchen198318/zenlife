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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.ZlBloodPressure;
import org.qifu.po.ZlChronic;
import org.qifu.po.ZlPerson;
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

import com.zenlife.service.IBloodPressureService;
import com.zenlife.service.IChronicService;
import com.zenlife.service.IPersonChronicService;
import com.zenlife.service.IPersonProfileService;
import com.zenlife.service.IPersonService;
import com.zenlife.service.logic.IProfileLogicService;

@EnableWebMvc
@Controller
public class PersonAction extends BaseController {
	
	private IPersonService<ZlPerson, String> personService;
	private IPersonProfileService<ZlPersonProfile, String> personProfileService;
	private IChronicService<ZlChronic, String> chronicService;
	private IPersonChronicService<ZlPersonChronic, String> personChronicService;
	private IBloodPressureService<ZlBloodPressure, String> bloodPressureService;
	private IProfileLogicService profileLogicService; 
	
	public IChronicService<ZlChronic, String> getChronicService() {
		return chronicService;
	}
	
	public IPersonService<ZlPerson, String> getPersonService() {
		return personService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.PersonService")
	@Required	
	public void setPersonService(IPersonService<ZlPerson, String> personService) {
		this.personService = personService;
	}
	
	public IPersonProfileService<ZlPersonProfile, String> getPersonProfileService() {
		return personProfileService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.PersonProfileService")
	@Required	
	public void setPersonProfileService(IPersonProfileService<ZlPersonProfile, String> personProfileService) {
		this.personProfileService = personProfileService;
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
	
	public IBloodPressureService<ZlBloodPressure, String> getBloodPressureService() {
		return bloodPressureService;
	}

	@Autowired
	@Resource(name="zenlife.service.BloodPressureService")
	@Required	
	public void setBloodPressureService(IBloodPressureService<ZlBloodPressure, String> bloodPressureService) {
		this.bloodPressureService = bloodPressureService;
	}	
	
	public IProfileLogicService getProfileLogicService() {
		return profileLogicService;
	}

	@Autowired
	@Resource(name="zenlife.service.logic.ProfileLogicService")
	@Required	
	public void setProfileLogicService(IProfileLogicService profileLogicService) {
		this.profileLogicService = profileLogicService;
	}

	private List<ZlPersonChronic> findPersonChronicList() throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", this.getAccountId());
		return this.personChronicService.findListByParams(paramMap);
	}
	
	private List<ZlBloodPressure> findForLastRecordView() throws ServiceException, Exception {
		DefaultResult<List<ZlBloodPressure>> result = this.bloodPressureService.findForLastRecord(this.getAccountId(), 1);
		return result.getValue();
	}	
	
	private void fillBaseDataForPage(ModelAndView mv) throws ServiceException, Exception {
		mv.addObject("birthdayStr", "");
		ZlPerson person = new ZlPerson();
		person.setId(this.getAccountId());
		DefaultResult<ZlPerson> mResult = this.personService.findEntityByUK(person);
		if ( mResult.getValue() == null ) {
			throw new ServiceException(mResult.getSystemMessage().getValue());
		}
		person = mResult.getValue();
		ZlPersonProfile profile = new ZlPersonProfile();
		profile.setId(person.getId());
		DefaultResult<ZlPersonProfile> bResult = this.personProfileService.findEntityByUK(profile);
		if (bResult.getValue() != null) { // ZL_PERSON_PROFILE 不一定會有資料
			profile = bResult.getValue();
		} else {
			profile.setWeight("0");
			profile.setHeight("0");
			profile.setBirthdayDay("");
			profile.setAddress("");
			profile.setGender("1");
		}
		mv.addObject("person", person);
		mv.addObject("profile", profile);
		if (!StringUtils.isBlank(profile.getBirthdayYear()) && !StringUtils.isBlank(profile.getBirthdayMonth()) 
				&& !StringUtils.isBlank(profile.getBirthdayDay())) {
			mv.addObject("birthdayStr", profile.getBirthdayYear()+"-"+profile.getBirthdayMonth()+"-"+profile.getBirthdayDay());
		}
	}

	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personProfileEdit.do", method = RequestMethod.GET)
	public ModelAndView personProfileEdit(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			this.fillBaseDataForPage(mv);
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
	
	private void checkFieldsForParam(DefaultControllerJsonResultObj<ZlPersonProfile> result, ZlPerson person, ZlPersonProfile profile) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("name", person, "@org.apache.commons.lang3.StringUtils@isBlank(name)", "名稱必須填寫")
		.testField("phone", person, "@org.apache.commons.lang3.StringUtils@isBlank(phone)", "手機號碼必須填寫")
		.testField("birthday", profile, "!@org.qifu.util.SimpleUtils@isDate(birthdayYear+birthdayMonth+birthdayDay)", "生日必須填寫")
		.throwMessage();
	}
	
	private void checkFieldsForParamForUpdatePwd(DefaultControllerJsonResultObj<ZlPerson> result, String pwd0, String pwd1, String pwd2) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("pwd0", StringUtils.isBlank(pwd0), "請輸入原密碼")
		.testField("pwd1", StringUtils.isBlank(pwd1), "請輸入新密碼")
		.testField("pwd2", StringUtils.isBlank(pwd2), "請輸入新密碼(驗證)")
		.testField("pwd1", !pwd1.equals(pwd2), "新密碼與(驗證)需相同")
		.throwMessage();
	}	
	
	private void update(DefaultControllerJsonResultObj<ZlPersonProfile> result, HttpServletRequest request) throws AuthorityException, ControllerException, ServiceException, Exception {
		ZlPerson person = new ZlPerson();
		ZlPersonProfile profile = new ZlPersonProfile();
		person.setOid( request.getParameter("personOid") );
		this.fillObjectFromRequest(request, person);
		this.fillObjectFromRequest(request, profile);
		profile.setHeight( NumberUtils.toInt(request.getParameter("heightStr"), 0)+"" );
		profile.setWeight( NumberUtils.toInt(request.getParameter("weightStr"), 0)+"" );
		String birthday = super.defaultString( request.getParameter("birthday") ).replaceAll("/", "").replaceAll("-", "");
		if (birthday.length() == 8) {
			profile.setBirthdayYear( birthday.substring(0, 4) );
			profile.setBirthdayMonth( birthday.substring(4, 6) );
			profile.setBirthdayDay( birthday.substring(6, 8) );
		}
		this.checkFieldsForParam(result, person, profile);
		DefaultResult<ZlPersonProfile> uResult = this.profileLogicService.updateProfile(
				person, 
				profile, 
				super.transformAppendKeyStringToList(request.getParameter("chronicAppendId")), 
				null);
		if (uResult.getValue() != null) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );		
	}
	
	private void updatePwd(DefaultControllerJsonResultObj<ZlPerson> result, HttpServletRequest request) throws AuthorityException, ControllerException, ServiceException, Exception {
		String pwd0 = this.defaultString(request.getParameter("pwd0")).trim();
		String pwd1 = this.defaultString(request.getParameter("pwd1")).trim();
		String pwd2 = this.defaultString(request.getParameter("pwd2")).trim();
		this.checkFieldsForParamForUpdatePwd(result, pwd0, pwd1, pwd2);
		ZlPerson person = new ZlPerson();
		person.setId( this.getAccountId() );
		DefaultResult<ZlPerson> uResult = this.profileLogicService.updatePassword(person, pwd0, pwd1);
		if (uResult.getValue() != null) {
			uResult.getValue().setPassword(" ");
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( uResult.getSystemMessage().getValue() );
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personProfileUpdateJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlPersonProfile> doUpdate(HttpServletRequest request) {
		DefaultControllerJsonResultObj<ZlPersonProfile> result = this.getDefaultJsonResult("ZENLIFE_FE_0004Q");
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/changePwEdit.do", method = RequestMethod.GET)
	public ModelAndView changePasswordEdit(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			viewName = "person/person-pw-edit";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personPwUpdateJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlPerson> doUpdatePwd(HttpServletRequest request) {
		DefaultControllerJsonResultObj<ZlPerson> result = this.getDefaultJsonResult("ZENLIFE_FE_0004Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.updatePwd(result, request);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0004Q")
	@RequestMapping(value = "/personProfileView.do", method = RequestMethod.GET)
	public ModelAndView personProfileView(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			this.fillBaseDataForPage(mv);
			mv.addObject("chronicList", this.chronicService.findListByParams(null));
			mv.addObject("personChronicList", this.findPersonChronicList());
			mv.addObject("bloodPressureList", this.findForLastRecordView());
			viewName = "person/person-view";
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
	
}
