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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.PageOf;
import org.qifu.base.model.QueryResult;
import org.qifu.base.model.SearchValue;
import org.qifu.base.service.SimpleService;
import org.qifu.po.ZlPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.IPersonDAO;
import com.zenlife.service.IPersonService;

@Service("zenlife.service.PersonService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PersonServiceImpl extends SimpleService<ZlPerson, String> implements IPersonService<ZlPerson, String> {
	protected Logger logger=Logger.getLogger(PersonServiceImpl.class);
	private IPersonDAO<ZlPerson, String> personDAO;
	
	public PersonServiceImpl() {
		super();
	}
	
	public IPersonDAO<ZlPerson, String> getPersonDAO() {
		return personDAO;
	}
	
	@Autowired
	@Resource(name="zenlife.dao.PersonDAO")
	@Required	
	public void setPersonDAO(IPersonDAO<ZlPerson, String> personDAO) {
		this.personDAO = personDAO;
	}
	
	@Override
	protected IBaseDAO<ZlPerson, String> getBaseDataAccessObject() {
		return this.personDAO;
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		return super.getQueryParamHandler(searchValue)
				.fullEquals4TextField("id")
				.containingLike("name")
				.getValue();
	}

	@Override
	public QueryResult<List<ZlPerson>> findGridResult(SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<ZlPerson>> result=this.personDAO.findPageQueryResultByQueryName("findPersonPageGrid", params, offset, limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

}
