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
package com.zenlife.dao.impl;

import java.util.List;

import org.qifu.base.dao.BaseDAO;
import org.qifu.base.model.YesNo;
import org.qifu.po.ZlCourse;
import org.springframework.stereotype.Repository;

import com.zenlife.dao.ICourseDAO;

@Repository("zenlife.dao.CourseDAO")
public class CourseDAOImpl extends BaseDAO<ZlCourse, String> implements ICourseDAO<ZlCourse, String> {
	
	public CourseDAOImpl() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ZlCourse> findCourseForShow() throws Exception {
		return this.getCurrentSession().createQuery("SELECT new org.qifu.po.ZlCourse(m.oid, m.id, m.title, m.description, m.showFlag, m.cuserid, m.cdate) FROM ZlCourse m WHERE m.showFlag = :showFlag ORDER BY m.id DESC")
				.setString("showFlag", YesNo.YES)
				.setMaxResults(100)
				.list();
	}
	
}
