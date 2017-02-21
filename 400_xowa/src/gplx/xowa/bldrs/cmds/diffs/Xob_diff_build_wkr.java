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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*;
import gplx.dbs.*; import gplx.dbs.metas.*; import gplx.dbs.diffs.*; import gplx.dbs.diffs.builds.*; import gplx.dbs.diffs.itms.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xob_diff_build_wkr {		
	private final    Gfdb_diff_bldr dif_bldr = new Gfdb_diff_bldr();
	private final    Xowe_wiki wiki;
	private Db_conn old_conn, new_conn, dif_conn;
	private final    Xowd_tbl_mapr tbl_mapr;
	public Xob_diff_build_wkr(Xob_bldr bldr, Xowe_wiki wiki, String old_url, String new_url, String dif_url, int commit_interval, Xowd_tbl_mapr tbl_mapr) {
		this.wiki = wiki;
		wiki.Init_by_wiki();
		Bry_fmt url_fmt = Bry_fmt.New("").Args_(New_url_args(wiki, tbl_mapr.Name));
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		old_conn = New_conn(tmp_bfr, wiki, url_fmt, Bool_.N, old_url);
		new_conn = New_conn(tmp_bfr, wiki, url_fmt, Bool_.N, new_url);
		dif_conn = New_conn(tmp_bfr, wiki, url_fmt, Bool_.Y, dif_url);
		this.tbl_mapr = tbl_mapr;
	}
	public void Exec() {
		Gdif_core dif_core = new Gdif_core(dif_conn);
		String name = String_.Format("{0}|{1}|diffs|{2}", wiki.Domain_str(), tbl_mapr.Name, wiki.Props().Modified_latest().XtoStr_fmt(DateAdp_.Fmt__yyyyMMdd));	// EX: "simple.wikipedia.org|text|diffs|20160112"
		String made_by = wiki.App().User().Key();
		Gdif_job_itm job_itm = dif_core.New_job(name, made_by);
		Gdif_bldr_ctx ctx = new Gdif_bldr_ctx().Init(dif_core, job_itm);
		Gfdb_diff_wkr__db dif_wkr = new Gfdb_diff_wkr__db();
		Gdif_db dif_db = dif_core.Db();
		dif_wkr.Init_conn(dif_db, 1000);
		dif_bldr.Init(dif_wkr);
		// wiki.Data__core_mgr().Db__core().Conn().Conn_info();
		Xow_db_file[] db_file_ary = wiki.Data__core_mgr().Db__core().Tbl__db().Select_all(wiki.Data__core_mgr().Props(), Io_url_.Empty);
		int db_files_len = db_file_ary.length;
		for (int i = 0; i < db_files_len; ++i) {
			Xow_db_file db_file = db_file_ary[i];
			if (tbl_mapr.Db_ids__has(db_file.Tid()))
				Compare(ctx);
		}
//			int old_tbl_len = old_tbl_mgr.Len();
//			for (int i = 0; i < old_tbl_len; ++i) {
//				Dbmeta_tbl_itm old_tbl = old_tbl_mgr.Get_at(i);
//				Dbmeta_tbl_itm new_tbl = new_tbl_mgr.Get_by(old_tbl.Name());
//				if (new_tbl == null) {
//					// delete all
//				}
//			}
	}
	private void Compare(Gdif_bldr_ctx ctx) {
		Dbmeta_tbl_mgr old_tbl_mgr = old_conn.Meta_mgr();
		Dbmeta_tbl_mgr new_tbl_mgr = old_conn.Meta_mgr();
		int new_tbl_len = new_tbl_mgr.Len();
		for (int i = 0; i < new_tbl_len; ++i) {
			Dbmeta_tbl_itm new_tbl = new_tbl_mgr.Get_at(i);
			Dbmeta_tbl_itm old_tbl = old_tbl_mgr.Get_by(new_tbl.Name()); if (old_tbl == null) continue;
			Gfdb_diff_tbl dif_tbl = Gfdb_diff_tbl.New(new_tbl);
			dif_bldr.Compare(ctx, dif_tbl, old_conn, new_conn);
			// save txn
		}
	}
	public static Db_conn New_conn(Bry_bfr tmp_bfr, Xow_wiki wiki, Bry_fmt fmtr, boolean autocreate, String url_fmt) {
		fmtr.Fmt_(url_fmt).Bld_many(tmp_bfr);
		return Db_conn_bldr.Instance.Get_or_autocreate(autocreate, Io_url_.new_any_(tmp_bfr.To_str_and_clear()));
	}
	private static Bfr_fmt_arg[] New_url_args(Xow_wiki wiki, String db_mapr_name) {
		Bfr_fmt_arg[] rv = new Bfr_fmt_arg[]
		{ new Bfr_fmt_arg(Bry_.new_a7(".dump_dir"), new Bfr_arg__dump_dir(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dump_core"), new Bfr_arg__dump_core(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dump_domain"), new Bfr_arg__dump_domain(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dir_spr"), new Bfr_arg__dir_spr())
		, new Bfr_fmt_arg(Bry_.new_a7(".dif_name"), Bfr_arg_.New_bry(db_mapr_name))
		};
		return rv;
	}		
	//old_url='~{.dump_dir}-prev/~{.dump_core}';
	//new_url='~{.dump_dir}/~{.dump_core}';
	//dif_url='~{.dump_dir}/~{.dump_domain}-{.dif_name}-diff.xowa';
	// old_conn='data source="~{.dump_dir}/~{.dump_core}";url='
	// dif_conn='gplx_key=sqlite;url='
}
