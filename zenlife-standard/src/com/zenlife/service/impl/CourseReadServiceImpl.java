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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.service.SimpleService;
import org.qifu.po.ZlCourseRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.ICourseReadDAO;
import com.zenlife.service.ICourseReadService;

@Service("zenlife.service.CourseReadService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class CourseReadServiceImpl extends SimpleService<ZlCourseRead, String> implements ICourseReadService<ZlCourseRead, String> {
	protected Logger logger=Logger.getLogger(CourseReadServiceImpl.class);
	private ICourseReadDAO<ZlCourseRead, String> courseReadDAO;
	
	public CourseReadServiceImpl() {
		super();
	}
	
	public ICourseReadDAO<ZlCourseRead, String> getCourseReadDAO() {
		return courseReadDAO;
	}
	
	@Autowired
	@Resource(name="zenlife.dao.CourseReadDAO")
	@Required	
	public void setCourseReadDAO(ICourseReadDAO<ZlCourseRead, String> courseReadDAO) {
		this.courseReadDAO = courseReadDAO;
	}
	
	@Override
	protected IBaseDAO<ZlCourseRead, String> getBaseDataAccessObject() {
		return this.courseReadDAO;
	}

}
