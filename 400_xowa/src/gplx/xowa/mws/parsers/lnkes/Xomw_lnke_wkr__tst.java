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
package gplx.xowa.mws.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import org.junit.*;
public class Xomw_lnke_wkr__tst {
	private final    Xomw_lnke_wkr__fxt fxt = new Xomw_lnke_wkr__fxt();
	@Test   public void Basic()                         {fxt.Test__parse("[https://a.org b]"           , "<a rel='nofollow' class='external text' href='https://a.org'>b</a>");}
	@Test   public void Invaild__protocol()             {fxt.Test__parse("[httpz:a.org]"               , "[httpz:a.org]");}
	@Test   public void Invaild__protocol_slash()       {fxt.Test__parse("[https:a.org]"               , "[https:a.org]");}
	@Test   public void Invaild__urlchars__0()          {fxt.Test__parse("[https://]"                  , "[https://]");}
	@Test   public void Invaild__urlchars__bad()        {fxt.Test__parse("[https://\"]"                , "[https://\"]");}
	@Test   public void Many() {
		fxt.Test__parse(String_.Concat_lines_nl_apos_skip_last
		( "a"
		, "[https://b.org c]"
		, "d"
		, "[https://e.org f]"
		, "g"
		), String_.Concat_lines_nl_apos_skip_last
		( "a"
		, "<a rel='nofollow' class='external text' href='https://b.org'>c</a>"
		, "d"
		, "<a rel='nofollow' class='external text' href='https://e.org'>f</a>"
		, "g"
		));
	}
}
class Xomw_lnke_wkr__fxt {
	private final    Xomw_lnke_wkr wkr = new Xomw_lnke_wkr(new Xomw_parser());
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private boolean apos = true;
	public Xomw_lnke_wkr__fxt() {
		Xomw_regex_space regex_space = new Xomw_regex_space();
		wkr.Init_by_wiki(Xomw_parser.Protocols__dflt(), new Xomw_regex_url(regex_space), regex_space);
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_external_links(new Xomw_parser_ctx(), pbfr.Init(src_bry));
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
