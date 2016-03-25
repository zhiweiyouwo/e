package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class SearchInput extends AbstractInput{
	
    public SearchInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
    public  String getType(){
		return "search_text";
	}
	private String label;
    private String tableName;

	@Override
	public String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<select class=\"form-control chosen-select\" ");
		buffer.append("id=\"").append(getInputId()).append("\"");
		buffer.append(" name=\"").append(getInputName()).append("\"");
		buffer.append(" label=\"").append(this.label).append("\"");
		buffer.append(" tableName=\"").append(this.tableName).append("\">");
		buffer.append(" <option value=\"\"></option> ");
		buffer.append("</select>");
		return buffer.toString();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<input type=\"text\"  i18n=\""+this.getI18nKey()+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control search-query\" ");
		buffer.append("id=\"").append(this.getSearchQueryId()).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" />");
		return buffer.toString();
	}
	public String getInputId(){
		return super.getInputId()+"_id";
	}
	public String getInputName(){
		return super.getInputName()+".id";
	}
}
