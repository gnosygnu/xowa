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
package gplx.xowa.addons.bldrs.mass_parses.parses.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.core.primitives.*; import gplx.core.lists.hashs.*;
public class Xomp_ns_ord_mgr {
	private final    Hash_adp__int hash = new Hash_adp__int();
	public Xomp_ns_ord_mgr(int[] ns_id_ary) {
		int len = ns_id_ary.length;
		for (int i = 0; i < len; ++i) {
			hash.Add(ns_id_ary[i], new Int_obj_val(i));
		}
	}
	public int Get_ord_by_ns_id(int ns_id) {return ((Int_obj_val)hash.Get_by_or_fail(ns_id)).Val();}
}
