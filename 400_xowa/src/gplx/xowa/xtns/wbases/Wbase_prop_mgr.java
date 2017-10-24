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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.data.*;
public class Wbase_prop_mgr {	// lang-agnostic registry of props; EX: "p15" -> commonsmedia
	private boolean init_needed = true;
	private Ordered_hash hash;
	public Wbase_prop_mgr(Wbase_prop_mgr_loader loader) {
		this.loader = loader;
	}
	public Wbase_prop_mgr_loader Loader() {return loader;} private Wbase_prop_mgr_loader loader;
	public void Loader_(Wbase_prop_mgr_loader v) {
		loader = v;
		init_needed = true;
	}
	private void Init() {
		init_needed = false;
		hash = loader.Load();
	}
	public String Get_or_null(String pid) {
		if (init_needed) Init();
		if (hash == null) return null;
		if (String_.Has_at_bgn(pid, "p"))
			pid = String_.Upper(pid);	// convert "p123" to "P123"; note (a) Scrib.v2 calls as "P123"; (b) db stores as "p123"; (c) XO loads as "P123"; DATE:2016-12-03
		String rv = (String)hash.Get_by(pid);
		if (rv == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:could not find datatype for pid; pid=~{0}", pid);
		}
		return rv;
	}
}
