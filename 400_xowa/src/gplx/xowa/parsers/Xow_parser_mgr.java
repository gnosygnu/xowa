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
// using gplx.langs.jsons;
import gplx.xowa.wikis.*; import gplx.core.envs.*;
import gplx.xowa.files.*;
import gplx.xowa.xtns.pfuncs.ifs.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.pfuncs.times.*;
public class Xow_parser_mgr {
	private final    Xowe_wiki wiki; private final    Xop_tkn_mkr tkn_mkr;
	private Xop_parser anchor_encode_parser;
	public Xow_parser_mgr(Xowe_wiki wiki) {
		this.wiki = wiki; this.tkn_mkr = wiki.Appe().Parser_mgr().Tkn_mkr();
		this.ctx = Xop_ctx.New__top(wiki);
		this.parser = Xop_parser.new_wiki(wiki);
	}
	public Xop_ctx					Ctx()				{return ctx;} private final    Xop_ctx ctx;
	public Xop_parser				Main()				{return parser;} private final    Xop_parser parser;
	public Scrib_core_mgr			Scrib()				{return scrib;} private final    Scrib_core_mgr scrib = new Scrib_core_mgr();
	public Pfunc_ifexist_mgr		Ifexist_mgr()		{return ifexist_mgr;} private final    Pfunc_ifexist_mgr ifexist_mgr = new Pfunc_ifexist_mgr();
	public Xof_url_bldr				Url_bldr()			{return url_bldr;} private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public List_adp					Time_parser_itms()	{return time_parser_itms;} private final    List_adp time_parser_itms = List_adp_.New();
	public Pft_func_formatdate_bldr Date_fmt_bldr()		{return date_fmt_bldr;} private final    Pft_func_formatdate_bldr date_fmt_bldr = new Pft_func_formatdate_bldr();
	// public Json_parser			Wbase_jdoc_parser() {return wbase_jdoc_parser;} private final    Json_parser wbase_jdoc_parser = new Json_parser();
	public Xop_parser Anchor_encoder() {
		if (anchor_encode_parser == null) {
			anchor_encode_parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), Xop_lxr_mgr.new_anchor_encoder());
			anchor_encode_parser.Init_by_wiki(wiki);
			anchor_encode_parser.Init_by_lang(wiki.Lang());
		}
		return anchor_encode_parser;
	}
	public void Parse(Xoae_page page, boolean clear) {	// main parse method
		if (!Env_.Mode_testing()) wiki.Init_assert();
		scrib.When_page_changed(page);	// notify scribunto about page changed
		ctx.Page_(page);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(page.Db().Text().Text_bry());
		if (clear) {page.Clear_all();}
		Xoa_ttl ttl = page.Ttl();
		if (	Xow_page_tid.Identify(wiki.Domain_tid(), ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext) {	// only parse page if wikitext; skip .js, .css, Module; DATE:2013-11-10
			byte[] data_raw = page.Db().Text().Text_bry();
			parser.Parse_text_to_wdom(root, ctx, tkn_mkr, data_raw , Xop_parser_.Doc_bgn_bos);
		}
		page.Root_(root);
		root.Data_htm_(root.Root_src());
	}
}
