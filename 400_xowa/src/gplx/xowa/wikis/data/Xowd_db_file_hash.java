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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
class Xowd_db_file_hash {
	private final Ordered_hash hash = Ordered_hash_.new_();
	public int Count_total() {return count_total;} private int count_total;
	public void Clear() {hash.Clear(); count_total = 0;}
	public void Del(Xowd_db_file file) {
		Ordered_hash tids = (Ordered_hash)hash.Get_by(file.Tid());
		if (tids == null) throw Err_.new_wo_type("unknown file.tid", "url", file.Url());
		if (!tids.Has(file.Id())) throw Err_.new_wo_type("unknown file.id", "url", file.Url());
		tids.Del(file.Id());
		--count_total;
	}
	public void Add_or_new(Xowd_db_file file) {
		byte tid = file.Tid();
		Ordered_hash tids = (Ordered_hash)hash.Get_by(tid);
		if (tids == null) {
			tids = Ordered_hash_.new_();
			hash.Add(tid, tids);
		}
		tids.Add(file.Id(), file);
		++count_total;
	}
	public int Count_of_tid(byte tid) {
		Ordered_hash tids = (Ordered_hash)hash.Get_by(tid);
		return tids == null ? 0 : tids.Count();
	}
}
