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

import java.util.Properties;
import org.apache.openejb.OpenEjbContainer;

@SuppressWarnings("serial")
public class OpenEjbContainerProperties extends Properties {

	public OpenEjbContainerProperties(final String appName, final String dsName, final String dbUrl, final String jdbcDriverClassName, final String dbUsername, final String dbPassword) {
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
