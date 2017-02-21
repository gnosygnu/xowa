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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.interfaces.*;
import gplx.gfui.ipts.*; import gplx.gfui.controls.windows.*;
public class GfuiStatusBarBnd implements InjectAble {
	public GfuiStatusBar Bar() {return statusBar;} GfuiStatusBar statusBar = GfuiStatusBar.new_();
	public void Inject(Object owner) {
		GfuiWin form = GfuiWin_.as_(owner); if (form == null) throw Err_.new_type_mismatch(GfuiWin.class, owner);
		statusBar.Owner_(form, "statusBar");
		IptBnd_.cmd_to_(IptCfg_.Null, form, statusBar, GfuiStatusBar.StatusBarFocus_cmd, IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.T));
		statusBar.MoveButton().TargetElem_set(form);
	}
	public static GfuiStatusBarBnd new_() {return new GfuiStatusBarBnd();} GfuiStatusBarBnd() {}
}
