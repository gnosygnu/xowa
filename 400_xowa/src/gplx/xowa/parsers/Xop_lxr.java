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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
public interface Xop_lxr {
	int		Lxr_tid();
	void	Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie);
	void	Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie);
	void	Term(Btrie_fast_mgr core_trie);
	int		Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos);
}
