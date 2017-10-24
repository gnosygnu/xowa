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
package gplx.xowa.addons.bldrs.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
public class Xobc_task_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		// init task_mgr
		page.Html_data().Cbk_enabled_(true);	// apply cbk_enabled early to capture logging statements
		Xoa_app app = wiki.App();
		if (task_mgr == null) task_mgr = New_task_mgr(app);
		task_mgr.Load_or_init();

		new Xobc_task_html().Bld_page_by_mustache(app, page, this);
	}
	public static Xobc_task_mgr Task_mgr(Xoa_app app) {
		if (task_mgr == null) {
			task_mgr = New_task_mgr(app);
		}
		return task_mgr;
	}	private static Xobc_task_mgr task_mgr;
	private static Xobc_task_mgr New_task_mgr(Xoa_app app) {
		Io_url data_db_url = app.Fsys_mgr().Bin_addon_dir().GenSubFil_nest("bldr", "central", "bldr_central.data_db.xowa");
		app.User().User_db_mgr().Init_site_mgr();
		return new Xobc_task_mgr(app, data_db_url);
	}

	Xobc_task_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xobc_task_special(Xow_special_meta.New_xo("XowaDownloadCentral", "Download Central"));
}
