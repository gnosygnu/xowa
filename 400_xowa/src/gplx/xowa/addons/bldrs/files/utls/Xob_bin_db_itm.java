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
package gplx.xowa.addons.bldrs.files.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.fsdb.meta.*;
public class Xob_bin_db_itm {
	public Xob_bin_db_itm(int id, Io_url db_url, int ns_id, int pt_id) {this.id = id; this.db_url = db_url; this.ns_id = ns_id; this.pt_id = pt_id;}
	public int Id() {return id;} private int id;
	public int Ns_id() {return ns_id;} private final    int ns_id;
	public int Pt_id() {return pt_id;} private int pt_id;
	public long Db_len() {return db_len;} public void Db_len_(long v) {this.db_len = v;} private long db_len;
	public Io_url Db_url() {return db_url;} public void Db_url_(Io_url v) {db_url = v;} private Io_url db_url;
	public void Set(int id, int pt_id, Io_url db_url) {
		this.id = id; this.pt_id = pt_id; this.db_url = db_url;
	}
	public static String Gen_name_v1(int pt_id) {
		return String_.Format("fsdb.bin.{0}.sqlite3", Int_.To_str_pad_bgn_zero(pt_id, 4));
	}
	public static String Gen_name_v2(String domain_str, int ns_id, int pt_id) {
		String ns_id_str = Int_.To_str_pad_bgn_zero(ns_id, 3);
		String pt_id_str = Int_.To_str_pad_bgn_zero(pt_id, 3);
		return String_.Format("{0}-file-ns.{1}-db.{2}.xowa", domain_str, ns_id_str, pt_id_str);
	}
	public static Xob_bin_db_itm new_v1(Fsm_bin_fil fil) {
		byte[] name = Bry_.new_u8(fil.Url_rel());	// EX: "fsdb.bin.0000.sqlite3"
		int ns_id = 0; // assume v1 dbs are all in main ns
		int pt_id = Bry_.To_int_or(name, 9 , 13, Int_.Min_value);			if (pt_id == Int_.Min_value) throw Err_.new_wo_type("bin_db_itm.parse: invalid pt_id", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		return new Xob_bin_db_itm(fil.Id(), fil.Url(), ns_id, pt_id);
	}
	public static Xob_bin_db_itm new_v2(Fsm_bin_fil fil) {
		byte[] ns_bgn_tkn = Bry_.new_a7("file-ns."), ns_end_tkn = Bry_.new_a7("-db."), pt_end_tkn = Bry_.new_a7(".xowa");
		int ns_bgn_tkn_len = ns_bgn_tkn.length, ns_end_tkn_len = ns_end_tkn.length; 
		byte[] name = Bry_.new_u8(fil.Url_rel());	// EX: en.wikipedia.org-file-ns.000-db.001.xowa
		int ns_bgn = Bry_find_.Find_fwd(name, ns_bgn_tkn, 0);				if (ns_bgn == Bry_find_.Not_found) throw Err_.new_wo_type("bin_db_itm.parse: invalid ns_bgn", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		ns_bgn += ns_bgn_tkn_len;
		int ns_end = Bry_find_.Find_fwd(name, ns_end_tkn, ns_bgn);			if (ns_end == Bry_find_.Not_found) throw Err_.new_wo_type("bin_db_itm.parse: invalid ns_end", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		int pt_bgn = ns_end + ns_end_tkn_len;
		int pt_end = Bry_find_.Find_fwd(name, pt_end_tkn, pt_bgn);			if (pt_end == Bry_find_.Not_found) throw Err_.new_wo_type("bin_db_itm.parse: invalid pt_end", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		int ns_id = Bry_.To_int_or(name, ns_bgn, ns_end, Int_.Min_value);	if (ns_id == Int_.Min_value) throw Err_.new_wo_type("bin_db_itm.parse: invalid ns_id", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		int pt_id = Bry_.To_int_or(name, pt_bgn, pt_end, Int_.Min_value);	if (pt_id == Int_.Min_value) throw Err_.new_wo_type("bin_db_itm.parse: invalid pt_id", "name", fil.Url_rel(), "conn", fil.Conn().Conn_info().Raw());
		return new Xob_bin_db_itm(fil.Id(), fil.Url(), ns_id, pt_id);
	}
}
