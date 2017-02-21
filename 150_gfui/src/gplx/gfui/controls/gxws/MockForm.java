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
