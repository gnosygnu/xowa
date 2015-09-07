/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
class Mwm_tkn_ {
	public static final Mwm_tkn Owner__null = null;
	public static final Mwm_tkn[] Ary_empty = new Mwm_tkn[0];
	public static final int Uid__root = 0;
	public static final int
		Tid__root					=  0
	,	Tid__text					=  1
	,	Tid__ws						=  2
	,	Tid__curly					=  3
	,	Tid__brack					=  4
	,	Tid__lit_basic				=  5
	,	Tid__lit_uf_lt				=  6
	,	Tid__lit_uf_op				=  7
	,	Tid__dlm_basic				=  8
	,	Tid__dlm_uf_lt				=  9
	,	Tid__dlm_uf_op				= 10
	,	Tid__sub					= 11
	,	Tid__sup					= 12
	,	Tid__next_row				= 13
	,	Tid__next_cell				= 14
	,	Tid__fnc					= 15
	,	Tid__fnc_latex				= 16
	,	Tid__fnc_mw					= 17
	,	Tid__matrix_bgn				= 18
	,	Tid__matrix_end				= 19
	,	Tid__pmatrix_bgn			= 20
	,	Tid__pmatrix_end			= 21
	,	Tid__bmatrix_bgn			= 22
	,	Tid__bmatrix_end			= 23
	,	Tid__Bmatrix_bgn			= 24
	,	Tid__Bmatrix_end			= 25
	,	Tid__vmatrix_bgn			= 26
	,	Tid__vmatrix_end			= 27
	,	Tid__Vmatrix_bgn			= 28
	,	Tid__Vmatrix_end			= 29
	,	Tid__array_bgn				= 30
	,	Tid__array_end				= 31
	,	Tid__align_bgn				= 32
	,	Tid__align_end				= 33
	,	Tid__alignat_bgn			= 34
	,	Tid__alignat_end			= 35
	,	Tid__smallmatrix_bgn		= 36
	,	Tid__smallmatrix_end		= 37
	,	Tid__cases_bgn				= 38
	,	Tid__cases_end				= 39
	;
	public static final int Tid_len = 40;
	private static final byte[][] Bry__ary = Bry__ary__new();
	private static byte[][] Bry__ary__new() {
		byte[][] rv = new byte[Tid_len][];
		Reg_itm(rv, Tid__root					, "root");
		Reg_itm(rv, Tid__text					, "text");
		Reg_itm(rv, Tid__ws						, "ws");
		Reg_itm(rv, Tid__fnc					, "func");
		Reg_itm(rv, Tid__curly					, "curly");
		Reg_itm(rv, Tid__brack					, "brack");
		Reg_itm(rv, Tid__lit_basic				, "literal_basic");
		Reg_itm(rv, Tid__lit_uf_lt				, "literal_uf_lt");
		Reg_itm(rv, Tid__lit_uf_op				, "literal_uf_op");
		Reg_itm(rv, Tid__dlm_basic				, "delimiter_basic");
		Reg_itm(rv, Tid__dlm_uf_lt				, "delimiter_uf_lt");
		Reg_itm(rv, Tid__dlm_uf_op				, "delimiter_uf_op");
		Reg_itm(rv, Tid__sub					, "sub");
		Reg_itm(rv, Tid__sup					, "sup");
		Reg_itm(rv, Tid__next_row				, "next_row");
		Reg_itm(rv, Tid__next_cell				, "next_cell");
		Reg_itm(rv, Tid__fnc_latex				, "func_latex");
		Reg_itm(rv, Tid__fnc_mw					, "func_mediawiki");
		Reg_itm(rv, Tid__matrix_bgn				, "matrix_bgn");
		Reg_itm(rv, Tid__matrix_end				, "matrix_end");
		Reg_itm(rv, Tid__pmatrix_bgn			, "pmatrix_bgn");
		Reg_itm(rv, Tid__pmatrix_end			, "pmatrix_end");
		Reg_itm(rv, Tid__bmatrix_bgn			, "bmatrix_bgn");
		Reg_itm(rv, Tid__bmatrix_end			, "bmatrix_end");
		Reg_itm(rv, Tid__Bmatrix_bgn			, "Bmatrix_bgn");
		Reg_itm(rv, Tid__Bmatrix_end			, "Bmatrix_bgn");
		Reg_itm(rv, Tid__vmatrix_bgn			, "vmatrix_bgn");
		Reg_itm(rv, Tid__vmatrix_end			, "vmatrix_end");
		Reg_itm(rv, Tid__Vmatrix_bgn			, "Vmatrix_bgn");
		Reg_itm(rv, Tid__Vmatrix_end			, "Vmatrix_end");
		Reg_itm(rv, Tid__array_bgn				, "array_bgn");
		Reg_itm(rv, Tid__array_end				, "array_end");
		Reg_itm(rv, Tid__align_bgn				, "align_bgn");
		Reg_itm(rv, Tid__align_end				, "align_end");
		Reg_itm(rv, Tid__alignat_bgn			, "alignat_bgn");
		Reg_itm(rv, Tid__alignat_end			, "alignat_end");
		Reg_itm(rv, Tid__smallmatrix_bgn		, "smallmatrix_bgn");
		Reg_itm(rv, Tid__smallmatrix_end		, "smallmatrix_end");
		Reg_itm(rv, Tid__cases_bgn				, "cases_bgn");
		Reg_itm(rv, Tid__cases_end				, "cases_end");
		return rv;
	}
	private static void Reg_itm(byte[][] ary, int id, String name) {ary[id] = Bry_.new_a7(name);}
	public static byte[] Tid_to_bry(int tid) {return Bry__ary[tid];}
	public static boolean Tid_is_node(int tid) {
		switch (tid) {
			case Mwm_tkn_.Tid__root:
			case Mwm_tkn_.Tid__fnc:
			case Mwm_tkn_.Tid__curly:
			case Mwm_tkn_.Tid__brack:
				return true;
			default:
				return false;
		}
	}
	public static void Tkn_to_bry__bgn(Bry_bfr bfr, int indent, Mwm_tkn tkn) {
		if (indent > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent);
		bfr.Add(Mwm_tkn_.Tid_to_bry(tkn.Tid()));
		bfr.Add_byte(Byte_ascii.Paren_bgn);
		bfr.Add_int_variable(tkn.Src_bgn());
		bfr.Add_byte_comma().Add_int_variable(tkn.Src_end());
	}
	public static void Tkn_to_bry__end_head(Bry_bfr bfr) {
		bfr.Add_byte(Byte_ascii.Paren_end);
		bfr.Add_byte_nl();
	}
}
