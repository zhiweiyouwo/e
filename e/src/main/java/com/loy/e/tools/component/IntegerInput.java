package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class IntegerInput extends AbstractInput{

	public IntegerInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
	private int min = 0;
	private int max = 9999999;
	private int step = 1;
	@Override
	public String getHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<input type=\"text\"  class=\"form-control spinner\" ");
		buffer.append("id=\"").append(getInputId()).append("\"");
		buffer.append(" name=\"").append(this.getInputName()).append("\"");
		buffer.append(" />");
		return buffer.toString();
	}
	@Override
	public String getConditionHtml() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"col-xs-12 col-sm-2 \">");
		buffer.append("<input type=\"text\"  i18n=\""+this.getI18nKey()+"\" placeholder =\""+this.getLabelName()+"\" class=\"form-control spinner search-query\" ");
		buffer.append("id=\"").append(this.getSearchQueryId()).append("\"");
		buffer.append(" name=\"").append(this.getCombineFieldName()).append("\"");
		buffer.append(" />");
		buffer.append("</div>");
		return buffer.toString();
	}
	@Override
	public String getType() {
		return "integer";
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}

	
}
