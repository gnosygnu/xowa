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
package gplx.xowa.parsers.mws.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import org.junit.*;
public class Xomw_hdr_wkr_tst {
	private final    Xomw_hdr_wkr_fxt fxt = new Xomw_hdr_wkr_fxt();
	@Test  public void Basic()		{
		fxt.Test__parse("==A=="					, "<h2>A</h2>");
		fxt.Test__parse("abc\n==A==\ndef"		, "abc\n<h2>A</h2>\ndef");

		fxt.Test__parse("abc"					, "abc");
		fxt.Test__parse("abc\ndef"				, "abc\ndef");
		fxt.Test__parse("abc\n=="				, "abc\n<h1></h1>");
	}
}
class Xomw_hdr_wkr_fxt {
	private final    Xomw_hdr_wkr wkr = new Xomw_hdr_wkr();
	private final    Bry_bfr bfr = Bry_bfr_.New(); private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Parse(bfr, pctx, src_bry, -1, src_bry.length);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear(), src_str);
	}
}
