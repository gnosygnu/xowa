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
import gplx.core.btries.*;
import gplx.xowa.html.tocs.*;
class Xop_under_lxr implements Xop_lxr {
	private Btrie_mgr words_trie_ci, words_trie_cs;
	public byte Lxr_tid() {return Xop_lxr_.Tid_under;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {
		Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
		int under_kwds_len = under_kwds.length;
		Xop_under_lxr lxr = new Xop_under_lxr();
		lxr.words_trie_cs = Btrie_slim_mgr.cs_();
		lxr.words_trie_ci = Btrie_utf8_mgr.new_(lang.Case_mgr());
		core_trie.Add(Xop_under_hook.Key_std, lxr);
		boolean hook_alt_null = true;
		for (int i = 0; i < under_kwds_len; i++) {
			int kwd_id = under_kwds[i];
			Xol_kwd_grp kwd_grp = kwd_mgr.Get_or_new(kwd_id);
			Xol_kwd_itm[] kwd_itms = kwd_grp.Itms(); if (kwd_itms == null) continue;
			int kwd_itms_len = kwd_itms.length;
			boolean kwd_case_match = kwd_grp.Case_match();
			Btrie_mgr words_trie = kwd_grp.Case_match() ? lxr.words_trie_cs : lxr.words_trie_ci;
			for (int j = 0; j < kwd_itms_len; j++) {
				Xol_kwd_itm kwd_itm = kwd_itms[j];
				byte[] kwd_bry = kwd_itm.Val();
				int kwd_len = kwd_bry.length;
				Object hook_obj = Hook_trie.Match_bgn(kwd_bry, 0, kwd_len);
				if (hook_obj != null) {
					Xop_under_hook hook = (Xop_under_hook)hook_obj;
					byte[] word_bry = Bry_.Mid(kwd_bry, hook.Key_len(), kwd_bry.length);
					words_trie.Add_obj(word_bry, new Xop_under_word(kwd_id, word_bry));
					if (hook_alt_null && hook.Tid() == Xop_under_hook.Tid_alt) {
						core_trie.Add(Xop_under_hook.Key_alt, lxr);
						hook_alt_null = false;
					}
				}
				else {									// kwd doesn't start with __; no known examples, but just in case; EX: "NOTOC"; DATE:2014-02-14
					Xop_word_lxr word_lxr = new Xop_word_lxr(kwd_id);
					if (kwd_case_match)					// cs; add word directly to trie
						core_trie.Add(kwd_bry, word_lxr);
					else {								// NOTE: next part is imprecise; XOWA parser is cs, but kwd is ci; for now, just add all upper and all lower
						lang.App().Usr_dlg().Warn_many("", "", "under keyword does not start with __; id=~{0} key=~{1} word=~{2}", kwd_id, String_.new_utf8_(kwd_grp.Key()), String_.new_utf8_(kwd_bry));
						core_trie.Add(lang.Case_mgr().Case_build_lower(kwd_bry), word_lxr);
						core_trie.Add(lang.Case_mgr().Case_build_upper(kwd_bry), word_lxr);	
					}
				}
			}
		}
	}
	private static final int[] under_kwds = new int[] // REF.MW:MagicWord.php
	{ Xol_kwd_grp_.Id_toc, Xol_kwd_grp_.Id_notoc, Xol_kwd_grp_.Id_forcetoc
	, Xol_kwd_grp_.Id_nogallery, Xol_kwd_grp_.Id_noheader, Xol_kwd_grp_.Id_noeditsection
	, Xol_kwd_grp_.Id_notitleconvert, Xol_kwd_grp_.Id_nocontentconvert, Xol_kwd_grp_.Id_newsectionlink, Xol_kwd_grp_.Id_nonewsectionlink
	, Xol_kwd_grp_.Id_hiddencat, Xol_kwd_grp_.Id_index, Xol_kwd_grp_.Id_noindex, Xol_kwd_grp_.Id_staticredirect
	, Xol_kwd_grp_.Id_disambig
	};
	private static final Btrie_fast_mgr Hook_trie = Btrie_fast_mgr.cs_()
	.Add(Xop_under_hook.Key_std, Xop_under_hook.Itm_std)
	.Add(Xop_under_hook.Key_alt, Xop_under_hook.Itm_alt)
	;
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (cur_pos == src_len) return ctx.Lxr_make_txt_(cur_pos);				// eos
		int rv = cur_pos;
		Object word_obj = words_trie_cs.Match_bgn(src, cur_pos, src_len);		// check cs
		if (word_obj == null) {
			word_obj = words_trie_ci.Match_bgn(src, cur_pos, src_len);			// check ci
			if (word_obj == null)
				return ctx.Lxr_make_txt_(cur_pos);								// kwd not found; EX: "TOCA__"
			else
				rv = words_trie_ci.Match_pos();
		}
		else
			rv = words_trie_cs.Match_pos();
		Xop_under_word word_itm = (Xop_under_word)word_obj;
		Xop_under_lxr.Make_tkn(ctx, tkn_mkr, root, src, src_len, bgn_pos, rv, word_itm.Kwd_id());
		return rv;
	}
	public static void Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int kwd_id) {
		Xoa_page page = ctx.Cur_page();
		Xow_hdr_mgr hdr_mgr = page.Hdr_mgr();
		switch (kwd_id) {
			case Xol_kwd_grp_.Id_toc:
				hdr_mgr.Toc_manual_();
				ctx.Para().Process_block_lnki_div();							// NOTE: __TOC__ will manually place <div toc> here; simulate div in order to close any pres; EX:\n\s__TOC__; PAGE:de.w:  DATE:2014-07-05
				ctx.Subs_add(root, tkn_mkr.Under(bgn_pos, cur_pos, kwd_id));	// NOTE: only save under_tkn for TOC (b/c its position is needed for insertion); DATE:2013-07-01
				break;	
			case Xol_kwd_grp_.Id_forcetoc:			hdr_mgr.Toc_force_(); break;
			case Xol_kwd_grp_.Id_notoc:				hdr_mgr.Toc_hide_(); break;
			case Xol_kwd_grp_.Id_noeditsection:		break;	// ignore; not handling edit sections
			case Xol_kwd_grp_.Id_nocontentconvert:	page.Lang_convert_content_(false); break;
			case Xol_kwd_grp_.Id_notitleconvert:	page.Lang_convert_title_(false); break;
			default:								break;	// ignore anything else
		}				
	}
	public static final Xop_under_lxr _ = new Xop_under_lxr(); Xop_under_lxr() {}
}
class Xop_word_lxr implements Xop_lxr {
	private int kwd_id;
	public Xop_word_lxr(int kwd_id) {this.kwd_id = kwd_id;}
	public byte Lxr_tid() {return Xop_lxr_.Tid_word;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Xop_under_lxr.Make_tkn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, kwd_id);	// for now, all word_lxrs only call the under_lxr; DATE:2014-02-14
		return cur_pos;
	}
}
class Xop_under_hook {
	Xop_under_hook(byte tid, byte[] key) {this.tid = tid; this.key = key; this.key_len = key.length;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Key() {return key;} private byte[] key;
	public int Key_len() {return key_len;} private int key_len;
	public static final byte Tid_std = 1, Tid_alt = 2;
	public static final byte[] Key_std = new byte[] {Byte_ascii.Underline, Byte_ascii.Underline}, Key_alt = Bry_.new_utf8_("＿＿");	// ja wikis
	public static final Xop_under_hook
	  Itm_std = new Xop_under_hook(Tid_std, Key_std)
	, Itm_alt = new Xop_under_hook(Tid_alt, Key_alt)
	;
}
class Xop_under_word {
	public Xop_under_word(int kwd_id, byte[] word_bry) {
		this.kwd_id = kwd_id;
		this.word_bry = word_bry;
		this.word_len = word_bry.length;
	}
	public int Kwd_id() {return kwd_id;} private int kwd_id;
	public byte[] Word_bry() {return word_bry;} private byte[] word_bry;
	public int Word_len() {return word_len;} private int word_len;
}
