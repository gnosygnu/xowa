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
package gplx.xowa.apis.xowa.wikis.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.wikis.*;
public class Xoap_lang_variants implements GfoInvkAble, GfoEvMgrOwner {
	public Xoap_lang_variants() {
		this.ev_mgr = GfoEvMgr.new_(this);
	}
	public GfoEvMgr EvMgr() {return ev_mgr;} private final GfoEvMgr ev_mgr;
	public byte[] Current() {return current;} private byte[] current;
	public void Current_(byte[] v) {
		this.current = v;
		GfoEvMgr_.PubVal(this, Evt_current_changed, v);
	}
	public void Subscribe(GfoEvObj sub) {
		GfoEvMgr_.SubSame(this, Evt_current_changed, sub);
		if (current != null) GfoInvkAble_.InvkCmd_val(sub, Evt_current_changed, current);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_current)) 							return String_.new_u8(current);
		else if	(ctx.Match(k, Invk_current_)) 							Current_(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_current = "current", Invk_current_ = "current_";
	public static final String
	  Evt_current_changed = "current_changed"
	;
}
