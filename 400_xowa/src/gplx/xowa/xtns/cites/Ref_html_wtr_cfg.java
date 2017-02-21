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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
public class Ref_html_wtr_cfg {
	public Bry_fmtr Itm_html() 			{return itm_html;} 			private Bry_fmtr itm_html; 			public Ref_html_wtr_cfg Itm_html_(String v) {itm_html 				= Bry_fmtr.new_(v, "itm_id", "grp_id", "grp_key"); return this;}
	public Bry_fmtr Itm_id_uid() 		{return itm_id_uid;} 		private Bry_fmtr itm_id_uid; 		public Ref_html_wtr_cfg Itm_id_uid_(String v) {itm_id_uid 			= Bry_fmtr.new_(v, "uid"); return this;}
	public Bry_fmtr Itm_id_key_one() 	{return itm_id_key_one;} 	private Bry_fmtr itm_id_key_one; 	public Ref_html_wtr_cfg Itm_id_key_one_(String v) {itm_id_key_one 	= Bry_fmtr.new_(v, "itm_key", "uid", "minor"); return this;}
	public Bry_fmtr Itm_id_key_many() 	{return itm_id_key_many;} 	private Bry_fmtr itm_id_key_many; 	public Ref_html_wtr_cfg Itm_id_key_many_(String v) {itm_id_key_many	= Bry_fmtr.new_(v, "itm_key", "uid"); return this;}
	public Bry_fmtr Itm_grp_text() 		{return itm_grp_text;} 		private Bry_fmtr itm_grp_text; 		public Ref_html_wtr_cfg Itm_grp_text_(String v) {itm_grp_text 		= Bry_fmtr.new_(v, "grp_key", "major"); return this;}
	public Bry_fmtr Grp_html_one() 		{return grp_html_one;} 		private Bry_fmtr grp_html_one; 		public Ref_html_wtr_cfg Grp_html_one_(String v) {grp_html_one 		= Bry_fmtr.new_(v, "grp_id", "itm_id", "text"); return this;}
	public Bry_fmtr Grp_html_many() 	{return grp_html_many;}		private Bry_fmtr grp_html_many; 	public Ref_html_wtr_cfg Grp_html_many_(String v) {grp_html_many 	= Bry_fmtr.new_(v, "grp_id", "related_ids", "text"); return this;}
	public Bry_fmtr Grp_html_list()		{return grp_html_list;}		private Bry_fmtr grp_html_list;		public Ref_html_wtr_cfg Grp_html_list_(String v) {grp_html_list		= Bry_fmtr.new_(v, "itm_id", "backlabel"); return this;}
	public Bry_fmtr Grp_id_uid() 		{return grp_id_uid;} 		private Bry_fmtr grp_id_uid; 		public Ref_html_wtr_cfg Grp_id_uid_(String v) {grp_id_uid 			= Bry_fmtr.new_(v, "uid"); return this;}
	public Bry_fmtr Grp_id_key() 		{return grp_id_key;} 		private Bry_fmtr grp_id_key; 		public Ref_html_wtr_cfg Grp_id_key_(String v) {grp_id_key 			= Bry_fmtr.new_(v, "itm_key", "major"); return this;}
	public byte[][] Backlabels() {return backlabels;} private byte[][] backlabels;
	public int Backlabels_len() {return backlabels_len;} private int backlabels_len;
	public byte[] Grp_bgn() {return grp_bgn;} private byte[] grp_bgn;
	public byte[] Grp_end() {return grp_end;} private byte[] grp_end;
	public void Init_by_wiki(Xowe_wiki wiki) {
		byte[] backlabels_bry = wiki.Msg_mgr().Val_by_key_obj(Msg_backlabels);
		Backlabels_(Ref_backlabels_xby_bry(backlabels_bry));
	}
	public void Backlabels_(byte[][] v) {
		backlabels		= v;
		backlabels_len	= v.length;
	}
	public static final    byte[] Msg_backlabels_err = Bry_.new_a7("cite_error_no_link_label_group");
	private static final    byte[] Msg_backlabels = Bry_.new_a7("cite_references_link_many_format_backlink_labels");
	public static final    byte[] Note_href_bgn = Bry_.new_a7("#cite_note-");	// for TOC
	public static Ref_html_wtr_cfg new_() {
		Ref_html_wtr_cfg rv = new Ref_html_wtr_cfg();
		rv.Itm_html_		("<sup id=\"cite_ref-~{itm_id}\" class=\"reference\"><a href=\"#cite_note-~{grp_id}\">[~{grp_key}]</a></sup>");
		rv.Itm_id_uid_		("~{uid}");
		rv.Itm_id_key_one_	("~{itm_key}_~{uid}-~{minor}");
		rv.Itm_id_key_many_	("~{itm_key}-~{uid}");
		rv.Itm_grp_text_	("~{grp_key} ~{major}");
		rv.Grp_html_one_	("<li id=\"cite_note-~{grp_id}\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-~{itm_id}\">^</a></span> <span class=\"reference-text\">~{text}</span></li>\n");
		rv.Grp_html_many_	("<li id=\"cite_note-~{grp_id}\"><span class=\"mw-cite-backlink\">^~{related_ids}</span> <span class=\"reference-text\">~{text}</span></li>\n");
		rv.Grp_html_list_	(" <sup><a href=\"#cite_ref-~{itm_id}\">~{backlabel}</a></sup>");
		rv.Grp_id_uid_		("~{uid}");
		rv.Grp_id_key_		("~{itm_key}-~{major}");
		rv.grp_bgn = Bry_.new_a7("<ol class=\"references\">\n");
		rv.grp_end = Bry_.new_a7("</ol>\n");
		rv.Backlabels_		(Ref_backlabels_default);
		return rv;
	}	Ref_html_wtr_cfg() {}
	private static final    byte[][] Ref_backlabels_default = Ref_backlabels_xby_str_ary(String_.Ary	// TEST:default backlabels for test only; actual backlabels will be overrriden by MediaWiki:Cite_references_link_many_format_backlink_labels; DATE:2014-06-07
	(  "a",  "b",  "c",  "d",  "e",  "f",  "g",  "h",  "i",  "j",  "k",  "l",  "m",  "n",  "o",  "p",  "q",  "r",  "s",  "t",  "u",  "v",  "w",  "x",  "y",  "z"
	, "aa", "ab", "ac", "ad", "ae", "af", "ag", "ah", "ai", "aj", "ak", "al", "am", "an", "ao", "ap", "aq", "ar", "as", "at", "au", "av", "aw", "ax", "ay", "az"
	, "ba", "bb", "bc", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bk", "bl", "bm", "bn", "bo", "bp", "bq", "br", "bs", "bt", "bu", "bv", "bw", "bx", "by", "bz"
	, "ca", "cb", "cc", "cd", "ce", "cf", "cg", "ch", "ci", "cj", "ck", "cl", "cm", "cn", "co", "cp", "cq", "cr", "cs", "ct", "cu", "cv", "cw", "cx", "cy", "cz"
	, "da", "db", "dc", "dd", "de", "df", "dg", "dh", "di", "dj", "dk", "dl", "dm", "dn", "do", "dp", "dq", "dr", "ds", "dt", "du", "dv", "dw", "dx", "dy", "dz"
	, "ea", "eb", "ec", "ed", "ee", "ef", "eg", "eh", "ei", "ej", "ek", "el", "em", "en", "eo", "ep", "eq", "er", "es", "et", "eu", "ev", "ew", "ex", "ey", "ez"
	, "fa", "fb", "fc", "fd", "fe", "ff", "fg", "fh", "fi", "fj", "fk", "fl", "fm", "fn", "fo", "fp", "fq", "fr", "fs", "ft", "fu", "fv", "fw", "fx", "fy", "fz"
	, "ga", "gb", "gc", "gd", "ge", "gf", "gg", "gh", "gi", "gj", "gk", "gl", "gm", "gn", "go", "gp", "gq", "gr", "gs", "gt", "gu", "gv", "gw", "gx", "gy", "gz"
	, "ha", "hb", "hc", "hd", "he", "hf", "hg", "hh", "hi", "hj", "hk", "hl", "hm", "hn", "ho", "hp", "hq", "hr", "hs", "ht", "hu", "hv", "hw", "hx", "hy", "hz"
	, "ia", "ib", "ic", "id", "ie", "if", "ig", "ih", "ii", "ij", "ik", "il", "im", "in", "io", "ip", "iq", "ir", "is", "it", "iu", "iv", "iw", "ix", "iy", "iz"
	, "ja", "jb", "jc", "jd", "je", "jf", "jg", "jh", "ji", "jj", "jk", "jl", "jm", "jn", "jo", "jp", "jq", "jr", "js", "jt", "ju", "jv", "jw", "jx", "jy", "jz"
	, "ka", "kb", "kc", "kd", "ke", "kf", "kg", "kh", "ki", "kj", "kk", "kl", "km", "kn", "ko", "kp", "kq", "kr", "ks", "kt", "ku", "kv", "kw", "kx", "ky", "kz"
	, "la", "lb", "lc", "ld", "le", "lf", "lg", "lh", "li", "lj", "lk", "ll", "lm", "ln", "lo", "lp", "lq", "lr", "ls", "lt", "lu", "lv", "lw", "lx", "ly", "lz"
	, "ma", "mb", "mc", "md", "me", "mf", "mg", "mh", "mi", "mj", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz"
	, "na", "nb", "nc", "nd", "ne", "nf", "ng", "nh", "ni", "nj", "nk", "nl", "nm", "nn", "no", "np", "nq", "nr", "ns", "nt", "nu", "nv", "nw", "nx", "ny", "nz"
	, "oa", "ob", "oc", "od", "oe", "of", "og", "oh", "oi", "oj", "ok", "ol", "om", "on", "oo", "op", "oq", "or", "os", "ot", "ou", "ov", "ow", "ox", "oy", "oz"
	, "pa", "pb", "pc", "pd", "pe", "pf", "pg", "ph", "pi", "pj", "pk", "pl", "pm", "pn", "po", "pp", "pq", "pr", "ps", "pt", "pu", "pv", "pw", "px", "py", "pz"
	, "qa", "qb", "qc", "qd", "qe", "qf", "qg", "qh", "qi", "qj", "qk", "ql", "qm", "qn", "qo", "qp", "qq", "qr", "qs", "qt", "qu", "qv", "qw", "qx", "qy", "qz"
	, "ra", "rb", "rc", "rd", "re", "rf", "rg", "rh", "ri", "rj", "rk", "rl", "rm", "rn", "ro", "rp", "rq", "rr", "rs", "rt", "ru", "rv", "rw", "rx", "ry", "rz"
	, "sa", "sb", "sc", "sd", "se", "sf", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sp", "sq", "sr", "ss", "st", "su", "sv", "sw", "sx", "sy", "sz"
	, "ta", "tb", "tc", "td", "te", "tf", "tg", "th", "ti", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tq", "tr", "ts", "tt", "tu", "tv", "tw", "tx", "ty", "tz"
	, "ua", "ub", "uc", "ud", "ue", "uf", "ug", "uh", "ui", "uj", "uk", "ul", "um", "un", "uo", "up", "uq", "ur", "us", "ut", "uu", "uv", "uw", "ux", "uy", "uz"
	, "va", "vb", "vc", "vd", "ve", "vf", "vg", "vh", "vi", "vj", "vk", "vl", "vm", "vn", "vo", "vp", "vq", "vr", "vs", "vt", "vu", "vv", "vw", "vx", "vy", "vz"
	, "wa", "wb", "wc", "wd", "we", "wf", "wg", "wh", "wi", "wj", "wk", "wl", "wm", "wn", "wo", "wp", "wq", "wr", "ws", "wt", "wu", "wv", "ww", "wx", "wy", "wz"
	, "xa", "xb", "xc", "xd", "xe", "xf", "xg", "xh", "xi", "xj", "xk", "xl", "xm", "xn", "xo", "xp", "xq", "xr", "xs", "xt", "xu", "xv", "xw", "xx", "xy", "xz"
	, "ya", "yb", "yc", "yd", "ye", "yf", "yg", "yh", "yi", "yj", "yk", "yl", "ym", "yn", "yo", "yp", "yq", "yr", "ys", "yt", "yu", "yv", "yw", "yx", "yy", "yz"
	, "za", "zb", "zc", "zd", "ze", "zf", "zg", "zh", "zi", "zj", "zk", "zl", "zm", "zn", "zo", "zp", "zq", "zr", "zs", "zt", "zu", "zv", "zw", "zx", "zy", "zz"
	));
	private static byte[][] Ref_backlabels_xby_str_ary(String[] ary) {
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++)
			rv[i] = Bry_.new_a7(ary[i]);
		return rv;
	}
	public static byte[][] Ref_backlabels_xby_bry(byte[] raw) {
		if (raw == null) return Ref_backlabels_default;
		List_adp list = List_adp_.New();
		int len = raw.length, pos = 0, bgn = -1;
		while (true) {
			boolean last = pos == len;
			byte b = last ? Byte_ascii.Space : raw[pos];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab:
					if (bgn != -1) { // guard against leading ws, or multiple ws; EX: "bos\s\s" or "\s\s"
						list.Add(Bry_.Mid(raw, bgn, pos));
						bgn = -1;
					}
					break;
				default:
					if (bgn == -1)
						bgn = pos;
					break;
			}
			if (last) break;
			++pos;
		}
		return (byte[][])list.To_ary_and_clear(byte[].class);
	}
}
