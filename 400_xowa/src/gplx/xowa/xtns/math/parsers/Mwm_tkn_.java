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
		Tid__root			= 0
	,	Tid__text			= 1
	,	Tid__ws				= 2
	,	Tid__func			= 3
	,	Tid__curly			= 4
	,	Tid__brack			= 5
	;
	public static byte[]
		Bry__root			= Bry_.new_a7("root")
	,	Bry__text			= Bry_.new_a7("text")
	,	Bry__ws				= Bry_.new_a7("ws")
	,	Bry__func			= Bry_.new_a7("func")
	,	Bry__curly			= Bry_.new_a7("curly")
	,	Bry__brack			= Bry_.new_a7("brack")
	;
	public static byte[] Tid_to_bry(int tid) {
		switch (tid) {
			case Tid__root:			return Bry__root;
			case Tid__text:			return Bry__text;
			case Tid__ws:			return Bry__ws;
			case Tid__func:			return Bry__func;
			case Tid__curly:		return Bry__curly;
			case Tid__brack:		return Bry__brack;
			default:				throw Err_.new_unhandled(tid);
		}
	}
	public static boolean Tid_is_node(int tid) {
		switch (tid) {
			case Mwm_tkn_.Tid__text:
			case Mwm_tkn_.Tid__ws:
				return false;
			default:
				return true;
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
