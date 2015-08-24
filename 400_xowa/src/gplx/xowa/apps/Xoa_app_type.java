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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
public class Xoa_app_type {
	Xoa_app_type(int uid) {this.uid = uid;}
	public int Uid() {return uid;} private final int uid;
	public boolean Uid_is_gui()		{return uid == Uid_gui;}
	public boolean Uid_is_tcp()		{return uid == Uid_tcp;}
	public boolean Uid_is_http()		{return uid == Uid_http;}
	public boolean Uid_supports_js() {
		switch (uid) {
			case Uid_gui:
			case Uid_tcp:		return true;
			default:			return false;
		}
	}
	public byte[] Name() {
		switch (uid) {
			case Uid_cmd:		return Key_cmd;
			case Uid_gui:		return Key_gui;
			case Uid_http: 		return Key_http;
			case Uid_tcp:		return Key_tcp;
			case Uid_file:		return Key_file;
			default:			return Key_cmd; // see parse
		}
	}
	private static final int Uid_cmd = 1, Uid_gui = 2, Uid_tcp = 3, Uid_http = 4, Uid_file = 5;
	private static final byte[] Key_cmd = Bry_.new_a7("cmd"), Key_gui = Bry_.new_a7("gui"), Key_tcp = Bry_.new_a7("server"), Key_http = Bry_.new_a7("http_server"), Key_file = Bry_.new_a7("file");
	private static final Hash_adp_bry type_hash = Hash_adp_bry.cs()
	.Add_bry_int(Key_cmd	, Uid_cmd)
	.Add_bry_int(Key_gui	, Uid_gui)
	.Add_bry_int(Key_http	, Uid_http)
	.Add_bry_int(Key_tcp	, Uid_tcp)
	.Add_bry_int(Key_file	, Uid_file)
	;
	public static final Xoa_app_type Itm_cmd = new Xoa_app_type(Uid_cmd), Itm_gui = new Xoa_app_type(Uid_gui), Itm_tcp = new Xoa_app_type(Uid_tcp), Itm_http = new Xoa_app_type(Uid_http), Itm_file = new Xoa_app_type(Uid_file);
	public static Xoa_app_type parse(String s) {
		Object o = type_hash.Get_by(Bry_.new_u8(s)); if (o == null) return Itm_cmd;	// default to cmd as per early behaviour; handles mistaken "--app_mode wrong"
		int uid = ((Int_obj_val)o).Val();
		switch (uid) {
			case Uid_cmd:		return Itm_cmd;
			case Uid_gui:		return Itm_gui;
			case Uid_http:		return Itm_http;
			case Uid_tcp:		return Itm_tcp;
			case Uid_file:		return Itm_file;
			default:			throw Err_.new_unhandled(uid);
		}
	}
}
