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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.ipts.*; import gplx.xowa.guis.cmds.*;
interface Xog_bnd_wkr {
	void Bind_ipt_to_box(String box, String ipt);
}
class Xog_bnd_wkr__null implements Xog_bnd_wkr {
	public void Bind_ipt_to_box(String box, String ipt) {}
}
class Xog_bnd_temp implements Gfo_invk {
	private Xoae_app app;
	private final    Ordered_hash regy = Ordered_hash_.New();
	private final    Xog_bnd_wkr bnd_wkr = new Xog_bnd_wkr__null();
	private Xog_bnd_box[] boxs = Xog_bnd_box_.Ary();
	public void Init_by_app(Xoae_app app) {
		this.app = app;
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y	, Xog_bnd_box_.Tid_browser				, "mod.c+key.t");
	}
	private void Init_itm(String cmd, int box, String... ipts) {
		int ipts_len = ipts.length;
		for (int i = 0; i < ipts_len; i++) {
			String ipt_str = ipts[i];
			Init_itm(cmd, i, box, IptArg_.parse_or_none_(ipt_str));
		}
	}
	// private void Init_itm(String cmd, int idx, int box, String ipt) {Init_itm(cmd, idx, box, IptArg_.parse_or_none_(ipt));}
	private void Init_itm(String cmd, int idx, int box, IptArg ipt) {
		String key = cmd + "-" + Int_.To_str(idx + List_adp_.Base1);		// EX: xowa.widgets.url.focus-1 xowa.widgets.url.focus-2
		Xog_bnd_itm itm = new Xog_bnd_itm(key, Bool_.Y, cmd, box, ipt);
		boxs[box].Add(itm);
		regy.Add(itm.Key(), itm);
		app.Cfg().Bind_many_app(this, cmd);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Xog_bnd_cfg_itm itm = Xog_bnd_cfg_itm.Parse(m.ReadStr("v"));
		bnd_wkr.Bind_ipt_to_box(itm.Box(), itm.Ipt());
		return this;
	}
}
class Xog_bnd_cfg_itm {
	public Xog_bnd_cfg_itm(String box, String ipt) {
		this.box = box;
		this.ipt = ipt;
	}
	public String Box() {return box;} private final    String box;
	public String Ipt() {return ipt;} private final    String ipt;
	public static Xog_bnd_cfg_itm Parse(String s) {
		String[] parts = String_.Split(s, "|");
		return new Xog_bnd_cfg_itm(parts[0], parts[1]);
	}
}
