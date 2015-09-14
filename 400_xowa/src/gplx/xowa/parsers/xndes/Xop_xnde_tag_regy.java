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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_xnde_tag_regy {
	private boolean init_needed = true;
	private final Btrie_slim_mgr		// NOTE:ci.utf8; he.s and <section> alias DATE:2014-07-18
	  trie_tmpl			= Btrie_slim_mgr.ci_u8()
	, trie_wtxt_main	= Btrie_slim_mgr.ci_u8()
	, trie_wtxt_tmpl	= Btrie_slim_mgr.ci_u8();
	public Btrie_slim_mgr Get_trie(int i) {
		if (init_needed) Init_by_hash(null);		// TEST:
		switch (i) {
			case Xop_parser_.Parse_tid_tmpl:			return trie_tmpl;
			case Xop_parser_.Parse_tid_page_tmpl:		return trie_wtxt_tmpl;
			case Xop_parser_.Parse_tid_page_wiki:		return trie_wtxt_main;
			case Xop_parser_.Parse_tid_null: default: 	return trie_wtxt_tmpl; // TODO: should throw Err_.new_unhandled(i);
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
			if (	xtn_hash != null				// xtn_hash is null during tests or when wiki is not in site_meta_db
				&&	xnde.Xtn_mw()					// only apply filter to xtn_xnde, not basic_xnde; EX: <dynamicpagelist> not <table>
				&&	!xtn_hash.Has(xnde.Name_bry())	// xtn_xnde is not in xtn_hash
				)	continue;						// skip; xtn is not defined in site_meta_db
			if (is_tmpl && !xnde.Xtn())				// is_tmpl and basic_xnde; EX: <b>
				continue;							// skip; tmpl only needs xtn_xnde;
			Add_itm(trie, xnde);
		}
		if (is_tmpl) {								// is_tmpl also has <nowiki>, <includeonly>, <noinclude>, <onlyinclude>
			Add_itm(trie, Xop_xnde_tag_.Tag_nowiki);
			Add_itm(trie, Xop_xnde_tag_.Tag_includeonly);
			Add_itm(trie, Xop_xnde_tag_.Tag_noinclude);
			Add_itm(trie, Xop_xnde_tag_.Tag_onlyinclude);
		}
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
