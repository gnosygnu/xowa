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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_lnki_align_h_ {
	public static final byte Null = 0, None = 1, Left = 2, Center = 3, Right = 4;	// SERIALIZED
	public static final byte[][] Html_names = new byte[][]
	{ Object_.Bry__null
	, Bry_.new_a7("none")
	, Bry_.new_a7("left")
	, Bry_.new_a7("center")
	, Bry_.new_a7("right")
	};
	public static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("tnone"	, None)
	.Add_str_byte("tleft"	, Left)
	.Add_str_byte("tcenter"	, Center)
	.Add_str_byte("tright"	, Right)
	;
	public static byte[] To_bry(int v) {return Html_names[v];}
}
class Xop_lnki_align_v_ {
	public static final byte None = 0, Top		= 1, Middle		= 2, Bottom		 = 4, Super		= 8, Sub        = 16, TextTop = 32, TextBottom = 64, Baseline = 127;
}
