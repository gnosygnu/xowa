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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_rel2abs extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	private static final    byte[] Ary_dot_slash = Bry_.new_a7("./"), Ary_dot_dot = Bry_.new_a7(".."), Ary_dot_dot_slash = Bry_.new_a7("../");
	private static void qry_bgns_with_init() {
		qry_bgns_with = Btrie_fast_mgr.cs();
		qry_bgns_with.Add(Byte_ascii.Slash, Int_obj_ref.New(Id_slash));
		qry_bgns_with.Add(Byte_ascii.Dot, Int_obj_ref.New(Id_dot));
		qry_bgns_with.Add(Ary_dot_slash, Int_obj_ref.New(Id_dot_slash));
		qry_bgns_with.Add(Ary_dot_dot, Int_obj_ref.New(Id_dot_dot));
		qry_bgns_with.Add(Ary_dot_dot_slash, Int_obj_ref.New(Id_dot_dot_slash));
	}	private static Btrie_fast_mgr qry_bgns_with;
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW:ParserFunctions_body.php
		byte[] qry = Eval_argx(ctx, src, caller, self);
		byte[] orig = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), 0);
		if (orig.length == 0) orig = ctx.Page().Ttl().Full_txt_w_ttl_case();
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
		try {bfr.Add(Rel2abs(tmp_bfr, ctx.Wiki().Parser_mgr().Rel2abs_ary(), qry, orig));}
		finally {tmp_bfr.Mkr_rls();}
	}
	public static boolean Rel2abs_ttl(byte[] ttl, int bgn, int end) {
		int last = end - 1;
		if (end - bgn > Ttl_max) return false;	// NOTE: some tmpls have long #if statements; quicker to fail here than wait for invalid char below
		boolean rv = false;
		for (int i = bgn; i < end; ++i) {
			switch (ttl[i]) {
				case Byte_ascii.Gt: case Byte_ascii.Pipe:	// simplified version of Xoa_ttl parse; note that Xoa_ttl accepts these if anchor is seen; this proc assumes that anything with anchor and invalid char is an invalid rel2abs; EX: "A../b#c[d" is not valid; DATE:2013-03-31
				case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Curly_bgn: case Byte_ascii.Curly_end:
					return false;
				case Byte_ascii.Slash:
					if 	(	!rv &&
							(	(i == bgn)										// bgns with "/"
							||	(i > bgn 	&& ttl[i - 1] == Byte_ascii.Dot)	// "./"
							||	(i < last 	&& ttl[i + 1] == Byte_ascii.Dot)	// "/."
							)
					) rv = true;
					break;
			}			
		}
		return rv;
	}
	private static final    Int_obj_ref ignore_rel2abs_tid = Int_obj_ref.New_zero();	// TS:return value not used
	public static byte[] Rel2abs(Bry_bfr tmp_bfr, int[] seg_ary, byte[] qry, byte[] src) {return Rel2abs(tmp_bfr, seg_ary, qry, src, ignore_rel2abs_tid);}
	public static byte[] Rel2abs(Bry_bfr tmp_bfr, int[] seg_ary, byte[] qry, byte[] src, Int_obj_ref rel2abs_tid) {
		if (qry_bgns_with == null) qry_bgns_with_init();
		int qry_len = qry.length, src_len = src.length;
		
		// qry_len = RTrim(qry, Byte_ascii.Slash, qry_bgn, qry_len); // not needed, but test anyway		
		if (qry_len == 0) return src;// no qry; return src; EX:{{#rel2abs:|a/b}} -> a/b
		byte[] tmp = src;
		int tmp_adj = 0, i = 0, prv_slash_end = 0, tmp_len = src_len, seg_pos = 0;
		boolean tmp_is_1st = true;
		Btrie_rv trv = new Btrie_rv();
		Object o = qry_bgns_with.Match_at(trv, qry, 0, qry_len);	// check if qry begins with ".", "/", "./", "../"; if it doesn't return;
		if (o != null) {
			int id = ((Int_obj_ref)o).Val();
			rel2abs_tid.Val_(id);
			switch (id) {
				case Id_dot:							// "."
					break;
				case Id_slash:							// "/"
				case Id_dot_slash:						// "./"
				case Id_dot_dot_slash:					// "../"
					break;								// qry is relative to src; noop
				case Id_dot_dot:						// ".."
					int match_end = trv.Pos();
					if (match_end < qry_len && qry[match_end] == Byte_ascii.Dot)	// NOTE: handles "..."; if "...*" then treat as invalid and return; needed for en.wiktionary.org/wiki/Wiktionary:Requests for cleanup/archive/2006
						return qry;
					break;
				default:
					return qry;							// NOTE: lnki parsing currently has fuzzy logic to try to detect rel2abs; for now, if false match, then return original
			}
		}
		else {											// qry is not relative to src; src is ignored; EX:{{#rel2abs:c|a/b}} -> c
			src_len = 0;
			tmp = qry;
			tmp_len = qry_len;
			tmp_is_1st = false;
		}

		// create segs; see NOTE_1 for approach
		byte b = Byte_.Zero;
		boolean loop = true, dot_mode = true;
		while (loop) {
			if (i == tmp_len) {							// finished an ary (either src or qry)
				if (tmp_is_1st) {						// finished src; EX: A/b
					tmp_is_1st = false;
					tmp = qry;
					tmp_adj = src_len + 1;
					tmp_len = src_len + 1 + qry_len;
				}
				else									// finished qry; EX: ../c
					loop = false;	
				b = Byte_ascii.Slash;					// fake a slash between ary and src
			}
			else										// inside ary
				b = tmp[i - tmp_adj];
			switch (b) {
				case Byte_ascii.Dot:					// "."; ignore; note that dot_mode defaults to true
					break;
				default:								// something else besides dot or slash; reset dot_mode
					dot_mode = false;
					break;
				case Byte_ascii.Slash:					// "/"; seg finished
					int seg_len = i - prv_slash_end; // EX: "a/b/c" prv_slash_end = 2; i = 3; len = 1
					boolean create_seg = false;
					switch (seg_len) {
						case 0:							// "//"; ignore
							break; 
						case 1:							// "/?/"
							if (dot_mode) {}			// "/./"; current seg; ignore;
							else create_seg = true;		// something else (EX: /A/); create seg;
							break;
						case 2:	
							if (dot_mode) {				// "/../"; pop seg_ary
								seg_pos -= 2;
								if (seg_pos < 0) return Bry_.Empty; // FUTURE: return MediaWiki error
							}
							else create_seg = true;		// something else; create seg
							break;
						default:						// something else; create seg
							create_seg = true;
							break;
					}
					if (create_seg) {
						seg_ary[seg_pos++] = prv_slash_end;
						seg_ary[seg_pos++] = i;
					}
					prv_slash_end = i + 1;	// +1: place after slash
					dot_mode = true;		// reset dot_mode
					break;
			}
			i++;
		}
		// write segs
		tmp = src; tmp_adj = 0; tmp_is_1st = true; tmp_len = src_len;
		if (src_len == 0) {
			tmp = qry;
			tmp_len = qry_len;
			tmp_is_1st = false;			
		}
		for (int j = 0; j < seg_pos; j += 2) {
			if (j != 0) tmp_bfr.Add_byte(Byte_ascii.Slash);
			if (seg_ary[j] >= tmp_len) {
				tmp = qry;
				tmp_adj = src_len + 1;
			}
			tmp_bfr.Add_mid(tmp, seg_ary[j] - tmp_adj, seg_ary[j+1] - tmp_adj);
		}
		return tmp_bfr.To_bry_and_clear();
	}
	public static final int Ttl_max = 2048;	// ASSUME: max len of 256 * 8 bytes
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_rel2abs;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_rel2abs().Name_(name);}
	public static final int Id_null = 0, Id_slash = 1, Id_dot = 2, Id_dot_slash = 3, Id_dot_dot = 4, Id_dot_dot_slash = 5;
}
/*
NOTE_1:approach (easiest explained with an example)
given qry = "../C/./D" and src = "A/B"
. combine two into a pseudo-array: "A/B/../C/./D"
. iterate over every slash to create "segs_ary"
A  -> [A]			add
B  -> [A, B]		add
.. -> [A]  			pop
C  -> [A, C]		add
.  -> [A, C] 		noop
D  -> [A, C, D] 	add
*/
