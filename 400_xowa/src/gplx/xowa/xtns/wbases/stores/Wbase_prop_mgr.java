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
package gplx.xowa.xtns.wbases.stores;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.xowa.xtns.wbases.core.*;
public class Wbase_prop_mgr {	// lang-agnostic registry of props; EX: "p15" -> commonsmedia		
	private Ordered_hash cache;
	private boolean init_needed = true;
	public Wbase_prop_mgr(Wbase_prop_mgr_loader loader) {
		this.loader = loader;
	}
	public Wbase_prop_mgr_loader Loader() {return loader;} private Wbase_prop_mgr_loader loader;
	public void Loader_(Wbase_prop_mgr_loader v) {
		loader = v;
		init_needed = true;
	}
	public String Get_or_null(String pid, byte[] page_url) {
		if (init_needed) Init();
		if (cache == null) return null;
		pid = Wbase_pid.Ucase_pid_as_str(pid);
		String rv = (String)cache.GetByOrNull(pid);
		if (rv == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:could not find datatype for pid; pid=~{0} url=~{1}", pid, page_url);
		}
		return rv;
	}
	private void Init() {
		init_needed = false;
		cache = loader.Load();
	}
}
