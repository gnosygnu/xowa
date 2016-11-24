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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.PointAdp;
import gplx.gfui.RectAdp;
import gplx.gfui.SizeAdp;
import gplx.gfui.controls.elems.GfuiElem;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public class Swt_control_ {
	public static void X_set(Control c, int v) {Point point = c.getLocation(); 	c.setLocation(v, point.y);}	
	public static void Y_set(Control c, int v) {Point point = c.getLocation(); 	c.setLocation(point.x, v);}
	public static void W_set(Control c, int v) {Point point = c.getSize();		c.setSize(v, point.y);}
	public static void H_set(Control c, int v) {Point point = c.getSize();		c.setSize(point.x, v);}
	public static void Pos_set(Control c, PointAdp v) 		{c.setLocation(v.X(), v.Y());}
	public static void Pos_set(Control c, int x, int y) 	{c.setLocation(x, y);}
	public static void Size_set(Control c, SizeAdp v) 		{c.setSize(v.Width(), v.Height());}
	public static void Size_set(Control c, int w, int h) 	{c.setSize(w, h);}
	public static void Rect_set(Control c, RectAdp v) 		{c.setBounds(Xto_rectangle(v));}
	public static void Rect_set(Control c, int x, int y, int w, int h) {c.setBounds(Xto_rectangle(x, y, w, h));}
	public static void Rect_add(Control c, RectAdp v, int x, int y, int w, int h) {c.setBounds(Xto_rectangle(v.X() + x, v.Y() + y, v.Width() + w, v.Height()+ h));}
	public static Rectangle Xto_rectangle(int x, int y, int w, int h) 	{return new Rectangle(x, y, w, h);}
	public static Rectangle Xto_rectangle(RectAdp v) 					{return new Rectangle(v.X(), v.Y(), v.Width(), v.Height());}
	public static Swt_control cast_or_fail(GfuiElem elem) {return (Swt_control)elem.UnderElem();}
}
