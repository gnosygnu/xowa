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
public interface GfxAdp extends RlsAble {
	void DrawLine(PenAdp pen, PointAdp src, PointAdp trg);
	void DrawRect(PenAdp pen, int x, int y, int width, int height);
	void DrawRect(PenAdp pen, PointAdp location, SizeAdp size);
	void DrawRect(PenAdp pen, RectAdp rect);
	void FillRect(SolidBrushAdp brush, int x, int y, int width, int height);
	void DrawImage(ImageAdp image, PointAdp location);
	void DrawImage(ImageAdp img, int trg_x, int trg_y, int trg_w, int trg_h, int src_x, int src_y, int src_w, int src_h);
	void DrawStringXtn(String s, FontAdp font, SolidBrushAdp brush, float x, float y, float width, float height, GfxStringData sd);
	float[] MeasureStringXtn(String s, FontAdp font, GfxStringData sd);
}
