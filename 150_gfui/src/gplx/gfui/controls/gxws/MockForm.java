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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.imgs.*;
public class MockForm extends GxwElem_mock_base implements GxwWin {
	public IconAdp IconWin() {return null;} public void IconWin_set(IconAdp v) {}
	public void ShowWin() {}
	public void CloseWin() {}
	public void HideWin() {}
	public boolean Maximized() {return false;} public void Maximized_(boolean v) {}
	public boolean Minimized() {return false;} public void Minimized_(boolean v) {}
	public boolean Pin() {return pin;} public void Pin_set(boolean val) {pin = val;} private boolean pin;
	public void OpenedCmd_set(Gfo_invk_cmd v) {}
	public void TaskbarVisible_set(boolean val) {}
	public void TaskbarParkingWindowFix(GxwElem form) {}
	public static final    MockForm Instance = new MockForm(); MockForm() {}
}
