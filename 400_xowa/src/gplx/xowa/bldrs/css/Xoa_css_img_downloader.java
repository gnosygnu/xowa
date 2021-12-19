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
package gplx.xowa.bldrs.css;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.wrappers.ByteVal;
import gplx.xowa.*;
import gplx.core.envs.*; import gplx.core.btries.*;
import gplx.xowa.files.downloads.*;
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
		byte[] rel_url_prefix = BryUtl.Add(Bry_fwd_slashes, wiki_domain);
		byte[] new_bry = Convert_to_local_urls(rel_url_prefix, old_bry, img_list);
		Io_url img_dir = css_fil.OwnerDir();
		Download_fils(img_dir, img_list.ToStrAry());
		Io_mgr.Instance.SaveFilBry(css_fil, new_bry);
	}
	public byte[] Convert_to_local_urls(byte[] rel_url_prefix, byte[] src, List_adp list) {
		try {
			// init vars
			int src_len = src.length;
			BryWtr bfr = BryWtr.NewWithSize(src_len);
			Hash_adp img_hash = Hash_adp_bry.cs();
			Btrie_rv trv = new Btrie_rv();

			int prv_pos = 0;

			// loop over src and convert each "url(...)"
			boolean loop = true;
			while (loop) {
				int url_pos = -1;

				// search forward for "url(" while skipping comments
				int cur_pos = prv_pos;
				while (loop && url_pos == -1) { // NOTE: that loop = false when EOS
					// EOS; exit
					if (cur_pos == src_len) {
						loop = false;
						break;
					}

					// match against trie
					Object match_obj = convert_trie.Match_at_w_b0(trv, src[cur_pos], src, cur_pos, src_len);
					if (match_obj == null) { // no match; increment by one and continue
						cur_pos++;
						continue;
					}

					// match found
					ByteVal match = (ByteVal)match_obj;
					switch (match.Val()) {
						case CONVERT_TID__COMMENT: // handle comment
							// search for "*/"
							int comment_end = BryFind.FindFwd(src, Bry_comment_end, cur_pos);

							// "*/" not found; exit;
							if (comment_end == BryFind.NotFound) {
								loop = false;
								break;
							}

							// otherwise, set cur_pos after comment_end and keep searching
							cur_pos = comment_end + Bry_comment_end.length;
							break;
						case CONVERT_TID__URL:
							// set url_pos which will break loop
							url_pos = cur_pos;
							break;
					}
				}

				// EOS reached; stop
				if (!loop) {
					bfr.AddMid(src, prv_pos, src_len);
					break;
				}

				// "url(" found
				// identify end byte: ' " )
				int bgn_pos = url_pos + Bry_url_len; // set bgn_pos after "url("
				byte bgn_byte = src[bgn_pos];
				byte end_byte = AsciiByte.Null;
				boolean quoted = true;
				switch (bgn_byte) {									// find end_byte
					case AsciiByte.Quote: case AsciiByte.Apos:	// quoted; end_byte is ' or "
						end_byte = bgn_byte;
						++bgn_pos;
						break;
					default:										// not quoted; end byte is ")"
						end_byte = AsciiByte.ParenEnd;
						quoted = false;
						break;
				}

				// find end_pos
				int end_pos = BryFind.FindFwd(src, end_byte, bgn_pos, src_len);
				if (end_pos == BryFind.NotFound) {	// unclosed "url("; exit since nothing else will be found
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.end_missing", "could not find end_sequence for 'url(': bgn='~{0}' end='~{1}'", prv_pos, StringUtl.NewU8ByLen(src, prv_pos, prv_pos + 25));
					bfr.AddMid(src, prv_pos, src_len);
					break;
				}

				// trim whitespace; EX: "background: url( //upload.wikimedia.org/wikipedia/commons/thumb/2/24/Gtk-media-forward-ltr.svg/24px-Gtk-media-forward-ltr.svg.png )" ISSUE#:307 PAGE:en.v:MediaWiki:Common.css/Slideshows.css DATE:2018-12-23
				bgn_pos = BryFind.FindFwdWhileSpaceOrTab(src, bgn_pos, end_pos);
				end_pos = BryFind.FindBwdSkipWs(src, end_pos, bgn_pos);

				// empty; "url()"; ignore
				if (end_pos - bgn_pos == 0) {
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.empty", "'url(' is empty: bgn='~{0}' end='~{1}'", prv_pos, StringUtl.NewU8ByLen(src, prv_pos, prv_pos + 25));
					bfr.AddMid(src, prv_pos, bgn_pos);
					prv_pos = bgn_pos;
					continue;
				}

				// parse img
				byte[] img_raw = BryLni.Mid(src, bgn_pos, end_pos); int img_raw_len = img_raw.length;
				if (BryUtl.HasAtBgn(img_raw, Bry_data_image, 0, img_raw_len)) {	// base64
					bfr.AddMid(src, prv_pos, end_pos);							// nothing to download; just add entire String
					prv_pos = end_pos;
					continue;
				}
				int import_url_end = Import_url_chk(rel_url_prefix, src, src_len, prv_pos, url_pos, img_raw, bfr);	// check for embedded stylesheets via @import tag
				if (import_url_end != BryFind.NotFound)  {
					prv_pos = import_url_end;
					continue;
				}
				byte[] img_cleaned = Xob_url_fixer.Fix(wiki_domain, img_raw, img_raw_len);
				if (img_cleaned == null) {	// could not clean img
					usr_dlg.Warn_many(GRP_KEY, "parse.invalid_url.clean_failed", "could not extract valid http src: bgn='~{0}' end='~{1}'", prv_pos, StringUtl.NewU8(img_raw));
					bfr.AddMid(src, prv_pos, bgn_pos); prv_pos = bgn_pos; continue;
				}
				if (!img_hash.Has(img_cleaned)) {// only add unique items for download;
					img_hash.AddAsKeyAndVal(img_cleaned);
					list.Add(StringUtl.NewU8(img_cleaned));
				}
				img_cleaned = Replace_invalid_chars(BryUtl.Copy(img_cleaned));	// NOTE: must call ByteAry.Copy else img_cleaned will change *inside* hash

				// add to bfr
				bfr.AddMid(src, prv_pos, bgn_pos);
				if (!quoted) bfr.AddByte(AsciiByte.Quote);
				bfr.Add(img_cleaned);
				if (!quoted) bfr.AddByte(AsciiByte.Quote);
				prv_pos = end_pos;
			}
			return bfr.ToBryAndClear();
		}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to convert local_urls: ~{0} ~{1}", StringUtl.NewU8(rel_url_prefix), ErrUtl.ToStrFull(e));
			return src;
		}
	}
	public static byte[] Import_url_build(byte[] stylesheet_prefix, byte[] rel_url_prefix, byte[] css_url) {
		return BryUtl.HasAtBgn(css_url, Bry_http_protocol)			// css_url already starts with "http"; return self; PAGE:tr.n:Main_Page; DATE:2014-06-04
			? css_url
			: BryUtl.Add(stylesheet_prefix, css_url)
			;
	}
	private int Import_url_chk(byte[] rel_url_prefix, byte[] src, int src_len, int old_pos, int find_bgn, byte[] url_raw, BryWtr bfr) {
		if (find_bgn < Bry_import_len) return BryFind.NotFound;
		if (!BryLni.Eq(src, find_bgn - Bry_import_len, find_bgn, Bry_import)) return BryFind.NotFound;
		byte[] css_url = url_raw; int css_url_len = css_url.length;
		if (css_url_len > 0 && css_url[0] == AsciiByte.Slash) {		// css_url starts with "/"; EX: "/page" or "//site/page" DATE:2014-02-03
			if (css_url_len > 1 && css_url[1] != AsciiByte.Slash)		// skip if css_url starts with "//"; EX: "//site/page"
				css_url = BryUtl.Add(rel_url_prefix, css_url);			// "/w/a.css" -> "//en.wikipedia.org/w/a.css"
		}
		css_url = BryUtl.Replace(css_url, AsciiByte.Space, AsciiByte.Underline);	// NOTE: must replace spaces with underlines else download will fail; EX:https://it.wikivoyage.org/w/index.php?title=MediaWiki:Container e Infobox.css&action=raw&ctype=text/css; DATE:2015-03-08
		byte[] css_src_bry = Import_url_build(stylesheet_prefix, rel_url_prefix, css_url);
		String css_src_str = StringUtl.NewU8(css_src_bry);
		download_wkr.Download_xrg().Prog_fmt_hdr_(usr_dlg.Log_many(GRP_KEY, "logo.download", "downloading import for '~{0}'", css_src_str));
		byte[] css_trg_bry = download_wkr.Download_xrg().Exec_as_bry(css_src_str);
		if (css_trg_bry == null) {
			usr_dlg.Warn_many("", "", "could not import css: url=~{0}", css_src_str);
			return BryFind.NotFound;	// css not found
		}
		bfr.AddMid(src, old_pos, find_bgn - Bry_import_len).AddByteNl();
		bfr.Add(Bry_comment_bgn).Add(css_url).Add(Bry_comment_end).AddByteNl();
		if (BryFind.FindFwd(css_url, Wikisource_dynimg_ttl) != -1) css_trg_bry = BryUtlByWtr.Replace(css_trg_bry, Wikisource_dynimg_find, Wikisource_dynimg_repl);	// FreedImg hack; PAGE:en.s:Page:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species.pdf/3 DATE:2014-09-06
		bfr.Add(css_trg_bry).AddByteNl();
		bfr.AddByteNl();
		int semic_pos = BryFind.FindFwd(src, AsciiByte.Semic, find_bgn + url_raw.length, src_len);
		return semic_pos + AsciiByte.Len1;
	}
	private static final byte[]
	  Wikisource_dynimg_ttl		= BryUtl.NewA7("en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css")
	, Wikisource_dynimg_find	= BryUtl.NewA7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	, Wikisource_dynimg_repl	= BryUtl.NewA7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], /*XOWA:handle file:// paths which will have /commons.wikimedia.org/ but not /wikipedia/ */ .freedImg img[src*=\"wikimedia\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	;
	public byte[] Clean_img_url(byte[] raw, int raw_len) {
		int pos_bgn = 0;
		if (BryUtl.HasAtBgn(raw, Bry_fwd_slashes, 0, raw_len)) pos_bgn = Bry_fwd_slashes.length;
		if (BryUtl.HasAtBgn(raw, Bry_http, 0, raw_len)) pos_bgn = Bry_http.length;
		int pos_slash = BryFind.FindFwd(raw, AsciiByte.Slash, pos_bgn, raw_len);
		if (pos_slash == BryFind.NotFound) return null; // first segment is site_name; at least one slash must be present for image name; EX: site.org/img_name.jpg
		if (pos_slash == raw_len - 1) return null; // "site.org/" is invalid
		int pos_end = raw_len;
		int pos_question = BryFind.FindBwd(raw, AsciiByte.Question);
		if (pos_question != BryFind.NotFound)
			pos_end = pos_question;	// remove query params; EX: img_name?key=val 
		return BryLni.Mid(raw, pos_bgn, pos_end);
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
	String Replace_invalid_chars_str(String raw_str) {return StringUtl.NewU8(Replace_invalid_chars(BryUtl.NewU8(raw_str)));}
	byte[] Replace_invalid_chars(byte[] raw_bry) {
		int raw_len = raw_bry.length;
		for (int i = 0; i < raw_len; i++) {	// convert invalid wnt chars to underscores
			byte b = raw_bry[i];
			switch (b) {
				//case Byte_ascii.Slash:
				case AsciiByte.Backslash: case AsciiByte.Colon: case AsciiByte.Star: case AsciiByte.Question:
				case AsciiByte.Quote: case AsciiByte.Lt: case AsciiByte.Gt: case AsciiByte.Pipe:
					raw_bry[i] = AsciiByte.Underline;
					break;
			}
		}
		return raw_bry;
	}
	private static final byte[]
	  Bry_url = BryUtl.NewA7("url("), Bry_data_image = BryUtl.NewA7("data:image/")
	, Bry_http = BryUtl.NewA7("http://"), Bry_fwd_slashes = BryUtl.NewA7("//"), Bry_import = BryUtl.NewA7("@import ")
	, Bry_http_protocol = BryUtl.NewA7("http")
	;
	public static final byte[] Bry_comment_bgn = BryUtl.NewA7("/*XOWA:"), Bry_comment_end = BryUtl.NewA7("*/");
	private static final int Bry_url_len = Bry_url.length, Bry_import_len = Bry_import.length;
	private static final byte CONVERT_TID__URL = 1, CONVERT_TID__COMMENT = 2;
	private static final Btrie_slim_mgr convert_trie = Btrie_slim_mgr.cs().Add_str_byte("url(", CONVERT_TID__URL).Add_str_byte("/*", CONVERT_TID__COMMENT);
	private static final String GRP_KEY = "xowa.wikis.init.css";
}
