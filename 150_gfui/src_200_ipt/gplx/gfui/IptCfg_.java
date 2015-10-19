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
public class IptCfg_ {
	public static final IptCfg Null = IptCfg_null.Instance;
	public static IptCfg new_(String key) {return IptCfgRegy.Instance.GetOrNew(key);}
}
class IptCfg_null implements IptCfg {
	public String CfgKey() {return "<<NULL KEY>>";}
	public IptCfgItm GetOrDefaultArgs(String bndKey, GfoMsg m, IptArg[] argAry) {return IptCfgItm.new_().Key_(bndKey).Ipt_(List_adp_.many_((Object[])argAry)).Msg_(m);}
	public void Owners_add(String key, IptBndsOwner owner) {}
	public void Owners_del(String key) {}
	public Object NewByKey(Object o) {return this;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
	public static final IptCfg_null Instance = new IptCfg_null(); IptCfg_null() {}
}
