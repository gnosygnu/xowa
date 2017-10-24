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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hzip_bfr extends Bry_bfr { 	private final    Xoh_hzip_int hzint = new Xoh_hzip_int();
	private final    byte stop_byte;
	public Xoh_hzip_bfr(int bfr_max, boolean mode_is_b256, byte stop_byte) {
		this.Init(bfr_max);
		this.stop_byte = stop_byte;
		Mode_is_b256_(mode_is_b256);
	}
	public Xoh_hzip_bfr Mode_is_b256_(boolean mode_is_b256) {
		hzint.Mode_is_b256_(mode_is_b256);
		return this;
	}
	public Xoh_hzip_bfr Add_hzip_bry(byte[] bry)					{Add(bry);					Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_mid(byte[] src, int bgn, int end)	{Add_mid(src, bgn, end);	Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_double(double val)					{this.Add_double(val);		Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_int(int reqd, int val) {
		hzint.Encode(reqd, this, val);
		return this;
	}
	public static Xoh_hzip_bfr New_txt(int bfr_max) {return new Xoh_hzip_bfr(bfr_max, Bool_.N, gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_.Escape);}
}
