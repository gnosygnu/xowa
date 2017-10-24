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
package gplx.gfui.envs; import gplx.*; import gplx.gfui.*;
import gplx.gfui.controls.gxws.*;
public class CursorAdp {
	public static PointAdp Pos() {
		if (testing) return testing_pos;
		return GxwCore_lang.XtoPointAdp(java.awt.MouseInfo.getPointerInfo().getLocation());
	}
	public static void Pos_set(PointAdp p) {
		if (testing)	// while in testing mode, never set Cursor.Position
			testing_pos = p;
				else {
			java.awt.Robot robot = null;
			try {robot = new java.awt.Robot();}
			catch (java.awt.AWTException e) {throw Err_.new_exc(e, "ui", "cursor pos set failed");}
			robot.mouseMove(p.X(), p.Y());
		}
			}		
	@gplx.Internal protected static void Pos_set_for_tests(PointAdp point) {
		testing = point != PointAdp_.Null;
		testing_pos = point;
	}	static PointAdp testing_pos = PointAdp_.Null; static boolean testing = false;
}
