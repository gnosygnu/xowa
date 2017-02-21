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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_2_ref {
	public Int_2_ref() {}
	public Int_2_ref(int v0, int v1) {Val_all_(v0, v1);}
	public int Val_0() {return val_0;} public Int_2_ref Val_0_(int v) {val_0 = v; return this;} private int val_0;
	public int Val_1() {return val_1;} public Int_2_ref Val_1_(int v) {val_1 = v; return this;} private int val_1;
	public Int_2_ref Val_all_(int v0, int v1) {val_0 = v0; val_1 = v1; return this;}
	@Override public int hashCode() {
		int hash = 23;
		hash = (hash * 31) + val_0;
		hash = (hash * 31) + val_1;
		return hash;
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Int_2_ref comp = (Int_2_ref)obj;
		return val_0 == comp.val_0 && val_1 == comp.val_1;
	}
	public static Int_2_ref parse(String raw) {
		try {
			String[] itms = String_.Split(raw, ",");
			int v0 = Int_.parse(itms[0]);
			int v1 = Int_.parse(itms[1]);
			return new Int_2_ref(v0, v1);
		} catch (Exception e) {Err_.Noop(e); throw Err_.new_parse("Int_2_ref", raw);}
	}
	public static Int_2_ref[] parse_ary_(String raw) {
		try {
			String[] itms = String_.Split(raw, ";");
			int itms_len = itms.length;
			Int_2_ref[] rv = new Int_2_ref[itms_len];
			for (int i = 0; i < itms_len; i++) {
				String[] vals = String_.Split(itms[i], ",");
				int v0 = Int_.parse(vals[0]);
				int v1 = Int_.parse(vals[1]);
				rv[i] = new Int_2_ref(v0, v1);
			}
			return rv;
		} catch (Exception e) {Err_.Noop(e); throw Err_.new_parse("Int_2_ref[]", raw);}
	}
}
