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
package gplx.xowa.addons.wikis.ctgs.bldrs.ucas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.bldrs.*;
import org.junit.*; import gplx.xowa.bldrs.*;
public class Uca_trie_tst {		
	@Before public void init() {fxt.Clear();} private Xob_base_fxt fxt = new Xob_base_fxt();
	@Test  public void Basic() {
		Uca_trie_fxt fxt = new Uca_trie_fxt();
		fxt.Clear();
		fxt.Init_trie_itm("a", Bry_.New_by_ints(10, 11));
		fxt.Init_trie_itm("b", Bry_.New_by_ints(20, 21));
		fxt.Init_trie_itm("c", Bry_.New_by_ints(30, 31));
		fxt.Test_decode(Bry_.New_by_ints(10, 11), "a");
		fxt.Test_decode(Bry_.New_by_ints(10, 11, 20, 21, 30, 31), "abc");
	}
}
class Uca_trie_fxt {
	public void Clear() {
		if (trie == null) {
			trie = new Uca_trie();
			bfr = Bry_bfr_.New();
		}
		trie.Clear();
	}	Uca_trie trie; Bry_bfr bfr;
	public void Init_trie_itm(String charAsStr, byte[] uca) {trie.Init_itm(gplx.core.intls.Utf16_.Decode_to_int(Bry_.new_u8(charAsStr), 0), uca);}
	public void Test_decode(byte[] bry, String expd) {
		trie.Decode(bfr, bry, 0, bry.length);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
