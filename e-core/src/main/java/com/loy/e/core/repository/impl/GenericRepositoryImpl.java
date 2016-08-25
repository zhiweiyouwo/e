package com.loy.e.core.repository.impl;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.loy.e.core.conf.Settings;
import com.loy.e.core.data.LoyPageRequest;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.core.entity.Entity;
import com.loy.e.core.exception.LoyException;
import com.loy.e.core.ql.DynamicQlStatementBuilder;
import com.loy.e.core.ql.StatementTemplate;
import com.loy.e.core.ql.StatementTemplate.TYPE;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.query.QueryParamHelper;
import com.loy.e.core.repository.GenericRepository;
import com.loy.e.core.util.JsonUtil;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Transactional(readOnly = true)
@SuppressWarnings("rawtypes")
public class GenericRepositoryImpl<T extends Entity<ID>, ID extends Serializable>
        implements GenericRepository<T, ID> {
    protected final Log logger = LogFactory.getLog(GenericRepositoryImpl.class);
    private EntityManager em = null;
    private JpaEntityInformation<T, ?> entityInformation = null;
    private Class<T> entityClass = null;
    private CrudMethodMetadata crudMethodMetadata = null;
    private Settings settings;

    DynamicQlStatementBuilder dynamicQlStatementBuilder;
    NamedParameterJdbcTemplate jdbcTemplate;

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
            EntityManager entityManager,
            DynamicQlStatementBuilder dynamicQlStatementBuilder,
            NamedParameterJdbcTemplate jdbcTemplate,
            Settings settings) {
        //super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.em = entityManager;
        this.dynamicQlStatementBuilder = dynamicQlStatementBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.settings = settings;

    }

    //	public GenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
    //		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);
    //	}

    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        this.crudMethodMetadata = crudMethodMetadata;
    }

    @Override
    @Transactional
    public T save(T entity) {
        if (entity.isNew() || entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            if (entity instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) entity;
                baseEntity.setModifiedTime(new Date());
            }
            return em.merge(entity);
        }
    }

    @Override
    public T get(ID id) {
        Class<T> domainType = entityClass;
        if (crudMethodMetadata == null) {
            return em.find(domainType, id);
        }
        LockModeType type = crudMethodMetadata.getLockModeType();
        Map<String, Object> hints = crudMethodMetadata.getQueryHints();
        return type == null ? em.find(domainType, id, hints) : em.find(domainType, id, type, hints);
    }

    @Override
    public List<T> findAll() {
        return getQuery(null, (Sort) null).getResultList();
    }

    @Transactional
    public void delete(ID id) {
        T entity = get(id);
        if (entity != null) {
            delete(entity);
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public void delete(List<ID> ids) {
        for (ID id : ids) {
            delete(id);
        }
    }

    @Override
    public List<T> find(String key, MapQueryParam param) {
        return this.find(key, param, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> find(String key, MapQueryParam param, Class<R> resultClass) {
        StatementTemplate statementTemplate = dynamicQlStatementBuilder.get(key);
        Map<String, Object> values = param.getValues();
        String ql = this.processTemplate(statementTemplate, values);
        logger.debug(ql);
        if (param != null) {
            logger.debug(param.getValues());
        }
        Query query = null;
        List list = null;
        if (statementTemplate.getType() == TYPE.HQL) {
            if (resultClass == null) {
                query = em.createQuery(ql);
            } else {
                query = em.createQuery(ql, resultClass);
            }
            setParameters(query, values);
            list = query.getResultList();

        } else {
            if (resultClass == null) {
                list = jdbcTemplate.queryForList(ql, values);
            } else {
                list = jdbcTemplate.queryForList(ql, values, resultClass);
            }
        }
        if (settings.getDebugPageResult()) {
            logger.debug("查询结果");
            String data = JsonUtil.json(list);
            logger.debug(JsonUtil.formatJson(data));
        }
        return list;
    }

    @Override
    public Page<T> findPage(MapQueryParam param, Pageable pageable) {
        return this.findPage(param, pageable, null);
    }

    @Override
    public <R> Page<R> findPage(MapQueryParam param, Pageable pageable, Class<R> resultClass) {
        Map<String, Object> values = param.getValues();
        StringBuilder ql = QueryParamHelper.build(param);
        String qlStr = ql.toString();
        if (!StringUtils.containsIgnoreCase(qlStr, "from")) {
            qlStr = " from  " + entityClass.getSimpleName() + " x " + qlStr;
        }
        String countQl = this.prepareCountHql(qlStr);
        return this.findPage(qlStr, countQl, values, pageable, StatementTemplate.TYPE.HQL,
                resultClass);
    }

    @Override
    public Page<T> findPage(String key, MapQueryParam param, Pageable pageable) {
        return this.findPage(key, param, pageable, null);
    }

    @Override
    public <R> Page<R> findPage(String key,
            MapQueryParam param,
            Pageable pageable,
            Class<R> resultClass) {
        Map<String, Object> values = null;
        if (param != null) {
            values = param.getValues();
        }
        StatementTemplate statementTemplate = dynamicQlStatementBuilder.get(key);
        String ql = this.processTemplate(statementTemplate, values);
        String countQl = this.prepareCountHql(ql);
        return this.findPage(ql, countQl, values, pageable, statementTemplate.getType(),
                resultClass);
    }

    @Override
    public Page<T> findPage(String key, String countKey, MapQueryParam param, Pageable pageable) {
        return this.findPage(key, countKey, param, pageable, null);
    }

    @Override
    public <R> Page<R> findPage(String key,
            String countKey,
            MapQueryParam param,
            Pageable pageable,
            Class<R> resultClass) {
        Map<String, Object> values = null;
        if (param != null) {
            values = param.getValues();
        }
        StatementTemplate statementTemplate = dynamicQlStatementBuilder.get(countKey);
        String countQl = this.processTemplate(statementTemplate, values);
        statementTemplate = dynamicQlStatementBuilder.get(key);
        String ql = this.processTemplate(statementTemplate, values);
        return this.findPage(ql, countQl, values, pageable, statementTemplate.getType(),
                resultClass);
    }

    @SuppressWarnings("unchecked")
    <R> Page<R> findPage(String hql,
            String countHql,
            Map<String, Object> param,
            Pageable pageable,
            TYPE type,
            Class<R> resultClass) {
        pageable = new LoyPageRequest(pageable);
        String countQl = null;
        if (countHql == null) {
            countQl = this.prepareCountHql(hql);
        } else {
            countQl = countHql;
        }
        logger.debug("ql count : " + deleteCRLFOnce(countQl));
        logger.debug("hql :" + deleteCRLFOnce(hql));
        if (param != null) {
            logger.debug("参数值");
            logger.debug("values :" + param);
        }
        Query query = null;
        Page<R> page = null;
        if (type == TYPE.HQL) {
            query = em.createQuery(countQl);
            if (param != null) {
                this.setParameters(query, param);
            }
            Long total = 0L;
            total = (Long) query.getSingleResult();
            query = em.createQuery(hql);
            setParameters(query, param);
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            List<R> list = query.getResultList();
            page = new PageImpl<R>(list, pageable, total);
        } else {
            List list = null;
            Long total = 0L;
            total = jdbcTemplate.queryForObject(countQl, param, Long.class);
            hql = this.getPageSQL(hql, pageable.getOffset(), pageable.getPageSize());
            if (resultClass == null) {
                list = jdbcTemplate.queryForList(hql, param);
            } else {
                list = jdbcTemplate.queryForList(hql, param, resultClass);
            }
            page = new PageImpl<R>(list, pageable, total);
        }
        if (settings.getDebugPageResult()) {
            logger.debug("分页结果");
            String data = JsonUtil.json(page);
            logger.debug(JsonUtil.formatJson(data));
        }

        return page;
    }

    protected String processTemplate(StatementTemplate statementTemplate,
            Map<String, ?> parameters) {
        StringWriter stringWriter = new StringWriter();
        if (statementTemplate == null) {
            logger.error("没能找到模板");
        }
        try {
            statementTemplate.getTemplate().process(parameters, stringWriter);
        } catch (Exception e) {
            logger.error(e);
            throw new LoyException("system_error");
        }
        String qlStr = stringWriter.toString();
        logger.debug("模板中的QL");
        logger.debug(deleteCRLFOnce(qlStr));
        return qlStr;
    }

    protected String prepareCountHql(String hql) {
        String countHql = QueryUtils.createCountQueryFor(hql, "*");
        return countHql;
    }

    protected void setParameters(Query query, Map<String, Object> params) {
        Set<Parameter<?>> parameters = query.getParameters();
        Set<String> paramNames = new HashSet<String>();
        if (parameters != null) {
            for (Parameter<?> p : parameters) {
                paramNames.add(p.getName());
            }
        }
        if (params != null) {
            Set<Map.Entry<String, Object>> p = params.entrySet();
            for (Map.Entry<String, Object> entry : p) {
                String paramName = entry.getKey();
                if (paramNames.contains(paramName)) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = applySpecificationToCriteria(spec, query);
        query.select(root);
        if (sort != null) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    private <S> Root<T> applySpecificationToCriteria(Specification<T> spec,
            CriteriaQuery<S> query) {
        Assert.notNull(query);
        Root<T> root = query.from(entityClass);
        if (spec == null) {
            return root;
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {
        if (crudMethodMetadata == null) {
            return query;
        }
        LockModeType type = crudMethodMetadata.getLockModeType();
        TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

        for (Entry<String, Object> hint : crudMethodMetadata.getQueryHints().entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
        return toReturn;
    }

    private String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
        String result = "";
        if (null != startIndex && null != pageSize) {
            result = queryString + " limit " + startIndex + "," + pageSize;
        } else if (null != startIndex && null == pageSize) {
            result = queryString + " limit " + startIndex;
        } else {
            result = queryString;
        }
        logger.debug("分页QL");
        logger.debug(result);
        return result;
    }

    private String deleteCRLFOnce(String input) {
        if (StringUtils.isNotEmpty(input)) {
            return input.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)",
                    "");
        } else {
            return "";
        }
    }
}