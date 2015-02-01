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
package gplx.xowa.hdumps.srls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.primitives.*; import gplx.xowa.hdumps.core.*;
import gplx.xowa2.gui.*;
public class Hpg_srl_itm_ {
	public static final byte	// SERIALIZED
	  Tid_body					= 0
	, Tid_html_module			= 1
	, Tid_display_ttl			= 2
	, Tid_content_sub			= 3
	, Tid_sidebar_div			= 4
	;
	public static final Hpg_srl_itm[] Itms = new Hpg_srl_itm[]		// NOTE: ary_idx must match tid above
	{ new Hpg_srl_itm__body()
	, new Hpg_srl_itm__html_module()
	, new Hpg_srl_itm__display_ttl()
	, new Hpg_srl_itm__content_sub()
	, new Hpg_srl_itm__sidebar_div()
	};
	private static final int Base_255_int = 255;
	private static final byte Base_255_byte = (byte)255;
	public static byte[] Save_bin_int_abrv(int val_int) {
		Bry_bfr bfr = Bry_bfr.reset_(10);
		Save_bin_int_abrv(bfr, val_int);
		return bfr.Xto_bry_and_clear();
	}
	public static void Save_bin_int_abrv(Bry_bfr bfr, int val_int) {	// save int in binary little endian form; range from -2,080,766,977 to 2,147,483,648; 255^4 or 4,228,250,625
		if (val_int == 0) {bfr.Add_byte(Byte_ascii.Nil); return;}
		long val = val_int;
		if (val < 0) val = Int_.MaxValue + -val;
		int count = 0;
		while (val > 0) {
			byte mod = (byte)(val % Base_255_int);
			int adj = 0;
			if (mod == 0) {mod = Base_255_byte; adj = 1;}	// if 0, then set byte to 255; also set adj to 1 to properly decrement value
			bfr.Add_byte(mod);
			++count;
			val = (val - adj) / Base_255_int;
		}
		if (count < 4) bfr.Add_byte(Byte_ascii.Nil);
	}
	public static int Load_bin_int_abrv(byte[] bry, int bry_len, int bgn, Int_obj_ref count_ref) {
		int end = bgn + 4;	// read no more than 4 bytes
		int count = 0;
		long rv = 0; int mult = 1;
		for (int i = bgn; i < end; ++i) {
			if (i == bry_len) break;				
			else {
				++count;
				int b = bry[i] & 0xFF; // PATCH.JAVA:need to convert to unsigned byte
				if (b == 0) break;
				rv += (b * mult);
				mult *= Base_255_int;
			}
		}
		if (rv > Int_.MaxValue) {
			rv -= Int_.MaxValue;
			rv *= -1;
		}
		count_ref.Val_(count);
		return (int)rv;
	}
}
abstract class Hpg_srl_itm__blob_base implements Hpg_srl_itm {
	public abstract byte Tid();
	public void Save_tid_n_() {save_tid = false;} private boolean save_tid = true;
	public void Save(Xog_page hpg, Bry_bfr bfr) {
		byte[] bry = Save_itm(hpg); if (bry == null) return;
		int len = bry.length; if (len == 0) return;
		if (save_tid)	// body won't save tid
			bfr.Add_byte(this.Tid());
		Hpg_srl_itm_.Save_bin_int_abrv(bfr, len);
		bfr.Add(bry);
	}
	public int Load(Xog_page hpg, byte[] bry, int bry_len, int itm_bgn, Int_obj_ref count_ref) {
		int itm_len = Hpg_srl_itm_.Load_bin_int_abrv(bry, bry_len, itm_bgn, count_ref); if (itm_len == -1) throw Err_.new_("bry_itm has invalid len: page={0} tid={1}", hpg.Page_id(), this.Tid());			
		int data_bgn = itm_bgn + count_ref.Val();
		if (itm_len == 0) return data_bgn;
		int data_end = data_bgn + itm_len;
		byte[] itm_data = Bry_.Mid(bry, data_bgn, data_end);
		this.Load_itm(hpg, itm_data);
		return data_end - itm_bgn;
	}
	public abstract void Load_itm(Xog_page hpg, byte[] data);
	public abstract byte[] Save_itm(Xog_page hpg);
}
class Hpg_srl_itm__body extends Hpg_srl_itm__blob_base {
	public Hpg_srl_itm__body() {this.Save_tid_n_();}
	@Override public byte Tid() {return Hpg_srl_itm_.Tid_body;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Page_body();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Page_body_(data);}
}
class Hpg_srl_itm__display_ttl extends Hpg_srl_itm__blob_base {
	@Override public byte Tid() {return Hpg_srl_itm_.Tid_display_ttl;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Display_ttl();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Display_ttl_(data);}
}
class Hpg_srl_itm__content_sub extends Hpg_srl_itm__blob_base {
	@Override public byte Tid() {return Hpg_srl_itm_.Tid_content_sub;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Content_sub();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Content_sub_(data);}
}
class Hpg_srl_itm__sidebar_div extends Hpg_srl_itm__blob_base {
	@Override public byte Tid() {return Hpg_srl_itm_.Tid_sidebar_div;}
	@Override public byte[] Save_itm(Xog_page hpg)				{return hpg.Sidebar_div();}
	@Override public void Load_itm(Xog_page hpg, byte[] data)	{hpg.Sidebar_div_(data);}
}
class Hpg_srl_itm__html_module implements Hpg_srl_itm {
	public byte Tid() {return Hpg_srl_itm_.Tid_html_module;}
	public int Load(Xog_page hpg, byte[] bry, int bry_len, int itm_bgn, Int_obj_ref count_ref) {
		itm_bgn += 2; // skip bin_int_abrv of [1, 0]
		byte flag = bry[itm_bgn];
		hpg.Module_mgr().Init(Enm_.Has_byte(flag, Tid_math), Enm_.Has_byte(flag, Tid_imap), Enm_.Has_byte(flag, Tid_packed), Enm_.Has_byte(flag, Tid_hiero));
		return 3;
	}
	public void Save(Xog_page hpg, Bry_bfr bfr) {
		byte flag = Calc_flag(hpg);
		if (flag == 0) return;
		bfr.Add_byte(this.Tid());
		Hpg_srl_itm_.Save_bin_int_abrv(bfr, 1);
		bfr.Add_byte(flag);
	}
	private static byte Calc_flag(Xog_page hpg) {
		Hdump_module_mgr module_mgr = hpg.Module_mgr();
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
