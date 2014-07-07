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
package gplx.xowa.xtns.cite; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Ref_itm_mgr {
	private Hash_adp_bry grps = Hash_adp_bry.ci_();
	private Ref_itm_grp grp_default = new Ref_itm_grp(Bry_.Empty);
	private int uid_last;		
	public int Tag__next_id() {return ++tag__next_id;} private int tag__next_id = 0;
	public boolean References__recursing() {return references__recursing;} public void References__recursing_(boolean v) {references__recursing = v;} private boolean references__recursing;
	public Ref_itm_lst Lst_get(byte[] grp_name, int lst_idx) {
		return Bry_.Len_eq_0(grp_name) ? grp_default.Lsts_get_at(lst_idx) : ((Ref_itm_grp)grps.Fetch(grp_name)).Lsts_get_at(lst_idx);	// NOTE: must be Bry_.Len_eq_0 else <references group=""/> not same as <references/>; DATE:2013-02-06
	}	
	public void Grps_add(byte[] grp_name, byte[] itm_name, byte[] follow, Ref_nde itm) {
		Ref_itm_grp grp = Grps_get(grp_name);
		grp.Lsts_add(itm_name, follow, itm);
		itm.Uid_(uid_last++);
	}
	public int Grps_seal(byte[] grp_name) {
		Ref_itm_grp grp = Grps_get(grp_name);
		return grp.Grp_seal();
	}
	public void Grps_clear() {
		grps.Clear();
		grp_default.Lsts_clear();
		uid_last = 0;
		tag__next_id = 0;
		references__recursing = false;
	}
	public Ref_itm_grp Grps_get(byte[] grp_name) {
		if (Bry_.Len_eq_0(grp_name)) return grp_default;
		Object o = grps.Get_by_bry(grp_name);
		if (o == null) {
			Ref_itm_grp grp = new Ref_itm_grp(grp_name);
			grps.Add(grp_name, grp);
			return grp;
		}
		else
			return (Ref_itm_grp)o;
	}
}
