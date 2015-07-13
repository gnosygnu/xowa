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
package gplx;
public class KeyValHash {
	private Ordered_hash hash = Ordered_hash_.new_();
	public int Count() {return hash.Count();}
	public KeyValHash Clear() {hash.Clear(); return this;}
	public boolean Has(String key) {return hash.Has(key);}
	public KeyVal Get_at(int i) {return (KeyVal)hash.Get_at(i);}
	public Object FetchValOr(String key, Object or) {KeyVal rv = FetchOrNull(key); return rv == null ? or : rv.Val();}
	public Object FetchValOrNull(String key) {return FetchValOr(key, null);}
	public Object FetchValOrFail(String key) {return KeyVal_.as_(hash.Get_by_or_fail(key)).Val();}
	public KeyValHash Add(KeyVal kv) {hash.Add(kv.Key(), kv); return this;}
	public KeyValHash Add(String key, Object val) {hash.Add(key, KeyVal_.new_(key, val)); return this;}
	public KeyValHash Add_if_dupe_use_nth(String key, Object val) {hash.Add_if_dupe_use_nth(key, KeyVal_.new_(key, val)); return this;}
	public void Del(String key) {hash.Del(key);}
	public KeyVal[] Xto_bry() {
		KeyVal[] rv = new KeyVal[this.Count()];
		for (int i = 0; i < rv.length; i++)
			rv[i] = this.Get_at(i);
		return rv;
	}
	public static KeyValHash new_() {return new KeyValHash();} protected KeyValHash() {}
	public static KeyValHash new_by_ary(KeyVal[] ary) {
		int ary_len = ary.length;
		KeyValHash rv = new KeyValHash();
		for (int i = 0; i < ary_len; i++)
			rv.Add(ary[i]);
		return rv;
	}
	public KeyVal FetchOrNull(String key) {return KeyVal_.as_(hash.Get_by(key));}
	public static KeyValHash strAry_(String[] ary) {// needed for consoleLine
		int aryLen = Array_.Len(ary); if (aryLen % 2 != 0) throw Exc_.new_("array length must be divisible by 2", "aryLen", aryLen, "ary", String_.Concat_lines_crlf(ary));
		KeyValHash rv = new KeyValHash();
		String key = null;
		for (int i = 0; i < aryLen; i++) {
			if (i % 2 == 0)
				key = ary[i];
			else
				rv.Add(key, ary[i]);
		}
		return rv;
	}
	public static final KeyValHash Empty = new KeyValHash();
}