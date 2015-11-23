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
package gplx.gfui; import gplx.*;
public class IptCfgRegy implements GfoInvkAble {
	public void Clear() {hash.Clear();}
	public IptCfg GetOrNew(String k) {
		IptCfg rv = (IptCfg)hash.Get_by(k);
		if (rv == null) {
			rv = (IptCfg)IptCfg_base.HashProto.NewByKey(k);
			hash.Add(k, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchIn(k, Invk_Get, Invk_get)) {
			String key = m.ReadStr("key");
			if (ctx.Deny()) return this;
			return GetOrNew(key);
		}
		return this;
	}	public static final String Invk_Get = "Get", Invk_get = "get";
	Ordered_hash hash = Ordered_hash_.New();
	public static final IptCfgRegy Instance = new IptCfgRegy();
	public IptCfgRegy() {}
}
