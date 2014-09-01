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
package gplx.xowa.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.xowa.hdumps.dbs.*;
public abstract class Hdump_data_img__base implements XtoStrAble {
	public Hdump_data_img__base Init_by_base(int uid, int view_w, int view_h, byte[] lnki_ttl, byte[] view_src) {
		this.uid = uid;
		this.view_w = view_w;
		this.view_h = view_h;
		this.lnki_ttl = lnki_ttl;
		this.view_src = view_src;
		return this;
	}
	public abstract int Tid();
	public int Uid() {return uid;} private int uid;
	public int View_w() {return view_w;} private int view_w;
	public int View_h() {return view_h;} private int view_h;
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[] View_src() {return view_src;} private byte[] view_src;
	public String XtoStr() {
		return String_.Concat_with_str("|", Int_.XtoStr(this.Tid()), Int_.XtoStr(uid), Int_.XtoStr(view_w), Int_.XtoStr(view_h), String_.new_utf8_(lnki_ttl), String_.new_utf8_(view_src));
	}
	public void Write(Bry_bfr bfr) {
		bfr	.Add_int_variable(Hdump_data_tid.Tid_img).Add_byte_pipe()
			.Add_int_variable(this.Tid()).Add_byte_pipe()
			.Add_int_variable(uid).Add_byte_pipe()
			.Add_int_variable(view_w).Add_byte_pipe()
			.Add_int_variable(view_h).Add_byte_pipe()
			.Add(lnki_ttl).Add_byte_pipe()
			.Add(view_src).Add_byte_pipe()
			;
		Write_hook(bfr);
		bfr.Add_byte_nl();
	}
	@gplx.Virtual public void Write_hook(Bry_bfr bfr) {}
	public static final Hdump_data_img__base[] Ary_empty = new Hdump_data_img__base[0];
	public static final int Tid_basic = 0, Tid_gallery = 1;
}
