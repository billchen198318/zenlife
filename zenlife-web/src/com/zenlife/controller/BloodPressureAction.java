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

import org.apache.commons.lang.math.NumberUtils;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.ZlBloodPressure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.IBloodPressureService;

@EnableWebMvc
@Controller
public class BloodPressureAction extends BaseController {
	
	private IBloodPressureService<ZlBloodPressure, String> bloodPressureService;
	
	public IBloodPressureService<ZlBloodPressure, String> getBloodPressureService() {
		return bloodPressureService;
	}

	@Autowired
	@Resource(name="zenlife.service.BloodPressureService")
	@Required	
	public void setBloodPressureService(IBloodPressureService<ZlBloodPressure, String> bloodPressureService) {
		this.bloodPressureService = bloodPressureService;
	}
	
	private List<ZlBloodPressure> findForLast7Record() throws ServiceException, Exception {
		DefaultResult<List<ZlBloodPressure>> result = this.bloodPressureService.findForLast7RecordAndSort(this.getAccountId());
		return result.getValue();
	}	
	
	private List<ZlBloodPressure> findForLastRecordView() throws ServiceException, Exception {
		DefaultResult<List<ZlBloodPressure>> result = this.bloodPressureService.findForLastRecord(this.getAccountId(), 100);
		return result.getValue();
	}

	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0003Q")
	@RequestMapping(value = "/bloodPressure.do", method = RequestMethod.GET)
	public ModelAndView bloodPressureHome(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("bloodPressureList", this.findForLast7Record());
			viewName = "blood-pressure/blood-pressure-home";
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
	
	private void checkFieldsForParam(DefaultControllerJsonResultObj<ZlBloodPressure> result, ZlBloodPressure bloodPressure) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("sbp", bloodPressure, "!(sbp>0 && sbp<1000)", "請輸入正確收縮壓(SBP)")
		.testField("dbp", bloodPressure, "!(dbp>0 && dbp<1000)", "請輸入正確舒張壓(DBP)")
		.testField("pulse", bloodPressure, "!(pulse>0 && pulse<1000)", "請輸入正確脈搏(PULSE)")
		.testField("logDate", bloodPressure, "!@org.qifu.util.SimpleUtils@isDate( @org.apache.commons.lang3.StringUtils@defaultString(logDate) )", "請輸入正確日期")
		.throwMessage();
	}
	
	private void save(DefaultControllerJsonResultObj<ZlBloodPressure> result, ZlBloodPressure bloodPressure) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForParam(result, bloodPressure);
		bloodPressure.setPersonId(this.getAccountId());
		if (super.defaultString(bloodPressure.getDescription()).length()>500) {
			bloodPressure.setDescription( super.defaultString(bloodPressure.getDescription()).substring(0, 500).trim() );
		}
		bloodPressure.setLogDate( bloodPressure.getLogDate().replaceAll("-", "").replaceAll("/", "") );
		DefaultResult<ZlBloodPressure> saveResult = this.bloodPressureService.saveEntity(bloodPressure);
		if (saveResult.getValue() != null) {
			result.setValue( saveResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( saveResult.getSystemMessage().getValue() );
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, ZlBloodPressure bloodPressure) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.bloodPressureService.deleteEntity(bloodPressure);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( dResult.getSystemMessage().getValue() );			
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0003Q")
	@RequestMapping(value = "/bloodPressureSaveJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlBloodPressure> doSave(HttpServletRequest request, ZlBloodPressure bloodPressure) {
		DefaultControllerJsonResultObj<ZlBloodPressure> result = this.getDefaultJsonResult("ZENLIFE_FE_0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			bloodPressure.setSbp( NumberUtils.toInt(request.getParameter("sbpStr"), 0) );
			bloodPressure.setDbp( NumberUtils.toInt(request.getParameter("dbpStr"), 0) );
			bloodPressure.setPulse( NumberUtils.toInt(request.getParameter("pulseStr"), 0) );
			this.save(result, bloodPressure);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0003Q")
	@RequestMapping(value = "/bloodPressureInput.do", method = RequestMethod.GET)
	public ModelAndView bloodPressureInput(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			
			viewName = "blood-pressure/blood-pressure-input";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0003Q")
	@RequestMapping(value = "/bloodPressureDeleteJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(ZlBloodPressure bloodPressure) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("ZENLIFE_FE_0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, bloodPressure);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0003Q")
	@RequestMapping(value = "/bloodPressureList.do", method = RequestMethod.GET)
	public ModelAndView bloodPressureList(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("bloodPressureList", this.findForLastRecordView());
			viewName = "blood-pressure/blood-pressure-list";
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
