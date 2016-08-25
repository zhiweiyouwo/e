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
public class LoyPageRequest implements Pageable {
    Pageable pageable = null;

    public LoyPageRequest(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public int getOffset() {
        int offset = pageable.getOffset() - pageable.getPageSize();
        if (offset < 0) {
            offset = 0;
        }
        return offset;
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    @Override
    public Pageable next() {
        return pageable.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return pageable.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return pageable.first();
    }

    @Override
    public boolean hasPrevious() {
        return pageable.hasPrevious();
    }

}