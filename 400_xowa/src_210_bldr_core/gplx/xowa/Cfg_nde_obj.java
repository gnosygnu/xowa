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
public interface Cfg_nde_obj {
	boolean Nde_typ_is_grp();
	void Nde_atrs_set(byte[][] ary);
	Cfg_nde_obj Nde_subs_make(byte[] itm_type, byte[] itm_key, byte[][] itm_atrs);
	void Nde_subs_add(byte[] itm_key, Cfg_nde_obj itm_obj);
	Cfg_nde_obj Nde_subs_get(byte[] key);
	int Nde_subs_len();
	Cfg_nde_obj Nde_subs_get_at(int i);
	void Nde_subs_del(byte[] key);
	byte[] Nde_key();
}
