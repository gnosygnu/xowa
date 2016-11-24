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
