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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.users.data.*;
public class Xou_cache_mgr implements Gfo_invk {
	private long fsys_size_cur = 0;
	private long fsys_size_min = Io_mgr.Len_mb * 75;
	private long fsys_size_max = Io_mgr.Len_mb * 100;
	private final    Xoa_wiki_mgr wiki_mgr; private final    Xou_cache_tbl cache_tbl; private final    Db_cfg_tbl cfg_tbl; private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(512);
	private final    Ordered_hash hash = Ordered_hash_.New_bry(); private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2(); private final    Object thread_lock = new Object();
	private final    Io_url cache_dir; private boolean db_load_needed = true;
	public Xou_cache_mgr(Xoa_wiki_mgr wiki_mgr, Io_url cache_dir, Xou_db_file db_file) {
		this.wiki_mgr = wiki_mgr; this.cache_dir = cache_dir;
		this.cfg_tbl = db_file.Tbl__cfg();
		this.cache_tbl = db_file.Tbl__cache();
	}
	public boolean Enabled() {return enabled;} private boolean enabled = true; public void Enabled_n_() {enabled = false;}
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__fsys_size_min, Cfg__fsys_size_max);
		app.Cfg().Sub_many_app(this, Run__fsys_reduce_to_min, Run__fsys_clear);
		app.Cfg().Dflt_mgr().Add(this, Val__fsys_info);
	}
	public Xou_cache_itm Get_or_null(Xof_fsdb_itm fsdb) {return Get_or_null(fsdb.Lnki_wiki_abrv(), fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page(), fsdb.User_thumb_w());}
	public Xou_cache_itm Get_or_null(byte[] wiki, byte[] ttl, int type, double upright, int w, int h, double time, int page, int user_thumb_w) {
		if (!enabled) return null;
		synchronized (thread_lock) {
			this.Page_bgn();
			byte[] key = Xou_cache_itm.Key_gen(tmp_bfr, wiki, ttl, type, upright, w, h, time, page, user_thumb_w);
			Xou_cache_itm rv = (Xou_cache_itm)hash.Get_by(key);
			if (rv == Xou_cache_itm.Null) {
				rv = cache_tbl.Select_one(wiki, ttl, type, upright, w, h, time, page, user_thumb_w);
				if (rv == Xou_cache_itm.Null) return Xou_cache_itm.Null;
				hash.Add(key, rv);
			}
			return rv;
		}
	}
	public void Update(Xof_fsdb_itm fsdb) {
		synchronized (thread_lock) {
			Xou_cache_itm itm = Get_or_null(fsdb.Lnki_wiki_abrv(), fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page(), fsdb.User_thumb_w());
			if (itm == Xou_cache_itm.Null) {
				itm = new Xou_cache_itm(tmp_bfr, Db_cmd_mode.Tid_create, fsdb.Lnki_wiki_abrv(), fsdb.Lnki_ttl(), fsdb.Lnki_type(), fsdb.Lnki_upright(), fsdb.Lnki_w(), fsdb.Lnki_h(), fsdb.Lnki_time(), fsdb.Lnki_page(), fsdb.User_thumb_w()
					, fsdb.Orig_repo_id(), fsdb.Orig_ttl(), fsdb.Orig_ext().Id(), fsdb.Orig_w(), fsdb.Orig_h()
					, fsdb.Html_w(), fsdb.Html_h(), fsdb.Lnki_time(), fsdb.Lnki_page()
					, fsdb.File_is_orig(), fsdb.File_w(), fsdb.Lnki_time(), fsdb.Lnki_page(), fsdb.File_size()
					, 1, Datetime_now.Get().Timestamp_unix())
					;
				hash.Add(itm.Lnki_key(), itm);
				fsys_size_cur += itm.File_size();
			}
			else
				itm.Update_view_stats();
		}
	}
	public void Page_bgn() {
		if (db_load_needed) {
			db_load_needed = false;
			fsys_size_cur = cfg_tbl.Assert_long("user.file_cache", "size_sum", 0);
			cache_tbl.Select_all(tmp_bfr, hash);
		}
	}
	public void Page_end(Xoa_wiki_mgr wiki_mgr) {	// threaded
		cfg_tbl.Update_long("user.file_cache", "size_sum", fsys_size_cur);
		this.Db_save();
		if (fsys_size_cur > fsys_size_max) this.Reduce(fsys_size_min);
	}
	@gplx.Internal protected void Clear() {
		db_load_needed = true;
		fsys_size_cur = 0;
		hash.Clear();
	}
	public void Db_save() {
		synchronized (thread_lock) {
			Db_conn conn = cache_tbl.Conn();
			try {
				conn.Txn_bgn("user__file_cache__save");
				int len = hash.Count();
				for (int i = 0; i < len; ++i) {
					Xou_cache_itm itm = (Xou_cache_itm)hash.Get_at(i);
					cache_tbl.Db_save(itm);
				}
				conn.Txn_end();
			}
			catch (Exception e) {conn.Txn_cxl(); throw Err_.new_exc(e, "cache", "unknown error while saving cache; err=~{0}", Err_.Message_gplx_log(e));}
		}
	}
	public void Reduce(long reduce_to) {
		Xoa_app_.Usr_dlg().Note_many("", "", "cache compress started");
		synchronized (thread_lock) {
			try {
				this.Db_save(); cache_tbl.Select_all(tmp_bfr, hash);		// save and load
				Ordered_hash grp_hash = Ordered_hash_.New();				// aggregate by file path; needed when same commons file used by two wikis
				int len = hash.Count();
				for (int i = 0; i < len; ++i) {
					Xou_cache_itm itm = (Xou_cache_itm)hash.Get_at(i);
					Io_url itm_url = Calc_url(itm);
					itm.File_url_(itm_url);
					Xou_cache_grp grp = (Xou_cache_grp)grp_hash.Get_by(itm_url.Raw());
					if (grp == null) {
						grp = new Xou_cache_grp(itm_url);
						grp_hash.Add(itm_url.Raw(), grp);
					}
					grp.Add(itm);
				}
				grp_hash.Sort_by(Xou_cache_grp_sorter.Instance);				// sorts by cache_time desc
				len = grp_hash.Count();
				long fsys_size_calc = 0, fsys_size_temp = 0;
				List_adp deleted = List_adp_.New();
				for (int i = 0; i < len; ++i) {							// iterate and find items to delete
					Xou_cache_grp grp = (Xou_cache_grp)grp_hash.Get_at(i);
					fsys_size_temp = fsys_size_calc + grp.File_size();
					if (	fsys_size_temp > reduce_to					// fsys_size_cur exceeded; mark itm for deletion
						||	fsys_size_temp == -1						// fsys_size sometimes -1; note -1 b/c file is missing; should fix, but for now, consider -1 size deleted; DATE:2015-08-05
						)
						deleted.Add(grp);
					else
						fsys_size_calc = fsys_size_temp;
				}
				len = deleted.Count();
				Db_conn conn = cache_tbl.Conn();
				conn.Txn_bgn("user__file_cache__delete");
				for (int i = 0; i < len; i++) {							// iterate and delete
					Xou_cache_grp grp = (Xou_cache_grp)deleted.Get_at(i);
					grp.Delete(hash, cache_tbl);
					fsys_size_cur -= grp.File_size();
				}
				conn.Txn_end();
				Io_mgr.Instance.Delete_dir_empty(cache_dir);
			}
			catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "failed to compress cache: err=~{0}", Err_.Message_gplx_full(e)); return;}
		}
		Xoa_app_.Usr_dlg().Note_many("", "", "cache compress done");
	}
	private Io_url Calc_url(Xou_cache_itm cache) {
		byte[] wiki_domain = Xow_abrv_xo_.To_itm(cache.Lnki_wiki_abrv()).Domain_bry();
		Xow_wiki wiki = wiki_mgr.Get_by_or_make_init_y(wiki_domain); if (wiki == null) return Io_url_.Empty;	// wiki is not available; should only happen in read-only mode; DATE:2015-05-23
		Xof_repo_itm trg_repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(cache.Orig_repo_id(), cache.Lnki_ttl(), Bry_.Empty);
		if (trg_repo == null) return Io_url_.Empty;
		byte[] orig_ttl = cache.Orig_ttl();
		byte[] orig_md5 = cache.Orig_ttl_md5();
		Xof_ext orig_ext = cache.Orig_ext_itm();
		orig_ttl = trg_repo.Gen_name_trg(tmp_bfr, orig_ttl, orig_md5, orig_ext);
		byte mode_id = cache.File_is_orig() ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb;
		return url_bldr.Init_for_trg_file(trg_repo, mode_id, orig_ttl, orig_md5, orig_ext, cache.File_w()
			, Xof_lnki_time.Convert_to_xowa_thumbtime	(orig_ext.Id(), cache.File_time())
			, Xof_lnki_time.Convert_to_xowa_page		(orig_ext.Id(), cache.File_page())
			).Xto_url();
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__fsys_size_min))			this.fsys_size_min = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Cfg__fsys_size_max))			this.fsys_size_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Val__fsys_info))				return this.Info_str();
		else if	(ctx.Match(k, Run__fsys_reduce_to_min))		{this.Reduce(fsys_size_min);}
		else if	(ctx.Match(k, Run__fsys_clear))				{this.Reduce(0);}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Cfg__fsys_size_min		= "xowa.files.cache.fsys_size_min"
	, Cfg__fsys_size_max		= "xowa.files.cache.fsys_size_max"
	, Run__fsys_reduce_to_min	= "xowa.files.cache.reduce_to_min"
	, Run__fsys_clear			= "xowa.files.cache.clear"
	, Val__fsys_info			= "xowa.files.cache.info"
	;
	public void Fsys_size_(long min, long max) {fsys_size_min = min; fsys_size_max = max;}	// TEST:
	private String Info_str() {
		this.Page_bgn();
		Bry_bfr bfr = Bry_bfr_.New_w_size(255);
		Keyval[] ary = this.Info_kvs();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = ary[i];
			bfr.Add_str_a7(kv.Key()).Add_str_a7(": ").Add_str_u8(kv.Val_to_str_or_empty()).Add_byte_nl();
		}
		return bfr.To_str_and_clear();
	}
	private Keyval[] Info_kvs() {
		long view_date = Long_.Max_value;
		long fsys_size = 0;
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xou_cache_itm itm = (Xou_cache_itm)hash.Get_at(i);
			fsys_size += itm.File_size();
			if (itm.View_date() < view_date) view_date = itm.View_date();
		}
		return Keyval_.Ary
		( Keyval_.new_("cache folder", cache_dir.Xto_api())
		, Keyval_.new_("space used", gplx.core.ios.Io_size_.To_str(fsys_size))
		, Keyval_.new_("file count", len)
		, Keyval_.new_("oldest file", view_date == Long_.Max_value ? "" : DateAdp_.unixtime_utc_seconds_(view_date).XtoStr_fmt_iso_8561())
		);
	}
}
class Xou_cache_grp {
	private final    List_adp list = List_adp_.New();
	public Xou_cache_grp(Io_url file_url) {this.file_url = file_url;}
	public long View_date() {return view_date;} private long view_date;
	public long File_size() {return file_size;} private long file_size;
	public Io_url File_url() {return file_url;} private final    Io_url file_url;
	public int Len() {return list.Count();}
	public void Add(Xou_cache_itm itm) {
		if (itm.View_date() > view_date) view_date = itm.View_date();
		file_size += itm.File_size();
		list.Add(itm);
	}
	public void Delete(Ordered_hash cache_hash, Xou_cache_tbl cache_tbl) {
		int len = list.Count();
		boolean deleted = false;
		for (int i = 0; i < len; ++i) {
			Xou_cache_itm itm = (Xou_cache_itm)list.Get_at(i);
			cache_hash.Del(itm.Lnki_key());
			itm.Db_state_(Db_cmd_mode.Tid_delete);
			cache_tbl.Db_save(itm);
			gplx.core.ios.IoItmFil fil = Io_mgr.Instance.QueryFil(itm.File_url());
			if (fil.Exists()) {
				Io_mgr.Instance.DeleteFil(itm.File_url());
				deleted = true;
			}
			else {
				if (!deleted)	// multiple itms in cache resolve to same path; 1st gets deleted, but 2nd won't; ignore warning; PAGE:s.w:File:William_Shakespeare_Chandos_Portrait.jpg; DATE:2015-05-23
					Xoa_app_.Usr_dlg().Warn_many("", "", "cache:file not found; file=~{0}", itm.File_url().Raw());
			}
		}
	}
	public Xou_cache_itm Get_at(int i) {return (Xou_cache_itm)list.Get_at(i);}
}
class Xou_cache_grp_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xou_cache_grp lhs = (Xou_cache_grp)lhsObj;
		Xou_cache_grp rhs = (Xou_cache_grp)rhsObj;
		return -Long_.Compare(lhs.View_date(), rhs.View_date());	// - for DESC sort
	}
	public static final    Xou_cache_grp_sorter Instance = new Xou_cache_grp_sorter(); Xou_cache_grp_sorter() {}
}
