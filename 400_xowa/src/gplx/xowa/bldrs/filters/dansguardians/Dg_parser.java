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
package gplx.xowa.bldrs.filters.dansguardians;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
class Dg_parser {
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance; private final BryWtr key_bldr = BryWtr.NewAndReset(32);
	private final List_adp files = List_adp_.New(), lines = List_adp_.New(), words = List_adp_.New();
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
		return (Dg_file[])files.ToAryAndClear(Dg_file.class);
	}
	private Dg_file Parse_fil(int file_idx, String rel_path, byte[] src) {
		int line_idx = 0; int line_bgn = 0; int src_len = src.length;
		lines.Clear();
		int file_id = ++next_id;
		while (line_bgn < src_len) {
			++line_idx;
			int line_end = BryFind.FindFwd(src, AsciiByte.Nl, line_bgn); if (line_end == BryFind.NotFound) line_end = src_len;
			Dg_rule line = Parse_line(rel_path, file_id, line_idx, src, line_bgn, line_end);
			if (line.Tid() != Dg_rule.Tid_invalid)
				lines.Add(line);
			line_bgn = line_end + 1;
		}
		return new Dg_file(file_id, rel_path, (Dg_rule[])lines.ToAryAndClear(Dg_rule.class));
	}
	public Dg_rule Parse_line(String rel_path, int file_id, int line_idx, byte[] src, int line_bgn, int line_end) {
		int score = Dg_rule.Score_banned;
		int brack_bgn = line_bgn;
		if (line_end - line_bgn <= 1)			return Dg_rule.Itm_blank;	// ignore blank lines; EX: ""
		if (src[line_bgn] == AsciiByte.Hash)	return Dg_rule.Itm_comment;	// ignore lines starting with hash; EX: "# comment"
		while (brack_bgn < line_end) {	// look for terms bracketed by "<>"
			if (src[brack_bgn] != AsciiByte.Lt) {Warn("dg.invalid_line.term must start with angle_bgn", rel_path, line_idx, src, line_bgn, line_end); return Dg_rule.Itm_invalid;}
			int brack_end = BryFind.FindFwd(src, AsciiByte.Gt, brack_bgn);
			if (brack_end == BryFind.NotFound) {Warn("dg.invalid_line.angle_end not found", rel_path, line_idx, src, line_bgn, line_end); return Dg_rule.Itm_invalid;}
			byte[] word = BryLni.Mid(src, brack_bgn + 1, brack_end);
			words.Add(word);
			int next_pos = brack_end + 1;
			if (next_pos == line_end) {
				score = Dg_rule.Score_banned;
				break;
			}
			byte next = src[next_pos];
			if (next == AsciiByte.Comma)
				brack_bgn = brack_end + 2;
			else {
				brack_bgn = brack_end + 1;
				if (src[brack_bgn] != AsciiByte.Lt) {Warn("dg.invalid_line.wrong_term_dlm", rel_path, line_idx, src, line_bgn, line_end); break;}
				brack_end = BryFind.FindFwd(src, AsciiByte.Gt, brack_bgn);
				if (brack_end == BryFind.NotFound) {Warn("dg.invalid_line.score not found", rel_path, line_idx, src, line_bgn, line_end); break;}
				int parse_score = BryUtl.ToIntOr(src, brack_bgn + 1, brack_end, IntUtl.MinValue);
				if (parse_score == IntUtl.MinValue) {Warn("dg.invalid_line.score is invalid", rel_path, line_idx, src, line_bgn, line_end); break;}
				score = parse_score;
				break;
			}
		}
		byte[] key = key_bldr.AddIntVariable(file_id).AddByteDot().AddIntVariable(line_idx).ToBryAndClear();
		return new Dg_rule(file_id, ++next_id, line_idx, Dg_rule.Tid_rule, key, score, Ary_new_by_ary((byte[][])words.ToAryAndClear(byte[].class)));
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
		usr_dlg.Warn_many("", "", err_msg + "; file=~{0} line_idx=~{1} line=~{2}", rel_path, line_idx, StringUtl.NewU8(src, line_bgn, line_end));
	}
}
