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
package gplx.xowa.addons.apps.cfgs.mgrs.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
class Xocfg_cache_sub {
	public Xocfg_cache_sub(Gfo_invk sub, String ctx, String evt, String key) {
		this.ctx = ctx;
		this.key = key;
		this.evt = evt;
		this.sub = sub;
	}
	public String Ctx() {return ctx;} private final    String ctx;
	public String Key() {return key;} private final    String key;
	public String Evt() {return evt;} private final    String evt;
	public Gfo_invk Sub() {return sub;} private final    Gfo_invk sub;
}
