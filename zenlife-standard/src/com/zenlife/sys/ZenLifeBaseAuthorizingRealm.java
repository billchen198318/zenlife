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
package com.zenlife.sys;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.qifu.base.AppContext;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.YesNo;
import org.qifu.po.ZlPerson;
import org.qifu.sys.GreenStepBaseUsernamePasswordToken;

import com.zenlife.service.IPersonService;

public class ZenLifeBaseAuthorizingRealm extends AuthorizingRealm {
	
	private SimpleAuthorizationInfo getSimpleAuthorizationInfo(String username) throws Exception {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("*"); // ZenLife 目前不需要
		info.addStringPermission("*"); // ZenLife 目前不需要
		return info;		
	}	

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.fromRealm(this.getName()).iterator().next();
		if (username==null || StringUtils.isBlank(username)) {
			return null;
		}
		try {
			return this.getSimpleAuthorizationInfo(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		GreenStepBaseUsernamePasswordToken token = (GreenStepBaseUsernamePasswordToken)authenticationToken;
		String account = token.getUsername();
		ZlPerson person = new ZlPerson();
		person.setId(account);
		try {
			@SuppressWarnings("unchecked")
			IPersonService<ZlPerson, String> personService = (IPersonService<ZlPerson, String>) AppContext.getBean("zenlife.service.PersonService");
			person = personService.findByEntityUK( person );
			if (null == person || !YesNo.YES.equals(person.getValidFlag())) {
				return null;
			}			
			return new SimpleAuthenticationInfo(person.getId(), person.getPassword(), this.getName());				
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
