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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*;
import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.lists.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.miscs.*;
public class Xop_lxr_mgr {
	private final    Xop_lxr[] ary;
	private final    List_adp page_lxr_list = List_adp_.New();
	public Xop_lxr_mgr(Xop_lxr[] ary) {this.ary = ary;}
	public Btrie_fast_mgr Trie() {return trie;} private final    Btrie_fast_mgr trie = Btrie_fast_mgr.cs();
	public void Page__add(Xowe_wiki wiki, Xop_lxr... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xop_lxr lxr = ary[i];
			lxr.Init_by_wiki(wiki, trie);
			page_lxr_list.Add(lxr);
		}
	}
	public void Page__del_all() {
		int len = page_lxr_list.Count();
		for (int i = 0; i < len; ++i) {
			Xop_lxr lxr = (Xop_lxr)page_lxr_list.Get_at(i);
			lxr.Term(trie);
		}
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xop_lxr lxr = ary[i];
			lxr.Init_by_wiki(wiki, trie);
		}
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xop_lxr lxr = ary[i];
			lxr.Init_by_lang(lang, trie);
		}
	}
	public static Xop_lxr_mgr new_tmpl_() {
		return new Xop_lxr_mgr(new Xop_lxr[] 
		{ Xop_pipe_lxr.Instance, new Xop_eq_lxr(true), Xop_colon_lxr.Instance, Xop_space_lxr.Instance, Xop_tab_lxr.Instance, Xop_nl_lxr.Instance
		, Xop_curly_bgn_lxr.Instance, Xop_curly_end_lxr.Instance
		, Xop_brack_bgn_lxr.Instance, Xop_brack_end_lxr.Instance
		, Xop_comm_lxr.Instance
		, Xop_xnde_lxr.Instance	// needed for xtn, noinclude, etc.
		, Xop_under_lxr.Instance
		, gplx.xowa.xtns.translates.Xop_tvar_lxr.Instance
		, Xop_cr_lxr.Instance		// always ignore \r; DATE:2014-03-02
		});
	}
	public static Xop_lxr_mgr new_wiki_() {
		return new Xop_lxr_mgr(new Xop_lxr[] 
		{ Xop_pipe_lxr.Instance, new Xop_eq_lxr(false), Xop_space_lxr.Instance, Xop_tab_lxr.Instance, Xop_nl_lxr.Instance
		, Xop_amp_lxr.Instance, Xop_apos_lxr.Instance, Xop_colon_lxr.Instance
		, Xop_lnki_lxr_bgn.Instance, Xop_lnki_lxr_end.Instance
		, Xop_list_lxr.Instance
		, Xop_hdr_lxr.Instance
		, Xop_hr_lxr.Instance
		, Xop_xnde_lxr.Instance
		, Xop_lnke_lxr.Instance, Xop_lnke_end_lxr.Instance
		, Xop_tblw_lxr.Instance
		, Xop_pre_lxr.Instance, Xop_nl_tab_lxr.Instance
		, Xop_comm_lxr.Instance
		, Xop_under_lxr.Instance
		});
	}
	public static Xop_lxr_mgr new_anchor_encoder() {
		return new Xop_lxr_mgr(new Xop_lxr[]
		{ Xop_pipe_lxr.Instance, new Xop_eq_lxr(false), Xop_space_lxr.Instance, Xop_tab_lxr.Instance, Xop_nl_lxr.Instance
		, Xop_curly_bgn_lxr.Instance, Xop_curly_end_lxr.Instance
		, Xop_amp_lxr.Instance, Xop_colon_lxr.Instance
		, Xop_apos_lxr.Instance
		, Xop_lnki_lxr_bgn.Instance, Xop_lnki_lxr_end.Instance
		, Xop_lnke_lxr.Instance, Xop_lnke_end_lxr.Instance
		, Xop_xnde_lxr.Instance
		});
	}
	public static final    Xop_lxr_mgr Popup_lxr_mgr	// same as orig_page, except apos_lxr added
		= new Xop_lxr_mgr(new Xop_lxr[] 
		{ Xop_pipe_lxr.Instance, new Xop_eq_lxr(true), Xop_colon_lxr.Instance, Xop_space_lxr.Instance, Xop_tab_lxr.Instance, Xop_nl_lxr.Instance
		, Xop_curly_bgn_lxr.Instance, Xop_curly_end_lxr.Instance
		, Xop_brack_bgn_lxr.Instance, Xop_brack_end_lxr.Instance
		, Xop_comm_lxr.Instance
		, Xop_xnde_lxr.Instance	// needed for xtn, noinclude, etc.
		, Xop_under_lxr.Instance
		, gplx.xowa.xtns.translates.Xop_tvar_lxr.Instance
		, Xop_cr_lxr.Instance		// always ignore \r; DATE:2014-03-02
		, gplx.xowa.parsers.apos.Xop_apos_lxr.Instance	// needed else multiple apos may be split across blocks;
		});
}
