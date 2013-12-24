package com.hartveld.commons.web.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.hartveld.commons.web.jetty.Resource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceClientBase<DTO> implements Resource<DTO> {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceClientBase.class);

	private final Client client;
	private final String url;
	private final String path;
	private final Class<DTO> dtoType;

	protected ResourceClientBase(final Client client, final String url, final String path, final Class<DTO> dtoType) {
		checkNotNull(client, "client");
		checkArgument(isNotEmpty(url), "url must be non-empty");
		checkArgument(isNotEmpty(path), "path must be non-empty");
		checkNotNull(dtoType, "dtoType");

		this.client = client;
		this.url = url;
		this.path = path;
		this.dtoType = dtoType;
	}

	@Override
	public final DTO retrieve(final long id) {
		return retrieveFromApiPath(path + '/' + id, dtoType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final List<DTO> retrieveAll() {
		return api().path(path + "/list").get(List.class);
	}

	protected final <T> T retrieveFromApiPath(final String path, final Class<T> type) {
		LOG.trace("retrieveFromApiPath: {}: {}", path, type.getName());

		return api().path(path)
				.accept(MediaType.APPLICATION_JSON)
				.get(type);
	}

	protected final DTO postToApiPath(final String path, final Class<DTO> type) {
		LOG.trace("postToApiPath: {}: {}", path, type.getName());

		return api().path(path)
				.accept(MediaType.APPLICATION_JSON)
				.post(type);
	}

	protected final DTO postToApiPath(final String path, final Class<DTO> type, final Object requestEntity) {
		LOG.trace("postToApiPath: {}: {}: {}", path, type.getName(), requestEntity);

		return api().path(path)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(type, requestEntity);
	}

	protected final WebResource api() {
		return root().path("api");
	}

	protected final WebResource root() {
		return client.resource(url);
	}

}
