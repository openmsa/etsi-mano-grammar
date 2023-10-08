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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GrammarContextTest {

	GrammarContext createService() {
		return new GrammarContext();
	}

	@Test
	void test() {
		final GrammarContext srv = createService();
		srv.setLeft(null);
		srv.setOp(null);
		srv.setRight(null);
		srv.pushAndClear();
		srv.clear();
		srv.setOpMulti(GrammarOperandType.EQ);
		srv.pushAndClear();
		srv.pushAttr("");
		srv.addValue("");
		srv.getCurrentOp();
		srv.getLeft();
		srv.getResults();
		srv.getRight();
		srv.isMulti();
	}

	@Test
	void testPushAttr() {
		final GrammarContext srv = createService();
		srv.pushAttr("");
		srv.pushAttr("");
		srv.setLeft(new GrammarValue(""));
		assertThrows(GrammarException.class, () -> srv.pushAttr(""));
	}

	@Test
	void testAddValue() {
		final GrammarContext srv = createService();
		srv.addValue("");
		srv.addValue("");
		srv.setRight(new GrammarLabel(""));
		assertThrows(GrammarException.class, () -> srv.addValue(""));
	}
}
