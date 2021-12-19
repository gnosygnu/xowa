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
package gplx.xowa.htmls.core.wkrs.glys;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.langs.htmls.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
class Bfr_arg__elem__capt implements BryBfrArgClearable {
	private byte[] capt;
	public Bfr_arg__elem__capt() {
		this.Clear();
	}
	public void Capt_(byte[] v) {this.capt = v;}
	public void Clear() {capt = null;}
	public void BfrArgClear() {this.Clear();}
	public boolean BfrArgIsMissing() {return capt == null;}
	public void AddToBfr(BryWtr bfr) {		// EX: '\n<li class="gallerycaption">Z</li>'
		if (BfrArgIsMissing()) return;
		bfr.AddByteNl();
		bfr.Add(Gfh_tag_.Li_lhs_bgn);			// '<li'
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, Xoh_gly_grp_data.Atr__cls__gallerycaption);	// ' class="gallerycaption"'
		bfr.AddByte(AsciiByte.AngleEnd);		// '>'
		bfr.Add(capt);
		bfr.Add(Gfh_tag_.Li_rhs);				// '</li>'
	}
}