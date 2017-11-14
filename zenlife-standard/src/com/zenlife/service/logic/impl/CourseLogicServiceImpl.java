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
package com.zenlife.service.logic.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.model.SystemMessage;
import org.qifu.base.service.logic.CoreBaseLogicService;
import org.qifu.po.ZlCourse;
import org.qifu.po.ZlCourseRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.service.ICourseReadService;
import com.zenlife.service.ICourseService;
import com.zenlife.service.logic.ICourseLogicService;

@Service("zenlife.service.logic.CourseLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class CourseLogicServiceImpl extends CoreBaseLogicService implements ICourseLogicService {
	protected Logger logger=Logger.getLogger(CourseLogicServiceImpl.class);
	
	private ICourseService<ZlCourse, String> courseService;
	private ICourseReadService<ZlCourseRead, String> courseReadService;	
	
	public CourseLogicServiceImpl() {
		super();
	}
	
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

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<ZlCourse> createReadLogAndGetCourse(String courseOid) throws ServiceException, Exception {
		if (super.isBlank(courseOid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		ZlCourse course = this.courseService.findByPKng(courseOid);
		if (null == course) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST));
		}
		DefaultResult<ZlCourse> result = new DefaultResult<ZlCourse>();
		result.setValue(course);
		result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_FAIL)) );
		ZlCourseRead courseRead = new ZlCourseRead();
		courseRead.setCourseId(course.getId());
		courseRead.setPersonId(this.getAccountId());
		if (this.courseReadService.countByEntityUK(courseRead)>0) {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.DATA_IS_EXIST)) );
			return result;
		}		
		if (this.courseReadService.saveEntity(courseRead).getValue()!=null) {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS)) );
		}
		return result;
	}	
	
}
