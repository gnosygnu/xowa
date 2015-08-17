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
import gplx.lists.*; /*Hash_adp_list*/
class GfuiWinKeyCmdMgr implements GfuiWinOpenAble, GfoInvkAble, GfoEvObj {
	private Hash_adp_list listHash = Hash_adp_list.new_();
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} private GfoEvMgr evMgr;
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
			&& !Enm_.HasInt(keyVal, IptKey_.Alt.Val())		// does key not have alt
			) return false;									// ignore keys from textbox if they do not have alt
		List_adp elemList = (List_adp)listHash.Get_by(keyVal); if (elemList == null) return false;
		for (int i = 0; i < elemList.Count(); i++) {
			GfuiElem elem = (GfuiElem)elemList.Get_at(i);
			if (elem.Visible())
				elem.Click();
		}
		return true;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		ctx.Match(k, k);
		CheckForHotKey(IptEventData.ctx_(ctx, m));
		//boolean handled = CheckForHotKey(IptEventData.cast_(msg.Val())); msg.Fwd_set(!handled); // TOMBSTONE: somehow cause alt-F4 to continue processing and dispose form
		return this;
	}	@gplx.Internal protected static final String CheckForHotKey_cmd = "CheckForHotKey_cmd";

	public static GfuiWinKeyCmdMgr new_() {return new GfuiWinKeyCmdMgr();} GfuiWinKeyCmdMgr() {}
	public static int ExtractPosFromText(String raw) {
		int pos = String_.FindFwd(raw, "&");
		if (pos == String_.Find_none || pos == String_.Len(raw) - 1) return String_.Find_none;	// & notFound or last char; return;				
		char nextChar = String_.CharAt(raw, pos + 1);			
		return Char_.IsLetterEnglish(nextChar) ? pos : String_.Find_none; // NOTE: .IsLetter checks against space; EX: "me & you" shouldn't bring back space
	}
	public static IptKey ExtractKeyFromText(String raw) {
		int pos = ExtractPosFromText(raw); if (pos == String_.Find_none) return IptKey_.None;
		return IptKey_.parse_("key." + String_.Lower(Char_.To_str(String_.CharAt(raw, pos + 1))));	// pos=& pos; + 1 to get next letter
	}
}
