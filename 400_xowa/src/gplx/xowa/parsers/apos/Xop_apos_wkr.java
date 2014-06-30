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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_apos_wkr implements Xop_ctx_wkr {
	public Xop_apos_dat Dat() {return dat;} private Xop_apos_dat dat = new Xop_apos_dat();
	private ListAdp stack = ListAdp_.new_(); private int bold_count, ital_count; private Xop_apos_tkn dual_tkn = null;
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {
		Reset();
	}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {
		this.EndFrame(ctx, root, src, src_len, false);
	}
	public void AutoClose(Xop_ctx ctx, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {}
	public int Stack_len() {return stack.Count();}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		cur_pos = Bry_finder.Find_fwd_while(src, cur_pos, src_len, Byte_ascii.Apos);
		int apos_len = cur_pos - bgn_pos;
		dat.Ident(ctx, src, apos_len, cur_pos);
		Xop_apos_tkn apos_tkn = tkn_mkr.Apos(bgn_pos, cur_pos, apos_len, dat.Typ(), dat.Cmd(), dat.Lit_apos());
		ctx.Subs_add(root, apos_tkn);
		ctx.Apos().RegTkn(apos_tkn, cur_pos);
		return cur_pos;
	}
	public void RegTkn(Xop_apos_tkn tkn, int cur_pos) { // REF.MW: Parser|doQuotes
		stack.Add(tkn);			
		switch (tkn.Apos_tid()) {
			case Xop_apos_tkn_.Len_ital: ital_count++; break; 
			case Xop_apos_tkn_.Len_bold: bold_count++; break;
			case Xop_apos_tkn_.Len_dual: //bold_count++; ital_count++;	// NOTE: removed b/c of '''''a''b'' was trying to convert ''''' to bold
				dual_tkn = tkn;
				break; 
		}
		if (dat.Dual_cmd() != 0) {	// earlier dual tkn assumed to be <i><b>; </i> encountered so change dual to <b><i>
			if (dual_tkn == null) throw Err_.new_("dual tkn is null");	// should never happen
			dual_tkn.Apos_cmd_(dat.Dual_cmd());
			dual_tkn = null;
		}
	}
	public void EndFrame(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int cur_pos, boolean skip_cancel_if_lnki_and_apos) {
		int state = dat.State();
		if (state == 0) {Reset(); return;}
		if (bold_count % 2 == 1 && ital_count % 2 == 1) ConvertBoldToItal(ctx, src);

		state = dat.State();
		int closeCmd = 0, closeTyp = 0;
		if (state == 0) {Reset(); return;}	// all closed: return
		byte cur_tkn_tid = ctx.Cur_tkn_tid();
		Xop_apos_tkn prv = Previous_bgn(stack, closeTyp);
		if (	skip_cancel_if_lnki_and_apos						// NOTE: if \n or tblw
			&&	cur_tkn_tid	== Xop_tkn_itm_.Tid_lnki				// and cur scope is lnki
//				&&	prv.Ctx_tkn_tid() != Xop_tkn_itm_.Tid_lnki			// but apos_bgn is not lnki; NOTE: disabled on 2013-11-10
			)
			return;													// don't end frame
		switch (state) {
			case Xop_apos_tkn_.State_i:		closeTyp = Xop_apos_tkn_.Typ_ital; closeCmd = Xop_apos_tkn_.Cmd_i_end; break;
			case Xop_apos_tkn_.State_b:		closeTyp = Xop_apos_tkn_.Typ_bold; closeCmd = Xop_apos_tkn_.Cmd_b_end; break;
			case Xop_apos_tkn_.State_dual:
			case Xop_apos_tkn_.State_ib:	closeTyp = Xop_apos_tkn_.Typ_dual; closeCmd = Xop_apos_tkn_.Cmd_bi_end; break;
			case Xop_apos_tkn_.State_bi:	closeTyp = Xop_apos_tkn_.Typ_dual; closeCmd = Xop_apos_tkn_.Cmd_ib_end; break;
		}
		ctx.Msg_log().Add_itm_none(Xop_apos_log.Dangling_apos, src, prv.Src_bgn(), cur_pos);
		ctx.Subs_add(root, ctx.Tkn_mkr().Apos(cur_pos, cur_pos, 0, closeTyp, closeCmd, 0));
		Reset();
	}
	private void ConvertBoldToItal(Xop_ctx ctx, byte[] src) {
		Xop_apos_tkn idxNeg1 = null, idxNeg2 = null, idxNone = null; // look at previous tkn for spaces; EX: "a '''" -> idxNeg1; " a'''" -> idxNeg2; "ab'''" -> idxNone
	    int tknsLen = stack.Count(); 
		for (int i = 0; i < tknsLen; i++) {
			Xop_apos_tkn apos = (Xop_apos_tkn)stack.FetchAt(i);
			if (apos.Apos_tid() != Xop_apos_tkn_.Typ_bold) continue;	// only look for bold
			int tknBgn = apos.Src_bgn();
			boolean idxNeg1Space = tknBgn > 0 && src[tknBgn - 1] == Byte_ascii.Space;
			boolean idxNeg2Space = tknBgn > 1 && src[tknBgn - 2] == Byte_ascii.Space;
			if		(idxNeg1 == null && idxNeg1Space)					{idxNeg1 = apos;}
			else if (idxNeg2 == null && idxNeg2Space)					{idxNeg2 = apos;}
			else if (idxNone == null && !idxNeg1Space && !idxNeg2Space)	{idxNone = apos;}
		}
		if		(idxNeg2 != null) ConvertBoldToItal(ctx, src, idxNeg2); // 1st single letter word
		else if (idxNone != null) ConvertBoldToItal(ctx, src, idxNone); // 1st multi letter word
		else if	(idxNeg1 != null) ConvertBoldToItal(ctx, src, idxNeg1); // everything else

		// now recalc all cmds for stack
		dat.State_clear();
		for (int i = 0; i < tknsLen; i++) {
			Xop_apos_tkn apos = (Xop_apos_tkn)stack.FetchAt(i);
			dat.Ident(ctx, src, apos.Apos_tid(), apos.Src_end());	// NOTE: apos.Typ() must map to apos_len
			int newCmd = dat.Cmd();
			if (newCmd == apos.Apos_cmd()) continue;
			apos.Apos_cmd_(newCmd);
		}
	}
	private void ConvertBoldToItal(Xop_ctx ctx, byte[] src, Xop_apos_tkn oldTkn) {
		ctx.Msg_log().Add_itm_none(Xop_apos_log.Bold_converted_to_ital, src, oldTkn.Src_bgn(), oldTkn.Src_end());
		oldTkn.Apos_tid_(Xop_apos_tkn_.Typ_ital).Apos_cmd_(Xop_apos_tkn_.Cmd_i_bgn).Apos_lit_(oldTkn.Apos_lit() + 1);// NOTE: Cmd_i_bgn may be overridden later
	}
	private void Reset() {
		bold_count = ital_count = 0;
		dual_tkn = null;
		stack.Clear();
		dat.State_clear();
	}
	private static Xop_apos_tkn Previous_bgn(ListAdp stack, int typ) {
		int stack_len = stack.Count();
		for (int i = stack_len - 1; i > -1; --i) {
			Xop_apos_tkn apos = (Xop_apos_tkn)stack.FetchAt(i);
			int cmd = apos.Apos_cmd();
			switch (typ) {
				case Xop_apos_tkn_.Typ_ital:
					switch (cmd) {
						case Xop_apos_tkn_.Cmd_i_bgn:
						case Xop_apos_tkn_.Cmd_ib_bgn:
						case Xop_apos_tkn_.Cmd_bi_bgn:
						case Xop_apos_tkn_.Cmd_ib_end__i_bgn:
						case Xop_apos_tkn_.Cmd_b_end__i_bgn:
							return apos;
					}
					break;
				case Xop_apos_tkn_.Typ_bold:
					switch (cmd) {
						case Xop_apos_tkn_.Cmd_b_bgn:
						case Xop_apos_tkn_.Cmd_ib_bgn:
						case Xop_apos_tkn_.Cmd_bi_bgn:
						case Xop_apos_tkn_.Cmd_bi_end__b_bgn:
						case Xop_apos_tkn_.Cmd_i_end__b_bgn:
							return apos;
					}
					break;
				default:	// NOTE: this is approximate; will not be exact in most dual situations; EX: <b>a<i>b will return <i>; should return <b> and <i>
					switch (cmd) {
						case Xop_apos_tkn_.Cmd_b_bgn:
						case Xop_apos_tkn_.Cmd_i_bgn:
						case Xop_apos_tkn_.Cmd_ib_bgn:
						case Xop_apos_tkn_.Cmd_bi_bgn:
						case Xop_apos_tkn_.Cmd_bi_end__b_bgn:
						case Xop_apos_tkn_.Cmd_i_end__b_bgn:
						case Xop_apos_tkn_.Cmd_ib_end__i_bgn:
						case Xop_apos_tkn_.Cmd_b_end__i_bgn:
							return apos;
					}
					break;
			}
		}
		return null;
	}
}
