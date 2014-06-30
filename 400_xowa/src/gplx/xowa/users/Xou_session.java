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
public class Xou_session implements GfoInvkAble {
	public Xou_session(Xou_user user) {this.user = user; window_mgr = new Xous_window_mgr(user);}
	public Xou_user User() {return user;} private Xou_user user;
	public Xous_window_mgr Window_mgr() {return window_mgr;} private Xous_window_mgr window_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {			
		if		(ctx.Match(k, Invk_window))			return window_mgr;
		return this;
	}	public static final String Invk_window = "window";
}
