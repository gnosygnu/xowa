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
public class Xou_user_mgr implements GfoInvkAble {
	public Xou_user_mgr(Xoa_app app, Xou_user user) {this.app = app; this.Add(user);} private Xoa_app app;
	public void Add(Xou_user itm) {regy.Add(itm.Key_str(), itm);}
	Xou_user GetByKey(String key) {return (Xou_user)regy.Fetch(key);}
	OrderedHash regy = OrderedHash_.new_();

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get)) {
			String user_key = m.ReadStr("key");
			Xou_user user = GetByKey(user_key);
			if (user == null) {
				user = new Xou_user(app, app.Fsys_mgr().Root_dir().GenSubDir_nest("user", user_key));
				this.Add(user);
			}
			return user;
		}
		else return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	private static final String Invk_get = "get";
}
