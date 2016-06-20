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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.files.*;
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.sections.*; import gplx.xowa.htmls.core.makes.imgs.*;
public class Xoh_page implements Xoa_page {
	// core
	public Xow_wiki					Wiki()				{return wiki;} private Xow_wiki wiki;
	public Xoa_url					Url()				{return page_url;} private Xoa_url page_url;
	public Xoa_ttl					Ttl()				{return page_ttl;} private Xoa_ttl page_ttl;

	// props
	public boolean						Exists()			{return exists;} public Xoh_page Exists_n_() {exists = false; return this;} private boolean exists = true;
	public int						Page_id()			{return page_id;} private int page_id;
	public byte[]					Body()				{return body;} public void Body_(byte[] v) {this.body = v;} private byte[] body;
	public int						Body_zip_tid()		{return body_zip_tid;}	private int body_zip_tid;
	public int						Body_hzip_tid()		{return body_hzip_tid;}	private int body_hzip_tid;
	public byte[]					Display_ttl()		{return display_ttl;} private byte[] display_ttl;
	public byte[]					Content_sub()		{return content_sub;} private byte[] content_sub;
	public byte[]					Sidebar_div()		{return sidebar_div;} private byte[] sidebar_div;
	public Xoh_section_mgr			Section_mgr()		{return section_mgr;} private final    Xoh_section_mgr section_mgr = new Xoh_section_mgr();
	public Xoh_img_mgr				Img_mgr()			{return img_mgr;} private Xoh_img_mgr img_mgr = new Xoh_img_mgr();
	public Ordered_hash				Redlink_uids()		{return redlink_uids;} private final    Ordered_hash redlink_uids = Ordered_hash_.New();
	public Xohd_img_itm__base[]		Img_itms()			{return img_itms;} public void Img_itms_(Xohd_img_itm__base[] v) {this.img_itms = v;} private Xohd_img_itm__base[] img_itms = Xohd_img_itm__base.Ary_empty;
	public Ordered_hash				Gallery_itms()		{return gallery_itms;} private Ordered_hash gallery_itms = Ordered_hash_.New();
	public Xopg_module_mgr			Head_mgr()			{return head_mgr;} private Xopg_module_mgr head_mgr = new Xopg_module_mgr();
	public void						Xtn_gallery_packed_exists_y_() {}
	public Xopg_revision_data		Revision_data() {return revision_data;} private Xopg_revision_data revision_data = new Xopg_revision_data();
	public Xopg_html_data			Html_data() {return html_data;} private Xopg_html_data html_data = new Xopg_html_data();
	public byte[]					Redirect_to_ttl() {return redirect_to_ttl;} private byte[] redirect_to_ttl; public void Redirect_to_ttl_(byte[] v) {this.redirect_to_ttl = v;}

	// util
	public Xopg_lnki_list			Redlink_list()		{return redlink_list;} private Xopg_lnki_list redlink_list;
	public Xoa_page__commons_mgr	Commons_mgr()		{return commons_mgr;} private final    Xoa_page__commons_mgr commons_mgr = new Xoa_page__commons_mgr();
	public int						Exec_tid()			{return exec_tid;} private int exec_tid = Xof_exec_tid.Tid_wiki_page;
	public byte[]					Html_head_xtn()		{return html_head_xtn;} public void Html_head_xtn_(byte[] v) {html_head_xtn = v;} private byte[] html_head_xtn = Bry_.Empty;	// drd:web_browser
	public byte[]					Url_bry_safe()		{return page_url == null ? Bry_.Empty : page_url.To_bry(Bool_.Y, Bool_.Y);}
	public void Init(Xow_wiki wiki, Xoa_url page_url, Xoa_ttl page_ttl, int page_id) {
		this.wiki = wiki; this.page_url = page_url; this.page_ttl = page_ttl; this.page_id = page_id; 
		this.Clear();
		this.redlink_list = new Xopg_lnki_list(page_ttl.Ns().Id_is_module());
	}
	public void Ctor_by_db(int head_flag, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, int zip_tid, int hzip_tid, byte[] body) {
		head_mgr.Flag_(head_flag);
		this.display_ttl = display_ttl; this.content_sub = content_sub; this.sidebar_div = sidebar_div; this.body = body; this.body_zip_tid = zip_tid; this.body_hzip_tid = hzip_tid;
	}
	public Xoh_page Ctor_by_page(Bry_bfr tmp_bfr, Xoae_page page) {
		this.page_id = page.Revision_data().Id();
		this.wiki = page.Wiki();
		this.body = page.Hdump_data().Body();
		this.page_url = page.Url();
		Xopg_html_data html_data = page.Html_data();
		Xoh_head_mgr mod_mgr = html_data.Head_mgr();	
		head_mgr.Init(mod_mgr.Itm__mathjax().Enabled(), mod_mgr.Itm__popups().Bind_hover_area(), mod_mgr.Itm__gallery().Enabled(), mod_mgr.Itm__hiero().Enabled());
		this.display_ttl = html_data.Display_ttl();
		this.content_sub = html_data.Content_sub();
		this.sidebar_div = Xoh_page_.Save_sidebars(tmp_bfr, page, html_data);
		return this;
	}
	public void Clear() {
		this.body_zip_tid = -1; this.body_hzip_tid = -1;
		display_ttl = content_sub = sidebar_div = Bry_.Empty;
		img_itms = Xohd_img_itm__base.Ary_empty;
		head_mgr.Clear(); gallery_itms.Clear(); redlink_uids.Clear(); commons_mgr.Clear();
		section_mgr.Clear(); img_mgr.Clear();
	}
}
