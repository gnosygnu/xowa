package gplx.objects.lists;

import gplx.objects.GfoKeyVal;
import gplx.objects.errs.Err_;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;

public class GfoIndexedList<K, V> implements Iterable<V> {
    private final GfoListBase<V> list = new GfoListBase<>();
    private final GfoHashBase<K, GfoIndexedListEntry<K, V>> hash = new GfoHashBase<>();
    public boolean Has(K key) {
        return hash.Has(key);
    }
    public int Len() {return hash.Len();}
    public V GetAt(int key) {
        return list.GetAt(key);
    }
    public V GetByOrFail(GfoHashKeyFunc<K> func) {return GetByOrFail(func.ToHashKey());}
    public V GetByOrFail(K key) {
        GfoIndexedListEntry<K, V> val = hash.GetByOrNull(key);
        if (val == null) {
            throw Err_.New_fmt("Unknown key: {0}", key);
        }
        return val.Val();
    }
    public V GetByOr(K key, V or) {
        GfoIndexedListEntry<K, V> val = hash.GetByOrNull(key);
        return val == null ? or : val.Val();
    }
    public Optional<V> GetBy(K key) {
        GfoIndexedListEntry<K, V> val = hash.GetByOrNull(key);
        return val == null ? Optional.empty() : Optional.of(val.Val());
    }
    public V GetByOrNull(K key) {
        GfoIndexedListEntry<K, V> val = hash.GetByOrNull(key);
        return val == null ? null : val.Val();
    }
    public void Sort(GfoComparator<V> comparator) {
		list.Sort(comparator);
    }
    public void AddAt(int i, K key, V val) {
        for (Map.Entry<K, GfoIndexedListEntry<K, V>> mapEntry : hash) {
            GfoIndexedListEntry<K, V> mapEntryItem = mapEntry.getValue();
            if (mapEntryItem.ListIdx() >= i) {
                mapEntryItem.ListIdxSet(mapEntryItem.ListIdx() + 1);
            }
        }
        hash.Add(key, new GfoIndexedListEntry<>(key, val, i));
        list.AddAt(i, val);
    }
    public void DelAt(int i, K key) {
        for (Map.Entry<K, GfoIndexedListEntry<K, V>> mapEntry : hash) {
            GfoIndexedListEntry<K, V> mapEntryItem = mapEntry.getValue();
            if (mapEntryItem.ListIdx() > i) {
                mapEntryItem.ListIdxSet(mapEntryItem.ListIdx() - 1);
            }
        }
        hash.Del(key);
        list.DelAt(i);
    }
    public void Add(GfoHashKeyFunc<K> func, V val) {Add(func.ToHashKey(), val);}
    public void Add(K key, V val) {
        hash.Add(key, new GfoIndexedListEntry<>(key, val, list.Len()));
        list.Add(val);
    }
    public void Set(K key, V val) {
        GfoIndexedListEntry<K, V> entry = hash.GetByOrNull(key);
        if (entry == null) {
            this.Add(key, val);
        }
        else {
            entry.ValSet(val);
            list.Set(entry.ListIdx(), val);
        }
    }
    public void DelBy(K key) {
        GfoIndexedListEntry<K, V> entry = hash.GetByOrNull(key);
        if (entry == null) {
            return; // if key is unknown, do nothing; matches behavior of java.util.Hashtable
        }
        hash.Del(key);
        int listIdx = entry.ListIdx();
        list.DelAt(listIdx);
        for (Map.Entry<K, GfoIndexedListEntry<K, V>> mapEntry : hash) {
            GfoIndexedListEntry<K, V> mapEntryItem = mapEntry.getValue();
            if (mapEntryItem.ListIdx() > listIdx) {
                mapEntryItem.ListIdxSet(mapEntryItem.ListIdx() - 1);
            }
        }
    }
    public void Clear() {
        list.Clear();
        hash.Clear();
    }
    public V[] ToAry(Class<?> clz) {return list.ToAry(clz);}
    public GfoKeyVal<K, V>[] ToKvAry() {
        int len = this.Len();
        GfoKeyVal<K, V>[] ary = new GfoKeyVal[len];
        int idx = 0;
        for (Map.Entry<K, GfoIndexedListEntry<K, V>> mapEntry : hash) {
            GfoIndexedListEntry<K, V> mapEntryItem = mapEntry.getValue();
            ary[idx++] = new GfoKeyVal<>(mapEntryItem.Key(), mapEntryItem.Val());
        }
        return ary;
    }

	@Override public Iterator<V> iterator() {return list.iterator();}
	@Override public void forEach(Consumer<? super V> action) {list.forEach(action);}
	@Override public Spliterator<V> spliterator() {return list.spliterator();}
}
class GfoIndexedListEntry<K, V> {
    private final K key;
    private V val;
    private int listIdx;
    public GfoIndexedListEntry(K key, V val, int listIdx) {
        this.key = key;
        this.val = val;
        this.listIdx = listIdx;
    }
    public K Key() {return key;}
    public V Val() {return val;} public void ValSet(V v) {this.val = v;}
    public int ListIdx() {return listIdx;} public void ListIdxSet(int v) {listIdx = v;}
}
