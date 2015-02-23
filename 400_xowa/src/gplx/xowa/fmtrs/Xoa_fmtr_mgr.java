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
package gplx.xowa.fmtrs; import gplx.*; import gplx.xowa.*;
import gplx.ios.*;
public class Xoa_fmtr_mgr implements GfoInvkAble {
	public Xoa_fmtr_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_new_grp))		return new Xoa_fmtr_itm(app);
		else return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_new_grp = "new_grp";
}
