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
package com.ubiqube.etsi.mano.grammar.v1;

import java.util.List;
import java.util.Objects;

import com.mano.etsi.grammar.v1.EtsiFilter.AttrNameContext;
import com.mano.etsi.grammar.v1.EtsiFilter.FilterExprContext;
import com.mano.etsi.grammar.v1.EtsiFilter.OpContext;
import com.mano.etsi.grammar.v1.EtsiFilter.SimpleFilterExprContext;
import com.mano.etsi.grammar.v1.EtsiFilter.ValueContext;
import com.mano.etsi.grammar.v1.EtsiFilterBaseListener;
import com.ubiqube.etsi.mano.grammar.GrammarContext;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class TreeBuilder extends EtsiFilterBaseListener {
	private final GrammarContext context = new GrammarContext();

	@Override
	public void exitOp(final @Nullable OpContext ctx) {
		Objects.requireNonNull(ctx);
		context.setOp(GrammarOperandType.valueOf(ctx.getText().toUpperCase()));
		super.exitOp(ctx);
	}

	@Override
	public void exitValue(final @Nullable ValueContext ctx) {
		Objects.requireNonNull(ctx);
		context.addValue(ctx.getText());
		super.exitValue(ctx);
	}

	@Override
	public void exitSimpleFilterExpr(final @Nullable SimpleFilterExprContext ctx) {
		context.pushAndClear();
	}

	@Override
	public void exitAttrName(final @Nullable AttrNameContext ctx) {
		Objects.requireNonNull(ctx);
		context.pushAttr(ctx.getText());
		super.exitAttrName(ctx);
	}

	public List<GrammarNode> getListNode() {
		return context.getResults();
	}

	@Override
	public void exitFilterExpr(final FilterExprContext ctx) {
		context.clear();
		super.exitFilterExpr(ctx);
	}

}
