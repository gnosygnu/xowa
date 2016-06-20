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
package gplx.xowa.xtns.imaps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_map_arg implements gplx.core.brys.Bfr_arg {
	private final    int imap_id;
	private final    Imap_shapes_arg shapes_arg;
	public Imap_map_arg(int imap_id, Imap_part_shape[] shapes, double scale) {
		this.imap_id = imap_id;
		this.shapes_arg = new Imap_shapes_arg(shapes, scale);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Imap_html_fmtrs.Map.Bld_bfr_many(bfr, imap_id, shapes_arg);
	}
}
