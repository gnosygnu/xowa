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
package gplx.core.lists.rings; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
public class Ring__string {
	String[] ary = String_.Ary_empty;
	public int Len() {return len;} int len;
	public Ring__string Max_(int v) {
		if (v != max) {
			ary = new String[v];
			max = v;
		}
		return this;
	} int max;
	public void Clear() {
		for (int i = 0; i < max; i++) {
			ary[i] = null;
		}
		len = nxt = 0;
	}
	int nxt;
	public void Push(String v) {
		int idx = nxt++;
		if (idx == max) {
			idx = 0;
		}
		if (nxt == max) {
			nxt = 0;
		}
		ary[idx] = v;
		if (len < max)
			++len;
	}
	public String[] Xto_str_ary() {
		String[] rv = new String[len];
		int ary_i = nxt - 1;
		for (int rv_i = len - 1; rv_i > -1; rv_i--) {
			if (ary_i == -1) {
				ary_i = max - 1;
			} 
			rv[rv_i] = ary[ary_i];
			--ary_i;
		}
		return rv;
	}
}
