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
package gplx.xowa.wikis.xwikis.sitelinks.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.xowa.wikis.xwikis.sitelinks.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
class Xoa_sitelink_grp_wtr implements BryBfrArg {
	private final Xoa_sitelink_itm_wtr itm_wtr = new Xoa_sitelink_itm_wtr();
	private Xoa_sitelink_grp_mgr mgr; 
	public void Init_by_app(Xoa_app app) {itm_wtr.Init_by_app(app);}
	public Xoa_sitelink_grp_wtr Fmt__init(Xoa_sitelink_grp_mgr mgr) {this.mgr = mgr; return this;}
	public void AddToBfr(BryWtr bfr) {
		int len = mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_grp grp = mgr.Get_at(i);
			if (grp.Active_len() == 0) continue;	// skip grps with no items
			fmtr.BldToBfrMany(bfr, grp.Name(), itm_wtr.Fmt__init(grp));
		}
	}
	private static final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "  <h4>~{all_name}</h4>"
	, "  <table style='width: 100%;'>~{grps}"
	, "  </table>"
	), "all_name", "grps");
}
