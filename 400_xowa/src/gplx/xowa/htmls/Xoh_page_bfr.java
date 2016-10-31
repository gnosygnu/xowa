/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
		if (this.toc_mode != Xoh_toc_data.Toc_mode__pgbnr)
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
