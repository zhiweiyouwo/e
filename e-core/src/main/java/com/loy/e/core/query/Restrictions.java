package com.loy.e.core.query;

import java.util.Collection;

import org.hibernate.criterion.MatchMode;
import org.springframework.util.StringUtils;

import com.loy.e.core.query.Criterion.Operator;
import com.loy.e.core.query.impl.LogicalExpression;
import com.loy.e.core.query.impl.SimpleExpression;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 条件构造器 
 * 用于创建条件表达式 
 */
public class Restrictions {

    /** 
     * 等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.EQ);
    }

    /** 
     * 不等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.NE);
    }

    /** 
     * 模糊匹配 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.LIKE);
    }

    /** 
     *  
     * @param fieldName 
     * @param value 
     * @param matchMode 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression like(String fieldName,
            String value,
            MatchMode matchMode,
            boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return null;
    }

    /** 
     * 大于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.GT);
    }

    /** 
     * 小于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.LT);
    }

    /** 
     * 大于等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.GTE);
    }

    /** 
     * 小于等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */
    public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
        if (StringUtils.isEmpty(value))
            return null;
        return new SimpleExpression(fieldName, value, Operator.LTE);
    }

    /** 
     * 并且 
     * @param criterions 
     * @return 
     */
    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions, Operator.AND);
    }

    /** 
     * 或者 
     * @param criterions 
     * @return 
     */
    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions, Operator.OR);
    }

    /** 
     * 包含于 
     * @param fieldName 
     * @param value 
     * @return 
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Operator.OR);
    }

    /***
     * Criteria<Event> c = new Criteria<Event>();  
        c.add(Restrictions.like("code", searchParam.getCode(), true));  
        c.add(Restrictions.eq("level", searchParam.getLevel(), false));  
        c.add(Restrictions.eq("mainStatus", searchParam.getMainStatus(), true));  
        c.add(Restrictions.eq("flowStatus", searchParam.getFlowStatus(), true));  
        c.add(Restrictions.eq("createUser.userName", searchParam.getCreateUser(), true));  
        c.add(Restrictions.lte("submitTime", searchParam.getStartSubmitTime(), true));  
        c.add(Restrictions.gte("submitTime", searchParam.getEndSubmitTime(), true));  
        c.add(Restrictions.eq("needFollow", searchParam.getIsfollow(), true));  
        c.add(Restrictions.ne("flowStatus", CaseConstants.CASE_STATUS_DRAFT, true));  
        c.add(Restrictions.in("solveTeam.code",teamCodes, true));  
    eventDao.findAll(c);  
    
    SetJoin<UserModel,DepModel> depJoin = root.join(root.getModel().getSet("setDep",DepModel.class) , JoinType.LEFT);
    
    Predicate p4 = cb.equal(depJoin.get("name").as(String.class), "ddd");
    //把Predicate应用到CriteriaQuery去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥 的
    query.where(cb.and(cb.and(p3,cb.or(p1,p2)),p4));
     */
}