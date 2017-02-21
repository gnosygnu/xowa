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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*;
class Paren_end_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_paren_rhs;}
	public byte[] Val_ary() {return val_ary;} private byte[] val_ary = Bry_.new_u8(val_str);
	public String Val_str() {return val_str;} static final    String val_str = ")";
	public static Paren_end_tkn Instance = new Paren_end_tkn(); Paren_end_tkn() {}
}
