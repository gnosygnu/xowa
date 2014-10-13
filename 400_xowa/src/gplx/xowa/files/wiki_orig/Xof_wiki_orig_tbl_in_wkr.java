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
package gplx.xowa.files.wiki_orig; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.files.fsdb.*;
class Xof_wiki_orig_tbl_in_wkr extends gplx.xowa.dbs.tbls.Xodb_in_wkr_base {
	private ListAdp itms_all;
	@Override public int Interval() {return Sqlite_engine_.Stmt_arg_max;}
	private OrderedHash itms_by_ttl;
	public Xof_wiki_orig_tbl_in_wkr Init(ListAdp v, OrderedHash itms_by_ttl) {this.itms_all = v; this.itms_by_ttl = itms_by_ttl; return this;}
	@Override public Db_qry Build_qry(Xodb_ctx db_ctx, int bgn, int end) {
		Object[] part_ary = gplx.xowa.dbs.tbls.Xodb_in_wkr_base.In_ary(end - bgn);
		String in_fld_name = Xof_wiki_orig_tbl.Fld_orig_ttl; 
		return Db_qry_.select_cols_
		(	Xof_wiki_orig_tbl.Tbl_name
		, 	Db_crt_.in_(in_fld_name, part_ary)
		)
		;
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms_all.FetchAt(i);
			stmt.Val_str_by_bry_(itm.Lnki_ttl());
		}
	}
	@Override public void Eval_rslts(Cancelable cancelable, Xodb_ctx db_ctx, DataRdr rdr) {
		while (rdr.MoveNextPeer()) {
			if (cancelable.Canceled()) return;
			Xof_wiki_orig_itm itm = Xof_wiki_orig_itm.load_(rdr);
			byte[] itm_ttl = itm.Ttl();
			if (!itms_by_ttl.Has(itm_ttl))	// guard against dupes (shouldn't happen)
				itms_by_ttl.Add(itm_ttl, itm);
		}
	}
}
class Xof_wiki_orig_tbl_evaluator {
	public static void Rdr_done(byte exec_tid, ListAdp itms_all, OrderedHash itms_by_ttl, Xof_url_bldr url_bldr, Xow_repo_mgr repo_mgr) {
		Xof_img_size img_size = new Xof_img_size();
		int len = itms_all.Count();
		for (int i = 0; i < len; i++) {
			Xof_fsdb_itm fsdb_itm = (Xof_fsdb_itm)itms_all.FetchAt(i);
			byte[] fsdb_itm_ttl = fsdb_itm.Lnki_ttl();
			fsdb_itm.Rslt_reg_(Xof_wiki_orig_wkr_.Tid_missing_reg);
			Xof_wiki_orig_itm regy_itm = (Xof_wiki_orig_itm)itms_by_ttl.Fetch(fsdb_itm_ttl); if (regy_itm == null) continue; // not in reg; do full search
			byte regy_itm_status = regy_itm.Status();
			fsdb_itm.Lnki_ext_(Xof_ext_.new_by_id_(regy_itm.Orig_ext()));	// overwrite ext_id with whatever's in file_orig; needed for ogg -> oga / ogv
			fsdb_itm.Rslt_reg_(regy_itm_status);
			if (regy_itm_status > Xof_wiki_orig_wkr_.Tid_found_orig) continue;	// only ignore if marked missing; DATE:2014-02-01
			byte repo_id = regy_itm.Orig_repo();
			Xof_repo_itm repo = null;
			if (repo_id <= Xof_repo_itm.Repo_local) { // bounds check
				fsdb_itm.Orig_repo_(repo_id);
				Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_id(repo_id);
				if (repo_pair == null)	// shouldn't happen, but try to avoid null ref;
					repo_pair = repo_mgr.Repos_get_at(repo_id);
				fsdb_itm.Orig_wiki_(repo_pair.Wiki_key());
				repo = repo_pair.Trg();
			}
			fsdb_itm.Orig_size_(regy_itm.Orig_w(), regy_itm.Orig_h());
			fsdb_itm.Rslt_reg_(Xof_wiki_orig_wkr_.Tid_found_orig);
			if (Bry_.Len_gt_0(regy_itm.Orig_redirect()))	// redirect exists;
				fsdb_itm.Init_by_redirect(regy_itm.Orig_redirect());
			fsdb_itm.Html_size_calc(img_size, exec_tid);
			Io_url html_url = url_bldr.Init_for_trg_file(fsdb_itm.Lnki_type_as_mode(), repo, fsdb_itm.Lnki_ttl(), fsdb_itm.Lnki_md5(), fsdb_itm.Lnki_ext(), fsdb_itm.Html_w(), fsdb_itm.Lnki_thumbtime(), fsdb_itm.Lnki_page()).Xto_url();
			fsdb_itm.Html_url_(html_url);
			if (!Io_mgr._.ExistsFil(html_url))
				fsdb_itm.Rslt_reg_(Xof_wiki_orig_wkr_.Tid_missing_reg);
			// build url; check if exists;
		}
	}
}
class Io_url_exists_mgr {
	private gplx.cache.Gfo_cache_mgr_bry cache_mgr = new gplx.cache.Gfo_cache_mgr_bry();
	public Io_url_exists_mgr() {
		cache_mgr.Compress_max_(Int_.MaxValue);
	}
	public boolean Has(Io_url url) {
		byte[] url_key = url.RawBry();
		Object rv_obj = cache_mgr.Get_or_null(url_key);
		if (rv_obj != null) return ((Bool_obj_ref)rv_obj).Val(); // cached val exists; use it
		boolean exists = Io_mgr._.ExistsFil(url);
		cache_mgr.Add(url_key, Bool_obj_ref.new_(exists));
		return exists;
	}
}
