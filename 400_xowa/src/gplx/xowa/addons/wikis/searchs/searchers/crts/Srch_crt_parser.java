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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
public class Srch_crt_parser {
	private final    Srch_crt_scanner scanner;
	private final    Srch_crt_visitor__words		words_visitor = new Srch_crt_visitor__words();
	private final    Srch_crt_visitor__print		print_visitor = new Srch_crt_visitor__print();
	private final    byte wildcard_byte;
	private int uid_next;
	public Srch_crt_parser(Srch_crt_scanner_syms trie_bldr) {
		this.wildcard_byte = trie_bldr.Wild();
		this.scanner = new Srch_crt_scanner(trie_bldr);
	}
	public int Next_uid() {return ++uid_next;}
	public Srch_crt_mgr Parse_or_invalid(byte[] src) {
		this.uid_next = -1;

		Srch_crt_tkn[] tkns_ary = scanner.Scan(src);
		Srch_crt_parser_frame root_frame = new Srch_crt_parser_frame(this);
		Parse_tkns(root_frame, tkns_ary, 0, tkns_ary.length);
		Srch_crt_itm root_itm = root_frame.Produce_or_null();

		if (root_itm == null) return Srch_crt_mgr.Invalid;
		byte[] key = print_visitor.Print(root_itm);
		words_visitor.Gather(root_itm);
		return new Srch_crt_mgr(key, tkns_ary, root_itm, words_visitor.Words_tid(), words_visitor.Words_ary(), words_visitor.Words_nth());
	}
	private int Parse_tkns(Srch_crt_parser_frame frame, Srch_crt_tkn[] tkns_ary, int tkns_bgn, int tkns_end) {
		int tkns_cur = tkns_bgn;
		while (tkns_cur < tkns_end) {
			Srch_crt_tkn cur_tkn = tkns_ary[tkns_cur];
			int new_tkns_cur = Process_tkn(frame, tkns_ary, tkns_cur, tkns_end, cur_tkn);
			if (new_tkns_cur < 0) {
				tkns_cur = -new_tkns_cur;
				break;
			}
			else
				tkns_cur = new_tkns_cur;
		}
		return tkns_cur;
	}
	private int Process_tkn(Srch_crt_parser_frame frame, Srch_crt_tkn[] tkns_ary, int tkns_cur, int tkns_end, Srch_crt_tkn cur_tkn) {
		byte cur_tid = cur_tkn.Tid;
		switch (cur_tid) {
			case Srch_crt_tkn.Tid__word:
			case Srch_crt_tkn.Tid__word_w_quote:
				frame.Subs__add(Srch_crt_itm.New_word(wildcard_byte, cur_tkn, frame.Next_uid(), cur_tkn.Val));
				break;
			case Srch_crt_tkn.Tid__and:
				frame.Eval_join(Srch_crt_itm.Tid__and);
				break;
			case Srch_crt_tkn.Tid__or:
				frame.Eval_join(Srch_crt_itm.Tid__or);
				break;
			case Srch_crt_tkn.Tid__paren_bgn: {
				Srch_crt_parser_frame paren_frame = new Srch_crt_parser_frame(this);
				int new_tkns_cur = Parse_tkns(paren_frame, tkns_ary, tkns_cur + 1, tkns_end);
				Srch_crt_itm paren_itm = paren_frame.Produce_or_null();
				if (paren_itm != null) {
					frame.Subs__add(paren_itm);
				}
				return new_tkns_cur;
			}
			case Srch_crt_tkn.Tid__paren_end:
				return -(tkns_cur + 1);
			case Srch_crt_tkn.Tid__not:
				frame.Notted_y_();
				break;
		}
		return tkns_cur + 1;
	}
}
class Srch_crt_parser_frame {
	public Srch_crt_parser_frame(Srch_crt_parser parser) {
		this.parser = parser;
	}
	private int join_tid = Srch_crt_tkn.Tid__null;
	private boolean notted = false;
	private final    List_adp subs = List_adp_.New();
	private final    Srch_crt_parser parser;
	public int Next_uid() {return parser.Next_uid();}
	public void Notted_y_() {
		if (notted)				// already notted; disable; EX: "--a"
			notted = false;
		else
			notted = true;
	}
	public void Subs__add(Srch_crt_itm itm) {
		// if notted, wrap itm in not; EX: "-a"; "-(a & b)"
		if		(notted) {
			itm = Srch_crt_itm.New_join(Srch_crt_itm.Tid__not, this.Next_uid(), itm);
			notted = false;
		}
		subs.Add(itm);

		// auto-and behavior; EX: "a b" -> "a & b"; EX: "a (b | c)" -> "a & (b | c)"
		if	(	join_tid == Srch_crt_tkn.Tid__null				// if currently null
			&&	subs.Len() > 1									// but 2 items in list
			)
			join_tid = Srch_crt_itm.Tid__and;					// default to AND;  EX: "a (b) c"
	}
	public void Eval_join(int tid) {
		if		(join_tid == Srch_crt_tkn.Tid__null)			join_tid = tid;
		else if	(join_tid == tid)								{}	// tid is same; ignore; note that this handles dupes; EX: "a & & b"
		else {	// tid changed; EX: a & b | c
			Merge_and_add();
			join_tid = tid;
		}
	}
	public Srch_crt_itm Produce_or_null() {
		int subs_len = subs.Len();
		switch (subs_len) {
			case 0:
				return null;
			case 1:
				join_tid = Srch_crt_tkn.Tid__null;
				return (Srch_crt_itm)subs.Get_at(0);
			default: 
				Srch_crt_itm[] subs_ary = (Srch_crt_itm[])subs.To_ary_and_clear(Srch_crt_itm.class);
				Srch_crt_itm rv = Srch_crt_itm.New_join(join_tid, parser.Next_uid(), subs_ary);
				join_tid = Srch_crt_tkn.Tid__null;
				return rv;
		}
	}
	private void Merge_and_add() {
		int subs_len = subs.Len();
		if (subs_len > 1)
			subs.Add(Produce_or_null());
	}
}
