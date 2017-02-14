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

class Swt_core__basic extends Swt_core__base {
	protected final Control             control;
	public Swt_core__basic(Control control) {
		super(control, control);
		this.control = control;
	}
	@Override public void Invalidate() {control.redraw(); control.update();}
	@Override public void Dispose() {control.dispose();}
}
