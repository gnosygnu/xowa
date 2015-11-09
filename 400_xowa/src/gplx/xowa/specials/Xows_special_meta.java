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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
public class Xows_special_meta {
	public Xows_special_meta(int src, int uid, String key_str) {
		this.src = src; this.uid = uid; this.key_str = key_str;
		this.key_bry = Bry_.new_u8(key_str);
		this.ttl_str = "Special:" + key_str;	// canonical name
		this.ttl_bry = Bry_.new_u8(ttl_str);
	}
	public int Src() {return src;} private final int src;
	public int Uid() {return uid;} private final int uid;
	public String Key_str() {return key_str;} private final String key_str;
	public byte[] Key_bry() {return key_bry;} private final byte[] key_bry;
	public String Ttl_str() {return ttl_str;} private final String ttl_str;
	public byte[] Ttl_bry() {return ttl_bry;} private final byte[] ttl_bry;
	public boolean Match_ttl(Xoa_ttl ttl) {
		return ttl.Ns().Id_is_special() && Bry_.Eq(ttl.Root_txt(), key_bry);
	}
}
