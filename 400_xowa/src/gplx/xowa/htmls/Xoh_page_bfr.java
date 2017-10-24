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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.core.wkrs.tocs.*;
public class Xoh_page_bfr {
	private byte toc_mode;
	private Bry_bfr head_bfr; private final    Bry_bfr body_bfr = Bry_bfr_.New();
	public void Init(Bry_bfr head_bfr) {
		this.toc_mode = Xoh_toc_data.Toc_mode__none;
		this.head_bfr = head_bfr;
		body_bfr.Clear();
	}
	public Bry_bfr Split_by_toc(byte toc_mode) {
		if (this.toc_mode != Xoh_toc_data.Toc_mode__pgbnr)// NOTE: "none" and "pgbnr" can exist on same page (especially in en.v); must make sure that "none" does not overwrite "pgbnr" else wide images; PAGE:en.v:UNESCO_World_Heritage_List DATE:2016-11-03
			this.toc_mode = toc_mode;
		return body_bfr;
	}
	public void Commit(Xoa_page pg) {
		boolean toc_mode_enabled = true, toc_mode_is_pgbnr = false;	// default to Xoh_toc_data_.Toc_mode__basic
		switch (toc_mode) {
			case Xoh_toc_data.Toc_mode__none	: toc_mode_enabled = false; break;
			case Xoh_toc_data.Toc_mode__pgbnr	: toc_mode_is_pgbnr = true; break;
		}

		// set flags
		pg.Html_data().Toc_mgr().Exists_y_();
		pg.Html_data().Head_mgr().Itm__pgbnr().Enabled_(toc_mode_is_pgbnr);

		// build bfr by add bfr_0, toc, body_bfr
		if (toc_mode_enabled) {
			pg.Html_data().Toc_mgr().To_html(head_bfr, gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Basic, toc_mode_is_pgbnr);
			head_bfr.Add_bfr_and_clear(body_bfr);
		}
	}
}
