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
package gplx.xowa.files.fsdb.tsts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.core.envs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.dbs.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.cnvs.*; import gplx.xowa.files.exts.*; import gplx.xowa.guis.cbks.js.*;
import gplx.fsdb.data.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.files.repos.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.lnkis.*;
class Xof_file_fxt {		
	private Xoae_app app; private Xof_fsdb_mgr__sql fsdb_mgr; private Xowe_wiki wiki; private Xof_orig_mgr orig_mgr;
	private final    Fsd_thm_itm tmp_thm = Fsd_thm_itm.new_();
	public void Rls() {}
	public void Reset() {
		Io_mgr.Instance.InitEngine_mem();	// NOTE: files are downloaded to mem_engine, regardless of Db being mem or sqlite; always reset
		Io_url root_url = Xoa_test_.Url_root();
		Xoa_test_.Db_init(root_url);
		app = Xoa_app_fxt.Make__app__edit(Op_sys.Cur().Os_name(), root_url);
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.File__fsdb_mode().Tid__v2__bld__y_();
		this.fsdb_mgr = (Xof_fsdb_mgr__sql)wiki.File_mgr().Fsdb_mgr();
		this.orig_mgr = wiki.File__orig_mgr();
		Xof_repo_fxt.Repos_init(app.File_mgr(), true, wiki);
		Xowe_wiki_.Create(wiki, 1, "dump.xml");
		Xow_db_file text_db = wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__text); text_db.Tbl__text().Create_tbl();
		Fsdb_db_mgr__v2 fsdb_core = Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, Bool_.Y);
		fsdb_mgr.Mnt_mgr().Ctor_by_load(fsdb_core);
		fsdb_mgr.Mnt_mgr().Mnts__get_main().Bin_mgr().Dbs__make("temp.xowa");
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		fsdb_mgr.Bin_mgr().Wkrs__del(Xof_bin_wkr_.Key_http_wmf);	// never use http_wmf wkr for these tests
		Xof_bin_wkr__fsdb_sql bin_wkr_fsdb = (Xof_bin_wkr__fsdb_sql)fsdb_mgr.Bin_mgr().Wkrs__get_or_null(Xof_bin_wkr_.Key_fsdb_wiki);
		fsdb_mgr.Bin_mgr().Resizer_(Xof_img_wkr_resize_img_mok.Instance);
		bin_wkr_fsdb.Resize_allowed_(true);
	}
	public void Init__orig_w_fsdb__commons_orig(String ttl, int w, int h) {
		this.Init_fsdb_db(Xof_fsdb_arg.new_comm(Bool_.N, ttl, w, h));
		this.Init_orig_db(Xof_orig_arg.new_comm(ttl, w, h));
	}
	public void Init_orig_db(Xof_orig_arg arg) {
		orig_mgr.Insert(arg.Repo(), arg.Page(), Xof_ext_.new_by_ttl_(arg.Page()).Id(), arg.W(), arg.H(), arg.Redirect());
	}
	public void Init_fsdb_db(Xof_fsdb_arg arg) {
		Fsm_mnt_itm mnt_itm = fsdb_mgr.Mnt_mgr().Mnts__get_main();
		Fsm_atr_fil atr_fil = mnt_itm.Atr_mgr().Db__core();
		Fsm_bin_fil bin_fil = mnt_itm.Bin_mgr().Dbs__get_nth();
		if (arg.Is_thumb())
			mnt_itm.Insert_thm(tmp_thm, atr_fil, bin_fil, arg.Wiki(), arg.Ttl(), arg.Ext(), arg.W(), arg.H(), arg.Time(), arg.Page(), arg.Bin().length, gplx.core.ios.streams.Io_stream_rdr_.New__mem(arg.Bin()));
		else
			mnt_itm.Insert_img(atr_fil, bin_fil, arg.Wiki(), arg.Ttl(), arg.Ext(), arg.W(), arg.H(), arg.Bin().length, gplx.core.ios.streams.Io_stream_rdr_.New__mem(arg.Bin()));
	}
	public void Exec_get(Xof_exec_arg arg) {
		byte[] ttl_bry = arg.Ttl();
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_at_lnki(arg.Exec_tid(), wiki.Domain_itm().Abrv_xo(), ttl_bry, arg.Lnki_type(), arg.Lnki_upright(), arg.Lnki_w(), arg.Lnki_h(), arg.Lnki_time(), Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
		List_adp itms_list = List_adp_.New(); itms_list.Add(itm);
		orig_mgr.Find_by_list(Ordered_hash_.New_bry(), itms_list, Xof_exec_tid.Tid_wiki_page);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Xow_ns_.Tid__main, ttl_bry);
		Xoae_page page = Xoae_page.New(wiki, ttl);
		fsdb_mgr.Fsdb_search_by_list(itms_list, wiki, page, Xog_js_wkr_.Noop);
		if (arg.Rslt_orig_exists()  != Bool_.__byte)	Tfds.Eq(arg.Rslt_orig_exists()  == Bool_.Y_byte, itm.Orig_exists(), "orig_exists");
		if (arg.Rslt_file_exists()  != Bool_.__byte)	Tfds.Eq(arg.Rslt_file_exists()  == Bool_.Y_byte, itm.File_exists(), "file_exists");
		if (arg.Rslt_file_resized() != Bool_.__byte)	Tfds.Eq(arg.Rslt_file_resized() == Bool_.Y_byte, itm.File_resized(), "file_resize");
	}
	public void Test_fsys_exists_y(String url)			{Test_fsys_exists(url, Bool_.Y);}
	public void Test_fsys_exists_n(String url)			{Test_fsys_exists(url, Bool_.N);}
	public void Test_fsys_exists(String url, boolean expd) {Tfds.Eq(expd, Io_mgr.Instance.ExistsFil(Io_url_.new_any_(url)));}
	public void Test_fsys(String url, String expd_bin)	{Tfds.Eq(expd_bin, Io_mgr.Instance.LoadFilStr(url));}
}
class Xof_repo_fxt {
	public static void Repos_init(Xof_file_mgr file_mgr, boolean src_repo_is_wmf, Xowe_wiki wiki) {
		byte[] src_commons = Bry_.new_a7("src_commons");
		byte[] src_en_wiki = Bry_.new_a7("src_en_wiki");
		byte[] trg_commons = Bry_.new_a7("trg_commons");
		byte[] trg_en_wiki = Bry_.new_a7("trg_en_wiki");
		Ini_repo_add(file_mgr, src_commons, "mem/src/commons.wikimedia.org/", Xow_domain_itm_.Str__commons, false);
		Ini_repo_add(file_mgr, src_en_wiki, "mem/src/en.wikipedia.org/"		, Xow_domain_itm_.Str__enwiki, false);
		Ini_repo_add(file_mgr, trg_commons, "mem/root/common/", Xow_domain_itm_.Str__commons, true).Primary_(true);
		Ini_repo_add(file_mgr, trg_en_wiki, "mem/root/enwiki/", Xow_domain_itm_.Str__enwiki, true).Primary_(true);
		Xowe_repo_mgr wiki_repo_mgr = wiki.File_mgr().Repo_mgr();
		Xof_repo_pair pair = null;
		pair = wiki_repo_mgr.Add_repo(src_commons, trg_commons);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf).Tarball_(!src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);

		pair = wiki_repo_mgr.Add_repo(src_en_wiki, trg_en_wiki);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);
	}
	private static Xof_repo_itm Ini_repo_add(Xof_file_mgr file_mgr, byte[] key, String root, String wiki, boolean trg) {
		return file_mgr.Repo_mgr().Set(String_.new_u8(key), root, wiki).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
	}
}
class Xof_orig_arg {
	Xof_orig_arg(byte repo, byte[] page, int w, int h, byte[] redirect) {this.repo = repo; this.page = page; this.w = w; this.h = h; this.redirect = redirect;}
	public byte			Repo() {return repo;} private final    byte repo;
	public byte[]		Page() {return page;} private final    byte[] page;
	public int			W() {return w;} private final    int w;
	public int			H() {return h;} private final    int h;
	public byte[]		Redirect() {return redirect;} private final    byte[] redirect;
	public static Xof_orig_arg new_comm_file(String page)								{return new_(Bool_.Y, page, Xof_img_size.Size_null, Xof_img_size.Size_null);}
	public static Xof_orig_arg new_comm(String page, int w, int h)						{return new_(Bool_.Y, page, w, h);}
	public static Xof_orig_arg new_wiki(String page, int w, int h)						{return new_(Bool_.N, page, w, h);}
	public static Xof_orig_arg new_wiki_redirect(String src, String trg)				{return new_(Bool_.N, src, 440, 400, trg);}
	public static Xof_orig_arg new_comm_redirect(String src, String trg)				{return new_(Bool_.Y, src, 440, 400, trg);}
	private static Xof_orig_arg new_(boolean repo_is_commons, String page, int w, int h)	{return new_(repo_is_commons, page, w, h, null);}
	public static Xof_orig_arg new_(boolean repo_is_commons, String page, int w, int h, String redirect_str) {
		byte repo = repo_is_commons ? Xof_repo_tid_.Tid__remote : Xof_repo_tid_.Tid__local;
		byte[] redirect = redirect_str == null ? Bry_.Empty : Bry_.new_u8(redirect_str);
		return new Xof_orig_arg(repo, Bry_.new_u8(page), w, h, redirect);
	}
}
class Xof_fsdb_arg {
	Xof_fsdb_arg(byte[] wiki, byte[] ttl, boolean is_thumb, int ext, int w, int h, int time, byte[] bin) {
		this.wiki = wiki; this.ttl = ttl; this.is_thumb = is_thumb; this.w = w; this.h = h; this.time = time; this.ext = ext; this.bin = bin;
	}
	public byte[] Wiki() {return wiki;} private final    byte[] wiki;
	public byte[] Ttl() {return ttl;} private final    byte[] ttl;
	public int Ext() {return ext;} private final    int ext;
	public boolean Is_thumb() {return is_thumb;} private final    boolean is_thumb;
	public int W() {return w;} private final    int w;
	public int H() {return h;} private final    int h;
	public double Time() {return time;} private final    double time;
	public int Page() {return page;} private final    int page = Xof_lnki_page.Null;
	public byte[] Bin() {return bin;} private final    byte[] bin;
	public DateAdp Modified() {return modified;} private final    DateAdp modified = Fsd_thm_tbl.Modified_null;
	public String Hash() {return hash;} private final    String hash = Fsd_thm_tbl.Hash_null;
	public static Xof_fsdb_arg new_comm_file(String ttl)						{return new_(Xow_domain_itm_.Bry__commons, Bool_.N, ttl, Xof_img_size.Null, Xof_img_size.Null, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl)						{return new_(Xow_domain_itm_.Bry__commons, Bool_.Y, ttl, W_default, H_default, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl, int w, int h)			{return new_(Xow_domain_itm_.Bry__commons, Bool_.Y, ttl, w, h, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl, int w, int h, int s)	{return new_(Xow_domain_itm_.Bry__commons, Bool_.Y, ttl, w, h, s);}
	public static Xof_fsdb_arg new_comm_orig(String ttl, int w, int h)			{return new_(Xow_domain_itm_.Bry__commons, Bool_.N, ttl, w, h, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_comm(boolean thumb, String ttl, int w, int h)	{return new_(Xow_domain_itm_.Bry__commons, thumb, ttl, w, h, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_wiki_thumb(String ttl, int w, int h)			{return new_(Xow_domain_itm_.Bry__enwiki, Bool_.Y, ttl, w, h, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_wiki_orig(String ttl, int w, int h)			{return new_(Xow_domain_itm_.Bry__enwiki, Bool_.N, ttl, w, h, Xof_lnki_time.Null_as_int);}
	public static Xof_fsdb_arg new_(byte[] wiki, boolean is_thumb, String ttl_str, int w, int h, int time) {
		byte[] ttl = Bry_.new_u8(ttl_str);
		int ext = Xof_ext_.new_by_ttl_(ttl).Id();
		String bin_str = ext == Xof_ext_.Id_svg ? file_svg_(w, h) : file_img_(w, h);
		byte[] bin = Bry_.new_a7(bin_str);
		return new Xof_fsdb_arg(wiki, ttl, is_thumb, ext, w, h, time, bin);
	}
	private static final int W_default = 220, H_default = 200;
	private static String file_img_(int w, int h) {return String_.Format("{0},{1}", w, h);}
	private static String file_svg_(int w, int h) {return String_.Format("<svg width=\"{0}\" height=\"{1}\" />", w, h);}
}
class Xof_exec_arg {
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte Lnki_type() {return lnki_type;} private byte lnki_type = Xop_lnki_type.Id_thumb;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h = Xop_lnki_tkn.Height_null;
	public double Lnki_upright() {return lnki_upright;} public Xof_exec_arg Lnki_upright_(double v) {lnki_upright = v; return this;} private double lnki_upright = Xop_lnki_tkn.Upright_null;
	public int Lnki_time() {return lnki_time;} public Xof_exec_arg Lnki_time_(int v) {lnki_time = v; return this;} private int lnki_time = Xof_lnki_time.Null_as_int;
	public int Lnki_page() {return lnki_page;} public Xof_exec_arg Lnki_page_(int v) {lnki_page = v; return this;} private int lnki_page = Xof_lnki_page.Null;
	public int Exec_tid() {return exec_tid;} public Xof_exec_arg Exec_tid_(int v) {exec_tid = v; return this;} private int exec_tid = Xof_exec_tid.Tid_wiki_page;
	public byte Rslt_orig_exists() {return rslt_orig_exists;} private byte rslt_orig_exists = Bool_.__byte;
	public byte Rslt_file_exists() {return rslt_file_exists;} private byte rslt_file_exists = Bool_.__byte;
	public byte Rslt_file_resized() {return rslt_file_resized;} private byte rslt_file_resized = Bool_.__byte;
	public boolean Lnki_type_is_thumb() {return Xop_lnki_type.Id_defaults_to_thumb(lnki_type);}
	public Xof_exec_arg Rslt_orig_exists_n()	{rslt_orig_exists = Bool_.N_byte; return this;}
	public Xof_exec_arg Rslt_orig_exists_y()	{rslt_orig_exists = Bool_.Y_byte; return this;}
	public Xof_exec_arg Rslt_file_exists_n()	{rslt_file_exists = Bool_.N_byte; return this;}
	public Xof_exec_arg Rslt_file_exists_y()	{rslt_file_exists = Bool_.Y_byte; return this;}
	public Xof_exec_arg Rslt_file_resized_n()	{rslt_file_resized = Bool_.N_byte; return this;}
	public Xof_exec_arg Rslt_file_resized_y()	{rslt_file_resized = Bool_.Y_byte; return this;}
	public static Xof_exec_arg new_thumb(String ttl)					{return new_(ttl, Xop_lnki_type.Id_thumb, 220, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_thumb(String ttl, int w)				{return new_(ttl, Xop_lnki_type.Id_thumb, w, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_thumb(String ttl, int w, int h)		{return new_(ttl, Xop_lnki_type.Id_thumb, w, h);}
	public static Xof_exec_arg new_orig(String ttl)						{return new_(ttl, Xop_lnki_type.Id_null, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_(String ttl, byte type, int w, int h) {
		Xof_exec_arg rv = new Xof_exec_arg();
		rv.ttl = Bry_.new_u8(ttl);
		rv.lnki_type = type;
		rv.lnki_w = w;
		rv.lnki_h = h;
		return rv;
	}
}
