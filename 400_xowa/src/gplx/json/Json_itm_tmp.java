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
package gplx.json; import gplx.*;
public class Json_itm_tmp implements Json_itm {
	public Json_itm_tmp(byte tid, String data) {this.tid = tid; this.data = data;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Data_bry() {return Bry_.new_u8(Object_.Xto_str_strict_or_empty(data));}
	public int Src_bgn() {return -1;}
	public int Src_end() {return -1;}
	public Object Data() {return data;} private String data;
	public void Print_as_json(Bry_bfr bfr, int depth) {bfr.Add_str(data);}
	public boolean Data_eq(byte[] comp) {return false;}
	public void Clear() {}
	public static Json_itm new_str_(String v)	{return new Json_itm_tmp(Json_itm_.Tid_string, "\"" + v + "\"");}
	public static Json_itm new_int_(int v)		{return new Json_itm_tmp(Json_itm_.Tid_int, Int_.Xto_str(v));}
}
