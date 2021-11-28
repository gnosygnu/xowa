package gplx.objects.lists;

import gplx.objects.errs.Err_;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class GfoSetBase<I> implements Iterable<I> {
    protected final Set<I> hash = new HashSet<>();

    public int Len() {return hash.size();}
    public void Clear() {hash.clear();}
    public GfoSetBase<I> AddMany(I... ary) {
    	for (I itm : ary) {
			Add(itm);
		}
		return this;
    }
    public void Add(GfoHashKeyFunc<I> keyFunc) {Add(keyFunc.ToHashKey());}
    public GfoSetBase<I> Add(I itm) {
    	if (itm == null) {
    		throw Err_.New_fmt("key cannot be null; val={0}", itm);
    	}
    	if (hash.contains(itm)) {
    		throw Err_.New_fmt("key already exists: key={0} val={1}", itm);
    	}
    	hash.add(itm);
		return this;
    }
    public void Del(I itm) {hash.remove(itm);}
    public boolean Has(I itm) {return hash.contains(itm);}

	@Override public Iterator<I> iterator() {return hash.iterator();}
	@Override public void forEach(Consumer<? super I> action) {hash.forEach(action);}
	@Override public Spliterator<I> spliterator() {return hash.spliterator();}
}
