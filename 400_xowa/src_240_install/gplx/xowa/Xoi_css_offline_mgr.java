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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
class Xoi_css_offline_itm {
	public Xoi_css_offline_itm(byte[] http_url) {this.http_url = http_url;}
	public byte[] Http_url() {return http_url;} private byte[] http_url;
	public byte[] File_url() {return file_url;} public void File_url_(byte[] v) {file_url = v;} private byte[] file_url;
}
class Xoi_css_url_info {
//		private Gfo_usr_dlg usr_dlg;
	public int Bgn_pos() {return bgn_pos;} public void Bgn_pos_(int v) {bgn_pos = v;} private int bgn_pos;
	public int End_pos() {return end_pos;} public void End_pos_(int v) {end_pos = v;} private int end_pos;
	public boolean Found() {return found;} public void Found_(boolean v) {found = v;} private boolean found;
	public void Init(Gfo_usr_dlg usr_dlg) {
//			this.usr_dlg = usr_dlg;
	}
	public void Clear() {
		bgn_pos = end_pos = -1;
		found = false;
	}
	public void Quote_data(byte end_byte, boolean quoted) {
	}
	public Xoi_css_url_info Rslt_fail(int end_pos, String fmt, Object... args) {
		this.end_pos = end_pos;
//				if (bgn_pos == src_len) {usr_dlg.Warn_many("", "", "eos after 'url(': bgn=~{bgn}", tkn_bgn); return Bry_finder.Not_found;}
		return this;
	}
	public Xoi_css_url_info Rslt_pass(int end_pos) {
		this.end_pos = end_pos;
		return this;
	}
	public Xoi_css_url_info Rslt_pass(int end_pos, byte[] url_clean) {
		this.end_pos = end_pos;
		return this;
	}
}
class Xoi_css_offline_mgr {
	private Bry_bfr bfr;
	private byte[] src;
	private int src_len, pos;
	private OrderedHash download_queue;
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Null;
	public void Offline(Bry_bfr bfr, OrderedHash download_queue, Object download_wkr, byte[] src) {
		this.bfr = bfr;
		this.download_queue = download_queue;
		this.src = src; this.src_len = src.length;
		this.pos = 0;
		while (true) {
			boolean last = pos == src_len;
			byte b = last ? Byte_ascii.NewLine : src[pos];
			Object o = tkns_trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null) {
				bfr.Add_byte(b);
				++pos;
			}
			else {
				byte tkn_tid = ((Byte_obj_val)o).Val();
				int match_pos = tkns_trie.Match_pos();
				int nxt_pos = -1;
				switch (tkn_tid) {
					case Tkn_url:		nxt_pos = Process_url(pos, match_pos); break;
					case Tkn_import:	nxt_pos = Process_import(pos, match_pos); break;
				}
				pos = nxt_pos;
			}
		}

	}
	// "//id.wikibooks.org/w/index.php?title=MediaWiki:Common.css&oldid=43393&action=raw&ctype=text/css";
	private int Process_import(int tkn_bgn, int tkn_end) {	// @import
		// get url
		// if null, add to bfr and exit;
		// else download, and recursively call self
//			int bgn_pos = Bry_finder.Find_fwd(src, end_byte, bgn_pos, src_len);
//			int end_pos = Bry_finder.Find_fwd(src, end_byte, bgn_pos, src_len);
		return -1;
	}
	private int Process_url(int tkn_bgn, int tkn_end) {	// " url"
		int bgn_pos = Bry_finder.Find_fwd_while_ws(src, tkn_end, src_len);	// skip any ws after " url("
		if (bgn_pos == src_len) {usr_dlg.Warn_many("", "", "eos after 'url(': bgn=~{bgn}", tkn_bgn); return Bry_finder.Not_found;}
		byte end_byte = src[bgn_pos]; boolean quoted = true;
		switch (end_byte) {
			case Byte_ascii.Quote: case Byte_ascii.Apos:	// quoted; increment position
				++bgn_pos;
				break;
			default:										// not quoted; end byte is ")"
				end_byte = Byte_ascii.Paren_end;
				quoted = false;
				break;
		}
		int end_pos = Bry_finder.Find_fwd(src, end_byte, bgn_pos, src_len);
		if (end_pos == Bry_.NotFound) {	// unclosed "url("; exit since nothing else will be found
			usr_dlg.Warn_many("", "", "could not end_byte for 'url(': bgn='~{0}' end='~{1}'", bgn_pos, String_.new_utf8_len_safe_(src, tkn_bgn, tkn_bgn + 25));
			bfr.Add_mid(src, tkn_bgn, src_len);
			return Bry_finder.Not_found;
		}
		if (end_pos - bgn_pos == 0) {		// empty; "url()"; ignore
			usr_dlg.Warn_many("", "", "'url(' is empty: bgn='~{0}' end='~{1}'", tkn_bgn, String_.new_utf8_len_safe_(src, tkn_bgn, tkn_bgn + 25));
			return end_pos;
		}
		byte[] url_raw = Bry_.Mid(src, bgn_pos, end_pos); int url_raw_len = url_raw.length;
		if (Bry_.HasAtBgn(url_raw, Bry_data_image, 0, url_raw_len)) {	// base64
			++end_pos;	// include end_byte;
			bfr.Add_mid(src, tkn_bgn, end_pos);							// nothing to download; just add entire String
			return end_pos;
		}
		byte[] url_cleaned = Clean_url(url_raw, url_raw_len);
		if (url_cleaned == null) {	// could not clean url
			usr_dlg.Warn_many("", "", "could not extract valid url: bgn='~{0}' end='~{1}'", tkn_bgn, String_.new_utf8_(url_raw));
			bfr.Add_mid(src, tkn_bgn, bgn_pos);
			return bgn_pos;
		}
		Xoi_css_offline_itm url_itm = (Xoi_css_offline_itm)download_queue.Fetch(url_cleaned);
		if (url_itm == null) {	// only add unique items for download;
			url_itm = new Xoi_css_offline_itm(url_cleaned);
			download_queue.Add(url_cleaned, url_itm);
		}
		byte[] file_url = Replace_invalid_chars(Bry_.Copy(url_cleaned));	// NOTE: must call ByteAry.Copy else url_cleaned will change *inside* bry
		url_itm.File_url_(file_url);
		bfr.Add_mid(src, tkn_bgn, tkn_end);
		if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
		bfr.Add(file_url);
		if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
		return end_pos;
	}
	public static Xoi_css_url_info Process_url(byte[] src, int src_len, int tkn_bgn, int tkn_end, Xoi_css_url_info inf) {	// " url"
		inf.Clear();
		int bgn_pos = Bry_finder.Find_fwd_while_ws(src, tkn_end, src_len);	// skip any ws after " url("
		if (bgn_pos == src_len) return inf.Rslt_fail(src_len, "eos after 'url(': bgn=~{bgn}");
		byte end_byte = src[bgn_pos]; boolean quoted = true;
		switch (end_byte) {
			case Byte_ascii.Quote: case Byte_ascii.Apos:	// quoted; increment position
				++bgn_pos;
				break;
			default:										// not quoted; end byte is ")"
				end_byte = Byte_ascii.Paren_end;
				quoted = false;
				break;
		}
		inf.Quote_data(end_byte, quoted);
		int end_pos = Bry_finder.Find_fwd(src, end_byte, bgn_pos, src_len);
		if (end_pos == Bry_.NotFound) {	// unclosed "url("; exit since nothing else will be found
			return inf.Rslt_fail(src_len, "could not end_byte for 'url(': bgn='~{0}' end='~{1}'");
		}
		if (end_pos - bgn_pos == 0) {		// empty; "url()"; ignore
			return inf.Rslt_fail(end_pos + 1, "'url(' is empty: bgn='~{0}' end='~{1}'");
		}
		byte[] url_raw = Bry_.Mid(src, bgn_pos, end_pos); int url_raw_len = url_raw.length;
		if (Bry_.HasAtBgn(url_raw, Bry_data_image, 0, url_raw_len)) {	// base64
			return inf.Rslt_pass(end_pos  + 1);								// nothing to download; just add entire String
		}
		byte[] url_cleaned = Clean_url(url_raw, url_raw_len);
		if (url_cleaned == null)	// could not clean url
			return inf.Rslt_fail(bgn_pos, "could not extract valid url: bgn='~{0}' end='~{1}'");
		return inf.Rslt_pass(end_pos, url_cleaned);
//			Xoi_css_offline_itm url_itm = (Xoi_css_offline_itm)download_queue.Fetch(url_cleaned);
//			if (url_itm == null) {	// only add unique items for download;
//				url_itm = new Xoi_css_offline_itm(url_cleaned);
//				download_queue.Add(url_cleaned, url_itm);
//			}
//			byte[] file_url = Replace_invalid_chars(Bry_.Copy(url_cleaned));	// NOTE: must call ByteAry.Copy else url_cleaned will change *inside* bry
//			url_itm.File_url_(file_url);
//			bfr.Add_mid(src, tkn_bgn, tkn_end);
//			if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
//			bfr.Add(file_url);
//			if (!quoted) bfr.Add_byte(Byte_ascii.Quote);
//			return inf;
	}
	public static byte[] Clean_url(byte[] raw, int len) {	// return "site/img.png" if "//site/img.png" or "http://site/img.png", "img.png?key=val"
		int bgn = 0;
		if		(Bry_.HasAtBgn(raw, Bry_fwd_slashes	, 0, len)) bgn = Bry_fwd_slashes.length;		// skip if starts with "//"
		else if (Bry_.HasAtBgn(raw, Bry_http		, 0, len)) bgn = Bry_http.length;				// skip if starts with "http://"
		else if (Bry_.HasAtBgn(raw, Bry_https		, 0, len)) bgn = Bry_https.length;				// skip if starts with "https://"
		int slash_pos = Bry_finder.Find_fwd(raw, Byte_ascii.Slash, bgn, len);						// find 1st slash
		if (	slash_pos == Bry_finder.Not_found	// no slashes; must have at least 1 slash to have 2 segments; EX: site.org/img.png
			||	slash_pos == len - 1				// first slash is last char; EX: "site.org/"
			) 
			return null;							// invalid
		int end = len;
		int question_pos = Bry_finder.Find_bwd(raw, Byte_ascii.Question);
		if (question_pos != Bry_finder.Not_found)	// url has query String; EX:site.org/img.png?key=val
			end = question_pos;						// remove query String
		return Bry_.Mid(raw, bgn, end);
	}
	public static byte[] Replace_invalid_chars(byte[] src) {
		int len = src.length;
		for (int i = 0; i < len; i++) {	// convert invalid wnt chars to underscores
			byte b = src[i];
			switch (b) {
				//case Byte_ascii.Slash:
				case Byte_ascii.Backslash: case Byte_ascii.Colon: case Byte_ascii.Asterisk: case Byte_ascii.Question:
				case Byte_ascii.Quote: case Byte_ascii.Lt: case Byte_ascii.Gt: case Byte_ascii.Pipe:
					src[i] = Byte_ascii.Underline;
					break;
			}
		}
		return src;
	}

	public static final byte[] Tkn_url_bry = Bry_.new_ascii_(" url(");
	private static final byte Tkn_import = 1, Tkn_url = 2;
	private static final Btrie_slim_mgr tkns_trie = Btrie_slim_mgr.ci_ascii_()
	.Add_str_byte("@import"		, Tkn_import)
	.Add_bry_bval(Tkn_url_bry	, Tkn_url)
	;
	private static final byte[] 
	  Bry_data_image = Bry_.new_ascii_("data:image/")
	, Bry_http = Bry_.new_ascii_("http://")
	, Bry_https = Bry_.new_ascii_("https://")
	, Bry_fwd_slashes = Bry_.new_ascii_("//")
//		, Bry_http_protocol = Bry_.new_ascii_("http"), Bry_url = Bry_.new_ascii_("url("), Bry_import = Bry_.new_ascii_("@import ")
	;
	public static final byte[] 
	  Bry_comment_bgn = Bry_.new_ascii_("/*XOWA:")
	, Bry_comment_end = Bry_.new_ascii_("*/")
	;
//		private static final int Bry_url_len = Bry_url.length, Bry_import_len = Bry_import.length;
}
//	class Io_download_itm {
//		public byte[] Src_url() {return src_url;} public void Src_url_(byte[] v) {src_url = v;} private byte[] src_url;
//		public Io_url Trg_url() {return trg_url;} public void Trg_url_(Io_url v) {trg_url = v;} private Io_url trg_url;]
//		public String Download_err() {return download_err;} public void Download_err_(String v) {download_err = v;} private String download_err;
//	}
//	interface Io_download_mgr {
//		void Download(Io_download_itm itm);
//	}
