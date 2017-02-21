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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_xnde_tag_regy {
	private boolean init_needed = true;
	private final    Btrie_slim_mgr		// NOTE:ci.utf8; he.s and <section> alias DATE:2014-07-18
	  trie_tmpl			= Btrie_slim_mgr.ci_u8()
	, trie_wtxt_main	= Btrie_slim_mgr.ci_u8()
	, trie_wtxt_tmpl	= Btrie_slim_mgr.ci_u8();
	public Btrie_slim_mgr Get_trie(int i) {
		if (init_needed) Init_by_hash(null);		// TEST:
		switch (i) {
			case Xop_parser_tid_.Tid__defn:			return trie_tmpl;
			case Xop_parser_tid_.Tid__tmpl:		return trie_wtxt_tmpl;
			case Xop_parser_tid_.Tid__wtxt:		return trie_wtxt_main;
			case Xop_parser_tid_.Tid__null: default: 	return trie_wtxt_tmpl; // TODO_OLD: should throw Err_.new_unhandled(i);
		}
	}
	public void Init_by_meta(Hash_adp_bry xtn_hash) {Init_by_hash(xtn_hash);}
	private void Init_by_hash(Hash_adp_bry xtn_hash) {
		this.init_needed = false;
		Init_trie(trie_tmpl			, xtn_hash, Bool_.Y);
		Init_trie(trie_wtxt_tmpl	, xtn_hash, Bool_.Y);
		Init_trie(trie_wtxt_main	, xtn_hash, Bool_.N);
	}
	private void Init_trie(Btrie_slim_mgr trie, Hash_adp_bry xtn_hash, boolean is_tmpl) {
		int len = Xop_xnde_tag_.Tid__len;
		Xop_xnde_tag[] ary = Xop_xnde_tag_.Ary;
		for (int i = 0; i < len; ++i) {
			Xop_xnde_tag xnde = ary[i];
			if (Ignore_xnde(xtn_hash, xnde))	continue;	// skip; xtn is not defined in site_meta_db
			if (is_tmpl && !xnde.Xtn())			continue;	// is_tmpl and basic_xnde; EX: <b>
			Add_itm(trie, xnde);
		}
		if (is_tmpl) {								// is_tmpl also has <nowiki>, <includeonly>, <noinclude>, <onlyinclude>
			Add_itm(trie, Xop_xnde_tag_.Tag__nowiki);
			Add_itm(trie, Xop_xnde_tag_.Tag__includeonly);
			Add_itm(trie, Xop_xnde_tag_.Tag__noinclude);
			Add_itm(trie, Xop_xnde_tag_.Tag__onlyinclude);
		}
	}
	private boolean Ignore_xnde(Hash_adp_bry xtn_hash, Xop_xnde_tag xnde) {
		return 	xtn_hash != null					// xtn_hash is null during tests or when wiki is not in site_meta_db
			&&	xnde.Xtn_mw()						// only apply filter to xtn_xnde, not basic_xnde; EX: <dynamicpagelist> not <table>
			&&	!xtn_hash.Has(xnde.Name_bry())		// xtn_xnde is not in xtn_hash
			&&	!Int_.In(xnde.Id(), Xop_xnde_tag_.Tid__translate, Xop_xnde_tag_.Tid__languages)	// always include <translate> and <languages>; TODO_OLD:filter out when extensions supported in site_cfg; DATE:2015-10-13
			;										// skip; xtn is not defined in site_meta_db
	}
	private void Add_itm(Btrie_slim_mgr trie, Xop_xnde_tag xnde) {
		trie.Add_obj(xnde.Name_bry(), xnde);
		Ordered_hash langs = xnde.Langs();
		if (langs != null) {						// tag has langs; EX: <section>; DATE:2014-07-18
			int langs_len = langs.Count();
			for (int i = 0; i < langs_len; ++i) {	// register each lang's tag; EX: "<Abschnitt>", "<trecho>"
				Xop_xnde_tag_lang lang = (Xop_xnde_tag_lang)langs.Get_at(i);
				trie.Add_obj(lang.Name_bry(), xnde);
			}
		}
	}
}
