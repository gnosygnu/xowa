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
package gplx;
public class GfoInvkXtoStr {
	public static GfoMsg ReadMsg(GfoInvkAble invk, String k) {
		GfsCtx ctx = GfsCtx.wtr_();
		GfoMsg m = GfoMsg_.rdr_(k);
		invk.Invk(ctx, 0, k, m);
		String invkKey = GfsCore._.FetchKey(invk);
		GfoMsg root = GfoMsg_.new_cast_(invkKey);
		root.Subs_add(m);
		return root;
	}
	public static GfoMsg WriteMsg(GfoInvkAble invk, String k, Object... ary) {return WriteMsg(GfsCore._.FetchKey(invk), invk, k, ary);}
	public static GfoMsg WriteMsg(String invkKey, GfoInvkAble invk, String k, Object... ary) {
		GfsCtx ctx = GfsCtx.wtr_();
		GfoMsg m = GfoMsg_.wtr_();
		invk.Invk(ctx, 0, k, m);
		GfoMsg rv = GfoMsg_.new_cast_(k);
		for (int i = 0; i < m.Args_count(); i++) {
			KeyVal kv = m.Args_getAt(i);
			rv.Add(kv.Key(), ary[i]);
		}
		GfoMsg root = GfoMsg_.new_cast_(invkKey);
		root.Subs_add(rv);
		return root;
	}
}
