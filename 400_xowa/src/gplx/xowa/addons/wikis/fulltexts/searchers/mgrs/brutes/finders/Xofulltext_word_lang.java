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
import gplx.core.intls.*;
import gplx.xowa.addons.wikis.fulltexts.core.*;
public class Xofulltext_word_lang {
	private final    Btrie_slim_mgr ws_bgn = Btrie_slim_mgr.cs().Add_many_str(Xofulltext_punct_.Ws_bgn_ary);
	private final    Btrie_slim_mgr ws_end;
	private final    Btrie_slim_mgr punct_bgn = Btrie_slim_mgr.cs().Add_many_str(Xofulltext_punct_.Punct_bgn_ary);
	private final    Btrie_slim_mgr punct_end;
	public Xofulltext_word_lang() {
		this.ws_end = ws_bgn;
		this.punct_end = punct_bgn;
	}
	public void Get_word_bounds(Xofulltext_word_bounds word_bounds, Btrie_rv trv, byte[] src, int src_end, int hook_bgn, int hook_end) {
		int tmp_pos = -1;
		Object tmp_obj = null;

		// find word_bgn
		int word_bgn = hook_bgn;
		tmp_pos = word_bgn;
		while (true) {
			// stop if BOS
			if (tmp_pos == 0) break;

			// move back one char
			tmp_pos = Utf8_.Get_prv_char_pos0(src, tmp_pos);

			// check if char is ws
			tmp_obj = ws_bgn.Match_at(trv, src, tmp_pos, hook_end);

			// char is ws -> stop
			if (tmp_obj != null) break;

			// char is not ws -> update word_end
			word_bgn = tmp_pos;
		}

		// find word_end
		int word_end = hook_end;
		tmp_pos = word_end;
		while (true) {
			// stop if passed EOS
			if (tmp_pos >= src_end) break;

			// check if char is ws
			tmp_obj = ws_end.Match_at(trv, src, tmp_pos, src_end);

			// stop if ws
			if (tmp_obj != null) break;

			// increment before
			tmp_pos++;

			// update word_end
			word_end = tmp_pos;
		}

		// trim punct at bgn; EX: "'abc" -> "abc"
		if (word_bgn < hook_bgn) {
			tmp_pos = word_bgn;
			while (true) {
				// stop if passed hook-end
				if (tmp_pos >= hook_bgn) break;

				// check if char is punct
				tmp_obj = punct_bgn.Match_at(trv, src, tmp_pos, word_end);

				// stop if not a punct
				if (tmp_obj == null) break;

				// increment before
				tmp_pos++;

				// update word_end
				word_bgn = tmp_pos;
			}
		}

		// trim punct at end; EX: "abc." -> "abc"
		if (word_end > hook_end) {
			tmp_pos = word_end;
			while (true) {
				// scan bwd one char
				tmp_pos = Utf8_.Get_prv_char_pos0(src, tmp_pos);

				// stop if passed hook-end
				if (tmp_pos < hook_end) break;

				// check if char is punct
				tmp_obj = punct_end.Match_at(trv, src, tmp_pos, word_end);

				// stop if not a punct
				if (tmp_obj == null) break;

				// update word_end
				word_end = tmp_pos;
			}
		}

		word_bounds.Init(word_bgn, word_end);
	}
}
