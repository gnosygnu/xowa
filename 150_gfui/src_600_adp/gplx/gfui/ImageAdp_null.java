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
public class ImageAdp_null implements ImageAdp {
	public Gfui_kit Kit() {return Gfui_kit_.Mem();}
	public SizeAdp Size() {return SizeAdp_.Zero;}
	public int Width() {return 0;}
	public int Height() {return 0;}
	public Io_url Url() {return Io_url_.Empty;} public ImageAdp Url_(Io_url v) {return this;}
	public Object Under() {return null;}
	public boolean Disposed() {return disposed;} private boolean disposed = false;
	public void Rls() {disposed = true;}
	public void SaveAsBmp(Io_url url) {}
	public void SaveAsPng(Io_url url) {}
	public ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size) {return Extract_image(src_rect.X(), src_rect.Y(), src_rect.Width(), src_rect.Height(), trg_size.Width(), trg_size.Height());}
	public ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h) {return this;}
	public ImageAdp Resize(int width, int height) {return this;}
	public static final ImageAdp_null _ = new ImageAdp_null(); ImageAdp_null() {}
}
