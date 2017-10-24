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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*;
class Bfr_arg__hatr__xogly implements gplx.core.brys.Bfr_arg_clearable {
	private final    byte[] atr_bgn;
	private int xnde_w, xnde_h, xnde_per_row;
	public Bfr_arg__hatr__xogly() {
		this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(gplx.xowa.xtns.gallery.Gallery_mgr_wtr.Bry__data_xogly);
		this.Clear();
	}
	public void Set_args(int xnde_w, int xnde_h, int xnde_per_row) {
		this.xnde_w = xnde_w; this.xnde_h = xnde_h; this.xnde_per_row = xnde_per_row;
	}
	public void Clear() {xnde_w = xnde_h = xnde_per_row = -1;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return false;} // NOTE: do not check if "xnde_w == -1 && xnde_h == -1 && xnde_per_row == -1" else will fail hzip diff; DATE:2016-07-02
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		bfr.Add_int_variable(xnde_w).Add_byte_pipe();
		bfr.Add_int_variable(xnde_h).Add_byte_pipe();
		bfr.Add_int_variable(xnde_per_row);
		bfr.Add_byte_quote();
	}
}