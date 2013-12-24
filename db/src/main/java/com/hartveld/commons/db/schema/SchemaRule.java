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

package com.hartveld.commons.db.schema;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hartveld.commons.db.schema.LiquibaseSchemaUpdater;

public class SchemaRule extends ExternalResource {

	private static final Logger LOG = LoggerFactory.getLogger(SchemaRule.class);

	private final String url;
	private final String username;
	private final String password;
	private final String context;

	public SchemaRule(final String url, final String username, final String password, final String context) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.context = context;
	}

	@Override
	protected void before() throws Throwable {
		LOG.trace("Setting up database ...");
		final LiquibaseSchemaUpdater updater = new LiquibaseSchemaUpdater(url, username, password, context);
		updater.dropAndCreate();
	}

	@Override
	protected void after() {
		LOG.trace("Cleaning up database ...");
		final LiquibaseSchemaUpdater updater = new LiquibaseSchemaUpdater(url, username, password, context);
		updater.drop();
	}

}
