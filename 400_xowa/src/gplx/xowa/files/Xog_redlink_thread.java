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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*; import gplx.xowa.guis.cbks.js.*;
public class Xog_redlink_thread implements Gfo_thread_wkr {
	private final    int[] redlink_ary; private final    Xog_js_wkr js_wkr;
	public Xog_redlink_thread(int[] redlink_ary, Xog_js_wkr js_wkr) {this.redlink_ary = redlink_ary; this.js_wkr = js_wkr;}
	public String	Thread__name() {return "xowa.gui.html.redlinks.set";}
	public boolean	Thread__resume() {return true;}
	public void Thread__exec() {
		int len = redlink_ary.length;
		for (int i = 0; i < len; ++i) {
			js_wkr.Html_redlink(gplx.xowa.wikis.pages.lnkis.Xopg_lnki_list.Lnki_id_prefix + Int_.To_str(redlink_ary[i]));
		}
	}
}
