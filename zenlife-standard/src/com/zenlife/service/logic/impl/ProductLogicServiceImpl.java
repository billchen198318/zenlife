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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.ServiceAuthority;
import org.qifu.base.model.ServiceMethodAuthority;
import org.qifu.base.model.ServiceMethodType;
import org.qifu.base.model.YesNo;
import org.qifu.base.service.logic.CoreBaseLogicService;
import org.qifu.model.TemplateResultObj;
import org.qifu.po.TbSysMailHelper;
import org.qifu.po.ZlPerson;
import org.qifu.po.ZlProduct;
import org.qifu.po.ZlProductNotice;
import org.qifu.service.ISysMailHelperService;
import org.qifu.util.SimpleUtils;
import org.qifu.util.SystemSettingConfigureUtils;
import org.qifu.util.TemplateUtils;
import org.qifu.vo.SysMailHelperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.service.IPersonService;
import com.zenlife.service.IProductNoticeService;
import com.zenlife.service.IProductService;
import com.zenlife.service.logic.IProductLogicService;
import com.zenlife.util.ZenLifeSettingConfigureUtils;

@ServiceAuthority(check=true)
@Service("zenlife.service.logic.ProductLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ProductLogicServiceImpl extends CoreBaseLogicService implements IProductLogicService {
	protected Logger logger=Logger.getLogger(ProductLogicServiceImpl.class);
	
	private IProductService<ZlProduct, String> productService;
	private IProductNoticeService<ZlProductNotice, String> productNoticeService;
	private IPersonService<ZlPerson, String> personService;
	private ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService;
	
	public ProductLogicServiceImpl() {
		super();
	}
	
	public IProductService<ZlProduct, String> getProductService() {
		return productService;
	}

	@Autowired
	@Resource(name="zenlife.service.ProductService")
	@Required	
	public void setProductService(IProductService<ZlProduct, String> productService) {
		this.productService = productService;
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

	public IPersonService<ZlPerson, String> getPersonService() {
		return personService;
	}

	@Autowired
	@Resource(name="zenlife.service.PersonService")
	@Required	
	public void setPersonService(IPersonService<ZlPerson, String> personService) {
		this.personService = personService;
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
	
	private void noticeProductToReceive(ZlProduct product, ZlProductNotice productNotice, ZlPerson person) {
		Map<String, Object> tplParamMap = new HashMap<String, Object>();
		tplParamMap.put("personId", person.getId());
		tplParamMap.put("name", person.getName());
		tplParamMap.put("phone", person.getPhone());
		tplParamMap.put("prodId", product.getProdId());
		tplParamMap.put("prodName", product.getName());
		tplParamMap.put("noticePhone", productNotice.getPhone());
		tplParamMap.put("noticeAddress", super.defaultString(productNotice.getAddress()));
		tplParamMap.put("message", super.defaultString(productNotice.getMessage()));
		try {
			TemplateResultObj tplResult = TemplateUtils.getResult("ZL-TPL-003", tplParamMap);
			SysMailHelperVO mailHelper = new SysMailHelperVO();
			mailHelper.setSubject(tplResult.getTitle());
			mailHelper.setText( tplResult.getContent().getBytes(Constants.BASE_ENCODING) );
			mailHelper.setMailFrom( SystemSettingConfigureUtils.getMailDefaultFromValue() );
			mailHelper.setMailTo( ZenLifeSettingConfigureUtils.getProductNoticeReciveMail() );
			mailHelper.setMailId( this.sysMailHelperService.findForMaxMailIdComplete(SimpleUtils.getStrYMD("")) );
			mailHelper.setRetainFlag( YesNo.NO );
			mailHelper.setSuccessFlag( YesNo.NO );
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
	public DefaultResult<ZlProductNotice> createNotice(String productOid, ZlProductNotice productNotice, String personId) throws ServiceException, Exception {
		if (super.isBlank(personId) || super.isBlank(productOid) || null == productNotice) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		ZlProduct product = new ZlProduct();
		product.setOid(productOid);
		DefaultResult<ZlProduct> mResult = this.productService.findEntityByOid(product);
		if (mResult.getValue() == null) {
			throw new ControllerException(mResult.getSystemMessage().getValue());
		}		
		product = mResult.getValue();
		
		ZlPerson person = new ZlPerson();
		person.setId( this.getAccountId() );
		DefaultResult<ZlPerson> pResult = this.personService.findEntityByUK(person);
		if (pResult.getValue() == null) {
			throw new ControllerException(pResult.getSystemMessage().getValue());
		}
		person = pResult.getValue();
		
		productNotice.setPersonId(personId);
		productNotice.setProdId(product.getProdId());
		super.setStringValueMaxLength(productNotice, "message", 500);
		DefaultResult<ZlProductNotice> result = this.productNoticeService.saveEntity(productNotice);
		if (result.getValue() != null) {
			this.noticeProductToReceive(product, productNotice, person);
		}
		
		return result;
	}
	
}
