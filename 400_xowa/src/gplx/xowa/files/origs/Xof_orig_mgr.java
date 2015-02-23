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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.wmfs.apis.*;
public class Xof_orig_mgr {
	private Xof_orig_wkr[] wkrs; private int wkrs_len;		
	private final Xof_orig_wkr__orig_db wkr_xowa_db = new Xof_orig_wkr__orig_db(); private Db_conn orig_conn = null;
	private Xof_url_bldr url_bldr; private Xow_repo_mgr repo_mgr;
	public Xof_orig_mgr() {this.Wkrs__clear();}
	public Xof_orig_wkr__orig_db Wkrs__get_xowa_db() {return wkr_xowa_db;}
	public void Init_by_wiki(Io_url db_dir, boolean version_is_1, Xow_repo_mgr repo_mgr, Xof_url_bldr url_bldr) {
		this.repo_mgr = repo_mgr; this.url_bldr = url_bldr;
		this.orig_conn = Xof_orig_tbl.Conn__get_or_make(db_dir, wkr_xowa_db.Tbl(), version_is_1);
		// Xof_orig_wkr wmf_api = new Xof_orig_wkr__wmf_api(new Xoapi_orig_wmf(), wiki.Appe().File_mgr().Wmf_mgr().Download_wkr(), wiki.File_mgr().Repo_mgr(), wiki.Domain_bry());	// NOTE: do not reinstate without handling scrib / pfunc calls to Orig_mgr
		this.Wkrs__add_many(wkr_xowa_db);
	}
	public Xof_orig_itm Find_by_ttl_or_null(byte[] ttl) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			Xof_orig_itm itm = wkr.Find_as_itm(ttl);
			if (itm != Xof_orig_itm.Null) return itm;
		}
		return Xof_orig_itm.Null;
	}
	public void Find_by_list(OrderedHash rv, ListAdp itms, byte exec_tid) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			if (wkr.Find_by_list(rv, itms)) break;
		}
		Xof_orig_rdr_func.Eval(rv, itms, exec_tid, url_bldr, repo_mgr);
	}
	public void Insert(byte repo, byte[] page, int ext, int w, int h, byte[] redirect, byte status) {
		for (int i = 0; i < wkrs_len; i++) {
			Xof_orig_wkr wkr = wkrs[i];
			if (wkr.Add_orig(repo, page, ext, w, h, redirect)) break;
		}			
	}
	public void Txn_save() {
		orig_conn.Txn_mgr().Txn_end_all();
	}
	public void Rls() {
		orig_conn.Conn_term();
	}
	private void		Wkrs__clear() {wkrs = Xof_orig_wkr_.Ary_empty; wkrs_len = 0;}
	private void		Wkrs__add_many(Xof_orig_wkr... v) {
		wkrs = (Xof_orig_wkr[])Array_.Resize_add(wkrs, v);
		wkrs_len += v.length;
	}
}
