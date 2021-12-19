/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.lists;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.ComparerAble;
public class Sorted_hash implements Hash_adp {
	public Sorted_hash() {this.hash = new java.util.TreeMap();}    
	public Sorted_hash(ComparerAble comparer) {this.hash = new java.util.TreeMap(comparer);}
	public boolean Has(Object key) {return Has_base(key);}
	public Object GetByOrNull(Object key) {return Fetch_base(key);}
	public Object GetByOrFail(Object key) {return Get_by_or_fail_base(key);}
	public void Add(Object key, Object val) {Add_base(key, val);}
	public Hash_adp AddAndMore(Object key, Object val) {Add_base(key, val); return this;}
	public Hash_adp AddManyAsKeyAndVal(Object... ary) {
		for (Object itm : ary)
			Add_base(itm, itm);
		return this;
	}
	public void AddAsKeyAndVal(Object val) {Add_base(val, val);}
	public void AddIfDupeUseNth(Object key, Object val) {
		Object existing = Fetch_base(key); if (existing != null) Del(key);    // overwrite if exists
		Add(key, val);
	}
	public boolean AddIfDupeUse1st(Object key, Object val) {
		if (Has(key)) return false;
		Add(key, val);
		return true;
	}
	public void Del(Object key) {Del_base(key);}
	protected Object Get_by_or_fail_base(Object key) {
		if (key == null) throw ErrUtl.NewArgs("key cannot be null");
		if (!Has_base(key)) throw ErrUtl.NewArgs("key not found", "key", key);
		return Fetch_base(key);
	}
	public Object[] Values_array() {
				return hash.values().toArray();
			}
	public Object Del_val_at_0() {
				return hash.pollFirstEntry().getValue();
			}

	private final java.util.TreeMap hash;
	public int Len() {return hash.size();}
	public void Clear() {hash.clear();}                                                            
	private void Add_base(Object key, Object val) {hash.put(key, val);}                            
	private void Del_base(Object key) {hash.remove(key);}                                        
	private boolean Has_base(Object key) {return hash.containsKey(key);}                                
	private Object Fetch_base(Object key) {return hash.get(key);}                                    
	public Object Get_val_at_0() {return hash.firstEntry().getValue();}                                    
	public java.util.Iterator iterator() {return hash.values().iterator();}    
}
