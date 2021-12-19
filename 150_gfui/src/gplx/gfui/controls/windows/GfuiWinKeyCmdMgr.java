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
package gplx.gfui.controls.windows;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.GfsCtx;
import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
import gplx.core.lists.*; import gplx.core.bits.*;
import gplx.frameworks.evts.Gfo_evt_itm;
import gplx.frameworks.evts.Gfo_evt_mgr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.StringUtl;
public class GfuiWinKeyCmdMgr implements GfuiWinOpenAble, Gfo_invk, Gfo_evt_itm {
	private Hash_adp_list listHash = Hash_adp_list.new_();
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public void Open_exec(GfuiWin form, GfuiElemBase owner, GfuiElemBase sub) {
		int keyVal = sub.Click_key().Val(); if (sub.Click_key().Eq(IptKey_.None)) return;
		listHash.AddInList(keyVal, sub);
		listHash.AddInList(keyVal ^ IptKey_.Alt.Val(), sub);
	}
	public boolean CheckForHotKey(IptEventData iptData) {
		if (iptData.EventType() != IptEventType_.KeyDown) return false; // NOTE: MouseClick sometimes will send key
		int keyVal = iptData.Key().Val();
		GfuiElem sender = GfuiElem_.as_(iptData.Sender());
		if (GfuiTextBox_.as_(sender) != null				// is sender textBox?
			&& !Bitmask_.Has_int(keyVal, IptKey_.Alt.Val())		// does key not have alt
			) return false;									// ignore keys from textbox if they do not have alt
		List_adp elemList = (List_adp)listHash.GetByOrNull(keyVal); if (elemList == null) return false;
		for (int i = 0; i < elemList.Len(); i++) {
			GfuiElem elem = (GfuiElem)elemList.GetAt(i);
			if (elem.Visible())
				elem.Click();
		}
		return true;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		ctx.Match(k, k);
		CheckForHotKey(IptEventData.ctx_(ctx, m));
		//boolean handled = CheckForHotKey(IptEventData.cast(msg.Val())); msg.Fwd_set(!handled); // TOMBSTONE: somehow cause alt-F4 to continue processing and dispose form
		return this;
	}	public static final String CheckForHotKey_cmd = "CheckForHotKey_cmd";

	public static GfuiWinKeyCmdMgr new_() {return new GfuiWinKeyCmdMgr();} GfuiWinKeyCmdMgr() {}
	public static int ExtractPosFromText(String raw) {
		int pos = StringUtl.FindFwd(raw, "&");
		if (pos == StringUtl.FindNone || pos == StringUtl.Len(raw) - 1) return StringUtl.FindNone;	// & notFound or last char; return;
		char nextChar = StringUtl.CharAt(raw, pos + 1);
		return CharUtl.IsLetterEnglish(nextChar) ? pos : StringUtl.FindNone; // NOTE: .IsLetter checks against space; EX: "me & you" shouldn't bring back space
	}
	public static IptKey ExtractKeyFromText(String raw) {
		int pos = ExtractPosFromText(raw); if (pos == StringUtl.FindNone) return IptKey_.None;
		return IptKey_.parse("key." + StringUtl.Lower(CharUtl.ToStr(StringUtl.CharAt(raw, pos + 1))));	// pos=& pos; + 1 to get next letter
	}
}
