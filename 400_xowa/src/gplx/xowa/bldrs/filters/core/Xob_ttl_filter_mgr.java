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
package gplx.xowa.bldrs.filters.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.xowa.wikis.ttls.*;
public class Xob_ttl_filter_mgr {
	private boolean exclude_is_empty = true, include_is_empty = true;
	private final    Xob_ttl_filter_mgr_srl srl = new Xob_ttl_filter_mgr_srl();
	private Hash_adp_bry exclude_hash = Hash_adp_bry.cs(), include_hash = Hash_adp_bry.cs();
	public void Clear() {
		exclude_hash.Clear();
		include_hash.Clear();
		exclude_is_empty = include_is_empty = true;
	}
	public boolean Match_include(byte[] src) {return include_is_empty ? false : include_hash.Has(src);}
	public boolean Match_exclude(byte[] src) {return exclude_is_empty ? false : exclude_hash.Has(src);}
	public void Load(boolean exclude, Io_url url) {
		byte[] src = Io_mgr.Instance.LoadFilBry_loose(url);
		if (Bry_.Len_gt_0(src)) Load(exclude, src);
	}
	public void Load(boolean exclude, byte[] src) {
		Hash_adp_bry hash = exclude ? exclude_hash : include_hash;
		srl.Init(hash).Load_by_bry(src);
		if (exclude)
			exclude_is_empty = exclude_hash.Count() == 0;
		else
			include_is_empty = include_hash.Count() == 0;
	}
}
