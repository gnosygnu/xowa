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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_apos_tkn_ {
	public static final int 
	  Cmd_nil = 0
	, Cmd_i_bgn = 1, Cmd_i_end = 2, Cmd_b_bgn = 3, Cmd_b_end = 4
	, Cmd_bi_bgn = 5, Cmd_ib_bgn = 6, Cmd_ib_end = 7, Cmd_bi_end = 8
	, Cmd_bi_end__b_bgn = 9, Cmd_ib_end__i_bgn = 10, Cmd_b_end__i_bgn = 11, Cmd_i_end__b_bgn = 12;
	public static final byte[][] Cmds 
	= new byte[][] 
	{ Bry_.new_a7("nil")
	, Bry_.new_a7("i+"), Bry_.new_a7("i-"), Bry_.new_a7("b+"), Bry_.new_a7("b-")
	, Bry_.new_a7("bi+"), Bry_.new_a7("ib+"), Bry_.new_a7("ib-"), Bry_.new_a7("bi-")
	, Bry_.new_a7("bi-b+"), Bry_.new_a7("ib-i+"), Bry_.new_a7("b-i+"), Bry_.new_a7("i-b+")
	};
	public static String Cmd_str(int id) {return String_.new_u8(Cmds[id]);}
	public static final int Len_ital = 2, Len_bold = 3, Len_dual = 5, Len_apos_bold = 4;
	public static final int Typ_ital = 2, Typ_bold = 3, Typ_dual = 5;
	public static final int State_nil = 0, State_i = 1, State_b = 2, State_bi = 3, State_ib = 4, State_dual = 5;
}
