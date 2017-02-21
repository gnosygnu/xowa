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
public class Srch_crt_mgr {
	public Srch_crt_mgr(byte[] key, Srch_crt_tkn[] tkns, Srch_crt_itm root, byte words_tid, Srch_crt_itm[] words_ary, Srch_crt_itm words_nth) {
		this.Key = key;
		this.Tkns = tkns;
		this.Root = root;
		this.Words_tid = words_tid;
		this.Words_ary = words_ary;
		this.Words_nth = words_nth;
	}
	public final    byte[]				Key;
	public final    Srch_crt_tkn[]		Tkns;
	public final    Srch_crt_itm		Root;
	public final    byte				Words_tid;
	public final    Srch_crt_itm[]		Words_ary;
	public final    Srch_crt_itm		Words_nth;
	public int Words_nth__len() {
		return Words_nth == null ? 0 : Words_nth.Raw.length;
	}

	public static final byte
	  Tid__one			= 0
	, Tid__ands			= 1
	, Tid__mixed		= 2
	;
	public static Srch_crt_mgr Invalid = new Srch_crt_mgr(Bry_.Empty, Srch_crt_tkn.Ary_empty, Srch_crt_itm.Invalid, Tid__one, Srch_crt_itm.Ary_empty, Srch_crt_itm.Invalid);
}
