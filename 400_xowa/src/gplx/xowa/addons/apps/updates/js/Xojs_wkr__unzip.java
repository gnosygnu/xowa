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
import gplx.core.ios.zips.*;
public class Xojs_wkr__unzip extends Xojs_wkr__base {
	public Xojs_wkr__unzip(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, Io_url src, Io_url trg, long prog_data_end) {super(cbk_mgr, cbk_trg, js_cbk, done_cbk, null, "unzipping");
		this.src = src; this.trg = trg;
		this.Prog_data_end_(prog_data_end);
	}
	public Io_url Src() {return src;} private final    Io_url src;
	public Io_url Trg() {return trg;} private final    Io_url trg;
	@Override protected void Exec_run() {
		Io_zip_decompress_cmd decompress = Io_zip_decompress_cmd_.Proto.Make_new();
		List_adp unzip_urls = List_adp_.New();
		decompress.Exec(this, src, trg, unzip_urls);
	}
}
