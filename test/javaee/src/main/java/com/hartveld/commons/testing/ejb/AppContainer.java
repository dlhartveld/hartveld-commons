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

/**
 * An easily manageable embedded {@link EJBContainer}.
 */
public class AppContainer implements AutoCloseable {

	private final EJBContainer container;

	/**
	 * Create and start a new {@link EJBContainer} which can be stopped by
	 * calling the {@link #close()} method.
	 */
	public AppContainer() {
		container = EJBContainer.createEJBContainer();
	}

	/**
	 * Create and start a new {@link EJBContainer} which can be stopped by
	 * calling the {@link #close()} method.
	 * 
	 * @param properties The {@link Properties} to configure the container. Must
	 *            be non-<code>null</code>.
	 */
	public AppContainer(final Properties properties) {
		checkNotNull(properties, "properties");

		container = EJBContainer.createEJBContainer(properties);
	}

	/**
	 * Close the container, stopping all its services.
	 */
	@Override
	public void close() {
		container.close();
	}

}
