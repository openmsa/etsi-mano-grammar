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
package com.ubiqube.etsi.mano.grammar.antlr;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.springframework.lang.Nullable;

import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;

public abstract class AbstractAntlrGrammar<T extends ParseTreeListener> implements GrammarParser {

	@Override
	public final GrammarNodeResult parse(final @Nullable String query) {
		List<GrammarNode> nodes = new ArrayList<>();
		final T treeBuilder = createTreeBuilder();
		if ((null != query) && !query.isEmpty()) {
			final Lexer el = createLexer(query);
			final CommonTokenStream tokens = new CommonTokenStream(el);
			createParser(tokens, treeBuilder);
			nodes = getNodes(treeBuilder);
			checkNodes(nodes);
		}
		return new GrammarNodeResult(nodes);
	}

	protected abstract Parser createParser(CommonTokenStream tokens, ParseTreeListener treeBuilder);

	protected abstract T createTreeBuilder();

	protected abstract List<GrammarNode> getNodes(T treeBuilder);

	protected abstract Lexer createLexer(String query);

	private static void checkNodes(final List<GrammarNode> nodes) {
		final List<BooleanExpression> res = nodes.stream()
				.filter(BooleanExpression.class::isInstance)
				.map(BooleanExpression.class::cast)
				.filter(x -> x.getOp() == null)
				.toList();

		if (!res.isEmpty()) {
			throw new GrammarException("Bad filter: " + res);
		}
	}

}
