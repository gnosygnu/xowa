/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.files.*;
public class Xoh_img_xoimg_data implements Bfr_arg_clearable {
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Pipe);
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
		lnki_type = Byte_.Zero;
		lnki_w = lnki_h = lnki_page = 0;
		lnki_upright = lnki_time = 0;
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
	public Xoh_img_xoimg_data Clone() {
		Xoh_img_xoimg_data rv = new Xoh_img_xoimg_data();
		rv.val_bgn = this.val_bgn;
		rv.val_end = this.val_end;
		rv.val_dat_exists = this.val_dat_exists;
		rv.lnki_type = this.lnki_type;
		rv.lnki_w = this.lnki_w;
		rv.lnki_h = this.lnki_h;
		rv.lnki_upright = this.lnki_upright;
		rv.lnki_time = this.lnki_time;
		rv.lnki_page = this.lnki_page;
		return rv;
	}
	public void Parse(Bry_err_wkr err_wkr, byte[] src, Gfh_tag tag) {
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Bry__data_xowa_image);
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
	public static final    byte[]
	  Bry__data_xowa_image			= Bry_.new_a7("data-xoimg")
	, Bry__data_xowa_title			= Bry_.new_a7("data-xowa-title")
	, Bry__data_xowa_image__full	= Bry_.new_a7("1|-1|-1|-1|-1|-1")	// for pagebanner
	;
	public static Xoh_img_xoimg_data New__pgbnr() {
		Xoh_img_xoimg_data rv = new Xoh_img_xoimg_data();
		rv.Set(Xop_lnki_type.Id_none, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null, Xop_lnki_tkn.Upright_null, Xof_lnki_time.Null, Xof_lnki_page.Null);
		return rv;
	}
}
