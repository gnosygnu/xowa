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
package gplx.xowa.mediawiki.includes.parsers.nbsps; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_nbsp_wkr__tst {
	private final    Xomw_nbsp_wkr__fxt fxt = new Xomw_nbsp_wkr__fxt();
	@Test   public void Noop()                             {fxt.Test__parse("abc"                         , "abc");}
	@Test   public void Space_lhs__colon()                 {fxt.Test__parse("a :b c"                      , "a&#160;:b c");}
	@Test   public void Space_lhs__laquo()                 {fxt.Test__parse("a »b c"                      , "a&#160;»b c");}
	@Test   public void Space_rhs()                        {fxt.Test__parse("a« b c"                      , "a«&#160;b c");}
	@Test   public void Important()                        {fxt.Test__parse("a &#160;! important b"       , "a  ! important b");}
}
class Xomw_nbsp_wkr__fxt {
	private final    Xomw_nbsp_wkr wkr = new Xomw_nbsp_wkr();
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private boolean apos = true;
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		pbfr.Init(src_bry);
		wkr.Do_nbsp(pctx, pbfr);
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
