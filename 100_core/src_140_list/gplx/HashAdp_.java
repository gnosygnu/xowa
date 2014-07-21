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
public class HashAdp_ {
	public static HashAdp new_() {return new HashAdp_obj();}
	public static HashAdp new_bry_() {return new HashAdp_bry();}
	public static final HashAdp Null = new HashAdp_null();
}
class HashAdp_obj extends gplx.lists.HashAdp_base implements HashAdp {}//_20110428
class HashAdp_null implements HashAdp {
	public int Count() {return 0;}
	public boolean Has(Object key) {return false;}
	public Object Fetch(Object key) {return null;}
	public Object FetchOrFail(Object key)				{throw Err_.missing_key_(Object_.Xto_str_strict_or_null_mark(key));}
	public Object FetchOrNew(Object key, NewAble proto) {throw Err_.new_("could not add to null hash");}
	public void Add(Object key, Object val) {}
	public void AddKeyVal(Object val) {}
	public void AddReplace(Object key, Object val) {}
	public boolean Add_if_new(Object key, Object val) {return false;}
	public void Del(Object key) {}
	public void Clear() {}
	public java.util.Iterator iterator() {return gplx.lists.Iterator_null._;}
}
class HashAdp_bry extends gplx.lists.HashAdp_base implements HashAdp {
	Bry_obj_ref key_ref = Bry_obj_ref.null_();
	@Override protected void Add_base(Object key, Object val) {super.Add_base(Bry_obj_ref.new_((byte[])key), val);}
	@Override protected void Del_base(Object key) {super.Del_base(key_ref.Val_((byte[])key));}
	@Override protected boolean Has_base(Object key) {return super.Has_base(key_ref.Val_((byte[])key));}
	@Override protected Object Fetch_base(Object key) {return super.Fetch_base(key_ref.Val_((byte[])key));}
}
