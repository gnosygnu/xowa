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
package gplx.xowa.addons.apps.cfgs.mgrs.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
public class Xocfg_cache_grp {
	private final    Hash_adp vals = Hash_adp_.New();
	private final    Ordered_hash subs = Ordered_hash_.New();
	public Xocfg_cache_grp(String key, String dflt, String data_type) {
		this.key = key;
		this.dflt = dflt;
		this.data_type = data_type;
	}
	public String Key() {return key;} private final    String key;
	public String Data_type() {return data_type;} private final    String data_type;
	public String Dflt() {return dflt;} private String dflt;
	public void Dflt_(String v) {this.dflt = v;}
	public String Get(String ctx) {
		// exact match; EX: "en.w|key_1"
		Xocfg_cache_itm rv = (Xocfg_cache_itm)vals.Get_by(ctx);
		if (rv != null) return rv.Val();

		// global match; EX: "app|key_1"
		rv = (Xocfg_cache_itm)vals.Get_by(Xocfg_mgr.Ctx__app);
		if (rv != null) return rv.Val();

		// dflt
		return dflt;
	}
	public void Set(String ctx, String val) {
		Xocfg_cache_itm rv = (Xocfg_cache_itm)vals.Get_by(ctx);
		if (rv == null) {
			rv = new Xocfg_cache_itm(ctx, key, val);
			vals.Add(ctx, rv);
		}
		else {
			rv.Val_(val);
		}
	}
	public void Del(String ctx) {
		vals.Del(ctx);
	}
	public void Add(String ctx, Xocfg_cache_itm itm) {
		vals.Add(ctx, itm);
	}
	public void Sub(Gfo_invk sub, String ctx, String evt) {
		List_adp list = (List_adp)subs.Get_by(ctx);
		if (list == null) {
			list = List_adp_.New();
			subs.Add(ctx, list);
		}
		list.Add(new Xocfg_cache_sub(sub, ctx, evt, key));
	}
	public void Pub(String ctx, String val) {
		// exact match; EX: "en.w|key_1"
		List_adp list = (List_adp)subs.Get_by(ctx);
		if (list == null) {// global match; EX: "app|key_1"
			int len = subs.Len();
			for (int i = 0; i < len; i++) {
				list = (List_adp)subs.Get_at(i);
				Pub(list, val);
			}
		}
		if (list != null)	// NOTE: check for null as it's possible for no subscribers
			Pub(list, val);
	}
	private void Pub(List_adp list, String val) {
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Xocfg_cache_sub sub = (Xocfg_cache_sub)list.Get_at(i);
			Gfo_invk_.Invk_by_msg(sub.Sub(), sub.Evt(), GfoMsg_.new_parse_(sub.Evt()).Add("v", val));
		}
	}
}
