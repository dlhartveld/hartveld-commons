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

package com.hartveld.commons.db.dynamic;

import com.hartveld.commons.db.EntityBase;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Object extends EntityBase {

	@ManyToOne(optional = false)
	private ObjectClass objectClass;

	@OneToMany(mappedBy = "object")
	private final Set<Property> properties = new HashSet<>();

	public Object(final ObjectClass objectClass) {
		this.objectClass = objectClass;
	}

	public ObjectClass getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(final ObjectClass objectClass) {
		this.objectClass = objectClass;
	}

	public Set<Property> getProperties() {
		return Collections.unmodifiableSet(properties);
	}

	public boolean addProperty(final Property property) {
		return properties.add(property);
	}

	public boolean removeProperty(final Property property) {
		return properties.remove(property);
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);

		builder.append("id", super.getId());
		builder.append("version", super.getVersion());

		builder.append("objectClass", getObjectClass());
		builder.append("properties", getProperties());

		return builder.toString();
	}

}
