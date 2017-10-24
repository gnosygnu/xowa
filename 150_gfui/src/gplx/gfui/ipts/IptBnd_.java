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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.strings.*;
public class IptBnd_ {
	public static void msg_(IptCfg cfg, IptBndsOwner box, String bndKey, GfoMsg m, IptArg... ipt) {bld_(cfg, box, (Gfo_invk)box, bndKey, m, ipt);}
	public static void msg_to_(IptCfg cfg, IptBndsOwner box, Gfo_invk invk, String bndKey, GfoMsg m, IptArg... ipt) {
		bld_(cfg, box, invk, bndKey, m, ipt);
	}
	public static void cmd_(IptCfg cfg, IptBndsOwner box, String key, IptArg... ipt) {bld_(cfg, box, (Gfo_invk)box, key, GfoMsg_.new_cast_(key), ipt);}
	public static void cmd_to_(IptCfg cfg, IptBndsOwner box, Gfo_invk invk, String key, IptArg... ipt) {bld_(cfg, box, invk, key, GfoMsg_.new_cast_(key), ipt);}
	public static void ipt_to_(IptCfg cfg, IptBndsOwner box, Gfo_invk invk, String key, IptEventType eventType, IptArg... ipt) {bld_(cfg, box, invk, key, GfoMsg_.new_cast_(key), eventType, ipt);}

	static void bld_(IptCfg cfg, IptBndsOwner box, Gfo_invk invk, String bndKey, GfoMsg m, IptArg... ipt) {bld_(cfg, box, invk, bndKey, m, IptEventType_.default_(ipt), ipt);}
	static void bld_(IptCfg cfg, IptBndsOwner box, Gfo_invk invk, String bnd_key, GfoMsg m, IptEventType ev_type, IptArg... ipt) {
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
		return sb.To_str();
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
	}	IptBndsOwner owner; Gfo_invk invk; IptCfgItm itm; GfoMsg m;
	public Object Srl(GfoMsg owner) {return IptBnd_.Srl(owner, this);}
	public static IptBnd_invk new_(IptBndsOwner owner, Gfo_invk invk, IptCfgItm itm, IptEventType evType) {
		IptBnd_invk rv = new IptBnd_invk();
		rv.owner = owner; rv.invk = invk; rv.itm = itm;
		rv.key = itm.Key(); rv.ipts = itm.Ipt(); rv.m = itm.Msg(); rv.eventTypes = evType;
		return rv;
	}
}
