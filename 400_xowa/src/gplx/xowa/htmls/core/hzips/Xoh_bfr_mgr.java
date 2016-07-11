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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.tocs.*;
class Xoh_bfr_mgr {
	private Xoh_page hpg;
	private Bry_bfr html_bfr;
	private Bry_bfr bfr_0 = Bry_bfr_.New(), bfr_1 = Bry_bfr_.New();
	private boolean toc_mode_is_pgbnr;
	public Bry_bfr Init(Xoh_page hpg, Bry_bfr html_bfr) {
		this.hpg = hpg; this.html_bfr = html_bfr;
		this.toc_mode_is_pgbnr = false;
		return bfr_0;
	}
	public Bry_bfr Split_by_toc(Xoh_data_itm data_itm) {
		hpg.Hdump_mgr().Toc_wtr().Exists_y_();
		this.toc_mode_is_pgbnr = ((Xoh_toc_data)data_itm).Toc_mode() == Xoh_toc_data.Toc_mode__pgbnr;
		if (toc_mode_is_pgbnr)
			hpg.Html_data().Head_mgr().Itm__pgbnr().Enabled_y_();
		return bfr_1;
	}
	public void Commit() {
		html_bfr.Add_bfr_and_clear(bfr_0);

		// calc toc by iterating sections
		if (hpg.Hdump_mgr().Toc_wtr().Exists())
			html_bfr.Add(hpg.Hdump_mgr().Toc_wtr().To_html(toc_mode_is_pgbnr));

		html_bfr.Add_bfr_and_clear(bfr_1);
	}
}
