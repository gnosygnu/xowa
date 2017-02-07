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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
public class Xogui_nde_iter {
	private final    Xoedit_nde_hash hash;
	private int bgn, max;
	public Xogui_nde_iter(Xoedit_nde_hash hash, int max) {
		this.hash = hash;
		this.max = max;
	}
	public boolean Move_next() {
		return bgn < hash.Len();
	}
	public String To_sql_in() {
		Bry_bfr bfr = Bry_bfr_.New();
		int end = bgn + max;
		if (end > hash.Len()) end = hash.Len();
		for (int i = bgn; i < end; i++) {
			Xoedit_nde nde = hash.Get_at(i);
			if (i != bgn) bfr.Add_byte_comma();
			bfr.Add_int_variable(nde.Id());
		}
		bgn = end;
		return bfr.To_str_and_clear();
	}
	public String To_sql_in_key() {
		Bry_bfr bfr = Bry_bfr_.New();
		int end = bgn + max;
		if (end > hash.Len()) end = hash.Len();
		for (int i = bgn; i < end; i++) {
			Xoedit_nde nde = (Xoedit_nde)hash.Get_at(i);
			if (i != bgn) bfr.Add_byte_comma();
			bfr.Add_byte_apos();
			bfr.Add_str_u8(nde.Key());
			bfr.Add_byte_apos();
		}
		bgn = end;
		return bfr.To_str_and_clear();
	}
	public static Xogui_nde_iter New_sql(Xoedit_nde_hash hash) {
		return new Xogui_nde_iter(hash, 255);
	}
}
