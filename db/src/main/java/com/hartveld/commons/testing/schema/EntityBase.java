package com.hartveld.commons.testing.schema;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Version
	private long version;

	public final long getId() {
		return id;
	}

	public final long getVersion() {
		return version;
	}

}
