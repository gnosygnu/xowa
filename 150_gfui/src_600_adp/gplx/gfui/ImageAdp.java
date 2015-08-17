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
import java.awt.Image;
public interface ImageAdp extends RlsAble {
	Gfui_kit Kit();
	SizeAdp Size();
	int Width();
	int Height();
	Io_url Url(); ImageAdp Url_(Io_url v);
	Object Under();
	boolean Disposed();
	void SaveAsBmp(Io_url url);
	void SaveAsPng(Io_url url);
	ImageAdp Resize(int width, int height);
	ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size);
	ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h);
}
class ImageAdp_txt implements ImageAdp {
	public Gfui_kit Kit() {return Swing_kit._;}
	public SizeAdp Size() {return size;} SizeAdp size;
	public int Width() {return size.Width();}
	public int Height() {return size.Height();}
	public Io_url Url() {return url;} public ImageAdp Url_(Io_url v) {url = v; return this;} Io_url url;
	public Object Under() {return null;}
	public boolean Disposed() {return disposed;} private boolean disposed = false;
	public void Rls() {disposed = true;}
	public void SaveAsBmp(Io_url url) {SaveAs(url, ".bmp");}
	public void SaveAsPng(Io_url url) {SaveAs(url, ".png");}
	void SaveAs(Io_url url, String ext) {Io_mgr.I.SaveFilStr(url.GenNewExt(ext), size.To_str());}
	public ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size) {return Extract_image(src_rect.X(), src_rect.Y(), src_rect.Width(), src_rect.Height(), trg_size.Width(), trg_size.Height());}
	public ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h) {return ImageAdp_.txt_mem_(Io_url_.Empty, SizeAdp_.new_(trg_w, trg_h));}
	public ImageAdp Resize(int width, int height) {return ImageAdp_.txt_mem_(Io_url_.Empty, SizeAdp_.new_(width, height));}
	public ImageAdp_txt(Io_url url, SizeAdp size) {this.url = url; this.size = size;}
}
