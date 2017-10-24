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
package gplx.xowa.addons.apps.scripts.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
public class Xoscript_page {		
	public Xoscript_page(Bry_bfr rv, Xoscript_env env_var, Xoscript_url url_var) {
		this.env_var = env_var;
		this.url_var = url_var;
		this.doc_var = new Xoscript_doc(rv, this);
	}
	public Xoscript_env env() {return env_var;} private final    Xoscript_env env_var;
	public Xoscript_url url() {return url_var;} private final    Xoscript_url url_var;
	public Xoscript_doc doc() {return doc_var;} private final    Xoscript_doc doc_var;
}
