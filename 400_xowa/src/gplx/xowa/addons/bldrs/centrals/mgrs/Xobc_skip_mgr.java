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
