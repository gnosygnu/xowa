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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
public class Xomp_make_cmd_cfg implements Gfo_invk {
	public boolean Delete_html_dbs() {return delete_html_dbs;} private boolean delete_html_dbs = true;
	public Ordered_hash Merger_wkrs() {return merger_wkrs;} private final    Ordered_hash merger_wkrs = Ordered_hash_.New();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__delete_html_dbs_))				delete_html_dbs = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__merger_wkrs_)) {
			String[] ary = m.ReadStrAry("k", "|");
			for (String itm : ary)
				merger_wkrs.Add(itm, itm);
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__delete_html_dbs_ = "delete_html_dbs_", Invk__merger_wkrs_ = "merger_wkrs_";
}
