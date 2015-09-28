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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.vnts.*;
class Vnt_flag_lang_mgr {
	private final Ordered_hash regy = Ordered_hash_.new_bry_();
	public int Count() {return regy.Count();}
	public boolean Has(byte[] vnt) {return regy.Has(vnt);}
	public void Clear() {regy.Clear();}
	public void Add(Xol_vnt_itm itm) {regy.Add(itm.Key(), itm);}
	public Xol_vnt_itm Get_at(int i) {return (Xol_vnt_itm)regy.Get_at(i);}
	public void To_bfr__dbg(Bry_bfr bfr) {
		int len = regy.Count();
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = (Xol_vnt_itm)regy.Get_at(i);
			if (bfr.Len_gt_0()) bfr.Add_byte_semic();
			bfr.Add(itm.Key());
		}
	}
}
