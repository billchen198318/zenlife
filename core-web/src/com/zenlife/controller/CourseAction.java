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

import org.apache.commons.lang3.StringUtils;
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
import org.qifu.po.ZlCourse;
import org.qifu.util.SimpleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.ICourseService;

@EnableWebMvc
@Controller
public class CourseAction extends BaseController {
	
	private ICourseService<ZlCourse, String> courseService;
	
	public ICourseService<ZlCourse, String> getCourseService() {
		return courseService;
	}

	@Autowired
	@Resource(name="zenlife.service.CourseService")
	@Required	
	public void setCourseService(ICourseService<ZlCourse, String> courseService) {
		this.courseService = courseService;
	}

	private void init(String type, HttpServletRequest request, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		
	}
	
	private void fetchData(ZlCourse course, ModelAndView mv) throws ServiceException, ControllerException, Exception {
		DefaultResult<ZlCourse> result = this.courseService.findEntityByOid(course);
		if ( result.getValue() == null ) {
			throw new ControllerException( result.getSystemMessage().getValue() );
		}
		course = result.getValue();
		mv.addObject("course", course);
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002Q")
	@RequestMapping(value = "/zenlife.courseManagement.do")	
	public ModelAndView queryPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("ZENLIFE_PROG001D0002Q");
		try {
			this.init("queryPage", request, mv);
			viewName = "zl-course/course-management";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002Q")
	@RequestMapping(value = "/zenlife.courseQueryGridJson.do", produces = "application/json")	
	public @ResponseBody QueryControllerJsonResultObj<List<ZlCourse>> queryGrid(SearchValue searchValue, PageOf pageOf) {
		QueryControllerJsonResultObj<List<ZlCourse>> result = this.getQueryJsonResult("ZENLIFE_PROG001D0002Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			QueryResult<List<ZlCourse>> queryResult = this.courseService.findGridResult(searchValue, pageOf);
			this.setQueryGridJsonResult(result, queryResult, pageOf);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002A")
	@RequestMapping(value = "/zenlife.courseCreate.do")
	public ModelAndView createPage(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("ZENLIFE_PROG001D0002A");
		try {
			this.init("createPage", request, mv);
			viewName = "zl-course/course-create";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002E")
	@RequestMapping(value = "/zenlife.courseEdit.do")
	public ModelAndView editPage(HttpServletRequest request, @RequestParam(name="oid") String oid) {
		String viewName = PAGE_SYS_ERROR;
		ModelAndView mv = this.getDefaultModelAndView("ZENLIFE_PROG001D0002E");
		try {
			ZlCourse course = new ZlCourse();
			course.setOid(oid);
			this.init("editPage", request, mv);
			this.fetchData(course, mv);
			viewName = "zl-course/course-edit";
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
	
	private void checkFields(DefaultControllerJsonResultObj<ZlCourse> result, ZlCourse course) throws ControllerException, Exception {
		this.getCheckControllerFieldHandler(result)
		.testField("id", course, "@org.apache.commons.lang3.StringUtils@isBlank(id)", "Id不可以空白!")
		.testField("id", ( !SimpleUtils.checkBeTrueOf_azAZ09(super.defaultString(course.getId()).replaceAll("-", "").replaceAll("_", "")) ), "Id不允許特別字元!")
		.testField("title", course, "@org.apache.commons.lang3.StringUtils@isBlank(title)", "標題不可以空白!")
		.testField("content", course, "@org.apache.commons.lang3.StringUtils@isBlank(content)", "內容不可以空白!")
		.testField("description", course, "@org.apache.commons.lang3.StringUtils@isBlank(description)", "描述不可以空白!")
		.throwMessage();			
	}
	
	private void save(DefaultControllerJsonResultObj<ZlCourse> result, ZlCourse course) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, course);
		if (StringUtils.defaultString(course.getDescription()).length()>500) {
			course.setDescription( course.getDescription().substring(0, 500) );
		}
		DefaultResult<ZlCourse> cResult = this.courseService.saveEntity(course);
		if ( cResult.getValue() != null ) {
			result.setValue( cResult.getValue() );
			result.setSuccess( YES );			
		}
		result.setMessage( cResult.getSystemMessage().getValue() );
	}	
	
	private void update(DefaultControllerJsonResultObj<ZlCourse> result, ZlCourse course) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFields(result, course);
		if (StringUtils.defaultString(course.getDescription()).length()>500) {
			course.setDescription( course.getDescription().substring(0, 500) );
		}
		DefaultResult<ZlCourse> oResult = this.courseService.findEntityByOid(course);
		if (oResult.getValue() == null) {
			throw new ServiceException( oResult.getSystemMessage().getValue() );
		}
		ZlCourse pCourse = oResult.getValue();
		BeanUtils.copyProperties(course, pCourse, "cdate", "cuserid");
		DefaultResult<ZlCourse> uResult = this.courseService.updateEntity(pCourse);
		if ( uResult.getValue() != null ) {
			result.setValue( uResult.getValue() );
			result.setSuccess( YES );			
		}
		result.setMessage( uResult.getSystemMessage().getValue() );		
	}
	
	private void delete(DefaultControllerJsonResultObj<Boolean> result, ZlCourse course) throws AuthorityException, ControllerException, ServiceException, Exception {
		DefaultResult<Boolean> dResult = this.courseService.deleteEntity(course);
		if ( dResult.getValue() != null && dResult.getValue() ) {
			result.setValue( dResult.getValue() );
			result.setSuccess( YES );			
		}
		result.setMessage( dResult.getSystemMessage().getValue() );				
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002A")
	@RequestMapping(value = "/zenlife.courseSaveJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<ZlCourse> doSave(ZlCourse course) {
		DefaultControllerJsonResultObj<ZlCourse> result = this.getDefaultJsonResult("ZENLIFE_PROG001D0002A");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, course);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002E")
	@RequestMapping(value = "/zenlife.courseUpdateJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<ZlCourse> doUpdate(ZlCourse course) {
		DefaultControllerJsonResultObj<ZlCourse> result = this.getDefaultJsonResult("ZENLIFE_PROG001D0002E");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.update(result, course);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_PROG001D0002D")
	@RequestMapping(value = "/zenlife.courseDeleteJson.do", produces = "application/json")		
	public @ResponseBody DefaultControllerJsonResultObj<Boolean> doDelete(ZlCourse course) {
		DefaultControllerJsonResultObj<Boolean> result = this.getDefaultJsonResult("ZENLIFE_PROG001D0002D");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.delete(result, course);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}	
	
}
