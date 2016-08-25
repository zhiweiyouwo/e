package com.loy.e.core.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class Criteria<T> implements Specification<T> {
    private List<Criterion> criterions = new ArrayList<Criterion>();

    public Predicate toPredicate(Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Criterion c : criterions) {
                predicates.add(c.toPredicate(root, query, builder));
            }
            // 将所有条件用 and 联合起来  
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    public void add(Criterion criterion) {
        if (criterion != null) {
            criterions.add(criterion);
        }
    }
}