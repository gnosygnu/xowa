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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
public class Xou_cfg implements GfoInvkAble {
	public Xou_cfg(Xoue_user user) {
		this.user = user;
		pages_mgr = new Xouc_pages_mgr(this); startup_mgr = new Xouc_startup_mgr(this); setup_mgr = new Xouc_setup_mgr(user);
		layout_mgr = new Xoc_layout_mgr(user.Appe());
		security_mgr = new Xou_security_mgr(user.Appe());
	}
	public Xoue_user User() {return user;} private Xoue_user user;
	public Xouc_pages_mgr Pages_mgr() {return pages_mgr;} private Xouc_pages_mgr pages_mgr;
	public Xouc_startup_mgr Startup_mgr() {return startup_mgr;} private Xouc_startup_mgr startup_mgr;
	public Xouc_setup_mgr Setup_mgr() {return setup_mgr;} private Xouc_setup_mgr setup_mgr;
	public Xoc_layout_mgr Layout_mgr() {return layout_mgr;} private Xoc_layout_mgr layout_mgr;
	public Xou_security_mgr Security_mgr() {return security_mgr;} private Xou_security_mgr security_mgr;
	public Xou_log_mgr Log_mgr() {return log_mgr;} private final Xou_log_mgr log_mgr = new Xou_log_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_pages))			return pages_mgr;
		else if	(ctx.Match(k, Invk_startup))		return startup_mgr;
		else if	(ctx.Match(k, Invk_setup))			return setup_mgr;
		else if	(ctx.Match(k, Invk_layout))			return layout_mgr;
		else if	(ctx.Match(k, Invk_security))		return security_mgr;
		else if	(ctx.Match(k, Invk_log))			return log_mgr;
		return this;
	}
	public static final String
	 Invk_pages = "pages", Invk_startup = "startup", Invk_setup = "setup", Invk_layout = "layout", Invk_security = "security", Invk_log = "log"
	;
}
