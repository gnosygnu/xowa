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
package gplx.xowa.addons.bldrs.centrals.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.progs.*; import gplx.core.gfobjs.*;
public interface Xobc_cmd_itm extends Gfo_prog_ui, Gfo_invk {
	int			Task_id();
	int			Step_id();
	int			Cmd_id();
	String		Cmd_type();			// "xowa.core.http_download"
	String		Cmd_name();			// "download"
	boolean		Cmd_suspendable();	// "true"
	String		Cmd_uid();			// for thread_pool_mgr: "0:0:0"
	void		Cmd_cleanup();
	String		Cmd_fallback();
	void		Cmd_clear();

	Gfobj_nde	Save_to(Gfobj_nde nde);
	void		Load_checkpoint();
}
