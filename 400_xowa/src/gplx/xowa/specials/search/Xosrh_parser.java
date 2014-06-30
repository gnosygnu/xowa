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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
class Xosrh_parser {
	public Xosrh_qry_itm Parse(byte[] src) {
		this.src = src;
		Xosrh_qry_tkn[] tkns = Xosrh_scanner._.Scan(src);
		return Parse_itm_or(make_ctx.Init(tkns));
	}	private Xosrh_parser_ctx make_ctx = new Xosrh_parser_ctx(); byte[] src;
	Xosrh_qry_itm Parse_itm_or(Xosrh_parser_ctx make_ctx) {
		Xosrh_qry_itm lhs = Parse_itm_and(make_ctx);
		while (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_or)) {
			make_ctx.Move_next();
			Xosrh_qry_itm rhs = Parse_itm_and(make_ctx);
			lhs = Xosrh_qry_itm.nde_(Xosrh_qry_itm.Tid_or, lhs, rhs);
		}
		return lhs;
	}
	Xosrh_qry_itm Parse_itm_and(Xosrh_parser_ctx make_ctx) {
		Xosrh_qry_itm lhs = Parse_itm_not(make_ctx);
		while (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_and)) {
			make_ctx.Move_next();
			Xosrh_qry_itm rhs = Parse_itm_not(make_ctx);
			lhs = Xosrh_qry_itm.nde_(Xosrh_qry_itm.Tid_and, lhs, rhs);
		}
		return lhs;
	}
	Xosrh_qry_itm Parse_itm_not(Xosrh_parser_ctx make_ctx) {
		Xosrh_qry_itm lhs = Parse_itm_leaf(make_ctx);
		while (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_not)) {
			make_ctx.Move_next();
			Xosrh_qry_itm rhs = Parse_itm_leaf(make_ctx);
			lhs = Xosrh_qry_itm.nde_(Xosrh_qry_itm.Tid_not, null, rhs);
		}
		return lhs;
	}
	Xosrh_qry_itm Parse_itm_leaf(Xosrh_parser_ctx make_ctx) {
		if (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_paren_bgn)) {
			make_ctx.Move_next();
			Xosrh_qry_itm lhs = Parse_itm_or(make_ctx);
			if (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_paren_end)) make_ctx.Move_next();	// skip token
			return lhs;
		}
		else if (make_ctx.Cur_tid(Xosrh_qry_tkn.Tid_eos))
			return Xosrh_qry_itm.Null;
		else {
			Xosrh_qry_tkn word_tkn = make_ctx.Move_next();
			if (word_tkn.Tid() == Xosrh_qry_tkn.Tid_not) {
				word_tkn = make_ctx.Move_next();
				if (word_tkn == null) return Xosrh_qry_itm.Null; // occurs in "a -"
				Xosrh_qry_itm word_itm = Xosrh_qry_itm.word_(src, word_tkn);
				return Xosrh_qry_itm.nde_(Xosrh_qry_itm.Tid_not, null, word_itm);
			}
			else
				return Xosrh_qry_itm.word_(src, word_tkn);
		}
	}
	public static final Xosrh_parser _ = new Xosrh_parser(); Xosrh_parser() {}
}
class Xosrh_parser_ctx {
	public Xosrh_parser_ctx Init(Xosrh_qry_tkn[] ary) {
		this.ary = ary;
		ary_len = ary.length; 
		this.pos = 0;
		return this;
	}	private Xosrh_qry_tkn[] ary; int pos = 0; int ary_len;
	public boolean Cur_tid(byte tid) {return pos < ary_len ? tid == ary[pos].Tid(): tid == Xosrh_qry_tkn.Tid_eos;}
	public Xosrh_qry_tkn Move_next() {
		Xosrh_qry_tkn rv = null; 
		if (pos < ary_len) rv = ary[pos++];
		return rv;
	}
}
