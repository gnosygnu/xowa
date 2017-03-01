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
package gplx.xowa.addons.wikis.searchs.fulltexts.finders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.core.btries.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Xosearch_word_node_ {
	public static Xosearch_word_node New_root(Srch_crt_itm src, Btrie_slim_mgr word_trie, byte wildchar_byte) {
		Xosearch_word_node trg = new Xosearch_word_node();
		trg.tid = src.Tid;

		// set word-related props
		switch (trg.tid) {
			case Srch_crt_itm.Tid__word:
			case Srch_crt_itm.Tid__word_quote:
				byte[] word_orig = src.Raw;         // EX: "abc*"

				// determine if wildcards at bgn / end
				int word_orig_len = word_orig.length;
				boolean wildcard_at_bgn = word_orig_len > 1 && word_orig[0] == wildchar_byte;
				boolean wildcard_at_end = word_orig_len > 1 && word_orig[word_orig_len - 1] == wildchar_byte;

				// get hook
				int hook_bgn = wildcard_at_bgn ? 1 : 0;
				int hook_end = wildcard_at_end ? word_orig_len - 1 : word_orig_len;
				byte[] word_hook = wildcard_at_bgn || wildcard_at_end ? Bry_.Mid(word_orig, hook_bgn, hook_end) : word_orig;

				// assign to trg
				trg.word_orig = word_orig;
				trg.word_hook = word_hook;
				trg.wildcard_at_bgn = wildcard_at_bgn;
				trg.wildcard_at_end = wildcard_at_end;

				// add to hash, trie
				if (word_trie.Match_exact(word_hook) == null) { // don't add if exists
					word_trie.Add_obj(word_hook, trg);
				}						
				break;
		}

		// set subs
		Srch_crt_itm[] src_subs = src.Subs;
		Xosearch_word_node[] trg_subs = new Xosearch_word_node[src_subs.length];
		trg.subs = trg_subs;
		int len = src_subs.length;
		for (int i = 0; i < len; i++) {
			trg.subs[i] = New_root(src_subs[i], word_trie, wildchar_byte);
		}

		return trg;
	}
}
