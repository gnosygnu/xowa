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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Scrib_proc_rslt {
	private KeyVal[] ary;
	public KeyVal[] Ary() {return ary;}
	public String Fail_msg() {return fail_msg;} private String fail_msg;
	public boolean Init_fail(String v)		{fail_msg = v; return false;}
	public boolean Init_null()				{Init_obj(null); return true;}
	public boolean Init_str_empty()		{Init_obj(""); return true;}
	public boolean Init_ary_empty()		{ary = KeyVal_.Ary_empty; return true;}
	public boolean Init_obj(Object val) {
		ary = new KeyVal[] {KeyVal_.int_(Scrib_core.Base_1, val)};
		return true;
	}
	public boolean Init_many_objs(Object... vals) {
		int len = vals.length;
		ary = new KeyVal[len];
		for (int i = 0; i < len; i++)
			ary[i] = KeyVal_.int_(i + Scrib_core.Base_1, vals[i]);
		return true;
	}
	public boolean Init_many_kvs(KeyVal... kvs) {
		ary = kvs;
		return true;
	}
	public boolean Init_many_list(ListAdp list) {
		int len = list.Count();
		ary = new KeyVal[len];
		for (int i = 0; i < len; i++)
			ary[i] = KeyVal_.int_(i + Scrib_core.Base_1, list.FetchAt(i));
		list.Clear();
		return true;
	}
	public boolean Init_many_empty() {
		ary = KeyVal_.Ary_empty;
		return true;
	}
	public boolean Init_bry_ary(byte[][] v) {
		int len = v.length;
		ary = new KeyVal[len];
		for (int i = 0; i < len; i++)
			ary[i] = KeyVal_.int_(i + Scrib_core.Base_1, String_.new_utf8_(v[i]));
		return true;
	}
}
