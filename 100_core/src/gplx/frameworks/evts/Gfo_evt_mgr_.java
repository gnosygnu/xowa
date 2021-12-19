/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.frameworks.evts;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfoMsg_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.commons.KeyVal;
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
	public static void Pub_obj(Gfo_evt_mgr_owner pub, String pubEvt, String key, Object v) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, KeyVal.NewStr(key, v)));}
	public static void Pub_val(Gfo_evt_mgr_owner pub, String pubEvt, Object v) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, KeyVal.NewStr("v", v)));}
	public static void Pub_vals(Gfo_evt_mgr_owner pub, String pubEvt, KeyVal... ary) {pub.Evt_mgr().Pub(GfsCtx.new_(), pubEvt, msg_(pubEvt, ary));}
	public static void Pub_msg(Gfo_evt_mgr_owner pub, GfsCtx ctx, String pubEvt, GfoMsg m) {pub.Evt_mgr().Pub(ctx, pubEvt, m);}
	public static void Lnk(Gfo_evt_mgr_owner pub, Gfo_evt_mgr_owner sub) {sub.Evt_mgr().Lnk(pub);}
	public static void Rls_pub(Gfo_evt_mgr_owner pub) {pub.Evt_mgr().RlsPub(pub);}
	public static void Rls_sub(Gfo_evt_mgr_owner sub) {sub.Evt_mgr().RlsSub(sub);}
	static GfoMsg msg_(String evt, KeyVal... kvAry) {
		GfoMsg m = GfoMsg_.new_cast_(evt);
		for (int i = 0; i < kvAry.length; i++) {
			KeyVal kv = kvAry[i];
			m.Add(kv.KeyToStr(), kv.Val());
		}
		return m;
	}
}
