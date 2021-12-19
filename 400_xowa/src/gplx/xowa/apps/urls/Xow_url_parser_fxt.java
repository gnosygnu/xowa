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
package gplx.xowa.apps.urls;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoa_url_;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.nss.Xow_ns_mgr;
public class Xow_url_parser_fxt {
	protected final Xoae_app app; protected final Xowe_wiki cur_wiki;
	protected final Xow_url_parser parser;
	protected Xoa_url actl_url;
	public Xow_url_parser_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.cur_wiki = Prep_create_wiki("en.wikipedia.org");
		this.parser = cur_wiki.Utl__url_parser();
		this.actl_url = Xoa_url.blank();	// default to blank for subclasses
	}
	public Xoae_app App() {return app;}
	public Xowe_wiki Wiki() {return cur_wiki;}
	public Xowe_wiki Prep_create_wiki(String domain) {
		Xowe_wiki rv = Xoa_app_fxt.Make__wiki__edit(app, domain);
		Prep_add_xwiki_to_user(domain);
		return rv;
	}
	public Xow_url_parser_fxt Prep_add_xwiki_to_wiki(String alias, String domain)				{return Prep_xwiki(cur_wiki, alias, domain, null);}
	public Xow_url_parser_fxt Prep_add_xwiki_to_wiki(String alias, String domain, String fmt)	{return Prep_xwiki(cur_wiki, alias, domain, fmt);}
	public Xow_url_parser_fxt Prep_add_xwiki_to_user(String domain)								{return Prep_xwiki(app.Usere().Wiki(), domain, domain, null);}
	public Xow_url_parser_fxt Prep_add_xwiki_to_user(String alias, String domain)				{return Prep_xwiki(app.Usere().Wiki(), alias, domain, null);}
	public Xow_url_parser_fxt Prep_add_xwiki_to_user(String alias, String domain, String fmt)	{return Prep_xwiki(app.Usere().Wiki(), alias, domain, fmt);}
	public Xow_url_parser_fxt Prep_xwiki(Xow_wiki wiki, String alias, String domain, String fmt) {
		wiki.Xwiki_mgr().Add_by_atrs(BryUtl.NewU8(alias), BryUtl.NewU8(domain), BryUtl.NewU8Safe(fmt));
		return this;
	}
	public Xow_ns_mgr Prep_get_ns_mgr_from_meta(String wiki) {
		return app.Dbmeta_mgr().Ns__get_or_load(BryUtl.NewU8(wiki));
	}
	public Xow_url_parser_fxt Exec__parse(String actl_str) {return Exec__parse(cur_wiki, actl_str);}
	public Xow_url_parser_fxt Exec__parse(Xow_wiki wiki, String actl_str) {
		this.actl_url = wiki.Utl__url_parser().Parse(BryUtl.NewU8(actl_str));
		return this;
	}
	public Xow_url_parser_fxt Exec__parse_reuse(String actl_str) {
		this.actl_url = parser.Parse(BryUtl.NewU8(actl_str));
		return this;
	}
	public Xow_url_parser_fxt Exec__parse_from_url_bar(String raw) {
		this.actl_url = parser.Parse_by_urlbar_or_null(raw);
		return this;
	}
	public Xow_url_parser_fxt	Test__tid(int v) 					{GfoTstr.Eq(v, actl_url.Tid()		, "tid"); return this;}
	public Xow_url_parser_fxt	Test__is_null() 					{GfoTstr.Eq(true, actl_url == null); return this;}
	public Xow_url_parser_fxt	Test__vnt(String v) 				{GfoTstr.Eq(v, actl_url.Vnt_bry()	, "vnt"); return this;}
	public Xow_url_parser_fxt	Test__wiki(String v) 				{GfoTstr.Eq(v, actl_url.Wiki_bry()	, "wiki"); return this;}
	public Xow_url_parser_fxt	Test__wiki_is_missing(boolean v)		{GfoTstr.Eq(v, actl_url.Wiki_is_missing(), "wiki_is_missing"); return this;}
	public Xow_url_parser_fxt	Test__page(String v) 				{GfoTstr.Eq(v, actl_url.Page_bry()	, "page"); return this;}
	public Xow_url_parser_fxt	Test__qargs(String v) 				{GfoTstr.Eq(v, actl_url.Qargs_mgr().To_bry(), "qargs"); return this;}
	public Xow_url_parser_fxt	Test__page_is_main_y() 				{return Test__page_is_main(BoolUtl.Y);}
	public Xow_url_parser_fxt	Test__page_is_main_n() 				{return Test__page_is_main(BoolUtl.N);}
	public Xow_url_parser_fxt	Test__page_is_main(boolean v)			{GfoTstr.Eq(v, actl_url.Page_is_main()	, "page_is_main"); return this;}
	public Xow_url_parser_fxt	Test__anch(String v) 				{GfoTstr.Eq(v, actl_url.Anch_bry(), "anch"); return this;}
	public Xow_url_parser_fxt	Test__action_is_edit_y() 			{return Test__action_is_edit_(BoolUtl.Y);}
	public Xow_url_parser_fxt	Test__action_is_edit_n() 			{return Test__action_is_edit_(BoolUtl.N);}
	private Xow_url_parser_fxt	Test__action_is_edit_(boolean v)		{GfoTstr.Eq(v, actl_url.Qargs_mgr().Match(Xoa_url_.Qarg__action, Xoa_url_.Qarg__action__edit), "action_is_edit"); return this;}
	public Xow_url_parser_fxt	Test__to_str(String v) 				{return Test__to_str(BoolUtl.Y, v);}
	public Xow_url_parser_fxt	Test__to_str(boolean full, String v)	{GfoTstr.Eq(v, actl_url.To_bry(full, BoolUtl.Y), "To_bry"); return this;}
	public Xow_url_parser_fxt	Test__build_str_is_same() {
		Xow_url_parser parser = new Xow_url_parser(cur_wiki);
		GfoTstr.Eq(actl_url.Raw(), parser.Build_str(actl_url), "build_str");
		return this;
	}
}
