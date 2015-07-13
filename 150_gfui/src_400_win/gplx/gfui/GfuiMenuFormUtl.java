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
public class GfuiMenuFormUtl {
	public static PointAdp CalcShowPos(RectAdp ownerRect, SizeAdp elemSize) {
//#if plat_wce
//			int x = ownerRect.X() + ownerRect.Width() - elemSize.Width();
//			int y = ownerRect.Y() + ownerRect.Height() - elemSize.Height();
//#else
		int x = CursorAdp.Pos().X();
		int y = CursorAdp.Pos().Y();
		// check if entire elem fits inside owner at x,y; if not, align to ownerEdge
		int ownerMinX = ownerRect.X();
		int ownerMinY = ownerRect.Y();
		int ownerMaxX = ownerRect.X() + ownerRect.Width();
		int ownerMaxY = ownerRect.Y() + ownerRect.Height();
		if (x < ownerMinX) x = ownerMinX;
		if (y < ownerMinY) y = ownerMinY;
		if (x + elemSize.Width() > ownerMaxX) x = ownerMaxX - elemSize.Width();
		if (y + elemSize.Height() > ownerMaxY) y = ownerMaxY - elemSize.Height();
//#endif
		return PointAdp_.new_(x, y);
	}
}
