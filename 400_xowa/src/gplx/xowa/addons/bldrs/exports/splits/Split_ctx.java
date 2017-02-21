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
package gplx.xowa.addons.bldrs.exports.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*; import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
public class Split_ctx {		
	private int trg_idx = -1;
	private final    Split_wkr[] wkrs;		
	private boolean trg_make = true;
	private long trg_max = 32 * Io_mgr.Len_mb;		
	public Split_ctx(Split_cfg cfg, Xow_wiki wiki, Split_wkr[] wkrs, Split_ns_itm[] ns_itms, Db_conn wkr_conn) {
		this.cfg = cfg; this.wiki = wiki; this.wkrs = wkrs;
		this.trg_max = cfg.Trg_max();
		this.ns_itms = ns_itms; this.wkr_conn = wkr_conn;
		this.rslt_mgr = new Split_rslt_mgr(wkr_conn);
		this.html_size_calc = new Split_db_size_calc(cfg.Html().Db_max(), cfg.Html().Db_idx());
		this.file_size_calc = new Split_db_size_calc(cfg.File().Db_max(), cfg.File().Db_idx());
	}
	public Split_cfg			Cfg() {return cfg;} private final    Split_cfg cfg;
	public Xow_wiki				Wiki() {return wiki;} private final    Xow_wiki wiki;
	public Split_ns_itm[]		Ns_itms() {return ns_itms;} private final    Split_ns_itm[] ns_itms;
	public Db_conn				Wkr_conn() {return wkr_conn;} private final    Db_conn wkr_conn;
	public Db_conn				Trg_conn() {return trg_conn;} private Db_conn trg_conn;
	public int					Trg_ns() {return trg_ns;} private int trg_ns; public void Trg_ns_(int v) {this.trg_ns = v;}
	public Split_page_mgr		Page_mgr() {return page_mgr;} private final    Split_page_mgr page_mgr = new Split_page_mgr();
	public Split_rslt_mgr		Rslt_mgr() {return rslt_mgr;} private final    Split_rslt_mgr rslt_mgr;
	public Split_db_size_calc	Html_size_calc() {return html_size_calc;} private final    Split_db_size_calc html_size_calc;
	public Split_db_size_calc	File_size_calc() {return file_size_calc;} private final    Split_db_size_calc file_size_calc;

	public void Trg_db__completed() {trg_make = true;}
	public void Trg_db__assert(int ns_id) {
		if (rslt_mgr.Db_size() < trg_max && !trg_make) return;
		trg_make = false;

		// term trg_conn
		if (trg_conn != null) Trg_db__term();

		// init trg_con
		Io_url trg_url = this.Trg_db__make(ns_id);
		for (Split_wkr wkr : wkrs)
			wkr.Split__trg__nth__new(this, trg_conn);
		rslt_mgr.On__nth__new(trg_idx, trg_url, ns_id);
		trg_conn.Txn_bgn("split");
	}
	public Io_url Trg_db__make(int ns_id) {
		// create new trg_conn
		String trg_name = Split_file_tid_.Make_file_name(String_.new_u8(wiki.Domain_itm().Abrv_wm()), wiki.Props().Modified_latest().XtoStr_fmt("yyyy.MM"), ++trg_idx, ns_id, ".xowa");
		Io_url trg_url = wiki.Fsys_mgr().Root_dir().GenSubFil_nest("tmp", "split", trg_name);
		this.trg_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, trg_url);
		return trg_url;
	}
	public void Trg_db__null() {trg_conn = null;}	// null conn, else Trg_db_assert will try to close txn
	public void Term() {
		Trg_db__term();
		rslt_mgr.Term();
	}
	private void Trg_db__term() {
		rslt_mgr.On__nth__rls(trg_conn);
		trg_conn.Txn_end();
		for (Split_wkr wkr : wkrs)
			wkr.Split__trg__nth__rls(this, trg_conn);
	}
}
