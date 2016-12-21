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
package gplx.xowa.addons; import gplx.*; import gplx.xowa.*;
import gplx.xowa.addons.wikis.searchs.gui.urlbars.*; import gplx.xowa.addons.wikis.searchs.gui.htmlbars.*; import gplx.xowa.addons.wikis.searchs.specials.*;
public class Xoax_addon_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New(); // LOCK: must synchronized else two search tabs will fail on startup
	public Xoax_addon_itm	Itms__get_or_null(String key) {synchronized (hash) {return (Xoax_addon_itm)hash.Get_by(key);}}
	public void				Itms__add_many(Xoax_addon_itm... ary) {
		for (Xoax_addon_itm itm : ary)
			Itms__add(itm);
	}
	public void				Itms__add(Xoax_addon_itm itm) {
		synchronized (hash) {
			String addon_key = itm.Addon__key();
			hash.Add(addon_key, itm);
			// Xoa_app_.Usr_dlg().Log_many("", "", "addons.init: ~{0}", addon_key);
		}
	}
	// HACK: should make separate generic app-level container
	public Srch_urlbar_mgr Itms__search__urlbar()	{return itms__search__urlbar;} private final    Srch_urlbar_mgr itms__search__urlbar = new Srch_urlbar_mgr();
	public Srch_htmlbar_mgr Itms__search__htmlbar()	{return itms__search__htmlbar;} private final    Srch_htmlbar_mgr itms__search__htmlbar = new Srch_htmlbar_mgr();
	public Srch_special_cfg Itms__search__special()	{return itms__search__special;} private final    Srch_special_cfg itms__search__special = new Srch_special_cfg();
	public void Init_by_kit(Xoae_app app, gplx.gfui.kits.core.Gfui_kit kit) {
		itms__search__urlbar.Init_by_kit(app, kit);
		itms__search__htmlbar.Init_by_kit(app, kit);
		itms__search__special.Init_by_kit(app, kit);
	}
	public Xoax_addon_mgr Add_dflts_by_app(Xoa_app app) {
		app.Bldr().Cmd_regy().Add_many
		( gplx.xowa.bldrs.cmds.utils.Xob_alert_cmd.Prototype
		);
		app.Addon_mgr().Itms__add_many
		// bldrs
		( new gplx.xowa.addons.bldrs.files				.Xoax_builds_files_addon()
		, new gplx.xowa.addons.bldrs.wmdumps.pagelinks	.Xoax_builds_pagelinks_addon()
		, new gplx.xowa.addons.bldrs.wmdumps.imglinks	.Imglnk_addon()
		, new gplx.xowa.addons.bldrs.utils_rankings		.Xoax_builds_utils_rankings_addon()
		, new gplx.xowa.addons.wikis.searchs			.Xoax_builds_search_addon()
		, new gplx.xowa.addons.bldrs.updates.files		.Xodel_addon()
		, new gplx.xowa.addons.bldrs.htmls				.Html__dump_to_fsys__addon()
		, new gplx.xowa.addons.bldrs.exports			.Export_addon()
		, new gplx.xowa.addons.wikis.pages.randoms		.Rndm_addon()
		, new gplx.xowa.addons.bldrs.hdumps.diffs		.Dumpdiff_addon()
		, new gplx.xowa.addons.wikis.ctgs.bldrs			.Xoax_ctg_bldr_addon()
		, new gplx.xowa.xtns.wbases.imports				.Xowb_bldr_addon()

		// specials
		, new gplx.xowa.addons.wikis.registrys			.Wiki_registry_addon()
		, new gplx.xowa.addons.wikis.imports			.Xow_import_addon()
		, new gplx.xowa.addons.bldrs.xodirs				.Xobc_xodir_addon()
		, new gplx.xowa.addons.bldrs.centrals			.Xobc_task_addon()
		, new gplx.xowa.addons.apps.helps.logs			.Xolog_addon()
		, new gplx.xowa.addons.wikis.pages.syncs		.Xosync_addon()
		, new gplx.xowa.addons.users.wikis.regys		.Xou_regy_addon()
		, new gplx.xowa.addons.apps.cfgs				.Xoa_cfg_addon()
		, new gplx.xowa.addons.apps.updates				.Xoa_update_addon()

		// jsons
		);
		if (app.Mode().Tid_is_http()) {
			app.Addon_mgr().Itms__add_many(new gplx.xowa.addons.servers.https.Xoax_long_poll_addon());
		}

		return this;
	}
	public void Run_by_app(Xoa_app app) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Xoax_addon_itm addon = (Xoax_addon_itm)hash.Get_at(i);

			// init
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__init.class)) {
				Xoax_addon_itm__init addon_init = (Xoax_addon_itm__init)addon;
				addon_init.Init_addon_by_app(app);
				init_list.Add(addon_init);
			}

			// add bldr cmds
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__bldr.class)) {
				Xoax_addon_itm__bldr addon_bldr = (Xoax_addon_itm__bldr)addon;
				app.Bldr().Cmd_regy().Add_many(addon_bldr.Bldr_cmds());
			}

			// add special pages
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__special.class)) {
				Xoax_addon_itm__special addon_sp = (Xoax_addon_itm__special)addon;
				app.Special_regy().Add_many(addon_sp.Special_pages());
			}

			// add json mgrs
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__json.class)) {
				Xoax_addon_itm__json addon_json = (Xoax_addon_itm__json)addon;
				gplx.xowa.htmls.bridges.Bridge_cmd_itm[] json_cmds = addon_json.Json_cmds();
				for (gplx.xowa.htmls.bridges.Bridge_cmd_itm json_cmd : json_cmds) {
					json_cmd.Init_by_app(app);
					app.Html__bridge_mgr().Cmd_mgr().Add(json_cmd);
				}
			}
		}
	}
	private boolean init_cfg = true;	// WORKAROUND:move cfg to separate proc instead of lazy-loading on 1st wiki
	public void Load_by_wiki(Xow_wiki wiki) {
		if (init_cfg) {
			init_cfg = false;
			gplx.xowa.bldrs.filters.dansguardians.Dg_match_mgr.Cfg__reg(wiki.App());
		}

		int len = init_list.Len();
		for (int i = 0; i < len; ++i) {
			Xoax_addon_itm__init itm = (Xoax_addon_itm__init)init_list.Get_at(i);
			itm.Init_addon_by_wiki(wiki);
		}
	}
	private final    List_adp init_list = List_adp_.New();
}
