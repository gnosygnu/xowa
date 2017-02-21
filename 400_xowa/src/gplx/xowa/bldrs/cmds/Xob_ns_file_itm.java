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
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.*;
public class Xob_ns_file_itm {
	public Xob_ns_file_itm(byte db_file_tid, String file_name, int[] ns_ids) {
		this.db_file_tid = db_file_tid; this.file_name = file_name; this.ns_ids = ns_ids;
		this.nth_db_id = Nth_db_id_null; this.nth_db_idx = 1;			
	}
	public byte		Db_file_tid() {return db_file_tid;} private final    byte db_file_tid;
	public String	File_name() {return file_name;} private final    String file_name;
	public int[]	Ns_ids() {return ns_ids;} private final    int[] ns_ids;
	public int		Nth_db_id() {return nth_db_id;} public void Nth_db_id_(int v) {nth_db_id = v;} private int nth_db_id;
	public int		Nth_db_idx() {return nth_db_idx;} private int nth_db_idx;
	public String Make_file_name() {								// EX: en.wikipedia.org-text-ns.000-001.xowa
		String rv = String_.Format("-{0}{1}{2}.xowa"				// EX: -text-ns.000-db.001.xowa
			, Xow_db_file_.To_key(db_file_tid)						// text
			, String_.Len_eq_0(file_name) ? "" : "-" + file_name	// if empty, don't add "ns.000" segment; produces en.wikipedia.org-text-001.xowa
			, nth_db_idx == 1 ? "" : "-db." + Int_.To_str_pad_bgn_zero(nth_db_idx, 3)			// "-db.001"
			);
		++nth_db_idx;
		return rv;
	}
	public static final int Nth_db_id_null = -1;

	public static void Init_ns_bldr_data(byte db_file_tid, Xow_ns_mgr ns_mgr, byte[] ns_file_map) {
		int ns_len = ns_mgr.Ords_len();
		Xob_ns_file_itm ns_file_itm_default = new Xob_ns_file_itm(db_file_tid, "", null);
		for (int i = 0; i < ns_len; ++i) {
			Xow_ns ns = ns_mgr.Ords_get_at(i);
			ns.Bldr_data_(ns_file_itm_default);
		}
		Xob_ns_file_itm_parser ns_itm_parser = new Xob_ns_file_itm_parser();
		ns_itm_parser.Ctor(db_file_tid, ns_mgr);
		Xob_ns_file_itm[] ns_itm_ary = ns_itm_parser.To_ary(ns_file_map);
		int ns_itm_ary_len = ns_itm_ary.length;
		for (int i = 0; i < ns_itm_ary_len; ++i) {
			Xob_ns_file_itm itm = ns_itm_ary[i];
			int[] ns_ids = itm.Ns_ids();
			int ns_ids_len = ns_ids.length;
			for (int j = 0; j < ns_ids_len; j++) {
				int ns_id = ns_ids[j];
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id); if (ns == null) continue; // some dumps may not have ns; for example, pre-2013 dumps won't have Module (828)
				ns.Bldr_data_(itm);
			}
		}
	}
}
