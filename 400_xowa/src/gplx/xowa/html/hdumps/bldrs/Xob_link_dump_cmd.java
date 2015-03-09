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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.core.brys.*;
import gplx.dbs.*; import gplx.xowa.bldrs.oimgs.*;
public class Xob_link_dump_cmd {
	private Rl_dump_tbl tbl;
	private int src_page_id; private Db_conn conn; private Io_url page_db_url; private String wiki_domain;
	public void Init_by_wiki(Xowe_wiki wiki) {
		this.wiki_domain = wiki.Domain_str(); this.page_db_url = wiki.Data_mgr__core_mgr().Dbs__get_db_core().Url(); 
		this.tbl = Rl_dump_tbl.Get_or_new(wiki); this.conn = tbl.Conn();
		conn.Txn_bgn();
	}
	public void Page_bgn(int src_page_id) {this.src_page_id = src_page_id;}
	public void Add(int src_html_uid, int trg_ns_id, byte[] trg_ttl) {tbl.Insert(src_page_id, src_html_uid, trg_ns_id, trg_ttl);}
	public void Wkr_commit()	{conn.Txn_commit();}
	public void Wkr_end() {
		try {
			conn.Txn_end();
			Db_batch_wkr__msg msg = conn.Batch_msg(Xoa_app_.Usr_dlg(), wiki_domain + ".link_dump."); Db_batch_wkr__sql sql = conn.Batch_sql();
			conn.Exec_sql__idx(msg, Db_meta_idx.new_normal_by_tbl(Rl_dump_tbl.Tbl_name, "src", Rl_dump_tbl.Fld_src_page_id, Rl_dump_tbl.Fld_src_html_uid));	// INDEX: src_page_id, src_html_uid
			conn.Exec_sql__idx(msg, Db_meta_idx.new_normal_by_tbl(Rl_dump_tbl.Tbl_name, "trg_temp", Rl_dump_tbl.Fld_trg_ns, Rl_dump_tbl.Fld_trg_ttl));		// INDEX: trg_ns, trg_ttl
			conn.Exec_sql(msg.Msg_("update_trg_page_id"), conn.Batch_attach("page_db", page_db_url), sql.Sql_
			( "REPLACE INTO rl_dump"
			, "SELECT  r.src_page_id"
			, ",       r.src_html_uid"
			, ",       Coalesce(p.page_id, -1)"
			, ",       r.trg_ns"
			, ",       r.trg_ttl"
			, "FROM    rl_dump r"
			, "        LEFT JOIN page_db.page p ON r.trg_lnki_ns = p.page_namespace AND r.trg_ttl = p.page_title"
			, ";"
			));
			conn.Exec_sql(msg.Msg_("clear_trg_flds"), sql.Sql_("UPDATE rl_dump SET trg_ns = -1 AND trg_ttl = '' WHERE trg_page_id != -1;"));
			conn.Exec_sql__idx(msg, Db_meta_idx.new_normal_by_tbl(Rl_dump_tbl.Tbl_name, "trg", Rl_dump_tbl.Fld_trg_page_id, Rl_dump_tbl.Fld_src_page_id, Rl_dump_tbl.Fld_src_html_uid)); // INDEX: trg_page_id, src_page_id, src_html_uid
			conn.Exec_sql__vacuum(msg);
		} catch (Exception e) {
                Tfds.Write(Err_.Message_gplx(e));
		}
	}
}
