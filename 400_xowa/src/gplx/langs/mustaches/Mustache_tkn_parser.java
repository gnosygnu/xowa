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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_tkn_parser {
	private byte[] src; private int src_end;
	private final    Mustache_tkn_def tkn_def = new Mustache_tkn_def();
	public Mustache_tkn_itm Parse(byte[] src) {return Parse(src, 0, src.length);}
	public Mustache_tkn_itm Parse(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_end = src_end;
		Mustache_tkn_root root = new Mustache_tkn_root();
		Parse_grp(root, src_bgn);
		return root;
	}
	private int Parse_grp(Mustache_tkn_itm owner, int src_bgn) {
		List_adp subs_list = List_adp_.New();
		int txt_bgn = src_bgn;
		boolean end_grp = false;
		while (true) {// loop for "{{"
			int lhs_bgn = Bry_find_.Find_fwd(src, tkn_def.Variable_lhs, txt_bgn, src_end);	// next "{{"
			if (lhs_bgn == Bry_find_.Not_found) {											// no more "{{"
				subs_list.Add(new Mustache_tkn_text(src, txt_bgn, src_end));				// add everything between prv "}}" and cur "{{"
				break;
			}
			int lhs_end = lhs_bgn + tkn_def.Variable_lhs_len;

			Mustache_tkn_data tkn_data = new Mustache_tkn_data(src[lhs_end]);				// preview tkn
			lhs_end += tkn_data.lhs_end_adj;

			int rhs_bgn = Bry_find_.Find_fwd(src, tkn_def.Variable_rhs, lhs_end, src_end);	// next "}}"
			if (rhs_bgn == Bry_find_.Not_found) throw Fail(lhs_bgn, "unclosed tag");		// fail if no "}}"
			int rhs_end = rhs_bgn + tkn_def.Variable_rhs_len;
			if (tkn_data.rhs_bgn_chk != Byte_ascii.Null) {
				if (src[rhs_bgn] != tkn_data.rhs_bgn_chk) throw Fail(lhs_end, "invalid check byte");
				++rhs_end;	// skip the chk_byte; note that bottom of function will skip "}}" by adding +2 
			}


			int txt_end = lhs_bgn;															// get text tkn
			if (tkn_data.ws_ignore) {
				int new_txt_bgn = Trim_bwd_to_nl(src, txt_bgn, txt_end);
				if (new_txt_bgn != -1) {
					int new_txt_end = Trim_fwd_to_nl(src, rhs_end, src_end);
					if (new_txt_end != -1) {
						txt_end = new_txt_bgn;
						rhs_end = new_txt_end == src_end ? src_end : new_txt_end + 1;
					}
				}
			}
			if (txt_end > txt_bgn)															// ignore 0-byte text tkns; occurs when consecutive tkns; EX: {{v1}}{{v2}} will try to create text tkn between "}}{{"
				subs_list.Add(new Mustache_tkn_text(src, txt_bgn, txt_end));				// add everything between prv "}}" and cur "{{"

			txt_bgn = Parse_itm(tkn_data, subs_list, lhs_end, rhs_bgn, rhs_end);			// do parse
			if (txt_bgn < 0) {																// NOTE: txt_bgn < 0 means end grp
				txt_bgn *= -1;
				end_grp = true;
			}
			if (end_grp) break;
		}
		if (subs_list.Count() > 0)															// don't create subs if no members
			owner.Subs_ary_((Mustache_tkn_itm[])subs_list.To_ary_and_clear(Mustache_tkn_itm.class));
		return txt_bgn;
	}
	private int Parse_itm(Mustache_tkn_data tkn_data, List_adp subs_list, int lhs_end, int rhs_bgn, int rhs_end) {
		byte[] val_bry = Bry_.Mid(src, lhs_end, rhs_bgn);
		Mustache_tkn_base tkn = null;
		switch (tkn_data.tid) {
			default:								throw Err_.new_unhandled(tkn_data.tid);
			case Mustache_tkn_def.Variable:			tkn = new Mustache_tkn_variable(val_bry);	break;
			case Mustache_tkn_def.Comment:			tkn = new Mustache_tkn_comment();			break;
			case Mustache_tkn_def.Partial:			tkn = new Mustache_tkn_partial(val_bry);	break;
			case Mustache_tkn_def.Delimiter_bgn:	tkn = new Mustache_tkn_delimiter(val_bry);	break;	// TODO_OLD: implement delimiter; EX: {{=<% %>=}}
			case Mustache_tkn_def.Escape_bgn:		tkn = new Mustache_tkn_escape(val_bry);		break;
			case Mustache_tkn_def.Section:			tkn = new Mustache_tkn_section(val_bry);	break;
			case Mustache_tkn_def.Inverted:			tkn = new Mustache_tkn_inverted(val_bry);	break;
			case Mustache_tkn_def.Grp_end: {
				return -(rhs_end);	// pop the stack
			}
		}
		subs_list.Add(tkn);
		if (tkn_data.parse_grp) {
			return Parse_grp(tkn, rhs_end);
		}
		else
			return rhs_end;
	}
	private Err Fail(int pos, String fmt, Object... args) {
		return Err_.new_("mustache", fmt, "excerpt", Bry_.Mid_by_len_safe(src, pos, 32));
	}
	private static int Trim_bwd_to_nl(byte[] src, int txt_bgn, int txt_end) {
		int stop = txt_bgn - 1;
		int pos = txt_end - 1;
		while (pos > stop) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Tab:
				case Byte_ascii.Space:	--pos; break;
				case Byte_ascii.Nl:		return pos + 1;	// 1 char after \n
				default:				return -1;
			}
		}
		return -1;
	}
	private static int Trim_fwd_to_nl(byte[] src, int txt_bgn, int txt_end) {
		int pos = txt_bgn;
		while (pos < txt_end) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Tab:
				case Byte_ascii.Space:	++pos; break;
				case Byte_ascii.Nl:		return pos;
				default:				return -1;
			}
		}
		return -1;
	}
}
class Mustache_tkn_data {
	public int tid;
	public int lhs_end_adj;
	public byte rhs_bgn_chk;
	public boolean parse_grp;
	public boolean ws_ignore;
	public Mustache_tkn_data(byte b) {
		tid = b;
		parse_grp = ws_ignore = false;
		lhs_end_adj = 1;
		rhs_bgn_chk = Byte_ascii.Null;
		switch (b) {
			default:								lhs_end_adj = 0; tid = Mustache_tkn_def.Variable; break;
			case Mustache_tkn_def.Comment:
			case Mustache_tkn_def.Partial:
			case Mustache_tkn_def.Grp_end:			ws_ignore = true; break;
			case Mustache_tkn_def.Delimiter_bgn:	rhs_bgn_chk = Mustache_tkn_def.Delimiter_end; break;	// check for "=}}"; TODO_OLD: implement delimiter; EX: {{=<% %>=}}
			case Mustache_tkn_def.Escape_bgn:		rhs_bgn_chk = Mustache_tkn_def.Escape_end; break;		// check for ""
			case Mustache_tkn_def.Section:
			case Mustache_tkn_def.Inverted:			ws_ignore = true; parse_grp = true; break;
		}
	}
}
