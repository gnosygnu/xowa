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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.xowa.ctgs.*; import gplx.xowa.xtns.gallery.*;
import gplx.xowa.html.portal.*; import gplx.xowa.html.tocs.*; import gplx.xowa.wikis.modules.*; import gplx.xowa.html.hzips.*;
public class Xow_html_mgr implements GfoInvkAble {
	public Xow_html_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		html_wtr = new Xoh_html_wtr(wiki, this);
		Xoae_app app = wiki.Appe();
		page_wtr_mgr = new Xohe_page_wtr_mgr(app.Gui_mgr().Kit().Tid() != gplx.gfui.Gfui_kit_.Swing_tid);	// reverse logic to handle swt,drd but not mem
		Io_url file_dir = app.Usere().Fsys_mgr().App_img_dir().GenSubDir_nest("file");
		img_media_play_btn = Xoa_app_.Utl__encoder_mgr().Fsys().Encode_http(file_dir.GenSubFil("play.png"));
		img_media_info_btn = Xoa_app_.Utl__encoder_mgr().Fsys().Encode_http(file_dir.GenSubFil("info.png"));
		img_thumb_magnify  = Xoa_app_.Utl__encoder_mgr().Fsys().Encode_http(file_dir.GenSubFil("magnify-clip.png"));
		img_xowa_protocol = Xoa_app_.Utl__encoder_mgr().Fsys().Encode_http(app.Usere().Fsys_mgr().App_img_dir().GenSubFil_nest("xowa", "protocol.png"));
		portal_mgr = new Xow_portal_mgr(wiki);
		imgs_mgr = new Xoh_imgs_mgr(this);
		module_mgr = new Xow_module_mgr(wiki);
		hzip_mgr = new Xow_hzip_mgr(app.Usr_dlg(), wiki);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		html_wtr.Init_by_wiki(wiki);
		module_mgr.Init_by_wiki(wiki);
	}
	public void Init_by_lang(Xol_lang lang) {
		portal_mgr.Init_by_lang(lang);
	}
	public Xowe_wiki			Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoh_html_wtr			Html_wtr() {return html_wtr;} private Xoh_html_wtr html_wtr;
	public Xohe_page_wtr_mgr	Page_wtr_mgr() {return page_wtr_mgr;} private Xohe_page_wtr_mgr page_wtr_mgr;
	public Xow_portal_mgr		Portal_mgr() {return portal_mgr;} private Xow_portal_mgr portal_mgr;
	public Xow_toc_mgr			Toc_mgr() {return toc_mgr;} private Xow_toc_mgr toc_mgr = new Xow_toc_mgr();
	public Xow_module_mgr		Module_mgr() {return module_mgr;} private Xow_module_mgr module_mgr;
	public Xow_hzip_mgr			Hzip_mgr() {return hzip_mgr;} private Xow_hzip_mgr hzip_mgr;
	public boolean Importing_ctgs() {return importing_ctgs;} public void Importing_ctgs_(boolean v) {importing_ctgs = v;} private boolean importing_ctgs;
	public int Img_thumb_width() {return img_thumb_width;} private int img_thumb_width = 220;
	public byte[] Img_media_play_btn() {return img_media_play_btn;} private byte[] img_media_play_btn;
	public byte[] Img_media_info_btn() {return img_media_info_btn;} private byte[] img_media_info_btn;
	public byte[] Img_thumb_magnify() {return img_thumb_magnify;} private byte[] img_thumb_magnify;
	public byte[] Img_xowa_protocol() {return img_xowa_protocol;} private byte[] img_xowa_protocol;
	public boolean Img_suppress_missing_src() {return img_suppress_missing_src;} public Xow_html_mgr Img_suppress_missing_src_(boolean v) {img_suppress_missing_src = v; return this;} private boolean img_suppress_missing_src = true;
	public Xohp_ctg_grp_mgr Ctg_mgr() {return ctg_mgr;} private Xohp_ctg_grp_mgr ctg_mgr = new Xohp_ctg_grp_mgr();
	public Xoctg_html_mgr Ns_ctg() {return ns_ctg;} private Xoctg_html_mgr ns_ctg = new Xoctg_html_mgr();
	public Xoh_imgs_mgr Imgs_mgr() {return imgs_mgr;} private Xoh_imgs_mgr imgs_mgr;
	public void Copy_cfg(Xow_html_mgr html_mgr) {imgs_mgr.Copy_cfg(html_mgr.Imgs_mgr());}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_article))							return page_wtr_mgr;
		else if	(ctx.Match(k, Invk_portal))								return portal_mgr;
		else if	(ctx.Match(k, Invk_imgs))								return imgs_mgr;
		else if	(ctx.Match(k, Invk_ns_ctg))								return ns_ctg;
		else if	(ctx.Match(k, Invk_modules))							return module_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	public static final String 
	  Invk_article = "article"
	, Invk_portal = "portal", Invk_imgs = "imgs", Invk_ns_ctg = "ns_ctg"
	, Invk_modules = "modules"
	;
}
