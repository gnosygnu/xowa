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
public interface GxwWin extends GxwElem {
	IconAdp IconWin(); void IconWin_set(IconAdp v);

	void ShowWin();
	void HideWin();
	boolean Maximized(); void Maximized_(boolean v);
	boolean Minimized(); void Minimized_(boolean v);
	void CloseWin();
	boolean Pin(); void Pin_set(boolean val);

	void OpenedCmd_set(Gfo_invk_cmd v);
	void TaskbarVisible_set(boolean val);
	void TaskbarParkingWindowFix(GxwElem form);
}
