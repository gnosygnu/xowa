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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
class Dg_parser {
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance; private final    Bry_bfr key_bldr = Bry_bfr_.Reset(32);
	private final    List_adp files = List_adp_.New(), lines = List_adp_.New(), words = List_adp_.New();
	private int next_id = 0;
	public Dg_file[] Parse_dir(Io_url dir) {
		Io_url[] fil_urls = Io_mgr.Instance.QueryDir_args(dir).Recur_(true).ExecAsUrlAry();
		this.usr_dlg = Gfo_usr_dlg_.Instance;
		files.Clear();
		int len = fil_urls.length;
		for (int i = 0; i < len; ++i) {
			Io_url fil_url = fil_urls[i];
			byte[] fil_src = Io_mgr.Instance.LoadFilBry_loose(fil_url);
			Dg_file file = Parse_fil(i, fil_url.GenRelUrl_orEmpty(dir), fil_src);
			if (file != null) files.Add(file);
		}
		return (Dg_file[])files.To_ary_and_clear(Dg_file.class);
	}
	private Dg_file Parse_fil(int file_idx, String rel_path, byte[] src) {
		int line_idx = 0; int line_bgn = 0; int src_len = src.length;
		lines.Clear();
		int file_id = ++next_id;
		while (line_bgn < src_len) {
			++line_idx;
			int line_end = Bry_find_.Find_fwd(src, Byte_ascii.Nl, line_bgn); if (line_end == Bry_find_.Not_found) line_end = src_len;
			Dg_rule line = Parse_line(rel_path, file_id, line_idx, src, line_bgn, line_end);
			if (line.Tid() != Dg_rule.Tid_invalid)
				lines.Add(line);
			line_bgn = line_end + 1;
		}
		return new Dg_file(file_id, rel_path, (Dg_rule[])lines.To_ary_and_clear(Dg_rule.class));
	}
	public Dg_rule Parse_line(String rel_path, int file_id, int line_idx, byte[] src, int line_bgn, int line_end) {
		int score = Dg_rule.Score_banned;
		int brack_bgn = line_bgn;
		if (line_end - line_bgn <= 1)			return Dg_rule.Itm_blank;	// ignore blank lines; EX: ""
		if (src[line_bgn] == Byte_ascii.Hash)	return Dg_rule.Itm_comment;	// ignore lines starting with hash; EX: "# comment"
		while (brack_bgn < line_end) {	// look for terms bracketed by "<>"
			if (src[brack_bgn] != Byte_ascii.Lt) {Warn("dg.invalid_line.term must start with angle_bgn", rel_path, line_idx, src, line_bgn, line_end); return Dg_rule.Itm_invalid;}
			int brack_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt, brack_bgn);
			if (brack_end == Bry_find_.Not_found) {Warn("dg.invalid_line.angle_end not found", rel_path, line_idx, src, line_bgn, line_end); return Dg_rule.Itm_invalid;} 
			byte[] word = Bry_.Mid(src, brack_bgn + 1, brack_end);
			words.Add(word);
			int next_pos = brack_end + 1;
			if (next_pos == line_end) {
				score = Dg_rule.Score_banned;
				break;
			}
			byte next = src[next_pos];
			if (next == Byte_ascii.Comma)
				brack_bgn = brack_end + 2;
			else {
				brack_bgn = brack_end + 1;
				if (src[brack_bgn] != Byte_ascii.Lt) {Warn("dg.invalid_line.wrong_term_dlm", rel_path, line_idx, src, line_bgn, line_end); break;}
				brack_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt, brack_bgn);
				if (brack_end == Bry_find_.Not_found) {Warn("dg.invalid_line.score not found", rel_path, line_idx, src, line_bgn, line_end); break;}
				int parse_score = Bry_.To_int_or(src, brack_bgn + 1, brack_end, Int_.Min_value);
				if (parse_score == Int_.Min_value) {Warn("dg.invalid_line.score is invalid", rel_path, line_idx, src, line_bgn, line_end); break;}
				score = parse_score;
				break;
			}
		}
		byte[] key = key_bldr.Add_int_variable(file_id).Add_byte_dot().Add_int_variable(line_idx).To_bry_and_clear();
		return new Dg_rule(file_id, ++next_id, line_idx, Dg_rule.Tid_rule, key, score, Ary_new_by_ary((byte[][])words.To_ary_and_clear(byte[].class)));
	}
	private static Dg_word[] Ary_new_by_ary(byte[][] ary) {
		int ary_len = ary.length;
		Dg_word[] rv = new Dg_word[ary_len];
		for (int i = 0; i < ary_len; ++i) {
			byte[] raw = ary[i];
			rv[i] = new Dg_word(raw);
		}
		return rv;
	}
	private void Warn(String err_msg, String rel_path, int line_idx, byte[] src, int line_bgn, int line_end) {
		usr_dlg.Warn_many("", "", err_msg + "; file=~{0} line_idx=~{1} line=~{2}", rel_path, line_idx, String_.new_u8(src, line_bgn, line_end));
	}
}
