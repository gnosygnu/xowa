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
package gplx.xowa.htmls.ns_files; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.primitives.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.xfers.*; import gplx.xowa.files.origs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_ns_file_page_mgr extends gplx.core.brys.Bfr_arg_base {
	private Xoa_ttl ttl; private Xoh_file_page_wtr html_wtr; private final Xoh_file_page__other_resolutions alt_wtr = new Xoh_file_page__other_resolutions();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_();
	private Xow_repo_mgr repo_mgr;
	private Xof_file_itm xfer_itm; private byte[] file_size_bry; private byte[] play_btn_icon;		
	private final Xof_img_size img_size = new Xof_img_size(); private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public void Bld_html(Xowe_wiki cur_wiki, Xop_ctx ctx, Xoae_page page, Bry_bfr bfr, Xoa_ttl ttl, Xoh_file_page_wtr html_wtr, Xof_xfer_queue queue) {
		Xowe_wiki wiki = (Xowe_wiki)page.Commons_mgr().Source_wiki_or(cur_wiki);
		this.ttl = ttl; this.html_wtr = html_wtr; this.repo_mgr = wiki.File__repo_mgr();
		this.play_btn_icon = wiki.Html_mgr().Img_media_play_btn();
		this.xfer_itm = wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_file, ctx, ctx.Cur_page(), queue, ttl.Page_txt()
		, Xop_lnki_type.Id_thumb, Xop_lnki_tkn.Upright_null, html_wtr.Main_img_w(), html_wtr.Main_img_h(), Xof_lnki_time.Null, Xof_lnki_page.Null, Bool_.N);
		Xof_orig_itm orig = wiki.File_mgr().Orig_mgr().Find_by_ttl_or_null(xfer_itm.Lnki_ttl());
		if (orig == Xof_orig_itm.Null) return;	// no orig;
		Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(orig.Repo(), xfer_itm.Lnki_ttl(), Bry_.Empty);
		if (repo == null) return;
		xfer_itm.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		xfer_itm.Init_at_html(Xof_exec_tid.Tid_wiki_file, img_size, repo, url_bldr);
		this.file_size_bry = Bry_.Empty;
		if (xfer_itm.File_exists()) {	// file exists
			long file_size = Io_mgr.Instance.QueryFil(xfer_itm.Html_orig_url()).Size();
			if (file_size == -1) file_size = 0; // QueryFil returns -1 if file doesn't exist
			this.file_size_bry = Bry_.new_a7(gplx.core.ios.Io_size_.To_str(file_size));
		}
		String commons_notice =  page.Commons_mgr().Xowa_mockup()
			? String_.Format(Str_commons_notice, gplx.langs.htmls.Html_utl.Escape_for_atr_val_as_bry(tmp_bfr, Byte_ascii.Apos, page.Ttl().Full_db_as_str()))
			: "";
		html_wtr.Html_main().Bld_bfr_many(bfr, this, commons_notice);
	}

	public void Bld_html(Xowe_wiki wiki, Bry_bfr bfr, Xof_file_itm xfer_itm, Xoa_ttl ttl, Xoh_file_page_wtr html_wtr, byte[] file_size_bry, byte[] play_btn_icon) {	// TEST:
		this.ttl = ttl; this.html_wtr = html_wtr; this.repo_mgr = wiki.File__repo_mgr();
		this.play_btn_icon = play_btn_icon;
		this.xfer_itm = xfer_itm;  this.file_size_bry = file_size_bry;
		html_wtr.Html_main().Bld_bfr_many(bfr, this, "");
	}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		alt_wtr.Init_by_fmtr(repo_mgr, xfer_itm, html_wtr);
		Xof_ext orig_ext = xfer_itm.Orig_ext();
		if (orig_ext.Id_is_thumbable_img())
			html_wtr.Html_main_img().Bld_bfr_many(bfr, xfer_itm.Orig_w(), xfer_itm.Orig_h(), xfer_itm.Html_orig_url().To_http_file_bry(), file_size_bry, orig_ext.Mime_type(), xfer_itm.Html_uid(), xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Html_view_url().To_http_file_bry(), ttl.Full_txt(), Xoa_app_.Utl__encoder_mgr().Http_url().Encode(ttl.Page_url()), alt_wtr);
		else if (orig_ext.Id_is_video())	// NOTE: video must precede audio else File:***.ogg will not show thumbs
			html_wtr.Html_main_vid().Bld_bfr_many(bfr, xfer_itm.Html_uid(), xfer_itm.Html_view_url().To_http_file_bry(), Atr_class_image, ttl.Page_db(), xfer_itm.Html_view_url().To_http_file_bry(), xfer_itm.Html_w(), xfer_itm.Html_h(), Bry_.Empty, xfer_itm.Html_orig_url().To_http_file_bry(), xfer_itm.Html_w(), xfer_itm.Html_w(), play_btn_icon);
		else if (orig_ext.Id_is_audio())
			html_wtr.Html_main_aud().Bld_bfr_many(bfr, xfer_itm.Html_orig_url().To_http_file_bry(), ttl.Page_db(), xfer_itm.Html_w(), xfer_itm.Html_w(), play_btn_icon);
	}
	private static final byte[] Atr_class_image = Bry_.new_a7("image");
	private static final String Str_commons_notice = String_.Concat_lines_nl_skip_last
	( "<table class='ambox ambox-delete' style=''>"
	, "  <tr>"
	, "    <td class='mbox-empty-cell'>"
	, "    </td>"
	, "    <td class='mbox-text' style=''>"
	, "<span class='mbox-text-span'>"
	, "<p>This offline page is a reduced version of the online one: <a href='https://commons.wikimedia.org/wiki/{0}'>https://commons.wikimedia.org/wiki/{0}</a>.</p>"
	, "<p>If you want XOWA to show an offline page just like the online version, you should download commons.wikimedia.org at <a href='/site/home/wiki/Help:Import/List'>Import online</a>.</p>"
	, "<ul>"
	, "<li>This wiki will use at least another 20 GB of disk space.</li>"
	, "<li>This wiki only provides wikitext. No extra images are downloaded. Note that the total size of images for commons.wikimedia.org would be too large (approximately 22 TB).</li>"
	, "</ul>"
	, "</span>"
	, "    </td>"
	, "  </tr>"
	, "</table>"
	);
}
