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

package com.hartveld.commons.testing.schema;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOBase<T> implements DAO<T> {

	private static final Logger LOG = LoggerFactory.getLogger(DAOBase.class);

	private final EntityManager em;

	private final Class<T> entityClass;
	private final String entityName;

	protected DAOBase(final EntityManager em, final Class<T> clazz) {
		checkNotNull(em, "em");
		checkNotNull(clazz, "clazz");

		this.em = em;
		this.entityClass = clazz;
		this.entityName = clazz.getSimpleName();
	}

	@Override
	public final EntityManager getEntityManager() {
		return em;
	}

	@Override
	public final Class<T> getEntityClass() {
		return entityClass;
	}

	@Override
	public final String getEntityName() {
		return entityName;
	}

	@Override
	public final void persist(final T entity) {
		LOG.trace("persist: {}", entity);

		checkNotNull(entity, "entity");

		em.persist(entity);
	}

	@Override
	public final void persistAll(@SuppressWarnings("unchecked") final T... entities) {
		LOG.trace("persistAll: {}", (Object) entities);

		for (final T cellType : entities) {
			persist(cellType);
		}
	}

	@Override
	public final List<T> retrieveAll() {
		LOG.trace("retrieveAll:");

		final TypedQuery<T> query = em.createQuery("from " + entityName, entityClass);

		final List<T> results = query.getResultList();

		return results;
	}

	protected final TypedQuery<T> createQuery(final String query) {
		LOG.trace("createQuery: {}", query);

		checkArgument(isNotEmpty(query), "query must be non-empty");

		return em.createQuery(query, entityClass);
	}

}
