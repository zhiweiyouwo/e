package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class CheckboxInput extends AbstractInput{
	
	public CheckboxInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
	public  String getType(){
		return "checkbox";
	}
	@Override
	public
	String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<input type=\"checkbox\"  class=\"form-control ace ace-switch ace-switch-5\" ");
		buffer.append("id=\"").append(getInputId()).append("\"");
		buffer.append(" name=\"").append(this.getInputName()).append("\"");
		buffer.append(" />");
		buffer.append("<span class=\"lbl\"></span>");
		return buffer.toString();
	}

	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"col-xs-12 col-sm-2 \">");
		buffer.append("<input type=\"checkbox\"  i18n=\""+this.getI18nKey()+"\"  class=\"form-control ace ace-switch ace-switch-5  search-query\" ");
		buffer.append("id=\"").append(this.getSearchQueryId()).append("\"");
		buffer.append(" name=\"").append(this.getCombineFieldName()).append("\"");
		buffer.append(" />");
		buffer.append("<span i18n=\""+this.getI18nKey()+"\"  class=\"lbl\">'+'</span>");
		buffer.append("</div>");
		return buffer.toString();
	}
}
