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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.type_xtns.*; import gplx.core.interfaces.*;
import gplx.gfui.controls.standards.*;
public class IptBnd_txt_cmd implements InjectAble, Gfo_invk, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public void Inject(Object owner) {
		txtBox = GfuiTextBox_.cast(owner);
		txtBox.TextAlignH_center_();
		IptBnd_.cmd_to_(IptCfg_.Null, txtBox, this, TxtBox_exec, IptKey_.Enter);
		Gfo_evt_mgr_.Sub_same(fwd, setEvt, this);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, TxtBox_exec))	Gfo_invk_.Invk_by_val(src, setCmd, cls.ParseOrNull(txtBox.Text()));
		else if	(ctx.Match(k, setEvt))			{
			int v = m.ReadInt("v");
			txtBox.Text_(cls.XtoUi(v, ClassXtnPool.Format_null));
		}
		else	return Gfo_invk_.Rv_unhandled;
		return Gfo_invk_.Rv_handled;
	}	static final    String TxtBox_exec = "TxtBox_exec";
	GfuiTextBox txtBox; Gfo_invk src; Gfo_evt_itm fwd; String setCmd, setEvt; ClassXtn cls;		
	public static IptBnd_txt_cmd new_(Gfo_evt_itm fwd, String setCmd, String setEvt, ClassXtn cls) {
		IptBnd_txt_cmd rv = new IptBnd_txt_cmd();
		rv.fwd = fwd; rv.src = (Gfo_invk)fwd;
		rv.setCmd = setCmd; rv.setEvt = setEvt; rv.cls = cls;
		return rv;
	}
}
