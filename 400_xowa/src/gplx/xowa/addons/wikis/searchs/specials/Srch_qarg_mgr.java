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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;	
public class Srch_qarg_mgr {
	public Srch_qarg_mgr(Srch_ns_mgr ns_mgr) {this.ns_mgr = ns_mgr;}
	public Srch_ns_mgr			Ns_mgr()		{return ns_mgr;} private final    Srch_ns_mgr ns_mgr;
	public byte[]				Search_raw()	{return search_raw;} private byte[] search_raw; public Srch_qarg_mgr Search_raw_(byte[] v) {search_raw = v; return this;} 
	public int					Slab_idx()		{return slab_idx;} private int slab_idx;
	public byte[]				Cancel()		{return cancel;} private byte[] cancel;
	public Srch_qarg_mgr Clear() {
		ns_mgr.Clear();
		this.search_raw = null;
		this.slab_idx = 0;
		this.cancel = null;
		return this;
	}
	public void Parse(Gfo_qarg_itm[] qargs_ary) {
		if (qargs_ary == null) return;
		int len = qargs_ary.length;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm qarg = qargs_ary[i];
			byte[] key = qarg.Key_bry();
			byte tid = qarg_regy.Get_as_byte_or(key, Byte_.Max_value_127);
			if (tid == Byte_.Max_value_127) {	// unknown qarg; check for ns*; EX: &ns0=1&ns8=1; NOTE: lowercase only
				if (Bry_.Has_at_bgn(key, Ns_bry))
					ns_mgr.Add_by_parse(key, qarg.Val_bry());
			}
			else {
				switch (tid) {
					case Uid__search: 			this.search_raw 	= Bry_.Replace(qarg.Val_bry(), Byte_ascii.Plus, Byte_ascii.Space); break;
					case Uid__slab_idx: 		this.slab_idx 		= Bry_.To_int_or(qarg.Val_bry(), 0); break;
					case Uid__cancel: 			this.cancel			= qarg.Val_bry(); break;
					default:					break;
				}
			}
		}
		ns_mgr.Add_main_if_empty();
	}
	private static byte[] Ns_bry = Bry_.new_a7("ns");
	private static final byte Uid__search = 0, Uid__slab_idx = 1, Uid__cancel = 2;
	public static final    byte[] Bry__slab_idx = Bry_.new_a7("xowa_page_index"), Bry__cancel = Bry_.new_a7("cancel");
	private static final    Hash_adp_bry qarg_regy = Hash_adp_bry.ci_a7()
	.Add_str_byte("search"				, Uid__search)
	.Add_bry_byte(Bry__slab_idx			, Uid__slab_idx)
	.Add_bry_byte(Bry__cancel			, Uid__cancel)
	;
}
