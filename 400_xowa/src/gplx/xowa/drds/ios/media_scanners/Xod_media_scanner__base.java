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
package gplx.xowa.drds.ios.media_scanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*; import gplx.xowa.drds.ios.*;
public abstract class Xod_media_scanner__base implements Xod_media_scanner {
	private final    List_adp list = List_adp_.New();
	public Xod_media_scanner__base() {
		this.evt_mgr = new Gfo_evt_mgr(this);
		Gfo_evt_mgr_.Sub_same(Io_mgr.Instance, Io_mgr.Evt__fil_created, this);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final    Gfo_evt_mgr evt_mgr;
	public Xod_media_scanner Add(Io_url url) {list.Add(url.Xto_api()); return this;}
	public void Scan() {
		String[] urls = list.To_str_ary_and_clear();
		Gfo_log_.Instance.Info("xo.io:media scan", "urls", String_.Concat_with_str(":", urls));
		this.Scan__hook(urls);
	}
	protected abstract void Scan__hook(String[] urls);
	private void On_fil_created(Io_url[] ary) {
		for (Io_url itm : ary)
			this.Add(itm);
		this.Scan();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Io_mgr.Evt__fil_created))			On_fil_created((Io_url[])m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
