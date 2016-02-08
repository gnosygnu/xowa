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
package gplx.xowa.specials.search.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
public class Srch_crt_parser {
	private final Srch_parser_ctx parse_ctx = new Srch_parser_ctx(); private byte[] src;
	public Srch_crt_node Parse(byte[] src) {
		this.src = src;
		Srch_crt_tkn[] tkns = new Srch_crt_scanner().Scan(src);
		return Parse_itm_or(parse_ctx.Init(tkns));
	}
	private Srch_crt_node Parse_itm_or(Srch_parser_ctx parse_ctx) {
		Srch_crt_node lhs = Parse_itm_and(parse_ctx);
		while (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_or)) {
			parse_ctx.Move_next();
			Srch_crt_node rhs = Parse_itm_and(parse_ctx);
			lhs = New_join(Srch_crt_node.Tid_or, lhs, rhs);
		}
		return lhs;
	}
	private Srch_crt_node Parse_itm_and(Srch_parser_ctx parse_ctx) {
		Srch_crt_node lhs = Parse_itm_not(parse_ctx);
		while (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_and)) {
			parse_ctx.Move_next();
			Srch_crt_node rhs = Parse_itm_not(parse_ctx);
			lhs = New_join(Srch_crt_node.Tid_and, lhs, rhs);
		}
		return lhs;
	}
	private Srch_crt_node Parse_itm_not(Srch_parser_ctx parse_ctx) {
		Srch_crt_node lhs = Parse_itm_leaf(parse_ctx);
		while (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_not)) {
			parse_ctx.Move_next();
			Srch_crt_node rhs = Parse_itm_leaf(parse_ctx);
			lhs = New_join(Srch_crt_node.Tid_not, null, rhs);
		}
		return lhs;
	}
	private Srch_crt_node Parse_itm_leaf(Srch_parser_ctx parse_ctx) {
		if (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_paren_bgn)) {
			parse_ctx.Move_next();
			Srch_crt_node lhs = Parse_itm_or(parse_ctx);
			if (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_paren_end)) parse_ctx.Move_next();	// skip token
			return lhs;
		}
		else if (parse_ctx.Cur_tid(Srch_crt_tkn.Tid_eos))
			return Srch_crt_node.Null;
		else {
			Srch_crt_tkn word_tkn = parse_ctx.Move_next();
			if (word_tkn.tid == Srch_crt_tkn.Tid_not) {
				word_tkn = parse_ctx.Move_next();
				if (word_tkn == null) return Srch_crt_node.Null; // occurs in "a -"
				Srch_crt_node word_itm = New_word(word_tkn, src);
				return New_join(Srch_crt_node.Tid_not, null, word_itm);
			}
			else
				return New_word(word_tkn, src);
		}
	}
	private static Srch_crt_node New_word(Srch_crt_tkn tkn, byte[] src) {
		int tid = tkn.tid == Srch_crt_tkn.Tid_word ? Srch_crt_node.Tid_word : Srch_crt_node.Tid_word_quote; 
		return new Srch_crt_node(tid, tkn.val, null, null);
	}
	private static Srch_crt_node New_join(int tid, Srch_crt_node lhs, Srch_crt_node rhs) {return new Srch_crt_node(tid, null, lhs, rhs);}
}
class Srch_parser_ctx {
	private Srch_crt_tkn[] ary; private int pos = 0; private int ary_len;
	public Srch_parser_ctx Init(Srch_crt_tkn[] ary) {
		this.ary = ary;
		this.ary_len = ary.length; 
		this.pos = 0;
		return this;
	}
	public boolean Cur_tid(byte tid) {return pos < ary_len ? tid == ary[pos].tid : tid == Srch_crt_tkn.Tid_eos;}
	public Srch_crt_tkn Move_next() {
		Srch_crt_tkn rv = null; 
		if (pos < ary_len) rv = ary[pos++];
		return rv;
	}
}
