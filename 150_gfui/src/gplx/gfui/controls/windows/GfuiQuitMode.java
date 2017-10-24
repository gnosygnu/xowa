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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.kits.core.*;
public class GfuiQuitMode {
	public int Val() {return val;} int val;
	GfuiQuitMode(int val) {this.val = val;}
	public static final    GfuiQuitMode ExitApp		= new GfuiQuitMode(1);
	public static final    GfuiQuitMode Destroy		= new GfuiQuitMode(2);
	public static final    GfuiQuitMode Suspend		= new GfuiQuitMode(3);
	public static final    String
		    Destroy_cmd			= "destroy"
		,	Suspend_cmd			= "suspend"
		,	SuspendApp_cmd		= "suspendApp"	// TODO_OLD: merge with suspend; needs Msg Addressing (*.suspend vs app.suspend)
		;
	public static void Exec(Gfo_invk invk, GfuiQuitMode stopMode) {
		int val = stopMode.Val();
		if		(val == GfuiQuitMode.Destroy.Val())		Gfo_invk_.Invk_by_key(invk, GfuiQuitMode.Destroy_cmd);
		else if	(val == GfuiQuitMode.Suspend.Val())		Gfo_invk_.Invk_by_key(invk, GfuiQuitMode.Suspend_cmd);
		else if	(val == GfuiQuitMode.ExitApp.Val())		GfuiEnv_.Exit();
	}
}
