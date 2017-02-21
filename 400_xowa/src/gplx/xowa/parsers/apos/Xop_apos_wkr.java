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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_apos_wkr implements Xop_ctx_wkr {
	private final    List_adp stack = List_adp_.New();	// stores all apos tkns for page; needed to recalc tkn type if apos are dangling
	private int bold_count, ital_count; private Xop_apos_tkn dual_tkn = null;
	private Xop_apos_dat dat = new Xop_apos_dat();
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {Clear();}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {
		this.End_frame(ctx, root, src, src_len, false);
	}
	public void AutoClose(Xop_ctx ctx, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {}
	public int Stack_len() {return stack.Len();}
	private void Clear() {
		bold_count = ital_count = 0;
		dual_tkn = null;
		stack.Clear();
		dat.State_clear();
	}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		cur_pos = Bry_find_.Find_fwd_while(src, cur_pos, src_len, Byte_ascii.Apos);
		int apos_len = cur_pos - bgn_pos;
		dat.Ident(ctx, src, apos_len, cur_pos);
		Xop_apos_tkn apos_tkn = tkn_mkr.Apos(bgn_pos, cur_pos, apos_len, dat.Typ(), dat.Cmd(), dat.Lit_apos());
		ctx.Subs_add(root, apos_tkn);
		ctx.Apos().Reg_tkn(apos_tkn, cur_pos);	// NOTE: register in root ctx (main document)
		return cur_pos;
	}
	private void Reg_tkn(Xop_apos_tkn tkn, int cur_pos) { // REF.MW: Parser|doQuotes
		stack.Add(tkn);			
		switch (tkn.Apos_tid()) {
			case Xop_apos_tkn_.Len_ital: ital_count++; break; 
			case Xop_apos_tkn_.Len_bold: bold_count++; break;
			case Xop_apos_tkn_.Len_dual: //bold_count++; ital_count++;	// NOTE: removed b/c of '''''a''b'' was trying to convert ''''' to bold
				dual_tkn = tkn;
				break; 
		}
		if (dat.Dual_cmd() != 0) {	// earlier dual tkn assumed to be <i><b>; </i> encountered so change dual to <b><i>
			if (dual_tkn == null) throw Err_.new_wo_type("dual tkn is null");	// should never happen
			dual_tkn.Apos_cmd_(dat.Dual_cmd());
			dual_tkn = null;
		}
	}
	public void End_frame(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int cur_pos, boolean skip_cancel_if_lnki_and_apos) {
		int state = dat.State();
		if (state == 0) {Clear(); return;}	// all apos close correctly; nothing dangling; return;

		if (bold_count % 2 == 1 && ital_count % 2 == 1) Convert_bold_to_ital(ctx, src, stack, dat);
		state = dat.State();
		if (state == 0) {Clear(); return;}	// all apos close correctly after converting bold to italic; return;

		int closeCmd = 0, closeTyp = 0;
		byte cur_tkn_tid = ctx.Cur_tkn_tid();
		if (	skip_cancel_if_lnki_and_apos						// NOTE: if \n or tblw
			&&	cur_tkn_tid	== Xop_tkn_itm_.Tid_lnki				// and cur scope is lnki
			)
			return;													// don't end frame
		switch (state) {
			case Xop_apos_tkn_.State_i:		closeTyp = Xop_apos_tkn_.Typ_ital; closeCmd = Xop_apos_tkn_.Cmd_i_end; break;
			case Xop_apos_tkn_.State_b:		closeTyp = Xop_apos_tkn_.Typ_bold; closeCmd = Xop_apos_tkn_.Cmd_b_end; break;
			case Xop_apos_tkn_.State_dual:
			case Xop_apos_tkn_.State_ib:	closeTyp = Xop_apos_tkn_.Typ_dual; closeCmd = Xop_apos_tkn_.Cmd_bi_end; break;
			case Xop_apos_tkn_.State_bi:	closeTyp = Xop_apos_tkn_.Typ_dual; closeCmd = Xop_apos_tkn_.Cmd_ib_end; break;
		}
		ctx.Subs_add(root, ctx.Tkn_mkr().Apos(cur_pos, cur_pos, 0, closeTyp, closeCmd, 0));
		Clear();
	}
	private static void Convert_bold_to_ital(Xop_ctx ctx, byte[] src, List_adp stack, Xop_apos_dat dat) {
		Xop_apos_tkn idxNeg1 = null, idxNeg2 = null, idxNone = null; // look at previous tkn for spaces; EX: "a '''" -> idxNeg1; " a'''" -> idxNeg2; "ab'''" -> idxNone
	    int len = stack.Len(); 
		for (int i = 0; i < len; ++i) {
			Xop_apos_tkn apos = (Xop_apos_tkn)stack.Get_at(i);
			if (apos.Apos_tid() != Xop_apos_tkn_.Typ_bold) continue;	// only look for bold
			int tkn_bgn = apos.Src_bgn();
			boolean idxNeg1Space = tkn_bgn > 0 && src[tkn_bgn - 1] == Byte_ascii.Space;
			boolean idxNeg2Space = tkn_bgn > 1 && src[tkn_bgn - 2] == Byte_ascii.Space;
			if		(idxNeg1 == null && idxNeg1Space)					{idxNeg1 = apos;}
			else if (idxNeg2 == null && idxNeg2Space)					{idxNeg2 = apos;}
			else if (idxNone == null && !idxNeg1Space && !idxNeg2Space)	{idxNone = apos;}
		}
		if		(idxNeg2 != null) Convert_bold_to_ital(ctx, src, idxNeg2); // 1st single letter word
		else if (idxNone != null) Convert_bold_to_ital(ctx, src, idxNone); // 1st multi letter word
		else if	(idxNeg1 != null) Convert_bold_to_ital(ctx, src, idxNeg1); // everything else

		// now recalc all cmds for stack
		dat.State_clear();
		for (int i = 0; i < len; i++) {
			Xop_apos_tkn apos = (Xop_apos_tkn)stack.Get_at(i);
			dat.Ident(ctx, src, apos.Apos_tid(), apos.Src_end());	// NOTE: apos.Typ() must map to apos_len
			int newCmd = dat.Cmd();
			if (newCmd == apos.Apos_cmd()) continue;
			apos.Apos_cmd_(newCmd);
		}
	}
	private static void Convert_bold_to_ital(Xop_ctx ctx, byte[] src, Xop_apos_tkn oldTkn) {
		oldTkn.Apos_tid_(Xop_apos_tkn_.Typ_ital).Apos_cmd_(Xop_apos_tkn_.Cmd_i_bgn).Apos_lit_(oldTkn.Apos_lit() + 1);// NOTE: Cmd_i_bgn may be overridden later
	}
}
