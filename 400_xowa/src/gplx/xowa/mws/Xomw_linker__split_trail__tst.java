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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*;
public class Xomw_linker__split_trail__tst {
	private final    Xomw_linker__split_trail__fxt fxt = new Xomw_linker__split_trail__fxt();
	@Test  public void Basic()                {fxt.Test__split_trail("abc def"          , "abc"             , " def");}
	@Test  public void None()                 {fxt.Test__split_trail(" abc"             , null              , " abc");}
}
class Xomw_linker__split_trail__fxt {
	private final    Xomw_linker linker = new Xomw_linker(new gplx.xowa.mws.linkers.Xomw_link_renderer(new Xomw_sanitizer()));
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Xomw_linker__split_trail__fxt() {
		String[] ary = new String[] {"a", "b", "c", "d", "e", "f"};
		for (String itm : ary)
			trie.Add_str_str(itm, itm);
		linker.Init_by_wiki(trie);
	}
	public void Test__split_trail(String trail_str, String expd_inside, String expd_trail) {
		byte[][] split_trail = linker.Split_trail(Bry_.new_u8(trail_str));
		Gftest.Eq__str(expd_inside, String_.new_u8(split_trail[0]));
		Gftest.Eq__str(expd_trail , String_.new_u8(split_trail[1]));
	}
}
