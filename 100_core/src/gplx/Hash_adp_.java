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
import gplx.core.primitives.*;
public class Hash_adp_ {
	public static Hash_adp new_() {return new Hash_adp_obj();}
	public static final Hash_adp Noop = new Hash_adp_noop();
}
class Hash_adp_obj extends gplx.core.lists.Hash_adp_base implements Hash_adp {}//_20110428
class Hash_adp_noop implements Hash_adp {
	public int Count() {return 0;}
	public boolean Has(Object key) {return false;}
	public Object Get_by(Object key) {return null;}
	public Object Get_by_or_fail(Object key)				{throw Err_.new_missing_key(Object_.Xto_str_strict_or_null_mark(key));}
	public void Add(Object key, Object val) {}
	public void Add_as_key_and_val(Object val) {}
	public void Add_if_dupe_use_nth(Object key, Object val) {}
	public boolean Add_if_dupe_use_1st(Object key, Object val) {return false;}
	public void Del(Object key) {}
	public void Clear() {}
	public java.util.Iterator iterator() {return gplx.core.lists.Iterator_null.Instance;}
}
