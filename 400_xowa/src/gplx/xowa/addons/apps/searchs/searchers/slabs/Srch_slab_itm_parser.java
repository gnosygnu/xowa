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
package gplx.xowa.addons.apps.searchs.searchers.slabs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
import gplx.core.brys.*;
public class Srch_slab_itm_parser {
	private final    List_adp itm_list = List_adp_.new_();
	private final    Bry_rdr rdr = new Bry_rdr();
	public Srch_slab_itm[] Parse(byte[] raw) {	// EX: en.wikipedia.org|41|60;en.wiktionary.org|21|40;
		rdr.Init_by_src(raw);
		while (!rdr.Pos_is_eos()) {
			byte[] wiki = rdr.Read_bry_to(Byte_ascii.Pipe);				
			int bgn = rdr.Read_int_to(Byte_ascii.Pipe);
			int end = rdr.Read_int_to(Byte_ascii.Semic);
			Srch_slab_itm itm = new Srch_slab_itm(wiki, bgn, end);
			itm_list.Add(itm);
		}
		return (Srch_slab_itm[])itm_list.To_ary_and_clear(Srch_slab_itm.class);
	}
}
