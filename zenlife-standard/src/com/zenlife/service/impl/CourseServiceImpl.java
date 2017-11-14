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
package com.zenlife.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.SystemMessage;
import org.qifu.base.service.SimpleService;
import org.qifu.po.ZlCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.ICourseDAO;
import com.zenlife.service.ICourseService;

@Service("zenlife.service.CourseService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class CourseServiceImpl extends SimpleService<ZlCourse, String> implements ICourseService<ZlCourse, String> {
	protected Logger logger=Logger.getLogger(CourseServiceImpl.class);
	private ICourseDAO<ZlCourse, String> courseDAO;
	
	public CourseServiceImpl() {
		super();
	}

	public ICourseDAO<ZlCourse, String> getCourseDAO() {
		return courseDAO;
	}

	@Autowired
	@Resource(name="zenlife.dao.CourseDAO")
	@Required	
	public void setCourseDAO(ICourseDAO<ZlCourse, String> courseDAO) {
		this.courseDAO = courseDAO;
	}

	@Override
	protected IBaseDAO<ZlCourse, String> getBaseDataAccessObject() {
		return this.courseDAO;
	}

	@Override
	public DefaultResult<List<ZlCourse>> findCourseForShow() throws ServiceException, Exception {
		DefaultResult<List<ZlCourse>> result = new DefaultResult<List<ZlCourse>>();
		List<ZlCourse> courseList = this.courseDAO.findCourseForShow();
		if (courseList!=null && courseList.size()>0) {
			result.setValue(courseList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}
	
}
