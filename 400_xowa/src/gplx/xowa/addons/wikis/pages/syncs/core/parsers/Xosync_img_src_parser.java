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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.core.brys.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.langs.htmls.*;	 import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.domains.*;
public class Xosync_img_src_parser {
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();

	private Xoh_hdoc_ctx hctx;
	private byte[] img_src_bgn_local, img_src_bgn_remote;
	private byte[] page_url, repo_local;
	private byte[] raw;

	public boolean				Repo_is_commons()	{return repo_is_commons;}	private boolean repo_is_commons;
	public byte[]			File_ttl_bry()		{return file_ttl_bry;}		private byte[] file_ttl_bry;
	public boolean				File_is_orig()		{return file_is_orig;}		private boolean file_is_orig;
	public int				File_w()			{return file_w;}			private int file_w;
	public double			File_time()			{return file_time;}			private double file_time;
	public int				File_page()			{return file_page;}			private int file_page;
	public String			Err_msg()			{return err_msg;}			private String err_msg;

	public Xosync_img_src_parser() {
		rdr.Err_wkr().Fail_throws_err_(false);
		img_src_bgn_remote = tmp_bfr.Add(Bry__xowa_src_bgn).Add(Xow_domain_itm_.Bry__commons).Add_byte_slash().To_bry_and_clear();
	}
	public void Init_by_page(Xoh_hdoc_ctx hctx) {
		this.hctx = hctx;
		this.page_url = hctx.Page__url();
		this.repo_local = To_wmf_repo_or_null(tmp_bfr, hctx.Wiki__domain_itm());
		if (repo_local == null) Gfo_usr_dlg_.Instance.Warn_many("", "", "unsupported wmf repo; domain=~{0}", hctx.Wiki__domain_itm().Domain_bry());
		img_src_bgn_local = tmp_bfr.Add(Bry__xowa_src_bgn).Add(hctx.Wiki__domain_bry()).Add_byte_slash().To_bry_and_clear();	// EX: "xowa:/file/en.wikipedia.org/"
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
		if (!rdr.Is(Bry__upload_wikimedia_org)) return Fail("url does not start with //upload.wikimedia.org");

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

		// if thumb, get width, time, page
		if (!file_is_orig) {
			int file_w_bgn = rdr.Pos();
			int file_w_end = rdr.Find_fwd_lr(Bry__px);
			if (file_w_end == -1) return Fail("missing px");
			file_w = Bry_.To_int_or(raw, file_w_bgn, file_w_end, -1);
			if (file_w == -1) return Fail("invalid file_w");
		}

		// make image
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_by_wm_parse(hctx.Wiki__domain_itm().Abrv_xo(), repo_is_commons, file_is_orig, file_ttl_bry, file_w, file_time, file_page);
		hctx.Page().Hdump_mgr().Imgs().Add(itm);

		return true;
	}
	public byte[] To_bry() {
		To_bfr(tmp_bfr);
		return tmp_bfr.To_bry_and_clear();
	}
	public void To_bfr(Bry_bfr bfr) {	// EX:'xowa:file/commons.wikimedia.org/thumb/7/0/1/c/A.png/220px.png'
		// init repo; either "xowa:file/commons.wikimedia.org" or "xowa:file/en.wikipedia.org"
		url_bldr.Init_by_root(repo_is_commons ? img_src_bgn_remote : img_src_bgn_local, Bool_.N, Byte_ascii.Slash, Bool_.N, Bool_.N, 4);

		// set other props and generate url;
		url_bldr.Init_by_itm(file_is_orig ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Encode(file_ttl_bry), Xof_file_wkr_.Md5(file_ttl_bry), Xof_ext_.new_by_ttl_(file_ttl_bry), file_w, file_time, file_page);
		bfr.Add(url_bldr.Xto_bry());
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
	  Bry__upload_wikimedia_org = Bry_.new_a7("//upload.wikimedia.org/")
	, Bry__repo_remote = Bry_.new_a7("wikipedia/commons/")
	, Bry__thumb = Bry_.new_a7("thumb/")
	, Bry__px = Bry_.new_a7("px")
	;
	public static final    byte[] Bry__xowa_src_bgn = Bry_.new_a7("xowa:/file/");

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
}
