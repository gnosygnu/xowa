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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
class Xob_url_fixer {
	public static byte[] Fix(byte[] site, byte[] src, int src_len) {	// return "site/img.png" if "//site/img.png" or "http://site/img.png"; also, handle "img.png?key=val"
		int bgn = 0; int bgn_tkn_tid = 0;
		Object o = Xob_url_fixer_tkn.Bgn_trie().Match_bgn(src, bgn, src_len);
		if (o != null) {
			Xob_url_fixer_tkn tkn = (Xob_url_fixer_tkn)o;
			bgn_tkn_tid = tkn.Tid();
			switch (bgn_tkn_tid) {
				case Xob_url_fixer_tkn.Tid_bgn_slash_2:
				case Xob_url_fixer_tkn.Tid_bgn_http:
				case Xob_url_fixer_tkn.Tid_bgn_https:
					bgn = tkn.Raw_len();				// remove "//", "http://", "https://"
					break;
				case Xob_url_fixer_tkn.Tid_bgn_slash_1:	// convert "/a" to "site/a"
					src = Bry_.Add(site, src);
					src_len = src.length;
					break;
			}
		}
		int pos = bgn, end = src_len; boolean no_slashes = true;
		Btrie_slim_mgr mid_trie = Xob_url_fixer_tkn.Mid_trie();
		while (pos < src_len) {
			byte b = src[pos];
			o = mid_trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o != null) {
				Xob_url_fixer_tkn tkn = (Xob_url_fixer_tkn)o;
				switch (tkn.Tid()) {
					case Xob_url_fixer_tkn.Tid_mid_slash:				if (no_slashes) no_slashes = false; break;
					case Xob_url_fixer_tkn.Tid_mid_question:			end = pos; pos = src_len; break;
					case Xob_url_fixer_tkn.Tid_mid_rel_1:
					case Xob_url_fixer_tkn.Tid_mid_rel_2:
						Bry_bfr tmp_bfr = Bry_bfr.new_(src_len);
						byte[] to_rel_root = Bry_.Mid(src, bgn, pos);
						byte[] to_rel_qry  = Bry_.Mid(src, pos, src_len);
						src = gplx.xowa.xtns.pfuncs.ttls.Pfunc_rel2abs.Rel2abs(tmp_bfr, to_rel_qry, to_rel_root, Int_obj_ref.neg1_());
						bgn = pos = 0;
						end = src_len = src.length;
						no_slashes = true;
						break;
				}
			}
			++pos;
		}
		if (no_slashes)	return null;	// invalid; EX: "//site"				
		return Bry_.Mid(src, bgn, end);
	}
}
class Xob_url_fixer_tkn {
	public Xob_url_fixer_tkn(int tid, byte[] raw) {this.tid = tid; this.raw = raw; this.raw_len = raw.length;}
	public int Tid() {return tid;} private int tid;
	public byte[] Raw() {return raw;} private byte[] raw;
	public int Raw_len() {return raw_len;} private int raw_len;
	public static Xob_url_fixer_tkn new_(int tid, String raw) {return new Xob_url_fixer_tkn(tid, Bry_.new_utf8_(raw));}

	private static void trie_add(Btrie_slim_mgr trie, int tid, String s) {trie.Add_obj(s, new_(tid, s));}
	public static final int Tid_bgn_slash_1 = 1, Tid_bgn_slash_2 = 2, Tid_bgn_http = 3, Tid_bgn_https = 4;
	private static Btrie_slim_mgr bgn_trie;
	public static Btrie_slim_mgr Bgn_trie() {
		if (bgn_trie == null) {
			bgn_trie = Btrie_slim_mgr.ci_ascii_();
			trie_add(bgn_trie, Tid_bgn_slash_1	, "/");
			trie_add(bgn_trie, Tid_bgn_slash_2	, "//");
			trie_add(bgn_trie, Tid_bgn_http		, "http://");
			trie_add(bgn_trie, Tid_bgn_https	, "https://");
		}
		return bgn_trie;
	}
	public static final int Tid_mid_rel_1 = 1, Tid_mid_rel_2 = 2, Tid_mid_slash = 3, Tid_mid_question = 4;
	private static Btrie_slim_mgr mid_trie;
	public static Btrie_slim_mgr Mid_trie() {
		if (mid_trie == null) {
			mid_trie = Btrie_slim_mgr.ci_ascii_();
			trie_add(mid_trie, Tid_mid_rel_1	, "/../");
			trie_add(mid_trie, Tid_mid_rel_2	, "/./");
			trie_add(mid_trie, Tid_mid_slash	, "/");
			trie_add(mid_trie, Tid_mid_question	, "?");
		}
		return mid_trie;
	}
}
