package com.hartveld.commons.web.jetty.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("api/secured")
public class SecuredResource {

	@GET
	@Path("forbidden")
	public String forbidden() {
		return "This is secret information and should never be retrievable anonymously.";
	}

}
