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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.pages.tags.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xopage_html_data {
	public Xopage_html_data(byte[] display_ttl, byte[] body) {
		this.display_ttl = display_ttl;
		this.body = body;
	}
	public byte[] Display_ttl()			{return display_ttl;} private byte[] display_ttl;
	public byte[] Body()				{return body;} private final    byte[] body;
	public boolean			Cbk_enabled()	{return cbk_enabled;} private boolean cbk_enabled; public void Cbk_enabled_y_() {this.cbk_enabled = true;}
	public boolean			Js_enabled()    {return js_enabled;} private boolean js_enabled; public void Js_enabled_y_() {this.js_enabled = true;}
	public Xopg_tag_mgr Head_tags()		{return head_tags;} private final    Xopg_tag_mgr head_tags = new Xopg_tag_mgr(Bool_.Y);
	public Xopg_tag_mgr Tail_tags()		{return tail_tags;} private final    Xopg_tag_mgr tail_tags = new Xopg_tag_mgr(Bool_.N);

	public void Apply(Xoa_page page) {
		Xopg_tag_wtr_.Add__tab_uid	(head_tags, page.Page_guid());

		Xopg_html_data html_data = page.Html_data();
		html_data.Html_restricted_n_();
		html_data.Skip_parse_(Bool_.Y);
		html_data.Display_ttl_(display_ttl);
		html_data.Custom_body_(this.Body());
		html_data.Custom_head_tags().Copy(head_tags);
		html_data.Custom_tail_tags().Copy(tail_tags);
		html_data.Cbk_enabled_(cbk_enabled);
		html_data.Js_enabled_(js_enabled);
	}

	public static Xopage_html_data err_(String msg) {return new Xopage_html_data(Bry_.Empty, Bry_.new_u8(msg));}
}
