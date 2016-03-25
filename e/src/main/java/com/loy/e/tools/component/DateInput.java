package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class DateInput extends AbstractInput{
    
	public DateInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
	public  String getType(){
		return "date";
	}
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
		if(this.getCount()>1){
			StringBuilder buffer = new StringBuilder();
			
			buffer.append("<div class=\"input-group col-xs-12 col-sm-2\"  style=\"float:left;padding-left: 15px;padding-right: 15px\">");
			buffer.append("<input type=\"text\"  i18n=\""+this.getI18nKey()+"Start"+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control  date-picker\" ");
			String fieldName = this.getFieldName();
			fieldName.replace("\\.", "_");
			buffer.append("id=\"").append(this.getSearchQueryId()+"_start").append("\"");
			buffer.append(" name=\"").append(this.getFieldName()).append("\"");
			buffer.append(" />");
			buffer.append("<span class=\"input-group-addon\"><i class=\"fa fa-calendar bigger-110\"></i></span>");
			buffer.append("</div>");
			
			
			buffer.append("<div class=\"input-group col-xs-12 col-sm-2\" style=\"float:left;padding-left: 15px;padding-right: 15px\">");
			buffer.append("<input type=\"text\" i18n=\""+this.getI18nKey()+"End"+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control  date-picker\" ");
			fieldName = this.getFieldName();
			fieldName.replace("\\.", "_");
			buffer.append("id=\"").append(this.getSearchQueryId()+"_end").append("\"");
			buffer.append(" name=\"").append(this.getFieldName()).append("\"");
			buffer.append(" />");
			buffer.append("<span class=\"input-group-addon\"><i class=\"fa fa-calendar bigger-110\"></i></span>");
			buffer.append("</div>");
			
			return buffer.toString();
		}else{
			StringBuilder buffer = new StringBuilder();
			buffer.append("<div class=\"input-group col-xs-12 col-sm-2\" style=\"float:left\">");
			buffer.append("<input type=\"text\"  i18n=\""+this.getI18nKey()+"\" placeholder =\""+this.getLabelName()+"\"  class=\"form-control search-query date-picker\" ");
			String fieldName = this.getFieldName();
			fieldName.replace("\\.", "_");
			buffer.append("id=\"").append(this.getSearchQueryId()).append("\"");
			buffer.append(" name=\"").append(this.getFieldName()).append("\"");
			buffer.append(" />");
			buffer.append("<span class=\"input-group-addon\"><i class=\"fa fa-calendar bigger-110\"></i></span>");
			buffer.append("</div>");
			return buffer.toString();
		}
		
	}
	public String getFormatter(){
		return "date";
	}
}
