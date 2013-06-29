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

package com.hartveld.commons.db.dynamic.test;

import com.hartveld.commons.db.dynamic.ObjectInstance;
import com.hartveld.commons.db.dynamic.ObjectClass;
import com.hartveld.commons.db.dynamic.ObjectClassDAO;
import com.hartveld.commons.db.dynamic.ObjectClassDAOImpl;
import com.hartveld.commons.db.dynamic.ObjectClassProperty;
import com.hartveld.commons.db.dynamic.ObjectClassPropertyDAO;
import com.hartveld.commons.db.dynamic.ObjectClassPropertyDAOImpl;
import com.hartveld.commons.db.dynamic.ObjectInstanceDAO;
import com.hartveld.commons.db.dynamic.ObjectInstanceDAOImpl;
import com.hartveld.commons.db.dynamic.PropertyInstance;
import com.hartveld.commons.db.dynamic.PropertyInstanceDAO;
import com.hartveld.commons.db.dynamic.PropertyInstanceDAOImpl;
import com.hartveld.commons.db.schema.SchemaRule;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DynamicDBIntegrationTest {

	@Rule
	public final SchemaRule schema = new SchemaRule("jdbc:postgresql://localhost/dynamicdb-test", "dynamicdb-test", "TestIt!", null);

	private EntityManagerFactory emf;
	private EntityManager em;

	private EntityTransaction tx;

	@Before
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("Dynamic-Test");
		em = emf.createEntityManager();

		tx = em.getTransaction();
		tx.begin();
	}

	@After
	public void tearDown() {
		optionallyRollBackTransaction();

		closeEntityManager();
		closeEntityManagerFactory();
	}

	@Test
	public void test() {
		final ObjectClassDAO classDAO = new ObjectClassDAOImpl(em);
		final ObjectClassPropertyDAO ocpDAO = new ObjectClassPropertyDAOImpl(em);

		final ObjectInstanceDAO objectInstanceDAO = new ObjectInstanceDAOImpl(em);
		final PropertyInstanceDAO propertyInstanceDAO = new PropertyInstanceDAOImpl(em);

		final ObjectClass c = new ObjectClass("C");
		classDAO.persist(c);

		final ObjectClassProperty ocp = new ObjectClassProperty(c, "p");
		ocpDAO.persist(ocp);

		final ObjectInstance o = new ObjectInstance(c);
		objectInstanceDAO.persist(o);

		final PropertyInstance p = new PropertyInstance(o, ocp, "Hello, world!");
		propertyInstanceDAO.persist(p);

		// IMPORTANT: make sure that the database accepts all changes.
		tx.commit();
	}

	private void optionallyRollBackTransaction() {
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
	}

	private void closeEntityManager() {
		if (em != null) {
			em.close();
		}
	}

	private void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
		}
	}

}
