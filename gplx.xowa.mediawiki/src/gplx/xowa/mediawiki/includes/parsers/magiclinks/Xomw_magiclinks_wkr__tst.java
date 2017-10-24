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
package gplx.xowa.mediawiki.includes.parsers.magiclinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_magiclinks_wkr__tst {
	private final    Xomw_magiclinks_wkr__fxt fxt = new Xomw_magiclinks_wkr__fxt();
	@Test   public void Basic() {fxt.Test__parse("a https://b.org z", "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a> z");}
	@Test   public void Invalid() {fxt.Test__parse("a _https://b.org z", "a _https://b.org z");}
	@Test   public void Tag__anch() {fxt.Test__parse("a <a title=\"https://b.org\">b</a> z", "a <a title=\"https://b.org\">b</a> z");}
	@Test   public void Tag__misc() {fxt.Test__parse("a <div title=\"https://b.org\">b</div> z", "a <div title=\"https://b.org\">b</div> z");}
	@Test   public void Interrupt() {
		// ent
		fxt.Test__parse("a https://b.org&lt;z"   , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>&lt;z");
		// hex
		fxt.Test__parse("a https://b.org&#x3c;z" , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>&#x3c;z");
		// dec
		fxt.Test__parse("a https://b.org&#60;z"  , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>&#60;z");
		// num_post_proto rule
		fxt.Test__parse("a https://&lt; z"       , "a https://&lt; z");
	}
	@Test   public void Interrupt__hex_dec() {// implementation specific test for mixed hex / dec
		// dec-hex
		fxt.Test__parse("a https://b.org&#3c;z"      , "a <a rel='nofollow' class='external free' href='https://b.org&amp;#3c;z'>https://b.org&amp;#3c;z</a>");
	}
	@Test   public void Separator() {
		// basic; ,;.:!?
		fxt.Test__parse("a https://b.org,;.:!? z"    , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>,;.:!? z");
		// ")" excluded
		fxt.Test__parse("a https://b.org).:!? z"     , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>).:!? z");
		// ")" included b/c "(" exists
		fxt.Test__parse("a https://b.org().:!? z"    , "a <a rel='nofollow' class='external free' href='https://b.org()'>https://b.org()</a>.:!? z");
		// ";" excluded
		fxt.Test__parse("a https://b.org;.:!? z"     , "a <a rel='nofollow' class='external free' href='https://b.org'>https://b.org</a>;.:!? z");
		// ";" included b/c of ent
		fxt.Test__parse("a https://b.org&abc;.:!? z" , "a <a rel='nofollow' class='external free' href='https://b.org&amp;abc;'>https://b.org&amp;abc;</a>.:!? z");
		// ";" included b/c of hex; note that Clean_url changes "&#xB1;" to "±"
		fxt.Test__parse("a https://b.org&#xB1;.:!? z", "a <a rel='nofollow' class='external free' href='https://b.org±'>https://b.org±</a>.:!? z");
		// ";" included b/c of dec; note that Clean_url changes "&#123;" to "{"
		fxt.Test__parse("a https://b.org&#123;.:!? z", "a <a rel='nofollow' class='external free' href='https://b.org{'>https://b.org{</a>.:!? z");
		// ";" excluded b/c of invalid.ent
		fxt.Test__parse("a https://b.org&a1b;.:!? z" , "a <a rel='nofollow' class='external free' href='https://b.org&amp;a1b'>https://b.org&amp;a1b</a>;.:!? z");
		// ";" excluded b/c of invalid.hex
		fxt.Test__parse("a https://b.org&#x;.:!? z"  , "a <a rel='nofollow' class='external free' href='https://b.org&amp;#x'>https://b.org&amp;#x</a>;.:!? z");
		// ";" excluded b/c of invalid.dec
		fxt.Test__parse("a https://b.org&#a;.:!? z"  , "a <a rel='nofollow' class='external free' href='https://b.org&amp;#a'>https://b.org&amp;#a</a>;.:!? z");
		// num_post_proto rule
		fxt.Test__parse("a https://.:!? z"           , "a https://.:!? z");
	}
	@Test   public void Clean_url() {
		// basic
		fxt.Test__parse("http://a᠆b.org/c᠆d"          , "<a rel='nofollow' class='external free' href='http://ab.org/c᠆d'>http://ab.org/c᠆d</a>");
	}
}
class Xomw_magiclinks_wkr__fxt {
	private final    Xomw_magiclinks_wkr wkr;
	private final    XomwParserCtx pctx = new XomwParserCtx();
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	public Xomw_magiclinks_wkr__fxt() {
		Xomw_regex_space regex_space = new Xomw_regex_space();
		XomwParser parser = new XomwParser(XomwEnv.NewTest());
		pctx.Init_by_page(XomwTitle.newFromText(parser.Env(), Bry_.new_a7("Page_1")));
		this.wkr = new Xomw_magiclinks_wkr(parser, parser.Sanitizer(), parser.Linker(), new Xomw_regex_boundary(regex_space), new Xomw_regex_url(regex_space));
		wkr.Init_by_wiki();
	}
	public void Test__parse(String src_str, String expd) {Test__parse(Bool_.Y, src_str, expd);}
	public void Test__parse(boolean apos, String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		pbfr.Init(src_bry);
		wkr.doMagicLinks(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
