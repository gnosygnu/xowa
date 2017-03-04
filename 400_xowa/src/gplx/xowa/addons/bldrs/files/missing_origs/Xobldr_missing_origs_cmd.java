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
package gplx.xowa.addons.bldrs.files.missing_origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.xowa.files.*; import gplx.xowa.files.origs.*; import gplx.xowa.apps.wms.apis.origs.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
public class Xobldr_missing_origs_cmd extends Xob_cmd__base {
	private int fail_max = 100000;
	public Xobldr_missing_origs_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		// got orig_tbl
		Db_conn conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_orig_regy_tbl.Create_table(conn);

		// get counts; fail if too many
		int fail_count = conn.Exec_sql(Db_sql_.Make_by_fmt(String_.Ary("SELECT Count(lnki_ttl) FROM orig_regy WHERE orig_page_id IS NULL")));
		if (fail_count > fail_max) throw Err_.new_wo_type("bldr.find_missing: too many missing: missing=~{0} max=~{1}", fail_count, fail_max);
		Gfo_usr_dlg_.Instance.Note_many("", "", "bldr.find_missing: found=~{0}", fail_count);

		// select into list; ignore any which are invalid titles
		List_adp list = List_adp_.New();
		byte[] wiki_abrv = wiki.Domain_itm().Abrv_xo();
		int invalid_count = 0;
		String sql = "SELECT lnki_ttl FROM orig_regy WHERE orig_page_id IS NULL";
		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				// get lnki_ttl; check if valid
				byte[] lnki_ttl = rdr.Read_bry("lnki_ttl");
				Xoa_ttl ttl = wiki.Ttl_parse(lnki_ttl);
				if (ttl == null) {
					invalid_count++;
					continue;
				}

				// create itm and add to list
				Xof_fsdb_itm itm = new Xof_fsdb_itm();
				itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, wiki_abrv, lnki_ttl, Byte_.Zero, Xof_img_size.Upright_null, -1, -1, -1, -1, Xof_patch_upright_tid_.Tid_all);
				list.Add(itm);
			}
		} finally {rdr.Rls();}
		Gfo_usr_dlg_.Instance.Note_many("", "", "bldr.find_missing: invalid=~{0}", invalid_count);

		// call api with list
		Xof_orig_wkr__wmf_api wkr = new Xof_orig_wkr__wmf_api(new Xoapi_orig_wmf(), wiki.App().Wmf_mgr().Download_wkr(), wiki.File__repo_mgr(), wiki.Domain_bry());
		wkr.Find_by_list(null, null);

		// loop list and update
		conn.Txn_bgn("bldr.find_missing");
		Db_stmt update_stmt = conn.Stmt_update("orig_regy", String_.Ary("lnki_ttl")
			, "orig_commons_flag", "orig_repo"
			, "orig_page_id", "orig_redirect_id", "orig_redirect_ttl"
			, "orig_file_id", "orig_file_ttl", "orig_file_ext"
			, "orig_size", "orig_w", "orig_h", "orig_bits", "orig_media_type", "orig_minor_mime", "orig_timestamp");
		int len = list.Len();
		for (int i = 0; i < len; i++)  {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)list.Get_at(i);
			update_stmt
				.Val_int("orig_w", itm.Orig_w())
				.Val_int("orig_h", itm.Orig_h())
				.Crt_bry_as_str("lnki_ttl", itm.Lnki_ttl()).Exec_update();
		}
		conn.Txn_end();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__fail_max_))					this.fail_max = m.ReadInt("v");
		else													return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk__fail_max_ = "fail_max_";

	public static final String BLDR_CMD_KEY = "file.orig_regy.find_missing";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr_missing_origs_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr_missing_origs_cmd(bldr, wiki);}
}
