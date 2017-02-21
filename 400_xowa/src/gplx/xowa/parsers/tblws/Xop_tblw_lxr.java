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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.miscs.*;
public class Xop_tblw_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_tblw;}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int rv = Handle_bang(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Continue) return rv;
		rv = Handle_lnki(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Continue) return rv;
		return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, false, wlxr_type, Xop_tblw_wkr.Called_from_general, -1, -1);
	}
	public static final int Continue = -2;	// -2 b/c -1 used by Called_from_pre
	public static int Handle_bang(int wlxr_type, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		// standalone "!" should be ignored if no tblw present; EX: "a b! c" should not trigger ! for header
		switch (wlxr_type) {
			case Xop_tblw_wkr.Tblw_type_th:		// \n!
			case Xop_tblw_wkr.Tblw_type_th2:	// !!
			case Xop_tblw_wkr.Tblw_type_td:		// \n|
				Xop_tkn_itm owner_tblw_tb = ctx.Stack_get_typ(Xop_tkn_itm_.Tid_tblw_tb);		// check entire stack for tblw; DATE:2014-03-11
				if (	owner_tblw_tb == null													// no tblw in stack; highly probably that current sequence is not tblw tkn
					||	ctx.Cur_tkn_tid() == Xop_tkn_itm_.Tid_lnki								// cur tid is lnki; PAGE:en.w:Pink_(singer); DATE:2014-06-25
					) {	
					int lnki_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_lnki);
					if (lnki_pos != Xop_ctx.Stack_not_found && wlxr_type == Xop_tblw_wkr.Tblw_type_td) {// lnki present;// NOTE: added Xop_tblw_wkr.Tblw_type_td b/c th should not apply when tkn_mkr.Pipe() is called below; DATE:2013-04-24
						Xop_tkn_itm lnki_tkn = ctx.Stack_pop_til(root, src, lnki_pos, false, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_tblw_td);	// pop any intervening nodes until lnki
						ctx.Stack_add(lnki_tkn);												// push lnki back onto stack; TODO_OLD: combine these 2 lines into 1
						// NOTE: this is a "\n|" inside a [[ ]]; must create two tokens for lnki to build correctly;
						ctx.Subs_add(root, tkn_mkr.NewLine(bgn_pos, bgn_pos + 1, Xop_nl_tkn.Tid_char, 1));
						return Xop_pipe_lxr.Instance.Make_tkn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);	// NOTE: need to call pipe_lxr in order to invalidate if lnki; DATE:2014-06-06
					}
					else {	// \n| or \n! but no tbl
						if (	bgn_pos != Xop_parser_.Doc_bgn_bos		// avoid ! at BOS
							&&	src[bgn_pos] == Byte_ascii.Nl)		// handle "!" etc.
							return Xop_tblw_wkr.Handle_false_tblw_match(ctx, root, src, bgn_pos, cur_pos, tkn_mkr.Txt(bgn_pos + 1, cur_pos), true);	// +1 to ignore \n of "\n!", "\n!!", "\n|"; DATE:2014-02-19
						else											// handle "!!" only
							return ctx.Lxr_make_txt_(cur_pos);
					}
				}
				if (wlxr_type == Xop_tblw_wkr.Tblw_type_th2) {											// !!; extra check to make sure \n! exists; DATE:2014-10-19
					int prv_th_pos = Bry_find_.Find_bwd(src, Byte_ascii.Nl, bgn_pos);				// search for previous \n
					boolean invalid = prv_th_pos == Bry_find_.Not_found;									// no \n; invalid
					if (!invalid) {
						++prv_th_pos;																	// skip \n
						prv_th_pos = Bry_find_.Find_fwd_while_space_or_tab(src, prv_th_pos, src_len);	// skip \s; needed for "\n\s!" which is still a tblw
						if (prv_th_pos == bgn_pos)														// invalid: "\n" is directly in front of "!!"
							invalid = true;
						else
							invalid = src[prv_th_pos] != Byte_ascii.Bang;								// invalid if not "\n!"
					}
					if (invalid)
						return Xop_tblw_wkr.Handle_false_tblw_match(ctx, root, src, bgn_pos, cur_pos, tkn_mkr.Txt(bgn_pos, cur_pos), false);
				}
				break;
		}
		return Continue;
	}
	public static int Handle_lnki(int wlxr_type, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Xop_tkn_itm last_tkn = ctx.Stack_get_last();
		if (	last_tkn != null
			&&	last_tkn.Tkn_tid() == Xop_tkn_itm_.Tid_lnki) {
			Xop_lnki_tkn lnki = (Xop_lnki_tkn)last_tkn;
			if (	lnki.Pipe_count_is_zero()) {		// 1st pipe; EX: [[A\n|+B]]
				boolean invalidate = false;
				switch (wlxr_type) {					// tblw found; check if in lnki and validate ttl; DATE:2014-03-29
					case Xop_tblw_wkr.Tblw_type_tb:		// \n{|
					case Xop_tblw_wkr.Tblw_type_tc:		// \n|+
					case Xop_tblw_wkr.Tblw_type_tr:		// \n|-
					case Xop_tblw_wkr.Tblw_type_te:		// \n|}
						invalidate = true;				// always invalidate
						break;
					case Xop_tblw_wkr.Tblw_type_td2:	// ||; EX: [[A||B]]
						if (ctx.Tid_is_image_map()) {	// if in ImageMap, then treat "||" as "pipe" (not "pipe_text"); note that outer tbl is ignored; EX:w:United_States_presidential_election,_1992
							ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
							return cur_pos;
						}
						invalidate = !Xop_lnki_wkr_.Parse_ttl(ctx, src, lnki, bgn_pos);	// check if invalid; EX: "[[A<||]]" would be invalid b/c of <
						if (!invalidate) {	// "valid" title, but "||" must be converted to pipe inside lnki; EX:cs.w:Main_Page; DATE:2014-05-09
							ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));	// NOTE: technically need to check if pipe or pipe_text; for now, do pipe as pipe_text could break [[File:A.png||20px]]; DATE:2014-05-06
							return cur_pos;
						}
						break;
				}
				if (invalidate) {
					ctx.Stack_pop_last();
					return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
				}
			}
			else {										// nth pipe; no need to check for invalidate
				switch (wlxr_type) {
					case Xop_tblw_wkr.Tblw_type_td2:	// ||
						ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
						return cur_pos;
					case Xop_tblw_wkr.Tblw_type_th2:	// !!
					case Xop_tblw_wkr.Tblw_type_th:		// !
						ctx.Subs_add(root, tkn_mkr.Txt(bgn_pos, cur_pos));	// NOTE: cur_pos should handle ! and !!
						return cur_pos;
				}
			}
		}
		return Continue;
	}
	public Xop_tblw_lxr(byte wlxr_type) {this.wlxr_type = wlxr_type;} private byte wlxr_type;
	public static final    Xop_tblw_lxr Instance = new Xop_tblw_lxr(); Xop_tblw_lxr() {}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		core_trie.Add(Hook_tb,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_tb));
		core_trie.Add(Hook_te,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_te));
		core_trie.Add(Hook_tr,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_tr));
		core_trie.Add(Hook_td,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_td));
		core_trie.Add(Hook_th,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_th));
		core_trie.Add(Hook_tc,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_tc));
		core_trie.Add(Hook_td2,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_td2));
		core_trie.Add(Hook_th2,	new Xop_tblw_lxr(Xop_tblw_wkr.Tblw_type_th2));
	}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public static final    byte[] Hook_tb = Bry_.new_a7("\n{|"), Hook_te = Bry_.new_a7("\n|}"), Hook_tr = Bry_.new_a7("\n|-")
		, Hook_td = Bry_.new_a7("\n|"), Hook_th = Bry_.new_a7("\n!"), Hook_tc = Bry_.new_a7("\n|+")
		, Hook_td2 = Bry_.new_a7("||"), Hook_th2 = Bry_.new_a7("!!");
}
