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
package gplx.xowa.mediawiki.includes.title; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.core.btries.*;
import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.mediawiki.languages.*; import gplx.xowa.langs.*;
public class XomwMediaWikiTitleCodecTest {
	private final    XomwMediaWikiTitleCodecFxt fxt = new XomwMediaWikiTitleCodecFxt();
	@Test  public void regexTitlePrefix() {
		// no match
		fxt.Test_regexTitlePrefix("a"         , "a", null);
		// ns
		fxt.Test_regexTitlePrefix("a:b"       , "a", "b");
		// underscores
		fxt.Test_regexTitlePrefix("a__:___b"  , "a", "b");
	}
	@Test  public void splitTitleString() {
		XomwMediaWikiTitleCodec codec = fxt.Make_codec(fxt.Make_lang());
		// ns
		fxt.Test_splitTitleString(codec, "File:A"    , fxt.Make_parts(XomwDefines.NS_FILE, "A"));
	}
}
class XomwMediaWikiTitleCodecFxt {
	private byte[][] regexTitlePrefixResult = new byte[2][];
	public void Test_regexTitlePrefix(String src, String expd_ns, String expd_ttl) {
		XomwRegexTitlePrefix.preg_match(regexTitlePrefixResult, Bry_.new_u8(src));
		Gftest.Eq__str(expd_ns,  String_.new_u8(regexTitlePrefixResult[0]));
		Gftest.Eq__str(expd_ttl, String_.new_u8(regexTitlePrefixResult[1]));
	}
	public XomwMediaWikiTitleCodecParts Make_parts(int ns, String dbkey) {
		return new XomwMediaWikiTitleCodecParts(Bry_.new_u8(dbkey), ns);
	}
	public XomwMediaWikiTitleCodec Make_codec(XomwLanguage lang) {
		XomwEnv env = new XomwEnv(lang.XoLang());
		return env.MediaWikiServices().getTitleFormatter();
	}
	public XomwLanguage Make_lang() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xol_lang_itm lang = new Xol_lang_itm(app.Lang_mgr(), Xol_lang_itm_.Key_en);
		return new XomwLanguage(lang);
	}
	public void Test_splitTitleString(XomwMediaWikiTitleCodec codec, String src, XomwMediaWikiTitleCodecParts expd) {
		XomwMediaWikiTitleCodecParts actl = codec.splitTitleString(Bry_.new_u8(src), XomwDefines.NS_MAIN);
		Gftest.Eq__str(expd.ToStr(), actl.ToStr());
	}
}
