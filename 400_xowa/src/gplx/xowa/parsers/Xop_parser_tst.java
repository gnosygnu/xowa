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
package gplx.xowa.parsers;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.core.btries.Btrie_fast_mgr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import org.junit.Test;
public class Xop_parser_tst {
	private final Xop_parser_fxt fxt = new Xop_parser_fxt();
	@Test public void Parse_to_src_end() {// if empty array, return 0, else IndexError; PAGE:commons.wikimedia.org/wiki/File:England_in_the_UK_and_Europe.svg; ISSUE#:668; DATE:2020-02-17
		fxt.Test__Parse_to_src_end("", 0, BoolUtl.Y, 0);
	}
}
class Xop_parser_fxt {
	private Xoae_app app;
	private Xowe_wiki wiki;
	private Xop_lxr_mgr tmpl_lxr_mgr, wiki_lxr_mgr;
	public Xop_parser_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		tmpl_lxr_mgr = Xop_lxr_mgr.new_tmpl_();
		wiki_lxr_mgr = Xop_lxr_mgr.new_wiki_();
	}
	public void Test__Parse_to_src_end(String src_str, int pos, boolean parse_is_tmpl, int expd)  {
		// init
		byte[] src = BryUtl.NewU8(src_str);
		Xop_ctx ctx = Xop_ctx.New__top(wiki);
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Xop_parser parser = wiki.Parser_mgr().Main();
		Btrie_fast_mgr trie = parse_is_tmpl ? tmpl_lxr_mgr.Trie() : wiki_lxr_mgr.Trie();

		int actl = parser.Parse_to_src_end(root, ctx, tkn_mkr, src, trie, pos, src.length);
            GfoTstr.Eq(expd, actl);
	}
}
