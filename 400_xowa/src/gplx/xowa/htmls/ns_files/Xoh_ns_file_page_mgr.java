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
package gplx.xowa.htmls.ns_files; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.primitives.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.xfers.*; import gplx.xowa.files.origs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.fsdb.fs_roots.*;
public class Xoh_ns_file_page_mgr implements gplx.core.brys.Bfr_arg {
	private Xoa_ttl ttl; private Xoh_file_page_wtr html_wtr; private final    Xoh_file_page__other_resolutions alt_wtr = new Xoh_file_page__other_resolutions();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xow_repo_mgr repo_mgr;
	private Xof_file_itm xfer_itm; private byte[] file_size_bry;
	private final    Xof_img_size img_size = new Xof_img_size(); private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public void Bld_html(Xowe_wiki cur_wiki, Xop_ctx ctx, Xoae_page page, Bry_bfr bfr, Xoa_ttl ttl, Xoh_file_page_wtr html_wtr, Xof_xfer_queue queue) {
		Xowe_wiki wiki = (Xowe_wiki)page.Commons_mgr().Source_wiki_or(cur_wiki);
		this.ttl = ttl; this.html_wtr = html_wtr; this.repo_mgr = wiki.File__repo_mgr();
		this.xfer_itm = wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_file, ctx, ctx.Page(), queue, ttl.Page_txt()
		, Xop_lnki_type.Id_thumb, Xop_lnki_tkn.Upright_null, html_wtr.Main_img_w(), html_wtr.Main_img_h(), Xof_lnki_time.Null, Xof_lnki_page.Null, Bool_.N);

		// get orig
		Xof_orig_itm orig = wiki.File_mgr().Orig_mgr().Find_by_ttl_or_null(xfer_itm.Lnki_ttl());
		if (orig == Xof_orig_itm.Null) return;	// no orig;
		Xof_repo_itm repo = wiki.File__repo_mgr().Get_trg_by_id_or_null(orig.Repo(), xfer_itm.Lnki_ttl(), Bry_.Empty);
		if (repo == null) return;
		xfer_itm.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		xfer_itm.Init_at_html(Xof_exec_tid.Tid_wiki_file, img_size, repo, url_bldr);

		// if non-wmf, point orig_url to fs_dir, not cache_dir; DATE:2017-02-01
		if (wiki.Domain_tid() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__other) {
			Xof_fsdb_mgr fsdb_mgr = cur_wiki.File_mgr().Fsdb_mgr();
			if (String_.Eq(fsdb_mgr.Key(), Fs_root_core.Fsdb_mgr_key)) {
				Fs_root_core fs_dir_core = (Fs_root_core)fsdb_mgr;
				Io_url orig_url = fs_dir_core.Get_orig_url_or_null(xfer_itm.Lnki_ttl());
				if (orig_url != null)
					xfer_itm.Html_orig_url_(orig_url);
			}
		}

		// get file size
		this.file_size_bry = Bry_.Empty;
		if (xfer_itm.File_exists()) {	// file exists
			long file_size = Io_mgr.Instance.QueryFil(xfer_itm.Html_orig_url()).Size();
			if (file_size == -1) file_size = 0; // QueryFil returns -1 if file doesn't exist
			this.file_size_bry = Bry_.new_a7(gplx.core.ios.Io_size_.To_str(file_size));
		}

		// get commons notice
		String commons_notice =  page.Commons_mgr().Xowa_mockup()
			? String_.Format(Str_commons_notice, gplx.langs.htmls.Gfh_utl.Escape_for_atr_val_as_bry(tmp_bfr, Byte_ascii.Apos, page.Ttl().Full_db_as_str()))
			: "";
		html_wtr.Html_main().Bld_bfr_many(bfr, this, commons_notice);
	}

	public void Bld_html(Xowe_wiki wiki, Bry_bfr bfr, Xof_file_itm xfer_itm, Xoa_ttl ttl, Xoh_file_page_wtr html_wtr, byte[] file_size_bry) {	// TEST:
		this.ttl = ttl; this.html_wtr = html_wtr; this.repo_mgr = wiki.File__repo_mgr();
		this.xfer_itm = xfer_itm;  this.file_size_bry = file_size_bry;
		html_wtr.Html_main().Bld_bfr_many(bfr, this, "");
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		alt_wtr.Init_by_fmtr(repo_mgr, xfer_itm, html_wtr);
		Xof_ext orig_ext = xfer_itm.Orig_ext();
		byte[] alt_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Encode(ttl.Full_txt_w_ttl_case());
		byte[] xowa_title = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Encode(ttl.Page_url());
		if (orig_ext.Id_is_thumbable_img())
			html_wtr.Html_main_img().Bld_bfr_many(bfr, xfer_itm.Orig_w(), xfer_itm.Orig_h(), xfer_itm.Html_orig_url().To_http_file_bry(), file_size_bry, orig_ext.Mime_type()
				, xfer_itm.Html_uid(), xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Html_view_url().To_http_file_bry()
				, alt_bry, xowa_title, alt_wtr);
		else if (orig_ext.Id_is_video())	// NOTE: video must precede audio else File:***.ogg will not show thumbs
			html_wtr.Html_main_vid().Bld_bfr_many(bfr, xfer_itm.Html_uid(), xfer_itm.Html_view_url().To_http_file_bry(), Atr_class_image, xowa_title
				, xfer_itm.Html_view_url().To_http_file_bry(), xfer_itm.Html_w(), xfer_itm.Html_h(), Bry_.Empty, xfer_itm.Html_orig_url().To_http_file_bry(), xfer_itm.Html_w(), xfer_itm.Html_w());
		else if (orig_ext.Id_is_audio())
			html_wtr.Html_main_aud().Bld_bfr_many(bfr, xfer_itm.Html_orig_url().To_http_file_bry(), xowa_title, xfer_itm.Html_w(), xfer_itm.Html_w());
	}
	private static final    byte[] Atr_class_image = Bry_.new_a7("image");
	private static final    String Str_commons_notice = String_.Concat_lines_nl_skip_last
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
