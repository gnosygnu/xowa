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
import gplx.langs.htmls.*;
class Bfr_arg__elem__capt implements gplx.core.brys.Bfr_arg_clearable {
	private byte[] capt;
	public Bfr_arg__elem__capt() {
		this.Clear();
	}
	public void Capt_(byte[] v) {this.capt = v;}
	public void Clear() {capt = null;}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return capt == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {		// EX: '\n<li class="gallerycaption">Z</li>'
		if (Bfr_arg__missing()) return;
		bfr.Add_byte_nl();
		bfr.Add(Gfh_tag_.Li_lhs_bgn);			// '<li'
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, Xoh_gly_grp_data.Atr__cls__gallerycaption);	// ' class="gallerycaption"'
		bfr.Add_byte(Byte_ascii.Angle_end);		// '>'
		bfr.Add(capt);
		bfr.Add(Gfh_tag_.Li_rhs);				// '</li>'
	}
}