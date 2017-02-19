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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class XomwLinker_SplitTrailTest {
	private final    XomwLinker_SplitTrailFxt fxt = new XomwLinker_SplitTrailFxt();
	@Test  public void Basic()                {fxt.Test__split_trail("abc def"          , "abc"             , " def");}
	@Test  public void None()                 {fxt.Test__split_trail(" abc"             , null              , " abc");}
}
class XomwLinker_SplitTrailFxt {
	private final    XomwLinker linker = new XomwLinker(new gplx.xowa.mediawiki.includes.linkers.Xomw_link_renderer(new XomwSanitizer()));
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public XomwLinker_SplitTrailFxt() {
		String[] ary = new String[] {"a", "b", "c", "d", "e", "f"};
		for (String itm : ary)
			trie.Add_str_str(itm, itm);
		linker.Init_by_wiki(new Xomw_parser_env(), trie);
	}
	public void Test__split_trail(String trail_str, String expd_inside, String expd_trail) {
		byte[][] split_trail = linker.splitTrail(Bry_.new_u8(trail_str));
		Gftest.Eq__str(expd_inside, String_.new_u8(split_trail[0]));
		Gftest.Eq__str(expd_trail , String_.new_u8(split_trail[1]));
	}
}
