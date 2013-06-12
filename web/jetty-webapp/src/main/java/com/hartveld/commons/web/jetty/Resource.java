package com.hartveld.commons.web.jetty;

import java.util.Collection;

public interface Resource<DTO> {

	public abstract DTO retrieve(final long id);

	public abstract Collection<DTO> retrieveAll();

}
