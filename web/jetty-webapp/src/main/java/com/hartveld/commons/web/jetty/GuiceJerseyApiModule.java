/*
 * Copyright (c) 2013 David Hartveld
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hartveld.commons.web.jetty;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Scopes;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiceJerseyApiModule extends JerseyServletModule {

	private static final Logger LOG = LoggerFactory.getLogger(GuiceJerseyApiModule.class);

	public static final String JERSEY_SCAN_PACKAGE = "com.sun.jersey.config.property.packages";

	private final String packageName;
	private final String context;
	private final String persistenceUnit;

	public GuiceJerseyApiModule(final String packageName, final String context, final String persistenceUnit) {
		checkArgument(isNotEmpty(packageName), "packageName must be non-empty");
		checkArgument(isNotEmpty(context), "context must be non-empty");

		checkArgument(!context.startsWith("/"), "context must not start with '/'");
		checkArgument(!context.endsWith("/"), "context must not end with '/'");

		this.packageName = packageName;
		this.context = context;
		this.persistenceUnit = persistenceUnit;
	}

	@Override
	protected void configureServlets() {
		LOG.trace("Configuring servlets ...");

		if (persistenceUnit != null) {
			installGuicePersistModule();
		} else {
			LOG.trace("No persistence unit configured, so ersistence module will not be installed.");
		}

		enableJerseyGuiceSupport();
	}

	private void installGuicePersistModule() {
		install(new JpaPersistModule(persistenceUnit));
		filter("/*").through(PersistFilter.class);
	}

	private void enableJerseyGuiceSupport() {
		enableJacksonJsonSupport();

		final Map<String, String> params = new HashMap<>();
		params.put(JERSEY_SCAN_PACKAGE, packageName);

		filter('/' + context + "/*").through(GuiceContainer.class, params);
	}

	private void enableJacksonJsonSupport() {
		bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
	}

}
