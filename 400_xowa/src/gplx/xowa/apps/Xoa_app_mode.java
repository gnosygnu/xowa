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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
public class Xoa_app_mode {
	Xoa_app_mode(int tid) {this.tid = tid;}
	public int Tid() {return tid;} private final    int tid;
	public boolean Tid_is_gui()		{return tid == Tid_gui;}
	public boolean Tid_is_tcp()		{return tid == Tid_tcp;}
	public boolean Tid_is_cmd()		{return tid == Tid_cmd;}
	public boolean Tid_is_http()		{return tid == Tid_http;}
	public boolean Tid_supports_js() {
		switch (tid) {
			case Tid_gui:
			case Tid_tcp:		return true;
			default:			return false;
		}
	}
	public byte[] Name() {
		switch (tid) {
			case Tid_cmd:		return Key_cmd;
			case Tid_gui:		return Key_gui;
			case Tid_http: 		return Key_http;
			case Tid_tcp:		return Key_tcp;
			case Tid_file:		return Key_file;
			default:			return Key_cmd; // see parse
		}
	}
	private static final int Tid_cmd = 1, Tid_gui = 2, Tid_tcp = 3, Tid_http = 4, Tid_file = 5;
	private static final    byte[] Key_cmd = Bry_.new_a7("cmd"), Key_gui = Bry_.new_a7("gui"), Key_tcp = Bry_.new_a7("server"), Key_http = Bry_.new_a7("http_server"), Key_file = Bry_.new_a7("file");
	public static final    Xoa_app_mode Itm_cmd = new Xoa_app_mode(Tid_cmd), Itm_gui = new Xoa_app_mode(Tid_gui), Itm_tcp = new Xoa_app_mode(Tid_tcp), Itm_http = new Xoa_app_mode(Tid_http), Itm_file = new Xoa_app_mode(Tid_file);
	private static final    Hash_adp_bry type_hash = Hash_adp_bry.cs()
	.Add_bry_int(Key_cmd	, Tid_cmd)
	.Add_bry_int(Key_gui	, Tid_gui)
	.Add_bry_int(Key_http	, Tid_http)
	.Add_bry_int(Key_tcp	, Tid_tcp)
	.Add_bry_int(Key_file	, Tid_file)
	;
	public static Xoa_app_mode parse(String s) {
		Object o = type_hash.Get_by(Bry_.new_u8(s)); if (o == null) return Itm_cmd;	// default to cmd as per early behaviour; handles mistaken "--app_mode wrong"
		int tid = ((Int_obj_val)o).Val();
		switch (tid) {
			case Tid_cmd:		return Itm_cmd;
			case Tid_gui:		return Itm_gui;
			case Tid_http:		return Itm_http;
			case Tid_tcp:		return Itm_tcp;
			case Tid_file:		return Itm_file;
			default:			throw Err_.new_unhandled(tid);
		}
	}
}
