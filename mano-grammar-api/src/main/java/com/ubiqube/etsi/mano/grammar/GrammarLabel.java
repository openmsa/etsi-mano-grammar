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
package com.ubiqube.etsi.mano.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Olivier Vignaud
 */
@Getter
@Setter
public class GrammarLabel implements GrammarNode {
	@Nonnull
	private String label;
	@Nullable
	private GrammarLabel right;

	public GrammarLabel(final String label) {
		this.label = label;
	}

	public GrammarLabel(final String label, final GrammarLabel right) {
		this.label = label;
		this.right = right;
	}

	public void append(final String text) {
		if (right == null) {
			right = new GrammarLabel(text);
		} else {
			right.append(text);
		}
	}

	boolean isSingle() {
		return right == null;
	}

	public String getAsString() {
		if (right != null) {
			throw new GrammarException("");
		}
		return label;
	}

	public List<String> getList() {
		return rec();
	}

	private List<String> rec() {
		final List<String> ret = new ArrayList<>();
		ret.add(label);
		if (right != null) {
			ret.addAll(right.rec());
		}
		return ret;
	}

	@Override
	public String toString() {
		final List<String> llst = rec();
		return llst.stream().collect(Collectors.joining(","));
	}
}
