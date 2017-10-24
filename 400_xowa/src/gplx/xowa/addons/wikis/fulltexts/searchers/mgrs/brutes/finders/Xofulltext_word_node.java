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
public class Xofulltext_word_node {
	public int tid;
	public Xofulltext_word_node[] subs;
	public byte[] word_orig;
	public byte[] word_hook;
	public boolean wildcard_at_bgn;
	public boolean wildcard_at_end;
	public boolean found;

	public boolean Match_word(Xofulltext_word_lang ctx, byte[] src, int hook_bgn, int hook_end, int word_bgn, int word_end) {
		// if no wildcard at bgn, hook_bgn must match word_bgn
		if (   !wildcard_at_bgn
			&& hook_bgn != word_bgn)
			return false;

		// if no wildcard at end, hook_end must match word_end
		if (   !wildcard_at_end
			&& hook_end != word_end)
			return false;

		return true;
	}
	public void Clear() {
		found = false;
		for (Xofulltext_word_node sub : subs)
			sub.Clear();
	}
	public boolean Eval() {
		switch (tid) {
			case Srch_crt_itm.Tid__and: {
				for (Xofulltext_word_node sub : subs)
					if (!sub.Eval())
						return false;
				return true;
			}
			case Srch_crt_itm.Tid__or: {
				for (Xofulltext_word_node sub : subs)
					if (sub.Eval())
						return true;
				return false;
			}
			case Srch_crt_itm.Tid__word:
			case Srch_crt_itm.Tid__word_quote:
				return found;
			case Srch_crt_itm.Tid__not:
				return !subs[0].Eval();
			case Srch_crt_itm.Tid__invalid:		return false;			// should not happen
			default:							throw Err_.new_unhandled_default(tid);
		}
	}
}
