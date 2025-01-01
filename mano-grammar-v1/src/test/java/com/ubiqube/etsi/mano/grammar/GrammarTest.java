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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.grammar.v1.Grammarv1Service;

class GrammarTest {
	private final Grammarv1Service grammarv1Service = new Grammarv1Service();

	/**
	 * Simple value can be cinfused with STRING <=> AATTRIBUTES.
	 */
	@Test
	void testSimpleValue() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.gt=aaa");
		assertEquals(1, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("id"), GrammarOperandType.GT, List.of("aaa"));
	}

	/**
	 * Value may be letter and digits.
	 */
	@Test
	void testComplexValue() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.eq=fce04624-6f92-42b1-bf50-437b682288a5");
		assertEquals(1, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("id"), GrammarOperandType.EQ, List.of("fce04624-6f92-42b1-bf50-437b682288a5"));
	}

	/**
	 * This is a multivalue, it will failed.
	 */
	@Test
	void testMultiValueIssue() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.eq=fce04624-6f92-42b1-bf50-437b682288a5,OOOOOOO");
		assertNode((BooleanExpression) nodes.get(0), List.of("id"), GrammarOperandType.EQ, List.of("fce04624-6f92-42b1-bf50-437b682288a5", "OOOOOOO"));
	}

	/**
	 * Multiple Operator separated by '&' and 2 simple values.
	 */
	@Test
	void testMultiOp() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.eq=string&vnfdVersion.gt=bad");
		assertEquals(2, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("id"), GrammarOperandType.EQ, List.of("string"));
		assertNode((BooleanExpression) nodes.get(1), List.of("vnfdVersion"), GrammarOperandType.GT, List.of("bad"));
	}

	/**
	 * Sub object attribute and simple values.
	 */
	@Test
	void testMultiAttr() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.my.bean.eq=string&vnfdVersion.gt=bad");
		assertEquals(2, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("id", "my", "bean"), GrammarOperandType.EQ, List.of("string"));
		assertNode((BooleanExpression) nodes.get(1), List.of("vnfdVersion"), GrammarOperandType.GT, List.of("bad"));
	}

	/**
	 *
	 */
	@Test
	void testMultiAttr2() {
		final GrammarNodeResult nodes = grammarv1Service.parse("id.my.bean.eq=stri556ng&my.long.vnfdVersion.gt=bad_ty");
		assertEquals(2, nodes.size());
		assertNode((BooleanExpression) nodes.get(0), List.of("id", "my", "bean"), GrammarOperandType.EQ, List.of("stri556ng"));
		assertNode((BooleanExpression) nodes.get(1), List.of("my", "long", "vnfdVersion"), GrammarOperandType.GT, List.of("bad_ty"));
	}

	private static void assertNode(final BooleanExpression node, final List<String> key, final GrammarOperandType op, final List<String> value) {
		final GrammarLabel l = (GrammarLabel) node.getLeft();
		assertKey(key, l);
		assertEquals(op, node.getOp());
		assertValue(value, (GrammarValue) node.getRight());
	}

	private static void assertKey(final List<String> keys, final GrammarLabel labels) {
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
			assertTrue(labels.isSingle());
			assertEquals(keys.get(0), labels.getAsString());
			return;
		}
		final List<String> labs = labels.getList();
		assertEquals(keys, labs);
	}
}
