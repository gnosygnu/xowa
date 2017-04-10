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
package gplx.xowa.addons.bldrs.centrals.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.xowa.addons.bldrs.centrals.dbs.datas.imports.*;
public class Xobc_skip_mgr implements Gfo_invk {
	private boolean category_enabled;
	public Xobc_skip_mgr(Xobc_task_mgr task_mgr, Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__namespaces_category);
	}
	public boolean Should_skip(Xobc_import_step_itm step_itm) {
		if (category_enabled && step_itm.Import_type == Xobc_import_type.Tid__wiki__ctg)
			return true;
		else
			return false;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__namespaces_category))	category_enabled = m.ReadBool("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Cfg__namespaces_category	= "xowa.bldr.download_central.namespaces.category"
	;
}
