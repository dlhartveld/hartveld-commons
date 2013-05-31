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

package com.hartveld.commons.web.jetty.test;

import com.hartveld.commons.web.jetty.JettyGuiceJerseyServer;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServerRule extends ExternalResource {

	private static final Logger LOG = LoggerFactory.getLogger(JettyServerRule.class);

	private static final String LOCALHOST = "localhost";

	private final JettyGuiceJerseyServer server;

	public JettyServerRule(final int port, final String apiContext, final String apiPackage, final String staticResourcesClassPathBase, final String persistenceUnit) {
		server = new JettyGuiceJerseyServer(port, apiContext, apiPackage, staticResourcesClassPathBase, persistenceUnit);
	}

	public String getHost() {
		return LOCALHOST;
	}

	public int getPort() {
		return server.getPort();
	}

	public String getServerUri() {
		return "http://" + getHost() + ':' + getPort() + '/';
	}

	@Override
	protected void before() throws Throwable {
		LOG.trace("Starting server ...");
		server.start();
	}

	@Override
	protected void after() {
		LOG.trace("Stopping server ...");
		try {
			server.close();
		} catch (final Exception ex) {
			LOG.warn("Failed to close server: {}", ex.getMessage(), ex);
		}
	}

}
