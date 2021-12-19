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
package gplx.xowa.addons.wikis.ctgs.enums;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.errs.ErrUtl;
public class Xoctg_collation_enum { // REF:https://www.mediawiki.org/wiki/Manual:$wgCategoryCollation
	private final Btrie_rv trv = new Btrie_rv();
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs()
		.Add_str_byte("uppercase"   , Tid__uppercase)
		.Add_str_byte("uca"         , Tid__uca)
		.Add_str_byte("numeric"     , Tid__numeric)  // NOTE: no logic implemented for numeric; DATE:2019-10-15
		.Add_str_byte("identity"    , Tid__identity) // NOTE: no logic implemented for identity; DATE:2019-10-15
		;
	public byte To_tid_or_fail(byte[] raw) {
		byte tid = trie.Match_byte_or(trv, raw, 0, raw.length, ByteUtl.MaxValue127);
		if (tid == ByteUtl.MaxValue127) throw ErrUtl.NewUnhandled(raw);
		return tid;
	}
	public static final byte Tid__uppercase = 1, Tid__uca = 3, Tid__numeric = 4, Tid__identity = 5;
}
