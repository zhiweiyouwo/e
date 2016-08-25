package com.loy.e.core.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.loy.e.core.entity.Entity;
import com.loy.e.core.query.MapQueryParam;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@NoRepositoryBean
public interface GenericRepository<T extends Entity<ID>, ID extends Serializable>
        extends Repository<T, ID> {

    T save(T entity);

    T get(ID id);

    List<T> findAll();

    void delete(ID id);

    void delete(T entity);

    void delete(List<ID> ids);

    public List<T> find(String key, MapQueryParam param);

    public Page<T> findPage(MapQueryParam param, Pageable pageable);

    public Page<T> findPage(String key, MapQueryParam param, Pageable pageable);

    public Page<T> findPage(String key, String countKey, MapQueryParam param, Pageable pageable);

    public <R> List<R> find(String key, MapQueryParam param, Class<R> resultClass);

    public <R> Page<R> findPage(MapQueryParam param, Pageable pageable, Class<R> resultClass);

    public <R> Page<R> findPage(String key,
            MapQueryParam param,
            Pageable pageable,
            Class<R> resultClass);

    public <R> Page<R> findPage(String key,
            String countKey,
            MapQueryParam param,
            Pageable pageable,
            Class<R> resultClass);

}
