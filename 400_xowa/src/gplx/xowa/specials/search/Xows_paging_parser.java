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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.brys.*;
class Xows_paging_parser {
	private final List_adp itm_list = List_adp_.new_();
	private final Bry_rdr rdr = new Bry_rdr();
	public Xows_paging_itm[] Parse(byte[] raw) {	// EX: en.wikipedia.org|41|60;en.wiktionary.org|21|40;
		rdr.Init(raw);
		while (!rdr.Pos_is_eos()) {
			byte[] wiki = rdr.Read_bry_to_pipe();
			int bgn = rdr.Read_int_to_pipe();
			int end = rdr.Read_int_to_semic();
			Xows_paging_itm itm = new Xows_paging_itm(wiki, bgn, end);
			itm_list.Add(itm);
		}
		return (Xows_paging_itm[])itm_list.To_ary_and_clear(Xows_paging_itm.class);
	}
}
class Xows_paging_itm {
	public Xows_paging_itm(byte[] wiki, int bgn, int end) {this.wiki = wiki; this.bgn = bgn; this.end = end;}
	public byte[] Wiki() {return wiki;} private final byte[] wiki;
	public int Bgn() {return bgn;} private final int bgn;
	public int End() {return end;} private final int end;
}
