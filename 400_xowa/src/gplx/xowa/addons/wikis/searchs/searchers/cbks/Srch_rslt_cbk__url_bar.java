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
package gplx.xowa.addons.wikis.searchs.searchers.cbks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.gfui.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
import gplx.xowa.apps.apis.xowa.addons.searchs.*;
public class Srch_rslt_cbk__url_bar implements Srch_rslt_cbk, Gfo_invk {
	private final    Xoae_app app;
	private final    GfuiComboBox url_bar;
	private String[] cbo_ary;
	private boolean rslts_finished;
	private int rslts_in_this_pass;
	private boolean rslts_shown = false;
	private int max_results;
	public Srch_rslt_cbk__url_bar(Xoae_app app, GfuiComboBox url_bar, int max_results) {
		this.app = app; this.url_bar = url_bar; this.max_results = max_results;
	}
	public void On_cancel() {}
	public void On_rslts_found(Srch_search_qry qry, Srch_rslt_list rslts_list, int rslts_bgn, int rslts_end) {
		int rslts_len = rslts_list.Len();
		this.rslts_finished = rslts_list.Rslts_are_enough || rslts_list.Rslts_are_done;

		// get # of items for drop-down; note special logic to reduce blinking
		rslts_in_this_pass = rslts_end - rslts_bgn;
		if (	rslts_in_this_pass == 0		// no new results; 
			&&	rslts_bgn != 0				// if first one, still update; blanks out results from previous try;
			&&	!rslts_finished)			// if rslts_finished, still update to force cbo to "shrink"
			return;							// exit now else will "blink" when refreshing;
		int cbo_len = max_results;			// force cbo_len to be max_rslts; reduces "blinking" when typing by keeping visible area to same size
		if (rslts_list.Rslts_are_done) {	// "shrink" cbo_len to rslts_len; EX: 10 wanted; 2 returned; shrink to 2 rows;
			cbo_len = rslts_len;
		}

		// fill cbo_ary with rslts from search, while "blanking" out rest
		this.cbo_ary = new String[cbo_len];
		for (int i = 0; i < cbo_len; ++i) {
			String cbo_itm = "";
			if (i >= max_results) break;
			if (i < rslts_len) {
				Srch_rslt_row rslt = rslts_list.Get_at(i);
				cbo_itm = String_.new_u8(rslt.Page_ttl_display(Bool_.N));
			}
			cbo_ary[i] = cbo_itm;
		}

		Gfo_invk_.Invk_by_key(app.Gui_mgr().Kit().New_cmd_sync(this), Srch_rslt_cbk__url_bar.Invk__items__update); // NOTE: needs to be sync, b/c page_wkr and link_wkr must execute in order; EX:"Portal:Science" does not show; DATE:2016-03-24
	}
	private void Items__update() {
		url_bar.Items__update(cbo_ary);
		if (!url_bar.List_visible()							// rslt_list not visible
			&& !rslts_shown									// auto-dropdown hasn't happened yet
			&& (rslts_in_this_pass > 0 || rslts_finished)	// at least 1 rslt, or search done
			) {
			rslts_shown = true;	// only auto-show dropdown once; allows user to close drop-down and not have it continually flashing
			url_bar.List_visible_(Bool_.Y);
		}
		Xoa_app_.Usr_dlg().Prog_none("", "", "");
		if (rslts_finished) {
			if (cbo_ary.length == 0)
				url_bar.List_visible_(Bool_.N);
			else
				url_bar.Items__size_to_fit(cbo_ary.length);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__items__update))			Items__update();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__items__update = "items__update";
}		
