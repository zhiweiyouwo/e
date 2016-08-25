package com.loy.upm.sys.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.core.entity.LogicDeleteable;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Entity
@Table(name = "loy_role")
public class RoleEntity extends BaseEntity implements LogicDeleteable {

    private static final long serialVersionUID = 4826881461196601489L;
    @Column(length = 100)
    private String name;
    @Column(length = 255)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "loy_role_resource", joinColumns = {
            @JoinColumn(name = "role_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "resource_id") })
    //@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ResourceEntity> resources = new HashSet<ResourceEntity>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void markDeleted() {

    }

    public Set<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }

}