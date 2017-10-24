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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.controls.windows.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.customs.*;
public class GfoFactory_gfui {
	public static void Btn_MinWin(GfuiElem owner, GfoMsg appWinMsg) {
		GfuiBtn_.msg_("minWin", owner, GfoMsg_.chain_(appWinMsg, GfuiWin.Invk_Minimize)).Text_("_").TipText_("minmize window").Width_(20);
	}
	public static void Btn_MinWin2(GfuiElem owner) {
		GfuiBtn_.msg_("minWin", owner, GfoMsg_.root_(".", GfuiElemBase.Invk_OwnerWin_cmd, GfuiWin.Invk_Minimize)).Text_("_").TipText_("minmize window").Width_(20);
	}
	public static void Btn_MoveBox(GfuiElem owner, GfuiElem target) {
		GfuiElem rv = GfuiBtn_.new_("moveBox").Owner_(owner).Text_("*").TipText_("move box").Width_(20);
		GfuiMoveElemBnd bnd = GfuiMoveElemBnd.new_();
		bnd.TargetElem_set(target);
		rv.Inject_(bnd);			
	}
	public static GfuiBtn Btn_QuitWin3(GfuiElem owner) {
		return (GfuiBtn)GfuiBtn_.msg_("quitWin", owner, GfoMsg_.root_(".", GfuiElemBase.Invk_OwnerWin_cmd, GfuiWin.Invk_Quit)).Text_("X").TipText_("quit win").Width_(20);
	}
	public static void Btn_QuitWin2(GfuiElem owner, GfoMsg quitMsg) {
		GfuiBtn_.msg_("quitWin", owner, quitMsg).Text_("X").TipText_("quit win").Width_(20);
	}
	public static final    GfoFactory_gfui Instance = new GfoFactory_gfui(); GfoFactory_gfui() {}
}
