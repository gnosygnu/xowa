/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xopage_html_data {
	public Xopage_html_data(byte[] display_ttl, byte[] body) {
		this.display_ttl = display_ttl;
		this.body = body;
	}
	public byte[] Display_ttl()		{return display_ttl;} private byte[] display_ttl;
	public byte[] Body()			{return body;} private final    byte[] body;
	public Xopg_tag_mgr Head_tags() {return head_tags;} private final    Xopg_tag_mgr head_tags = new Xopg_tag_mgr();
	public Xopg_tag_mgr Tail_tags() {return tail_tags;} private final    Xopg_tag_mgr tail_tags = new Xopg_tag_mgr();

	public void Apply(Xoa_page page) {
		page.Html_data().Html_restricted_n_();
		page.Html_data().Skip_parse_(Bool_.Y);
		page.Html_data().Display_ttl_(display_ttl);
		page.Html_data().Custom_body_(this.Body());
		page.Html_data().Custom_head_tags().Copy(head_tags);
		page.Html_data().Custom_tail_tags().Copy(tail_tags);
	}

	public static Xopage_html_data err_(String msg) {return new Xopage_html_data(Bry_.Empty, Bry_.new_u8(msg));}
}
