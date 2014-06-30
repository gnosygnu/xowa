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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
public class GfxAdpBase implements GfxAdp {
	public void DrawLine(PenAdp pen, PointAdp src, PointAdp trg) {
				gfx.setColor(ColorAdpCache._.GetNativeColor(pen.Color()));
		gfx.setStroke(pen.UnderStroke());
		gfx.drawLine(src.X(), src.Y(), trg.X(), trg.Y());
			}
	public void DrawRect(PenAdp pen, PointAdp pos, SizeAdp size)			{this.DrawRect(pen, pos.X(), pos.Y(), size.Width(), size.Height());}
	public void DrawRect(PenAdp pen, RectAdp rect)							{this.DrawRect(pen, rect.X(), rect.Y(), rect.Width(), rect.Height());}
	public void DrawRect(PenAdp pen, int x, int y, int width, int height)	{
				gfx.setPaint(ColorAdpCache._.GetNativeColor(pen.Color()));
		gfx.setStroke(pen.UnderStroke());
		gfx.drawRect(x, y, width, height);
			}
	public void FillRect(SolidBrushAdp brush, int x, int y, int width, int height) {
				gfx.setPaint(ColorAdpCache._.GetNativeColor(brush.Color()));
		gfx.fillRect(x, y, width, height);
			}
	public void DrawStringXtn(String s, FontAdp font, SolidBrushAdp brush, float x, float y, float width, float height, GfxStringData sd) {
				gfx.setPaint(ColorAdpCache._.GetNativeColor(brush.Color()));
		// height = y - ascent + descent -> rect.y - rect.height [assume ascent] + 2 [assume descent]
	    gfx.setClip((int)x, (int)y - (int)height + 2, (int)width, (int)height);		
		if (sd == null || sd.mnemonicString == null) {
			gfx.setFont(font.UnderFont());
			gfx.drawString(s, x, y - 2);
		}
		else {
			gfx.drawString(sd.mnemonicString.getIterator(), x, y - 2);
		}
		gfx.setClip(null);
			}
	public float[] MeasureStringXtn(String s, FontAdp font, GfxStringData sd) {
			    FontMetrics fontMetrics = gfx.getFontMetrics(font.UnderFont());
	    Rectangle2D stringMetrics = fontMetrics.getStringBounds(s, gfx);
	    float width = (float)stringMetrics.getWidth();
	    int height = fontMetrics.getHeight();
	    int descent = fontMetrics.getDescent();
	    return new float[] {width, height, descent};
	    	}
	public static float[] GetStringBounds(String s, FontAdp font, Object o) {
				JComponent jcomponent = (JComponent)o;
		Graphics2D gfx = (Graphics2D)jcomponent.getGraphics();
	    FontMetrics fontMetrics = gfx.getFontMetrics(font.UnderFont());
	    Rectangle2D stringMetrics = fontMetrics.getStringBounds(s, gfx);
	    float width = (float)stringMetrics.getWidth();
	    int height = fontMetrics.getHeight();
	    int descent = fontMetrics.getDescent();
		return new float[] {width, height, descent};
			}
	
	public void DrawImage(ImageAdp image, PointAdp pt) {
				if (image == ImageAdp_.Null) return;
		gfx.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		gfx.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		gfx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gfx.drawImage((java.awt.Image)image.Under(), pt.X(), pt.Y(), null);
//		gfx.drawImage(image.UnderImage(),pt.X(),pt.Y(),
//                pt.X()+image.Width(),pt.Y()+image.Height(),
//                pt.X(),pt.Y(),
//                pt.X()+image.Width(),pt.Y()+image.Height(),
//                null);
			}
	public void DrawImage(ImageAdp img, int trg_x, int trg_y, int trg_w, int trg_h, int src_x, int src_y, int src_w, int src_h) {
				if (img == ImageAdp_.Null) return;
		gfx.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		gfx.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		gfx.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gfx.drawImage((java.awt.Image)img.Under(), trg_x, trg_y, trg_x + trg_w, trg_y + trg_h, src_x, src_y, src_x + src_w, src_y + src_h, null);
			}
	public void Rls() {gfx.dispose();}	
		public Object Under() {return gfx;}
		Graphics2D gfx;	
	@gplx.Internal protected static GfxAdpBase new_(Graphics2D gfx) {	
		GfxAdpBase rv = new GfxAdpBase();
		rv.gfx = gfx;
		return rv;
	}	GfxAdpBase() {}
}
