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
import org.qifu.po.ZlBloodPressure;
import org.springframework.stereotype.Repository;

import com.zenlife.dao.IBloodPressureDAO;

@Repository("zenlife.dao.BloodPressureDAO")
public class BloodPressureDAOImpl extends BaseDAO<ZlBloodPressure, String> implements IBloodPressureDAO<ZlBloodPressure, String> {
	
	public BloodPressureDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZlBloodPressure> findForLast7Record(String personId) throws Exception {
		return this.getCurrentSession().createQuery("FROM ZlBloodPressure WHERE personId = :personId ORDER BY logDate DESC, timePeriod DESC")
				.setString("personId", personId)
				.setMaxResults(7)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZlBloodPressure> findForLastRecord(String personId, int sizeLimit) throws Exception {
		return this.getCurrentSession().createQuery("FROM ZlBloodPressure WHERE personId = :personId ORDER BY logDate DESC, timePeriod DESC")
				.setString("personId", personId)
				.setMaxResults(sizeLimit)
				.list();
	}
	
}
