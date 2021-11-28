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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public abstract class Xob_cmd__base implements Xob_cmd, Gfo_invk {
	protected final Xoae_app app; protected final Xob_bldr bldr; protected Xowe_wiki wiki; protected final Gfo_usr_dlg usr_dlg;
	public Xob_cmd__base(Xob_bldr bldr, Xowe_wiki wiki) {
		this.bldr = bldr;
		this.wiki = wiki;
		this.app = bldr == null ? null : bldr.App();;
		this.usr_dlg = bldr == null ? null : bldr.Usr_dlg();
	}
	public abstract String	Cmd_key();
	public Xob_cmd	Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public abstract void	Cmd_run();
	public void		Cmd_init(Xob_bldr bldr) {}
	public void		Cmd_bgn(Xob_bldr bldr) {}
	public void		Cmd_end() {}
	public void		Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
}
