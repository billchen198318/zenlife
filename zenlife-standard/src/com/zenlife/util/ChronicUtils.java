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
package com.zenlife.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.qifu.po.ZlPersonChronic;

public class ChronicUtils {
	
	/**
	 * 提供給 person-edit.jsp ( q:checkBox checkedTest ) 用的
	 * 
	 * @param chronicId
	 * @param personChronicList
	 * @return
	 */
	public static boolean isCheck(String chronicId, List<ZlPersonChronic> personChronicList) {
		if (StringUtils.isBlank(chronicId) || null == personChronicList || personChronicList.size() < 1) {
			return false;
		}
		boolean f = false;
		for (int i=0; !f && i<personChronicList.size(); i++) {
			if (chronicId.equals(personChronicList.get(i).getCid())) {
				f = true;
			}
		}
		return f;
	}
	
}
