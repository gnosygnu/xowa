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
package gplx.types.basics.arrays;
import gplx.types.errs.ErrUtl;
public class IntAry {
	private int max;
	public IntAry(int max) {
		this.len = 0;
		this.max = max;
		this.ary = new int[max];
	}
	public int Len() {return len;} private int len;
	public int[] Ary() {return ary;} private int[] ary;
	public void Clear() {
		for (int i = 0; i < len; ++i)
			ary[i] = 0;
		len = 0;
	}
	public int GetAt(int i) {
		if (i > -1 && i < len)
			return ary[i];
		else
			throw ErrUtl.NewArgs("index is invalid", "i", i, "len", len);
	}
	public void Add(int v) {
		if (len == max) {
			int newMax = max * 2;
			int[] newAry = new int[newMax];
			for (int i = 0; i < len; ++i)
				newAry[i] = ary[i];
			this.ary = newAry;
			this.max = newMax;
		}
		ary[len] = v;
		++len;
	}
	public int PopOrFail() {
		if (len == 0) throw ErrUtl.NewArgs("stack is empty");
		return PopOr(-1);
	}
	public int PopOr(int or) {
		if (len == 0) return or;
		int rv = ary[len - 1];
		--len;
		return rv;
	}
	public int IdxOf(int find) {
		for (int i = len - 1; i > -1; --i) {
			if (ary[i] == find)
				return i;
		}
		return NotFound;
	}
	public boolean DelFromEnd(int find) {
		int findIdx = IdxOf(find); if (findIdx == NotFound) return false;
		int lastIdx = len - 1;
		for (int i = findIdx; i < lastIdx; ++i)
			ary[i] = ary[i + 1];
		ary[lastIdx] = 0;
		--len;
		return true;
	}
	public static final int NotFound = -1;
}
