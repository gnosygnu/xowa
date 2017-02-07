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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*;
import gplx.langs.htmls.*;
import gplx.xowa.xtns.pagebanners.*;
public class Bfr_arg__pgbnr implements Bfr_arg_clearable {
	private Pgbnr_itm pgbnr;
	public Bfr_arg__pgbnr Set(Pgbnr_itm pgbnr) {
		this.pgbnr = pgbnr;
		return this;
	}
	public void Bfr_arg__clear() {pgbnr = null;}
	public boolean Bfr_arg__missing() {return pgbnr == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__srcset, pgbnr.Srcset());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_x, pgbnr.Data_pos_x());
		Gfh_atr_.Add_double(bfr, Pgbnr_itm.Atr_key__data_pos_y, pgbnr.Data_pos_y());
		Gfh_atr_.Add(bfr, Gfh_atr_.Bry__style, pgbnr.Style());
	}
}
