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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import org.eclipse.swt.widgets.*;
import gplx.gfui.controls.gxws.*;

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

	@Override public void Invalidate() {outer.update(); inner.update();}
	@Override public void Dispose() {outer.dispose(); inner.dispose();}	
}
