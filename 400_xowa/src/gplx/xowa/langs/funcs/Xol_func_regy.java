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
package gplx.xowa.langs.funcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*; import gplx.core.intls.*; import gplx.core.envs.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.langs.kwds.*;
public class Xol_func_regy {
	private final    Xoa_lang_mgr lang_mgr; private final    Xol_lang_itm lang;
	private final    Btrie_slim_mgr cs_trie = Btrie_slim_mgr.cs(), ci_trie = Btrie_slim_mgr.ci_u8();
	public Xol_func_regy(Xoa_lang_mgr lang_mgr, Xol_lang_itm lang) {this.lang_mgr = lang_mgr; this.lang = lang;}
	public void Evt_lang_changed(Xol_lang_itm lang) {
		Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
		ci_trie.Clear(); cs_trie.Clear();
		int[] kwd_ary = Pf_func_.Ary_get(null, !lang.Kwd_mgr__strx());
		int len = kwd_ary.length;
		for (int i = 0; i < len; i++) {
			int id = kwd_ary[i];
			Xol_kwd_grp list = kwd_mgr.Get_at(id);
			if (list == null) {
				if (Env_.Mode_testing())
					continue;		// TEST: allows partial parsing of $magicWords
				else
					 list = lang_mgr.Lang_en().Kwd_mgr().Get_at(id);	// get from fallback language; TODO_OLD: allow other fallback langs besides "English"
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
	public void Find_defn(Xol_func_itm rv, byte[] src, int txt_bgn, int txt_end) {
		rv.Clear();
		for (int i = 0; i < 2; i++) {
			if (txt_bgn == txt_end) return;	// NOTE: true when tmpl_name is either not loaded, or doesn't exist
			Xot_defn func = Match_bgn(src, txt_bgn, txt_end);
			if (func == null) return;		// NOTE: null when tmpl_name is either not loaded, or doesn't exist
			byte[] func_name = func.Name();
			int match_pos = func_name.length + txt_bgn;
			byte defn_tid = func.Defn_tid();
			switch (defn_tid) {
				case Xot_defn_.Tid_func:
					if		(match_pos == txt_end)						// next char is ws (b/c match_pos == txt_end)
						rv.Func_(func, -1);
					else if (src[match_pos] == Pf_func_.Name_dlm)		// next char is :
						rv.Func_(func, match_pos);
					else {												// func is close, but not quite: ex: #ifx: or padlefts:
						return;
					}
					break;
				case Xot_defn_.Tid_safesubst:
				case Xot_defn_.Tid_subst:
					rv.Init_by_subst(defn_tid, txt_bgn, match_pos);
					if (match_pos < txt_end) txt_bgn = Bry_find_.Find_fwd_while_not_ws(src, match_pos, txt_end);
					break;
				case Xot_defn_.Tid_raw:
				case Xot_defn_.Tid_msg:
				case Xot_defn_.Tid_msgnw:
					rv.Init_by_subst(defn_tid, txt_bgn, match_pos);
					if (match_pos + 1 < txt_end)	// +1 to include ":" (keyword id "raw", not "raw:")
						txt_bgn = Bry_find_.Find_fwd_while_not_ws(src, match_pos + 1, txt_end);
					break;
				default: return;
			}
		}
		return;
	}
	private Xot_defn Match_bgn(byte[] src, int bgn, int end) {
		Object cs_obj = cs_trie.Match_bgn(src, bgn, end);
		Xot_defn rv = null;
		if (cs_obj != null) {					// match found for cs; could be false_match; EX: NAME"+"SPACE and NAME"+"SPACENUMBER
			rv = (Xot_defn)cs_obj;
			if (rv.Name().length == end - bgn)	// func_name matches cur_name; DATE:2013-04-15
				return rv;
			// else {}							// func_name doesn't match cur_name; continue below; EX: NAME"+"SPACENUMBER passed in and matches NAME"+"SPACE (which is cs); note that NAME"+"SPACENUMBER only exists in ci
		}
		byte[] ary = lang.Case_mgr().Case_build_lower(src, bgn, end);	// NOTE: cannot call Case_reuse_lower b/c some langs (Turkish) may have differently-sized brys between upper and lower; DATE:2017-01-26
		Xot_defn rv_alt = (Xot_defn)ci_trie.Match_bgn(ary, 0, ary.length);
		return (rv != null && rv_alt == null) 
			? rv		// name not found in ci, but name was found in cs; return cs; handles NAME"+"SPACENUMBER
			: rv_alt;	// else return rv_alt
	}
}
