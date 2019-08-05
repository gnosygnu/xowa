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
package gplx.xowa.addons.wikis.searchs.gui.htmlbars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.gui.*;
import gplx.core.js.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
class Srch_rslt_cbk__js implements Srch_rslt_cbk {
	protected final    Js_wtr js_wtr = new Js_wtr();
	private byte[] cbk_func, search_raw;
	public Srch_rslt_cbk__js(byte[] cbk_func, byte[] search_raw) {
		this.cbk_func = cbk_func;
		this.search_raw = search_raw;
	}
	public String To_str_and_clear() {return js_wtr.To_str_and_clear();}
	public void On_cancel() {}
	@gplx.Virtual public void On_rslts_found(Srch_search_qry qry, Srch_rslt_list rslts_list, int rslts_bgn, int rslts_end) {
		// exit if done
		if (!rslts_list.Rslts_are_enough && !rslts_list.Rslts_are_done) return;

		// build js; EX: "receiveSuggestions('search_word', ["a"])"
		js_wtr.Func_init(cbk_func);
		js_wtr.Prm_bry(search_raw);
		js_wtr.Prm_spr();
		js_wtr.Ary_init();
		int rslts_len = rslts_list.Len();
		for (int i = 0; i < qry.Slab_end; i++) {
			if (i >= rslts_len) break;	// rslts_end will overshoot actual rslts_len; check for out of bounds and exit; EX: default suggest will have rslts_end of 25, but "earth time" will retrieve 15 results
			Srch_rslt_row row = rslts_list.Get_at(i);
			js_wtr.Ary_bry(row.Page_ttl.Full_txt_w_ttl_case());
			js_wtr.Ary_bry(row.Page_ttl_display(Bool_.Y));
		}
		js_wtr.Ary_term();
		js_wtr.Func_term();
	}
}
class Srch_rslt_cbk__swt extends Srch_rslt_cbk__js implements Gfo_invk { 	private final    Xoae_app app;
	public Srch_rslt_cbk__swt(Xoae_app app, byte[] cbk_func, byte[] search_raw) {super(cbk_func, search_raw);
		this.app = app;
	}
	@Override public void On_rslts_found(Srch_search_qry qry, Srch_rslt_list list, int rslts_bgn, int rslts_end) {
		super.On_rslts_found(qry, list, rslts_bgn, rslts_end);
		Gfo_invk_.Invk_by_val(app.Gui_mgr().Kit().New_cmd_sync(this), Invk__notify, js_wtr.To_str_and_clear());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__notify)) {
			app.Gui_mgr().Browser_win().Active_html_box().Html_js_eval_script(m.ReadStr("v"));
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__notify = "notify";
}
