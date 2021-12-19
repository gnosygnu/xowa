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
package gplx.xowa.bldrs.filters.dansguardians;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.utls.IntUtl;
import gplx.core.lists.hashs.*;
import gplx.libs.files.Io_url;
class Dg_ns_skip_mgr {
	private final Hash_adp__int ns_hash = new Hash_adp__int();
	private boolean is_empty = true;
	public boolean Has(int ns) {return is_empty ? false : ns_hash.Get_by_or_null(ns) != null;}
	public void Load(Io_url url) {
		// load from file
		Gfo_usr_dlg_.Instance.Log_many("", "", "loading ns.skip file; url=~{0}", url.Raw());
		byte[] src = Io_mgr.Instance.LoadFilBry_loose(url);

		// parse to lines
		byte[][] lines = BrySplit.SplitLines(src);

		// add to hash
		for (byte[] line : lines) {
			int ns_id = BryUtl.ToIntOr(line, IntUtl.MaxValue);
			if (ns_id != IntUtl.MaxValue) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "adding ns; ns_id=~{0}", ns_id);
				ns_hash.Add_if_dupe_use_1st(ns_id, line);
				is_empty = false;
			}
		}
	}
}
