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
package gplx.xowa; import gplx.*;
class Xoo_mgr implements GfoInvkAble {
	public Xoo_mgr(Xoa_app app) {this.app = app;} private Xoa_app app;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner)) return app;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
class Xoo_grp implements GfoInvkAble {
	public Xoo_grp(Xoo_mgr mgr, String key) {this.mgr = mgr; this.key = key;} private Xoo_mgr mgr;
	public String Key() {return key;} private String key;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner)) return mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
class Xoo_itm_html {
	public Xoo_itm_html(Xoo_grp grp) {this.grp = grp;} private Xoo_grp grp;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner)) return grp;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
class Xoo_itm_file {
	public Xoo_itm_file(Xoo_grp grp) {this.grp = grp;} private Xoo_grp grp;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner)) return grp;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
class Xoo_itm_interwiki {
	public Xoo_itm_interwiki(Xoo_grp grp) {this.grp = grp;} private Xoo_grp grp;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner)) return grp;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner";
}
