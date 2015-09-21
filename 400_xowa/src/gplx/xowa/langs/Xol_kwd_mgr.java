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
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
public class Xol_kwd_mgr implements GfoInvkAble {
	public Xol_kwd_mgr(Xol_lang lang) {this.lang = lang;} private Xol_lang lang; Xol_kwd_grp[] grps = new Xol_kwd_grp[Xol_kwd_grp_.Id__max];
	public int Len() {return grps.length;}
	public void Clear() {
		int len = grps.length;
		for (int i = 0; i < len; ++i)
			grps[i] = null;
	}
	public Btrie_slim_mgr Trie_raw() {if (trie_raw == null) trie_raw = Xol_kwd_mgr.trie_(this, Xol_kwd_grp_.Id_str_rawsuffix); return trie_raw;} private Btrie_slim_mgr trie_raw;
	public Btrie_slim_mgr Trie_nosep() {if (trie_nosep == null) trie_nosep = Xol_kwd_mgr.trie_(this, Xol_kwd_grp_.Id_nocommafysuffix); return trie_nosep;} private Btrie_slim_mgr trie_nosep;
	public void Kwd_default_match_reset() {kwd_default_init_needed = true;}	// TEST:
	public boolean Kwd_default_match(byte[] match) {	// handle multiple #default keywords; DATE:2014-07-28
		if (match == null) return false;			// null never matches #default
		int match_len = match.length;
		if (match_len == 0) return false;			// "" never matches default
		if (kwd_default_init_needed) {
			kwd_default_init_needed = false;
			Xol_kwd_grp grp = this.Get_at(Xol_kwd_grp_.Id_xtn_default);
			int len = grp.Itms().length;
			if (len == 1) 
				kwd_default_key = grp.Itms()[0].Val();
			else {
				kwd_default_trie = Btrie_slim_mgr.new_(grp.Case_match());
				for (int i = 0; i < len; i++) {
					Xol_kwd_itm itm = grp.Itms()[i];
					kwd_default_trie.Add_obj(itm.Val(), itm);
				}
			}
		}
		return kwd_default_trie == null
			? Bry_.Has_at_bgn(match, kwd_default_key, 0, match_len)
			: kwd_default_trie.Match_bgn(match, 0, match_len) != null
			;
	}
	private Btrie_slim_mgr kwd_default_trie; private byte[] kwd_default_key; private boolean kwd_default_init_needed = true;
	public Xol_kwd_grp Get_at(int id) {return grps[id];}
	public Xol_kwd_grp Get_or_new(int id) {
		Xol_kwd_grp rv = grps[id];
		if (rv == null) {
			rv = new Xol_kwd_grp(Xol_kwd_grp_.Bry_by_id(id));
			grps[id] = rv;
		}
		return rv;
	}
	public Xol_kwd_grp New(boolean case_match, int id, String... words_str) {
		Xol_kwd_grp rv = Get_or_new(id);
		rv.Srl_load(case_match, Bry_.Ary(words_str));
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lang))					return lang;
		else if	(ctx.Match(k, Invk_load_text))				Xol_lang_srl.Load_keywords(this, m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_lang = Xol_lang_srl.Invk_lang, Invk_load_text = Xol_lang_srl.Invk_load_text;
	public static Btrie_slim_mgr trie_(Xol_kwd_mgr mgr, int id) {
		Xol_kwd_grp grp = mgr.Get_at(id);
		Btrie_slim_mgr rv = Btrie_slim_mgr.new_(grp.Case_match());
		int len = grp.Itms().length;
		for (int i = 0; i < len; i++) {
			Xol_kwd_itm itm = grp.Itms()[i];
			rv.Add_obj(itm.Val(), itm);
		}
		return rv;
	}
	public static Hash_adp_bry hash_(Xol_kwd_mgr mgr, int id) {
		Xol_kwd_grp grp = mgr.Get_at(id);
		Hash_adp_bry rv = Hash_adp_bry.c__u8(grp.Case_match(), mgr.lang.Case_mgr());
		int len = grp.Itms().length;
		for (int i = 0; i < len; i++) {
			Xol_kwd_itm itm = grp.Itms()[i];
			rv.Add(itm.Val(), itm);
		}
		return rv;
	}
}
