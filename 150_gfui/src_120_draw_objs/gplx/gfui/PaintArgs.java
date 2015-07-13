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
public class PaintArgs {
	public GfxAdp Graphics() {return graphics;} GfxAdp graphics;
	public RectAdp ClipRect() {return clipRect;} RectAdp clipRect;

	public static PaintArgs cast_(Object obj) {try {return (PaintArgs)obj;} catch(Exception exc) {throw Exc_.new_type_mismatch_w_exc(exc, PaintArgs.class, obj);}}
	public static PaintArgs new_(GfxAdp graphics, RectAdp clipRect) {
		PaintArgs rv = new PaintArgs();
		rv.graphics = graphics; rv.clipRect = clipRect;
		return rv;
	}
}
