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
package gplx.xowa.drds.powers; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
public class Xod_power_mgr_ {
	public static Xod_power_mgr Instance = new Xod_power_mgr__shim();
}
class Xod_power_mgr__shim implements Xod_power_mgr {
	// private final Ordered_hash hash = Ordered_hash_.New();
	public void Wake_lock__get(String name) {
		// if (hash.Has(name)) {hash.Clear(); throw Err_.new_("itm exists", "name", name);}
		// hash.Add(name, name);
	}
	public void Wake_lock__rls(String name) {
		// if (!hash.Has(name)) throw Err_.new_("itm missing", "name", name);
		// hash.Del(name);
	}
}
