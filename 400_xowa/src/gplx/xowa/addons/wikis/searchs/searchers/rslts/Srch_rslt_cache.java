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
package gplx.xowa.addons.wikis.searchs.searchers.rslts; import gplx.*;
public class Srch_rslt_cache {
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {hash.Clear();}
	public Srch_rslt_list Get_or_new(byte[] key) {
		Srch_rslt_list rv = (Srch_rslt_list)hash.GetByOrNull(key);
		if (rv == null) {
			rv = new Srch_rslt_list();
			hash.Add(key, rv);
		}
		rv.Rslts_are_first = true;
		rv.Rslts_are_enough = false;
		return rv;
	}
}
