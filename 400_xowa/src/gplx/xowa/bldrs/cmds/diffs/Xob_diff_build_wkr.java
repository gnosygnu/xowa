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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.dbs.*; import gplx.dbs.metas.*; import gplx.dbs.diffs.*; import gplx.dbs.diffs.builds.*;
class Xob_diff_build_wkr {		
	private final Gfdb_diff_bldr dif_bldr = new Gfdb_diff_bldr();
	private Db_conn old_conn, new_conn, dif_conn;
	public Xob_diff_build_wkr(Xob_bldr bldr, Xowe_wiki wiki, String old_url, String new_url, String dif_url, int commit_interval) {
		wiki.Init_by_wiki();
		Bry_fmt url_fmt = Bry_fmt.New("").Args_(New_url_args(wiki));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		old_conn = New_conn(Bool_.N, wiki, url_fmt, old_url, tmp_bfr);
		new_conn = New_conn(Bool_.N, wiki, url_fmt, new_url, tmp_bfr);
		dif_conn = New_conn(Bool_.Y, wiki, url_fmt, dif_url, tmp_bfr);
	}
	public void Exec() {
		Gfdb_diff_db dif_db = new Gfdb_diff_db(dif_conn);
		Gfdb_diff_wkr__db dif_wkr = new Gfdb_diff_wkr__db();
		dif_wkr.Init_conn(dif_db, 1000);
		dif_bldr.Init(dif_wkr);
		Dbmeta_tbl_mgr old_tbl_mgr = old_conn.Meta_tbl_load_all();
		Dbmeta_tbl_mgr new_tbl_mgr = old_conn.Meta_tbl_load_all();
		int new_tbl_len = new_tbl_mgr.Len();
		int txn = -1;
		for (int i = 0; i < new_tbl_len; ++i) {
			Dbmeta_tbl_itm new_tbl = new_tbl_mgr.Get_at(i);
			Dbmeta_tbl_itm old_tbl = old_tbl_mgr.Get_by(new_tbl.Name()); if (old_tbl == null) continue;
			Gfdb_diff_tbl dif_tbl = Gfdb_diff_tbl.New(new_tbl);
			dif_bldr.Compare(++txn, dif_tbl, old_conn, new_conn);
			// save txn
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
	public static Db_conn New_conn(boolean autocreate, Xow_wiki wiki, Bry_fmt fmtr, String url_fmt, Bry_bfr tmp_bfr) {
		fmtr.Fmt_(url_fmt).Bld_bfr_many(tmp_bfr);
		return Db_conn_bldr.Instance.Get_or_autocreate(autocreate, Io_url_.new_any_(tmp_bfr.To_str_and_clear()));
	}
	private static Bfr_fmt_arg[] New_url_args(Xow_wiki wiki) {
		Bfr_fmt_arg[] rv = new Bfr_fmt_arg[]
		{ new Bfr_fmt_arg(Bry_.new_a7(".dump_dir"), new Bfr_arg__dump_dir(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dump_core"), new Bfr_arg__dump_core(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dump_domain"), new Bfr_arg__dump_domain(wiki))
		, new Bfr_fmt_arg(Bry_.new_a7(".dir_spr"), new Bfr_arg__dir_spr())
		};
		return rv;
	}
	//old_url='~{.dump_dir}-prev/~{.dump_core}';
	//new_url='~{.dump_dir}/~{.dump_core}';
	//dif_url='~{.dump_dir}/~{.dump_domain}-diff.xowa';
}
