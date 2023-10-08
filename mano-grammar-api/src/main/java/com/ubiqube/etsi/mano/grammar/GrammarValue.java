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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrammarValue implements GrammarNode {
	private String value;
	private GrammarValue next;

	public GrammarValue(final String value) {
		this.value = value;
	}

	public GrammarValue(final String value, final GrammarValue next) {
		this.value = value;
		this.next = next;
	}

	public void append(final String text) {
		if (null == next) {
			next = new GrammarValue(text);
		} else {
			next.append(text);
		}
	}

	public boolean isSingle() {
		return null == next;
	}

	public Object getAsString() {
		if (next != null) {
			throw new GrammarException("");
		}
		return value;
	}

	public List<String> getList() {
		return rec();
	}

	private List<String> rec() {
		final List<String> ret = new ArrayList<>();
		ret.add(value);
		if (next != null) {
			ret.addAll(next.rec());
		}
		return ret;
	}

	@Override
	public String toString() {
		final List<String> llst = rec();
		return llst.stream().collect(Collectors.joining(","));
	}
}
