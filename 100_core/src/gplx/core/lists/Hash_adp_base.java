/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.lists; import gplx.*; import gplx.core.*;
public abstract class Hash_adp_base implements Hash_adp {
	public boolean Has(Object key) {return Has_base(key);}
	public Object Get_by(Object key) {return Fetch_base(key);}
	public Object Get_by_or_fail(Object key) {return Get_by_or_fail_base(key);}
	public void Add(Object key, Object val) {Add_base(key, val);}
	public Hash_adp Add_and_more(Object key, Object val) {Add_base(key, val); return this;}
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
