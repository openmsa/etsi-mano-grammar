/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.grammar.v25;

import java.util.List;
import java.util.Objects;

import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.AttrNameContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.FilterContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.OpMultiContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.OpOneContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.SimpleFilterExprMultiContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.SimpleFilterExprOneContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25.ValueContext;
import com.mano.etsi.mano.grammar.v25.EtsiFilterV25BaseListener;
import com.ubiqube.etsi.mano.grammar.GrammarContext;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;

import jakarta.annotation.Nullable;

public class TreeBuilderV25 extends EtsiFilterV25BaseListener {

	private final GrammarContext context = new GrammarContext();

	public List<GrammarNode> getListNode() {
		return context.getResults();
	}

	@Override
	public void enterSimpleFilterExprOne(final @Nullable SimpleFilterExprOneContext ctx) {
		context.clear();
	}

	@Override
	public void enterSimpleFilterExprMulti(final @Nullable SimpleFilterExprMultiContext ctx) {
		context.clear();
	}

	@Override
	public void exitSimpleFilterExprOne(final @Nullable SimpleFilterExprOneContext ctx) {
		context.pushAndClear();
	}

	@Override
	public void exitSimpleFilterExprMulti(final @Nullable SimpleFilterExprMultiContext ctx) {
		context.pushAndClear();
	}

	@Override
	public void exitFilter(final @Nullable FilterContext ctx) {
		Objects.requireNonNull(ctx);
		context.pushAndClear();
	}

	@Override
	public void exitOpOne(final @Nullable OpOneContext ctx) {
		Objects.requireNonNull(ctx);
		final GrammarOperandType op = GrammarOperandType.valueOf(ctx.getText().toUpperCase());
		context.setOp(op);
	}

	@Override
	public void exitOpMulti(final @Nullable OpMultiContext ctx) {
		Objects.requireNonNull(ctx);
		final GrammarOperandType op = GrammarOperandType.valueOf(ctx.getText().toUpperCase());
		context.setOpMulti(op);
	}

	@Override
	public void exitAttrName(final @Nullable AttrNameContext ctx) {
		Objects.requireNonNull(ctx);
		context.pushAttr(ctx.getText());
	}

	@Override
	public void exitValue(final @Nullable ValueContext ctx) {
		Objects.requireNonNull(ctx);
		context.addValue(ctx.getText());
	}

}
