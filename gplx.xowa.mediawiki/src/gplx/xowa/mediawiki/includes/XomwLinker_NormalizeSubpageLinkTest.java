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
import org.junit.*; import gplx.core.tests.*;
public class XomwLinker_NormalizeSubpageLinkTest {
	private final    XomwLinker_NormalizeSubpageLinkFxt fxt = new XomwLinker_NormalizeSubpageLinkFxt();
	@Test  public void None()                {fxt.Test__normalize_subpage_link("A/B/C"          , "Z"             , ""    , "Z"          , "");}
	@Test  public void Hash()                {fxt.Test__normalize_subpage_link("A/B/C"          , "/Y#Z"          , ""    , "A/B/C/Y#Z"  , "/Y#Z");}
	@Test  public void Slash__basic()        {fxt.Test__normalize_subpage_link("A/B/C"          , "/Z"            , ""    , "A/B/C/Z"    , "/Z");}
	@Test  public void Slash__slash()        {fxt.Test__normalize_subpage_link("A/B/C"          , "/Z/"           , ""    , "A/B/C/Z"    , "Z");}
	@Test  public void Dot2__empty()         {fxt.Test__normalize_subpage_link("A/B/C"          , "../"           , ""    , "A/B"        , "");}
	@Test  public void Dot2__many()          {fxt.Test__normalize_subpage_link("A/B/C"          , "../../Z"       , "z1"  , "A/Z"        , "z1");}
	@Test  public void Dot2__trailing()      {fxt.Test__normalize_subpage_link("A/B/C"          , "../../Z/"      , ""    , "A/Z"        , "Z");}
}
class XomwLinker_NormalizeSubpageLinkFxt {
	private final    XomwEnv env;
	private final    XomwLinker mgr = new XomwLinker(new gplx.xowa.mediawiki.includes.linkers.XomwLinkRenderer(new XomwSanitizer()));
	private final    XomwLinker_NormalizeSubpageLink normalize_subpage_link = new XomwLinker_NormalizeSubpageLink();
	public XomwLinker_NormalizeSubpageLinkFxt() {
		this.env = XomwEnv.NewTest();
	}
	public void Test__normalize_subpage_link(String page_title_str, String link, String text, String expd_link, String expd_text) {
		mgr.normalizeSubpageLink(normalize_subpage_link, XomwTitle.newFromText(env, Bry_.new_u8(page_title_str)), Bry_.new_u8(link), Bry_.new_u8(text));
		Gftest.Eq__str(expd_link, String_.new_u8(normalize_subpage_link.link));
		Gftest.Eq__str(expd_text, String_.new_u8(normalize_subpage_link.text));
	}
}
