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
package gplx.xowa.mws.parsers.magiclinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import org.junit.*;
public class Xomw_magiclinks_wkr__tst {
	private final    Xomw_magiclinks_wkr__fxt fxt = new Xomw_magiclinks_wkr__fxt();
	@Test   public void Basic() {fxt.Test__parse("a https://b.org c", "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a> c");}
	@Test   public void Interrupt() {
		// ent
		fxt.Test__parse("a https://b.org&lt;c"   , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>&lt;c");
		// hex
		fxt.Test__parse("a https://b.org&#x3c;c" , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>&#x3c;c");
		// dec
		fxt.Test__parse("a https://b.org&#60;c"  , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>&#60;c");
	}
	@Test   public void Interrupt__hex_dec() {// implementation specific test for mixed hex / dec
		// hex-dec
		fxt.Test__parse("a https://b.org&#x60;c" , "a <a class='external free' rel='nofollow' href='https://b.org&amp;#x60;c'>https://b.org&amp;#x60;c</a>");
		// dec-hex
		fxt.Test__parse("a https://b.org&#3c;c"  , "a <a class='external free' rel='nofollow' href='https://b.org&amp;#3c;c'>https://b.org&amp;#3c;c</a>");
	}
	@Test   public void Separator() {
		// basic
		fxt.Test__parse("a https://b.org.:!? c"      , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>.:!? c");
		// ")" excluded
		fxt.Test__parse("a https://b.org).:!? c"     , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>).:!? c");
		// ")" included b/c "(" exists
		fxt.Test__parse("a https://b.org().:!? c"    , "a <a class='external free' rel='nofollow' href='https://b.org()'>https://b.org()</a>.:!? c");
		// ";" excluded
		fxt.Test__parse("a https://b.org;.:!? c"     , "a <a class='external free' rel='nofollow' href='https://b.org'>https://b.org</a>;.:!? c");
		// ";" included b/c of ent
		fxt.Test__parse("a https://b.org&abc;.:!? c" , "a <a class='external free' rel='nofollow' href='https://b.org&amp;abc;'>https://b.org&amp;abc;</a>.:!? c");
		// ";" included b/c of hex
		fxt.Test__parse("a https://b.org&#xB1;.:!? c", "a <a class='external free' rel='nofollow' href='https://b.org&amp;#xB1;'>https://b.org&amp;#xB1;</a>.:!? c");
		// ";" included b/c of dec
		fxt.Test__parse("a https://b.org&#123;.:!? c", "a <a class='external free' rel='nofollow' href='https://b.org&amp;#123;'>https://b.org&amp;#123;</a>.:!? c");
		// ";" excluded b/c of invalid.ent
		fxt.Test__parse("a https://b.org&a1b;.:!? c" , "a <a class='external free' rel='nofollow' href='https://b.org&amp;a1b'>https://b.org&amp;a1b</a>;.:!? c");
		// ";" excluded b/c of invalid.hex
		fxt.Test__parse("a https://b.org&#x;.:!? c"  , "a <a class='external free' rel='nofollow' href='https://b.org&amp;#x'>https://b.org&amp;#x</a>;.:!? c");
		// ";" excluded b/c of invalid.dec
		fxt.Test__parse("a https://b.org&#a;.:!? c"  , "a <a class='external free' rel='nofollow' href='https://b.org&amp;#a'>https://b.org&amp;#a</a>;.:!? c");
	}
/*
TESTS: regex
"<a https://a.org>"
"<img https://a.org>"
*/
	@Test   public void Invalid() {fxt.Test__parse("a _https://b.org c", "a _https://b.org c");}
}
class Xomw_magiclinks_wkr__fxt {
	private final    Xomw_magiclinks_wkr wkr = new Xomw_magiclinks_wkr();
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private boolean apos = true;
	public Xomw_magiclinks_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);

		Xomw_regex_space regex_space = new Xomw_regex_space();
		pctx.Init_by_page(wiki.Ttl_parse(Bry_.new_a7("Page_1")));
		wkr.Init_by_wiki(new Xomw_linker(), new Xomw_regex_boundary(regex_space), new Xomw_regex_url(regex_space));
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		pbfr.Init(src_bry);
		wkr.Do_magic_links(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
