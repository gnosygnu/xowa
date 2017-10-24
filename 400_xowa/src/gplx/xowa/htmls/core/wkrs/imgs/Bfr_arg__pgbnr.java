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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*;
import gplx.langs.htmls.*;
import gplx.xowa.xtns.pagebanners.*;
public class Bfr_arg__pgbnr implements Bfr_arg_clearable {
	private Pgbnr_itm pgbnr;
	public Bfr_arg__pgbnr Set(Pgbnr_itm pgbnr) {
		this.pgbnr = pgbnr;
		return this;
	}
	public void Bfr_arg__clear() {pgbnr = null;}
	public boolean Bfr_arg__missing() {return pgbnr == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__srcset, pgbnr.Srcset());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_x, pgbnr.Data_pos_x());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_y, pgbnr.Data_pos_y());
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__style, pgbnr.Style());
	}
}
