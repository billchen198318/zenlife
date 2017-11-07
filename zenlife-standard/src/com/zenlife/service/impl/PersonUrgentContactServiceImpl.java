/* 
 * Copyright 2012-2017 qifu of copyright Chen Xin Nien
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
import org.qifu.po.ZlPersonUrgentContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.IPersonUrgentContactDAO;
import com.zenlife.service.IPersonUrgentContactService;

@Service("zenlife.service.PersonUrgentContactService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PersonUrgentContactServiceImpl extends SimpleService<ZlPersonUrgentContact, String> implements IPersonUrgentContactService<ZlPersonUrgentContact, String> {
	protected Logger logger=Logger.getLogger(PersonUrgentContactServiceImpl.class);
	private IPersonUrgentContactDAO<ZlPersonUrgentContact, String> personUrgentContactDAO;
	
	public PersonUrgentContactServiceImpl() {
		super();
	}
	
	public IPersonUrgentContactDAO<ZlPersonUrgentContact, String> getPersonUrgentContactDAO() {
		return personUrgentContactDAO;
	}
	
	@Autowired
	@Resource(name="zenlife.dao.PersonUrgentContactDAO")
	@Required	
	public void setPersonUrgentContactDAO(IPersonUrgentContactDAO<ZlPersonUrgentContact, String> personUrgentContactDAO) {
		this.personUrgentContactDAO = personUrgentContactDAO;
	}
	
	@Override
	protected IBaseDAO<ZlPersonUrgentContact, String> getBaseDataAccessObject() {
		return this.personUrgentContactDAO;
	}

}
