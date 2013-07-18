package com.hartveld.commons.web.jetty;

import java.util.List;

public interface Resource<DTO> {

	public abstract DTO create(DTO dto);

	public abstract void remove(long id);

	public abstract DTO update(long id, DTO dto);

	public abstract DTO retrieve(long id);

	public abstract List<DTO> retrieveAll();

}
