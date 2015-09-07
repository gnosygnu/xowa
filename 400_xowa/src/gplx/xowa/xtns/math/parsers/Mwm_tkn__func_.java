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
import gplx.core.btries.*;
class Mwm_tkn__func_ {
	public static final int
		Tid__space			= 0
	,	Tid__lit_uf_lt		= 1
	,	Tid__dlm_uf_lt		= 2
	,	Tid__lit_uf_op		= 3
	,	Tid__dlm_uf_op		= 4
	,	Tid__fnc_latex		= 5
	,	Tid__fnc_mw			= 6
	;
	public Btrie_slim_mgr Make_trie() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();	// NOTE: texvc tkns are ascii
		Make_itm(rv, Tid__space			, " ", "\t", "\n", "\r");
		Make_itm(rv, Tid__lit_uf_lt		, ",", ":", ";", "?", "!", "'");
		Make_itm(rv, Tid__dlm_uf_lt		, "(", ")", ".");
		Make_itm(rv, Tid__lit_uf_op		, "+", "-", "*", "=");
		Make_itm(rv, Tid__dlm_uf_op		, "/", "|");
		Make_itm(rv, Tid__fnc_latex		, "arccos", "arcsin", "arctan", "arg", "cos", "cosh", "cot", "coth", "csc"
			, "deg", "det", "dim", "exp", "gcd", "hom", "inf", "ker", "lg", "lim", "liminf", "limsup", "ln", "log"
			, "max", "min", "Pr", "sec", "sin", "sinh", "sup", "tan", "tanh");
		Make_itm(rv, Tid__fnc_mw		, "arccot", "arcsec", "arccsc", "sgn", "sen");
		return rv;
	}
	private void Make_itm(Btrie_slim_mgr trie, int tid, String... ary) {
		for (String itm : ary)
			trie.Add_bry_int(Bry_.new_a7(itm), tid);
	}
//let alpha = ['a'-'z' 'A'-'Z']
//let literal_id = ['a'-'z' 'A'-'Z']
//let literal_mn = ['0'-'9']
//let boxchars  = ['0'-'9' 'a'-'z' 'A'-'Z' '+' '-' '*' ',' '=' '(' ')' ':' '/' ';' '?' '.' '!' '\'' '`' ' ' '\128'-'\255']
//let aboxchars = ['0'-'9' 'a'-'z' 'A'-'Z' '+' '-' '*' ',' '=' '(' ')' ':' '/' ';' '?' '.' '!' '\'' '`' ' ']
}
