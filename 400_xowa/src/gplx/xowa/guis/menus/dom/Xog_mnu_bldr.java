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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.gfui.imgs.*;
import gplx.xowa.guis.cmds.*; import gplx.xowa.apps.gfs.*;
public class Xog_mnu_bldr {
	private Xoae_app app; private Gfui_kit kit; private Io_url img_dir;
	public void Init_by_kit(Xoae_app app, Gfui_kit kit, Io_url img_dir) {
		this.app = app; this.kit = kit; this.img_dir = img_dir; 
	}
	public void Build(Gfui_mnu_grp grp_gui, Xog_mnu_grp grp_dom) {
		if (grp_gui == null) return;	// NOTE: hackish, ignore call from user.gfs b/c it fires before kit is inited; note that Xog_popup_mnu_mgr will call html_box's context menu explicitly
		grp_gui.Itms_clear();
		Build_owner(grp_gui, grp_dom);
	}
	private void Build_owner(Gfui_mnu_grp grp_gui, Xog_mnu_base grp_dom) {
		int len = grp_dom.Len();
		for (int i = 0; i < len; i++) {
			Xog_mnu_itm sub_dom = grp_dom.Get_at(i);
			Gfui_mnu_itm sub_gui = null;
			String sub_text = sub_dom.Name(), sub_shortcut = sub_dom.Shortcut();
			switch (sub_dom.Tid()) {
				case Xog_mnu_itm.Tid_spr: sub_gui = grp_gui.Itms_add_separator(); break;
				case Xog_mnu_itm.Tid_btn: sub_gui = Add_btn(grp_gui, sub_dom, sub_text, sub_shortcut); break;
				case Xog_mnu_itm.Tid_chk: sub_gui = Add_chk(grp_gui, sub_dom, sub_text, sub_shortcut); break;
				case Xog_mnu_itm.Tid_rdo: sub_gui = Add_rdo(grp_gui, sub_dom, sub_text, sub_shortcut); break;
				case Xog_mnu_itm.Tid_grp: {
					Gfui_mnu_grp sub_gui_grp = grp_gui.Itms_add_grp(sub_dom.Gui_text(), Get_img(sub_dom.Img_nest()));
					Build_owner(sub_gui_grp, (Xog_mnu_base)sub_dom);
					sub_gui = sub_gui_grp;
					break;
				}
				default: throw Err_.new_unhandled(sub_dom.Tid());
			}
			sub_dom.Under_gui_(sub_gui);
		}
	}
	public ImageAdp Get_img(String[] img_nest) {
		Io_url img_url = img_nest.length == 0 ? Io_url_.Empty : img_dir.GenSubFil_nest(img_nest);
		return Io_mgr.Instance.ExistsFil(img_url) ? kit.New_img_load(img_url) : ImageAdp_null.Instance;	// NOTE: must check if file exists else swt exception; NOTE: must use ImageAdp_null.Instance, not ImageAdp_.Null, else error in non-X11 environments
	}
	private Gfui_mnu_itm Add_btn(Gfui_mnu_grp owner_gui, Xog_mnu_itm sub, String sub_text, String sub_shortcut) {
		String cmd_text = "app.api.exec('" + sub.Key() + "');";
		GfoMsg msg = Xoa_gfs_mgr_.Parse_to_msg(cmd_text);
		ImageAdp img = Get_img(sub.Img_nest());
		return owner_gui.Itms_add_btn_msg(sub.Gui_text(), img, app, app.Gfs_mgr(), msg);
	}
	private Gfui_mnu_itm Add_chk(Gfui_mnu_grp owner_gui, Xog_mnu_itm sub, String sub_text, String sub_shortcut) {
		ImageAdp img = Get_img(sub.Img_nest());
		GfoMsg msg_n = Xoa_gfs_mgr_.Parse_to_msg("app.api.exec('" + sub.Key() + "n_');");
		GfoMsg msg_y = Xoa_gfs_mgr_.Parse_to_msg("app.api.exec('" + sub.Key() + "y_');");
		Gfui_mnu_itm mnu_itm = owner_gui.Itms_add_chk_msg(sub.Gui_text(), img, app, app.Gfs_mgr(), msg_n, msg_y);
		sub.Evt_mgr().Sub(mnu_itm);
		Xog_cmd_itm cmd = app.Gui_mgr().Cmd_mgr().Get_or_null(sub.Key());
		boolean v = Bool_.Cast(app.Gfs_mgr().Run_str_for(app, cmd.Cmd()));
		mnu_itm.Selected_(v);
		return mnu_itm;
	}
//		private void Add_chk(String key, String text, String shortcut, String img) {
//			Xog_mnu_itm rv = Add_itm(Xog_mnu_itm.Tid_chk, key, text, shortcut, img);
//			Xog_cmd_itm cmd = gui_mgr.Cmd_mgr().Regy().Get_or_null(key); if (cmd == null) throw Err_.new_wo_type("unknown cmd; key={0}", key);
//			Gfo_evt_itm pub = gui_mgr.App().Gfs_mgr().Get_owner_as_event_obj(cmd.Cmd());
//			Gfo_evt_mgr_.Sub_same(pub, Xog_mnu_evt_mgr.Evt_selected_changed, rv.Evt_mgr());
//		}
	private Gfui_mnu_itm Add_rdo(Gfui_mnu_grp owner_gui, Xog_mnu_itm sub, String sub_text, String sub_shortcut) {
		ImageAdp img = Get_img(sub.Img_nest());
		GfoMsg msg = Xoa_gfs_mgr_.Parse_to_msg("app.api.exec('" + sub.Key() + "');");
		return owner_gui.Itms_add_rdo_msg(sub.Gui_text(), img, app, app.Gfs_mgr(), msg);
	}
}
