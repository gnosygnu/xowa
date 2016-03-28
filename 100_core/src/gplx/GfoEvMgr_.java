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
public class GfoEvMgr_ {
	public static void Sub(GfoEvMgrOwner pub, String pubEvt, GfoEvObj sub, String subEvt) {pub.EvMgr().AddSub(pub, pubEvt, sub, subEvt);}
	public static void SubSame(GfoEvMgrOwner pub, String evt, GfoEvObj sub) {pub.EvMgr().AddSub(pub, evt, sub, evt);}
	public static void SubSame_many(GfoEvMgrOwner pub, GfoEvObj sub, String... evts) {
		int len = evts.length;
		for (int i = 0; i < len; i++) {
			String evt = evts[i];
			pub.EvMgr().AddSub(pub, evt, sub, evt);
		}
	}
	public static void Pub(GfoEvMgrOwner pub, String pubEvt) {pub.EvMgr().Pub(GfsCtx.new_(), pubEvt, GfoMsg_.new_cast_(pubEvt));}
	public static void PubObj(GfoEvMgrOwner pub, String pubEvt, String key, Object v) {pub.EvMgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, Keyval_.new_(key, v)));}
	public static void PubVal(GfoEvMgrOwner pub, String pubEvt, Object v) {pub.EvMgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, Keyval_.new_("v", v)));}
	public static void PubVals(GfoEvMgrOwner pub, String pubEvt, Keyval... ary) {pub.EvMgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, ary));}
	public static void PubMsg(GfoEvMgrOwner pub, GfsCtx ctx, String pubEvt, GfoMsg m) {pub.EvMgr().Pub(ctx, pubEvt, m);}
	public static void Lnk(GfoEvMgrOwner pub, GfoEvMgrOwner sub) {sub.EvMgr().Lnk(pub);}
	public static void RlsPub(GfoEvMgrOwner pub) {pub.EvMgr().RlsPub(pub);}
	public static void RlsSub(GfoEvMgrOwner sub) {sub.EvMgr().RlsSub(sub);}
	static GfoMsg msg_(String evt, Keyval... kvAry) {
		GfoMsg m = GfoMsg_.new_cast_(evt);
		for (int i = 0; i < kvAry.length; i++) {
			Keyval kv = kvAry[i];
			m.Add(kv.Key(), kv.Val());
		}
		return m;
	}
}
