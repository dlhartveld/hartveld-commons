package com.hartveld.commons.web.jetty;

import java.util.List;

public interface Resource<DTO> {

	public abstract DTO retrieve(final long id);

	public abstract List<DTO> retrieveAll();

}
