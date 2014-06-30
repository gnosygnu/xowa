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
package gplx.xowa.xtns.refs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Ref_itm_lst {
	public Ref_itm_lst(byte[] grp_name) {this.grp_name = grp_name;}
	byte[] grp_name; int idx_major_last = 0;
	public int Itms_len() {return list.Count();}
	public Ref_nde Itms_get_at(int i) {return (Ref_nde)list.FetchAt(i);}
	public void Itms_add(byte[] itm_name, byte[] follow, Ref_nde itm) {
		if (itm_name == Bry_.Empty) {
			if (follow == Bry_.Empty) {
				itm.Idx_major_(idx_major_last++);
				list.Add(itm);
			}
			else {
				Object o = hash.Fetch(follow);
				if (o == null) {// fail
				}
				else {
					Ref_nde head = (Ref_nde)o;
					head.Related_add(itm, Ref_nde.Idx_minor_follow);
					itm.Idx_major_(head.Idx_major());
				}

			}
		}
		else {
			Object o = hash.Fetch(itm_name);
			if (o == null) {
				hash.Add(itm_name, itm);
				itm.Idx_major_(idx_major_last++);
				list.Add(itm);
			}
			else {
				Ref_nde head = (Ref_nde)o;
				head.Related_add(itm, head.Related_len());
				itm.Idx_major_(head.Idx_major());
				itm.Idx_minor_(head.Related_len());
			}
		}
	}
	public void Itms_clear() {
		hash.Clear();
		list.Clear();
		idx_major_last = 0;
	}
	HashAdp hash = HashAdp_.new_bry_(); ListAdp list = ListAdp_.new_();
}
