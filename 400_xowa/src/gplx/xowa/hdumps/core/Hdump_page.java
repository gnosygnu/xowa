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
import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*;
public class Hdump_page {
	public int				Page_id() {return page_id;} private int page_id;
	public Xoa_url			Page_url() {return page_url;} private Xoa_url page_url;
	public Xoa_ttl			Page_ttl() {return page_ttl;} private Xoa_ttl page_ttl;
	public int				Version_id() {return version_id;} public void Version_id_(int v) {version_id = v;} private int version_id;
	public int				Img_count() {return img_count;} public void Img_count_(int v) {img_count = v;} private int img_count;
	public Hdump_module_mgr Module_mgr() {return module_mgr;} private Hdump_module_mgr module_mgr = new Hdump_module_mgr();
	public byte[]			Page_body() {return page_body;} public void Page_body_(byte[] v) {this.page_body = v;} private byte[] page_body;
	public byte[]			Display_ttl() {return display_ttl;} public void Display_ttl_(byte[] v) {this.display_ttl = v;} private byte[] display_ttl;
	public byte[]			Content_sub() {return content_sub;} public void Content_sub_(byte[] v) {this.content_sub = v;} private byte[] content_sub;
	public byte[]			Sidebar_div() {return sidebar_div;} public void Sidebar_div_(byte[] v) {this.sidebar_div = v;} private byte[] sidebar_div;
	public int[]			Redlink_uids() {return redlink_uids;} public void Redlink_uids_(int[] v) {redlink_uids = v;} private int[] redlink_uids;
	public Hdump_data_img__base[]	Img_itms() {return img_itms;} public void Img_itms_(Hdump_data_img__base[] v) {this.img_itms = v;} private Hdump_data_img__base[] img_itms;
	public OrderedHash		Gly_itms() {return gly_itms;} private OrderedHash gly_itms = OrderedHash_.new_();
	public Hdump_page Init(int page_id, Xoa_url page_url, Xoa_ttl page_ttl) {
		this.page_id = page_id; this.page_url = page_url; this.page_ttl = page_ttl;
		content_sub = sidebar_div = Bry_.Empty;
		display_ttl = null;
		img_itms = Hdump_data_img__base.Ary_empty;
		module_mgr.Clear();
		gly_itms.Clear();
		return this;
	}
	public void Init(Bry_bfr tmp_bfr, Xoa_page page) {
		page_id			= page.Revision_data().Id();
		page_body		= page.Hdump_data().Body();
		Xopg_html_data html_data = page.Html_data();
		gplx.xowa.html.modules.Xoh_module_mgr mod_mgr = html_data.Module_mgr();	
		module_mgr.Init(mod_mgr.Itm_mathjax().Enabled(), mod_mgr.Itm_popups().Bind_hover_area(), mod_mgr.Itm_gallery().Enabled(), mod_mgr.Itm_hiero().Enabled());
		display_ttl = html_data.Display_ttl();
		content_sub = html_data.Content_sub();
		sidebar_div = Save_sidebars(tmp_bfr, page, html_data);
	}
	private static byte[] Save_sidebars(Bry_bfr tmp_bfr, Xoa_page page, Xopg_html_data html_data) {
		Xopg_xtn_skin_mgr mgr = html_data.Xtn_skin_mgr();
		int len = mgr.Count();
		boolean sidebar_exists = false;
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				sidebar_exists = true;
				itm.Write(tmp_bfr, page);
			}
		}
		return sidebar_exists ? tmp_bfr.Xto_bry_and_clear() : null;
	}
}
