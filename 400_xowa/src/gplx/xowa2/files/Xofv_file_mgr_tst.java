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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.caches.*;
import gplx.xowa2.apps.*; import gplx.xowa2.wikis.*; import gplx.xowa.files.origs.*;
public class Xofv_file_mgr_tst {
	@Before public void init() {fxt.Clear();} private final Xofv_file_mgr_fxt fxt = new Xofv_file_mgr_fxt();
	@After  public void term() {Gfo_usr_dlg_._ = Gfo_usr_dlg_.Null;}
	@Test   public void Thumb() {
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
			.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
			.Exec_process_lnki()
			.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/220px.png", 220, 200))
			.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")
			.Test_fsdb_download(1);
			;
	}
	@Test   public void Orig() {
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_orig("A.png", 440, 400))
			.Init_xfer_add(fxt.Mkr_xfer().Init_none(0, "A.png"))
			.Exec_process_lnki()
			.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/orig/7/0/A.png", 440, 400))
			.Test_fsys_get("mem/xowa/file/comm/orig/7/0/A.png")
			.Test_fsdb_download(1);
			;
	}
	@Test   public void Img_size() {	// PURPOSE: test integration of Xof_img_size
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 110, 100))
			.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", Xof_img_size.Null, Xof_img_size.Null).Upright_(.5f))
			.Exec_process_lnki()
			.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/110px.png", 110, 100))
			.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/110px.png")
			.Test_fsdb_download(1);
			;
	}
	@Test   public void Orig_mgr() {	// PURPOSE: test integration of Orig_mgr
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm_redirect("B.jpg", "A.png", 440, 400))	// B.jpg redirects to A.png
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
			.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "B.jpg", 220, 200))
			.Exec_process_lnki()
			.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/220px.png", 220, 200))
			.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")
			.Test_fsdb_download(1);
			;
	}
	@Test   public void Cache_exists() {
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
			.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
			.Init_cache_add(fxt.Mkr_cache().Init("comm", "A.png", Bool_.N, 220))	// add to cache
			.Init_fsys_add("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")			// copy file to fsys
			.Exec_process_lnki()
			.Test_fsdb_download(0)	// skip download
			;
	}
	@Test   public void Cache_absent() {
		fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
			.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
			.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
			.Init_cache_add(fxt.Mkr_cache().Init("commons", "A.png", Bool_.N, 220))	// add to cache
			.Exec_process_lnki()
			.Test_fsdb_download(1)	// do download
			;
	}
}
class Xofv_file_mgr_fxt {
	private Xofv_file_mgr file_mgr;
	private final Xof_fsdb_mgr__test fsdb_mgr = new Xof_fsdb_mgr__test(); private final Xog_html_gui__test html_gui = new Xog_html_gui__test();
	public Xof_xfer_mkr Mkr_xfer() {return mkr_xfer;} private final Xof_xfer_mkr mkr_xfer = new Xof_xfer_mkr();
	public Xof_orig_itm_mkr Mkr_orig() {return mkr_orig;} private final Xof_orig_itm_mkr mkr_orig = new Xof_orig_itm_mkr();
	public Xof_fsdb_mkr Mkr_fsdb() {return mkr_fsdb;} private final Xof_fsdb_mkr mkr_fsdb = new Xof_fsdb_mkr();		
	public Xog_html_rsc_mkr Mkr_html() {return mkr_html;} private final Xog_html_rsc_mkr mkr_html = new Xog_html_rsc_mkr();
	public Xou_cache_itm_mkr Mkr_cache() {return mkr_cache;} private final Xou_cache_itm_mkr mkr_cache = new Xou_cache_itm_mkr();
	public void Clear() {
		file_mgr = new Xofv_file_mgr(Bry_.new_ascii_("enwiki"));
		Db_conn_bldr.I.Reg_default_mem();
		Db_conn conn = Db_conn_bldr.I.New("", Io_url_.mem_fil_("mem/file/cache.db")); boolean created = Bool_.Y; boolean version_is_1 = Bool_.N;
		file_mgr.Cache_mgr().Init_for_db(conn, created, version_is_1);
		file_mgr.Orig_wkr().Conn_(conn, created, version_is_1);
		fsdb_mgr.Clear();
		html_gui.Clear();
		file_mgr.Fsdb_mgr_(fsdb_mgr);
		Clear_repos();
	}
	private void Clear_repos() {
		Xofv_repo_mgr repo_mgr = file_mgr.Repo_mgr();
		Io_url root_dir = Io_url_.mem_dir_("mem/xowa/file/");
		Xofv_repo_itm repo_comm = Xofv_repo_itm.new_trg_fsys(Xofv_repo_itm.Tid_val_comm, Bry_.new_ascii_("comm"), root_dir.GenSubDir("comm"));
		Xofv_repo_itm repo_wiki = Xofv_repo_itm.new_trg_fsys(Xofv_repo_itm.Tid_val_wiki, Bry_.new_ascii_("wiki"), root_dir.GenSubDir("wiki"));
		repo_mgr.Add(repo_comm).Add(repo_wiki);
		mkr_orig.Setup_repos(repo_comm, repo_wiki);
		mkr_fsdb.Setup_repos(Bry_.new_ascii_("comm"), Bry_.new_ascii_("wiki"));
	}
	public Xofv_file_mgr_fxt Init_xfer_add(Xof_xfer_mkr mkr)	{file_mgr.Reg(mkr.Make()); return this;}
	public Xofv_file_mgr_fxt Init_orig_add(Xof_orig_itm_mkr mkr)	{mkr.Make(file_mgr.Orig_wkr()); return this;}
	public Xofv_file_mgr_fxt Init_fsdb_add(Xof_fsdb_mkr mkr)	{fsdb_mgr.Add(mkr.Make()); return this;}
	public Xofv_file_mgr_fxt Init_cache_add(Xou_cache_itm_mkr mkr)	{mkr.Make(file_mgr.Cache_mgr()); return this;}
	public Xofv_file_mgr_fxt Init_fsys_add(String s) {Io_mgr._.SaveFilStr(s, ""); return this;}
	public Xofv_file_mgr_fxt Exec_process_lnki() {file_mgr.Process_lnki(); return this;}
	public Xofv_file_mgr_fxt Test_fsys_get(String path) {
		Tfds.Eq_true(Io_mgr._.ExistsFil(Io_url_.mem_fil_(path)), "fsys: " + path);
		return this;
	}
	public Xofv_file_mgr_fxt Test_html_get(Xog_html_rsc_mkr mkr) {
		file_mgr.Process_html(html_gui);
		Xog_html_rsc expd = mkr.Make();
		Xog_html_rsc actl = html_gui.Get_by_id(expd.Uid());
		Bry_bfr bfr = Bry_bfr.reset_(255);
		Tfds.Eq_str_lines(Xog_html_rsc_mkr.Xto_str(bfr, expd), Xog_html_rsc_mkr.Xto_str(bfr, actl));
		return this;
	}
	public Xofv_file_mgr_fxt Test_fsdb_download(int expd) {
		Tfds.Eq(expd, fsdb_mgr.Download_count());
		return this;
	}
}
class Xog_html_rsc_mkr {
	private int uid; private String src; private int html_w, html_h;
	public Xog_html_rsc_mkr Init(int uid, String src, int html_w, int html_h) {this.uid = uid; this.src = src; this.html_w = html_w; this.html_h = html_h; return this;}
	private void Reset() {}
	public Xog_html_rsc Make() {
		Xog_html_rsc rv = new Xog_html_rsc(uid, src, html_w, html_h);
		this.Reset();
		return rv;
	}
	public static String Xto_str(Bry_bfr bfr, Xog_html_rsc itm) {
		if (itm == null) return "";
		bfr	.Add_int_variable(itm.Uid()).Add_byte_nl()
			.Add_str_utf8(itm.Src()).Add_byte_nl()
			.Add_int_variable(itm.Html_w()).Add_byte_nl()
			.Add_int_variable(itm.Html_h()).Add_byte_nl()
			;
		return bfr.Xto_str_and_clear();
	}
}
class Xof_xfer_mkr {
	private byte[] ttl_bry; private byte lnki_type; private int xfer_w, xfer_h; private int uid;
	private byte[] redirect_bry; private double upright, thumbtime; private int page;
	public Xof_xfer_mkr() {this.Reset();}
	public Xof_xfer_mkr Upright_(double v) {upright = v; return this;}
	private void Reset() {
		redirect_bry = Bry_.Empty;
		upright = Xop_lnki_tkn.Upright_null;
		thumbtime = Xof_doc_thumb.Null;
		page = Xof_doc_page.Null;
	}
	public Xof_xfer_mkr Init_thumb(int uid, String ttl_str, int xfer_w, int xfer_h) {
		this.lnki_type = Xop_lnki_type.Id_thumb;
		this.uid = uid; this.ttl_bry = Bry_.new_utf8_(ttl_str); this.xfer_w = xfer_w; this.xfer_h = xfer_h;
		return this;
	}
	public Xof_xfer_mkr Init_none(int uid, String ttl_str) {
		this.lnki_type = Xop_lnki_type.Id_none;
		this.uid = uid; this.ttl_bry = Bry_.new_utf8_(ttl_str); this.xfer_w = Xof_img_size.Null; this.xfer_h = Xof_img_size.Null;
		return this;
	}
	public Xof_xfer_itm Make() {
		Xof_xfer_itm rv = new Xof_xfer_itm();
		rv.Init_by_lnki(ttl_bry, redirect_bry, lnki_type, xfer_w, xfer_h, upright, thumbtime, page);
		rv.Set__html_uid_tid(uid, Xof_html_elem.Tid_img);
		this.Reset();
		return rv;
	}
}
class Xof_orig_itm_mkr {
	private byte[] ttl_bry; private int ext, orig_w, orig_h; private Xofv_repo_itm repo;
	private byte[] redirect_bry;
	private Xofv_repo_itm repo_comm, repo_wiki;
	public Xof_orig_itm_mkr() {this.Reset();}
	private void Reset() {
		redirect_bry = Bry_.Empty;
	}
	public void Setup_repos(Xofv_repo_itm repo_comm, Xofv_repo_itm repo_wiki) {this.repo_comm = repo_comm; this.repo_wiki = repo_wiki;}
	public Xof_orig_itm_mkr Init_comm_redirect(String src, String trg, int orig_w, int orig_h) {return Init(Bool_.Y, src, trg, orig_w, orig_h);}
	public Xof_orig_itm_mkr Init_comm(String ttl_str, int orig_w, int orig_h) {return Init(Bool_.Y, ttl_str, null, orig_w, orig_h);}
	public Xof_orig_itm_mkr Init_wiki(String ttl_str, int orig_w, int orig_h) {return Init(Bool_.N, ttl_str, null, orig_w, orig_h);}
	private Xof_orig_itm_mkr Init(boolean repo_is_comm, String ttl_str, String redirect_str, int orig_w, int orig_h) {
		repo = repo_is_comm ? repo_comm : repo_wiki;
		this.ttl_bry = Bry_.new_utf8_(ttl_str); this.orig_w = orig_w; this.orig_h = orig_h;
		this.redirect_bry = redirect_str == null ? Bry_.Empty : Bry_.new_utf8_(redirect_str);
		this.ext = Xof_ext_.new_by_ttl_(ttl_bry).Id();
		return this;
	}
	public void Make(Xof_orig_wkr wkr) {
		wkr.Add_orig(repo.Tid(), ttl_bry, ext, orig_w, orig_h, redirect_bry);
		this.Reset();
	}
}
class Xof_fsdb_mkr {
	private byte[] repo_comm, repo_wiki, repo;
	private boolean file_is_orig;
	private byte[] ttl_bry; private byte lnki_type; private Xof_ext ext; private byte[] md5; private int file_w, file_h;
	private double upright, thumbtime; private int page;
	public Xof_fsdb_mkr() {this.Reset();}
	public void Setup_repos(byte[] repo_comm, byte[] repo_wiki) {this.repo_comm = repo_comm; this.repo_wiki = repo_wiki;}
	private void Reset() {
		upright = Xop_lnki_tkn.Upright_null;
		thumbtime = Xof_doc_thumb.Null;
		page = Xof_doc_page.Null;
	}
	public Xof_fsdb_mkr Init_comm_thum(String ttl_str, int file_w, int file_h)	{return Init(Bool_.Y, Bool_.N, ttl_str, file_w, file_h);}
	public Xof_fsdb_mkr Init_comm_orig(String ttl_str, int file_w, int file_h)	{return Init(Bool_.Y, Bool_.Y, ttl_str, file_w, file_h);}
	public Xof_fsdb_mkr Init(boolean repo_is_commons, boolean file_is_orig, String ttl_str, int file_w, int file_h) {
		this.file_is_orig = file_is_orig; this.lnki_type = file_is_orig ? Xop_lnki_type.Id_none : Xop_lnki_type.Id_thumb; 
		this.repo = repo_is_commons ? repo_comm : repo_wiki;
		this.ttl_bry = Bry_.new_utf8_(ttl_str); this.ext = Xof_ext_.new_by_ttl_(ttl_bry); this.md5 = Xof_xfer_itm_.Md5_(ttl_bry);
		this.file_w = file_w; this.file_h = file_h;
		return this;
	}
	public Xof_fsdb_itm Make() {
		Xof_fsdb_itm rv = new Xof_fsdb_itm();
		rv.Ctor_by_lnki(ttl_bry, ext, md5, lnki_type, file_w, file_h, Xof_patch_upright_tid_.Tid_all, upright, thumbtime, page);
		rv.Orig_repo_name_(repo);
		rv.File_is_orig_(file_is_orig);
		this.Reset();
		return rv;
	}
}
class Xou_cache_itm_mkr {
	private byte[] dir; private byte[] ttl; private boolean is_orig; private int w, h; private double time; private int page; private long size;
	public Xou_cache_itm_mkr() {this.Reset();}
	private void Reset() {
		this.time = Xof_doc_thumb.Null;
		this.page = Xof_doc_page.Null;
		this.h = 200;
		this.size = 1;
	}
	public Xou_cache_itm_mkr Init(String dir_str, String ttl_str, boolean is_orig, int w) {
		this.dir = Bry_.new_utf8_(dir_str);
		this.ttl = Bry_.new_utf8_(ttl_str);
		this.is_orig = is_orig;
		this.w = w;
		return this;
	}
	public void Make(Xof_cache_mgr cache_mgr) {
		cache_mgr.Fil__make(dir, ttl, is_orig, w, h, time, page, size);
		this.Reset();
	}
}
class Xof_fsdb_mgr__test implements Xof_fsdb_mgr {
	private Hash_adp_bry hash = Hash_adp_bry.cs_();
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private int download_count;
	public Xof_fsdb_mgr__test() {this.Clear();}
	public int Download_count() {return download_count;}
	public void Add(Xof_fsdb_itm itm) {
		byte[] key = Bld_key_to_bry(tmp_bfr, itm.Orig_repo_name(), itm.Lnki_ttl(), itm.File_is_orig(), itm.Lnki_w(), itm.Lnki_time(), itm.Lnki_page());
//			Tfds.Write("add:" + String_.new_utf8_(key));
		hash.Add(key, itm);
	}
	public void Clear() {
		download_count = 0;
		Io_mgr._.InitEngine_mem();
		hash.Clear();
	}
	public boolean Download(Xofv_file_itm itm) {
		byte[] key = Bld_key_to_bry(tmp_bfr, itm.File_repo(), itm.File_ttl(), itm.Lnki_is_orig(), itm.Html_w(), itm.Lnki_time(), itm.Lnki_page());
//			Tfds.Write("down:" + String_.new_utf8_(key));
		if (!hash.Has(key)) {
			return false;
		}
		Io_mgr._.SaveFilStr(itm.File_url(), "");
		++download_count;
		return true;
	}
	private static byte[] Bld_key_to_bry(Bry_bfr bfr, byte[] dir, byte[] name, boolean is_orig, int w, double time, int page) {
		bfr	.Add(dir).Add_byte_pipe()
			.Add(name).Add_byte_pipe()
			.Add_int_bool(is_orig).Add_byte_pipe()
			.Add_int_variable(w).Add_byte_pipe()
			.Add_double(time).Add_byte_pipe()
			.Add_int_variable(page).Add_byte_pipe()
			;
		return bfr.Xto_bry_and_clear();
	}
}
class Xog_html_gui__test implements Xog_html_gui {
	private HashAdp hash = HashAdp_.new_();
	public void Clear() {hash.Clear();}
	public Xog_html_rsc Get_by_id(int v) {return (Xog_html_rsc)hash.Fetch(Int_obj_ref.new_(v));}
	public void Update(int id, String src, int w, int h) {
		Xog_html_rsc rsc = new Xog_html_rsc(id, src, w, h);
		hash.Add(Int_obj_ref.new_(id), rsc);
	}
}
class Xog_html_rsc {
	public Xog_html_rsc(int uid, String src, int html_w, int html_h) {this.uid = uid; this.src = src; this.html_w = html_w; this.html_h = html_h;}
	public int Uid() {return uid;} private final int uid;
	public String Src() {return src;} private final String src;
	public int Html_w() {return html_w;} private final int html_w;
	public int Html_h() {return html_h;} private final int html_h;
}
