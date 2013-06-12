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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiquibaseSchemaUpdater {

	private static final Logger LOG = LoggerFactory.getLogger(LiquibaseSchemaUpdater.class);

	private final String url;
	private final String username;
	private final String password;

	private final String context;

	public LiquibaseSchemaUpdater(final String url, final String username, final String password, final String context) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.context = context;
	}

	public void dropAndCreate() {
		drop();
		create();
	}

	public void create() {
		try (Connection connection = createConnection()) {
			doLiquibasing(connection);

			connection.commit();
		} catch (LiquibaseException | SQLException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void drop() {
		try (Connection conn = createConnection()) {
			dropAllContents(conn);
		} catch (final SQLException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void doLiquibasing(final Connection connection) throws LiquibaseException, DatabaseException {
		LOG.trace("Processing all liquibase changesets...");

		// Should be cleaned up.
		final JdbcConnection liquibaseConnection = new JdbcConnection(connection);

		final Liquibase liquibase = new Liquibase("liquibase.xml", new ClassLoaderResourceAccessor(), liquibaseConnection);
		liquibase.update(context);

		LOG.debug("Finished processing all liquibase changesets.");
	}

	private void dropAllContents(final Connection connection) throws SQLException {
		LOG.trace("Dropping database contents: url: {} - owner: {}", url, username);

		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP OWNED BY \"" + username + "\"");

			connection.commit();
		} catch (final SQLException e) {
			LOG.error("Failed to drop all contents owned by: {}", username);

			connection.rollback();

			throw e;
		}

		LOG.trace("Database contents dropped.");
	}

	private Connection createConnection() throws SQLException {
		LOG.trace("Creating connection...");

		final Connection connection = DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);

		return connection;
	}

}
