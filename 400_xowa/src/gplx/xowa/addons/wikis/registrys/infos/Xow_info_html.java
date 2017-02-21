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
package gplx.xowa.addons.wikis.registrys.infos; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.registrys.*;
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.users.data.*; 
class Xow_info_html extends Xow_special_wtr__base {
	private final    byte[] wiki_domain;
	public Xow_info_html(byte[] wiki_domain) {this.wiki_domain = wiki_domain;}
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "wiki", "registry", "info");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xow_info.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		// load itm from db
		app.User().User_db_mgr().Init_site_mgr();	// HACK.USER_DB: init site_mgr for desktop
		Xoud_site_row site_itm = app.User().User_db_mgr().Site_mgr().Select_by_domain(wiki_domain);
		if (site_itm == null) return null;	// handle deleted wikis
		String wiki_dir = site_itm.Path();
		if (String_.Eq(site_itm.Date(), "")) {
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_n(wiki_domain);
			wiki.Init_by_wiki();	// force init to load Modified_latest
			site_itm.Date_(wiki.Props().Modified_latest__yyyy_MM_dd());
			app.User().User_db_mgr().Site_mgr().Update(site_itm);
		}
		return new Xow_info_doc
		( new Xow_info_doc_wiki(wiki_domain, site_itm.Date(), wiki_dir
		, Calc_file_size(Io_url_.new_dir_(wiki_dir)))
		);
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_alertify_.Add_tags	(head_tags, app.Fsys_mgr().Http_root());
		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xow_info.css")));
	}
	@Override protected void Handle_invalid(Xoa_app app, Xoa_page page, Xow_special_page special) {
		new Xopage_html_data(special.Special__meta().Display_ttl(), Bry_.Add(wiki_domain, Bry_.new_a7(" has been deleted"))).Apply(page);
	}

	private static String Calc_file_size(Io_url dir) {
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(dir);
		int len = urls.length;
		long size = 0;
		for (int i = 0; i < len; ++i) {
			Io_url url = urls[i];
			size += Io_mgr.Instance.QueryFil(url).Size();
		}
		return gplx.core.ios.Io_size_.To_str_new(Bry_bfr_.New(), size, 2);
	}
}
