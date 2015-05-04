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
public class Xoa_app_type {
	Xoa_app_type(int uid) {this.uid = uid;}
	public int Uid() {return uid;} private final int uid;
	public boolean Uid_is_gui()	{return uid == Uid_gui;}
	public boolean Uid_is_tcp()	{return uid == Uid_tcp;}
	public boolean Uid_is_http()	{return uid == Uid_http;}
	private static final int Uid_cmd = 1, Uid_gui = 2, Uid_tcp = 3, Uid_http = 4;
	private static final String Key_cmd = "cmd", Key_gui = "gui", Key_tcp = "server", Key_http = "http_server";
	public static final Xoa_app_type Itm_cmd = new Xoa_app_type(Uid_cmd), Itm_gui = new Xoa_app_type(Uid_gui), Itm_tcp = new Xoa_app_type(Uid_tcp), Itm_http = new Xoa_app_type(Uid_http);
	public static Xoa_app_type parse(String s) {
		if		(String_.Eq(s, Key_cmd))		return Itm_cmd;
		else if	(String_.Eq(s, Key_gui))		return Itm_gui;
		else if	(String_.Eq(s, Key_tcp))		return Itm_tcp;
		else if	(String_.Eq(s, Key_http))		return Itm_http;
		else									return Itm_cmd;	// default to cmd as per early behaviour; handles mistaken "--app_mode wrong"
	}
}
