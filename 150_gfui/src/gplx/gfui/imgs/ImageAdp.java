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
package gplx.gfui.imgs; import gplx.*; import gplx.gfui.*;
import java.awt.Image;
import gplx.gfui.kits.core.*;
public interface ImageAdp extends Rls_able {
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
	public Gfui_kit Kit() {return Swing_kit.Instance;}
	public SizeAdp Size() {return size;} SizeAdp size;
	public int Width() {return size.Width();}
	public int Height() {return size.Height();}
	public Io_url Url() {return url;} public ImageAdp Url_(Io_url v) {url = v; return this;} Io_url url;
	public Object Under() {return null;}
	public boolean Disposed() {return disposed;} private boolean disposed = false;
	public void Rls() {disposed = true;}
	public void SaveAsBmp(Io_url url) {SaveAs(url, ".bmp");}
	public void SaveAsPng(Io_url url) {SaveAs(url, ".png");}
	void SaveAs(Io_url url, String ext) {Io_mgr.Instance.SaveFilStr(url.GenNewExt(ext), size.To_str());}
	public ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size) {return Extract_image(src_rect.X(), src_rect.Y(), src_rect.Width(), src_rect.Height(), trg_size.Width(), trg_size.Height());}
	public ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h) {return ImageAdp_.txt_mem_(Io_url_.Empty, SizeAdp_.new_(trg_w, trg_h));}
	public ImageAdp Resize(int width, int height) {return ImageAdp_.txt_mem_(Io_url_.Empty, SizeAdp_.new_(width, height));}
	public ImageAdp_txt(Io_url url, SizeAdp size) {this.url = url; this.size = size;}
}
