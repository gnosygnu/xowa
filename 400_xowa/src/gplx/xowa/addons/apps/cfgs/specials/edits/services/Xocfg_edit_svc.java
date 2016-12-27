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
package gplx.xowa.addons.apps.cfgs.specials.edits.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.jsons.*;
import gplx.core.gfobjs.*;
import gplx.xowa.guis.cbks.*; import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.specials.edits.objs.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.pages.*; import gplx.xowa.addons.apps.cfgs.mgrs.caches.*;
public class Xocfg_edit_svc {
	private final    Xoa_app app;
	private Xocfg_edit_loader edit_loader;
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xocfg_edit_special.Prototype.Special__meta().Ttl_bry());
	public Xocfg_edit_svc(Xoa_app app) {
		this.app = app;
	}
	public void Upsert(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String val = args.Get_as_str("val");
		String gui = args.Get_as_str("gui");
		if (String_.Has_at_bgn(gui, "gui.binding")) {
			val = gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_binding.To_db_str(val);
		}
		app.Cfg().Set_str(ctx, key, val);

		Xocfg_cache_grp grp = app.Cfg().Cache_mgr().Grps__get_or_load(key);
		if (String_.Eq(grp.Dflt(), val))
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.revert__recv", Gfobj_nde.New().Add_str("key", key).Add_str("val", val));
		else
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.upsert__recv", Gfobj_nde.New().Add_str("key", key));
	}
	public void Revert(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		String gui = args.Get_as_str("gui");
		app.Cfg().Del(ctx, key);
		String val = app.Cfg().Get_str(ctx, key);
		if (String_.Has_at_bgn(gui, "gui.binding")) {
			val = String_.Concat_with_str("|", gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_binding.To_gui(val));
		}
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.revert__recv", Gfobj_nde.New().Add_str("key", key).Add_str("val", val));
	}
	public void Load(Json_nde args) {
		String ctx = args.Get_as_str("ctx");
		String key = args.Get_as_str("key");
		if (edit_loader == null) edit_loader = Xocfg_edit_loader.New(app);
		Xoedit_root root = edit_loader.Load_root(key, ctx, "en");
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.load__recv", root.To_nde(Bry_bfr_.New()));
		app.Cfg().Set_str_app("xowa.app.cfg.recent_page", key);
	}
}
