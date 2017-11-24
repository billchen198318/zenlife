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
package com.zenlife.util;

import org.qifu.base.AppContext;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.po.TbSysCode;
import org.qifu.service.ISysCodeService;
import org.qifu.vo.SysCodeVO;

@SuppressWarnings("unchecked")
public class ZenLifeSettingConfigureUtils {
	private static final String CODE_TYPE = "CNF";
	private static final String _MAIL_PRODUCT_NOTICE_REVICE_CODE = "CNF_CONF006";
	
	private static ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;	
	
	static {
		sysCodeService = (ISysCodeService<SysCodeVO, TbSysCode, String>)AppContext.getBean("core.service.SysCodeService");
	}
	
	public static SysCodeVO getCode(String code) {
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType(CODE_TYPE);
		sysCode.setCode(code);
		try {
			DefaultResult<SysCodeVO> result = sysCodeService.findByUK(sysCode);
			if (result.getValue()!=null) {
				sysCode = result.getValue();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysCode;
	}
	
	public static SysCodeVO getProductNoticeReciveCode() {
		return getCode(_MAIL_PRODUCT_NOTICE_REVICE_CODE);
	}
	
	public static String getProductNoticeReciveMail() {
		return getProductNoticeReciveCode().getParam1();
	}		
	
}
