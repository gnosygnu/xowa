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
package gplx.xowa.mediawiki.includes.parsers.hrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_hr_wkr__tst {
	private final    Xomw_hr_wkr__fxt fxt = new Xomw_hr_wkr__fxt();
	@Test  public void Basic()       {fxt.Test__parse("a\n-----b"                         , "a\n<hr />b");}
	@Test  public void Extend()      {fxt.Test__parse("a\n------b"                        , "a\n<hr />b");}
	@Test  public void Not_found()   {fxt.Test__parse("a\n----b"                          , "a\n----b");}
	@Test  public void Bos()         {fxt.Test__parse("-----a"                            , "<hr />a");}
	@Test  public void Bos_and_mid() {fxt.Test__parse("-----a\n-----b"                    , "<hr />a\n<hr />b");}
}
class Xomw_hr_wkr__fxt {
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private final    Xomw_hr_wkr wkr = new Xomw_hr_wkr();
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_hrs(new Xomw_parser_ctx(), pbfr.Init(src_bry));
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
