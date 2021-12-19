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
package gplx.xowa.apps.apis.xowa.addons.bldrs;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
public class Xoapi_central_api implements Gfo_invk {
	public boolean		Log_verbose()		{return log_verbose;}		private boolean log_verbose = false;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__log_verbose_)) 						log_verbose = m.ReadBool("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk__log_verbose_				= "log_verbose_"
	;
}
