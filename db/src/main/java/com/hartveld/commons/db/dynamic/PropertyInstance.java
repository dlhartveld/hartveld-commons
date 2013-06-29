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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class PropertyInstance extends EntityBase {

	@ManyToOne(optional = false)
	private ObjectInstance objectInstance;

	@ManyToOne(optional = false)
	private ObjectClassProperty property;

	@Column(nullable = false)
	private String value;

	public PropertyInstance(final ObjectInstance objectInstance, final ObjectClassProperty property, final String value) {
		this.objectInstance = objectInstance;
		this.property = property;
		this.value = value;
	}

	public ObjectInstance getObject() {
		return objectInstance;
	}

	public void setObject(final ObjectInstance objectInstance) {
		this.objectInstance = objectInstance;
	}

	public ObjectClassProperty getProperty() {
		return property;
	}

	public void setProperty(final ObjectClassProperty property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);

		builder.append("id", getId());
		builder.append("version", getVersion());

		builder.append("objectInstance.id", getObject().getId());
		builder.append("property.id", getProperty());
		builder.append("value", getValue());

		return builder.toString();
	}

}
