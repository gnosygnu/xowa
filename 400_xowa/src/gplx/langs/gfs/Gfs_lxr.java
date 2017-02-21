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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
interface Gfs_lxr {
	int Lxr_tid();
	int Process(Gfs_parser_ctx ctx, int bgn, int end);
}
class Gfs_lxr_whitespace implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_whitespace;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		byte[] src = ctx.Src(); int src_len = ctx.Src_len();
		int rv = Gfs_lxr_.Rv_eos, cur_pos;
		for (cur_pos = end; cur_pos < src_len; cur_pos++) {
			byte b = src[cur_pos];
			Object o = ctx.Trie().Match_at_w_b0(ctx.Trie_rv(), b, src, cur_pos, src_len);
			if (o == null) {
				rv = Gfs_lxr_.Rv_null;
				ctx.Process_null(cur_pos);
				break;
			}
			else {
				Gfs_lxr lxr = (Gfs_lxr)o;
				if (lxr.Lxr_tid() == Gfs_lxr_.Tid_whitespace) {}
				else {
					rv = Gfs_lxr_.Rv_lxr;
					ctx.Process_lxr(cur_pos, lxr);
					break;
				}
			}
		}
		return rv;
	}
	public static final    Gfs_lxr_whitespace Instance = new Gfs_lxr_whitespace(); Gfs_lxr_whitespace() {}
}
class Gfs_lxr_comment_flat implements Gfs_lxr {
	public Gfs_lxr_comment_flat(byte[] bgn_bry, byte[] end_bry) {
		this.bgn_bry = bgn_bry; this.bgn_bry_len = bgn_bry.length;
		this.end_bry = end_bry; this.end_bry_len = end_bry.length;
	} 	byte[] bgn_bry, end_bry; int bgn_bry_len, end_bry_len;
	public int Lxr_tid() {return Gfs_lxr_.Tid_comment;}
	public int Process(Gfs_parser_ctx ctx, int lxr_bgn, int lxr_end) {
		byte[] src = ctx.Src(); int src_len = ctx.Src_len();
		int end_pos = Bry_find_.Find_fwd(src, end_bry, lxr_end, src_len);
		// if (end_pos == Bry_find_.Not_found) throw Err_.new_fmt_("comment is not closed: {0}", String_.new_u8(end_bry));	
		return (end_pos == Bry_find_.Not_found) 
			? src_len						// allow eos to terminate flat comment; needed for "tidy-always-adds-nl-in-textarea" fix; NOTE: DATE:2014-06-21
			: end_pos + end_bry_len;		// position after end_bry
	}
}
class Gfs_lxr_identifier implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_identifier;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		byte[] src = ctx.Src(); int src_len = ctx.Src_len();
		int pos, rv = Gfs_lxr_.Rv_eos;
		for (pos = end; pos < src_len; pos++) {
			byte b = src[pos];
			Object o = ctx.Trie().Match_at_w_b0(ctx.Trie_rv(), b, src, pos, src_len);
			if (o == null) {	// invalid char; stop;
				rv = Gfs_lxr_.Rv_null;
				ctx.Process_null(pos);
				break;
			}
			else {
				Gfs_lxr lxr = (Gfs_lxr)o;
				if (lxr.Lxr_tid() == Gfs_lxr_.Tid_identifier) {}	// still an identifier; continue
				else {	// new lxr (EX: "." in "abc."); (a) hold word of "abc"; mark "." as new lxr;
					ctx.Hold_word(bgn, pos);
					rv = Gfs_lxr_.Rv_lxr;
					ctx.Process_lxr(pos, lxr);
					break;
				}
			}
		}
		if (rv == Gfs_lxr_.Rv_eos) ctx.Process_eos();	// eos
		return rv;
	}
	public static final    Gfs_lxr_identifier Instance = new Gfs_lxr_identifier(); Gfs_lxr_identifier() {}
}
class Gfs_lxr_semic implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_semic;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_identifier:	ctx.Make_nde(bgn, end); ctx.Cur_nde_from_stack(); break;		// a;
			case Gfs_lxr_.Tid_quote:
			case Gfs_lxr_.Tid_paren_end:	ctx.Cur_nde_from_stack(); break;								// a();
			case Gfs_lxr_.Tid_semic:		break;															// a;; ignore;
			default:						ctx.Err_mgr().Fail_invalid_lxr(ctx, bgn, this.Lxr_tid(), Byte_ascii.Semic); break;
		}
		return end;
	}
	public static final    Gfs_lxr_semic Instance = new Gfs_lxr_semic(); Gfs_lxr_semic() {}
}
class Gfs_lxr_dot implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_dot;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_identifier:	ctx.Make_nde(bgn, end); break;		// a.
			case Gfs_lxr_.Tid_paren_end:	break;								// a().
			default:						ctx.Err_mgr().Fail_invalid_lxr(ctx, bgn, this.Lxr_tid(), Byte_ascii.Dot); break;
		}
		return end;
	}
	public static final    Gfs_lxr_dot Instance = new Gfs_lxr_dot(); Gfs_lxr_dot() {}
}
class Gfs_lxr_paren_bgn implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_paren_bgn;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_identifier:	ctx.Make_nde(bgn, end); break;						// a(;
			default:						ctx.Err_mgr().Fail_invalid_lxr(ctx, bgn, this.Lxr_tid(), Byte_ascii.Paren_bgn); break;
		}
		return end;
	}
	public static final    Gfs_lxr_paren_bgn Instance = new Gfs_lxr_paren_bgn(); Gfs_lxr_paren_bgn() {}
}
class Gfs_lxr_paren_end implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_paren_end;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_paren_bgn:
			case Gfs_lxr_.Tid_quote:		break;									// "))", "abc)", "'abc')"
			case Gfs_lxr_.Tid_identifier:	ctx.Make_atr_by_idf(); break;			// 123)
			default:						ctx.Err_mgr().Fail_invalid_lxr(ctx, bgn, this.Lxr_tid(), Byte_ascii.Paren_end); break;
		}
		return end;
	}
	public static final    Gfs_lxr_paren_end Instance = new Gfs_lxr_paren_end(); Gfs_lxr_paren_end() {}
}
class Gfs_lxr_quote implements Gfs_lxr {
	public Gfs_lxr_quote(byte[] bgn_bry, byte[] end_bry) {
		this.bgn_bry_len = bgn_bry.length;
		this.end_bry = end_bry; this.end_bry_len = end_bry.length;
	}	private byte[] end_bry; private int bgn_bry_len, end_bry_len;
	public int Lxr_tid() {return Gfs_lxr_.Tid_quote;}
	public int Process(Gfs_parser_ctx ctx, int lxr_bgn, int lxr_end) {
		byte[] src = ctx.Src(); int src_len = ctx.Src_len();
		int end_pos = Bry_find_.Find_fwd(src, end_bry, lxr_end, src_len);
		if (end_pos == Bry_find_.Not_found) throw Err_.new_wo_type("quote is not closed", "end", String_.new_u8(end_bry));
		Bry_bfr bfr = ctx.Tmp_bfr().Clear();
		int prv_pos = lxr_end;
		int nxt_pos = end_pos + end_bry_len;
		if (Bry_.Match(src, nxt_pos, nxt_pos + end_bry_len, end_bry)) {	// end_bry is doubled; EX: end_bry = ' and raw = a''
			while (true) {
				bfr.Add_mid(src, prv_pos, end_pos);		// add everything up to end_bry
				bfr.Add(end_bry);						// add end_bry
				prv_pos = nxt_pos + end_bry_len;		// set prv_pos to after doubled end_bry
				end_pos = Bry_find_.Find_fwd(src, end_bry, prv_pos, src_len);
				if (end_pos == Bry_find_.Not_found) throw Err_.new_wo_type("quote is not closed", "end", String_.new_u8(end_bry));
				nxt_pos = end_pos + end_bry_len;
				if (!Bry_.Match(src, nxt_pos, nxt_pos + end_bry_len, end_bry)) {
					bfr.Add_mid(src, prv_pos, end_pos);
					break;				
				}
			}
			ctx.Make_atr_by_bry(lxr_bgn + bgn_bry_len, end_pos, bfr.To_bry_and_clear());
		}
		else
			ctx.Make_atr(lxr_bgn + bgn_bry_len, end_pos);
		return end_pos + end_bry_len;	// position after quote
	}
}
class Gfs_lxr_curly_bgn implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_curly_bgn;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_identifier:		ctx.Make_nde(bgn, end); ctx.Stack_add(); break;		// a{;
			case Gfs_lxr_.Tid_paren_end:		ctx.Stack_add(); break;								// a(){; NOTE: node exists but needs to be pushed onto stack
			default:							ctx.Err_mgr().Fail_invalid_lxr(ctx, bgn, this.Lxr_tid(), Byte_ascii.Curly_bgn); break;
		}
		return end;
	}
	public static final    Gfs_lxr_curly_bgn Instance = new Gfs_lxr_curly_bgn(); Gfs_lxr_curly_bgn() {}
}
class Gfs_lxr_curly_end implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_curly_end;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		ctx.Stack_pop(bgn);
		return end;
	}
	public static final    Gfs_lxr_curly_end Instance = new Gfs_lxr_curly_end(); Gfs_lxr_curly_end() {}
}
class Gfs_lxr_equal implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_eq;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		ctx.Make_nde(bgn, end).Op_tid_(Gfs_nde.Op_tid_assign);
		return end;
	}
	public static final    Gfs_lxr_equal Instance = new Gfs_lxr_equal(); Gfs_lxr_equal() {}
}
class Gfs_lxr_comma implements Gfs_lxr {
	public int Lxr_tid() {return Gfs_lxr_.Tid_comma;}
	public int Process(Gfs_parser_ctx ctx, int bgn, int end) {
		switch (ctx.Prv_lxr()) {
			case Gfs_lxr_.Tid_identifier:	ctx.Make_atr_by_idf(); break;			// 123,
		}
		return end;
	}
	public static final    Gfs_lxr_comma Instance = new Gfs_lxr_comma(); Gfs_lxr_comma() {}
}
