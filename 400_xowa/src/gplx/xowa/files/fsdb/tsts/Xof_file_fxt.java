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
package gplx.xowa.files.fsdb.tsts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.*; import gplx.dbs.*; import gplx.xowa.files.wiki_orig.*; import gplx.xowa.files.qrys.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.cnvs.*;
class Xof_file_fxt {		
	private Fsdb_xtn_thm_itm tmp_thm = Fsdb_xtn_thm_itm.new_(); private Fsdb_xtn_img_itm tmp_img = new Fsdb_xtn_img_itm();
	private Xof_fsdb_mgr fsdb_mgr; private Xof_qry_wrk_mock qry_wkr_mock = new Xof_qry_wrk_mock();
	public boolean Db_skip() {return Xoa_test_.Db_skip();}
	public void Reset() {this.Reset(Bool_.__byte);}
	public void Reset(byte mem_tid) {
		Xoa_app app = Xoa_app_fxt.app_();
		Xow_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Xof_repo_fxt.Repos_init(app.File_mgr(), true, wiki);
		fsdb_mgr = fsdb_mgr_new_(mem_tid, wiki);
		qry_wkr_mock.Clear();
		fsdb_mgr.Init_by_wiki(wiki, Xoa_test_.Url_file_enwiki(), Io_url_.mem_dir_("mem/root/"), wiki.File_mgr().Repo_mgr());
		fsdb_mgr.Qry_mgr().Add(qry_wkr_mock);
		fsdb_mgr.Bin_mgr().Add(fsdb_mgr.Bin_wkr_fsdb());
		fsdb_mgr.Bin_mgr().Resizer_(Xof_img_wkr_resize_img_mok._);
		fsdb_mgr.Bin_wkr_fsdb().Bin_wkr_resize_(true);
	}
	private static Xof_fsdb_mgr fsdb_mgr_new_(byte mem_tid, Xow_wiki wiki) {
		boolean mem = false;
		switch (mem_tid) {
			case Bool_.Y_byte: mem = true; break;
			case Bool_.N_byte: mem = false; break;
			case Bool_.__byte: mem = Xoa_test_.Fsdb_is_mem; break;
		}
		Xof_fsdb_mgr rv = mem ? (Xof_fsdb_mgr)new Xof_fsdb_mgr_mem() : (Xof_fsdb_mgr)new Xof_fsdb_mgr_sql(wiki);
		if (!mem) Io_mgr._.DeleteDirDeep(Xoa_test_.Url_file_enwiki());
		return rv;
	}
	public void Init_qry_xowa__bin_fsdb__commons_orig(String ttl, int w, int h) {
		this.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons(Bool_.N, ttl, w, h));
		this.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons(ttl, w, h));
	}
	public void Init_qry_xowa__bin_fsdb__en_wiki_orig(String ttl, int w, int h) {
		this.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_en_wiki(Bool_.N, ttl, w, h));
		this.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki(ttl, w, h));
	}
	public void Init_bin_fsdb(Xof_fsdb_arg_init_bin arg) {
		if (arg.Is_thumb())
			fsdb_mgr.Thm_insert(tmp_thm, arg.Wiki(), arg.Ttl(), arg.Ext_id(), arg.W(), arg.H(), arg.Thumbtime(), arg.Page(), arg.Modified(), arg.Hash(), arg.Bin().length, gplx.ios.Io_stream_rdr_.mem_(arg.Bin()));
		else
			fsdb_mgr.Img_insert(tmp_img, arg.Wiki(), arg.Ttl(), arg.Ext_id(), arg.W(), arg.H(), arg.Modified(), arg.Hash(), arg.Bin().length, gplx.ios.Io_stream_rdr_.mem_(arg.Bin()));
	}
	public void Init_qry_xowa(Xof_fsdb_arg_init_qry arg) {
		qry_wkr_mock.Add_wiki_size(arg.Ttl(), arg.Wiki(), arg.W(), arg.H());
		if (arg.Redirect_trg() != null)
			qry_wkr_mock.Add_redirect(arg.Ttl(), arg.Redirect_trg());
	}
	public void Exec_get(Xof_fsdb_arg_exec_get arg) {
		Xof_fsdb_itm itm = itm_(String_.new_utf8_(arg.Ttl()), arg.Lnki_type(), arg.Lnki_w(), arg.Lnki_h(), arg.Lnki_upright(), arg.Lnki_thumbtime());
		ListAdp itms_list = ListAdp_.new_();
		itms_list.Add(itm);
		fsdb_mgr.Reg_select(Xoa_page.Empty, arg.Exec_tid(), itms_list);
		if (arg.Rslt_reg() != Xof_wiki_orig_wkr_.Tid_null) Tfds.Eq(arg.Rslt_reg(), itm.Rslt_reg(), "rslt_reg");
		if (arg.Rslt_qry() != Xof_qry_wkr_.Tid_null) Tfds.Eq(arg.Rslt_qry(), itm.Rslt_qry(), "rslt_qry");
		if (arg.Rslt_bin() != Xof_bin_wkr_.Tid_null) Tfds.Eq(arg.Rslt_bin(), itm.Rslt_bin(), "rslt_bin");
		if (arg.Rslt_cnv() != Xof_cnv_wkr_.Tid_null) Tfds.Eq(arg.Rslt_cnv(), itm.Rslt_cnv(), "rslt_cnv");
	}
	public void Test_fsys(String url, String expd_bin) {Tfds.Eq(expd_bin, Io_mgr._.LoadFilStr(url));}
	public void Test_fsys_exists_y(String url) {Test_fsys_exists(url, Bool_.Y);}
	public void Test_fsys_exists_n(String url) {Test_fsys_exists(url, Bool_.N);}
	public void Test_fsys_exists(String url, boolean expd) {Tfds.Eq(expd, Io_mgr._.ExistsFil(Io_url_.new_any_(url)));}
	public void Test_regy(String ttl, Xof_fsdb_arg_reg_get expd) {
		Xof_fsdb_itm itm = Exec_reg_select_itm(ttl);
		if (expd.Orig_w()	!= Xop_lnki_tkn.Width_null)		Tfds.Eq(expd.Orig_w(), itm.Orig_w());
		if (expd.Orig_h()	!= Xop_lnki_tkn.Height_null)	Tfds.Eq(expd.Orig_h(), itm.Orig_h());
		if (expd.Redirect()	!= null)						Tfds.Eq_bry(expd.Redirect(), itm.Orig_redirect());
		if (expd.Repo_id()	!= Xof_repo_itm.Repo_null)		Tfds.Eq(expd.Repo_id(), itm.Orig_repo());
	}
	public Xof_fsdb_itm Test_regy_pass(String key) {
		Xof_fsdb_itm itm = Exec_reg_select_itm(key) ;
		Tfds.Eq_false(itm == null);
		return itm;
	}
	public void Test_regy_missing_qry(String key)		{Tfds.Eq(Xof_wiki_orig_wkr_.Tid_missing_qry, Exec_reg_select_itm(key).Rslt_reg());}
	public void Test_regy_missing_bin(String key)		{Tfds.Eq(Xof_wiki_orig_wkr_.Tid_missing_bin, Exec_reg_select_itm(key).Rslt_reg());}
	private Xof_fsdb_itm itm_(String ttl_str, byte type, int w, int h, double upright, int thumbtime) {
		byte[] ttl_bry = Bry_.new_ascii_(ttl_str);
		byte[] md5 = Xof_xfer_itm_.Md5_(ttl_bry);
		Xof_ext ext = Xof_ext_.new_by_ttl_(ttl_bry);
		return new Xof_fsdb_itm().Init_by_lnki(ttl_bry, ext, md5, type, w, h, true, upright, thumbtime, Xof_doc_page.Null);
	}
	public void Test_itm_ext(Xof_fsdb_itm itm, int expd_ext_id) {Tfds.Eq(expd_ext_id, itm.Lnki_ext().Id());}
	public void Rls() {fsdb_mgr.Rls();}
	private Xof_fsdb_itm Exec_reg_select_itm(String ttl) {
		Xof_fsdb_itm itm = itm_(ttl, Xop_lnki_type.Id_null, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null, Xop_lnki_tkn.Upright_null, Xof_doc_thumb.Null_as_int);
		ListAdp list = ListAdp_.new_();
		list.Add(itm);
		fsdb_mgr.Reg_select(Xoa_page.Empty, Xof_exec_tid.Tid_wiki_page, list);
		return itm;
	}
	public static String file_img_(int w, int h) {return String_.Format("{0},{1}", w, h);}
	public static String file_svg_(int w, int h) {return String_.Format("<svg width=\"{0}\" height=\"{1}\" />", w, h);}
}
class Xof_fsdb_arg_init_bin {
	public byte[] Wiki() {return wiki;} public Xof_fsdb_arg_init_bin Wiki_(byte[] v) {wiki = v; return this;} private byte[] wiki;
	public byte[] Ttl() {return ttl;} public Xof_fsdb_arg_init_bin Ttl_(byte[] v) {ttl = v; return this;} private byte[] ttl;
	public int Ext_id() {return ext_id;} public Xof_fsdb_arg_init_bin Ext_id_(int v) {ext_id = v; return this;} private int ext_id;
	public int W() {return w;} public Xof_fsdb_arg_init_bin W_(int v) {w = v; return this;} private int w = W_default;
	public double Thumbtime() {return thumbtime;} public Xof_fsdb_arg_init_bin Thumbtime_(double v) {thumbtime = v; return this;} private double thumbtime = Xof_doc_thumb.Null;
	public int Page() {return page;} public Xof_fsdb_arg_init_bin Page_(int v) {page = v; return this;} private int page = Xof_doc_page.Null;
	public int H() {return h;} public Xof_fsdb_arg_init_bin H_(int v) {h = v; return this;} private int h = H_default;
	public DateAdp Modified() {return modified;} public Xof_fsdb_arg_init_bin Modified_(DateAdp v) {modified = v; return this;} private DateAdp modified = Fsdb_xtn_thm_tbl.Modified_null;
	public String Hash() {return hash;} public Xof_fsdb_arg_init_bin Hash_(String v) {hash = v; return this;} private String hash = Fsdb_xtn_thm_tbl.Hash_null;
	public byte[] Bin() {return bin;} public Xof_fsdb_arg_init_bin Bin_(byte[] v) {bin = v; return this;} private byte[] bin;
	public boolean Is_thumb() {return is_thumb;} public Xof_fsdb_arg_init_bin Is_thumb_(boolean v) {is_thumb = v; return this;} private boolean is_thumb;
	public Xof_fsdb_arg_init_bin Ttl_(String v) {ttl = Bry_.new_utf8_(v); return this;}
	public Xof_fsdb_arg_init_bin Ext_id_() {ext_id = Xof_ext_.new_by_ttl_(ttl).Id(); return this;}
	public Xof_fsdb_arg_init_bin Bin_(String v) {return Bin_(Bry_.new_ascii_(v));} 
	public Xof_fsdb_arg_init_bin Bin_by_size_() {
		String bin_str = ext_id == Xof_ext_.Id_svg ? Xof_file_fxt.file_svg_(w, h) : Xof_file_fxt.file_img_(w, h);
		return Bin_(Bry_.new_ascii_(bin_str));
	}
	public Xof_fsdb_arg_init_bin Init_commons_file(String ttl)						{return Init(Xow_wiki_.Domain_commons_bry, Bool_.N, ttl, Xof_img_size.Null, Xof_img_size.Null, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_commons_thumb(String ttl)						{return Init(Xow_wiki_.Domain_commons_bry, Bool_.Y, ttl, W_default, H_default, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_commons_thumb(String ttl, int w, int h)		{return Init(Xow_wiki_.Domain_commons_bry, Bool_.Y, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_commons_thumb(String ttl, int w, int h, int s){return Init(Xow_wiki_.Domain_commons_bry, Bool_.Y, ttl, w, h, s);}
	public Xof_fsdb_arg_init_bin Init_commons_orig(String ttl, int w, int h)		{return Init(Xow_wiki_.Domain_commons_bry, Bool_.N, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_commons(boolean thumb, String ttl, int w, int h)	{return Init(Xow_wiki_.Domain_commons_bry, thumb, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_en_wiki_thumb(String ttl)						{return Init(Xow_wiki_.Domain_en_wiki_bry, Bool_.Y, ttl, W_default, H_default, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_en_wiki_thumb(String ttl, int w, int h)		{return Init(Xow_wiki_.Domain_en_wiki_bry, Bool_.Y, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_en_wiki_thumb(String ttl, int w, int h, int s){return Init(Xow_wiki_.Domain_en_wiki_bry, Bool_.Y, ttl, w, h, s);}
	public Xof_fsdb_arg_init_bin Init_en_wiki_orig(String ttl, int w, int h)		{return Init(Xow_wiki_.Domain_en_wiki_bry, Bool_.N, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init_en_wiki(boolean thumb, String ttl, int w, int h)	{return Init(Xow_wiki_.Domain_en_wiki_bry, thumb, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public Xof_fsdb_arg_init_bin Init(byte[] wiki, boolean thumb, String ttl, int w, int h, int thumbtime) {
		return Wiki_(wiki).Is_thumb_(thumb).Ttl_(ttl).W_(w).H_(h).Thumbtime_(thumbtime).Ext_id_().Bin_by_size_();
	}
	public static final int W_default = 220, H_default = 200;
	public static Xof_fsdb_arg_init_bin new_() {return new Xof_fsdb_arg_init_bin();} Xof_fsdb_arg_init_bin() {}
}
class Xof_fsdb_arg_init_qry {
	public byte[] Wiki() {return wiki;} private byte[] wiki;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Redirect_trg() {return redirect_trg;} private byte[] redirect_trg;
	public int W() {return w;} private int w = W_default;
	public int H() {return h;} private int h = H_default;
	public Xof_fsdb_arg_init_qry Init_commons_file(String ttl)					{this.wiki = Xow_wiki_.Domain_commons_bry; this.ttl = Bry_.new_ascii_(ttl); this.w = Xof_img_size.Size_null; this.h = Xof_img_size.Size_null; return this;}
	public Xof_fsdb_arg_init_qry Init_commons(String ttl, int w, int h)			{this.wiki = Xow_wiki_.Domain_commons_bry; this.ttl = Bry_.new_ascii_(ttl); this.w = w; this.h = h; return this;}
	public Xof_fsdb_arg_init_qry Init_en_wiki(String ttl, int w, int h)			{this.wiki = Xow_wiki_.Domain_en_wiki_bry; this.ttl = Bry_.new_ascii_(ttl); this.w = w; this.h = h; return this;}
	public Xof_fsdb_arg_init_qry Init_en_wiki_redirect(String src, String trg)	{this.wiki = Xow_wiki_.Domain_en_wiki_bry; this.ttl = Bry_.new_ascii_(src); this.redirect_trg = Bry_.new_ascii_(trg); return this;}
	public Xof_fsdb_arg_init_qry Init_commons_redirect(String src, String trg)	{this.wiki = Xow_wiki_.Domain_commons_bry; this.ttl = Bry_.new_ascii_(src); this.redirect_trg = Bry_.new_ascii_(trg); return this;}
	public static Xof_fsdb_arg_init_qry new_() {return new Xof_fsdb_arg_init_qry();} Xof_fsdb_arg_init_qry() {}
	public static final int W_default = 440, H_default = 400;
}
class Xof_fsdb_arg_exec_get {
	public byte[] Ttl() {return ttl;} public Xof_fsdb_arg_exec_get Ttl_(byte[] v) {ttl = v; return this;} private byte[] ttl;
	public byte Exec_tid() {return exec_tid;} public Xof_fsdb_arg_exec_get Exec_tid_(byte v) {exec_tid = v; return this;} private byte exec_tid = Xof_exec_tid.Tid_wiki_page;
	public byte Lnki_type() {return lnki_type;} public Xof_fsdb_arg_exec_get Lnki_type_(byte v) {lnki_type = v; return this;} private byte lnki_type = Xop_lnki_type.Id_thumb;
	public int Lnki_w() {return lnki_w;} public Xof_fsdb_arg_exec_get Lnki_w_(int v) {lnki_w = v; return this;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} public Xof_fsdb_arg_exec_get Lnki_h_(int v) {lnki_h = v; return this;} private int lnki_h = Xop_lnki_tkn.Height_null;
	public double Lnki_upright() {return lnki_upright;} public Xof_fsdb_arg_exec_get Lnki_upright_(double v) {lnki_upright = v; return this;} private double lnki_upright = Xop_lnki_tkn.Upright_null;
	public int Lnki_thumbtime() {return lnki_thumbtime;} public Xof_fsdb_arg_exec_get Lnki_thumbtime_(int v) {lnki_thumbtime = v; return this;} private int lnki_thumbtime = Xof_doc_thumb.Null_as_int;
	public int Lnki_page() {return lnki_page;} public Xof_fsdb_arg_exec_get Lnki_page_(int v) {lnki_page = v; return this;} private int lnki_page = Xof_doc_page.Null;
	public byte Rslt_reg() {return rslt_reg;} public Xof_fsdb_arg_exec_get Rslt_reg_(byte v) {rslt_reg = v; return this;} private byte rslt_reg = Xof_wiki_orig_wkr_.Tid_null;
	public byte Rslt_qry() {return rslt_qry;} public Xof_fsdb_arg_exec_get Rslt_qry_(byte v) {rslt_qry = v; return this;} private byte rslt_qry = Xof_qry_wkr_.Tid_null;
	public byte Rslt_bin() {return rslt_bin;} public Xof_fsdb_arg_exec_get Rslt_bin_(byte v) {rslt_bin = v; return this;} private byte rslt_bin = Xof_bin_wkr_.Tid_null;
	public byte Rslt_cnv() {return rslt_cnv;} public Xof_fsdb_arg_exec_get Rslt_cnv_(byte v) {rslt_cnv = v; return this;} private byte rslt_cnv = Xof_cnv_wkr_.Tid_null;
	public boolean Lnki_type_is_thumb() {return Xop_lnki_type.Id_defaults_to_thumb(lnki_type);}
	public Xof_fsdb_arg_exec_get Init_thumb(String ttl)					{return Init(ttl, Xop_lnki_type.Id_thumb, 220, Xop_lnki_tkn.Height_null);}
	public Xof_fsdb_arg_exec_get Init_thumb(String ttl, int w)			{return Init(ttl, Xop_lnki_type.Id_thumb, w, Xop_lnki_tkn.Height_null);}
	public Xof_fsdb_arg_exec_get Init_thumb(String ttl, int w, int h)	{return Init(ttl, Xop_lnki_type.Id_thumb, w, h);}
	public Xof_fsdb_arg_exec_get Init_orig(String ttl)					{return Init(ttl, Xop_lnki_type.Id_null, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null);}
	public Xof_fsdb_arg_exec_get Init(String ttl, byte type, int w, int h) {
		this.ttl = Bry_.new_utf8_(ttl);
		this.lnki_type = type;
		this.lnki_w = w;
		this.lnki_h = h;
		return this;
	}
	public Xof_fsdb_arg_exec_get Rslt_reg_noop() {rslt_reg = Xof_wiki_orig_wkr_.Tid_noop; return this;}
	public Xof_fsdb_arg_exec_get Rslt_reg_missing_reg() {rslt_reg = Xof_wiki_orig_wkr_.Tid_missing_reg; return this;}
	public Xof_fsdb_arg_exec_get Rslt_reg_missing_qry() {rslt_reg = Xof_wiki_orig_wkr_.Tid_missing_qry; return this;}
	public Xof_fsdb_arg_exec_get Rslt_reg_missing_bin() {rslt_reg = Xof_wiki_orig_wkr_.Tid_missing_bin; return this;}
	public Xof_fsdb_arg_exec_get Rslt_reg_found_orig() {rslt_reg = Xof_wiki_orig_wkr_.Tid_found_orig; return this;}
	public Xof_fsdb_arg_exec_get Rslt_qry_mock_() {rslt_qry = Xof_qry_wkr_.Tid_mock; return this;}
	public Xof_fsdb_arg_exec_get Rslt_qry_noop_() {rslt_qry = Xof_qry_wkr_.Tid_noop; return this;}
	public Xof_fsdb_arg_exec_get Rslt_qry_unavailable_() {rslt_qry = Xof_qry_wkr_.Tid_missing; return this;}
	public Xof_fsdb_arg_exec_get Rslt_bin_fsdb_() {rslt_bin = Xof_bin_wkr_.Tid_fsdb_wiki; return this;}
	public Xof_fsdb_arg_exec_get Rslt_bin_not_found_() {rslt_bin = Xof_bin_wkr_.Tid_not_found; return this;}
	public Xof_fsdb_arg_exec_get Rslt_bin_noop_() {rslt_bin = Xof_bin_wkr_.Tid_noop; return this;}
	public Xof_fsdb_arg_exec_get Rslt_cnv_y_() {rslt_cnv = Xof_cnv_wkr_.Tid_y; return this;}
	public Xof_fsdb_arg_exec_get Rslt_cnv_n_() {rslt_cnv = Xof_cnv_wkr_.Tid_n; return this;}
	public static Xof_fsdb_arg_exec_get new_() {return new Xof_fsdb_arg_exec_get();} Xof_fsdb_arg_exec_get() {}
}
class Xof_repo_fxt {
	public static void Repos_init(Xof_file_mgr file_mgr, boolean src_repo_is_wmf, Xow_wiki wiki) {
		byte[] src_commons = Bry_.new_ascii_("src_commons");
		byte[] src_en_wiki = Bry_.new_ascii_("src_en_wiki");
		byte[] trg_commons = Bry_.new_ascii_("trg_commons");
		byte[] trg_en_wiki = Bry_.new_ascii_("trg_en_wiki");
		Ini_repo_add(file_mgr, src_commons, "mem/src/commons.wikimedia.org/", Xow_wiki_.Domain_commons_str, false);
		Ini_repo_add(file_mgr, src_en_wiki, "mem/src/en.wikipedia.org/"		, Xow_wiki_.Domain_enwiki_str, false);
		Ini_repo_add(file_mgr, trg_commons, "mem/root/common/", Xow_wiki_.Domain_commons_str, true).Primary_(true);
		Ini_repo_add(file_mgr, trg_en_wiki, "mem/root/enwiki/", Xow_wiki_.Domain_enwiki_str, true).Primary_(true);
		Xow_repo_mgr wiki_repo_mgr = wiki.File_mgr().Repo_mgr();
		Xof_repo_pair pair = null;
		pair = wiki_repo_mgr.Add_repo(src_commons, trg_commons);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf).Tarball_(!src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);

		pair = wiki_repo_mgr.Add_repo(src_en_wiki, trg_en_wiki);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);
	}
	private static Xof_repo_itm Ini_repo_add(Xof_file_mgr file_mgr, byte[] key, String root, String wiki, boolean trg) {
		return file_mgr.Repo_mgr().Set(String_.new_utf8_(key), root, wiki).Ext_rules_(Xoft_rule_grp.Grp_app_default).Dir_depth_(2);
	}
}
class Xof_fsdb_arg_reg_get {
	public byte[] Ttl() {return ttl;} public Xof_fsdb_arg_reg_get Ttl_(byte[] v) {ttl = v; return this;} private byte[] ttl;
	public byte[] Redirect() {return redirect;} public Xof_fsdb_arg_reg_get Redirect_(byte[] v) {redirect = v; return this;} private byte[] redirect = null;
	public byte Repo_id() {return repo_id;} public Xof_fsdb_arg_reg_get Repo_id_(byte v) {repo_id = v; return this;} private byte repo_id = Xof_repo_itm.Repo_null;
	public int Orig_w() {return orig_w;} public Xof_fsdb_arg_reg_get Orig_w_(int v) {orig_w = v; return this;} private int orig_w = Xop_lnki_tkn.Width_null;
	public int Orig_h() {return orig_h;} public Xof_fsdb_arg_reg_get Orig_h_(int v) {orig_h = v; return this;} private int orig_h = Xop_lnki_tkn.Height_null;
	public Xof_fsdb_arg_reg_get Orig_size_(int w, int h) {orig_w = w; orig_h = h; return this;}
	public Xof_fsdb_arg_reg_get Init_commons(int w, int h)					{return Init(Xof_repo_itm.Repo_remote, w, h, String_.Empty);}
	public Xof_fsdb_arg_reg_get Init_commons(int w, int h, String redirect)	{return Init(Xof_repo_itm.Repo_remote, w, h, redirect);}
	public Xof_fsdb_arg_reg_get Init_en_wiki(int w, int h)					{return Init(Xof_repo_itm.Repo_local, w, h, String_.Empty);}
	public Xof_fsdb_arg_reg_get Init_en_wiki(int w, int h, String redirect) {return Init(Xof_repo_itm.Repo_local, w, h, redirect);}
	public Xof_fsdb_arg_reg_get Init(byte repo_id, int w, int h, String redirect) {
		this.repo_id = repo_id; this.orig_w = w; this.orig_h = h; this.redirect = Bry_.new_utf8_(redirect);
		return this;
	}
	public static Xof_fsdb_arg_reg_get new_() {return new Xof_fsdb_arg_reg_get();}
}
