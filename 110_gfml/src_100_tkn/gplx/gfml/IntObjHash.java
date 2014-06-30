/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.gfml; import gplx.*;
class IntObjHash_base {
	public int Count() {return count;} int count;
	public boolean Has(int key) {return Fetch(key) != null;}
	public Object Fetch(int key) {
		if (key < 0) throw Err_.new_("key must be >= 0").Add("key", key);
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
		if (key < 0) throw Err_.new_("key must be >= 0").Add("key", key);
		if (obj == null) throw Err_.new_("Object cannot be null; call .Del on key instead").Add("key", key);
		if (key > maxKey) ExpandRootAry(key);
		Object[] subAry = FetchSubAry(key);
		if (subAry == null) {
			subAry = new Object[subAryLength];
			rootAry[rootIdx] = subAry;
		}
		Object curVal = subAry[subIdx];
		if ( isAdd && curVal != null) throw Err_.new_(".Add cannot be called on non-null vals; call .Set instead").Add("key", key).Add("val", curVal);
		if (!isAdd && curVal == null) throw Err_.new_(".Set cannot be called on null vals; call .Add instead").Add("key", key);
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
