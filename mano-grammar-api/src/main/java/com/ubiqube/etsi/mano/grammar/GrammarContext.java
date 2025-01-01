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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import lombok.Getter;

@Getter
public class GrammarContext {
	private GrammarNode left;
	private GrammarOperandType currentOp;
	private GrammarNode right;
	private boolean multi;
	@Nonnull
	private final List<GrammarNode> results = new ArrayList<>();

	public void pushAndClear() {
		results.add(new BooleanExpression(left, currentOp, right));
		clear();
	}

	public void clear() {
		left = null;
		currentOp = null;
		right = null;
		multi = false;
	}

	public void setOp(final GrammarOperandType op) {
		Objects.isNull(currentOp);
		currentOp = op;
	}

	public void setOpMulti(final GrammarOperandType op) {
		setOp(op);
		multi = true;
	}

	public void pushAttr(final String text) {
		if (left instanceof final GrammarLabel gl) {
			gl.append(text);
		} else if (null == left) {
			left = new GrammarLabel(text);
		} else {
			throw new GrammarException("Unknown value for left: " + left);
		}
	}

	public void addValue(final String text) {
		if (right == null) {
			right = new GrammarValue(text);
		} else if (right instanceof final GrammarValue gv) {
			gv.append(text);
		} else {
			throw new GrammarException("Unknown value for right: " + right);
		}
	}

	public void setLeft(final GrammarNode left) {
		this.left = left;
	}

	public void setRight(final GrammarNode right) {
		this.right = right;
	}

}
