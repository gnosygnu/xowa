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
public class ScreenAdp {
	public int Index() {return index;} int index;
	public RectAdp Rect() {return bounds;} RectAdp bounds = RectAdp_.Zero;
	public SizeAdp Size() {return bounds.Size();}
	public int Width() {return bounds.Width();}
	public int Height() {return bounds.Height();}		
	public PointAdp Pos() {return bounds.Pos();}
	public int X() {return bounds.X();}
	public int Y() {return bounds.Y();}
			
	@gplx.Internal protected ScreenAdp(int index, RectAdp bounds) {
		this.index = index; this.bounds = bounds;
	}
}
