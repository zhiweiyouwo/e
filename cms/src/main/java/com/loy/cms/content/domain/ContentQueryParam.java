/**
 * 
 */
package com.loy.cms.content.domain;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.query.Op;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ContentQueryParam {

	@ConditionParam(name = "title", op = Op.like)
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
