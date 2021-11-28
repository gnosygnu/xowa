package gplx.objects;

public class GfoKeyVal<K, V> {
    public GfoKeyVal(K key, V val) {
        this.key = key;
        this.val = val;
    }
    public K Key() {return key;} private final K key;
    public V Val() {return val;} public void ValSet(V v) {this.val = v;} private V val;
}
