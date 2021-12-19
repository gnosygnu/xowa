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
package gplx.types.basics.lists;
import gplx.types.basics.arrays.IntAryUtl;
public class IntList {
	private int capacity;
	private int[] ary;
	private int aryLen, aryMax;
	public IntList(int capacity) {
		this.capacity = capacity;
		this.ary = new int[capacity];
	}
	public void Add(int uid) {
		int newLen = aryLen + 1;
		if (newLen > aryMax) {
			aryMax += 16;
			int[] newAry = new int[aryMax];
			IntAryUtl.CopyTo(ary, aryLen, newAry);
			ary = newAry;
		}
		ary[aryLen] = uid;
		aryLen = newLen;
	}
	public int Len() {return aryLen;}
	public int GetAt(int i) {return ary[i];}
	public void Clear() {
		if (aryLen > capacity) {
			ary = (capacity == 0) ? IntAryUtl.Empty : new int[capacity];
		}
		aryLen = aryMax = 0;
	}
	public int[] ToAry() {
		int[] rv = new int[aryLen];
		for (int i = 0; i < aryLen; i++)
			rv[i] = ary[i];
		return rv;
	}
}
