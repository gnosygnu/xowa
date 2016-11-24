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
public class Gfo_evt_mgr_ {
	public static void Sub(Gfo_evt_mgr_owner pub, String pubEvt, Gfo_evt_itm sub, String subEvt) {pub.Evt_mgr().AddSub(pub, pubEvt, sub, subEvt);}
	public static void Sub_same(Gfo_evt_mgr_owner pub, String evt, Gfo_evt_itm sub) {pub.Evt_mgr().AddSub(pub, evt, sub, evt);}
	public static void Sub_same_many(Gfo_evt_mgr_owner pub, Gfo_evt_itm sub, String... evts) {
		int len = evts.length;
		for (int i = 0; i < len; i++) {
			String evt = evts[i];
			pub.Evt_mgr().AddSub(pub, evt, sub, evt);
		}
	}
	public static void Pub(Gfo_evt_mgr_owner pub, String pubEvt) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, GfoMsg_.new_cast_(pubEvt));}
	public static void Pub_obj(Gfo_evt_mgr_owner pub, String pubEvt, String key, Object v) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, Keyval_.new_(key, v)));}
	public static void Pub_val(Gfo_evt_mgr_owner pub, String pubEvt, Object v) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, Keyval_.new_("v", v)));}
	public static void Pub_vals(Gfo_evt_mgr_owner pub, String pubEvt, Keyval... ary) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, ary));}
	public static void Pub_msg(Gfo_evt_mgr_owner pub, GfsCtx ctx, String pubEvt, GfoMsg m) {pub.Evt_mgr().Pub(ctx, pubEvt, m);}
	public static void Lnk(Gfo_evt_mgr_owner pub, Gfo_evt_mgr_owner sub) {sub.Evt_mgr().Lnk(pub);}
	public static void Rls_pub(Gfo_evt_mgr_owner pub) {pub.Evt_mgr().RlsPub(pub);}
	public static void Rls_sub(Gfo_evt_mgr_owner sub) {sub.Evt_mgr().RlsSub(sub);}
	static GfoMsg msg_(String evt, Keyval... kvAry) {
		GfoMsg m = GfoMsg_.new_cast_(evt);
		for (int i = 0; i < kvAry.length; i++) {
			Keyval kv = kvAry[i];
			m.Add(kv.Key(), kv.Val());
		}
		return m;
	}
}
