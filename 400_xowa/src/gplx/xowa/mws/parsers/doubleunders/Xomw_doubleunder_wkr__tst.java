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
package gplx.xowa.mws.parsers.doubleunders; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xomw_doubleunder_wkr__tst {
	private final    Xomw_doubleunder_wkr__fxt fxt = new Xomw_doubleunder_wkr__fxt();
	@Test  public void No_match()        {fxt.Test__parse("a b c"                                    , "a b c");}
	@Test  public void Force_toc()       {fxt.Test__parse("a __FORCETOC__ b"                         , "a  b").Test__prop_y(fxt.data.force_toc);}
	@Test  public void Toc()             {fxt.Test__parse("a __TOC__ b __TOC__ c"                    , "a <!--MWTOC--> b  c").Test__prop_y(fxt.data.toc, fxt.data.show_toc, fxt.data.force_toc_position);}
	@Test  public void Notoc_only()      {fxt.Test__parse("a __NOTOC__ b"                            , "a  b").Test__prop_y(fxt.data.no_toc).Test__prop_n(fxt.data.show_toc);}	// show_toc is false
	@Test  public void Notoc_w_toc()     {fxt.Test__parse("a __TOC__ b __NOTOC__ c"                  , "a <!--MWTOC--> b  c").Test__prop_y(fxt.data.toc, fxt.data.show_toc, fxt.data.force_toc_position);} // show_toc is true
}
class Xomw_doubleunder_wkr__fxt {
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private final    Xomw_doubleunder_wkr wkr = new Xomw_doubleunder_wkr();
	public Xomw_doubleunder_data data;
	public Xomw_doubleunder_wkr__fxt() {
		wkr.Init_by_wiki();
		data = wkr.data;
	}
	public Xomw_doubleunder_wkr__fxt Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Do_double_underscore(pctx, pbfr.Init(src_bry));
		Gftest.Eq__str(expd, pbfr.Rslt().To_str_and_clear(), src_str);
		return this;
	}
	public  Xomw_doubleunder_wkr__fxt Test__prop_y(boolean... ary) {return Test__prop(Bool_.Y, ary);}
	public  Xomw_doubleunder_wkr__fxt Test__prop_n(boolean... ary) {return Test__prop(Bool_.N, ary);}
	private Xomw_doubleunder_wkr__fxt Test__prop(boolean expd, boolean... ary) {
		for (boolean v : ary)
			Gftest.Eq__bool(expd, v);
		return this;
	}
}
