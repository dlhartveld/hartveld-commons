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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiceJerseyApiContextListener extends GuiceServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(GuiceJerseyApiContextListener.class);

	private final String apiPackage;
	private final String apiContext;

	public GuiceJerseyApiContextListener(final String apiPackage, final String apiContext) {
		checkArgument(isNotEmpty(apiPackage), "apiPackage must be non-empty");
		checkArgument(isNotEmpty(apiContext), "apiContext must be non-empty");

		this.apiPackage = apiPackage;
		this.apiContext = apiContext;
	}

	@Override
	protected Injector getInjector() {
		LOG.trace("Creating injector ...");

		return Guice.createInjector(
				new GuiceJerseyApiModule(apiPackage, apiContext)
		);
	}

}
