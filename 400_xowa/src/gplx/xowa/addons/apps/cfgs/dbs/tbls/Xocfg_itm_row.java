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
package gplx.xowa.addons.apps.cfgs.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
public class Xocfg_itm_row {
	public Xocfg_itm_row(int id, String key, int scope, String type, String dflt, String html_atrs, String html_cls) {
		this.id = id;
		this.key = key;
		this.scope = scope;
		this.type = type;
		this.dflt = dflt;
		this.html_atrs = html_atrs;
		this.html_cls = html_cls;
	}
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public int Scope() {return scope;} private final    int scope;
	public String Type() {return type;} private final    String type;
	public String Dflt() {return dflt;} private final    String dflt;
	public String Html_atrs() {return html_atrs;} private final    String html_atrs;
	public String Html_cls() {return html_cls;} private final    String html_cls;
}
