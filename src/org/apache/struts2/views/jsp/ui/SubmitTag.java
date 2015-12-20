/*
 * $Id: SubmitTag.java 681101 2008-07-30 16:06:15Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * 该类对应着<s:submit>标签，重写该类实现对权限细粒度的划分
 */

package org.apache.struts2.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts2.components.Component;
import org.apache.struts2.components.Submit;

import com.kdyzm.utils.ValidateUtils;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @see Submit
 */
public class SubmitTag extends AbstractClosingTag {

    private static final long serialVersionUID = 2179281109958301343L;

    protected String action;
    protected String method;
    protected String align;
    protected String type;
    protected String src;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Submit(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Submit submit = ((Submit) component);
        submit.setAction(action);
        submit.setMethod(method);
        submit.setAlign(align);
        submit.setType(type);
        submit.setSrc(src);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSrc(String src) {
        this.src = src;
    }
    //Submit标签需要重写两个方法才行
    @Override
    public int doStartTag() throws JspException {
    	boolean result=ValidateUtils.hasRight(getFormNamespace(), getFormActionName(), (HttpServletRequest)pageContext.getRequest(), null);
    	if(result==false){
    		return SKIP_BODY;
    	}else{
    		return super.doStartTag();
    	}
    }
    @Override
    public int doEndTag() throws JspException {
//    	System.out.println("表单标签："+getFormNamespace()+getFormActionName());
    	boolean result=ValidateUtils.hasRight(getFormNamespace(), getFormActionName(), (HttpServletRequest)pageContext.getRequest(), null);
    	if(result==false){
    		return SKIP_BODY;
    	}else{
    		return super.doEndTag();
    	}
    }
    public String getFormNamespace(){
    	Tag tag=this.getParent();
    	while(tag!=null){
    		if(tag instanceof FormTag){
    			FormTag formTag=(FormTag) tag;
    			String namespace=formTag.namespace;
    			if(namespace==null||"/".equals(namespace)){
    				namespace="";
    			}
    			return namespace;
    		}else{
    			tag=tag.getParent();
    		}
    	}
    	return "";
    }
    public String getFormActionName(){
    	Tag tag=this.getParent();
    	while(tag!=null){
    		if(tag instanceof FormTag){
    			FormTag formTag=(FormTag) tag;
    			String actionName=formTag.action;
    			if(actionName!=null&&actionName.endsWith(".action")){
    				actionName=actionName.substring(0, actionName.indexOf("."));
    				return actionName;
    			}else{
    				actionName="";
    				return actionName;
    			}
    		}else{
    			tag=tag.getParent();
    		}
    	}
    	return "";
    }
}
