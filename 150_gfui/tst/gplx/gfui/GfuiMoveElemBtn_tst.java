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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class GfuiMoveElemBtn_tst {
	@Before public void setup() {
		form = GfuiWin_.app_("form"); form.Size_(100, 100);
		moveBtn = GfuiBtn_.new_("moveBtn");
		GfuiMoveElemBnd bnd = GfuiMoveElemBnd.new_(); bnd.TargetElem_set(form);
		moveBtn.IptBnds().Add(bnd);
	}
	@Test  public void Basic() {
		Tfds.Eq(form.X(), 0);
		IptEventMgr.ExecKeyDown(moveBtn, IptEvtDataKey.test_(MoveRightArg()));
		Tfds.Eq(form.X(), 10);
	}

	IptKey MoveRightArg() {return IptKey_.Ctrl.Add(IptKey_.Right);}
	GfuiWin form; GfuiBtn moveBtn;
}
