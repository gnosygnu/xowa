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
package gplx.xowa.addons.wikis.registrys.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.registrys.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
public class Xow_list_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		// if cmd=add passed; import "file"; occurs when "file" is selected by file_browser
		if (url_args.Read_enm_as_int_or(Enm_cmd.Itm, -1) == Enm_cmd.Tid__add) {
			byte[] file = url_args.Read_bry_or_fail("file");
			if (wiki.App().Tid_is_edit()) wiki.App().User().User_db_mgr().Init_site_mgr();	// HACK.USER_DB: init site_mgr for desktop
			wiki.App().Wiki_mgri().Import_by_url(Io_url_.new_fil_(String_.new_u8(file)));
		}

		// show list of wikis
		new Xow_list_html().Bld_page_by_mustache(wiki.App(), page, this);
	}

	static class Enm_cmd {//#*nested
		public static final int Tid__add = 0;
		public static final    Gfo_qarg_enum_itm Itm = new Gfo_qarg_enum_itm("cmd").Add("add", Tid__add);
	}

	Xow_list_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xow_list_special(Xow_special_meta.New_xo("XowaWikiList", "Wikis", "XowaWikis"));
}
