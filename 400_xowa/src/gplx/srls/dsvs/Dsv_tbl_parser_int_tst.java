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
package gplx.srls.dsvs; import gplx.*; import gplx.srls.*;
import org.junit.*;
public class Dsv_tbl_parser_int_tst {		
	private Dsv_mok_fxt fxt = new Dsv_mok_fxt();
	@Test  public void Basic() {
		fxt	.Test_load(String_.Concat_lines_nl_skip_last
		( "a|1|3"
		, "b|2|4"
		)
		, fxt.mgr_int_()
		, fxt.itm_int_("a", 1, 3)
		, fxt.itm_int_("b", 2, 4)
		);
	}
}
class Mok_int_itm implements XtoStrAble {
	private String fld_0;
	private int fld_1, fld_2;
	public Mok_int_itm(String fld_0, int fld_1, int fld_2) {this.fld_0 = fld_0; this.fld_1 = fld_1; this.fld_2 = fld_2;}
	public String XtoStr() {return String_.Concat_with_str("|", fld_0, Int_.Xto_str(fld_1), Int_.Xto_str(fld_2));}
}
class Mok_int_mgr extends Mok_mgr_base {
	public void Clear() {itms.Clear();}
	@Override public XtoStrAble[] Itms() {return (XtoStrAble[])itms.Xto_ary(XtoStrAble.class);} private ListAdp itms = ListAdp_.new_();
	private String fld_0;
	private int fld_1, fld_2;
	@Override public Dsv_fld_parser[] Fld_parsers() {
		return new Dsv_fld_parser[] {Dsv_fld_parser_bry._, Dsv_fld_parser_int._, Dsv_fld_parser_int._};
	}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: fld_0 = String_.new_utf8_(src, bgn, end); return true;
			default: return false;
		}
	}
	@Override public boolean Write_int(Dsv_tbl_parser parser, int fld_idx, int pos, int val_int) {
		switch (fld_idx) {
			case 1: fld_1 = val_int; return true;
			case 2: fld_2 = val_int; return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		Mok_int_itm itm = new Mok_int_itm(fld_0, fld_1, fld_2);
		itms.Add(itm);
	}
}
