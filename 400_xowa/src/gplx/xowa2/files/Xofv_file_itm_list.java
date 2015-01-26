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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
class Xofv_file_itm_list {
	private final ListAdp list = ListAdp_.new_();
	public void Add(Xofv_file_itm file_itm) {
		synchronized (list) {list.Add(file_itm);}
	}
	public Xofv_file_itm Pop_at_or_null(int i)  {
		synchronized (list) {
			if (i >= list.Count()) return null;
			Xofv_file_itm rv = (Xofv_file_itm)list.FetchAt(i);
			list.DelAt(i);
			return rv;
		}
	}
}
class Xop_xfer_itm_hash {	// thread-safe
	private final OrderedHash hash = OrderedHash_.new_();
	public int Count() {
		synchronized (hash) {return hash.Count();}
	}
	public void Clear() {
		synchronized (hash) {hash.Clear();}
	}
	public void Add(Xof_xfer_itm xfer_itm) {
		synchronized (hash) {hash.Add_if_new(xfer_itm.Lnki_ttl(), xfer_itm);}
	}
	public Xof_xfer_itm Get_at_or_null(int i)  {
		synchronized (hash) {				
			return i < hash.Count() ? (Xof_xfer_itm)hash.FetchAt(i) : null;
		}
	}
	public void Del(byte[] lnki_ttl) {
		synchronized (hash) {hash.Del(lnki_ttl);}
	}
	public Xof_xfer_itm Pop_at_or_null(int i)  {
		synchronized (hash) {
			if (i >= hash.Count()) return null;
			Xof_xfer_itm rv = (Xof_xfer_itm)hash.FetchAt(i);
			hash.Del(rv.Lnki_ttl());
			return rv;
		}
	}
}
interface Xog_html_gui {
	void Update(int id, String src, int w, int h);
}
