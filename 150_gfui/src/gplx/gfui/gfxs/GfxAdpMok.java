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
package gplx.gfui.gfxs; import gplx.*; import gplx.gfui.*;
import gplx.gfui.draws.*; import gplx.gfui.imgs.*;
public class GfxAdpMok implements GfxAdp {
	public GfxItmList SubItms() {return subItms;} GfxItmList subItms = new GfxItmList();
	public void DrawStringXtn(String s, FontAdp font, SolidBrushAdp brush, float x, float y, float width, float height, GfxStringData sd) {
		float[] sizeAry = MeasureStringXtn(s, font, null);
		SizeAdp size = SizeAdp_.new_((int)sizeAry[0], (int)sizeAry[1]);
		GfxStringItm str = GfxStringItm.new_(PointAdp_.new_((int)x, (int)y), size, s, font, brush);
		subItms.Add(str);
	}
	public void DrawRect(PenAdp pen, PointAdp location, SizeAdp size) {this.DrawRect(pen, location.X(), location.Y(), size.Width(), size.Height());}
	public void DrawRect(PenAdp pen, RectAdp rect) {this.DrawRect(pen, rect.X(), rect.Y(), rect.Width(), rect.Height());}
	public void DrawRect(PenAdp pen, int x, int y, int width, int height) {
		GfxRectItm rect = GfxRectItm.new_(PointAdp_.new_(x, y), SizeAdp_.new_(width, height), pen.Width(), pen.Color());
		subItms.Add(rect);
	}
	public void DrawLine(PenAdp pen, PointAdp src, PointAdp trg) {
		GfxLineItm line = GfxLineItm.new_(src, trg, pen.Width(), pen.Color());
		subItms.Add(line);
	}
	public void DrawImage(ImageAdp image, PointAdp location) {
		// gfx.DrawImage(image, width, height);
	}
	public void DrawImage(ImageAdp img, int trg_x, int trg_y, int trg_w, int trg_h, int src_x, int src_y, int src_w, int src_h) {
		// gfx.DrawImage(image, dst, src, GraphicsUnit.Pixel);
	}
	public void FillRect(SolidBrushAdp brush, int x, int y, int width, int height) {
		// gfx.FillRect(brush, x, y, width, height);
	}
	public float[] MeasureStringXtn(String s, FontAdp font, GfxStringData str) {return new float[] {13 * String_.Len(s), 17};}
	public void Rls() {}
	public static GfxAdpMok new_() {return new GfxAdpMok();} GfxAdpMok() {}
}
