package com.loy.e.core.converter;

import org.springframework.data.domain.PageImpl;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public abstract class AbsPageConverter<S,T> implements Converter, org.springframework.core.convert.converter.Converter<S, T>{

	@Override
	public Object converter(Object source) {
		PageImpl p = (PageImpl)source;
		Object page = p.map(this);
		return page;
	}

}
