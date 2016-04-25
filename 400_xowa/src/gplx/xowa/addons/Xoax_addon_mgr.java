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
public class Xoax_addon_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	// THREAD: must synchronized else two search tabs will fail on startup
	public Xoax_addon_itm	Itms__get_or_null(byte[] key) {synchronized (hash) {return (Xoax_addon_itm)hash.Get_by(key);}}
	public void				Itms__add_many(Xoax_addon_itm... ary) {
		for (Xoax_addon_itm itm : ary)
			Itms__add(itm);
	}
	public void				Itms__add(Xoax_addon_itm itm) {
		synchronized (hash) {
			Xoa_app_.Usr_dlg().Log_many("", "", "addons.init: ~{0}", itm.Addon__key());
			hash.Add(itm.Addon__key(), itm);
		}
	}
	public Xoax_addon_mgr Add_dflts_by_app(Xoa_app app) {
		app.Bldr().Cmd_regy().Add_many
		( gplx.xowa.bldrs.cmds.utils.Xob_alert_cmd.Prototype
		);
		app.Addon_mgr().Itms__add_many
		// bldrs
		( new gplx.xowa.addons.builds.files				.Xoax_builds_files_addon()
		, new gplx.xowa.addons.builds.pagelinks			.Xoax_builds_pagelinks_addon()
		, new gplx.xowa.addons.builds.utils_rankings	.Xoax_builds_utils_rankings_addon()
		, new gplx.xowa.addons.apps.searchs				.Xoax_builds_search_addon()
		, new gplx.xowa.addons.updates.files			.Xoax_updates_files_addon()
		, new gplx.xowa.addons.builds.htmls				.Html__dump_to_fsys__addon()

		// specials
		, new gplx.xowa.addons.apps.file_browsers		.Fbrow_addon()
		, new gplx.xowa.addons.updates.downloads		.Xoax_downloads_addon()

		// jsons
		, new gplx.xowa.addons.servers.https			.Xoax_long_poll_addon()
		);
		return this;
	}
	public void Run_by_app(Xoa_app app) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Xoax_addon_itm addon = (Xoax_addon_itm)hash.Get_at(i);
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__bldr.class)) {
				Xoax_addon_itm__bldr addon_bldr = (Xoax_addon_itm__bldr)addon;
				app.Bldr().Cmd_regy().Add_many(addon_bldr.Cmds_ary());
			}
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__special.class)) {
				Xoax_addon_itm__special addon_sp = (Xoax_addon_itm__special)addon;
				app.Special_regy().Add_many(addon_sp.Pages_ary());
			}
			if (Type_adp_.Implements_intf_obj(addon, Xoax_addon_itm__json.class)) {
				Xoax_addon_itm__json addon_json = (Xoax_addon_itm__json)addon;
				gplx.xowa.htmls.bridges.Bridge_cmd_itm[] json_cmds = addon_json.Json__cmds();
				for (gplx.xowa.htmls.bridges.Bridge_cmd_itm json_cmd : json_cmds)
					app.Html__bridge_mgr().Cmd_mgr().Add(json_cmd);
			}
		}
		app.Gui__cbk_mgr().Reg(gplx.xowa.addons.servers.https.Xog_cbk_wkr__http.Instance);
	}
}
