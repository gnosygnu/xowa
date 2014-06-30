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
package gplx.xowa; import gplx.*;
public class Cfg_nde_obj_ {
	public static void Fill_recurse(OrderedHash rv, Cfg_nde_obj peer_grp) {
		int subs_len = peer_grp.Nde_subs_len();
		for (int i = 0; i < subs_len; i++) {
			Cfg_nde_obj sub_obj = (Cfg_nde_obj)peer_grp.Nde_subs_get_at(i);
			if (sub_obj.Nde_typ_is_grp())
				Cfg_nde_obj_.Fill_recurse(rv, (Cfg_nde_obj)sub_obj);
			else {
				rv.AddReplace(sub_obj.Nde_key(), sub_obj);
			}
		}
	}
}
