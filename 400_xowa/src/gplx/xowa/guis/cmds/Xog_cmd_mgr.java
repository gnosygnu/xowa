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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class Xog_cmd_mgr {
	public void Init_by_kit(Xoae_app app) {
		invk_mgr.Ctor(app, this);
		Load_ctg_msgs(app);
		Load_cmd_msgs(app);
	}
	public Xog_cmd_mgr_invk Invk_mgr() {return invk_mgr;} private Xog_cmd_mgr_invk invk_mgr = new Xog_cmd_mgr_invk();
	private void Load_ctg_msgs(Xoae_app app) {
		Xog_cmd_ctg[] ary = Xog_ctg_itm_.Ary;
		int len = ary.length;
		Xol_lang_itm lang = app.Usere().Lang();
		for (int i = 0; i < len; i++) {
			Xog_cmd_ctg itm = ary[i];
			itm.Name_(Xol_msg_mgr_.Get_msg_val_gui_or_null(app.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_ctg, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_name));
		}
	}
	private void Load_cmd_msgs(Xoae_app app) {
		int len = this.Len();
		Xol_lang_itm lang = app.Usere().Lang();
		for (int i = 0; i < len; i++) {
			Xog_cmd_itm itm = this.Get_at(i);
			itm.Name_(Xol_msg_mgr_.Get_msg_val_gui_or_null(app.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_api, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_name));
			itm.Tip_(Xol_msg_mgr_.Get_msg_val_gui_or_null(app.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_api, itm.Key_bry(), Xog_cmd_itm_.Msg_suf_tip));
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
