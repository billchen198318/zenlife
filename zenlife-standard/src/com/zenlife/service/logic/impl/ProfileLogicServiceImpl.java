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
import org.qifu.base.service.logic.CoreBaseLogicService;
import org.qifu.po.ZlChronic;
import org.qifu.po.ZlPerson;
import org.qifu.po.ZlPersonChronic;
import org.qifu.po.ZlPersonProfile;
import org.qifu.po.ZlPersonUrgentContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.service.IChronicService;
import com.zenlife.service.IPersonChronicService;
import com.zenlife.service.IPersonProfileService;
import com.zenlife.service.IPersonService;
import com.zenlife.service.IPersonUrgentContactService;
import com.zenlife.service.logic.IProfileLogicService;

@ServiceAuthority(check=true)
@Service("zenlife.service.logic.ProfileLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ProfileLogicServiceImpl extends CoreBaseLogicService implements IProfileLogicService {
	protected Logger logger=Logger.getLogger(ProfileLogicServiceImpl.class);
	
	private IPersonService<ZlPerson, String> personService;
	private IPersonProfileService<ZlPersonProfile, String> personProfileService;
	private IPersonChronicService<ZlPersonChronic, String> personChronicService;
	private IPersonUrgentContactService<ZlPersonUrgentContact, String> personUrgentContactService;
	private IChronicService<ZlChronic, String> chronicService;
	
	public ProfileLogicServiceImpl() {
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

	public IChronicService<ZlChronic, String> getChronicService() {
		return chronicService;
	}

	@Autowired
	@Resource(name="zenlife.service.ChronicService")
	@Required	
	public void setChronicService(IChronicService<ZlChronic, String> chronicService) {
		this.chronicService = chronicService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<ZlPersonProfile> updateProfile(ZlPerson person, ZlPersonProfile profile, List<String> chronicList, List<ZlPersonUrgentContact> urgentContactList) throws ServiceException, Exception {
		if (null == person || null == profile || super.isBlank(person.getOid())) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<ZlPerson> mResult = this.personService.findEntityByOid(person);
		if (mResult.getValue() == null) {
			throw new ServiceException( mResult.getSystemMessage().getValue() );
		}
		mResult.getValue().setPhone( person.getPhone() );
		mResult.getValue().setName( person.getName() );
		mResult.getValue().setTel( person.getTel() );
		this.personService.updateEntity( mResult.getValue() );
		
		profile.setId( mResult.getValue().getId() );
		
		DefaultResult<ZlPersonProfile> result = this.personProfileService.findEntityByUK(profile); 
		if (result.getValue() == null) { // 之前沒有 profile
			result = this.personProfileService.saveEntity(profile);
		} else {
			result = this.personProfileService.updateEntity(profile);
		}
		this.deleteChronic(person);
		this.createChronic(person, chronicList);
		this.deleteUrgentContact(person);
		this.createUrgentContact(person, urgentContactList);
		return result;
	}
	
	private void createChronic(ZlPerson person, List<String> chronicList) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getId()) || null == chronicList || chronicList.size() < 1) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (String cid : chronicList) {
			paramMap.clear();
			paramMap.put("id", cid);
			if (this.chronicService.countByParams(paramMap) < 1) {
				throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS));
			}
			ZlPersonChronic personChronic = new ZlPersonChronic();
			personChronic.setId(person.getId());
			personChronic.setCid(cid);
			this.personChronicService.saveEntity(personChronic);
		}
	}
	
	private void deleteChronic(ZlPerson person) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getId())) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", person.getId());
		List<ZlPersonChronic> chronicList = this.personChronicService.findListByParams(paramMap);
		if (null == chronicList || chronicList.size() < 1) {
			return;
		}
		for (ZlPersonChronic personChronic : chronicList) {
			this.personChronicService.delete(personChronic);
		}
	}
	
	private void createUrgentContact(ZlPerson person, List<ZlPersonUrgentContact> urgentContactList) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getId()) || null == urgentContactList || urgentContactList.size() < 1) {
			return;
		}
		for (ZlPersonUrgentContact urgentContact : urgentContactList) {
			urgentContact.setId(person.getId());
			this.personUrgentContactService.saveEntity(urgentContact);
		}
	}
	
	private void deleteUrgentContact(ZlPerson person) throws ServiceException, Exception {
		if (null == person || super.isBlank(person.getId())) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", person.getId());
		List<ZlPersonUrgentContact> urgentContactList = this.personUrgentContactService.findListByParams(paramMap);
		if (null == urgentContactList || urgentContactList.size() < 1) {
			return;
		}
		for (ZlPersonUrgentContact urgentContact : urgentContactList) {
			this.personUrgentContactService.delete(urgentContact);
		}
	}
	
}
