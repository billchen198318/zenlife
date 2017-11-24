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
package com.zenlife.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.ZlPerson;
import org.qifu.po.ZlPersonProfile;
import org.qifu.po.ZlProduct;
import org.qifu.po.ZlProductNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.service.IPersonProfileService;
import com.zenlife.service.IPersonService;
import com.zenlife.service.IProductService;
import com.zenlife.service.logic.IProductLogicService;

@EnableWebMvc
@Controller
public class ProductAction extends BaseController {
	
	private IProductService<ZlProduct, String> productService;
	private IPersonService<ZlPerson, String> personService;
	private IPersonProfileService<ZlPersonProfile, String> personProfileService;	
	private IProductLogicService productLogicService;

	public IProductService<ZlProduct, String> getProductService() {
		return productService;
	}

	@Autowired
	@Resource(name="zenlife.service.ProductService")
	@Required	
	public void setProductService(IProductService<ZlProduct, String> productService) {
		this.productService = productService;
	}
	
	private List<ZlProduct> findProductList() throws ServiceException, Exception {
		return this.productService.findListByParams(null);
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
	
	public IProductLogicService getProductLogicService() {
		return productLogicService;
	}

	@Autowired
	@Resource(name="zenlife.service.logic.ProductLogicService")
	@Required	
	public void setProductLogicService(IProductLogicService productLogicService) {
		this.productLogicService = productLogicService;
	}

	private void fillBaseData(ModelAndView mv, ZlProduct product) throws ControllerException, ServiceException, Exception {
		if (null == product || StringUtils.isBlank(product.getOid())) {
			throw new ControllerException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<ZlProduct> result = this.productService.findEntityByOid(product);
		if (result.getValue() == null) {
			throw new ControllerException(result.getSystemMessage().getValue());
		}
		mv.addObject("product", result.getValue());
		ZlPerson person = new ZlPerson();
		person.setId( this.getAccountId() );
		DefaultResult<ZlPerson> pResult = this.personService.findEntityByUK(person);
		if (pResult.getValue() == null) {
			throw new ControllerException(pResult.getSystemMessage().getValue());
		}
		mv.addObject("person", pResult.getValue());
		ZlPersonProfile profile = new ZlPersonProfile();
		profile.setId( this.getAccountId() );
		DefaultResult<ZlPersonProfile> bResult = this.personProfileService.findEntityByUK(profile);
		if (bResult.getValue() != null) {
			profile = bResult.getValue();
		}
		if (StringUtils.isBlank(profile.getAddress())) {
			profile.setAddress("");
		}
		mv.addObject("profile", profile);
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0005Q")
	@RequestMapping(value = "/product.do", method = RequestMethod.GET)
	public ModelAndView productHome(HttpServletRequest request) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			mv.addObject("productList", this.findProductList());
			viewName = "product/product-home";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}		
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0005Q")
	@RequestMapping(value = "/productNotice.do", method = RequestMethod.GET)
	public ModelAndView productNotice(HttpServletRequest request, ZlProduct product) {
		String viewName = PAGE_SYS_ERROR;
		String c = "";
		ModelAndView mv = this.getDefaultModelAndView();
		try {
			c = request.getParameter("c");
			this.fillBaseData(mv, product);
			viewName = "product/product-notice";
		} catch (AuthorityException e) {
			viewName = this.getAuthorityExceptionPage(e, request);
		} catch (ServiceException | ControllerException e) {
			viewName = this.getServiceOrControllerExceptionPage(e, request);
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		}
		mv.addObject("c", super.defaultString(c));
		mv.setViewName(viewName);
		return mv;
	}		
	
	private void checkFieldsForParam(DefaultControllerJsonResultObj<ZlProductNotice> result, String productOid, ZlProductNotice productNotice) throws AuthorityException, ControllerException, ServiceException, Exception {
		if (StringUtils.isBlank(productOid)) {
			throw new ControllerException("資料錯誤");
		}
		this.getCheckControllerFieldHandler(result)
		.testField("phone", productNotice, "@org.apache.commons.lang3.StringUtils@isBlank(phone)", "手機號碼必須填寫")
		.throwMessage();		
	}
	
	private void save(DefaultControllerJsonResultObj<ZlProductNotice> result, String productOid, ZlProductNotice productNotice) throws AuthorityException, ControllerException, ServiceException, Exception {
		this.checkFieldsForParam(result, productOid, productNotice);
		DefaultResult<ZlProductNotice> saveResult = this.productLogicService.createNotice(
				productOid, productNotice, this.getAccountId());
		if (saveResult.getValue() != null) {
			result.setValue( saveResult.getValue() );
			result.setSuccess( YES );
		}
		result.setMessage( saveResult.getSystemMessage().getValue() );		
	}
	
	@ControllerMethodAuthority(check = true, programId = "ZENLIFE_FE_0005Q")
	@RequestMapping(value = "/productNoticeSaveJson.do", produces = "application/json")
	public @ResponseBody DefaultControllerJsonResultObj<ZlProductNotice> doSaveNotice(HttpServletRequest request, ZlProductNotice productNotice) {
		DefaultControllerJsonResultObj<ZlProductNotice> result = this.getDefaultJsonResult("ZENLIFE_FE_0005Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		try {
			this.save(result, request.getParameter("prodOid"), productNotice);
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			this.exceptionResult(result, e);
		}
		return result;
	}		
	
}
