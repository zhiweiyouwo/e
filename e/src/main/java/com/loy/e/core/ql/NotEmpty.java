package com.loy.e.core.ql;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

public class NotEmpty implements TemplateDirectiveModel{  
  
    @Override  
    public void execute(Environment env, Map params, TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException {  
    	 Writer out = env.getOut();
    	 Object name = params.get("name");
    	 boolean empty = false;
    	 if(name == null){
    		 empty = true;
		 }else{
			  String n = name.toString();
			  Object obj = env.__getitem__(n);
			  if(obj == null){
				  empty = true;
			  }else{
				  if(obj instanceof String){
					  if(StringUtils.isEmpty(obj.toString())){
						  empty = true;
					  }
				  }
			  }
		 }
 
    	 if (body != null && empty) {
    		 out = new StringWriter();
         } 
    	 body.render(out);
    }   
}