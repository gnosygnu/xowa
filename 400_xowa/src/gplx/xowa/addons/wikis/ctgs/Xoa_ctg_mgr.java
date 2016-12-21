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
package gplx.xowa.addons.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.addons.wikis.ctgs.htmls.*;
public class Xoa_ctg_mgr {
	public static final byte Version_null = Byte_.Zero, Version_1 = 1, Version_2 = 2;
	public static final byte Tid__subc = 0, Tid__file = 1, Tid__page = 2, Tid___max = 3;	// SERIALIZED; cat_link.cl_type_id
	public static final byte Hidden_n = Byte_.Zero, Hidden_y = (byte)1;
	public static final String Html__cls__str = "CategoryTreeLabel CategoryTreeLabelNs14 CategoryTreeLabelCategory";
	public static final    byte[] Html__cls__bry = Bry_.new_a7(Html__cls__str);
}
