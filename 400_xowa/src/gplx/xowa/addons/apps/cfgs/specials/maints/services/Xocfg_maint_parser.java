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
package gplx.xowa.addons.apps.cfgs.specials.maints.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.maints.*;
import gplx.langs.gfs.*;
class Xocfg_maint_parser {
	public Xocfg_maint_nde[] Parse(String raw) {
		GfoMsg root = Gfs_msg_bldr.Instance.ParseToMsg(raw);

		int len = root.Subs_count();
		Xocfg_maint_nde[] rv = new Xocfg_maint_nde[len];
		for (int i = 0; i < len; i++) {
			GfoMsg msg = root.Subs_getAt(i);
			rv[i] = Parse_nde(msg);
		}
		return rv;
	}
	private Xocfg_maint_nde Parse_nde(GfoMsg msg) {
		Ordered_hash hash = To_atr_hash(msg);
		// get common atrs
		int id			= Int_.parse(Get_atr_as_str_or_fail(hash, "id_"));
		String owner	= Get_atr_as_str_or_fail(hash, "owner_");
		String key		= Get_atr_as_str_or_fail(hash, "key_");
		String name		= Get_atr_as_str_or_fail(hash, "name_");
		String help		= Get_atr_as_str_or_fail(hash, "help_");

		// create
		String nde_type = msg.Key();
		if (String_.Eq(nde_type, "grp")) {
			return new Xocfg_maint_grp(owner, id, key, name, help);
		}
		else if (String_.Eq(nde_type, "itm")) {
			String scope		= Get_atr_as_str_or(hash, "scope_", ""); if (String_.Eq(scope, "")) scope = "wiki";
			String type			= Get_atr_as_str_or_fail(hash, "type_");
			String dflt			= Get_atr_as_str_or_fail(hash, "dflt_");
			String html_atrs	= Get_atr_as_str_or(hash, "html_atrs_", "");
			String html_cls		= Get_atr_as_str_or(hash, "html_cls_", "");
			return new Xocfg_maint_itm(owner, id, key, name, help, scope, type, dflt, html_atrs, html_cls);
		}
		else throw Err_.new_wo_type("xo.cfg_maint:unknown type", "type", nde_type);
	}
	private static Ordered_hash To_atr_hash(GfoMsg msg) {
		Ordered_hash rv = Ordered_hash_.New();
		int len = msg.Subs_count();
		for (int i = 0; i < len; i++) {
			GfoMsg sub = msg.Subs_getAt(i);
			rv.Add(sub.Key(), sub.Args_getAt(0).Val());
		}
		return rv;
	}
	private static String Get_atr_as_str_or_fail(Ordered_hash hash, String key) {			
		String rv = Get_atr_as_str_or(hash, key, null);
		if (rv == null) throw Err_.new_wo_type("xo.cfg_maint:missing key", "key", key);
		return rv;
	}
	private static String Get_atr_as_str_or(Ordered_hash hash, String key, String or) {
		String val = (String)hash.Get_by(key);
		return val == null ? or : val;
	}
}
