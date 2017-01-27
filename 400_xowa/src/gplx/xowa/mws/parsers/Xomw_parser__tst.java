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
import org.junit.*;
public class Xomw_parser__tst {
	private final    Xomw_parser__fxt fxt = new Xomw_parser__fxt();
	@Test  public void Basic() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		("a https://c.org b"
		), String_.Concat_lines_nl_skip_last
		( ""
		));
	}		
}
class Xomw_parser__fxt {
	private final    Xomw_parser mgr = new Xomw_parser();
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	public Xomw_parser__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		mgr.Init_by_wiki(wiki);
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		mgr.Internal_parse(pbfr, src_bry);
		mgr.Internal_parse_half_parsed(pbfr, true, true);
		Tfds.Eq_str_lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
}
