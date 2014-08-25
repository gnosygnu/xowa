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
public class Hdump_img_itm {
	public Hdump_img_itm(int idx, int view_w, int view_h, byte[] lnki_ttl, byte[] view_src) {
		this.idx = idx;
		this.view_w = view_w;
		this.view_h = view_h;
		this.lnki_ttl = lnki_ttl;
		this.view_src = view_src;
	}
	public int Idx() {return idx;} private int idx;
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public int View_w() {return view_w;} private int view_w;
	public int View_h() {return view_h;} private int view_h;
	public byte[] View_src() {return view_src;} private byte[] view_src;
	@Override public String toString() {
		return String_.Concat_with_str("|", Int_.XtoStr(idx), Int_.XtoStr(view_w), Int_.XtoStr(view_h), String_.new_utf8_(lnki_ttl), String_.new_utf8_(view_src));
	}
	public static final Hdump_img_itm[] Ary_empty = new Hdump_img_itm[0];
}
