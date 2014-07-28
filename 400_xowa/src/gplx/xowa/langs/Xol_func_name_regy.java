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
import gplx.core.btries.*; import gplx.intl.*; import gplx.xowa.xtns.pfuncs.*;
public class Xol_func_name_regy {
	private Xol_func_name_itm finder = new Xol_func_name_itm();
	private Btrie_slim_mgr cs_trie = Btrie_slim_mgr.cs_(), ci_trie = Btrie_slim_mgr.ci_utf_8_();
	public Xol_func_name_regy(Xol_lang lang) {this.lang = lang;} private Xol_lang lang;
	public void Evt_lang_changed(Xol_lang lang) {
		Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
		ci_trie.Clear(); cs_trie.Clear();
		int len = Pf_func_.Ary__.length;
		for (int i = 0; i < len; i++) {
			int id = Pf_func_.Ary__[i];
			Xol_kwd_grp list = kwd_mgr.Get_at(id);
			if (list == null) {
				if (Env_.Mode_testing())
					continue;		// TEST: allows partial parsing of $magicWords
				else
					 list = lang.App().Lang_mgr().Lang_en().Kwd_mgr().Get_at(id);	// get from fallback language; TODO: allow other fallback langs besides "English"
			}
			Reg_defn(kwd_mgr, id, Pf_func_.Get_prototype(id));
		}
	}
	public void Reg_defn(Xol_kwd_mgr kwd_mgr, int id, Xot_defn defn) {
		Xol_kwd_grp grp = kwd_mgr.Get_at(id); if (grp == null) return;
		Xol_kwd_itm[] itms = grp.Itms(); if (itms == null) return;
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; i++) {
			byte[] name = itms[i].Val();
			this.Add(name, grp.Case_match(), defn.Clone(id, name));
		}
	}
	private void Add(byte[] ary, boolean case_match, Xot_defn func) {
		if (case_match)
			cs_trie.Add_obj(ary, func);
		else {
			byte[] lower_ary = lang.Case_mgr().Case_build_lower(ary, 0, ary.length);
			ci_trie.Add_obj(lower_ary, func);
		}
	}
	public Xol_func_name_itm Find_defn(byte[] src, int txt_bgn, int txt_end) {
		finder.Clear();
		for (int i = 0; i < 2; i++) {
			if (txt_bgn == txt_end) return finder;	// NOTE: true when tmpl_name is either not loaded, or doesn't exist
			Xot_defn func = Match_bgn(src, txt_bgn, txt_end);
			if (func == null) return finder;		// NOTE: null when tmpl_name is either not loaded, or doesn't exist
			byte[] func_name = func.Name();
			int match_pos = func_name.length + txt_bgn;
			byte defn_tid = func.Defn_tid();
			switch (defn_tid) {
				case Xot_defn_.Tid_func:
					if		(match_pos == txt_end)						// next char is ws (b/c match_pos == txt_end)
						finder.Func_set(func, -1);
					else if (src[match_pos] == Pf_func_.Name_dlm)		// next char is :
						finder.Func_set(func, match_pos);
					else {											// func is close, but not quite: ex: #ifx: or padlefts:
						return finder;
					}
					break;
				case Xot_defn_.Tid_safesubst:
				case Xot_defn_.Tid_subst:
					finder.Subst_set_(defn_tid, txt_bgn, match_pos);
					if (match_pos < txt_end) txt_bgn = Bry_finder.Find_fwd_while_not_ws(src, match_pos, txt_end);
					break;
				case Xot_defn_.Tid_raw:
				case Xot_defn_.Tid_msg:
				case Xot_defn_.Tid_msgnw:
					finder.Subst_set_(defn_tid, txt_bgn, match_pos);
					if (match_pos + 1 < txt_end)	// +1 to include ":" (keyword id "raw", not "raw:")
						txt_bgn = Bry_finder.Find_fwd_while_not_ws(src, match_pos + 1, txt_end);
					break;
				default: return finder;
			}
		}
		return finder;
	}
	private Xot_defn Match_bgn(byte[] src, int bgn, int end) {
		Object cs_obj = cs_trie.Match_bgn(src, bgn, end);
		Xot_defn rv = null;
		if (cs_obj != null) {					// match found for cs; could be false_match; EX: NAME"+"SPACE and NAME"+"SPACENUMBER
			rv = (Xot_defn)cs_obj;
			if (rv.Name().length == end - bgn)	// func_name matchese cur_name; DATE:2013-04-15
				return rv;
			// else {}							// func_name doesn't match cur_name; continue below; EX: NAME"+"SPACENUMBER passed in and matches NAME"+"SPACE (which is cs); note that NAME"+"SPACENUMBER only exists in ci
		}
		LowerAry(src, bgn, end);
		byte[] ary = lang.Case_mgr().Case_build_lower(lower_ary, 0, end - bgn);
		Xot_defn rv_alt = (Xot_defn)ci_trie.Match_bgn(ary, 0, end - bgn);
		return (rv != null && rv_alt == null) 
			? rv		// name not found in ci, but name was found in cs; return cs; handles NAME"+"SPACENUMBER
			: rv_alt;	// else return rv_alt
	}
	private void LowerAry(byte[] src, int bgn, int end) {
		int len = end - bgn;
		if (len > lower_ary_len) {lower_ary = new byte[len]; lower_ary_len = len;}
		lower_ary_len = len;
		Array_.CopyTo(src, bgn, lower_ary, 0, len);
	}	byte[] lower_ary = new byte[255]; int lower_ary_len = 255;
}
