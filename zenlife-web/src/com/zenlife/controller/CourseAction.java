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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.YesNo;
import org.qifu.po.ZlCourse;
import org.qifu.po.ZlCourseRead;
import org.qifu.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.ICourseReadService;
import com.zenlife.service.ICourseService;
import com.zenlife.service.logic.ICourseLogicService;

@EnableWebMvc
@Controller
public class CourseAction extends BaseController {
	
	private ICourseService<ZlCourse, String> courseService;
	private ICourseReadService<ZlCourseRead, String> courseReadService;
	private ICourseLogicService courseLogicService;
	
	public ICourseService<ZlCourse, String> getCourseService() {
		return courseService;
	}

	@Autowired
	@Resource(name="zenlife.service.CourseService")
	@Required		
	public void setCourseService(ICourseService<ZlCourse, String> courseService) {
		this.courseService = courseService;
	}
	
	public ICourseReadService<ZlCourseRead, String> getCourseReadService() {
		return courseReadService;
	}

	@Autowired
	@Resource(name="zenlife.service.CourseReadService")
	@Required	
	public void setCourseReadService(ICourseReadService<ZlCourseRead, String> courseReadService) {
		this.courseReadService = courseReadService;
	}

	public ICourseLogicService getCourseLogicService() {
		return courseLogicService;
	}

	@Autowired
	@Resource(name="zenlife.service.logic.CourseLogicService")
	@Required	
	public void setCourseLogicService(ICourseLogicService courseLogicService) {
		this.courseLogicService = courseLogicService;
	}

	private List<CourseVO> findCourseList() throws ServiceException, Exception {
		List<CourseVO> courseList = new LinkedList<CourseVO>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		DefaultResult<List<ZlCourse>> result = this.courseService.findCourseForShow();
		if (result.getValue() == null) {
			return courseList;
		}
		List<ZlCourse> zlCourseList = result.getValue();
		for (ZlCourse zlCourse : zlCourseList) {
			CourseVO course = new CourseVO();
			course.setCourse(zlCourse);
			course.setRead(YesNo.NO);
			paramMap.clear();
			paramMap.put("courseId", zlCourse.getId());
			paramMap.put("personId", this.getAccountId());
			if (this.courseReadService.countByParams(paramMap) > 0) {
				course.setRead(YesNo.YES);
			}
			courseList.add(course);
		}
		return courseList;
	}
	
	private ZlCourse fetchCourse(ZlCourse course) throws ServiceException, Exception {
		DefaultResult<ZlCourse> result = this.courseLogicService.createReadLogAndGetCourse(course.getOid());
		return result.getValue();
	}

	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0002Q")
	@RequestMapping(value = "/course.do", method = RequestMethod.GET)
	public ModelAndView courseHome(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("courseList", this.findCourseList());
			viewName = "course/course-home";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0002Q")
	@RequestMapping(value = "/courseRead.do", method = RequestMethod.GET)
	public ModelAndView courseRead(HttpServletRequest request, ZlCourse course) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("course", this.fetchCourse(course));
			viewName = "course/course-read";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0002Q")
	@RequestMapping(value = "/course-sm.do", method = RequestMethod.GET)
	public ModelAndView courseHomeSimpleMode(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("courseList", this.findCourseList());
			viewName = "course/course-home-sm";
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
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0002Q")
	@RequestMapping(value = "/courseRead-sm.do", method = RequestMethod.GET)
	public ModelAndView courseReadSimpleMode(HttpServletRequest request, ZlCourse course) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("course", this.fetchCourse(course));
			viewName = "course/course-read-sm";
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
