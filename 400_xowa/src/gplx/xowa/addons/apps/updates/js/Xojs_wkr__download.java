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
package gplx.xowa.addons.apps.updates.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.guis.cbks.*;
import gplx.core.net.downloads.*;
public class Xojs_wkr__download extends Xojs_wkr__base {
	public Xojs_wkr__download(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, Gfo_invk_cmd fail_cbk, String src, Io_url trg, long src_len) {super(cbk_mgr, cbk_trg, js_cbk, done_cbk, fail_cbk, "downloading");
		this.src = src;
		this.src_len = src_len;
		this.trg = trg;
		this.Prog_data_end_(src_len);
	}
	public String Src() {return src;} private final    String src;
	public Io_url Trg() {return trg;} private final    Io_url trg;
	public long Src_len() {return src_len;} private final    long src_len;
	@Override protected void Exec_run() {
		Http_download_wkr wkr = Http_download_wkr_.Proto.Make_new();
		if (wkr.Exec(this, src, trg, src_len) != gplx.core.progs.Gfo_prog_ui_.Status__done)
			this.Cbk_mgr().Send_json(this.Cbk_trg(), "xo.app_updater.write_status", gplx.core.gfobjs.Gfobj_nde.New().Add_str("msg", "failed to download update: " + wkr.Fail_msg()));
	}
}
