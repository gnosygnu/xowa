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
			catch (java.awt.AWTException e) {throw Err_.err_key_(e, GfuiEnv_.Err_GfuiException, "cursor pos set failed");}
			robot.mouseMove(p.X(), p.Y());
		}
			}		
	@gplx.Internal protected static void Pos_set_for_tests(PointAdp point) {
		testing = point != PointAdp_.Null;
		testing_pos = point;
	}	static PointAdp testing_pos = PointAdp_.Null; static boolean testing = false;
}
