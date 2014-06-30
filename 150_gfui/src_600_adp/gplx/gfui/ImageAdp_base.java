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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageAdp_base implements ImageAdp, RlsAble {
	@gplx.Internal protected ImageAdp_base(Image img) {this.under = img;}
	public Gfui_kit Kit() {return kit;} public void Kit_(Gfui_kit v) {this.kit = v;} Gfui_kit kit;
	public SizeAdp Size() {
		if (this == ImageAdp_.Null) return SizeAdp_.Null;
		if (size == null) {
			size = SizeAdp_.new_(this.Width(), this.Height());
		}
		return size;
	}	SizeAdp size = null;
	public int Width() {return under.getWidth(null);}	
	public int Height() {return under.getHeight(null);}	
	public Io_url Url() {return url;} public ImageAdp Url_(Io_url v) {url = v; return this;} Io_url url = Io_url_.Null;
	public Object Under() {return under;} Image under;
	public boolean Disposed() {return disposed;} private boolean disposed = false;

	public void Rls() {disposed = true; under.flush();}	
	public void SaveAsBmp(Io_url url) {SaveAs(url, "bmp");}
	public void SaveAsPng(Io_url url) {SaveAs(url, "png");}
	void SaveAs(Io_url url, String fmtStr) {
		Io_mgr._.CreateDirIfAbsent(url.OwnerDir());
			    File fil = new File(url.Xto_api());
//		String[] formatNames = ImageIO.getWriterFormatNames();
//		for (String s : formatNames)
//			Tfds.Write(s);
	    boolean success = false;
	    try {success = ImageIO.write((BufferedImage)under, fmtStr, fil);}
	    catch (IOException e) {}
	    if (!success) throw Err_.new_key_("gplx.gfui.imgs.SaveImageFailed", "save image failed").Add("srcUrl", url.Xto_api()).Add("trgFil", fil).Add("fmt", fmtStr);
		//#@endif
			}
	public ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size) {return Extract_image(src_rect.X(), src_rect.Y(), src_rect.Width(), src_rect.Height(), trg_size.Width(), trg_size.Height());}
	public ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h) {
		if (this == ImageAdp_.Null) return ImageAdp_.Null; // TODO: create ImageAdpNull class (along with ImageAdp interface)
		if (disposed) return ImageAdp_.new_(1, 1);
		ImageAdp rv = ImageAdp_.new_(trg_w, trg_h);
		GfxAdp gfx = GfxAdp_.image_(rv);
		gfx.DrawImage(this, 0, 0, trg_w, trg_h, src_x, src_y, src_w, src_h);
		gfx.Rls();
		return rv;
	}
	public ImageAdp Resize(int width, int height) {return Extract_image(0, 0, this.Width(), this.Height(), width, height);}
}
