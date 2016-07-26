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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.wikis.data.*;
class Xomp_html_db_wtr {
	private final    long len_max;
	private final    Xowe_wiki wiki; private final    Xow_db_mgr db_mgr;
	private long len_cur;
	private Xow_db_file html_db; private Xowd_html_tbl html_tbl;
	public Xomp_html_db_wtr(Xowe_wiki wiki) {
		this.wiki = wiki; this.db_mgr = wiki.Data__core_mgr();
		this.len_max = wiki.Appe().Api_root().Bldr().Wiki().Import().Html_db_max();

		// delete all existing tbls
		if (!db_mgr.Props().Layout_html().Tid_is_all())
			wiki.Data__core_mgr().Dbs__delete_by_tid(Xow_db_file_.Tid__html_data);
	}
	public int Cur_db_id() {return html_db.Id();}
	public Xowd_html_tbl Tbls__get_or_new(int ns_id, long html_len) {
		long len_new = len_cur + html_len;
		boolean not_inited = html_tbl == null, out_of_space = len_new > len_max;
		if (not_inited || out_of_space) {
			if (out_of_space)
				Commit();
			if (	db_mgr.Props().Layout_html().Tid_is_all_or_few()	// is not "lot"
				&&	not_inited											// not_inited; set html_db
				) {
				this.html_db = wiki.Data__core_mgr().Dbs__get_by_tid_or_null(Xow_db_file_.Tid__html_data);
				if (html_db == null)
					this.html_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__html_data);
			}
			else
				this.html_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__html_data);

			this.html_tbl = new Xowd_html_tbl(html_db.Conn());
			html_tbl.Create_tbl();
			html_db.Conn().Txn_bgn("xomp.html_db_wtr");
			len_cur = html_len;	// NOTE: this effectively resets len_new to 0 + html_len;
		}
		else // initied and still has space; just update len
			len_cur = len_new;
		return html_tbl;
	}
	public void Rls() {
		this.Commit();
	}
	private void Commit() {
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
}
