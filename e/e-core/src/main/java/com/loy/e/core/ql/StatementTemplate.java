package com.loy.e.core.ql;

import freemarker.template.Template;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class StatementTemplate {

	public enum TYPE{
		HQL,SQL
	}
	private TYPE type;
	Template template;
	
	public StatementTemplate(TYPE type,Template template){
		this.type = type;
		this.template = template;
	}
	
	public TYPE getType() {
		return type;
	}

	public Template getTemplate() {
		return template;
	}
	
	
}