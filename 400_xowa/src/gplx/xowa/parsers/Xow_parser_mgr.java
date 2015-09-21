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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.*;
public class Xow_parser_mgr {
	private final Xowe_wiki wiki; private final Xop_tkn_mkr tkn_mkr;
	private Xop_parser anchor_encode_parser;
	public Xow_parser_mgr(Xowe_wiki wiki) {
		this.wiki = wiki; this.tkn_mkr = wiki.Appe().Parser_mgr().Tkn_mkr();
		this.ctx = Xop_ctx.new_(wiki);
		this.main = Xop_parser.new_wiki(wiki);
	}
	public Xop_ctx Ctx() {return ctx;} private final Xop_ctx ctx;
	public Xop_parser Main() {return main;} private final Xop_parser main;
	public Xop_parser Anchor_encoder() {
		if (anchor_encode_parser == null) {
			anchor_encode_parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), Xop_lxr_mgr.new_anchor_encoder());
			anchor_encode_parser.Init_by_wiki(wiki);
			anchor_encode_parser.Init_by_lang(wiki.Lang());
		}
		return anchor_encode_parser;
	}
	public void Parse(Xoae_page page, boolean clear) {
		if (!Env_.Mode_testing()) wiki.Init_assert();
		gplx.xowa.xtns.scribunto.Scrib_core.Core_page_changed(page);		// notify scribunto about page changed
		ctx.Cur_page_(page);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(page.Data_raw());
		if (clear) {page.Clear();}
		Xoa_ttl ttl = page.Ttl();
		if (Xow_page_tid.Identify(wiki.Domain_tid(), ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext)	// only parse page if wikitext; skip .js, .css, Module; DATE:2013-11-10
			main.Parse_text_to_wdom(root, ctx, tkn_mkr, page.Data_raw(), Xop_parser_.Doc_bgn_bos);
		page.Root_(root);
		root.Data_htm_(root.Root_src());
	}
}
