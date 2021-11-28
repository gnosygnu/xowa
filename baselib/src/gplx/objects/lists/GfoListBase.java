package gplx.objects.lists;

import gplx.objects.errs.Err_;
import gplx.objects.events.GfoEvent;
import gplx.objects.events.GfoEventOwner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GfoListBase<E> implements Iterable<E>, GfoEventOwner {
    protected final List<E> list = new ArrayList<>();
    private boolean eventsEnabled;

    public GfoListBase() {
		this.itemsChanged = new GfoEvent<>("Add", this);
    }

    public int Len() {return list.size();}
    public E GetAt(int i) {return list.get(i);}
    public E GetAtBgn() {return list.get(0);}
    public E GetAtEnd() {return list.get(list.size() - 1);}

	@Override
	public boolean EventsEnabled() {
		return eventsEnabled;
    }

	@Override
	public void EventsEnabledSet(boolean v) {
		eventsEnabled = v;
	}
	public GfoEvent<ItemsChangedArg<E>> ItemsChanged() {return itemsChanged;} private final GfoEvent<ItemsChangedArg<E>> itemsChanged;
    public GfoListBase<E> Add(E itm) {
    	list.add(itm);
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Add, GfoListBase.NewFromArray(itm)));
		}
		return this;
	}
    public GfoListBase<E> AddMany(E... ary) {
    	for (E itm : ary) {
    		this.Add(itm);
    	}
    	return this;
	}
    public GfoListBase<E> AddMany(GfoListBase<E> list) {
    	for (E itm : list) {
    		this.Add(itm);
    	}
    	return this;
	}
    public void AddAt(int i, E itm) {
    	list.add(i, itm);
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Add, GfoListBase.NewFromArray(itm)));
		}
    }
    public void Set(int i, E itm) {
    	list.set(i, itm);
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Set, GfoListBase.NewFromArray(itm)));
		}
    }
    public void DelAt(int i) {
    	list.remove(i);
    }
    public void DelBetween(int i) {DelBetween(i, list.size() - 1);}
    public void DelBetween(int bgn, int end) {
    	// put items in array for event
    	int len = end - bgn;
    	GfoListBase<E> old = new GfoListBase<>();
    	for (int i = 0; i < len; i++) {
    		old.Add(list.get(i + bgn));
    	}

    	// actually delete
    	for (int i = bgn; i < end; i++) {
			list.remove(bgn);
		}
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Del, old));
		}
    }

    public void DelAtEnd() {
    	int idx = list.size() - 1;
    	E itm = list.get(idx);
    	list.remove(idx);
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Del, GfoListBase.NewFromArray(itm)));
		}
	}
    public void Clear() {
    	GfoListBase<E> old = new GfoListBase<>();
    	for (E itm : list)
    		old.Add(itm);
    	list.clear();
    	if (eventsEnabled) {
			itemsChanged.Run(new ItemsChangedArg<>(ItemsChangedType.Clear, old));
		}
    }
    public void Sort(GfoComparator<E> comparator) {
		list.sort(comparator);
    }
    public E[] ToAry(Class<?> clz) {
    	int len = list.size();
    	E[] rv = (E[])Array.newInstance(clz, len);
    	for (int i = 0; i < len; i++)
    		rv[i] = list.get(i);
    	return rv;
    }
    public String[] ToStringAry() {
    	int len = list.size();
    	String[] rv = new String[len];
    	for (int i = 0; i < len; i++)
    		rv[i] = list.get(i).toString();
    	return rv;
    }

	@Override
	public Iterator<E> iterator() {
		return new GfoListBaseIterator(list);
	}

	class GfoListBaseIterator implements Iterator<E> {
		private final List<E> list;
		private int curIdx;
		private int len;
		public GfoListBaseIterator(List<E> list) {
			this.list = list;
			this.len = list.size();
		}
		@Override
		public boolean hasNext() {
			return curIdx < len;
		}

		@Override
		public E next() {
			return (E)list.get(curIdx++);
		}
		@Override
		public void remove() {
			throw Err_.New_unimplemented();
		}
	}

	public static <E> GfoListBase<E> NewFromArray(E... array) {
		GfoListBase<E> rv = new GfoListBase<>();
		for (E itm : array) {
			rv.Add(itm);
		}
		return rv;
	}
}
