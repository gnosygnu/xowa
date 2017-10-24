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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_data {
	public Xopg_db_page			Page()			{return page;}			private final    Xopg_db_page page = new Xopg_db_page();
	public Xopg_db_text			Text()			{return text;}			private final    Xopg_db_text text = new Xopg_db_text();
	public Xopg_db_html			Html()			{return html;}			private final    Xopg_db_html html = new Xopg_db_html();
	public Xopg_db_protection	Protection()	{return protection;}	private final    Xopg_db_protection protection = new Xopg_db_protection();
	public void Clear() {
		page.Clear();
		html.Clear();
		text.Clear();
		protection.Clear();
	}
}
