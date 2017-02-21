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
package gplx.xowa.htmls.core.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
public class Xowd_html_row {
	public void Load(int page_id, int head_flag, int body_flag, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, byte[] body) {
		this.page_id = page_id;
		this.head_flag = head_flag;
		this.body_flag = body_flag;
		this.display_ttl = display_ttl;
		this.content_sub = content_sub;
		this.sidebar_div = sidebar_div;
		this.body = body;
	}
	public int Page_id() {return page_id;} private int page_id;
	public int Head_flag() {return head_flag;} private int head_flag;
	public int Body_flag() {return body_flag;} private int body_flag;
	public byte[] Display_ttl() {return display_ttl;} private byte[] display_ttl;
	public byte[] Content_sub() {return content_sub;} private byte[] content_sub;
	public byte[] Sidebar_div() {return sidebar_div;} private byte[] sidebar_div;
	public byte[] Body() {return body;} private byte[] body;

	public static final int Db_row_size_fixed = (3 * 4);	// page_id, head_flag, body_flag
}
