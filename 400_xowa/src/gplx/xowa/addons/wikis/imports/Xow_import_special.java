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
package gplx.xowa.addons.wikis.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
import gplx.core.ios.*;	
public class Xow_import_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		// get path
		String owner_str = url_args.Read_str_or_null("path");
		if (owner_str == null) {
			Xopage_html_data.err_("url has unknown path").Apply(page);
			return;
		}

		// check if dir_cmd is available
		byte[] dir_cmd = url_args.Read_bry_or_null("dir_cmd");

		// check selected
		int selected = url_args.Read_int_or("selected", -1);
		if (	selected == 1
			&&	dir_cmd != null) {
			Xow_import_addon addon = Xow_import_addon.Addon__get(wiki);
			Xow_import_dir_cbk import_cbk = addon.Dir_selected_cbks__get_by(String_.new_u8(dir_cmd));
			import_cbk.Cbk__dir_selected(wiki, page, owner_str);
		}

		new Xow_import_html(Io_url_.new_dir_(owner_str), dir_cmd).Bld_page_by_mustache(wiki.App(), page, this);
	}

	public static byte[] Get_root_url() {
		byte tid = gplx.core.envs.Op_sys.Cur().Tid();
		byte[] rv = Bry_.new_a7("/");
		switch (tid) {
			case gplx.core.envs.Op_sys.Tid_wnt	: rv = Bry_.new_a7("C:\\"); break;
		}
		rv = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(rv);
		return rv;
	}

	Xow_import_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xow_import_special(Xow_special_meta.New_xo("XowaWikiImport", "Import wiki", "XowaFileBrowser"));
}
