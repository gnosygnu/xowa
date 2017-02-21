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
package gplx.xowa.htmls.core.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.brys.*; import gplx.dbs.*;
public class Xob_link_dump_cmd {
	private Xob_link_dump_tbl tbl; private int src_page_id; private Io_url page_db_url;
	public void Init_by_wiki(Xowe_wiki wiki) {
		this.page_db_url = wiki.Data__core_mgr().Db__core().Url(); 
		this.tbl = Xob_link_dump_tbl.Get_or_new(wiki);
		tbl.Insert_bgn();
	}
	public void Page_bgn(int src_page_id) {this.src_page_id = src_page_id;}
	public void Add(int src_html_uid, int trg_ns_id, byte[] trg_ttl) {tbl.Insert_cmd_by_batch(src_page_id, src_html_uid, trg_ns_id, trg_ttl);}
	public void Wkr_commit() {tbl.Conn().Txn_sav();}
	public void Wkr_end() {
		try {
			tbl.Insert_end();
			tbl.Create_idx_1();
			Db_conn conn = tbl.Conn();
			new Db_attach_mgr(conn,  new Db_attach_itm("page_db", page_db_url))
				.Exec_sql_w_msg("update trg_page_id", String_.Concat_lines_nl_skip_last
				( "REPLACE INTO link_dump"
				, "SELECT  r.uid"
				, ",       r.src_page_id"
				, ",       r.src_html_uid"
				, ",       Coalesce(p.page_id, -1)"
				, ",       r.trg_ns"
				, ",       r.trg_ttl"
				, "FROM    link_dump r"
				, "        LEFT JOIN <page_db>page p ON r.trg_ns = p.page_namespace AND r.trg_ttl = p.page_title"
				, ";"
				));;
			conn.Exec_sql("UPDATE link_dump SET trg_ns = -1 AND trg_ttl = '' WHERE trg_page_id != -1;");
			tbl.Create_idx_2();
			conn.Env_vacuum();
		} catch (Exception e) {
                Tfds.Dbg(Err_.Message_gplx_full(e));
		}
	}
}
