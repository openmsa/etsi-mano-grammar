/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
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
package com.ubiqube.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import com.mano.etsi.grammar.v1.EtsiFilter;
import com.mano.etsi.grammar.v1.EtsiLexer;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.v1.TreeBuilder;

@SuppressWarnings("static-method")
class FilterTest {

	@Test
	void treeTest() {
		final CodePointCharStream input = CharStreams.fromString("weight.eq=100");
		final EtsiLexer lexer = new EtsiLexer(input);

		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final EtsiFilter parser = new EtsiFilter(tokens);
		final TreeBuilder treeBuilder = new TreeBuilder();
		parser.addParseListener(treeBuilder);
		parser.filterExpr();
		final List<GrammarNode> listNode = treeBuilder.getListNode();
		assertNotNull(listNode);
		assertEquals(1, listNode.size());
	}

	@Test
	void testMultiFilter() throws Exception {
		final CodePointCharStream input = CharStreams.fromString("weight.eq=100&color.neq=red");
		final EtsiLexer lexer = new EtsiLexer(input);

		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final EtsiFilter parser = new EtsiFilter(tokens);
		final TreeBuilder treeBuilder = new TreeBuilder();
		parser.addParseListener(treeBuilder);
		parser.filterExpr();
		final List<GrammarNode> listNode = treeBuilder.getListNode();
		assertNotNull(listNode);
		assertEquals(2, listNode.size());
	}

	@Test
	void testMultiDot() throws Exception {
		final CodePointCharStream input = CharStreams.fromString("color.of.my.bean.neq=red");
		final EtsiLexer lexer = new EtsiLexer(input);

		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final EtsiFilter parser = new EtsiFilter(tokens);
		final TreeBuilder treeBuilder = new TreeBuilder();
		parser.addParseListener(treeBuilder);
		parser.filterExpr();
		final List<GrammarNode> listNode = treeBuilder.getListNode();
		assertNotNull(listNode);
		assertEquals(1, listNode.size());
	}
}
