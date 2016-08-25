package com.loy.upm.sys.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.sys.domain.entity.MenuI18nEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@CacheConfig(cacheNames = "menuI18ns")
public interface MenuI18nRepository extends GenericRepository<MenuI18nEntity, String> {
    @Cacheable
    public MenuI18nEntity findByKeyAndLang(String key, String lang);

}
