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
package gplx.xowa.mws.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import org.junit.*;
public class Xomw_lnki_wkr__tst {
	private final    Xomw_lnki_wkr__fxt fxt = new Xomw_lnki_wkr__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Text()                             {fxt.Test__parse("a [[A]] z"         , "a <!--LINK 0--> z");}
	@Test   public void Capt()                             {fxt.Test__parse("a [[A|a]] z"       , "a <!--LINK 0--> z");}
	@Test   public void Invalid__char()                    {fxt.Test__parse("a [[<A>]] z"       , "a [[<A>]] z");}
	@Test   public void Html__self()                       {fxt.Test__to_html("[[Page_1]]"      , "<strong class='selflink'>Page_1</strong>");}
	@Test   public void Html__text()                       {fxt.Test__to_html("[[A]]"           , "<a href='/wiki/A' title='A'>A</a>");}
	@Test   public void Html__capt()                       {fxt.Test__to_html("[[A|a]]"         , "<a href='/wiki/A' title='A'>a</a>");}
//		@Test   public void Html__file()                       {fxt.Test__to_html("[[File:A.png|thumb|abc]]"  , "<strong class='selflink'>Page_1</strong>");}
}
class Xomw_lnki_wkr__fxt {
	private final    Xomw_lnki_wkr wkr;
	private final    Xomw_parser_ctx pctx;
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private boolean apos = true;
	public Xomw_lnki_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xomw_parser parser = new Xomw_parser();
		wkr = parser.Lnki_wkr();
		parser.Init_by_wiki(wiki);

		pctx = new Xomw_parser_ctx();
		pctx.Init_by_page(wiki.Ttl_parse(Bry_.new_a7("Page_1")));
	}
	public void Clear() {
		wkr.Clear_state();
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_internal_links(pctx, pbfr.Init(src_bry));
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
	public void Test__to_html(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_internal_links(pctx, pbfr.Init(src_bry));
		wkr.Replace_link_holders(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
