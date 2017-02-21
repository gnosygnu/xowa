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
import gplx.gfui.envs.*;
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
