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
package gplx.xowa.addons.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
class Xogui_nde_hash {
	private final    Ordered_hash hash = Ordered_hash_.New(); 
	private final    List_adp deleted = List_adp_.New();
	public int Len() {
		return hash.Len();
	}
	public void Add(Xogui_nde nde) {
		hash.Add(nde.Id(), nde);
	}
	public Xogui_nde Get_at(int i) {
		return (Xogui_nde)hash.Get_at(i);
	}
	public Xogui_nde Get_by_or_fail(int id) {
		return (Xogui_nde)hash.Get_by_or_fail(id);
	}
	public Xogui_nde_hash Merge(Xogui_nde_hash src) {
		int len = src.Len();
		for (int i = 0; i < len; i++) {
			this.Add(src.Get_at(i));
		}
		return this;
	}
	public void Deleted__add(Xogui_nde nde) {
		deleted.Add(nde);
	}
	public void Deleted__commit() {
		int len = deleted.Len();
		for (int i = 0; i < len; i++) {
			Xogui_nde nde = (Xogui_nde)deleted.Get_at(i);
			hash.Del(nde.Id());
		}
	}
}
class Xogui_nde_iter {
	private final    Xogui_nde_hash hash;
	private int bgn, max;
	public Xogui_nde_iter(Xogui_nde_hash hash, int max) {
		this.hash = hash;
		this.max = max;
	}
	public boolean Move_next() {
		return bgn < hash.Len();
	}
	public String To_sql_in() {
		Bry_bfr bfr = Bry_bfr_.New();
		int end = bgn + max;
		for (int i = bgn; i < end; i++) {
			Xogui_nde nde = hash.Get_at(i);
			if (i != bgn) bfr.Add_byte_comma();
			bfr.Add_int_variable(nde.Id());
		}
		bgn = end;
		return bfr.To_str_and_clear();
	}
	public static Xogui_nde_iter New_sql(Xogui_nde_hash hash) {
		return new Xogui_nde_iter(hash, 255);
	}
}
