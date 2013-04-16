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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.Test;

public class OtherTest extends AbstractSwingFrameTest {

	private static final String LABEL1 = "label1";
	private static final String LABEL2 = "label2";

	private JLabel label1;
	private JLabel label2;

	@Override
	protected JFrame createTestableFrame() {
		final JFrame frame = new JFrame("Frame");
		frame.setName("frame");

		label1 = new JLabel("Label 1");
		label1.setName(LABEL1);

		label2 = new JLabel("Label 2");
		label2.setName(LABEL2);

		frame.add(label1);
		frame.add(label2);

		return frame;
	}

	@Override
	protected void lookupComponents() { }

	@Test
	public void testLookupOfUnnamedComponent() {
		final JLabel lookedUp = lookup(JLabel.class);

		assertThat(lookedUp, is(sameInstance(label1)));
	}

	@Test
	public void testLookupOfFirstNamedComponent() {
		final JLabel lookedUp = lookup(LABEL1, JLabel.class);

		assertThat(lookedUp, is(sameInstance(label1)));
	}

	@Test
	public void testLookupOfSecondNamedComponent() {
		final JLabel lookedUp = lookup(LABEL2, JLabel.class);

		assertThat(lookedUp, is(sameInstance(label2)));
	}

}
