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
package gplx.xowa.htmls.core.wkrs.imgs;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.langs.htmls.*;
import gplx.xowa.xtns.pagebanners.*;
public class Bfr_arg__pgbnr implements BryBfrArgClearable {
	private Pgbnr_itm pgbnr;
	public Bfr_arg__pgbnr Set(Pgbnr_itm pgbnr) {
		this.pgbnr = pgbnr;
		return this;
	}
	public void BfrArgClear() {pgbnr = null;}
	public boolean BfrArgIsMissing() {return pgbnr == null;}
	public void AddToBfr(BryWtr bfr) {
		if (BfrArgIsMissing()) return;
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__srcset, pgbnr.Srcset());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_x, pgbnr.Data_pos_x());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_y, pgbnr.Data_pos_y());
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__style, pgbnr.Style());
	}
}
