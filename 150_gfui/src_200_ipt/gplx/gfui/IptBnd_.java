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
import gplx.core.strings.*;
public class IptBnd_ {
	public static void msg_(IptCfg cfg, IptBndsOwner box, String bndKey, GfoMsg m, IptArg... ipt) {bld_(cfg, box, GfoInvkAble_.as_(box), bndKey, m, ipt);}
	public static void msg_to_(IptCfg cfg, IptBndsOwner box, GfoInvkAble invk, String bndKey, GfoMsg m, IptArg... ipt) {
		bld_(cfg, box, invk, bndKey, m, ipt);
	}
	public static void cmd_(IptCfg cfg, IptBndsOwner box, String key, IptArg... ipt) {bld_(cfg, box, GfoInvkAble_.as_(box), key, GfoMsg_.new_cast_(key), ipt);}
	public static void cmd_to_(IptCfg cfg, IptBndsOwner box, GfoInvkAble invk, String key, IptArg... ipt) {bld_(cfg, box, invk, key, GfoMsg_.new_cast_(key), ipt);}
	public static void ipt_to_(IptCfg cfg, IptBndsOwner box, GfoInvkAble invk, String key, IptEventType eventType, IptArg... ipt) {bld_(cfg, box, invk, key, GfoMsg_.new_cast_(key), eventType, ipt);}

	static void bld_(IptCfg cfg, IptBndsOwner box, GfoInvkAble invk, String bndKey, GfoMsg m, IptArg... ipt) {bld_(cfg, box, invk, bndKey, m, IptEventType_.default_(ipt), ipt);}
	static void bld_(IptCfg cfg, IptBndsOwner box, GfoInvkAble invk, String bnd_key, GfoMsg m, IptEventType ev_type, IptArg... ipt) {
		IptCfgItm itm = cfg.GetOrDefaultArgs(bnd_key, m, ipt);
		IptBnd bnd = IptBnd_invk.new_(box, invk, itm, ev_type);
		cfg.Owners_add(bnd_key, box);
		box.IptBnds().Add(bnd);
	}
	public static Object Srl(GfoMsg owner, IptBnd bnd) {GfoMsg_.srl_(owner, "bnd").Add("key", bnd.Key()).Add("ipt", AryXtoStr(bnd.Ipts())); return bnd;}
	static String AryXtoStr(List_adp ary) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary.Count(); i++)
			sb.Add_spr_unless_first(((IptArg)ary.Get_at(i)).Key(), "|", i);
		return sb.XtoStr();
	}
}
class IptBnd_invk implements IptBnd {
	public String		Key() {return key;} private String key;
	public List_adp		Ipts() {return ipts;}  List_adp ipts;
	public IptEventType	EventTypes() {return eventTypes;} IptEventType	eventTypes;
	public void			Exec(IptEventData iptData) {
		GfoMsg newMsg = m.CloneNew();
		newMsg.Add("iptData", iptData);
		GfsCtx ctx = GfsCtx.new_().MsgSrc_(owner);
		invk.Invk(ctx, 0, m.Key(), newMsg);
		iptData.Handled_on();	// NOTE: treat invk as SingleDispatch
	}	IptBndsOwner owner; GfoInvkAble invk; IptCfgItm itm; GfoMsg m;
	public Object Srl(GfoMsg owner) {return IptBnd_.Srl(owner, this);}
	public static IptBnd_invk new_(IptBndsOwner owner, GfoInvkAble invk, IptCfgItm itm, IptEventType evType) {
		IptBnd_invk rv = new IptBnd_invk();
		rv.owner = owner; rv.invk = invk; rv.itm = itm;
		rv.key = itm.Key(); rv.ipts = itm.Ipt(); rv.m = itm.Msg(); rv.eventTypes = evType;
		return rv;
	}
}
