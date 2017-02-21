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
package gplx.gfml; import gplx.*;
class IntObjHash_base {
	public int Count() {return count;} int count;
	public boolean Has(int key) {return Get_by(key) != null;}
	public Object Get_by(int key) {
		if (key < 0) throw Err_.new_wo_type("key must be >= 0", "key", key);
		if (key > maxKey) return null;
		Object[] subAry = FetchSubAry(key);
		return subAry == null ? null : subAry[subIdx];
	}
	public void Add(int key, Object obj) {PutObjAtKey(key, obj, true);}
	public void Set(int key, Object obj) {PutObjAtKey(key, obj, false);}
	public void Del(int key) {
		if (key > maxKey) return; // key does not exist; exit
		Object[] subAry = FetchSubAry(key);
		if (subAry == null) return;
		subAry[subIdx] = null;
		count--;
	}
	public void Clear() {
		rootAry = new Object[0];
		count = 0;
		maxKey = -1;
	}
	void PutObjAtKey(int key, Object obj, boolean isAdd) {
		if (key < 0) throw Err_.new_wo_type("key must be >= 0", "key", key);
		if (obj == null) throw Err_.new_wo_type("Object cannot be null; call .Del on key instead", "key", key);
		if (key > maxKey) ExpandRootAry(key);
		Object[] subAry = FetchSubAry(key);
		if (subAry == null) {
			subAry = new Object[subAryLength];
			rootAry[rootIdx] = subAry;
		}
		Object curVal = subAry[subIdx];
		if ( isAdd && curVal != null) throw Err_.new_wo_type(".Add cannot be called on non-null vals; call .Set instead", "key", key, "val", curVal);
		if (!isAdd && curVal == null) throw Err_.new_wo_type(".Set cannot be called on null vals; call .Add instead", "key", key);
		subAry[subIdx] = obj;
		if (isAdd) count++;
	}
	void ExpandRootAry(int key) {
		int newRootAryBound = (key / subAryLength) + 1;
		Object[] newRootAry = new Object[newRootAryBound];
		Array_.Copy(rootAry, newRootAry);
		rootAry = newRootAry;
		maxKey = (rootAry.length * subAryLength) - 1;
	}
	Object[] FetchSubAry(int key) {
		rootIdx = key / subAryLength;
		subIdx = key % subAryLength;
		return (Object[])rootAry[rootIdx];
	}
	Object[] rootAry = new Object[0]; int maxKey = -1;
	int rootIdx, subIdx;	// NOTE: these are temp values that are cached at class level for PERF (originally out)
	public Object Bay() {return bay;} public void Bay_set(Object v) {bay = v;} Object bay = null;
	static final int subAryLength = 16;
}
class IntObjHash_base_ {
        public static IntObjHash_base new_() {return new IntObjHash_base();}
	public static IntObjHash_base as_(Object obj) {return obj instanceof IntObjHash_base ? (IntObjHash_base)obj : null;}
}
