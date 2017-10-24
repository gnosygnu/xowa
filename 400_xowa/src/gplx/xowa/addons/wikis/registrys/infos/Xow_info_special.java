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
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
public class Xow_info_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		// if cmd=delete passed; delete "wiki"
		byte[] wiki_domain = url_args.Read_bry_or_fail("wiki");
		if (url_args.Read_enm_as_int_or(Enm_cmd.Itm, -1) == Enm_cmd.Tid__delete) {
			wiki.App().User().User_db_mgr().Site_mgr().Delete_by_domain(wiki_domain);
			Xow_wiki delete_wiki = wiki.App().Wiki_mgri().Get_by_or_make_init_n(wiki_domain);
			if (delete_wiki != null) {	// guard against revisiting url for deleted wiki
				if (delete_wiki.Data__core_mgr() != null)	// null check needed in case wiki is not loaded
					delete_wiki.Data__core_mgr().Rls();		// release connection if open
				Delete_wiki_files(delete_wiki.Fsys_mgr().Root_dir());
			}
			page.Redirect_trail().Itms__add__special(wiki,  gplx.xowa.addons.wikis.registrys.lists.Xow_list_special.Prototype.Special__meta());
			return;
		}

		// show info of wikis
		new Xow_info_html(wiki_domain).Bld_page_by_mustache(wiki.App(), page, this);
	}
	private static void Delete_wiki_files(Io_url root_dir) {
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(root_dir);
		for (Io_url url : urls) {
			try {
				Io_mgr.Instance.DeleteFil(url);
			} catch (Exception e) {
				Gfo_log_.Instance.Warn("failed to delete wiki file", "wiki", url.Raw(), "err", Err_.Message_gplx_log(e));
			}
		}
	}

	static class Enm_cmd {//#*nested
		public static final int Tid__delete = 0;
		public static final    Gfo_qarg_enum_itm Itm = new Gfo_qarg_enum_itm("cmd").Add("delete", Tid__delete);
	}

	Xow_info_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xow_info_special(Xow_special_meta.New_xo("XowaWikiInfo", "Wiki Info"));
}
