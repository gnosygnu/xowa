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
package gplx.xowa; import gplx.*;
public class Xop_list_wkr implements Xop_ctx_wkr {
	private int listId = 0; byte[] curSymAry = new byte[Max_list_depth]; int curSymLen = 0; byte[] prvSymAry = Bry_.Empty;
	private HierPosAryBldr posBldr = new HierPosAryBldr(Max_list_depth);
	private boolean SymAry_fill_overflow;
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {Reset(0);}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public boolean List_dirty() {return posBldr.Dirty();}
	private void Dd_colon_hide(Xop_root_tkn root) {	// : seen, but nl encountered; mark ":" as invisible (i.e.: consume for <dd>; don't let it show as ":"); DATE:2013-11-07
		int subs_len = root.Subs_len();
		for (int i = subs_len - 1; i > -1; i--) {
			Xop_tkn_itm colon_tkn = root.Subs_get(i);
			if (colon_tkn.Tkn_tid() == Xop_tkn_itm_.Tid_colon) {
				colon_tkn.Ignore_y_();
				break;
			}
		}
	}
	public boolean Dd_chk() {return dd_chk;} public Xop_list_wkr Dd_chk_(boolean v) {dd_chk = v; return this;} private boolean dd_chk;
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {
		if (dd_chk) Dd_colon_hide(root);
		// NOTE: list_tkns can not be explicitly closed, so auto-close will happen for all items
		MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, (Xop_list_tkn)tkn, Bool_.Y_byte);
		Reset(listId + 1);
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag_ul);
	}
	public int MakeTkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {// REF.MW: Parser|doBlockLevels
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) bgn_pos = 0;	// do not allow -1 pos
		if (dd_chk) Dd_colon_hide(root);

		// pop hdr if exists; EX: \n== a ==\n*b; \n* needs to close hdr
		int acsPos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_hdr);
		if (acsPos != -1) ctx.Stack_pop_til(root, src, acsPos, true, bgn_pos, cur_pos);

		// close apos
		ctx.Apos().EndFrame(ctx, root, src, bgn_pos, false);
		byte symByt = src[cur_pos - 1];  // -1 b/c symByt is byte before curByt; EX: \n*a; cur_pos is at a; want to get *
		int prvSymLen = curSymLen;
		cur_pos = SymAry_fill(src, cur_pos, src_len, symByt);
		symByt = src[cur_pos - 1];	// NOTE: get symByt again b/c cur_pos may have changed; EX: "#*"; # may have triggered list, but last symByt should be *
		if (SymAry_fill_overflow) return ctx.Lxr_make_txt_(cur_pos);
		int trim_line_end = Trim_empty_item(src, src_len, cur_pos);
		if (trim_line_end != Bry_.NotFound) {
			curSymLen = prvSymLen;
			ctx.Tkn_mkr().Ignore(bgn_pos, trim_line_end, Xop_ignore_tkn.Ignore_tid_empty_li);
			return trim_line_end; 
		}
		PrvItm_compare();
		ctx.Para().Process_block__bgn__nl_w_symbol(ctx, root, src, bgn_pos, cur_pos - 1, Xop_xnde_tag_.Tag_li);	// -1 b/c cur_pos includes sym_byte; EX: \n*; pass li; should pass correct tag, but for purposes of para_wkr, <li> doesn't matter
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
				Xop_list_tkn prvItm = tkn_mkr.List_bgn(bgn_pos, cur_pos, symByt, i + ListAdp_.Base1).List_path_(posBldr.XtoIntAry()).List_uid_(listId);
				ctx.Subs_add_and_stack(root, prvItm);
				ctx.Empty_ignored_y_();
			}
		}
		if (allDd && cur_pos < src_len - 2 && src[cur_pos] == '{' && src[cur_pos + 1] == '|') // NOTE: if indent && next == {| then invoke table; EX: ":::{|"
			return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, cur_pos, cur_pos + 2, false, Xop_tblw_wkr.Tblw_type_tb, Xop_tblw_wkr.Called_from_list, -1, -1);	// NOTE: ws_enabled must be set to true; see test for Adinkras; Cato the Elder
		else {
			dd_chk = symByt == Xop_list_tkn_.List_itmTyp_dt;
			return cur_pos;
		}
	}
	public void MakeTkn_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_list_tkn bgn, byte sub_last) {
		// boolean empty_ignored = ctx.Empty_ignored(); // commented; see below; DATE:2014-06-24
		Xop_tkn_itm end_tkn = tkn_mkr.List_end(bgn_pos, bgn.List_itmTyp()).List_path_(bgn.List_path()).List_uid_(listId).List_sub_last_(sub_last);
		ctx.Subs_add(root, end_tkn);
		// if (empty_ignored) ctx.Empty_ignore(root, bgn.Tkn_sub_idx());	// commented; code was incorrectly deactivating "*a" when "<li>" encountered; PAGE:en.w:Bristol_Bullfinch DATE:2014-06-24
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag_ul);
	}
	private int Trim_empty_item(byte[] src, int src_len, int pos) {
		while (pos < src_len) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Tab: case Byte_ascii.CarriageReturn: case Byte_ascii.Space:
					++pos;
					break;
				case Byte_ascii.NewLine:
					return pos;
				default:
					return Bry_.NotFound;
			} 		
		}
		return Bry_.NotFound;
	}
	private Xop_list_tkn PopTil(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, byte subLast) {
		int acs_pos = ctx.Stack_idx_find_but_stop_at_tbl(Xop_tkn_itm_.Tid_list);
		if (acs_pos == -1) return null;
		Xop_list_tkn rv = (Xop_list_tkn)ctx.Stack_pop_til(root, src, acs_pos, false, bgn_pos, cur_pos);
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
						case Byte_ascii.Asterisk:
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
					curSymAry[i] = Byte_ascii.Nil;
				curSymLen = 0;
				SymAry_fill_overflow = true;
				return cur_pos;
			}
			curByt = src[cur_pos];
			switch (curByt) {
				case Byte_ascii.Asterisk:
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
class Xop_list_wkr_ {
	public static byte[] MakeSymAry(byte[] curSymAry, int curSymLen) {
		byte[] rv = new byte[curSymLen];
		for (int i = 0; i < curSymLen; i++)
			rv[i] = curSymAry[i];
		return rv;
	}
	public static byte Compare_normalize(byte b) {	// convert : to ; for sake of determining levels; EX: ";:" is actually same group
		switch (b) {
			case Byte_ascii.Asterisk:
			case Byte_ascii.Hash:
			case Byte_ascii.Semic:		return b;
			case Byte_ascii.Colon:		return Byte_ascii.Semic;
			default:					throw Err_.unhandled(b);
		}
	}
	public static void Close_list_if_present(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int bgn_pos, int cur_pos) {
		if (ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_tmpl_invk) != Xop_ctx.Stack_not_found) return; // list is inside template; do not close;
		while (true) {	// close all list tkns on stack; EX: *** n should close all 3 stars; used to only close 1
			int acs_pos = -1, acs_len = ctx.Stack_len();
			for (int i = acs_len - 1; i > -1; i--) {						// find auto-close pos
				byte cur_acs_tid = ctx.Stack_get(i).Tkn_tid();
				switch (cur_acs_tid) {
					case Xop_tkn_itm_.Tid_tblw_tb:							// do not bypass tbl_elem; EX: ": {| |- *a |b }" should not close ":"
					case Xop_tkn_itm_.Tid_tblw_tc:
					case Xop_tkn_itm_.Tid_tblw_te:
					case Xop_tkn_itm_.Tid_tblw_td:
					case Xop_tkn_itm_.Tid_tblw_th:
					case Xop_tkn_itm_.Tid_tblw_tr:
						i = -1; // force break;
						break;
					case Xop_tkn_itm_.Tid_list:
						acs_pos = i;
						break;
					default:
						break;
				}
			}
//				int acs_idx = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_list);
			if (acs_pos == Xop_ctx.Stack_not_found) break;	// no more list tokens found
			ctx.Stack_pop_til(root, src, acs_pos, true, bgn_pos, cur_pos);
		}
	}
}
