package gplx.objects.lists;

import gplx.objects.errs.Err_;

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
    		throw Err_.New_fmt("key cannot be null; val={0}", v);
    	}
    	if (map.containsKey(k)) {
    		throw Err_.New_fmt("key already exists: key={0} val={1}", k, v);
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
    		throw Err_.New_fmt("val not found; key={0}", k);
    	}
    	return v;
	}

	@Override public Iterator<Map.Entry<K, V>> iterator() {return map.entrySet().iterator();}
	@Override public void forEach(Consumer<? super Map.Entry<K, V>> action) {map.entrySet().forEach(action);}
	@Override public Spliterator<Map.Entry<K, V>> spliterator() {return map.entrySet().spliterator();}
}
