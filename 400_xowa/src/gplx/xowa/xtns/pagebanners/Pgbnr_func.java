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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.langs.mustaches.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.parsers.utils.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*;
import gplx.xowa.files.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.langs.htmls.encoders.*;
public class Pgbnr_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_pagebanner;}
	@Override public Pf_func New(int id, byte[] name) {return new Pgbnr_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) { // {{PAGEBANNER}} appears on page; WikidataPageBanner.hooks.php|addCustomBanner
		Xowe_wiki wiki = ctx.Wiki(); Xoae_page page = ctx.Page();
		Pgbnr_xtn_mgr xtn_mgr = wiki.Xtn_mgr().Xtn_pgbnr();
		Pgbnr_cfg cfg = xtn_mgr.Cfg();
		Xoa_ttl ttl = page.Ttl();
		if (!cfg.Chk_pgbnr_allowed(ttl, wiki)) return;
		byte[] tooltip = ttl.Page_txt(), title = ttl.Page_txt(), toc = Bry_.Empty, origin_x = Bry_.Empty;
		boolean bottomtoc = false;;
		double data_pos_x = 0, data_pos_y = 0;
		List_adp icons_list = null;
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		int args_len = self.Args_len();
		Xop_func_arg_itm func_arg = new Xop_func_arg_itm();
		for (int i = 0; i < args_len; ++i) {
			Arg_nde_tkn arg = self.Args_get_by_idx(i);
			func_arg.Set(tmp_bfr, ctx, src, caller, self, arg);
			byte[] key = func_arg.key;
			byte[] val = func_arg.val;
			if (key == Bry_.Empty) continue; // ignore blank args; EX:{{PAGEBANNER:A.png|\n|toc=yes}}
			int tid = arg_hash.Get_as_int_or(key, -1);
			if (tid == Arg__pgname)
				tooltip = title = val;
			if (tid == Arg__tooltip)	// note that this overrides pgname above
				tooltip = val;
			if (tid == Arg__bottomtoc	&& Bry_.Eq(val, Bry__yes))
				bottomtoc = true;
			if (tid == Arg__toc			&& Bry_.Eq(val, Bry__yes))						// REF.MW:addToc
				toc = Bry_.Empty;						// note that "" will be overwritten later by actual toc html
			if (	tid == -1							// note that "icon-*" won't have a tid 
				&&	Bry_.Has_at_bgn(key, Bry__icon)		// if (substr($key, 0, 5) === 'icon-')
				&&	Bry_.Len(key)	> 5					// if ( !isset( $iconname) )
				&&	Bry_.Len_gt_0(val)					// if ( !isset( $$value ) ) 
				) {										// REF.MW:addIcons
				tid = Arg__icon;
				if (icons_list == null) icons_list = List_adp_.New();
				byte[] icon_key = Bry_.Mid(key, 5);
				byte[] icon_name = Xop_sanitizer.Escape_cls(icon_key);
				byte[] icon_title = icon_name;
				Xoa_ttl icon_ttl = wiki.Ttl_parse(val);
				byte[] icon_href = Bry__icon_href_dflt;
				if (icon_ttl != null) {
					icon_href = Bry_.Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, icon_ttl.Page_db());
					icon_title = icon_ttl.Page_txt();
				}
				icons_list.Add(new Pgbnr_icon(tmp_bfr, icon_name, icon_title, icon_href));
			}
			if (tid == Arg__origin) {					// REF.MW:addFocus
				double tmp_data_pos_x = Double_.NaN, tmp_data_pos_y = Double_.NaN;
				int comma_pos = Bry_find_.Find_fwd(val, Byte_ascii.Comma);
				if (comma_pos != Bry_find_.Not_found) {
					tmp_data_pos_x = Bry_.To_double_or(val, 0, comma_pos, Double_.NaN);
					if (!Double_.IsNaN(tmp_data_pos_x)) {
						if (tmp_data_pos_x >= -1 && tmp_data_pos_x <= 1) {
							data_pos_x = tmp_data_pos_x;
							origin_x = tmp_data_pos_x <= .25d ? Bry__origin_x__left : Bry__origin_x__right;
						}
					}
					if (!Double_.IsNaN(tmp_data_pos_y)) {
						if (tmp_data_pos_y >= -1 && tmp_data_pos_y <= 1)
							data_pos_y = tmp_data_pos_y;
					}
				}
			}
			if (tid == -1) Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown arg type; page=~{0} key=~{1} val=~{2}", page.Ttl().Full_db(), key, val);
		}

		byte[] banner_name = Eval_argx(ctx, src, caller, self);
		Xoa_ttl banner_ttl = wiki.Ttl_parse(banner_name); // NOTE: MW also creates title to auto-register page and image in imagelinks
		if (banner_ttl == null)		// if ttl is invalid, get it from wikidata; PAGE:en.v:Diving_the_Cape_Peninsula_and_False_Bay/Whale_Rock; DATE:2016-07-12
			banner_ttl = Get_wikidata_banner(wiki.Appe(), wiki, wiki.Xtn_mgr().Xtn_pgbnr().Cfg(), page.Ttl());
		if (banner_ttl == null) {	// if ttl is still invalid, exit now else will fail with nullref below; PAGE:en.v:Peterborough (New Hampshire); DATE:2016-07-12
			Gfo_usr_dlg_.Instance.Warn_many("", "", "banner file is invalid; page=~{0} banner=~{1}", page.Url_bry_safe(), banner_name);
			return;
		}
		Xof_file_itm banner_file_itm = File__make_tkn(ctx, Xop_file_logger_.Tid__pgbnr_main, banner_ttl, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null);

		Pgbnr_itm itm = new Pgbnr_itm();
		itm.Init_from_wtxt(banner_ttl, banner_file_itm, tooltip, title, bottomtoc, toc, data_pos_x, data_pos_y, origin_x, icons_list == null ? Pgbnr_icon.Ary_empty : (Pgbnr_icon[])icons_list.To_ary_and_clear(Pgbnr_icon.class));
		page.Html_data().Xtn_pgbnr_(itm);
		page.Html_data().Head_mgr().Itm__pgbnr().Enabled_y_();	// register css / js during parse stage
		page.Wtxt().Toc().Flag__toc_(true);	// NOTE: must mark toc_manual else will show 2nd TOC in edit mode; DATE:2016-07-10
	}
	public static void Add_banner(Bry_bfr bfr, Xoae_page wpg, Xop_ctx ctx, Xoh_wtr_ctx hctx, Pgbnr_itm itm) {
		Xowe_wiki wiki = ctx.Wiki(); Xoae_app app = wiki.Appe();
		Pgbnr_cfg cfg = wiki.Xtn_mgr().Xtn_pgbnr().Cfg(); if (!cfg.enabled) return;
		Xoa_ttl ttl = wpg.Ttl();
		Xoa_ttl banner_ttl = null; byte[] banner_html = null;
		if (itm != null) {							// {{PAGEBANNER}} exists in wikitext
			itm.Init_hdump(hctx.Mode_is_hdump());
			banner_ttl = itm.banner_ttl;
			banner_html = Get_banner_html(wiki, ctx, hctx, cfg, banner_ttl, itm);
			if (banner_html == null) {	// no banner; try again using title from wikidata; note that this should only happen if no banner_ttl or banner_ttl is invalid; EX:{{PAGEBANNER:|toc=yes}}
				banner_ttl = Get_wikidata_banner(app, wiki, cfg, ttl);
				banner_html = Get_banner_html(wiki, ctx, hctx, cfg, banner_ttl, itm);
			}
		}
		else if (	ttl.Ns().Id_is_main()			// {{PAGEBANNER}} missing, but wiki is marked as enable_default_banner
				&&	cfg.enable_default_banner
				&&	cfg.Chk_pgbnr_allowed(ttl, wiki)
				) {
			banner_ttl = Get_wikidata_banner(app, wiki, cfg, ttl);
			if (banner_ttl == null)
				banner_ttl = wiki.Ttl_parse(cfg.dflt_img_title);
			Xof_file_itm banner_file_itm = File__make_tkn(ctx, Xop_file_logger_.Tid__pgbnr_main, banner_ttl, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null);
			itm = new Pgbnr_itm();
			itm.Init_from_wtxt(banner_ttl, banner_file_itm, Bry_.Empty, Bry_.Empty, false, Bry_.Empty, 0, 0, Bry_.Empty, Pgbnr_icon.Ary_empty);
			itm.Init_hdump(hctx.Mode_is_hdump());
			banner_html = Get_banner_html(wiki, ctx, hctx, cfg, banner_ttl, itm);
		}
		if (banner_html != null)
			bfr.Add(banner_html);
	}
	public static byte[] Get_banner_html(Xowe_wiki wiki, Xop_ctx ctx, Xoh_wtr_ctx hctx, Pgbnr_cfg cfg, Xoa_ttl banner_ttl, Pgbnr_itm itm) {
		byte[][] urls = Get_standard_size_urls(wiki, cfg, banner_ttl); if (urls == null) return null;
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		int urls_len = urls.length;
		int[] sizes = cfg.standard_sizes;
		for (int i = 0; i < urls_len; ++i) {
			int size = sizes[i];
			if (i != 0) tmp_bfr.Add_byte_comma();
			tmp_bfr.Add(urls[i]).Add_byte_space().Add_int_variable(size).Add_byte(Byte_ascii.Ltr_w); //	REF.MW: $srcset[] = "$url {$size}w";
		}
		byte[] srcset = tmp_bfr.To_bry_and_clear();
		byte[] banner_url = itm.banner_img_src != null ? itm.banner_img_src : urls.length == 0 ? Bry_.Empty : urls[urls_len - 1];	// gets largest url			
		int max_width = itm.banner_file_itm.Orig_w();  // $file = wfFindFile( banner_file ); $options['max_width'] = $file->getWidth();
		byte[] banner_file = null;   // $bannerfile->getLocalUrl();

		byte[] toc_html = null;
		if (hctx.Mode_is_hdump()) {
			gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_tag(tmp_bfr, true);
			toc_html = tmp_bfr.To_bry_and_clear();
			banner_file = Bry_.Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, gplx.xowa.wikis.nss.Xow_ns_.Bry__file, Byte_ascii.Colon_bry
				, Gfo_url_encoder_.Href.Encode(banner_ttl.Full_db()));	// NOTE: must encode so "'" becomes "%27", not "&#39;"; PAGE:en.v:'s-Hertogenbosch; DATE:2016-07-12
		}
		else {
			ctx.Page().Html_data().Toc_mgr().To_html(tmp_bfr, Xoh_wtr_ctx.Basic, true);
			toc_html = tmp_bfr.To_bry_and_clear();
		}
		itm.Init_from_html(max_width, banner_file, banner_url, srcset, cfg.enable_heading_override, toc_html);

		Mustache_render_ctx mctx = new Mustache_render_ctx().Init(itm);
		Mustache_bfr mbfr = Mustache_bfr.New_bfr(tmp_bfr);
		wiki.Xtn_mgr().Xtn_pgbnr().Template_root().Render(mbfr, mctx);
		return mbfr.To_bry_and_clear();
	}
	private static byte[][] Get_standard_size_urls(Xow_wiki wiki, Pgbnr_cfg cfg, Xoa_ttl banner_ttl) {
		Ordered_hash hash = Ordered_hash_.New_bry();
		int[] sizes = cfg.standard_sizes;
		int sizes_len = sizes.length;
		for (int i = 0; i < sizes_len; ++i) {
			byte[] url = Get_image_url(wiki, banner_ttl, sizes[i]);
			if (url != null)
				hash.Add_if_dupe_use_1st(url, url);
		}
		return (byte[][])hash.To_ary_and_clear(byte[].class);
	}
	private static byte[] Get_image_url(Xow_wiki wiki, Xoa_ttl banner_ttl, int width) {
		// Object file = new Object(); // $file = wfFindFile( file_ttl );
		// if (file == null) return null;
		if (width >= 0 && width <= 3000) {
			// $mto = $file->transform( array( 'width' => $imagewidth ) );
			byte[] url = new byte[0];	// $url = wfExpandUrl( $mto->getUrl(), PROTO_CURRENT );
			return url;
		}
		else
			return new byte[0];		//	$file->getFullUrl();
	}
	private static Xoa_ttl Get_wikidata_banner(Xoae_app app, Xow_wiki wiki, Pgbnr_cfg cfg, Xoa_ttl ttl) {
		byte[] rv = app.Wiki_mgr().Wdata_mgr().Get_claim_or(wiki.Domain_itm(), ttl, cfg.dflt_img_wdata_prop, null);	// don't log misses; wikivoyage pages will default to show pagebanner, and many pages may not have wikidata definitions
		if (rv == null) return null;
		return wiki.Ttl_parse(rv);
	}
	private static Xof_file_itm File__make_tkn(Xop_ctx ctx, byte tid, Xoa_ttl file_ttl, int file_w, int file_h) {
		Xop_lnki_tkn lnki = ctx.Tkn_mkr().Lnki(file_w, file_h).Ttl_(file_ttl);
		ctx.Page().Lnki_list().Add(lnki);
		ctx.Lnki().File_logger().Log_file(ctx, lnki, tid);	// NOTE: do not set file_wkr ref early (as member var); parse_all sets late
		Xof_file_itm file_itm = ctx.Wiki().Html_mgr().Html_wtr().Lnki_wtr().File_wtr().Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, ctx.Page(), lnki);
		return file_itm;
	}
	private static final    byte[] Bry__yes = Bry_.new_a7("yes"), Bry__icon = Bry_.new_a7("icon-"), Bry__icon_href_dflt = Bry_.new_a7("#"), Bry__origin_x__left = Bry_.new_a7("wpb-left"), Bry__origin_x__right = Bry_.new_a7("wpb-right");
	private static final int Arg__pgname = 0, Arg__tooltip = 1, Arg__bottomtoc = 2, Arg__toc = 3, Arg__icon = 4, Arg__origin = 5;
	private static final    Hash_adp_bry arg_hash = Hash_adp_bry.cs().Add_str_int("pgname", Arg__pgname)
		.Add_str_int("tooltip", Arg__tooltip).Add_str_int("bottomtoc", Arg__bottomtoc).Add_str_int("toc", Arg__toc).Add_str_int("origin", Arg__origin);
}
