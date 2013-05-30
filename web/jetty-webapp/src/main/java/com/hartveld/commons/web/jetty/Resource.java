package com.hartveld.commons.web.jetty;

public interface Resource<DTO> {

	DTO retrieve(final long id);

}
