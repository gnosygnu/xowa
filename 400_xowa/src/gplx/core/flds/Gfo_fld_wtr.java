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
package gplx.core.flds;
import gplx.core.ios.Io_url_gen;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_fld_wtr extends Gfo_fld_base {
	public BryWtr Bfr() {return bfr;} public Gfo_fld_wtr Bfr_(BryWtr v) {bfr = v; return this;} BryWtr bfr;
	public Gfo_fld_wtr() {this.bfr = BryWtr.New();}
	public Gfo_fld_wtr Write_byte_fld(byte v)								{bfr.AddByte(v);								bfr.AddByte(fld_dlm); return this;}
	public Gfo_fld_wtr Write_bry_escape_fld(byte[] val)						{Write_bry_escape(val, 0, val.length);			bfr.AddByte(fld_dlm); return this;}
	public Gfo_fld_wtr Write_dlm_row()										{												bfr.AddByte(row_dlm); return this;}
	Gfo_fld_wtr Write_bry_escape(byte[] val, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = val[i];
			byte escape_val = encode_regy[b & 0xFF];	// PATCH.JAVA:need to convert to unsigned byte
			if (escape_val == AsciiByte.Null) 	bfr.AddByte(b);
			else 								{bfr.AddByte(escape_dlm); bfr.AddByte(escape_val);}
		}
		return this;
	}
	public Gfo_fld_wtr Rls() {bfr.Rls(); return this;}

	public Io_url_gen Fil_gen() {return fil_gen;} public Gfo_fld_wtr Fil_gen_(Io_url_gen v) {fil_gen = v; return this;} Io_url_gen fil_gen;
	public int Bfr_max() {return bfr_max;} public Gfo_fld_wtr Bfr_max_(int v) {bfr_max = v; return this;} private int bfr_max = IoConsts.LenMB;
	public boolean Flush_needed(int v) {return bfr.Len() + v > bfr_max;}
	public void Flush() {
		if (Fil_gen().Cur_url() == null) fil_gen.Nxt_url();
		Io_mgr.Instance.AppendFilBfr(fil_gen.Cur_url(), bfr);
	}
	public void Flush_nxt() {Flush(); fil_gen.Nxt_url();}
	public Gfo_fld_wtr Ctor_xdat() {return (Gfo_fld_wtr)super.Ctor_xdat_base();}
	public static Gfo_fld_wtr xowa_() {return new Gfo_fld_wtr().Ctor_xdat();}
}
