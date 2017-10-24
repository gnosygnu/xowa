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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*; import gplx.langs.jsons.*; import gplx.langs.phps.*; import gplx.langs.gfs.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.parsers.*;
interface Json_itm_wkr {
	void Read_kv_sub(byte[] key, byte[] val);
}
abstract class Json_itm_wkr__base implements Json_itm_wkr {
	private Json_parser json_parser = new Json_parser();
	private Php_text_itm_parser php_quote_parser = new Php_text_itm_parser().Quote_is_single_(true);	// assume values are equivalent to php single quote; DATE:2014-08-06
	public void Exec(byte[] src) {
		List_adp tmp_list = List_adp_.New(); Byte_obj_ref tmp_result = Byte_obj_ref.zero_(); Bry_bfr tmp_bfr = Bry_bfr_.Reset(16); 
		Json_doc jdoc = json_parser.Parse(src);
		this.Exec_bgn();
		Json_nde root = jdoc.Root_nde();
		int subs_len = root.Len();
		for (int i = 0; i < subs_len; ++i) {
			Json_itm itm = root.Get_at(i);
			switch (itm.Tid()) {
				case Json_itm_.Tid__kv:
					Json_kv kv = (Json_kv)itm;
					if (kv.Key().Data_eq(Name_metadata)) continue;		// ignore @metadata node
					byte[] kv_key = kv.Key().Data_bry();
					byte[] kv_val = kv.Val().Data_bry();						
					kv_val = php_quote_parser.Parse_as_bry(tmp_list, kv_val, tmp_result, tmp_bfr);
					Read_kv_sub(kv_key, kv_val);
					break;
			}
		}
		this.Exec_end();
	}
	@gplx.Virtual public void Exec_bgn() {}
	@gplx.Virtual public void Exec_end() {}
	public abstract void Read_kv_sub(byte[] key, byte[] val);
	private static final    byte[] Name_metadata = Bry_.new_a7("@metadata");
}
class Json_itm_wkr__gfs extends Json_itm_wkr__base {
	private Xoa_gfs_bldr gfs_bldr = new Xoa_gfs_bldr();
	private Xol_csv_parser csv_parser = Xol_csv_parser.Instance;
	private Bry_bfr bfr;
	public byte[] Xto_bry() {return gfs_bldr.Xto_bry();}
	@Override public void Exec_bgn() {
		bfr = gfs_bldr.Bfr();
		gfs_bldr.Add_proc_init_many("this", "messages", "load_text").Add_paren_bgn().Add_nl();
		gfs_bldr.Add_quote_xtn_bgn();
	}
	@Override public void Exec_end() {
		gfs_bldr.Add_quote_xtn_end().Add_paren_end().Add_term_nl();
	}
	@Override public void Read_kv_sub(byte[] key, byte[] val) {
		csv_parser.Save(bfr, key);					// key
		bfr.Add_byte_pipe();						// |
		csv_parser.Save(bfr, val);					// val
		bfr.Add_byte_nl();							// \n
	}
}
class Json_itm_wkr__msgs extends Json_itm_wkr__base {
	private Xol_msg_mgr msg_mgr; private boolean dirty;
	public void Ctor(boolean dirty, Xol_msg_mgr msg_mgr) {this.dirty = dirty; this.msg_mgr = msg_mgr;}
	@Override public void Read_kv_sub(byte[] key, byte[] val) {
		Xol_msg_itm msg_itm = msg_mgr.Itm_by_key_or_new(key);
		Xol_msg_itm_.update_val_(msg_itm, val);
		if (dirty)	// bldr needs to dirty message to generate lang.gfs; DATE:2014-08-05
			msg_itm.Dirty_(true);
	}
}
