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
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
public class Texvc_tkn_ {
	public static final int
		Tid__root					=  0
	,	Tid__func					=  1	// '\alpha', '\frac', '\begin', ...
	,	Tid__curly					=  2 	// '{'
	,	Tid__ws						=  3	// '\s', '\n', '\r', '\t'
	,	Tid__text					=  4	// 'abc', '123', ...
	,	Tid__literal				=  5	// '+', '<', ...
	,	Tid__delimiter				=  6	// '(', ')', ...
	,	Tid__sub					=  7	// '_'
	,	Tid__sup					=  8	// '^'
	,	Tid__next_row				=  9	// '\\'
	,	Tid__next_cell				= 10	// '%'
	,	Tid__brack_bgn				= 11	// '['
	,	Tid__brack_end				= 12	// '['
	;
	public static final int Tid_len = 13;
	public static final byte[][] Bry__ary = Bry_.Ary
	( "root", "func", "curly"
	, "ws", "text", "literal", "delimiter"
	, "sub", "sup", "next_row", "next_cell", "brack_bgn", "brack_end"
	);
	public static final Texvc_tkn[] Ary_empty = new Texvc_tkn[0];
	public static boolean Tid_is_node(int tid) {
		switch (tid) {
			case Texvc_tkn_.Tid__root: case Texvc_tkn_.Tid__func: case Texvc_tkn_.Tid__curly: return true;
			default: return false;
		}
	}
	public static String Print_dbg_str(Bry_bfr bfr, Texvc_tkn[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			ary[i].Print_dbg_bry(bfr, 0);
		return bfr.To_str_and_clear();
	}
	public static void Print_dbg_str__bgn(Bry_bfr bfr, int indent, Texvc_tkn tkn) {
		if (indent > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent * 2);
		bfr.Add(Bry__ary[tkn.Tid()]);
		bfr.Add_byte(Byte_ascii.Paren_bgn);
		bfr.Add_int_variable(tkn.Src_bgn());
		bfr.Add_byte_comma().Add_int_variable(tkn.Src_end());
	}
	public static void Print_dbg_str__end_head(Bry_bfr bfr) {
		bfr.Add_byte(Byte_ascii.Paren_end);
		bfr.Add_byte_nl();
	}
}
