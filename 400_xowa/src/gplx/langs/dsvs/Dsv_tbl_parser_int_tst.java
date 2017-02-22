/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
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
class Mok_int_itm implements To_str_able {
	private String fld_0;
	private int fld_1, fld_2;
	public Mok_int_itm(String fld_0, int fld_1, int fld_2) {this.fld_0 = fld_0; this.fld_1 = fld_1; this.fld_2 = fld_2;}
	public String To_str() {return String_.Concat_with_str("|", fld_0, Int_.To_str(fld_1), Int_.To_str(fld_2));}
}
class Mok_int_mgr extends Mok_mgr_base {
	public void Clear() {itms.Clear();}
	@Override public To_str_able[] Itms() {return (To_str_able[])itms.To_ary(To_str_able.class);} private List_adp itms = List_adp_.New();
	private String fld_0;
	private int fld_1, fld_2;
	@Override public Dsv_fld_parser[] Fld_parsers() {
		return new Dsv_fld_parser[] {Dsv_fld_parser_bry.Instance, Dsv_fld_parser_int.Instance, Dsv_fld_parser_int.Instance};
	}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: fld_0 = String_.new_u8(src, bgn, end); return true;
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
