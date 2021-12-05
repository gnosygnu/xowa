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
package gplx.objects.lists;
import gplx.objects.errs.ErrUtl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
public class GfoHashBase<K, V> implements Iterable<Map.Entry<K, V>> {
	protected final Map<K, V> map = new HashMap<>();

	public int Len() {return map.size();}
	public void Clear() {map.clear();}
	public void Add(GfoHashKeyFunc<K> keyFunc, V v) {Add(keyFunc.ToHashKey(), v);}
	public void Add(K k, V v) {
		if (k == null) {
			throw ErrUtl.NewFmt("key cannot be null; val={0}", v);
		}
		if (map.containsKey(k)) {
			throw ErrUtl.NewFmt("key already exists: key={0} val={1}", k, v);
		}
		map.put(k, v);
	}
	public void Del(K k) {map.remove(k);}
	public void Set(K k, V v) {map.put(k, v);}
	public boolean Has(K k) {return map.containsKey(k);}
	public V GetByOrNull(K k) {return map.get(k);}
	public V GetByOr(K k, V or) {
		V v = map.get(k);
		if (v == null) {
			v = or;
		}
		return v;
	}
	public V GetByOrFail(GfoHashKeyFunc<K> func) {return GetByOrFail(func.ToHashKey());}
	public V GetByOrFail(K k) {
		V v = map.get(k);
		if (v == null) {
			throw ErrUtl.NewFmt("val not found; key={0}", k);
		}
		return v;
	}

	@Override public Iterator<Map.Entry<K, V>> iterator() {return map.entrySet().iterator();}
	@Override public void forEach(Consumer<? super Map.Entry<K, V>> action) {map.entrySet().forEach(action);}
	@Override public Spliterator<Map.Entry<K, V>> spliterator() {return map.entrySet().spliterator();}
}
