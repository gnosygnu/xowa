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
package gplx.xowa.xtns.pfuncs.exprs;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
class Num_tkn implements Expr_tkn {
	public int Tid() {return Expr_tkn_.Tid_number;}		
	public byte[] Val_ary()	{return val_ary;} private byte[] val_ary;
	public String Val_str()	{return StringUtl.NewU8(val_ary);}
	public Num_tkn(int val_int) {
		this.val_int = val_int;
		this.val_ary = new byte[] {ByteUtl.ByInt(val_int + AsciiByte.Num0)};
	}	int val_int;
}
