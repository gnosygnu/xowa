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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
class Hzip_bfr_mgr {
	private Gfo_usr_dlg usr_dlg; private Bry_bfr_mkr bfr_mkr;
	private List_adp stack = List_adp_.new_();
	public Hzip_bfr_mgr(Gfo_usr_dlg usr_dlg, Bry_bfr_mkr bfr_mkr) {this.usr_dlg = usr_dlg; this.bfr_mkr = bfr_mkr;}		
	public Bry_bfr Add(Hzip_bfr_itm__base itm) {
		stack.Add(itm);
		return bfr_mkr.Get_k004();
	}
	public Bry_bfr Pop(int expd_tid, Bry_bfr cur, byte[] src, int src_len, int pos) {
		Hzip_bfr_itm__base itm = (Hzip_bfr_itm__base)List_adp_.Pop(stack);
		if (expd_tid != itm.Tid()) {usr_dlg.Warn_many("", "", "bfr_mgr tid does not match; expd=~{0} actl=~{1}", expd_tid, itm.Tid());}
		itm.Pop_exec(cur, src, src_len, pos);
		cur.Mkr_rls();
		return itm.Bfr();
	}
}
abstract class Hzip_bfr_itm__base {
	public Hzip_bfr_itm__base Init(Bry_bfr bfr) {this.bfr = bfr; return this;}
	public Bry_bfr Bfr() {return bfr;} protected Bry_bfr bfr;
	public abstract int Tid();
	public abstract void Pop_exec(Bry_bfr cur, byte[] src, int src_len, int pos);
}
class Hzip_bfr_itm__anchor extends Hzip_bfr_itm__base {	// <a href="/wiki/File:Earth_Eastern_Hemisphere.jpg" class="image" title="Photograph of Earth, taken by NASA" xowa_title="Earth Eastern Hemisphere.jpg">
	@Override public int Tid() {return Tid_file;} public static final int Tid_file = 1;
	@Override public void Pop_exec(Bry_bfr cur, byte[] src, int src_len, int pos) {
		byte[] caption_bry = cur.Xto_bry();	// Calc_title(cur.Xto_bry())
		bfr.Add(caption_bry).Add_str("'>");
		bfr.Add_bfr_and_clear(cur);
		bfr.Add_str_a7("</a>");
	}
	public static Hzip_bfr_itm__base new_(Bry_bfr cur, byte[] src, int src_len, int pos) {
		Hzip_bfr_itm__anchor rv = new Hzip_bfr_itm__anchor();
		rv.Init(cur);
		return rv;
	}
}
