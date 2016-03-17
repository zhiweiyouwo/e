package com.loy.e.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;




/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@MappedSuperclass
public class AbstractEntity<ID extends Serializable>  implements Entity<ID> {

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 36)
	private ID id;
	
	public ID getId() {
		return id;
	}

	@Override
	public void setId(ID id) {
		 this.id = id;
	}   

	
	public boolean isNew(){
		if(this.id == null || "".equals(this.id)){
			this.setId(null);
			return true;
		}
		return false;
	}
}