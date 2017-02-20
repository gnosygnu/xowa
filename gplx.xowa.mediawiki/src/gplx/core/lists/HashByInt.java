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
package gplx.core.lists; import gplx.*; import gplx.core.*;
import gplx.core.primitives.*;
public class HashByInt {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    Int_obj_ref tmp_key = Int_obj_ref.New_neg1();
	public void Clear() {
		hash.Clear();
	}
	public int Len() {
		return hash.Len();
	}
	public Object Get_by_or_fail(int key) {
		synchronized (tmp_key) {
			HashByIntItem item = (HashByIntItem)hash.Get_by_or_fail(tmp_key.Val_(key));
			return item.val;
		}
	}
	public Object Get_by_or_null(int key) {
		synchronized (tmp_key) {
			HashByIntItem item = (HashByIntItem)hash.Get_by(tmp_key.Val_(key));
			return item == null ? null : item.val;
		}
	}
	public HashByInt Add_as_bry(int key, String val) {return Add(key, Bry_.new_u8(val));}
	public HashByInt Add(int key, Object val) {
		HashByIntItem item = new HashByIntItem(key, val);
		hash.Add(Int_obj_ref.New(key), item);
		return this;
	}
	public HashByInt Clone() {
		HashByInt rv = new HashByInt();

		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			HashByIntItem item = (HashByIntItem)hash.Get_at(i);
			rv.Add(item.key, item);
		}
		return rv;
	}
}
class HashByIntItem {
	public final    int key;
	public final    Object val;
	public HashByIntItem(int key, Object val) {
		this.key = key;
		this.val = val;
	}
}
