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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.ServiceAuthority;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.model.YesNo;
import org.qifu.base.service.logic.CoreBaseLogicService;
import org.qifu.po.ZlBloodPressure;
import org.qifu.po.ZlPerson;
import org.qifu.po.ZlPersonChronic;
import org.qifu.po.ZlPersonProfile;
import org.qifu.po.ZlPersonUrgentContact;
import org.qifu.po.ZlProductNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.service.IBloodPressureService;
import com.zenlife.service.IPersonChronicService;
import com.zenlife.service.IPersonProfileService;
import com.zenlife.service.IPersonService;
import com.zenlife.service.IPersonUrgentContactService;
import com.zenlife.service.IProductNoticeService;
import com.zenlife.service.logic.IPersonLogicService;

@ServiceAuthority(check=true)
@Service("zenlife.service.logic.PersonLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PersonLogicServiceImpl extends CoreBaseLogicService implements IPersonLogicService {
	protected Logger logger=Logger.getLogger(PersonLogicServiceImpl.class);
	
	private IPersonService<ZlPerson, String> personService;
	private IPersonProfileService<ZlPersonProfile, String> personProfileService;
	private IPersonChronicService<ZlPersonChronic, String> personChronicService;
	private IPersonUrgentContactService<ZlPersonUrgentContact, String> personUrgentContactService;
	private IProductNoticeService<ZlProductNotice, String> productNoticeService;
	private IBloodPressureService<ZlBloodPressure, String> bloodPressureService;
	
	public PersonLogicServiceImpl() {
		super();
	}
	
	public IPersonService<ZlPerson, String> getPersonService() {
		return personService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.PersonService")
	@Required	
	public void setPersonService(IPersonService<ZlPerson, String> personService) {
		this.personService = personService;
	}
	
	public IPersonProfileService<ZlPersonProfile, String> getPersonProfileService() {
		return personProfileService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.PersonProfileService")
	@Required	
	public void setPersonProfileService(IPersonProfileService<ZlPersonProfile, String> personProfileService) {
		this.personProfileService = personProfileService;
	}
	
	public IPersonChronicService<ZlPersonChronic, String> getPersonChronicService() {
		return personChronicService;
	}

	@Autowired
	@Resource(name="zenlife.service.PersonChronicService")
	@Required	
	public void setPersonChronicService(IPersonChronicService<ZlPersonChronic, String> personChronicService) {
		this.personChronicService = personChronicService;
	}
	
	public IPersonUrgentContactService<ZlPersonUrgentContact, String> getPersonUrgentContactService() {
		return personUrgentContactService;
	}
	
	@Autowired
	@Resource(name="zenlife.service.PersonUrgentContactService")
	@Required	
	public void setPersonUrgentContactService(IPersonUrgentContactService<ZlPersonUrgentContact, String> personUrgentContactService) {
		this.personUrgentContactService = personUrgentContactService;
	}	
	
	public IProductNoticeService<ZlProductNotice, String> getProductNoticeService() {
		return productNoticeService;
	}

	@Autowired
	@Resource(name="zenlife.service.ProductNoticeService")
	@Required		
	public void setProductNoticeService(IProductNoticeService<ZlProductNotice, String> productNoticeService) {
		this.productNoticeService = productNoticeService;
	}

	public IBloodPressureService<ZlBloodPressure, String> getBloodPressureService() {
		return bloodPressureService;
	}

	@Autowired
	@Resource(name="zenlife.service.BloodPressureService")
	@Required	
	public void setBloodPressureService(IBloodPressureService<ZlBloodPressure, String> bloodPressureService) {
		this.bloodPressureService = bloodPressureService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> changeValidFlag(ZlPerson person) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getOid())) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<ZlPerson> pResult = this.personService.findEntityByOid(person);
		if (pResult.getValue() == null) {
			throw new ServiceException( pResult.getSystemMessage().getValue() );
		}
		person = pResult.getValue();
		if (YesNo.YES.equals(person.getValidFlag())) {
			person.setValidFlag( YesNo.NO );
		} else {
			person.setValidFlag( YesNo.YES );
		}
		pResult = this.personService.updateEntity(person);
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setSystemMessage( pResult.getSystemMessage() );
		result.setValue( Boolean.TRUE );
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> delete(ZlPerson person) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getOid())) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<ZlPerson> pResult = this.personService.findEntityByOid(person);
		if (pResult.getValue() == null) {
			throw new ServiceException( pResult.getSystemMessage().getValue() );
		}
		person = pResult.getValue();		
		this.deletePersonProfileWithWorkData(person);
		return this.personService.deleteEntity(person);
	}
	
	private void deletePersonProfileWithWorkData(ZlPerson person) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getOid())) {
			return;
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", person.getId());
		
		ZlPersonProfile profile = new ZlPersonProfile();
		profile.setId(person.getId());
		DefaultResult<ZlPersonProfile> zppResult = this.personProfileService.findEntityByUK(profile);
		if (zppResult.getValue() != null) {
			profile = zppResult.getValue();
			this.personProfileService.deleteEntity(profile);
		}
		
		List<ZlPersonChronic> chronicList = this.personChronicService.findListByParams(paramMap);
		for (int i=0; chronicList != null && i<chronicList.size(); i++) {
			this.personChronicService.deleteEntity(chronicList.get(i));
		}
		
		List<ZlPersonUrgentContact> urgentContactList = this.personUrgentContactService.findListByParams(paramMap);
		for (int i=0; urgentContactList != null && i<urgentContactList.size(); i++) {
			this.personUrgentContactService.deleteEntity(urgentContactList.get(i));
		}
		
		paramMap.clear();
		paramMap.put("personId", person.getId());
		
		List<ZlProductNotice> productNoticeList = this.productNoticeService.findListByParams(paramMap);
		for (int i=0; productNoticeList!=null && i<productNoticeList.size(); i++) {
			this.productNoticeService.deleteEntity(productNoticeList.get(i));
		}
		
		List<ZlBloodPressure> bloodPressureList = this.bloodPressureService.findListByParams(paramMap);
		for (int i=0; bloodPressureList!=null && i<bloodPressureList.size(); i++) {
			this.bloodPressureService.deleteEntity(bloodPressureList.get(i));
		}
	}

}
