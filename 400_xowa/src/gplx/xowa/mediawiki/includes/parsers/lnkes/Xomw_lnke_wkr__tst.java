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
package gplx.xowa.mediawiki.includes.parsers.lnkes;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.xowa.mediawiki.XomwEnv_fxt;
import gplx.xowa.mediawiki.includes.parsers.XomwParser;
import gplx.xowa.mediawiki.includes.parsers.XomwParserBfr;
import gplx.xowa.mediawiki.includes.parsers.XomwParserCtx;
import gplx.xowa.mediawiki.includes.parsers.Xomw_regex_space;
import gplx.xowa.mediawiki.includes.parsers.Xomw_regex_url;
import org.junit.Test;
public class Xomw_lnke_wkr__tst {
	private final Xomw_lnke_wkr__fxt fxt = new Xomw_lnke_wkr__fxt();
	@Test public void Basic()                         {fxt.Test__parse("[https://a.org b]"           , "<a rel='nofollow' class='external text' href='https://a.org'>b</a>");}
	@Test public void Invaild__protocol()             {fxt.Test__parse("[httpz:a.org]"               , "[httpz:a.org]");}
	@Test public void Invaild__protocol_slash()       {fxt.Test__parse("[https:a.org]"               , "[https:a.org]");}
	@Test public void Invaild__urlchars__0()          {fxt.Test__parse("[https://]"                  , "[https://]");}
	@Test public void Invaild__urlchars__bad()        {fxt.Test__parse("[https://\"]"                , "[https://\"]");}
	@Test public void Many() {
		fxt.Test__parse(BryUtlByWtr.NewU8NlSwapAposAsStr
		( "a"
		, "[https://b.org c]"
		, "d"
		, "[https://e.org f]"
		, "g"
		), BryUtlByWtr.NewU8NlSwapAposAsStr
		( "a"
		, "<a rel='nofollow' class='external text' href='https://b.org'>c</a>"
		, "d"
		, "<a rel='nofollow' class='external text' href='https://e.org'>f</a>"
		, "g"
		));
	}
	@Test public void Protocol_rel() {
		fxt.Test__parse("[//a.org b]"                             , "<a rel='nofollow' class='external text' href='//a.org'>b</a>");
	}
	@Test public void Url_should_not_has_angle_entities() {
		fxt.Test__parse("[https://a.org/b&lt;c z]"                , "<a rel='nofollow' class='external text' href='https://a.org/b'>&lt;c z</a>");
		fxt.Test__parse("[https://a.org/b&gt;c z]"                , "<a rel='nofollow' class='external text' href='https://a.org/b'>&gt;c z</a>");
	}
	@Test public void Link_trail() {// checks for noop via "Have link text"
		fxt.Test__parse("[https://a.org b]xyz"                    , "<a rel='nofollow' class='external text' href='https://a.org'>b</a>xyz");
		fxt.Test__parse("[https://a.org b]x!z"                    , "<a rel='nofollow' class='external text' href='https://a.org'>b</a>x!z");
	}
	@Test public void Clean_url() {
		fxt.Test__parse("[https://a&quot;­b c]"                   , "<a rel='nofollow' class='external text' href='https://a%22b'>c</a>");
	}
}
class Xomw_lnke_wkr__fxt {
	private final Xomw_lnke_wkr wkr;
	private final XomwParserBfr pbfr = new XomwParserBfr();
	private boolean apos = true;
	public Xomw_lnke_wkr__fxt() {
		XomwParser parser = new XomwParser(XomwEnv_fxt.NewTest());
		this.wkr = new Xomw_lnke_wkr(parser, BryWtr.New(), parser.Linker(), parser.Sanitizer());
		Xomw_regex_space regex_space = new Xomw_regex_space();
		wkr.Init_by_wiki(XomwParser.Protocols__dflt(), new Xomw_regex_url(regex_space), regex_space);
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		wkr.replaceExternalLinks(new XomwParserCtx(), pbfr.Init(src_bry));
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		GfoTstr.EqLines(expd, pbfr.Rslt().ToStrAndClear(), src_str);
	}
}
