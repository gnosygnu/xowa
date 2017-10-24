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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
public class Xou_user_mgr implements Gfo_invk {
	private final    Ordered_hash regy = Ordered_hash_.New();
	private final    Xoae_app app;
	public Xou_user_mgr(Xoae_app app, Xoue_user user) {this.app = app; this.Add(user);}
	public void Add(Xoue_user itm) {regy.Add(itm.Key(), itm);}
	private Xoue_user GetByKey(String key) {return (Xoue_user)regy.Get_by(key);}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get)) {
			String user_key = m.ReadStr("key");
			Xoue_user user = GetByKey(user_key);
			if (user == null) {
				user = new Xoue_user(app, app.Fsys_mgr().Root_dir().GenSubDir_nest("user", user_key));
				this.Add(user);
			}
			return user;
		}
		else return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
