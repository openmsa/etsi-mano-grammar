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
package com.ubiqube.etsi.mano.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class GrammarLabelTest {

	GrammarLabel createService() {
		return new GrammarLabel("0");
	}

	@Test
	void test() {
		final GrammarLabel srv = createService();
		assertTrue(srv.isSingle());
		assertNotNull(srv.getAsString());
		srv.append("a");
		srv.append("b");
		final List<String> res = srv.getList();
		assertEquals(List.of("0", "a", "b"), res);
		assertThrows(GrammarException.class, () -> srv.getAsString());
		assertFalse(srv.isSingle());
	}

	@Test
	void testCtor() {
		final GrammarLabel srv = new GrammarLabel("0", null);
		srv.getLabel();
		srv.getRight();
		srv.setLabel("e");
		srv.setRight(null);
		assertThrows(NullPointerException.class, () -> srv.setLabel(null));
		assertNotNull(srv.toString());
	}

}
