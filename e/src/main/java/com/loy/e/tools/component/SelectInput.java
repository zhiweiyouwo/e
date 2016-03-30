package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class SelectInput extends AbstractInput{
	
	private String group;
    public SelectInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
    public  String getType(){
		return "select";
	}
	

	@Override
	public String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<select class=\"form-control chosen-select\" group=\""+this.group+"\"");
		buffer.append(" id=\"").append(getInputId()).append("\"");
		buffer.append(" name=\"").append(getInputName()).append("\">");
		buffer.append(" <option value=\"\"></option> ");
		buffer.append("</select>");
		return buffer.toString();
	}

	

	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"col-xs-12 col-sm-2 \">");
		buffer.append("<select group=\""+this.group+"\"   i18n=\""+this.getI18nKey()+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control search-query\" ");
		buffer.append(" id=\"").append(this.getSearchQueryId()).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" >");
		buffer.append(" <option value=\"\">ALL</option> ");
		buffer.append("</select>");
		buffer.append("</div>");
		return buffer.toString();
	}
	public String getInputId(){
		return super.getInputId()+"_id";
	}
	public String getInputName(){
		return super.getInputName()+".id";
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSearchQueryId(){
		return super.getSearchQueryId()+"Id";
	}
	
}