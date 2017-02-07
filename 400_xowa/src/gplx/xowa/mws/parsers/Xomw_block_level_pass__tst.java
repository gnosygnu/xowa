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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mws.linkers.*;
public class Xomw_block_level_pass__tst {
	private final    Xomw_block_level_pass__fxt fxt = new Xomw_block_level_pass__fxt();
	@Test   public void Basic() {
		fxt.Test__do_block_levels(String_.Concat_lines_nl_skip_last
		( "a"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
	}
}
class Xomw_block_level_pass__fxt {
	private final    Xomw_block_level_pass block_level_pass = new Xomw_block_level_pass();
	private final    Xomw_parser_ctx pctx = new Xomw_parser_ctx();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private boolean apos = true;
	public void Test__do_block_levels(String src, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		block_level_pass.Do_block_levels(pctx, pbfr.Init(Bry_.new_u8(src)), true);
		Gftest.Eq__str(expd, pbfr.Rslt().To_str_and_clear());
	}
}
