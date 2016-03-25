package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class TextInput extends AbstractInput{
	
	public TextInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
	public  String getType(){
		return "text";
	}
	@Override
	public
	String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<input type=\"text\"  class=\"form-control\" ");
		buffer.append("id=\"").append(getInputId()).append("\"");
		buffer.append(" name=\"").append(this.getInputName()).append("\"");
		buffer.append(" />");
		return buffer.toString();
	}

	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"col-xs-12 col-sm-2 \">");
		buffer.append("<input type=\"text\"  i18n=\""+this.getI18nKey()+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control  col-xs-12 col-sm-2 search-query\" ");
		buffer.append("id=\"").append(this.getSearchQueryId()).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" />");
		buffer.append("</div>");
		return buffer.toString();
	}
}
