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
public class Xop_xnde_tag_regy {
	public Btrie_slim_mgr XndeNames(int i) {
		if (nild) {Init(); nild = false;}
		switch (i) {
			case Xop_parser_.Parse_tid_tmpl:		return tag_regy_tmpl;
			case Xop_parser_.Parse_tid_page_tmpl:	return tag_regy_wiki_tmpl;
			case Xop_parser_.Parse_tid_page_wiki:	return tag_regy_wiki_main;
			default:								return tag_regy_wiki_tmpl; //throw Exc_.new_unhandled(i);
		}
	}	boolean nild = true;
	public void Init() {
		Init_reg(tag_regy_tmpl		, FilterXtns(Xop_xnde_tag_.Ary, Xop_xnde_tag_.Tag_includeonly, Xop_xnde_tag_.Tag_noinclude, Xop_xnde_tag_.Tag_onlyinclude, Xop_xnde_tag_.Tag_nowiki));
		Init_reg(tag_regy_wiki_tmpl	, FilterXtns(Xop_xnde_tag_.Ary, Xop_xnde_tag_.Tag_includeonly, Xop_xnde_tag_.Tag_noinclude, Xop_xnde_tag_.Tag_onlyinclude, Xop_xnde_tag_.Tag_nowiki));
		Init_reg(tag_regy_wiki_main	, Xop_xnde_tag_.Ary);
	}
	private Xop_xnde_tag[] FilterXtns(Xop_xnde_tag[] ary, Xop_xnde_tag... more) {
		List_adp rv = List_adp_.new_();
		for (Xop_xnde_tag itm : ary)
			if (itm.Xtn()) rv.Add(itm);
		for (Xop_xnde_tag itm : more)
			rv.Add(itm);
		return (Xop_xnde_tag[])rv.To_ary(Xop_xnde_tag.class);
	}
	private void Init_reg(Btrie_slim_mgr tag_regy, Xop_xnde_tag... ary) {
		for (Xop_xnde_tag tag : ary) {
			tag_regy.Add_obj(tag.Name_bry(), tag);
			Ordered_hash langs = tag.Langs();
			if (langs != null) {						// tag has langs; EX: <section>; DATE:2014-07-18
				int langs_len = langs.Count();
				for (int i = 0; i < langs_len; ++i) {	// register each lang's tag; EX:"<Abschnitt>", "<trecho>"
					Xop_xnde_tag_lang lang = (Xop_xnde_tag_lang)langs.Get_at(i);
					tag_regy.Add_obj(lang.Name_bry(), tag);
				}
			}
		}
	}
	private Btrie_slim_mgr
	  tag_regy_wiki_main	= Btrie_slim_mgr.ci_utf_8_()	// NOTE:ci.utf8; he.s and <section> alias DATE:2014-07-18
	, tag_regy_wiki_tmpl	= Btrie_slim_mgr.ci_utf_8_()
	, tag_regy_tmpl			= Btrie_slim_mgr.ci_utf_8_()
	;
}
