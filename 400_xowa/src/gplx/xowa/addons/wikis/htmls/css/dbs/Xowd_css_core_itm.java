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
package gplx.xowa.addons.wikis.htmls.css.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
public class Xowd_css_core_itm {
	public Xowd_css_core_itm(int id, String key, DateAdp updated_on) {
		this.id = id; this.key = key; this.updated_on = updated_on;
	}
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public DateAdp Updated_on() {return updated_on;} private final    DateAdp updated_on;
}
