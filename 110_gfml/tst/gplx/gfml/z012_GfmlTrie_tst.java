/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.gfml; import gplx.*;
import org.junit.*;
import gplx.texts.*; /*CharStream*/
public class z012_GfmlTrie_tst {
	@Before public void setup() {
		trie = GfmlTrie.new_();
	}	GfmlTrie trie;
	@Test public void Null() {
		tst_FindMatch_first("", null);
		tst_FindMatch_first("{", null);
	}
	@Test public void OneChar() {
		trie.Add("{", "val0");
		tst_FindMatch_first("{", "val0");
		tst_FindMatch_first(":", null);	
	}
	@Test public void TwoChar() {
		trie.Add("/*", "val0");
		tst_FindMatch_first("/*", "val0");
		tst_FindMatch_first("//", null);
	}
	@Test public void ManySym() {
		trie.Add(":", "val0");
		trie.Add("{", "val1");
		tst_FindMatch_first(":", "val0");
		tst_FindMatch_first("{", "val1");
		tst_FindMatch_first("-", null);
	}
	@Test public void Overlap_1_2() {
		trie.Add("[", "val0");
		trie.Add("[:", "val1");
		tst_FindMatch_first("[", "val0");
		tst_FindMatch_first("[:", "val1");	
		tst_FindMatch_first("[-", "val0");
		tst_FindMatch_first(":", null);
	}
	@Test public void Overlap_2_1() {
		trie.Add("[:", "val0");
		trie.Add("[", "val1");
		tst_FindMatch_first("[:", "val0");	
		tst_FindMatch_first("[", "val1");
		tst_FindMatch_first("[-", "val1");
		tst_FindMatch_first(":", null);
	}
	@Test public void Overlap_1_1() {
		trie.Add("[", "val0");
		trie.Add("[", "val1");
		tst_FindMatch_first("[", "val1");	// return last added
		tst_FindMatch_first(":", null);
	}
	void tst_FindMatch_first(String text, String expd) {
		CharStream stream = CharStream.pos0_(text);
		String actl = (String)trie.FindMatch(stream);
		Tfds.Eq(expd, actl);
	}
}
