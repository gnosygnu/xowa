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
package gplx.xowa.html.hdumps.data.srl; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*; import gplx.xowa.html.hdumps.data.*;
import gplx.core.primitives.*; import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hzips.*; import gplx.xowa.html.hdumps.pages.*;
import gplx.xowa.gui.*;
class Xohd_page_srl_itm__body extends Xohd_page_srl_itm__base {
	public Xohd_page_srl_itm__body() {this.Save_tid_n_();}
	@Override public byte Tid() {return Xohd_page_srl_itm_.Tid_body;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Page_body();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Page_body_(data);}
}
class Xohd_page_srl_itm__display_ttl extends Xohd_page_srl_itm__base {
	@Override public byte Tid() {return Xohd_page_srl_itm_.Tid_display_ttl;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Display_ttl();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Display_ttl_(data);}
}
class Xohd_page_srl_itm__content_sub extends Xohd_page_srl_itm__base {
	@Override public byte Tid() {return Xohd_page_srl_itm_.Tid_content_sub;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Content_sub();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Content_sub_(data);}
}
class Xohd_page_srl_itm__sidebar_div extends Xohd_page_srl_itm__base {
	@Override public byte Tid() {return Xohd_page_srl_itm_.Tid_sidebar_div;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Sidebar_div();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Sidebar_div_(data);}
}
class Xohd_page_srl_itm__html_module implements Xohd_page_srl_itm {
	public byte Tid() {return Xohd_page_srl_itm_.Tid_html_module;}
	public int Load(Xog_page hpg, byte[] bry, int bry_len, int itm_bgn, Int_obj_ref count_ref) {
		itm_bgn += 2; // skip bin_int_abrv of [1, 0]
		byte flag = bry[itm_bgn];
		hpg.Head_mgr().Init(Enm_.Has_byte(flag, Tid_math), Enm_.Has_byte(flag, Tid_imap), Enm_.Has_byte(flag, Tid_packed), Enm_.Has_byte(flag, Tid_hiero));
		return 3;
	}
	public void Save(Xog_page hpg, Bry_bfr bfr) {
		byte flag = Calc_flag(hpg);
		if (flag == 0) return;
		bfr.Add_byte(this.Tid());
		Xow_hzip_int_.Save_bin_int_abrv(bfr, 1);
		bfr.Add_byte(flag);
	}
	private static byte Calc_flag(Xog_page hpg) {
		Xopg_module_mgr module_mgr = hpg.Head_mgr();
		return Calc_flag(module_mgr.Math_exists(), module_mgr.Imap_exists(), module_mgr.Gallery_packed_exists(), module_mgr.Hiero_exists());
	}
	public static byte Calc_flag(boolean math, boolean imap, boolean packed, boolean hiero) {
		byte rv = 0;
		if (math)			rv = Enm_.Add_byte(rv, Tid_math);
		if (imap)			rv = Enm_.Add_byte(rv, Tid_imap);
		if (packed)			rv = Enm_.Add_byte(rv, Tid_packed);
		if (hiero)			rv = Enm_.Add_byte(rv, Tid_hiero);
		return rv;
	}
	private static final byte		// SERIALIZED; only supports 8 different types
	  Tid_math		= 1
	, Tid_imap		= 2
	, Tid_packed	= 4
	, Tid_hiero		= 8
	;
}
