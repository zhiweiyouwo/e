package com.loy.e.tools.component;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class SearchInput extends AbstractInput{
	
    private String label;
    private String tableName;

	@Override
	public String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<select class=\"chosen-select\" ");
		String fieldName = this.getFieldName();
		fieldName = fieldName.replaceAll("\\.", "_");
		buffer.append("id=\"").append(fieldName+"_id").append("\"");
		buffer.append(" name=\"").append(this.getFieldName()+".id").append("\"");
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
		return null;
	}
	
}
