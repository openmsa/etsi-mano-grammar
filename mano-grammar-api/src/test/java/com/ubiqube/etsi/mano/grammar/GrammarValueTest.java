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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class GrammarValueTest {

	GrammarValue createService() {
		return new GrammarValue("0");
	}

	@Test
	void test() {
		final GrammarValue srv = new GrammarValue(null, null);
		assertNotNull(srv);
		srv.getNext();
		srv.getValue();
		srv.setNext(null);
		srv.setValue("");
		srv.toString();
	}

	@Test
	void testAppend() {
		final GrammarValue srv = createService();
		assertTrue(srv.isSingle());
		srv.append("a");
		assertFalse(srv.isSingle());
		srv.append("b");
		assertThrows(GrammarException.class, () -> srv.getAsString());
		final List<String> res = srv.getList();
		assertEquals(List.of("0", "a", "b"), res);
		srv.toString();
	}

	@Test
	void testSingle() {
		final GrammarValue srv = new GrammarValue("abc", null);
		assertEquals("abc", srv.getAsString());
	}
}
