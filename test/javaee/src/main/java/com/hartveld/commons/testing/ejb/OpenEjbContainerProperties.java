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

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Properties;
import org.apache.openejb.OpenEjbContainer;

/**
 * Convenience subclass of {@link Properties} that preconfigures itself for use
 * with OpenEJB.
 */
@SuppressWarnings("serial")
public class OpenEjbContainerProperties extends Properties {

	/**
	 * Create a new instance configured with the given parameters.
	 * 
	 * @param appName The name of the application. Must be non-empty.
	 * @param dsName The name of the datasource that is to be supplied. Must be
	 *            non-empty.
	 * @param dbUrl The URL of the JDBC database connections to use. Must be
	 *            non-empty.
	 * @param jdbcDriverClassName The name of the JDBC Driver implementation
	 *            class to use. Must be non-empty.
	 * @param dbUsername The username to use when connecting to the database.
	 *            Must be non-empty.
	 * @param dbPassword The password to use when connecting to the database.
	 *            Must be non-empty.
	 */
	public OpenEjbContainerProperties(final String appName, final String dsName, final String dbUrl, final String jdbcDriverClassName, final String dbUsername, final String dbPassword) {
		checkArgument(isNotEmpty(appName), "appName must be non-null and non-empty");
		checkArgument(isNotEmpty(dsName), "dsName must be non-null and non-empty");
		checkArgument(isNotEmpty(jdbcDriverClassName), "jdbcDriverClassName must be non-null and non-empty");
		checkArgument(isNotEmpty(dbUrl), "dbUrl must be non-null and non-empty");
		checkArgument(isNotEmpty(dbUsername), "dbUsername must be non-null and non-empty");
		checkArgument(isNotEmpty(dbPassword), "dbPassword must be non-null and non-empty");

		setProperty(OpenEjbContainer.APP_NAME, appName);
		setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");

		setProperty("openejb.log.factory", "org.apache.openejb.util.Slf4jLogStreamFactory");

		setProperty(dsName, "new://Resource?type=DataSource");
		setProperty(dsName + ".JdbcDriver", jdbcDriverClassName);
		setProperty(dsName + ".JdbcUrl", dbUrl);
		setProperty(dsName + ".JtaManaged", "true");
		setProperty(dsName + ".UserName", dbUsername);
		setProperty(dsName + ".Password", dbPassword);
	}

}
