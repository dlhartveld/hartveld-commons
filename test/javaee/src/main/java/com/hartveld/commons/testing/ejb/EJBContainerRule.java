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

package com.hartveld.commons.testing.ejb;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;

/**
 * A JUnit 4 {@link TestRule} implementation that manages an embedded
 * {@link EJBContainer}.
 */
public class EJBContainerRule extends ExternalResource {

	private final Properties properties;

	private AppContainer container;

	/**
	 * Create a new {@link EJBContainerRule} instance with an empty set of
	 * properties.
	 */
	public EJBContainerRule() {
		this(new Properties());
	}

	/**
	 * Create a new {@link EJBContainerRule} instance that will configure the
	 * {@link EJBContainer} with the given set of {@link Properties}.
	 * 
	 * @param properties The {@link Properties} to use when configuring the
	 *            {@link EJBContainer}. Must be non-<code>null</code>.
	 */
	public EJBContainerRule(final Properties properties) {
		checkNotNull(properties, "properties");

		this.properties = properties;
	}

	/**
	 * Create and start the container.
	 */
	@Override
	protected void before() throws Throwable {
		container = new AppContainer(properties);
	}

	/**
	 * Close the container.
	 */
	@Override
	protected void after() {
		container.close();
	}

}
