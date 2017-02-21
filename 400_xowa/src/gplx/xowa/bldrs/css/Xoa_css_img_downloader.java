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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.files.downloads.*; import gplx.core.envs.*;
public class Xoa_css_img_downloader {
	private byte[] wiki_domain;
	public Xoa_css_img_downloader Ctor(Gfo_usr_dlg usr_dlg, Xof_download_wkr download_wkr, byte[] stylesheet_prefix) {
		this.usr_dlg = usr_dlg; this.download_wkr = download_wkr; this.stylesheet_prefix = stylesheet_prefix;
		return this;
	}	private Gfo_usr_dlg usr_dlg; private Xof_download_wkr download_wkr;
	public Xoa_css_img_downloader Stylesheet_prefix_(byte[] v) {stylesheet_prefix = v; return this;} private byte[] stylesheet_prefix;	// TEST: setter exposed b/c tests can handle "mem/" but not "//mem"
	public void Chk(byte[] wiki_domain, Io_url css_fil) {
		this.wiki_domain = wiki_domain;
		List_adp img_list = List_adp_.New();
		byte[] old_bry = Io_mgr.Instance.LoadFilBry(css_fil);
		byte[] rel_url_prefix = Bry_.Add(Bry_fwd_slashes, wiki_domain);
		byte[] new_bry = Convert_to_local_urls(rel_url_prefix, old_bry, img_list);
		Io_url img_dir = css_fil.OwnerDir();
		Download_fils(img_dir, img_list.To_str_ary());
		Io_mgr.Instance.SaveFilBry(css_fil, new_bry);
	}
	public byte[] Convert_to_local_urls(byte[] rel_url_prefix, byte[] src, List_adp list) {
		try {
			int src_len = src.length;
			int prv_pos = 0;
			Bry_bfr bfr = Bry_bfr_.New_w_size(src_len);
			Hash_adp img_hash = Hash_adp_bry.cs();
			while (true) {
				int url_pos = Bry_find_.Find_fwd(src, Bry_url, prv_pos);
				if (url_pos == Bry_find_.Not_found) {bfr.Add_mid(src, prv_pos, src_len); break;}	// no more "url("; exit;
				int bgn_pos = url_pos + Bry_url_len;	// set bgn_pos after "url("
				byte bgn_byte = src[bgn_pos];
				byte end_byte = Byte_ascii.Null;
				boolean quoted = true;
				switch (bgn_byte) {									// find end_byte
					case Byte_ascii.Quote: case Byte_ascii.Apos:	// quoted; end_byte is ' or "
						end_byte = bgn_byte;
						++bgn_pos;
						break;
					default:										// not quoted; end byte is ")"
						end_byte = Byte_ascii.Paren_end;
						quoted = false;
						break;
				}
				int end_pos = Bry_find_.Find_fwd(src, end_byte, bgn_pos, src_len);
				if (end_pos == Bry_find_.Not_found) {	// unclosed "url("; exit since nothing else will be found
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.end_missing", "could not find end_sequence for 'url(': bgn='~{0}' end='~{1}'", prv_pos, String_.new_u8__by_len(src, prv_pos, prv_pos + 25));
					bfr.Add_mid(src, prv_pos, src_len);
					break;
				}	
				if (end_pos - bgn_pos == 0) {		// empty; "url()"; ignore
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.empty", "'url(' is empty: bgn='~{0}' end='~{1}'", prv_pos, String_.new_u8__by_len(src, prv_pos, prv_pos + 25));
					bfr.Add_mid(src, prv_pos, bgn_pos);
					prv_pos = bgn_pos;
					continue;
				}
				byte[] img_raw = Bry_.Mid(src, bgn_pos, end_pos); int img_raw_len = img_raw.length;
				if (Bry_.Has_at_bgn(img_raw, Bry_data_image, 0, img_raw_len)) {	// base64
					bfr.Add_mid(src, prv_pos, end_pos);							// nothing to download; just add entire String
					prv_pos = end_pos;
					continue;
				}
				int import_url_end = Import_url_chk(rel_url_prefix, src, src_len, prv_pos, url_pos, img_raw, bfr);	// check for embedded stylesheets via @import tag
				if (import_url_end != Bry_find_.Not_found)  {
					prv_pos = import_url_end;
					continue;
				}
				byte[] img_cleaned = Xob_url_fixer.Fix(wiki_domain, img_raw, img_raw_len);
				if (img_cleaned == null) {	// could not clean img
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.clean_failed", "could not extract valid http src: bgn='~{0}' end='~{1}'", prv_pos, String_.new_u8(img_raw));
					bfr.Add_mid(src, prv_pos, bgn_pos); prv_pos = bgn_pos; continue;
				}
				if (!img_hash.Has(img_cleaned)) {// only add unique items for download;
					img_hash.Add_as_key_and_val(img_cleaned);
					list.Add(String_.new_u8(img_cleaned));
				}
				img_cleaned = Replace_invalid_chars(Bry_.Copy(img_cleaned));	// NOTE: must call ByteAry.Copy else img_cleaned will change *inside* hash
				bfr.Add_mid(src, prv_pos, bgn_pos);
				if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
				bfr.Add(img_cleaned);
				if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
				prv_pos = end_pos;
			}
			return bfr.To_bry_and_clear();
		}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to convert local_urls: ~{0} ~{1}", String_.new_u8(rel_url_prefix), Err_.Message_gplx_full(e));
			return src;
		}
	}
	public static byte[] Import_url_build(byte[] stylesheet_prefix, byte[] rel_url_prefix, byte[] css_url) {
		return Bry_.Has_at_bgn(css_url, Bry_http_protocol)			// css_url already starts with "http"; return self; PAGE:tr.n:Main_Page; DATE:2014-06-04
			? css_url
			: Bry_.Add(stylesheet_prefix, css_url)
			;
	}
	private int Import_url_chk(byte[] rel_url_prefix, byte[] src, int src_len, int old_pos, int find_bgn, byte[] url_raw, Bry_bfr bfr) {
		if (find_bgn < Bry_import_len) return Bry_find_.Not_found;
		if (!Bry_.Match(src, find_bgn - Bry_import_len, find_bgn, Bry_import)) return Bry_find_.Not_found;
		byte[] css_url = url_raw; int css_url_len = css_url.length;
		if (css_url_len > 0 && css_url[0] == Byte_ascii.Slash) {		// css_url starts with "/"; EX: "/page" or "//site/page" DATE:2014-02-03
			if (css_url_len > 1 && css_url[1] != Byte_ascii.Slash)		// skip if css_url starts with "//"; EX: "//site/page"
				css_url = Bry_.Add(rel_url_prefix, css_url);			// "/w/a.css" -> "//en.wikipedia.org/w/a.css"
		}
		css_url = Bry_.Replace(css_url, Byte_ascii.Space, Byte_ascii.Underline);	// NOTE: must replace spaces with underlines else download will fail; EX:https://it.wikivoyage.org/w/index.php?title=MediaWiki:Container e Infobox.css&action=raw&ctype=text/css; DATE:2015-03-08
		byte[] css_src_bry = Import_url_build(stylesheet_prefix, rel_url_prefix, css_url);
		String css_src_str = String_.new_u8(css_src_bry);
		download_wkr.Download_xrg().Prog_fmt_hdr_(usr_dlg.Log_many(GRP_KEY, "logo.download", "downloading import for '~{0}'", css_src_str));
		byte[] css_trg_bry = download_wkr.Download_xrg().Exec_as_bry(css_src_str);
		if (css_trg_bry == null) {
			usr_dlg.Warn_many("", "", "could not import css: url=~{0}", css_src_str);
			return Bry_find_.Not_found;	// css not found
		}
		bfr.Add_mid(src, old_pos, find_bgn - Bry_import_len).Add_byte_nl();
		bfr.Add(Bry_comment_bgn).Add(css_url).Add(Bry_comment_end).Add_byte_nl();			
		if (Bry_find_.Find_fwd(css_url, Wikisource_dynimg_ttl) != -1) css_trg_bry = Bry_.Replace(css_trg_bry, Wikisource_dynimg_find, Wikisource_dynimg_repl);	// FreedImg hack; PAGE:en.s:Page:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species.pdf/3 DATE:2014-09-06
		bfr.Add(css_trg_bry).Add_byte_nl();
		bfr.Add_byte_nl();
		int semic_pos = Bry_find_.Find_fwd(src, Byte_ascii.Semic, find_bgn + url_raw.length, src_len);
		return semic_pos + Int_.Const_dlm_len;
	}
	private static final    byte[]
	  Wikisource_dynimg_ttl		= Bry_.new_a7("en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css")
	, Wikisource_dynimg_find	= Bry_.new_a7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	, Wikisource_dynimg_repl	= Bry_.new_a7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], /*XOWA:handle file:// paths which will have /commons.wikimedia.org/ but not /wikipedia/ */ .freedImg img[src*=\"wikimedia\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	;
	public byte[] Clean_img_url(byte[] raw, int raw_len) {
		int pos_bgn = 0;
		if (Bry_.Has_at_bgn(raw, Bry_fwd_slashes, 0, raw_len)) pos_bgn = Bry_fwd_slashes.length;
		if (Bry_.Has_at_bgn(raw, Bry_http, 0, raw_len)) pos_bgn = Bry_http.length;
		int pos_slash = Bry_find_.Find_fwd(raw, Byte_ascii.Slash, pos_bgn, raw_len);
		if (pos_slash == Bry_find_.Not_found) return null; // first segment is site_name; at least one slash must be present for image name; EX: site.org/img_name.jpg
		if (pos_slash == raw_len - 1) return null; // "site.org/" is invalid
		int pos_end = raw_len;
		int pos_question = Bry_find_.Find_bwd(raw, Byte_ascii.Question);
		if (pos_question != Bry_find_.Not_found)
			pos_end = pos_question;	// remove query params; EX: img_name?key=val 
		return Bry_.Mid(raw, pos_bgn, pos_end);
	}
	private void Download_fils(Io_url css_dir, String[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String src = ary[i];
			Io_url trg = css_dir.GenSubFil_nest(Op_sys.Cur().Fsys_http_frag_to_url_str(Replace_invalid_chars_str(src)));
			if (Io_mgr.Instance.ExistsFil(trg)) continue;
			download_wkr.Download(true, "https://" + src, trg, "download: " + src); // ILN
			if (Io_mgr.Instance.QueryFil(trg).Size() == 0) {	// warn if 0 byte files downloaded; DATE:2015-07-06
				Xoa_app_.Usr_dlg().Warn_many("", "", "css.download; 0 byte file downloaded; file=~{0}", trg.Raw());
			}
		}
	}
	String Replace_invalid_chars_str(String raw_str) {return String_.new_u8(Replace_invalid_chars(Bry_.new_u8(raw_str)));}
	byte[] Replace_invalid_chars(byte[] raw_bry) {
		int raw_len = raw_bry.length;
		for (int i = 0; i < raw_len; i++) {	// convert invalid wnt chars to underscores
			byte b = raw_bry[i];
			switch (b) {
				//case Byte_ascii.Slash:
				case Byte_ascii.Backslash: case Byte_ascii.Colon: case Byte_ascii.Star: case Byte_ascii.Question:
				case Byte_ascii.Quote: case Byte_ascii.Lt: case Byte_ascii.Gt: case Byte_ascii.Pipe:
					raw_bry[i] = Byte_ascii.Underline;
					break;
			}
		}
		return raw_bry;
	}
	private static final    byte[] 
	  Bry_url = Bry_.new_a7("url("), Bry_data_image = Bry_.new_a7("data:image/")
	, Bry_http = Bry_.new_a7("http://"), Bry_fwd_slashes = Bry_.new_a7("//"), Bry_import = Bry_.new_a7("@import ")
	, Bry_http_protocol = Bry_.new_a7("http")
	;
	public static final    byte[] 
		  Bry_comment_bgn = Bry_.new_a7("/*XOWA:"), Bry_comment_end = Bry_.new_a7("*/");
	private static final    int Bry_url_len = Bry_url.length, Bry_import_len = Bry_import.length;
	static final String GRP_KEY = "xowa.wikis.init.css";
}
