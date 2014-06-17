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

package com.hartveld.commons.db;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAOBase<T extends EntityBase> implements DAO<T> {

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
	public final void flush() {
		em.flush();
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
	public final void persist(final EntityBase entity) {
		LOG.trace("persist: {}", entity);

		checkNotNull(entity, "entity");

		em.persist(entity);
	}

	@Override
	public final void persistAll(final EntityBase... entities) {
		LOG.trace("persistAll: {}", (Object) entities);

		for (final EntityBase entity : entities) {
			persist(entity);
		}
	}

	@Override
	public final void persistAll(final Collection<? extends EntityBase> entities) {
		LOG.trace("persistAll: {}", entities);

		for (final EntityBase entity : entities) {
			persist(entity);
		}
	}

	@Override
	public final void remove(final EntityBase entity) {
		LOG.trace("remove: {}", entity);

		checkNotNull(entity, "entity");

		em.remove(entity);
	}

	@Override
	public void removeById(final long id) {
		LOG.trace("removeById: {}", id);

		final T entity = em.find(entityClass, id);

		if (entity != null) {
			em.remove(entity);
		} else {
			throw new EntityNotFoundException("Entity of type '" + entityName + "' with id '" + id + "' does not exist");
		}
	}

	@Override
	public List<T> retrieveAll() {
		LOG.trace("retrieveAll:");

		final TypedQuery<T> query = em.createQuery("from " + entityName, entityClass);

		return query.getResultList();
	}

	@Override
	public T retrieveById(final long id) {
		LOG.trace("retrieveById: {}", id);

		final TypedQuery<T> query = em.createQuery("from " + entityName + " where id = :id", entityClass);

		query.setParameter("id", id);

		return query.getSingleResult();
	}

	protected final TypedQuery<T> createQuery(final String query) {
		LOG.trace("createQuery: {}", query);

		checkArgument(isNotEmpty(query), "query must be non-empty");

		return em.createQuery(query, entityClass);
	}

}
