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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.ServiceAuthority;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.model.SystemMessage;
import org.qifu.base.model.YesNo;
import org.qifu.base.service.logic.CoreBaseLogicService;
import org.qifu.model.TemplateResultObj;
import org.qifu.po.TbSysMailHelper;
import org.qifu.po.ZlPerson;
import org.qifu.po.ZlPersonChronic;
import org.qifu.po.ZlPersonProfile;
import org.qifu.po.ZlPersonUrgentContact;
import org.qifu.service.ISysMailHelperService;
import org.qifu.util.SystemSettingConfigureUtils;
import org.qifu.util.TemplateUtils;
import org.qifu.vo.SysMailHelperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.service.IPersonChronicService;
import com.zenlife.service.IPersonProfileService;
import com.zenlife.service.IPersonService;
import com.zenlife.service.IPersonUrgentContactService;
import com.zenlife.service.logic.IFePersonLogicService;

/**
 * 給前台 register 用的版本, 不要check權限, 要自己填入 CUSERID, CDATE
 */
@ServiceAuthority(check=false)
@Service("zenlife.service.logic.FePersonLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class FePersonLogicServiceImpl extends CoreBaseLogicService implements IFePersonLogicService {
	protected Logger logger=Logger.getLogger(FePersonLogicServiceImpl.class);
	
	private IPersonService<ZlPerson, String> personService;
	private IPersonProfileService<ZlPersonProfile, String> personProfileService;
	private IPersonChronicService<ZlPersonChronic, String> personChronicService;
	private ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService;
	private IPersonUrgentContactService<ZlPersonUrgentContact, String> personUrgentContactService;
	
	public FePersonLogicServiceImpl() {
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
	
	public ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> getSysMailHelperService() {
		return sysMailHelperService;
	}

	@Autowired
	@Resource(name="core.service.SysMailHelperService")
	@Required	
	public void setSysMailHelperService(ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService) {
		this.sysMailHelperService = sysMailHelperService;
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
	
	private void noticeValidMail(ZlPerson person, String pwd) {
		
		if (super.isBlank(person.getMail())) {
			return;
		}
		Map<String, Object> tplParamMap = new HashMap<String, Object>();
		tplParamMap.put("personId", person.getId());
		tplParamMap.put("password", pwd);
		try {
			TemplateResultObj tplResult = TemplateUtils.getResult("ZL-TPL-001", tplParamMap);
			SysMailHelperVO mailHelper = new SysMailHelperVO();
			mailHelper.setSubject(tplResult.getTitle());
			mailHelper.setText( tplResult.getContent().getBytes(Constants.BASE_ENCODING) );
			mailHelper.setMailFrom( SystemSettingConfigureUtils.getMailDefaultFromValue() );
			mailHelper.setMailTo( person.getMail() );
			mailHelper.setMailId( this.sysMailHelperService.findForMaxMailIdComplete("ZL-REG") );
			mailHelper.setRetainFlag( YesNo.NO );
			this.sysMailHelperService.saveObject(mailHelper);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<ZlPerson> create(ZlPerson person) throws ServiceException, Exception {
		if (null == person) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		logger.info("call createForFrontEnd Id:" + person.getId());
		DefaultResult<ZlPerson> result=new DefaultResult<ZlPerson>();
		if (this.personService.countByEntityUK(person) >0) {
			//throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_IS_EXIST));
			throw new ServiceException("已經有相同的帳戶注冊了，請更換帳戶!");
		}
		String befPwd = person.getPassword();
		person.setOid( this.generateOid() );
		person.setCuserid("admin");
		person.setCdate(new Date());
		person.setValidFlag(YesNo.YES); // 使用者都是老人, 沒法叫他們很麻煩的驗證了
		person.setPassword( this.getAccountService().tranPassword(person.getPassword()) );
		ZlPerson insertEntity = this.personService.save(person);
		if (insertEntity!=null && insertEntity.getOid()!=null ) {
			result.setValue(insertEntity);
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS)));
			this.noticeValidMail(insertEntity, befPwd);
		} else {
			result.setSystemMessage(new SystemMessage(SysMessageUtil.get(SysMsgConstants.INSERT_FAIL)));
		}
		return result;				
	}
	
}
