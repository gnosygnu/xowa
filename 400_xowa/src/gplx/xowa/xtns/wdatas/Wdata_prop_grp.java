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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wdata_prop_grp {
	public Wdata_prop_grp(Int_obj_ref id_ref) {this.id_ref = id_ref;}
	public Int_obj_ref Id_ref() {return id_ref;} Int_obj_ref id_ref;
	public int Id() {return id_ref.Val();}
	public int Itms_len() {return itms.length;} Wdata_prop_itm_core[] itms;
	public Wdata_prop_itm_core Itms_get_at(int i) {return itms[i];}
	public void Itms_add(Wdata_prop_itm_core itm) {list.Add(itm);} ListAdp list = ListAdp_.new_();
	public void Itms_make() {
		itms = (Wdata_prop_itm_core[])list.XtoAryAndClear(Wdata_prop_itm_core.class);
		list = null;
	}
}
