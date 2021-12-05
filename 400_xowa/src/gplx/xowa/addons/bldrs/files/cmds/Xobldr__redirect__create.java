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
package gplx.xowa.addons.bldrs.files.cmds;
import gplx.Int_ary_;
import gplx.dbs.Db_conn;
import gplx.dbs.cfgs.Db_cfg_tbl;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.objects.primitives.BoolUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.bldrs.files.dbs.Xob_redirect_tbl;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.bldrs.cmds.Xob_dump_mgr_base;
import gplx.xowa.bldrs.wkrs.Xob_cmd;
import gplx.xowa.parsers.utils.Xop_redirect_mgr;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.dbs.Xodb_mgr_sql;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
public class Xobldr__redirect__create extends Xob_dump_mgr_base {		
	private Db_conn conn; private Xob_redirect_tbl redirect_tbl;
	private Xodb_mgr_sql db_mgr; private Xop_redirect_mgr redirect_mgr; private Gfo_url_encoder encoder;
	public Xobldr__redirect__create(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.Reset_db_y_();}
	@Override public int[] Init_ns_ary() {return Int_ary_.New(Xow_ns_.Tid__file);}	// restrict to file ns
	@Override public byte Init_redirect() {return BoolUtl.YByte;}					// restrict to redirects
	@Override protected void Init_reset(Db_conn conn) {
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
		cfg_tbl.Delete_all();
		conn.Exec_sql("DELETE FROM " + Xob_redirect_tbl.Tbl_name);
	}
	@Override protected Db_conn Init_db_file() {
		this.db_mgr = wiki.Db_mgr_as_sql();
		redirect_mgr = wiki.Redirect_mgr();
		encoder = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url_ttl;
		redirect_tbl = new Xob_redirect_tbl(wiki.Fsys_mgr().Root_dir(), gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url_ttl).Create_table();
		conn = redirect_tbl.Conn();
		conn.Txn_bgn("bldr__redirect");
		return conn;
	}		
	@Override protected void Cmd_bgn_end() {}
	@Override public void Exec_pg_itm_hook(int ns_ord, Xow_ns ns, Xowd_page_itm page, byte[] page_src) {
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(page_src);
		byte[] redirect_ttl_bry = Xoa_ttl.Replace_spaces(redirect_ttl.Page_db());	// NOTE: spaces can still exist b/c redirect is scraped from #REDIRECT which sometimes has a mix; EX: "A_b c"
		redirect_ttl_bry = encoder.Decode(redirect_ttl_bry);
		redirect_tbl.Insert(page.Id(), Xoa_ttl.Replace_spaces(page.Ttl_page_db()), -1, redirect_ttl.Ns().Id(), redirect_ttl_bry, redirect_ttl.Anch_txt(), 1);
	}
	@Override public void Exec_commit_hook() {
		conn.Txn_sav();
	}
	@Override public void Exec_end_hook() {
		conn.Txn_end();			
		redirect_tbl.Create_indexes(usr_dlg);
		redirect_tbl.Update_trg_redirect_id(db_mgr.Core_data_mgr().Db__core().Url(), 4);
	}

	public static final String BLDR_CMD_KEY = "wiki.redirect";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final Xob_cmd Prototype = new Xobldr__redirect__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__redirect__create(bldr, wiki);}
}
