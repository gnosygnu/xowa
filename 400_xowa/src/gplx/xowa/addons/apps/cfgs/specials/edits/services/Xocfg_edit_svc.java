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
package gplx.xowa.addons.apps.cfgs.specials.edits.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.jsons.*;
import gplx.core.gfobjs.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.specials.edits.objs.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.pages.*; import gplx.xowa.addons.apps.cfgs.mgrs.caches.*; import gplx.xowa.addons.apps.cfgs.enums.*;
public class Xocfg_edit_svc {
	private final    Xoa_app app;
	private Xocfg_edit_loader edit_loader;
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xocfg_edit_special.Prototype.Special__meta().Ttl_bry());
	public Xocfg_edit_svc(Xoa_app app) {
		this.app = app;
	}
	public void Update(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String val = args.Get_as_str("val");
		String type = args.Get_as_str("type");
		if (String_.Has_at_bgn(type, "gui.binding")) {
			val = Xoitm_gui_binding.To_db_str(val);
		}

		Xocfg_cache_grp grp = app.Cfg().Cache_mgr().Grps__get_or_load(key);
		try {
			app.Cfg().Set_str(ctx, key, val);
		} catch (Exception exc) {				
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.update__fail", Gfobj_nde.New().Add_str("key", key).Add_str("new_val", args.Get_as_str("val")).Add_str("old_val", grp.Get(ctx)).Add_str("err", Err_.Message_lang(exc)));
			return;
		}

		if (!String_.Eq(type, "btn") && String_.Eq(grp.Dflt(), val)) {
			if (String_.Has_at_bgn(type, "gui.binding")) {
				val = String_.Concat_with_str("|", gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_binding.To_gui(val));
			}
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.delete__recv", Gfobj_nde.New().Add_str("key", key).Add_str("type", type).Add_str("val", val));
		}
		else
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.update__pass", Gfobj_nde.New().Add_str("key", key).Add_str("type", type));
	}
	public void Delete(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String type = args.Get_as_str("type");
		app.Cfg().Del(ctx, key);
		String val = app.Cfg().Get_str(ctx, key);
		if (String_.Has_at_bgn(type, "gui.binding")) {
			val = String_.Concat_with_str("|", gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_binding.To_gui(val));
		}
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.delete__recv", Gfobj_nde.New().Add_str("key", key).Add_str("val", val));
	}
	public void Select(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		if (edit_loader == null) edit_loader = Xocfg_edit_loader.New(app);
		Xoedit_root root = edit_loader.Load_root(key, ctx, "en");
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.select__recv", root.To_nde(Bry_bfr_.New()));
		app.Cfg().Set_str_app(Xocfg_edit_special.Cfg__previous_grp, key);
	}
}
