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
package gplx.core.caches; import gplx.*; import gplx.core.*;
public class Gfo_cache_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private final    List_adp tmp_delete = List_adp_.New();
	public int Cur_size()	{return cur_size;}	private int cur_size;
	public int Max_size()	{return max_size;}	public Gfo_cache_mgr Max_size_(int v)	{max_size = v; return this;} private int max_size;
	public int Reduce_by()	{return reduce_by;} public Gfo_cache_mgr Reduce_by_(int v)	{reduce_by = v; return this;} private int reduce_by;
	public void Clear() {
		synchronized (tmp_delete) {
			hash.Clear();
			cur_size = 0;
		}
	}
	public Object Get_by_key(byte[] key) {
		Object o = hash.Get_by(key);
		return o == null ? null : ((Gfo_cache_data)o).Val_and_update();
	}
	public void Add_replace(byte[] key, Rls_able val, int size) {
		Object o = hash.Get_by(key);
		if (o == null)
			Add(key, val, size);
		else {
			Gfo_cache_data itm = (Gfo_cache_data)o;
			synchronized (itm) {
				cur_size -= itm.Size();
				cur_size += size;
				itm.Val_(val, size);
			}
		}
	}
	public void Add(byte[] key, Rls_able val, int size) {
		synchronized (tmp_delete) {
			if (hash.Has(key)) return;	// THREAD: since Get is not locked, it's possible to Add the same item twice
			cur_size += size;
			Gfo_cache_data itm = new Gfo_cache_data(key, val, size);
			hash.Add(key, itm);
			if (cur_size > max_size) this.Reduce();
		}
	}
	private void Reduce() {
		hash.Sort();
		int len = hash.Len();
		int list_size = 0;
		for (int i = 0; i < len; ++i) {
			Gfo_cache_data itm = (Gfo_cache_data)hash.Get_at(i);
			int new_size = list_size + itm.Size();
			if (new_size > reduce_by)
				tmp_delete.Add(itm);
			else
				list_size = new_size;
		}
		this.cur_size = list_size;
		len = tmp_delete.Len();
		for (int i = 0; i < len; ++i) {
			Gfo_cache_data itm = (Gfo_cache_data)tmp_delete.Get_at(i);
			hash.Del(itm.Key());
		}
		tmp_delete.Clear();
	}
	public int Test__len()		{return hash.Len();}
	public Object Test__get_at(int i) {
		Gfo_cache_data rv = (Gfo_cache_data)hash.Get_at(i);
		return rv.Val();
	}
	// NOTE: not called yet
	/*
	public void Del(byte[] key) {
		Object o = hash.Get_by(key); if (o == null) return;
		Gfo_cache_data itm = (Gfo_cache_data)o;
		cur_size -= itm.Size();
		hash.Del(itm.Key());
		itm.Rls();
	}
	*/
}
