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
package gplx.xowa.xtns.pagebanners;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.*;
public class Pgbnr_cfg {
	private final Hash_adp ns_hash = Hash_adp_.New(); private final IntRef tmp_ns_key = IntRef.NewNeg1();
	public Pgbnr_cfg(boolean enabled, boolean enable_heading_override, boolean enable_default_banner, int[] ns_ary, int dflt_img_wdata_prop, byte[] dflt_img_title, int[] standard_sizes) {
		this.enabled = enabled; this.enable_heading_override = enable_heading_override; this.enable_default_banner = enable_default_banner;
		this.standard_sizes = standard_sizes;
		this.dflt_img_wdata_prop = dflt_img_wdata_prop; this.dflt_img_title = dflt_img_title;
		for (int ns : ns_ary)
			this.ns_hash.AddAsKeyAndVal(IntRef.New(ns));
	}
	public final boolean enabled;
	public final boolean enable_heading_override;
	public final boolean enable_default_banner;
	public final int dflt_img_wdata_prop;
	public final byte[] dflt_img_title;
	public final int[] standard_sizes;
	public boolean Chk_pgbnr_allowed(Xoa_ttl ttl, Xowe_wiki wiki) {
		boolean enabled_in_ns = ns_hash.Has(tmp_ns_key.ValSet(ttl.Ns().Id()));
		return	enabled_in_ns										// chk if ns allows banner
			&&	!BryLni.Eq(ttl.Page_db(), wiki.Props().Main_page()); 	// never show on main page
	}
}
