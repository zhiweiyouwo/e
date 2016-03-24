package com.loy.e.tools.component;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class DateInput extends AbstractInput{
    
	@Override
	public String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"input-group\">");
		buffer.append("<input type=\"text\"  class=\"form-control  date-picker\" ");
		String fieldName = this.getFieldName();
		fieldName.replace("\\.", "_");
		buffer.append("id=\"").append(fieldName).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" />");
		buffer.append("<span class=\"input-group-addon\"><i class=\"fa fa-calendar bigger-110\"></i></span>");
		buffer.append("</div>");
		return buffer.toString();
	}

	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"input-group\">");
		buffer.append("<input type=\"text\"  class=\"form-control  date-picker\" ");
		String fieldName = this.getFieldName();
		fieldName.replace("\\.", "_");
		buffer.append("id=\"").append(fieldName).append("\"");
		buffer.append(" name=\"").append(this.getFieldName()).append("\"");
		buffer.append(" />");
		buffer.append("<span class=\"input-group-addon\"><i class=\"fa fa-calendar bigger-110\"></i></span>");
		buffer.append("</div>");
		return buffer.toString();
	}

}
