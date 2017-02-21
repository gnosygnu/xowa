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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.caches.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.*; import gplx.xowa.files.origs.*;
public class Xofv_file_mgr_tst {
//		@Before public void init() {fxt.Clear();} private final Xofv_file_mgr_fxt fxt = new Xofv_file_mgr_fxt();
	@After  public void term() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;}
	@Test  public void Stub() {}
//		@Test   public void Thumb() {
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
//				.Exec_process_lnki()
//				.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/220px.png", 220, 200))
//				.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")
//				.Test_fsdb_download(1);
//				;
//		}
//		@Test   public void Orig() {
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_orig("A.png", 440, 400))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_none(0, "A.png"))
//				.Exec_process_lnki()
//				.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/orig/7/0/A.png", 440, 400))
//				.Test_fsys_get("mem/xowa/file/comm/orig/7/0/A.png")
//				.Test_fsdb_download(1);
//				;
//		}
//		@Test   public void Img_size() {	// PURPOSE: test integration of Xof_img_size
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 110, 100))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", Xof_img_size.Null, Xof_img_size.Null).Upright_(.5f))
//				.Exec_process_lnki()
//				.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/110px.png", 110, 100))
//				.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/110px.png")
//				.Test_fsdb_download(1);
//				;
//		}
//		@Test   public void Orig_mgr() {	// PURPOSE: test integration of Orig_mgr
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm_redirect("B.jpg", "A.png", 440, 400))	// B.jpg redirects to A.png
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "B.jpg", 220, 200))
//				.Exec_process_lnki()
//				.Test_html_get(fxt.Mkr_html().Init(0, "file:///mem/xowa/file/comm/thumb/7/0/A.png/220px.png", 220, 200))
//				.Test_fsys_get("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")
//				.Test_fsdb_download(1);
//				;
//		}
//		@Test   public void Cache_exists() {
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
//				.Init_cache_add(fxt.Mkr_cache().Init("comm", "A.png", Bool_.N, 220))	// add to cache
//				.Init_fsys_add("mem/xowa/file/comm/thumb/7/0/A.png/220px.png")			// copy file to fsys
//				.Exec_process_lnki()
//				.Test_fsdb_download(0)	// skip download
//				;
//		}
//		@Test   public void Cache_absent() {
//			fxt	.Init_orig_add(fxt.Mkr_orig().Init_comm("A.png", 440, 400))
//				.Init_fsdb_add(fxt.Mkr_fsdb().Init_comm_thum("A.png", 220, 200))
//				.Init_xfer_add(fxt.Mkr_xfer().Init_thumb(0, "A.png", 220, 200))
//				.Init_cache_add(fxt.Mkr_cache().Init("commons", "A.png", Bool_.N, 220))	// add to cache
//				.Exec_process_lnki()
//				.Test_fsdb_download(1)	// do download
//				;
//		}
}
//	class Xofv_file_mgr_fxt {
//		private Xofv_file_mgr file_mgr;
//		public Xof_xfer_mkr Mkr_xfer() {return mkr_xfer;} private final Xof_xfer_mkr mkr_xfer = new Xof_xfer_mkr();
//		public Xof_orig_itm_mkr Mkr_orig() {return mkr_orig;} private final Xof_orig_itm_mkr mkr_orig = new Xof_orig_itm_mkr();
//		public Xof_fsdb_mkr Mkr_fsdb() {return mkr_fsdb;} private final Xof_fsdb_mkr mkr_fsdb = new Xof_fsdb_mkr();		
//		public Xou_cache_itm_mkr Mkr_cache() {return mkr_cache;} private final Xou_cache_itm_mkr mkr_cache = new Xou_cache_itm_mkr();
//		public void Clear() {
//			file_mgr = new Xofv_file_mgr(Bry_.Empty);
//			Clear_repos();
//		}
//		private void Clear_repos() {
//			Xofv_repo_mgr repo_mgr = file_mgr.Repo_mgr();
//			Io_url root_dir = Io_url_.mem_dir_("mem/xowa/file/");
//			Xofv_repo_itm repo_comm = Xofv_repo_itm.new_trg_fsys(Xofv_repo_itm.Tid_val_comm, Bry_.new_a7("comm"), root_dir.GenSubDir("comm"));
//			Xofv_repo_itm repo_wiki = Xofv_repo_itm.new_trg_fsys(Xofv_repo_itm.Tid_val_wiki, Bry_.new_a7("wiki"), root_dir.GenSubDir("wiki"));
//			repo_mgr.Add(repo_comm).Add(repo_wiki);
//			mkr_orig.Setup_repos(repo_comm, repo_wiki);
//			mkr_fsdb.Setup_repos(Bry_.new_a7("comm"), Bry_.new_a7("wiki"));
//		}
//		public Xofv_file_mgr_fxt Init_xfer_add(Xof_xfer_mkr mkr)	{file_mgr.Reg(mkr.Make()); return this;}
//		public Xofv_file_mgr_fxt Init_cache_add(Xou_cache_itm_mkr mkr)	{mkr.Make(file_mgr.Cache_mgr()); return this;}
//		public Xofv_file_mgr_fxt Init_fsys_add(String s) {Io_mgr.Instance.SaveFilStr(s, ""); return this;}
//		public Xofv_file_mgr_fxt Exec_process_lnki() {file_mgr.Process_lnki(); return this;}
//		public Xofv_file_mgr_fxt Test_fsys_get(String path) {
//			Tfds.Eq_true(Io_mgr.Instance.ExistsFil(Io_url_.mem_fil_(path)), "fsys: " + path);
//			return this;
//		}
//	}
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
		this.ttl_bry = Bry_.new_u8(ttl_str); this.orig_w = orig_w; this.orig_h = orig_h;
		this.redirect_bry = redirect_str == null ? Bry_.Empty : Bry_.new_u8(redirect_str);
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
	private byte[] ttl_bry; private byte lnki_type; private int file_w, file_h;
	private double upright, time; private int page;
	public Xof_fsdb_mkr() {this.Reset();}
	public void Setup_repos(byte[] repo_comm, byte[] repo_wiki) {this.repo_comm = repo_comm; this.repo_wiki = repo_wiki;}
	private void Reset() {
		upright = Xop_lnki_tkn.Upright_null;
		time = Xof_lnki_time.Null;
		page = Xof_lnki_page.Null;
	}
	public Xof_fsdb_mkr Init_comm_thum(String ttl_str, int file_w, int file_h)	{return Init(Bool_.Y, Bool_.N, ttl_str, file_w, file_h);}
	public Xof_fsdb_mkr Init_comm_orig(String ttl_str, int file_w, int file_h)	{return Init(Bool_.Y, Bool_.Y, ttl_str, file_w, file_h);}
	public Xof_fsdb_mkr Init(boolean repo_is_commons, boolean file_is_orig, String ttl_str, int file_w, int file_h) {
		this.lnki_type = file_is_orig ? Xop_lnki_type.Id_none : Xop_lnki_type.Id_thumb; 
		this.repo = repo_is_commons ? repo_comm : repo_wiki;
		this.ttl_bry = Bry_.new_u8(ttl_str);
		this.file_w = file_w; this.file_h = file_h;
		return this;
	}
	public Xof_fsdb_itm Make() {
		Xof_fsdb_itm rv = new Xof_fsdb_itm();
		rv.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, Bry_.new_a7("en.w"), ttl_bry, lnki_type, upright, file_w, file_h, time, page, Xof_patch_upright_tid_.Tid_all);
		rv.Orig_repo_name_(repo);
		this.Reset();
		return rv;
	}
}
class Xou_cache_itm_mkr {
//		private byte[] dir; private byte[] ttl; private boolean is_orig; private int w, h; private double time; private int page; private long size;
	public Xou_cache_itm_mkr() {this.Reset();}
	private void Reset() {
//			this.time = Xof_lnki_time.Null;
//			this.page = Xof_lnki_page.Null;
//			this.h = 200;
//			this.size = 1;
	}
	public Xou_cache_itm_mkr Init(String dir_str, String ttl_str, boolean is_orig, int w) {
//			this.dir = Bry_.new_u8(dir_str);
//			this.ttl = Bry_.new_u8(ttl_str);
//			this.is_orig = is_orig;
//			this.w = w;
		return this;
	}
	public void Make(Xof_cache_mgr cache_mgr) {
//			cache_mgr.Fil__make(dir, ttl, is_orig, w, h, time, page, size);
		this.Reset();
	}
}
