package com.document.uid.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AbstractTimestampEntity {
	
	@Column(name = "created", nullable = false)
	private Date created = new Date();

	@Column(name = "updated", nullable = false)
	private Date updated = new Date();
	
	@PrePersist
	protected void onCreate() {
		this.updated = this.created = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated = new Date();
	}
}