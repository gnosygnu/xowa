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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
import gplx.langs.htmls.*;	 import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.domains.*;
public class Xosync_img_src_parser {
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    byte[] wiki_abrv_commons;

	private Xoh_hdoc_ctx hctx;
	private byte path_tid;
	private byte[] img_src_bgn_local, img_src_bgn_remote;
	private byte[] page_url, repo_local;
	private byte[] raw;

	public boolean				Repo_is_commons()	{return repo_is_commons;}	private boolean repo_is_commons;
	public byte[]			File_ttl_bry()		{return file_ttl_bry;}		private byte[] file_ttl_bry;
	public boolean				File_is_orig()		{return file_is_orig;}		private boolean file_is_orig;
	public Xof_ext			File_ext()			{return file_ext;}			private Xof_ext file_ext;
	public int				File_w()			{return file_w;}			private int file_w;
	public double			File_time()			{return file_time;}			private double file_time;
	public int				File_page()			{return file_page;}			private int file_page;
	public String			Err_msg()			{return err_msg;}			private String err_msg;

	public Xosync_img_src_parser() {
		rdr.Err_wkr().Fail_throws_err_(false);
		img_src_bgn_remote = tmp_bfr.Add(Bry__xowa_file).Add(Xow_domain_itm_.Bry__commons).Add_byte_slash().To_bry_and_clear();
		wiki_abrv_commons = Xow_abrv_xo_.To_bry(Xow_domain_itm_.Bry__commons);
	}
	public void Init_by_page(Xoh_hdoc_ctx hctx) {
		this.hctx = hctx;
		this.page_url = hctx.Page__url();
		this.path_tid = Path__unknown;
		this.repo_local = To_wmf_repo_or_null(tmp_bfr, hctx.Wiki__domain_itm());
		if (repo_local == null) Gfo_usr_dlg_.Instance.Warn_many("", "", "unsupported wmf repo; domain=~{0}", hctx.Wiki__domain_itm().Domain_bry());
		img_src_bgn_local = tmp_bfr.Add(Bry__xowa_file).Add(hctx.Wiki__domain_bry()).Add_byte_slash().To_bry_and_clear();	// EX: "xowa:/file/en.wikipedia.org/"
	}
	public boolean Parse(byte[] raw) {
		// init
		this.Clear();

		// set raw, raw_len; exit if empty
		this.raw = raw;
		int raw_len = raw.length;
		if (raw_len == 0) return Fail("empty img_src");
		rdr.Init_by_src(raw);

		// check "//upload.wikimedia.org/" at bgn
		this.path_tid = rdr.Chk_or(path_trie, Path__unknown);
		switch (path_tid) {
			case Path__file:	return Parse_file(raw_len);
			case Path__math:	return Parse_math(raw_len);
			default:			return Fail("img src does not start with known sequence");
		}
	}
	private boolean Parse_file(int raw_len) {
		// get repo: either "wikipedia/commons/" or "wiki_type/wiki_lang/"; EX:"wiktionary/fr"
		if (rdr.Is(Bry__repo_remote))
			this.repo_is_commons = true;
		else {
			if (!rdr.Is(repo_local)) return Fail("unknown repo");
		}

		// get file_is_orig; note omitting "else" b/c default is file_is_orig == false
		if (!rdr.Is(Bry__thumb)) file_is_orig = true; // no "/thumb";

		// check md5
		if (!Check_md5()) return Fail("invalid md5");
		
		// get file_ttl
		int file_ttl_bgn = rdr.Pos();
		int file_ttl_end = rdr.Find_fwd_lr_or(Byte_ascii.Slash, raw_len);
		file_ttl_bry = Bry_.Mid(raw, file_ttl_bgn, file_ttl_end);
		file_ttl_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(file_ttl_bry);	// NOTE: @src is always url-encoded; file_ttl_bry is un-encoded (for MD5, database lookups, etc.)
		this.file_ext = Xof_ext_.new_by_ttl_(file_ttl_bry); 
		if (file_ext.Id_is_ogg()) file_ext = Xof_ext_.new_by_id_(Xof_ext_.Id_ogv);


		// if thumb, get file_w, file_time, file_page
		if (!file_is_orig) {
			// if "page", then file_page exists; EX: // "page1-320px"
			if (rdr.Is(Bry__page)) {					
				int file_page_bgn = rdr.Pos();
				int file_page_end = rdr.Find_fwd_lr(Byte_ascii.Dash);
				file_page = Bry_.To_int_or_fail(raw, file_page_bgn, file_page_end);
			}

			// get file_w; EX: "320px-"
			int file_w_bgn = rdr.Pos();
			int file_w_end = rdr.Find_fwd_lr(Bry__px);
			if (file_w_end == -1) return Fail("missing px");
			file_w = Bry_.To_int_or(raw, file_w_bgn, file_w_end, -1);
			if (file_w == -1) return Fail("invalid file_w");

			// get time via "-seek%3D"; EX: "320px-seek%3D67-"
			int seek_end = rdr.Find_fwd_rr(Bry__seek);
			if (seek_end != Bry_find_.Not_found) {
				int file_time_bgn = rdr.Pos();
				int file_time_end = rdr.Find_fwd_lr(Byte_ascii.Dash);
				file_time = Bry_.To_double(raw, file_time_bgn, file_time_end);
			}
		}

		// make image
		Add_img(hctx.Wiki__domain_itm().Abrv_xo());
		return true;
	}
	private boolean Parse_math(int raw_len) {
		// set file_ttl_bry to rest of src + ".svg"; EX: "https://wikimedia.org/api/rest_v1/media/math/render/svg/596f8baf206a81478afd4194b44138715dc1a05c" -> "596f8baf206a81478afd4194b44138715dc1a05c.svg"
		this.file_ttl_bry = Bry_.Add(Bry_.Mid(raw, rdr.Pos(), raw_len), Byte_ascii.Dot_bry, Xof_ext_.Bry_svg);
		this.repo_is_commons = true;
		this.file_is_orig = true;
		this.file_ext = Xof_ext_.new_by_id_(Xof_ext_.Id_svg);

		Add_img(wiki_abrv_commons);
		return true;
	}
	private void Add_img(byte[] wiki_abrv) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		hctx.Page().Hdump_mgr().Imgs().Add(itm);
		itm.Init_by_wm_parse(wiki_abrv, repo_is_commons, file_is_orig, file_ttl_bry, file_ext, file_w, file_time, file_page);
	}
	public byte[] To_bry() {
		switch (path_tid) {
			case Path__file: To_bfr_file(tmp_bfr); break;
			case Path__math: To_bfr_math(tmp_bfr); break;
		}
		return tmp_bfr.To_bry_and_clear();
	}
	private void To_bfr_file(Bry_bfr bfr) {	// EX:'xowa:/file/commons.wikimedia.org/thumb/7/0/1/c/A.png/220px.png'
		// init repo; either "xowa:/file/commons.wikimedia.org" or "xowa:/file/en.wikipedia.org"
		byte repo_tid = repo_is_commons ? Xof_repo_tid_.Tid__remote : Xof_repo_tid_.Tid__local;
		byte[] fsys_root = repo_is_commons ? img_src_bgn_remote : img_src_bgn_local;			
		url_bldr.Init_by_repo(repo_tid, fsys_root, Bool_.N, Byte_ascii.Slash, Bool_.N, Bool_.N, 4);

		// set other props and generate url;
		url_bldr.Init_by_itm(file_is_orig ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb, gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Encode(file_ttl_bry), Xof_file_wkr_.Md5(file_ttl_bry), Xof_ext_.new_by_ttl_(file_ttl_bry), file_w, file_time, file_page);
		bfr.Add(url_bldr.Xto_bry());
	}
	private void To_bfr_math(Bry_bfr bfr) {	// EX:'xowa:/math/596f8baf206a81478afd4194b44138715dc1a05c
		bfr.Add(Bry__xowa_math).Add(file_ttl_bry);
	}
	private void Clear() {
		this.file_ttl_bry = null;
		this.repo_is_commons = false;
		this.file_is_orig = false;
		this.file_w = -1;
		this.file_time = -1;
		this.file_page = -1;
		this.err_msg = null;
		this.raw = null;
	}
	private boolean Fail(String fmt) {
		this.err_msg = "wm.parse:" + fmt;
		String msg = String_.Format("", err_msg + "; page={0} raw={1}", page_url, raw);
		Gfo_usr_dlg_.Instance.Warn_many("", "", msg);
		return false;
	}
	private boolean Check_md5() {	// check if md5; also, skip past md5; EX: "a/a0/"
		int pos = rdr.Pos();
		if (!Byte_.Eq_many(Byte_ascii.Slash, raw[pos + 1], raw[pos + 4])) return false;		// check slashes
		byte b_0 = raw[pos + 0], b_2 = raw[pos + 2];
		if (b_0 != b_2) return false;														// WM repeats 1st MD5 char; EX: "a" in "a/a0"
		if (!gplx.core.encoders.Hex_utl_.Is_hex_many(b_0, b_2, raw[pos + 3])) return false;	// check rest is hex
		rdr.Move_to(pos + 5);
		return true;
	}

	private static final    byte[] 
	  Bry__repo_remote = Bry_.new_a7("wikipedia/commons/")
	, Bry__thumb = Bry_.new_a7("thumb/")
	, Bry__px = Bry_.new_a7("px")
	, Bry__seek = Bry_.new_a7("-seek%3D")
	, Bry__page = Bry_.new_a7("page")
	;
	public static final byte Path__unknown = 0, Path__file = 1, Path__math = 2;
	private final    Btrie_slim_mgr path_trie = Btrie_slim_mgr.cs()
	.Add_str_byte("//upload.wikimedia.org/", Path__file)
	.Add_str_byte("https://wikimedia.org/api/rest_v1/media/math/render/svg/", Path__math)
	;

	public static final    byte[] Bry__xowa_file = Bry_.new_a7("xowa:/file/"), Bry__xowa_math = Bry_.new_a7("xowa:/math/");
	public static Btrie_slim_mgr Src_xo_trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Bry__xowa_file, Path__file)
	.Add_bry_byte(Bry__xowa_math, Path__math)
	;

	private static byte[] To_wmf_repo_or_null(Bry_bfr bfr, Xow_domain_itm domain_itm) {
		// add type; EX: "fr.wiktionary.org" -> "wiktionary/"
		switch (domain_itm.Domain_type_id()) {
			case Xow_domain_tid_.Tid__wikipedia:
			case Xow_domain_tid_.Tid__wiktionary:
			case Xow_domain_tid_.Tid__wikisource:
			case Xow_domain_tid_.Tid__wikivoyage:
			case Xow_domain_tid_.Tid__wikiquote:
			case Xow_domain_tid_.Tid__wikibooks:
			case Xow_domain_tid_.Tid__wikiversity:
			case Xow_domain_tid_.Tid__wikinews:
				bfr.Add(domain_itm.Domain_type().Key_bry()).Add_byte_slash();
				break;
			default:
				return null;
		}

		// add lang; EX: "fr.wiktionary.org" -> "fr/"
		bfr.Add(domain_itm.Lang_orig_key()).Add_byte_slash();
		return bfr.To_bry_and_clear();
	}
	public static Xof_ext Ext_by_ttl(byte[] file_ttl_bry, byte repo_tid) {
		Xof_ext rv = Xof_ext_.new_by_ttl_(file_ttl_bry); 
		if (rv.Id_is_ogg()) rv = Xof_ext_.new_by_id_(Xof_ext_.Id_ogv);
		if (repo_tid == Xof_repo_tid_.Tid__math) rv = Xof_ext_.new_by_id_(Xof_ext_.Id_svg);
		return rv;
	}
}
