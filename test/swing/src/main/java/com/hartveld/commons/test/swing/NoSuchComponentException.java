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

package com.hartveld.commons.test.swing;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Container;

@SuppressWarnings("serial")
public class NoSuchComponentException extends RuntimeException {

	public final Container container;
	public final Class<?> clazz;

	public NoSuchComponentException(final Container container, final Class<?> clazz) {
		this(container, null, clazz);
	}

	public NoSuchComponentException(final Container container, final String name, final Class<?> clazz) {
		super(message(container, name, clazz));

		checkNotNull(container, "container");
		checkNotNull(clazz, "clazz");

		this.container = container;
		this.clazz = clazz;
	}

	private static String message(final Container container, final String name, final Class<?> clazz) {
		checkNotNull(container, "container");
		checkNotNull(clazz, "clazz");

		if (name == null || name.isEmpty()) {
			return message2(container, clazz);
		} else {
			return message3(container, name, clazz);
		}
	}

	private static String message2(final Container container, final Class<?> clazz) {
		return "Container " + container.getName() + " does not contain component of type " + clazz.getName();
	}

	private static String message3(final Container container, final String name, final Class<?> clazz) {
		return "Container " + container.getName() + " does not contain component named " + name + " of type " + clazz.getName();
	}

}
