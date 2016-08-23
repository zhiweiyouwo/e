package com.loy.e.core.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.loy.e.core.conf.Settings;
import com.loy.e.core.entity.Entity;
import com.loy.e.core.ql.DynamicQlStatementBuilder;
import com.loy.e.core.repository.GenericRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class DefaultRepositoryFactoryBean<R extends JpaRepository<M, ID>, M extends Entity<ID>, ID extends Serializable>
		extends JpaRepositoryFactoryBean<R, M, ID> {
	@Autowired
	DynamicQlStatementBuilder dynamicQlStatementBuilder;
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private Settings settings;
	public DefaultRepositoryFactoryBean() {
	}

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new DefaultRepositoryFactory<M, ID >(entityManager,dynamicQlStatementBuilder, jdbcTemplate,settings);
	}
}

class DefaultRepositoryFactory< M extends Entity<ID>, ID extends Serializable> extends JpaRepositoryFactory {
	private EntityManager entityManager;
	private DynamicQlStatementBuilder dynamicQlStatementBuilder;
	private NamedParameterJdbcTemplate jdbcTemplate;
	private Settings settings;
	public DefaultRepositoryFactory(EntityManager entityManager,
			DynamicQlStatementBuilder dynamicQlStatementBuilder,
			NamedParameterJdbcTemplate jdbcTemplate ,
			Settings settings) {
		super(entityManager);
		this.entityManager = entityManager;
		this.dynamicQlStatementBuilder = dynamicQlStatementBuilder;
		this.jdbcTemplate = jdbcTemplate;
		this.settings = settings;
	}

	@SuppressWarnings("unchecked")
	protected Object getTargetRepository(RepositoryInformation metadata) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		if (isBaseRepository(repositoryInterface)) {
			JpaEntityInformation<M, ID> entityInformation = getEntityInformation((Class<M>) metadata.getDomainType());
			GenericRepository<M, ID> repository = new GenericRepositoryImpl<M, ID>(entityInformation,
					entityManager,dynamicQlStatementBuilder,jdbcTemplate,settings);
			return repository;
		}
		return super.getTargetRepository(metadata);
	}

	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isBaseRepository(metadata.getRepositoryInterface())) {
			return GenericRepository.class;
		}
		return super.getRepositoryBaseClass(metadata);
	}

	private boolean isBaseRepository(Class<?> repositoryInterface) {
		return GenericRepository.class.isAssignableFrom(repositoryInterface);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
		return super.getQueryLookupStrategy(key);
	}

}