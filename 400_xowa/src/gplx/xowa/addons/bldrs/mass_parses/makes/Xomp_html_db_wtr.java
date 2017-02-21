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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.bldrs.cmds.*;
class Xomp_html_db_wtr {
	private final    long len_max;
	private final    Xowe_wiki wiki; private final    Xow_db_mgr db_mgr;
	private long len_cur;
	private int prv_ns_id = -1;
	private Xow_db_file html_db; private Xowd_html_tbl html_tbl;
	private Xob_ns_file_itm ns_itm;
	public Xomp_html_db_wtr(Xowe_wiki wiki) {
		this.wiki = wiki; this.db_mgr = wiki.Data__core_mgr();
		this.len_max = gplx.xowa.bldrs.Xobldr_cfg.Max_size__html(wiki.App());
	}
	public int Cur_db_id() {return html_db.Id();}
	public Xowd_html_tbl Tbls__get_or_new(int ns_id, long html_len) {
		long len_new = len_cur + html_len;
		boolean not_inited = html_tbl == null, out_of_space = len_new > len_max;
		boolean is_all_or_few = db_mgr.Props().Layout_html().Tid_is_all_or_few();
		boolean ns_changed = ns_id != prv_ns_id;
		if (not_inited || out_of_space 
			|| (ns_changed && !is_all_or_few)	// only make new html_db if lot and ns_changed
			) {
			Commit();
			if (	is_all_or_few		// is not "lot"
				&&	not_inited			// not_inited; set html_db
				) {
				this.html_db = wiki.Data__core_mgr().Dbs__get_by_tid_or_null(Xow_db_file_.Tid__html_data);
				if (html_db == null)
					Make_html_db(is_all_or_few, ns_id);
			}
			else
				Make_html_db(is_all_or_few, ns_id);

			this.html_tbl = new Xowd_html_tbl(html_db.Conn());
			html_tbl.Create_tbl();
			html_db.Conn().Txn_bgn("xomp.html_db_wtr");
			len_cur = html_len;	// NOTE: this effectively resets len_new to 0 + html_len;
		}
		else // initied and still has space; just update len
			len_cur = len_new;
		return html_tbl;
	}
	private void Make_html_db(boolean is_all_or_few, int ns_id) {
		if (prv_ns_id != ns_id
			|| ns_itm == null) {
			prv_ns_id = ns_id;
			ns_itm = new Xob_ns_file_itm(Xow_db_file_.Tid__html_data, "ns." + Int_.To_str_pad_bgn_zero(ns_id, 3), Int_.Ary(ns_id));
		}
		String file_name = is_all_or_few ? "-html.xowa" : ns_itm.Make_file_name();
		this.html_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__html_data, Int_.To_str(ns_id), ns_itm.Nth_db_idx(), file_name);
	}
	public void Rls() {
		this.Commit();
	}
	private void Commit() {
		if (html_tbl == null) return;
		html_tbl.Conn().Txn_end();
		html_tbl.Conn().Rls_conn();

		// update page_ids
		String sql = String_.Format(String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "UPDATE  page"
		, "SET     page_html_db_id = {0}"
		, "WHERE   page_id IN (SELECT page_id FROM <html_db>html h)"
		), html_db.Id());
		Db_attach_mgr attach_mgr = new Db_attach_mgr(db_mgr.Db__core().Conn(), new Db_attach_itm("html_db", html_db.Conn()));
		attach_mgr.Exec_sql_w_msg("updating page_ids: " + Int_.To_str(html_db.Id()), sql);
	}
	public static void Delete_html_dbs(Xowe_wiki wiki) {
		// run only for few /lot
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		if (db_mgr.Props().Layout_html().Tid_is_all()) return;

		// loop thru dbs and delete files
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; ++i) {
			Xow_db_file db_file = db_mgr.Dbs__get_at(i);
			if (db_file.Tid() == Xow_db_file_.Tid__html_data)
				Io_mgr.Instance.DeleteFil(db_file.Url());
		}

		// remove from xowa_db
		db_mgr.Dbs__delete_by_tid(Xow_db_file_.Tid__html_data);
	}
}
