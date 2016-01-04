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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
public class Xoh_img_xoimg_data implements Bfr_arg_clearable {
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Pipe);
	public int Val_bgn() {return val_bgn;} private int val_bgn;
	public int Val_end() {return val_end;} private int val_end;
	public boolean Val_dat_exists() {return val_dat_exists;} private boolean val_dat_exists;
	public byte Lnki_type() {return lnki_type;} private byte lnki_type;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h;
	public double Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double Lnki_time() {return lnki_time;} private double lnki_time;
	public int Lnki_page() {return lnki_page;} private int lnki_page;
	public void Clear() {
		val_bgn = val_end = -1;
		val_dat_exists = false;
	}
	public void Set(byte tid, int w, int h, double upright, double time, int page) {
		this.lnki_type = tid;
		this.lnki_w = w;
		this.lnki_h = h;
		this.lnki_upright = upright;
		this.lnki_time = time;
		this.lnki_page = page;
		this.val_dat_exists = true;
	}
	public void Parse(Bry_err_wkr err_wkr, byte[] src, Gfh_tag tag) {
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Bry__name);
		Parse(err_wkr, src, atr.Val_bgn(), atr.Val_end());
	}
	public void Parse(Bry_err_wkr err_wkr, byte[] src, int src_bgn, int src_end) {
		if (src_bgn == -1)
			this.Clear();
		else {				
			rdr.Init_by_wkr(err_wkr, "img.xoimg", src_bgn, src_end);
			this.val_bgn = src_bgn;
			this.val_end = src_end;
			this.lnki_type = (byte)(rdr.Read_byte_to() - Byte_ascii.Num_0);
			this.lnki_w = rdr.Read_int_to();
			this.lnki_h = rdr.Read_int_to();
			this.lnki_upright = rdr.Read_double_to();
			this.lnki_time = rdr.Read_double_to();
			this.lnki_page = rdr.Read_int_to();
			this.val_dat_exists = true;
		}
	}
	public void Bfr_arg__clear() {}
	public boolean Bfr_arg__missing() {return false;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_int_variable(lnki_type).Add_byte_pipe();
		bfr.Add_int_variable(lnki_w).Add_byte_pipe();
		bfr.Add_int_variable(lnki_h).Add_byte_pipe();
		bfr.Add_double(lnki_upright).Add_byte_pipe();
		bfr.Add_double(lnki_time).Add_byte_pipe();
		bfr.Add_int_variable(lnki_page);
	}
	public static final byte[]
	  Bry__name		= Bry_.new_a7("data-xoimg")
	, Bry__html		= Bry_.new_a7("\" data-xoimg=\"")
	;
}
