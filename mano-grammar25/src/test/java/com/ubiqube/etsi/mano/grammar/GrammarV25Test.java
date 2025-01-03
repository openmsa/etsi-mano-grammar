/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.grammar.v25.Grammar25Service;

/**
 *
 * @author olivier
 *
 */
@SuppressWarnings("static-method")
class GrammarV25Test {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(GrammarV25Test.class);

	@Test
	void test() {
		final GrammarParser gp = new Grammar25Service();
		// (eq,weight,100);(neq,weight/aa,100);(in,weight/aa,100,55)
		final GrammarNodeResult nodes = gp.parse("(eq,weight,100);(neq,weight/aa,100);(in,weight/aa,100,55);(eq,version,'1.0.0')");
		assertEquals(4, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("weight"), GrammarOperandType.EQ, List.of("100"));
		assertNode((BooleanExpression) nodes.get(1), List.of("weight", "aa"), GrammarOperandType.NEQ, List.of("100"));
		assertNode((BooleanExpression) nodes.get(2), List.of("weight", "aa"), GrammarOperandType.IN, List.of("100", "55"));
		assertNode((BooleanExpression) nodes.get(3), List.of("version"), GrammarOperandType.EQ, List.of("1.0.0"));
	}

	@Test
	void test2() {
		final GrammarParser gp = new Grammar25Service();
		final GrammarNodeResult nodes = gp.parse("(eq,onboardingState,ONBOARDED)");
		assertEquals(1, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("onboardingState"), GrammarOperandType.EQ, List.of("ONBOARDED"));
	}

	@Test
	void testParseFail() {
		final GrammarParser gp = new Grammar25Service();
		assertThrows(GrammarException.class, () -> gp.parse("(bad,onboardingState,ONBOARDED)"));
	}

	private static void assertNode(final BooleanExpression node, final List<String> key, final GrammarOperandType op, final List<String> value) {
		final GrammarLabel l = (GrammarLabel) node.getLeft();
		assertKey(key, l);
		assertEquals(op, node.getOp());
		assertValue(value, (GrammarValue) node.getRight());
	}

	private static void assertKey(final List<String> keys, final GrammarLabel labels) {
		LOG.info("key size {}", keys.size());
		if (keys.size() == 1) {
			assertTrue(labels.isSingle());
			assertEquals(keys.get(0), labels.getAsString());
			return;
		}
		final List<String> labs = labels.getList();
		assertEquals(keys, labs);
	}

	private static void assertValue(final List<String> keys, final GrammarValue labels) {
		if (keys.size() == 1) {
			assertTrue(labels.isSingle(), "Value should be single.");
			assertEquals(keys.get(0), labels.getAsString());
			return;
		}
		final List<String> labs = labels.getList();
		assertEquals(keys, labs);
	}
}
