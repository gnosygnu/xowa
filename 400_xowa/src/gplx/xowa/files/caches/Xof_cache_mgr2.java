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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa2.files.*; import gplx.xowa.wikis.*;
public class Xof_cache_mgr2 {
	private Xof_cache_tbl tbl; private final Bry_bfr key_bfr = Bry_bfr.reset_(512);
	private final OrderedHash hash = OrderedHash_.new_bry_(); // private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_();
	private Db_conn conn; private int site;
	private long cache_size = -1, cache_min = Io_mgr.Len_mb * 75, cache_max = Io_mgr.Len_mb * 100;
	public void Conn_(Db_conn conn, boolean created, int site) {
		this.conn = conn; this.site = site;
		this.tbl = new Xof_cache_tbl(conn);
		if (created) tbl.Create_tbl();
	}
	public Xof_cache_itm Get_or_null(Xof_fsdb_itm fsdb) {return Get_or_null(fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page());}
	public Xof_cache_itm Get_or_null(byte[] ttl, int type, double upright, int w, int h, double time, int page) {
		synchronized (hash) {
			byte[] key = Xof_cache_itm.Key_gen(key_bfr, site, ttl, type, upright, w, h, time, page);
			Xof_cache_itm rv = (Xof_cache_itm)hash.Fetch(key);
			if (rv == Xof_cache_itm.Null) {
				rv = tbl.Select_one(site, ttl, type, upright, w, h, time, page);
				if (rv == Xof_cache_itm.Null) return Xof_cache_itm.Null;
				hash.Add(key, rv);
			}
			return rv;
		}
	}
	public void Update(Xof_fsdb_itm fsdb) {
		synchronized (hash) {
			Xof_cache_itm itm = Get_or_null(fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page());
			if (itm == Xof_cache_itm.Null) {
				itm = new Xof_cache_itm(key_bfr, Db_cmd_mode.Tid_create, site, fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page()
					, fsdb.Orig_repo_id(), fsdb.Orig_ttl(), fsdb.Orig_ext().Id(), fsdb.Orig_w(), fsdb.Orig_h()
					, fsdb.Lnki_time(), fsdb.Lnki_page(), fsdb.Temp_file_size(), 1, DateAdp_.Now().Timestamp_unix(), fsdb.Temp_file_w())
					;
				hash.Add(itm.Lnki_key(), itm);
				cache_size += itm.Temp_file_size();
			}
			else
				itm.Update_view_stats();
		}
	}
	public void Page_bgn() {
		if (cache_size == -1) {
			cache_size = tbl.Select_max_size();
			tbl.Select_all(key_bfr, hash);
		}
	}
	public void Page_end() {	// threaded
		this.Db_save();
		if (cache_size > cache_max) this.Compress(cache_min);
	}
	private void Db_save() {
		synchronized (hash) {
			try {
				conn.Txn_bgn();
				int len = hash.Count();
				for (int i = 0; i < len; ++i) {
					Xof_cache_itm itm = (Xof_cache_itm)hash.FetchAt(i);
					tbl.Db_save(itm);
				}
				conn.Txn_end();
			}
			catch (Exception e) {conn.Txn_cxl(); throw Err_.err_(e);}
		}
	}
	public long Compress(long cache_min) {
		synchronized (hash) {
			try {
				Xoa_app_.Usr_dlg().Note_many("", "", "cache compress started");
				this.Db_save(); tbl.Select_all(key_bfr, hash);			// save and load
				hash.SortBy(Xof_cache_mgr_sorter.I);					// sorts by cache_time desc
				int len = hash.Count();
				long cache_size_actl = 0, cache_size_temp = 0;
				ListAdp deleted = ListAdp_.new_();
				tbl.Conn().Txn_bgn();
				for (int i = 0; i < len; ++i) {							// iterate over entire hash to figure out what to delete
					Xof_cache_itm itm = (Xof_cache_itm)hash.FetchAt(i);
					long itm_size = itm.Temp_file_size();
					cache_size_temp = cache_size_actl + itm_size;
					if (cache_size_temp > cache_min)
						deleted.Add(itm);
					else
						cache_size_actl = cache_size_temp;
				}
				len = deleted.Count();
				conn.Txn_bgn();
				for (int i = 0; i < len; i++) {							// iterate over deleted and delete
					Xof_cache_itm itm = (Xof_cache_itm)deleted.FetchAt(i);
					hash.Del(itm.Lnki_key());
					itm.Db_state_(Db_cmd_mode.Tid_delete);
					tbl.Db_save(itm);
					Io_mgr._.DeleteFil(itm.Temp_file_url());
					cache_size -= itm.Temp_file_size();
				}
				conn.Txn_end();
				Xoa_app_.Usr_dlg().Note_many("", "", "cache compress done");
				return cache_size_actl;
			}
			catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "failed to compress cache: err=~{0}", Err_.Message_gplx_brief(e)); return cache_min;}
			finally {tbl.Conn().Txn_end();}
		}
	}
//		private Io_url Calc_url(Xof_url_bldr url_bldr, Xoa_wiki_mgr wiki_mgr, Xof_cache_itm itm) {
//			byte mode_id = itm.File_is_orig() ? gplx.xowa.files.repos.Xof_repo_itm.Mode_orig : gplx.xowa.files.repos.Xof_repo_itm.Mode_thumb;
//			byte[] wiki_domain = Xow_domain_tid_.To_domain(itm.Lnki_site()).Domain_bry();
//			Xow_wiki wiki = wiki_mgr.Get_by_key_or_make(wiki_domain);
//			gplx.xowa.files.repos.Xof_repo_itm trg_repo = wiki.File_mgr__repo_mgr().Repos_get_by_wiki(wiki_domain).Trg();
//			byte[] ttl = itm.Lnki_ttl();			
//			byte[] md5 = Xof_xfer_itm_.Md5_(ttl);
//			Xof_ext itm_ext = Xof_ext_.new_by_id_(itm.Orig_ext());
//			return url_bldr.Init_for_trg_file(mode_id, trg_repo, ttl, md5, itm_ext, itm.Temp_w()
//				, Xof_lnki_time.Convert_to_xowa_thumbtime	(itm_ext.Id(), itm.File_time())
//				, Xof_lnki_time.Convert_to_xowa_page		(itm_ext.Id(), itm.File_page())
//				).Xto_url();
//		}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cache_min))		return cache_min / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_min_))		cache_min = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_max))		return cache_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_max_))		cache_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_compress))	this.Compress(cache_min);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_cache_min = "cache_min", Invk_cache_min_ = "cache_min_", Invk_cache_max = "cache_max", Invk_cache_max_ = "cache_max_", Invk_cache_compress = "cache_compress";
}
class Xof_cache_mgr_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xof_cache_itm lhs = (Xof_cache_itm)lhsObj;
		Xof_cache_itm rhs = (Xof_cache_itm)rhsObj;
		return -Long_.Compare(lhs.Temp_view_date(), rhs.Temp_view_date());	// - for DESC sort
	}
	public static final Xof_cache_mgr_sorter I = new Xof_cache_mgr_sorter(); Xof_cache_mgr_sorter() {}
}
