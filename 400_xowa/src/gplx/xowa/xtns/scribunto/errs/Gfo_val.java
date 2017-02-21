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
package gplx.xowa.xtns.scribunto.errs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public interface Gfo_val extends CompareAble {
	boolean Eq(Gfo_val rhs);
	boolean Match_1(Gfo_comp_op_1 op, Gfo_val comp);
}
class Gfo_val_mok implements Gfo_val {
	public String s = "";
	public boolean Eq(Gfo_val rhs) {return true;}
	public int compareTo(Object obj) {return 0;}
	public boolean Match_1(Gfo_comp_op_1 op, Gfo_val comp_obj) {
		Gfo_val_mok comp = (Gfo_val_mok)comp_obj;
		return op.Comp_str(s, comp.s);
	}
}
