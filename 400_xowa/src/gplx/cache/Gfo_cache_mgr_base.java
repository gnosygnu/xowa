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
package gplx.cache; import gplx.*;
public class Gfo_cache_mgr_base {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	public int Compress_max() {return compress_max;} public void Compress_max_(int v) {compress_max = v;} private int compress_max = 16;
	public int Compress_to() {return compress_to;} public void Compress_to_(int v) {compress_to = v;} private int compress_to = 8;
	protected Object Base_get_or_null(byte[] key) {
		Object rv_obj = hash.Get_by(key);
		return rv_obj == null ? null : ((Gfo_cache_itm)rv_obj).Val();
	}
	protected void Base_add(byte[] key, Object val) {
		if (hash.Count() >= compress_max) Compress(); 
		Gfo_cache_itm itm = new Gfo_cache_itm(key, val); 
		hash.Add(key, itm);
	}
	protected void Base_del(byte[] key) {
		hash.Del(key);
	}
	public void Compress() {
		hash.Sort_by(Gfo_cache_itm_comparer.Touched_asc);
		int del_len = hash.Count() - compress_to;
		List_adp del_list = List_adp_.new_();
		for (int i = 0; i < del_len; i++) {
			Gfo_cache_itm itm = (Gfo_cache_itm)hash.Get_at(i);
			del_list.Add(itm);
		}
		for (int i = 0; i < del_len; i++) {
			Gfo_cache_itm itm = (Gfo_cache_itm)del_list.Get_at(i);
			hash.Del(itm.Key());
		}
	}
}
