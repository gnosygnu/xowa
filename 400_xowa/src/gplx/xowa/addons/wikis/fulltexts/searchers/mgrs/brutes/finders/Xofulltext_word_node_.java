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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.finders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.*;
import gplx.core.btries.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Xofulltext_word_node_ {
	public static Xofulltext_word_node New_root(Srch_crt_itm src, Btrie_slim_mgr word_trie, boolean auto_wildcard_bgn, boolean auto_wildcard_end, byte wildchar_byte, byte not_byte) {
		Xofulltext_word_node trg = new Xofulltext_word_node();
		trg.tid = src.Tid;

		// set word-related props
		switch (trg.tid) {
			case Srch_crt_itm.Tid__word:
			case Srch_crt_itm.Tid__word_quote:
				// get word_orig; EX: "abc*"
				byte[] word_orig = src.Raw;
				int word_orig_len = word_orig.length;

				// init hook_bgn / hook_end
				int hook_bgn = 0;
				int hook_end = word_orig_len;

				// handle wildcard at bgn; EX: "*a"
				boolean wildcard_at_bgn = auto_wildcard_bgn;
				if (word_orig_len > hook_bgn + 1 && word_orig[hook_bgn] == wildchar_byte) {
					wildcard_at_bgn = true;
					hook_bgn++;
				}

				// handle wildcard at end; EX: "a*"
				boolean wildcard_at_end = auto_wildcard_end;
				if (word_orig_len > hook_bgn + 1 && word_orig[hook_end - 1] == wildchar_byte) {
					wildcard_at_end = true;
					hook_end--;
				}

				// get hook
				byte[] word_hook = wildcard_at_bgn || wildcard_at_end ? Bry_.Mid(word_orig, hook_bgn, hook_end) : word_orig;

				// assign to trg
				trg.word_orig = word_orig;
				trg.word_hook = word_hook;
				trg.wildcard_at_bgn = wildcard_at_bgn;
				trg.wildcard_at_end = wildcard_at_end;

				// add to trie
				if (word_trie.Match_exact(word_hook) == null) { // don't add if exists
					word_trie.Add_obj(word_hook, trg);
				}						
				break;
		}

		// set subs
		Srch_crt_itm[] src_subs = src.Subs;
		Xofulltext_word_node[] trg_subs = new Xofulltext_word_node[src_subs.length];
		trg.subs = trg_subs;
		int len = src_subs.length;
		for (int i = 0; i < len; i++) {
			trg.subs[i] = New_root(src_subs[i], word_trie, auto_wildcard_bgn, auto_wildcard_end, wildchar_byte, not_byte);
		}

		return trg;
	}
}
