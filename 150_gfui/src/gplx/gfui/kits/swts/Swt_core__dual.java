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
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Color;
import gplx.gfui.controls.gxws.*;
import gplx.gfui.draws.ColorAdp;

class Swt_core__dual extends Swt_core__base {
	private final   Control             outer, inner;
	private final   int                 inner_adj_w, inner_adj_h;
	public Swt_core__dual(Composite outer, Control inner, int inner_adj_w, int inner_adj_h) {
		super(outer, inner);
		this.outer = outer; this.inner = inner;
		this.inner_adj_w = inner_adj_w; this.inner_adj_h = inner_adj_h;
	}
	@Override public void               Width_set(int v)                {super.Width_set(v);  Swt_control_.W_set(outer, v + inner_adj_w);}
	@Override public void               Height_set(int v)               {super.Height_set(v); Swt_control_.H_set(outer, v + inner_adj_h);}
	@Override public void               Size_set(SizeAdp v)             {super.Size_set(v);   Swt_control_.Size_set(inner, v.Width() + inner_adj_w, v.Height() + inner_adj_h);}
	@Override public void               Rect_set(RectAdp v)             {super.Rect_set(v);   Swt_control_.Size_set(inner, v.Width() + inner_adj_w, v.Height() + inner_adj_h);}
	@Override public ColorAdp           BackColor()                     {return To_color_gfui(inner.getBackground());}
	@Override public void               BackColor_set(ColorAdp v)       {
		Color color = To_color_swt(inner, v);
		inner.setBackground(color);
		outer.setBackground(color);
	}
	@Override public ColorAdp           ForeColor()                     {return To_color_gfui(inner.getForeground());}
	@Override public void               ForeColor_set(ColorAdp v)       {
		Color color = To_color_swt(inner, v);
		inner.setForeground(color);
		outer.setForeground(color);
	}

	@Override public void Invalidate() {outer.update(); inner.update();}
	@Override public void Dispose() {outer.dispose(); inner.dispose();}	
}
