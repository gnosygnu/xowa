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
package gplx.xowa.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
public class Hdump_page_itm {
	public int				Page_id() {return page_id;} private int page_id;
	public Xoa_url			Page_url() {return page_url;} private Xoa_url page_url;
	public int				Version_id() {return version_id;} public void Version_id_(int v) {version_id = v;} private int version_id;
	public byte[]			Page_body() {return page_body;} public void Page_body_(byte[] v) {this.page_body = v;} private byte[] page_body = Bry_.Empty;
	public byte[]			Display_ttl() {return display_ttl;} private byte[] display_ttl = Bry_.Empty;
	public byte[]			Content_sub() {return content_sub;} private byte[] content_sub = Bry_.Empty;
	public byte[][]			Sidebar_divs() {return sidebar_divs;} private byte[][] sidebar_divs = Bry_.Ary_empty;
	public Hdump_img_itm[]	Img_itms() {return img_itms;} public void Img_itms_(Hdump_img_itm[] v) {this.img_itms = v;} private Hdump_img_itm[] img_itms = Hdump_img_itm.Ary_empty;
	public void Init(int page_id, Xoa_url page_url, int version_id, byte[] display_ttl, byte[] content_sub, byte[] page_body, byte[][] sidebar_divs, Hdump_img_itm[] img_itms) {
		this.page_id = page_id;
		this.page_url = page_url;
		this.version_id = version_id;
		this.display_ttl = display_ttl;
		this.content_sub = content_sub;
		this.page_body = page_body;
		this.sidebar_divs = sidebar_divs;
		this.img_itms = img_itms;
	}
}
