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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.RectAdp;
import gplx.gfui.SizeAdp;
import gplx.gfui.SizeAdp_;
import gplx.gfui.imgs.ImageAdp;
import gplx.gfui.kits.core.Gfui_kit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Swt_img implements ImageAdp {
	public Swt_img(Gfui_kit kit, Image under, int w, int h) {this.kit = kit; this.under = under; this.width = w; this.height = h;}
	public Gfui_kit Kit() {return kit;} Gfui_kit kit;
	public SizeAdp Size() {if (size == null) size = SizeAdp_.new_(width, height); return size;} SizeAdp size;
	public int Width() {return width;} int width;
	public int Height() {return height;} int height;
	public Io_url Url() {return url;} public ImageAdp Url_(Io_url v) {url = v; return this;} Io_url url = Io_url_.Empty;
	public Object Under() {return under;} Image under;
	public boolean Disposed() {return under.isDisposed();}
	public void Rls() {under.dispose();}
	public void SaveAsBmp(Io_url url) {throw Err_.new_unimplemented();}
	public void SaveAsPng(Io_url url) {throw Err_.new_unimplemented();}
	public ImageAdp Resize(int trg_w, int trg_h) {return Extract_image(0, 0, width, height, trg_w, trg_h);}
	public ImageAdp Extract_image(RectAdp src_rect, SizeAdp trg_size) {return Extract_image(src_rect.X(), src_rect.Y(), src_rect.Width(), src_rect.Height(), trg_size.Width(), trg_size.Height());}
	public ImageAdp Extract_image(int src_x, int src_y, int src_w, int src_h, int trg_w, int trg_h) {
		Image trg_img = new Image(Display.getDefault(), trg_w, trg_h);
		GC gc = new GC(trg_img);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(under, src_x, src_y, src_w, src_h, 0, 0, trg_w, trg_h);
		gc.dispose();
		return new Swt_img(kit, trg_img, trg_w, trg_h);
	}
}
