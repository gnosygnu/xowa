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
package gplx.xowa.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.langs.dsvs.*;
import gplx.xowa.wikis.nss.*;
public class Xob_ns_file_itm_parser extends Dsv_wkr_base {
	private byte[] ns_ids_bry; private String name; private final    List_adp rslts = List_adp_.New();
	private Xow_ns_mgr ns_mgr; private byte db_file_tid; private boolean mode_each = false;
	public void Ctor(byte db_file_tid, Xow_ns_mgr ns_mgr) {
		this.db_file_tid = db_file_tid; this.ns_mgr = ns_mgr;
		this.mode_each = false; rslts.Clear();
	}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: ns_ids_bry	= Bry_.Mid(src, bgn, end); return true;
			case 1: name		= String_.new_u8(src, bgn, end); return true;
			default: return false;
		}
	}		
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		if (ns_ids_bry == null)		throw parser.Err_row_bgn("ns_itm missing ns_ids", pos);
		if (mode_each) return;

		// mode is <each>; create map with each ns in separate file 
		if	(Bry_.Eq(ns_ids_bry, Ns_file_map__each)) {
			mode_each = true;
			int len = ns_mgr.Ords_len();
			for (int i = 0; i < len; ++i) {
				Xow_ns ns = ns_mgr.Ords_get_at(i);
				int ns_id = ns.Id();
				rslts.Add(new Xob_ns_file_itm(db_file_tid, "ns." + Int_.To_str_pad_bgn_zero(ns_id, 3), Int_.Ary(ns_id)));
			}
			return;
		}
		// mode is <few>; create map with each ns in one file; // DB.FEW: DATE:2016-06-07
		else if	(Bry_.Eq(ns_ids_bry, Ns_file_map__few)) {
			int len = ns_mgr.Ords_len();
			int[] ns_ary_for_few = new int[len];
			for (int i = 0; i < len; ++i) {
				ns_ary_for_few[i] = ns_mgr.Ords_get_at(i).Id();
			}
			rslts.Add(new Xob_ns_file_itm(db_file_tid, String_.Empty, ns_ary_for_few));
			return;
		}

		int[] ns_ids = null;
		if (ns_ids_bry.length == 1 && ns_ids_bry[0] == Byte_ascii.Star) {	// "*"
			int len = ns_mgr.Ords_len();
			ns_ids = new int[len];
			for (int i = 0; i < len; ++i)
				ns_ids[i] = ns_mgr.Ords_get_at(i).Id();
		}
		else
			ns_ids = Int_.Ary_parse(String_.new_u8(ns_ids_bry), ",");
		if (ns_ids.length == 0) throw Err_.new_wo_type("map.invalid.ns_missing", "src", this.Src());
		if (String_.Len_eq_0(name)) {	// no name; auto-generate
			int ns_id_1st = ns_ids[0];	// take 1st ns_id
			name = "ns." + Int_.To_str_pad_bgn_zero(ns_id_1st, 3);	// EX: ns.000
		}
		Xob_ns_file_itm ns_itm = new Xob_ns_file_itm(db_file_tid, name, ns_ids);
		rslts.Add(ns_itm);
		ns_itm.toString();
		ns_ids = null; name = null;
	}
	public Xob_ns_file_itm[] To_ary(byte[] bry) {
		this.Load_by_bry(bry);
		return (Xob_ns_file_itm[])rslts.To_ary(Xob_ns_file_itm.class);
	}
	public static final    byte[] Ns_file_map__few = Bry_.new_a7("few"), Ns_file_map__each = Bry_.new_a7("<each>");
	/*
"" -> no rules; return "default"; generates "text-001" and lumps all ns into it
"*|<id>|3700|2" -> auto-generate per ns
<single-file>
<all>||gzip
<each>||gzip
	*/
}
