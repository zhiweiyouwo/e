package com.loy.e.core.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyPageRequest implements Pageable{
	Pageable pageable = null;
	public LoyPageRequest(Pageable pageable){
		this.pageable = pageable;
	}
	
	@Override
	public int getPageNumber() {
		// TODO Auto-generated method stub
		return pageable.getPageNumber();
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageable.getPageSize();
	}

	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return pageable.getOffset()-pageable.getPageSize();
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return pageable.getSort();
	}

	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return pageable.next();
	}

	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return pageable.previousOrFirst();
	}

	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		return pageable.first();
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return pageable.hasPrevious();
	}

}