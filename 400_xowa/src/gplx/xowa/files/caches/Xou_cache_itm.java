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
import gplx.dbs.*;
public class Xou_cache_itm {
	public Xou_cache_itm
	( Bry_bfr lnki_key_bfr, byte db_state
	, byte[] lnki_wiki_abrv, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page, int user_thumb_w
	, int orig_repo_id, byte[] orig_ttl, int orig_ext_id, int orig_w, int orig_h
	, int html_w, int html_h, double html_time, int html_page
	, boolean file_is_orig, int file_w, double file_time, int file_page, long file_size
	, int view_count, long view_date
	) {
		this.db_state = db_state;
		this.lnki_wiki_abrv = lnki_wiki_abrv; this.lnki_ttl = lnki_ttl; this.lnki_type = lnki_type; this.lnki_upright = lnki_upright; this.lnki_w = lnki_w; this.lnki_h = lnki_h; this.lnki_time = lnki_time; this.lnki_page = lnki_page; this.user_thumb_w = user_thumb_w;
		this.orig_repo_id = orig_repo_id; this.orig_ttl = orig_ttl; this.orig_ext_id = orig_ext_id; this.orig_w = orig_w; this.orig_h = orig_h;
		this.file_is_orig = file_is_orig; this.html_w = html_w; this.html_h = html_h; this.html_time = html_time; this.html_page = html_page;
		this.file_w = file_w; this.file_time = file_time; this.file_page = file_page; this.file_size = file_size;
		this.view_count = view_count; this.view_date = view_date;
		this.lnki_key = Key_gen(lnki_key_bfr, lnki_wiki_abrv, lnki_ttl, lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, user_thumb_w);
	}
	public byte Db_state() {return db_state;} public void Db_state_(byte v) {db_state = v;} private byte db_state;
	public byte[]		Lnki_wiki_abrv() {return lnki_wiki_abrv;} private final    byte[] lnki_wiki_abrv;	// differentiate commonwiki rows inserted by one wiki vs another
	public byte[]		Lnki_key() {return lnki_key;} private final    byte[] lnki_key;		// unique key by lnki props
	public byte[]		Lnki_ttl() {return lnki_ttl;} private final    byte[] lnki_ttl;
	public int			Lnki_type() {return lnki_type;} private final    int lnki_type;
	public double		Lnki_upright() {return lnki_upright;} private final    double lnki_upright;
	public int			Lnki_w() {return lnki_w;} private final    int lnki_w;
	public int			Lnki_h() {return lnki_h;} private final    int lnki_h;
	public double		Lnki_time() {return lnki_time;} private final    double lnki_time;
	public int			Lnki_page() {return lnki_page;} private final    int lnki_page;
	public int			User_thumb_w() {return user_thumb_w;} private final    int user_thumb_w;
	public int			Orig_repo_id() {return orig_repo_id;} private final    int orig_repo_id;
	public byte[]		Orig_ttl() {return orig_ttl;} private final    byte[] orig_ttl;
	public byte[]		Orig_ttl_md5() {if (orig_ttl_md5 == null) orig_ttl_md5 = Xof_file_wkr_.Md5_fast(orig_ttl); return orig_ttl_md5;} private byte[] orig_ttl_md5;
	public int			Orig_ext_id() {return orig_ext_id;} private final    int orig_ext_id;
	public Xof_ext		Orig_ext_itm() {if (orig_ext_itm == null) orig_ext_itm = Xof_ext_.new_by_id_(orig_ext_id); return orig_ext_itm;} private Xof_ext orig_ext_itm;
	public int			Orig_w() {return orig_w;} private final    int orig_w;
	public int			Orig_h() {return orig_h;} private final    int orig_h;
	public int			Html_w() {return html_w;} private final    int html_w;
	public int			Html_h() {return html_h;} private final    int html_h;
	public double		Html_time() {return html_time;} private final    double html_time;
	public int			Html_page() {return html_page;} private final    int html_page;
	public boolean			File_is_orig() {return file_is_orig;} private final    boolean file_is_orig;
	public int			File_w() {return file_w;} private int file_w;
	public double		File_time() {return file_time;} private final    double file_time;
	public int			File_page() {return file_page;} private final    int file_page;
	public long			File_size() {return file_size;} private final    long file_size;
	public Io_url		File_url() {return file_url;} public void File_url_(Io_url v) {file_url = v;} private Io_url file_url;
	public int			View_count() {return view_count;} private int view_count;
	public long			View_date() {return view_date;} private long view_date;
	public void Update_view_stats() {
		++view_count;
		view_date = Datetime_now.Get().Timestamp_unix();
		db_state = Db_cmd_mode.To_update(db_state);
	}
	public static final    Xou_cache_itm Null = null;
	public static byte[] Key_gen(Bry_bfr key_bfr, byte[] lnki_wiki_abrv, byte[] ttl, int type, double upright, int w, int h, double time, int page, int user_thumb_w) {
		key_bfr.Add(lnki_wiki_abrv).Add_byte_pipe()
			.Add(ttl).Add_byte_pipe().Add_int_variable(type).Add_byte_pipe().Add_double(upright).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe().Add_int_variable(h).Add_byte_pipe().Add_double(time).Add_byte_pipe().Add_int_variable(page)
			.Add_int_variable(user_thumb_w)
			;
		return key_bfr.To_bry_and_clear();
	}
}
class Xof_cache_mgr_sorter implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xou_cache_itm lhs = (Xou_cache_itm)lhsObj;
		Xou_cache_itm rhs = (Xou_cache_itm)rhsObj;
		return -Long_.Compare(lhs.View_date(), rhs.View_date());	// - for DESC sort
	}
	public static final    Xof_cache_mgr_sorter Instance = new Xof_cache_mgr_sorter(); Xof_cache_mgr_sorter() {}
}
