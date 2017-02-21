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
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.core.dbs.*;
public class Xob_redlink_mkr_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private int commit_interval = 10000, commit_count = 0;
	public Xob_redlink_mkr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_html_redlinks;}
	public void Cmd_run() {Read_data();}
	private void Read_data() {
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		wiki.Init_assert();
		Xow_db_file core_db = wiki.Data__core_mgr().Db__core();
		Xob_db_file link_dump_db = Xob_db_file.New__redlink(wiki.Fsys_mgr().Root_dir());
		Db_attach_mgr attach_mgr = new Db_attach_mgr(link_dump_db.Conn(), new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		String attach_sql = attach_mgr.Resolve_sql(Sql_select_clause);
		attach_mgr.Attach();
		Xowd_page_tbl page_tbl = core_db.Tbl__page();
		int cur_html_db_id = -1, cur_page_id = -1;
		Xoh_redlink_tbl redlink_tbl = new Xoh_redlink_tbl(page_tbl.Conn());
		Db_rdr rdr = link_dump_db.Conn().Exec_rdr(attach_sql);
		try {
			while (rdr.Move_next()) {
				// switch html_db if needed
				int html_db_id = rdr.Read_int(page_tbl.Fld_html_db_id());
				if (html_db_id != cur_html_db_id) {
					if (redlink_tbl != null) redlink_tbl.Conn().Txn_end();
					// redlink_tbl = wiki.Data__core_mgr().Dbs__get_by_id(html_db_id).Tbl__html_redlink();
					redlink_tbl.Conn().Txn_bgn("bldr__redlink");
					cur_html_db_id = html_db_id;
				}
				// commit page_id if needed
				int page_id = rdr.Read_int(page_tbl.Fld_page_id());
				if (cur_page_id != page_id) {
					if (cur_page_id != -1) Commit(redlink_tbl, cur_page_id, bfr);
					bfr.Add_int_variable(2).Add_byte_pipe();	// 2=gplx.xowa.htmls.core.makes.imgs.Xohd_img_tid.Tid_redlink
					cur_page_id = page_id;
				}
				// add html_uid to cur_page's bfr
				int html_uid = rdr.Read_int(Xob_link_dump_tbl.Fld_src_html_uid);
				bfr.Add_int_variable(html_uid).Add_byte_pipe();
			}
		}
		finally {rdr.Rls();}
		Commit(redlink_tbl, cur_page_id, bfr);	// commit cur page
		redlink_tbl.Conn().Txn_end();				// close cur tbl
		attach_mgr.Detach();
	}
	private void Commit(Xoh_redlink_tbl redlink_tbl, int cur_page_id, Bry_bfr bfr) {
		redlink_tbl.Insert(cur_page_id, bfr.To_bry_and_clear());
		++commit_count;
		if ((commit_count % commit_interval ) == 0)
			redlink_tbl.Conn().Txn_sav();
	}
	private static final    String Sql_select_clause = String_.Concat_lines_nl_skip_last
	( "SELECT p.page_html_db_id"
	, ",      p.page_id"
	, ",      ld.src_html_uid"
	, "FROM   link_dump ld"
	, "	   JOIN <page_db>page p ON p.page_id = ld.src_page_id "
	, "WHERE  ld.trg_page_id = -1"
	, "ORDER BY p.page_html_db_id, p.page_id"
	, ";"
	);
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_commit_interval_ = "commit_interval_";			
}
