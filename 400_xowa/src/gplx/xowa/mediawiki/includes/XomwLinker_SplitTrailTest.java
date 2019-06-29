/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class XomwLinker_SplitTrailTest {
	private final    XomwLinker_SplitTrailFxt fxt = new XomwLinker_SplitTrailFxt();
	@Test  public void Basic()                {fxt.Test__split_trail("abc def"          , "abc"             , " def");}
	@Test  public void None()                 {fxt.Test__split_trail(" abc"             , null              , " abc");}
}
class XomwLinker_SplitTrailFxt {
	private final    XomwLinker linker = new XomwLinker(new gplx.xowa.mediawiki.includes.linkers.XomwLinkRenderer(new XomwSanitizer()));
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public XomwLinker_SplitTrailFxt() {
		String[] ary = new String[] {"a", "b", "c", "d", "e", "f"};
		for (String itm : ary)
			trie.Add_str_str(itm, itm);
		linker.Init_by_wiki(XomwEnv.NewTest(), trie);
	}
	public void Test__split_trail(String trail_str, String expd_inside, String expd_trail) {
		byte[][] split_trail = linker.splitTrail(Bry_.new_u8(trail_str));
		Gftest.Eq__str(expd_inside, String_.new_u8(split_trail[0]));
		Gftest.Eq__str(expd_trail , String_.new_u8(split_trail[1]));
	}
}
