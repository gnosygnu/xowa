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
import gplx.gfui.kits.core.*;
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
	public static final    ImageAdp_null Instance = new ImageAdp_null(); ImageAdp_null() {}
}
