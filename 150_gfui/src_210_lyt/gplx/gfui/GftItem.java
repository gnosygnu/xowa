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
public interface GftItem {
	String Key_of_GfuiElem();
	int Gft_h(); GftItem Gft_h_(int v);
	int Gft_w(); GftItem Gft_w_(int v);
	int Gft_x(); GftItem Gft_x_(int v);
	int Gft_y(); GftItem Gft_y_(int v);
	GftItem Gft_rect_(RectAdp rect);
}
class GftItem_mok implements GftItem {
	public String Key_of_GfuiElem() {return "null";}
	public int Gft_h() {return gft_h;} public GftItem Gft_h_(int v) {gft_h = v; return this;} int gft_h;
	public int Gft_w() {return gft_w;} public GftItem Gft_w_(int v) {gft_w = v; return this;} int gft_w;
	public int Gft_x() {return gft_x;} public GftItem Gft_x_(int v) {gft_x = v; return this;} int gft_x;
	public int Gft_y() {return gft_y;} public GftItem Gft_y_(int v) {gft_y = v; return this;} int gft_y;
	public GftItem Gft_rect_(RectAdp rect) {gft_x = rect.X(); gft_y = rect.Y(); gft_w = rect.Width(); gft_h = rect.Height(); return this;}
}
