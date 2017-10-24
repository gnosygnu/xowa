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
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_search_cmd implements Cancelable, Gfo_invk {
	private final    Srch_search_mgr	search_mgr;
	public final    Srch_search_qry		qry;
	public final    Srch_crt_mgr		crt_mgr;
	private final    Srch_rslt_cbk		rslt_cbk;
	private final    Srch_rslt_list		rslts_list;
	public Srch_search_cmd(Srch_search_mgr search_mgr, Srch_search_qry qry, Srch_crt_mgr crt_mgr, Srch_rslt_cbk rslt_cbk, Srch_rslt_list rslts_list) {
		this.search_mgr = search_mgr; this.qry = qry; this.crt_mgr = crt_mgr; this.rslt_cbk = rslt_cbk; this.rslts_list = rslts_list;
	}
	public boolean Canceled() {return canceled;} private boolean canceled;
	public void Cancel() {
		canceled = true;
		rslt_cbk.On_cancel();
	}
	public void Search() {
		try {
			search_mgr.Search_async(this, qry, crt_mgr, rslt_cbk, rslts_list);	// NOTE: must handle any errors in async mode
		}
		catch(Exception e) {
			Xoa_app_.Usr_dlg().Prog_many("", "", "error during search: err=~{0}", Err_.Message_gplx_log(e));
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search))	Search();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk__search = "search";
	public static Srch_search_cmd Noop() {
		if (noop == null) {
			noop = new Srch_search_cmd(null, null, null, Srch_rslt_cbk_.Noop, null);
		}
		return noop;
	}	private static Srch_search_cmd noop;
}
