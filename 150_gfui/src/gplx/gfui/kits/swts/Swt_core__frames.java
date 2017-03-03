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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import gplx.gfui.controls.gxws.*;
import gplx.gfui.draws.*;
import gplx.gfui.kits.*;
import gplx.gfui.kits.core.Swt_kit;
import gplx.gfui.layouts.swts.*;

class Swt_core__frames extends Swt_core__base {
	private final Composite outer; 
	private final Control inner;
	private final Swt_frame_itm[] frames;
	private final int frames_len;
	public Swt_core__frames(final Swt_text_w_border text_w_border, final Composite outer, final Swt_frame_itm[] frames) {
		super(outer, frames[frames.length - 1].Item());
		this.frames = frames;
		this.frames_len = frames.length;
		this.outer = outer;
		this.inner = frames[frames_len - 1].Item();
		
		// listener needed for layout changes to propagate to Gxw size methods
		outer.addListener (SWT.Resize,  new Listener() {
		    public void handleEvent (Event e) {
		    	Rectangle outer_rect = outer.getBounds();
		    	Frames_w_set(outer_rect.width);
		    	Frames_h_set(outer_rect.height);
		    	
		    	// vertically center
		    	int text_size = frames[frames_len - 1].Item().getSize().y;
		    	text_w_border.margins_t = (outer_rect.height - text_size) / 2;
		    	// Tfds.Write(outer_rect.height, frames[frames_len - 2].Item().getSize().y, text_size, text_w_border.margins_t, frames[frames_len - 1].Item().getToolTipText());
		    }
		});		
	}
	@Override public void               Width_set(int v)                {super.Width_set(v);  Frames_w_set(v);}
	@Override public void               Height_set(int v)               {super.Height_set(v); Frames_h_set(v);}
	@Override public void               Size_set(SizeAdp v)             {super.Size_set(v);   Frames_size_set(v);}
	@Override public void               Rect_set(RectAdp v)             {super.Rect_set(v);   Frames_size_set(v.Size());}
	@Override public void BackColor_set(ColorAdp v) {
		Color color = Swt_core__base.To_color_swt(outer, v);
		for (int i = 0; i < frames_len; i++) {
			frames[i].Item().setBackground(color);
		}
	}
	@Override public void ForeColor_set(ColorAdp v) {
		Color color = Swt_core__base.To_color_swt(outer, v);
		for (int i = 0; i < frames_len; i++)
			frames[i].Item().setForeground(color);
	}
	@Override public void Controls_add(GxwElem sub)	{throw Err_.new_unimplemented();}
	@Override public void Controls_del(GxwElem sub)	{}
	@Override public void Invalidate() {
		inner.redraw();
		inner.update();
	}
	@Override public void Dispose() {outer.dispose(); inner.dispose();}	
	private void Frames_w_set(int v) {
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(v, this.Height());
	}
	private void Frames_h_set(int v) {		
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(this.Width(), v);
	}
	private void Frames_size_set(SizeAdp v) {		
		for (int i = 0; i < frames_len; i++)
			frames[i].Rect_set(v.Width(), v.Height());
	}
}
interface Swt_frame_itm {
	Control Item();
	void Rect_set(int w, int h);
}
class Swt_frame_itm__manual implements Swt_frame_itm {
	private final Control control; private final int x, y, w, h;
	public Swt_frame_itm__manual(Control control, int x, int y, int w, int h) {
		this.control = control; this.x = x; this.y = y; this.w = w; this.h = h;
	}
	public Control Item() {return control;}
	public void Rect_set(int new_w, int new_h) {
		Swt_control_.Rect_set(control, x, y, new_w + w, new_h + h);
	}
}
class Swt_frame_itm__center_v implements Swt_frame_itm {
	private final Control control; private final Swt_text_w_border margin_owner;
	public Swt_frame_itm__center_v(Control control, Swt_text_w_border margin_owner) {
		this.control = control;
		this.margin_owner = margin_owner;
	}
	public Control Item() {return control;}
	public void Rect_set(int new_w, int new_h) {
		int margin_t = margin_owner.margins_t;
		int margin_b = margin_owner.margins_b;
		// Tfds.Write(margin_t, margin_b, new_h - (margin_t + margin_b), control.getToolTipText());
		Swt_control_.Rect_set(control, 0, margin_t, new_w, new_h - (margin_t + margin_b));
	}
}
