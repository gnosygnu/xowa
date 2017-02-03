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
public abstract class Hash_adp_base implements Hash_adp {
	public boolean Has(Object key) {return Has_base(key);}
	public Object Get_by(Object key) {return Fetch_base(key);}
	public Object Get_by_or_fail(Object key) {return Get_by_or_fail_base(key);}
	public void Add(Object key, Object val) {Add_base(key, val);}
	public void Add_as_key_and_val(Object val) {Add_base(val, val);}
	public void Add_if_dupe_use_nth(Object key, Object val) {
		Object existing = Fetch_base(key); if (existing != null) Del(key);	// overwrite if exists
		Add(key, val);
	}
	public boolean Add_if_dupe_use_1st(Object key, Object val) {
		if (Has(key)) return false;
		Add(key, val);
		return true;
	}
	@gplx.Virtual public void Del(Object key) {Del_base(key);}
	protected Object Get_by_or_fail_base(Object key) {
		if (key == null) throw Err_.new_wo_type("key cannot be null");
		if (!Has_base(key)) throw Err_.new_wo_type("key not found", "key", key);
		return Fetch_base(key);
	}

	// private final    java.util.HashMap<Object, Object> hash = new java.util.HashMap<Object, Object>();			
	private final    java.util.Hashtable hash = new java.util.Hashtable();			
	@gplx.Virtual public int Len() {return hash.size();}														
	@gplx.Virtual public int Count() {return hash.size();}														
	@gplx.Virtual public void Clear() {hash.clear();}															
	@gplx.Virtual protected void Add_base(Object key, Object val) {hash.put(key, val);}						
	@gplx.Virtual protected void Del_base(Object key) {hash.remove(key);}										
	@gplx.Virtual protected boolean Has_base(Object key) {return hash.containsKey(key);}							
	@gplx.Virtual protected Object Fetch_base(Object key) {return hash.get(key);}									
	@gplx.Virtual public java.util.Iterator iterator() {return hash.values().iterator();}	
}
