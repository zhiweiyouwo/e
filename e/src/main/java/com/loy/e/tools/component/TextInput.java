package com.loy.e.tools.component;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class TextInput extends AbstractInput{
	
	@Override
	public
	String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<input type=\"text\"  class=\"form-control\" ");
		String fieldName = this.getFieldName();
		fieldName.replace("\\.", "_");
		buffer.append("id=\"").append(fieldName).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" />");
		return buffer.toString();
	}
}
