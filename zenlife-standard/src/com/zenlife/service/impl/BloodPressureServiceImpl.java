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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.SystemMessage;
import org.qifu.base.service.SimpleService;
import org.qifu.po.ZlBloodPressure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.IBloodPressureDAO;
import com.zenlife.service.IBloodPressureService;

@Service("zenlife.service.BloodPressureService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class BloodPressureServiceImpl extends SimpleService<ZlBloodPressure, String> implements IBloodPressureService<ZlBloodPressure, String> {
	protected Logger logger=Logger.getLogger(BloodPressureServiceImpl.class);
	private IBloodPressureDAO<ZlBloodPressure, String> bloodPressureDAO; 
	
	public BloodPressureServiceImpl() {
		super();
	}

	public IBloodPressureDAO<ZlBloodPressure, String> getBloodPressureDAO() {
		return bloodPressureDAO;
	}

	@Autowired
	@Resource(name="zenlife.dao.BloodPressureDAO")
	@Required	
	public void setBloodPressureDAO(IBloodPressureDAO<ZlBloodPressure, String> bloodPressureDAO) {
		this.bloodPressureDAO = bloodPressureDAO;
	}

	@Override
	protected IBaseDAO<ZlBloodPressure, String> getBaseDataAccessObject() {
		return this.bloodPressureDAO;
	}

	@Override
	public DefaultResult<List<ZlBloodPressure>> findForLast7Record(String personId) throws ServiceException, Exception {
		if (StringUtils.isBlank(personId)) {
			throw new ServiceException( SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK) );
		}
		DefaultResult<List<ZlBloodPressure>> result = new DefaultResult<List<ZlBloodPressure>>();
		List<ZlBloodPressure> bloodPressureList = this.bloodPressureDAO.findForLast7Record(personId);
		if (null != bloodPressureList && bloodPressureList.size()>0) {
			result.setValue(bloodPressureList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

}
