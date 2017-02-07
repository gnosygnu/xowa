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
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	public Xomw_magiclinks_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);

		Xomw_regex_space regex_space = new Xomw_regex_space();
		pctx.Init_by_page(wiki.Ttl_parse(Bry_.new_a7("Page_1")));
		Xomw_parser parser = new Xomw_parser();
		this.wkr = new Xomw_magiclinks_wkr(parser, parser.Sanitizer(), parser.Linker(), new Xomw_regex_boundary(regex_space), new Xomw_regex_url(regex_space));
		wkr.Init_by_wiki();
	}
	public void Test__parse(String src_str, String expd) {Test__parse(Bool_.Y, src_str, expd);}
	public void Test__parse(boolean apos, String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		pbfr.Init(src_bry);
		wkr.Do_magic_links(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
