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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.xndes.*;
public class Xop_list_wkr implements Xop_ctx_wkr {
	private int listId = 0; byte[] curSymAry = new byte[Max_list_depth]; int curSymLen = 0; byte[] prvSymAry = Bry_.Empty;
	private HierPosAryBldr posBldr = new HierPosAryBldr(Max_list_depth);
	private boolean SymAry_fill_overflow;
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {Reset(0);}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public boolean List_dirty() {return posBldr.Dirty();}
	public boolean Dd_chk() {return dd_chk;} public Xop_list_wkr Dd_chk_(boolean v) {dd_chk = v; return this;} private boolean dd_chk;
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		// NOTE: list_tkns can not be explicitly closed, so auto-close will happen for all items
		MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, (Xop_list_tkn)tkn, Bool_.Y_byte);
		Reset(listId + 1);
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__ul);
	}
	public int MakeTkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {// REF.MW: Parser|doBlockLevels
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) bgn_pos = 0;	// do not allow -1 pos

		// pop hdr if exists; EX: \n== a ==\n*b; \n* needs to close hdr
		int acsPos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_hdr);
		if (acsPos != -1) ctx.Stack_pop_til(root, src, acsPos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_list);

		// close apos
		ctx.Apos().End_frame(ctx, root, src, bgn_pos, false);
		byte symByt = src[cur_pos - 1];  // -1 b/c symByt is byte before curByt; EX: \n*a; cur_pos is at a; want to get *
		int prvSymLen = curSymLen;
		cur_pos = SymAry_fill(src, cur_pos, src_len, symByt);
		symByt = src[cur_pos - 1];	// NOTE: get symByt again b/c cur_pos may have changed; EX: "#*"; # may have triggered list, but last symByt should be *
		if (SymAry_fill_overflow) return ctx.Lxr_make_txt_(cur_pos);
		PrvItm_compare();
		ctx.Para().Process_block__bgn__nl_w_symbol(ctx, root, src, bgn_pos, cur_pos - 1, Xop_xnde_tag_.Tag__li);	// -1 b/c cur_pos includes sym_byte; EX: \n*; pass li; should pass correct tag, but for purposes of para_wkr, <li> doesn't matter
		if	(prvSymMatch) {
			PopTil(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Bool_.N_byte);
			posBldr.MoveNext();
			prvSymAry = Xop_list_wkr_.MakeSymAry(curSymAry, curSymLen);
			Xop_list_tkn prvItm = tkn_mkr.List_bgn(bgn_pos, cur_pos, curSymAry[curSymLen - 1], curSymLen).List_path_(posBldr.XtoIntAry()).List_uid_(listId);
			ctx.Subs_add_and_stack(root, prvItm);
			ctx.Empty_ignored_y_();
		}
		else {
			for (int i = prvSymLen; i > commonSymLen; i--) {	// close all discontinued itms: EX: ##\n#\n
				PopTil(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Bool_.Y_byte);
				posBldr.MoveUp();
			}
			if (commonSymLen == 0 && prvSymLen != 0) {	// nothing in common; reset list
				listId++;
				posBldr.Init();
			}
			if (curSymLen == commonSymLen) {					// add another itm if continuing; EX: #\n#\n
				PopTil(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Bool_.N_byte);
				if ((prvSymLen - curSymLen) > 0		// moving up many levels; do not open new list; just MoveNext; EX: #1\n###3\n##2
					&& curSymLen != 1) {			// do not moveNext if at level 1; this has to do with strange incrementing logic in posBldr at rootLvl
					posBldr.MoveNext();
				}
				else {
					posBldr.MoveUp(); posBldr.MoveDown();
				}
				prvSymAry = Xop_list_wkr_.MakeSymAry(curSymAry, curSymLen);
				symByt = src[cur_pos - 1];
				Xop_list_tkn prvItm = tkn_mkr.List_bgn(bgn_pos, cur_pos, symByt, curSymLen).List_path_(posBldr.XtoIntAry()).List_uid_(listId);
				ctx.Subs_add_and_stack(root, prvItm);
				ctx.Empty_ignored_y_();
			}
			for (int i = commonSymLen; i < curSymLen; i++) {	// open new itms; EX: #\n##\n
				posBldr.MoveDown();
				symByt = curSymAry[i];
				prvSymAry = Xop_list_wkr_.MakeSymAry(curSymAry, curSymLen);
				Xop_list_tkn prvItm = tkn_mkr.List_bgn(bgn_pos, cur_pos, symByt, i + List_adp_.Base1).List_path_(posBldr.XtoIntAry()).List_uid_(listId);
				ctx.Subs_add_and_stack(root, prvItm);
				ctx.Empty_ignored_y_();
			}
		}
		if (allDd) { // NOTE: if indent && next == {| then invoke table; EX: ":::{|"
			int tblw_bgn = Bry_find_.Find_fwd_while(src, cur_pos, src_len, Byte_ascii.Space);  // skip spaces; EX: ": {|" DATE:2017-01-26
			if (tblw_bgn < src_len - 2 && src[tblw_bgn] == '{' && src[tblw_bgn + 1] == '|')	   // check if next chars are "{|"
				return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, tblw_bgn, tblw_bgn+ 2, false, Xop_tblw_wkr.Tblw_type_tb, Xop_tblw_wkr.Called_from_list, -1, -1);	// NOTE: ws_enabled must be set to true; see test for Adinkras; Cato the Elder
		}
		dd_chk = symByt == Xop_list_tkn_.List_itmTyp_dt;
		return cur_pos;
	}
	public void MakeTkn_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_list_tkn bgn, byte sub_last) {
		// boolean empty_ignored = ctx.Empty_ignored(); // commented; see below; DATE:2014-06-24
		Xop_tkn_itm end_tkn = tkn_mkr.List_end(bgn_pos, bgn.List_itmTyp()).List_path_(bgn.List_path()).List_uid_(listId).List_sub_last_(sub_last);
		ctx.Subs_add(root, end_tkn);
		// if (empty_ignored) ctx.Empty_ignore(root, bgn.Tkn_sub_idx());	// commented; code was incorrectly deactivating "*a" when "<li>" encountered; PAGE:en.w:Bristol_Bullfinch DATE:2014-06-24
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__ul);
	}
	private Xop_list_tkn PopTil(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, byte subLast) {
		int acs_pos = ctx.Stack_idx_find_but_stop_at_tbl(Xop_tkn_itm_.Tid_list);
		if (acs_pos == -1) return null;
		Xop_list_tkn rv = (Xop_list_tkn)ctx.Stack_pop_til(root, src, acs_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_list);
		MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, rv, subLast);
		return rv;
	}
	private void PrvItm_compare() {
		int prvSymLen = prvSymAry.length;
		prvSymMatch = curSymLen == prvSymLen; commonSymLen = 0;
		for (int i = 0; i < curSymLen; i++) {
			if (i < prvSymLen && (Xop_list_wkr_.Compare_normalize(curSymAry[i]) == Xop_list_wkr_.Compare_normalize(prvSymAry[i]))) {
				commonSymLen = i + 1;
			}
			else {
				prvSymMatch = false;
				break;
			}
		}
	}	boolean prvSymMatch; int commonSymLen = 0; boolean allDd = false;
	private int SymAry_fill(byte[] src, int cur_pos, int src_len, byte curByt) {
		curSymLen = 0;
		curSymAry[curSymLen++] = curByt;
		allDd = true;
		boolean loop = true;
		SymAry_fill_overflow = false;
		while (loop) {
			if (cur_pos == src_len) break;
			if (curSymLen == Max_list_depth) {	// WORKAROUND: xowa imposes max list depth of 256; MW is unlimited; may change for future release but 256 should accomodate all real-world usages
				boolean stop = false;
				for (int i = cur_pos; i < src_len; i++) {
					curByt = src[i];
					switch (curByt) {
						case Byte_ascii.Star:
						case Byte_ascii.Hash:
						case Byte_ascii.Semic:
						case Byte_ascii.Colon:
							cur_pos = i;
							break;
						default:
							stop = true;
							break;
					}
					if (stop) break;
				}
				for (int i = 0; i < Max_list_depth; i++)
					curSymAry[i] = Byte_ascii.Null;
				curSymLen = 0;
				SymAry_fill_overflow = true;
				return cur_pos;
			}
			curByt = src[cur_pos];
			switch (curByt) {
				case Byte_ascii.Star:
				case Byte_ascii.Hash:
				case Byte_ascii.Semic:
					curSymAry[curSymLen++] = curByt;
					cur_pos++;
					allDd = false;
					break;
				case Byte_ascii.Colon:
					curSymAry[curSymLen++] = curByt;
					cur_pos++;
					break;
				default:
					loop = false;
					break;
			}
		}
		return cur_pos;
	}
	private void Reset(int newListId) {
		posBldr.Init();
		curSymLen = 0;
		prvSymAry = Bry_.Empty;
		dd_chk = false;
		listId = newListId;
	}
	public static final int Max_list_depth = 256;
}
