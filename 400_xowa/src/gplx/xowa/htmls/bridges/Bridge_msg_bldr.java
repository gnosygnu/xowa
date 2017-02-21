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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.jsons.*;
public class Bridge_msg_bldr {
	private final    Json_wtr wtr = new Json_wtr();
	private boolean rslt_pass; private String rslt_msg;
	private String notify_text; private String notify_status;
	private final    Gfo_tree_list data_root = new Gfo_tree_list("data");
	public Bridge_msg_bldr() {
		wtr.Opt_ws_(Bool_.N);
		this.Clear();
	}
	public Bridge_msg_bldr Opt_quote_byte_apos_() {wtr.Opt_quote_byte_(Byte_ascii.Apos); return this;}
	public Bridge_msg_bldr Rslt_pass_y_()			{return Rslt_pass_(Bool_.Y);}
	public Bridge_msg_bldr Rslt_pass_n_(String v)	{Rslt_msg_(v); return Rslt_pass_(Bool_.N);}
	private Bridge_msg_bldr Rslt_pass_(boolean v)	{synchronized(wtr){this.rslt_pass = v;} return this;}
	private Bridge_msg_bldr Rslt_msg_(String v)	{synchronized(wtr){this.rslt_msg = v;} return this;}
	public Bridge_msg_bldr Notify_hint_(String v)	{synchronized(wtr){this.notify_hint = v;} return this;} private String notify_hint;
	public Bridge_msg_bldr Notify_pass_(String v){synchronized(wtr){this.notify_text = v; this.notify_status = "success";} return this;}
	public Bridge_msg_bldr Notify_fail_(String v){synchronized(wtr){this.notify_text = v; this.notify_status = "error"; this.rslt_pass = false;} return this;}
	public Bridge_msg_bldr Data(String key, boolean val)			{return Data_obj(key, val, Type_adp_.Tid__bool);}
	public Bridge_msg_bldr Data(String key, int val)			{return Data_obj(key, val, Type_adp_.Tid__int);}
	public Bridge_msg_bldr Data(String key, String val)			{return Data_obj(key, val, Type_adp_.Tid__str);}
	public Bridge_msg_bldr Data(String key, byte[] val)			{return Data_obj(key, val, Type_adp_.Tid__bry);}
	private Bridge_msg_bldr Data_obj(String key, Object val, int val_tid) {
		synchronized (wtr) {data_root.Add_data(key, val, val_tid);}
		return this;
	}
	public byte[] To_json_bry() {synchronized(wtr){ Bld_json(); return wtr.To_bry_and_clear();}}
	public String To_json_str() {synchronized(wtr){ Bld_json(); return wtr.To_str_and_clear();}}
	public String To_json_str__empty() {return "{}";}
	public Bridge_msg_bldr Clear() {
		synchronized (wtr) {
			rslt_pass = true;	// by default, set all msgs to pass==true
			rslt_msg = null;
			notify_hint = null;
			notify_text = null;
			notify_status = null;
			data_root.Clear();
		}
		return this;
	}
	private void Bld_json() {
		wtr.Clear();
		wtr.Doc_nde_bgn();
		wtr.Nde_bgn(Key_rslt);
		wtr.Kv_bool(Key_rslt_pass, rslt_pass);
		if (rslt_msg != null) wtr.Kv_str(Key_rslt_msg, rslt_msg);
		wtr.Nde_end();
		if (notify_text != null) {
			wtr.Nde_bgn(Key_notify);
			wtr.Kv_str(Key_notify_text, notify_text);
			wtr.Kv_str(Key_notify_status, notify_status);
			if (notify_hint != null)
				wtr.Kv_str(Key_notify_hint, notify_hint);
			wtr.Nde_end();
		}
		Bld_json_for_hash(wtr, data_root);
		wtr.Doc_nde_end();
	}
	private void Bld_json_for_hash(Json_wtr wtr, Gfo_tree_list hash) {
		int len = hash.Len(); if (len == 0) return;
		wtr.Nde_bgn(hash.Key());
		for (int i = 0; i < len; ++i) {
			Gfo_tree_itm itm = hash.Get_at(i);
			if (itm.Tid() == Gfo_tree_itm_.Tid_data) {
				Gfo_tree_data sub_kv = (Gfo_tree_data)itm;
				String key = sub_kv.Key(); Object val = sub_kv.Val();
				switch (sub_kv.Val_tid()) {
					case Type_adp_.Tid__bool:	wtr.Kv_bool(key, Bool_.Cast(val)); break;
					case Type_adp_.Tid__int:		wtr.Kv_int(key, Int_.cast(val)); break;
					case Type_adp_.Tid__bry:		wtr.Kv_bry(key, (byte[])val); break;
					default:					wtr.Kv_str(key, Object_.Xto_str_strict_or_null_mark(val)); break;
				}
			}
			else {
				Gfo_tree_list sub_hash = (Gfo_tree_list)itm;
				Bld_json_for_hash(wtr, sub_hash);
			}
		}
		wtr.Nde_end();
	}
	private static final    byte[]
	  Key_rslt = Bry_.new_a7("rslt"), Key_rslt_pass = Bry_.new_a7("pass"), Key_rslt_msg = Bry_.new_a7("msg")
	, Key_notify = Bry_.new_a7("notify"), Key_notify_text = Bry_.new_a7("text"), Key_notify_status = Bry_.new_a7("status"), Key_notify_hint = Bry_.new_a7("hint")
	;
}
