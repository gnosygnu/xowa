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
package gplx.types.commons.lists;
import gplx.types.errs.ErrUtl;

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
			throw ErrUtl.NewFmt("key cannot be null; val={0}", itm);
		}
		if (hash.contains(itm)) {
			throw ErrUtl.NewFmt("key already exists: key={0} val={1}", itm);
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
