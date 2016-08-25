package com.loy.e.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface Criterion {
    public enum Operator {
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR
    }

    public Predicate toPredicate(Root<?> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder);
}