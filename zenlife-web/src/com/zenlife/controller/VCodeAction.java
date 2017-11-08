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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qifu.base.controller.BaseController;
import org.qifu.base.model.ControllerMethodAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zenlife.base.ZenLifeConstants;

@EnableWebMvc
@Controller
public class VCodeAction extends BaseController {
	private static final String RND_STR[]="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0".split(",");
	
	@ControllerMethodAuthority(check = false, programId = "ZENLIFE_FE_9999Q")
	@RequestMapping(value = "/vCode.do", method = RequestMethod.GET)
	public String createCodeImage(HttpServletRequest request, HttpServletResponse response) {
		ByteArrayOutputStream output = null;
		try {
	        int width=100, height=25;  
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	        Graphics g = image.getGraphics();
	        Random random = new Random();
	        //g.setColor(getRandColor(200,250));
	        g.setColor(new Color(238, 238, 238));
	        g.fillRect(0, 0, width, height); 
	        g.setFont(new Font("",Font.BOLD,18));
	        //g.setColor(getRandColor(160,200));
	        g.setColor(new Color(238, 238, 238));
	        for (int i=0;i<155;i++) {
	            int x = random.nextInt(width);
	            int y = random.nextInt(height); 
	            int xl = random.nextInt(12);
	            int yl = random.nextInt(12);
	            g.drawLine(x,y,x+xl,y+yl);
	        }
	        String sRand="";
	        for (int i=0;i<4;i++) {
	            String rand=RND_STR[random.nextInt(RND_STR.length)];
	            sRand+=rand;
	            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	            g.drawString(rand, 18*i, 20); // 13*i+6, 16 改  20            
	        }
	        request.getSession().setAttribute(ZenLifeConstants.SESS_VCODE, sRand);
	        g.dispose();
	        output = new ByteArrayOutputStream();  
	        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);  
	        ImageIO.write(image, "PNG", imageOut);  
	        imageOut.close();  
	        ServletOutputStream ouputStream = response.getOutputStream();
	        ouputStream.write(output.toByteArray());
	        ouputStream.flush();
	        ouputStream.close();
		} catch (Exception e) {
			this.getExceptionPage(e, request);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				output = null;
			}
		}
		return null;
	}	

}
