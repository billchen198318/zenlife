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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.service.SimpleService;
import org.qifu.po.ZlProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zenlife.dao.IProductDAO;
import com.zenlife.service.IProductService;

@Service("zenlife.service.ProductService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ProductServiceImpl extends SimpleService<ZlProduct, String> implements IProductService<ZlProduct, String> {
	protected Logger logger=Logger.getLogger(ProductServiceImpl.class);
	private IProductDAO<ZlProduct, String> productDAO;
	
	public ProductServiceImpl() {
		super();
	}

	public IProductDAO<ZlProduct, String> getProductDAO() {
		return productDAO;
	}

	@Autowired
	@Resource(name="zenlife.dao.ProductDAO")
	@Required	
	public void setProductDAO(IProductDAO<ZlProduct, String> productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	protected IBaseDAO<ZlProduct, String> getBaseDataAccessObject() {
		return this.productDAO;
	}

}
