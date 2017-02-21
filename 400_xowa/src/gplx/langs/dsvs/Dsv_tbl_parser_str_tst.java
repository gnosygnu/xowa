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
public class Dsv_tbl_parser_str_tst {
	private Dsv_mok_fxt fxt = new Dsv_mok_fxt();
	@Test  public void Basic() {
		fxt	.Test_load(String_.Concat_lines_nl_skip_last
		( "a|A"
		, "b|B"
		)
		, fxt.mgr_str_(2)
		, fxt.itm_str_("a", "A")
		, fxt.itm_str_("b", "B")
		);
	}
	@Test  public void Blank_lines() {
		fxt	.Test_load(String_.Concat_lines_nl_skip_last
		( ""
		, "a|A"
		, ""
		, "b|B"
		, ""
		)
		, fxt.mgr_str_(2)
		, fxt.itm_str_("a", "A")
		, fxt.itm_str_("b", "B")
		);
	}
	@Test  public void Incomplete_row() {
		fxt	.Test_load(String_.Concat_lines_nl_skip_last
		( "a"
		, "b"
		, ""
		)
		, fxt.mgr_str_(2)
		, fxt.itm_str_("a")
		, fxt.itm_str_("b")
		);
	}
	@Test  public void Incomplete_row_2() {	// PURPOSE: handle multiple incomplete cells
		fxt	.Test_load(String_.Concat_lines_nl_skip_last
		( "a|")
		, fxt.mgr_str_(3)
		, fxt.itm_str_("a", "")
		);
	}
}
abstract class Mok_mgr_base extends Dsv_wkr_base {
	public abstract To_str_able[] Itms();
}
class Dsv_mok_fxt {
	private Dsv_tbl_parser tbl_parser = new Dsv_tbl_parser();
	public Dsv_mok_fxt Clear() {
		tbl_parser.Clear();
		return this;
	}
	public Mok_mgr_base mgr_int_() {return new Mok_int_mgr();}
	public Mok_mgr_base mgr_str_(int len) {return new Mok_str_mgr(len);}
	public Mok_str_itm itm_str_(String... flds) {return new Mok_str_itm(flds);}
	public Mok_int_itm itm_int_(String fld_0, int fld_1, int fld_2) {return new Mok_int_itm(fld_0, fld_1, fld_2);}
	public void Test_load(String src, Mok_mgr_base mgr, To_str_able... expd) {
		mgr.Load_by_bry(Bry_.new_u8(src));
		Tfds.Eq_ary_str(expd, mgr.Itms());
	}
}
class Mok_str_itm implements To_str_able {
	private String[] flds;
	public Mok_str_itm(String[] flds) {this.flds = flds;}
	public String To_str() {return String_.Concat_with_str("|", flds);}
}
class Mok_str_mgr extends Mok_mgr_base {
	private int flds_len;
	public Mok_str_mgr(int flds_len) {
		this.flds_len = flds_len;
	}
	public void Clear() {itms.Clear();}
	@Override public To_str_able[] Itms() {return (To_str_able[])itms.To_ary(To_str_able.class);} private List_adp itms = List_adp_.New();
	private List_adp flds = List_adp_.New();
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		flds.Add(String_.new_u8(src, bgn, end));
		return true;
	}
	@Override public Dsv_fld_parser[] Fld_parsers() {
		Dsv_fld_parser[] rv = new Dsv_fld_parser[flds_len];
		for (int i = 0; i < flds_len; i++)
			rv[i] = Dsv_fld_parser_.Bry_parser;
		return rv;
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		Mok_str_itm itm = new Mok_str_itm((String[])flds.To_ary_and_clear(String.class));
		itms.Add(itm);
	}
}
