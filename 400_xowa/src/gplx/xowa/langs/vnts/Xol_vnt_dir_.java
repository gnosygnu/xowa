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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_vnt_dir_ {
	public static final int Tid__none = 0, Tid__uni = 1, Tid__bi = 2;
	public static int Parse(byte[] v) {return hash.Get_as_int_or(v, Tid__none);}
	private static final byte[] Bry__none = Bry_.new_a7("disable"), Bry__uni = Bry_.new_a7("unidirectional"), Bry__bi = Bry_.new_a7("bidirectional");
	private static final Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_bry_int(Bry__none	, Tid__none)
	.Add_bry_int(Bry__uni	, Tid__uni)
	.Add_bry_int(Bry__bi	, Tid__bi);
}
