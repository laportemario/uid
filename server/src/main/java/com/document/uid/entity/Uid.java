package com.document.uid.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "uid") 
public class Uid extends AbstractTimestampEntity implements Serializable {

	private static final long serialVersionUID = 894579580669467657L;

	@Id
	@Column(name = "id", nullable = false, length = 9)
	private String id;

	@Generated(GenerationTime.INSERT)
	@Column(name = "serial", columnDefinition = "serial", updatable = false, nullable = false)
	private Integer serial;

	public Uid(String id) {
		super();
		this.id = id;
	}

	public Uid() {
		super();
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	@Column(name = "is_used", nullable = false)
	private boolean isUsed;

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isUsed ? 1231 : 1237);
		result = prime * result + ((serial == null) ? 0 : serial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Uid other = (Uid) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isUsed != other.isUsed)
			return false;
		if (serial == null) {
			if (other.serial != null)
				return false;
		} else if (!serial.equals(other.serial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uid [id=" + id + ", serial=" + serial + ", isUsed=" + isUsed + "]";
	}

}
