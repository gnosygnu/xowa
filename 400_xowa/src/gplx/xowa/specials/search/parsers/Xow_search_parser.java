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
package gplx.xowa.specials.search.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
public class Xow_search_parser {
	private Xow_search_parser_ctx parse_ctx = new Xow_search_parser_ctx(); private byte[] src;
	public Xow_search_matcher Parse(byte[] src) {
		this.src = src;
		Xow_search_tkn[] tkns = Xow_search_scanner.I.Scan(src);
		return Parse_itm_or(parse_ctx.Init(tkns));
	}
	private Xow_search_matcher Parse_itm_or(Xow_search_parser_ctx parse_ctx) {
		Xow_search_matcher lhs = Parse_itm_and(parse_ctx);
		while (parse_ctx.Cur_tid(Xow_search_tkn.Tid_or)) {
			parse_ctx.Move_next();
			Xow_search_matcher rhs = Parse_itm_and(parse_ctx);
			lhs = new_join(Xow_search_matcher.Tid_or, lhs, rhs);
		}
		return lhs;
	}
	private Xow_search_matcher Parse_itm_and(Xow_search_parser_ctx parse_ctx) {
		Xow_search_matcher lhs = Parse_itm_not(parse_ctx);
		while (parse_ctx.Cur_tid(Xow_search_tkn.Tid_and)) {
			parse_ctx.Move_next();
			Xow_search_matcher rhs = Parse_itm_not(parse_ctx);
			lhs = new_join(Xow_search_matcher.Tid_and, lhs, rhs);
		}
		return lhs;
	}
	private Xow_search_matcher Parse_itm_not(Xow_search_parser_ctx parse_ctx) {
		Xow_search_matcher lhs = Parse_itm_leaf(parse_ctx);
		while (parse_ctx.Cur_tid(Xow_search_tkn.Tid_not)) {
			parse_ctx.Move_next();
			Xow_search_matcher rhs = Parse_itm_leaf(parse_ctx);
			lhs = new_join(Xow_search_matcher.Tid_not, null, rhs);
		}
		return lhs;
	}
	private Xow_search_matcher Parse_itm_leaf(Xow_search_parser_ctx parse_ctx) {
		if (parse_ctx.Cur_tid(Xow_search_tkn.Tid_paren_bgn)) {
			parse_ctx.Move_next();
			Xow_search_matcher lhs = Parse_itm_or(parse_ctx);
			if (parse_ctx.Cur_tid(Xow_search_tkn.Tid_paren_end)) parse_ctx.Move_next();	// skip token
			return lhs;
		}
		else if (parse_ctx.Cur_tid(Xow_search_tkn.Tid_eos))
			return Xow_search_matcher.Null;
		else {
			Xow_search_tkn word_tkn = parse_ctx.Move_next();
			if (word_tkn.Tid() == Xow_search_tkn.Tid_not) {
				word_tkn = parse_ctx.Move_next();
				if (word_tkn == null) return Xow_search_matcher.Null; // occurs in "a -"
				Xow_search_matcher word_itm = new_word(word_tkn, src);
				return new_join(Xow_search_matcher.Tid_not, null, word_itm);
			}
			else
				return new_word(word_tkn, src);
		}
	}
	private static Xow_search_matcher new_word(Xow_search_tkn tkn, byte[] src) {
		int tid = tkn.Tid() == Xow_search_tkn.Tid_word ? Xow_search_matcher.Tid_word : Xow_search_matcher.Tid_word_quote; 
		return new Xow_search_matcher(tid, tkn.Val(src), null, null);
	}
	private static Xow_search_matcher new_join(int tid, Xow_search_matcher lhs, Xow_search_matcher rhs) {return new Xow_search_matcher(tid, null, lhs, rhs);}
	public static final Xow_search_parser _ = new Xow_search_parser(); Xow_search_parser() {}
}
class Xow_search_parser_ctx {
	private Xow_search_tkn[] ary; private int pos = 0; private int ary_len;
	public Xow_search_parser_ctx Init(Xow_search_tkn[] ary) {
		this.ary = ary;
		this.ary_len = ary.length; 
		this.pos = 0;
		return this;
	}
	public boolean Cur_tid(byte tid) {return pos < ary_len ? tid == ary[pos].Tid(): tid == Xow_search_tkn.Tid_eos;}
	public Xow_search_tkn Move_next() {
		Xow_search_tkn rv = null; 
		if (pos < ary_len) rv = ary[pos++];
		return rv;
	}
}
