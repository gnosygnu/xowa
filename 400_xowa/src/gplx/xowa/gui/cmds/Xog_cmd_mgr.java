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
package gplx.xowa.gui.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.xowa.apis.xowa.*;
public class Xog_cmd_mgr {
	public void Init_by_kit(Xoa_app app) {
		invk_mgr.Ctor(app, this);
		Load_ctg_msgs(app);
		Load_cmd_msgs(app);
	}
	public Xog_cmd_mgr_invk Invk_mgr() {return invk_mgr;} private Xog_cmd_mgr_invk invk_mgr = new Xog_cmd_mgr_invk();
	private void Load_ctg_msgs(Xoa_app app) {
		Xog_cmd_ctg[] ary = Xog_ctg_itm_.Ary;
		int len = ary.length;
		Xol_lang lang = app.User().Lang();
		for (int i = 0; i < len; i++) {
			Xog_cmd_ctg itm = ary[i];
			itm.Name_(Pf_msg_mgr.Get_msg_val_gui_or_null(lang, Xog_cmd_itm_.Msg_pre_ctg, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_name));
		}
	}
	private void Load_cmd_msgs(Xoa_app app) {
		int len = this.Len();
		Xol_lang lang = app.User().Lang();
		for (int i = 0; i < len; i++) {
			Xog_cmd_itm itm = this.Get_at(i);
			itm.Name_(Pf_msg_mgr.Get_msg_val_gui_or_null(lang, Xog_cmd_itm_.Msg_pre_api, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_name));
			itm.Tip_(Pf_msg_mgr.Get_msg_val_gui_or_null(lang, Xog_cmd_itm_.Msg_pre_api, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_tip));
		}
	}
	public int Len() {return Xog_cmd_itm_.Regy_len();}
	public Xog_cmd_itm Get_at(int i) {return Xog_cmd_itm_.Regy_get_at(i);}
	public Xog_cmd_itm Get_or_null(String key) {return Xog_cmd_itm_.Regy_get_or_null(key);}
	public Xog_cmd_itm Get_or_make(String key) {
		Xog_cmd_itm rv = Xog_cmd_itm_.Regy_get_or_null(key);
		if (rv == null) {
			rv = new Xog_cmd_itm(key, Xog_ctg_itm_.Itm_custom, null);	// pass null for cmd; will be filled in
			Xog_cmd_itm_.Regy_add(rv);
		}
		return rv;
	}
//		public Xog_cmd_regy Regy() {return regy;} private Xog_cmd_regy regy = new Xog_cmd_regy();
}
