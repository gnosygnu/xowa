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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.langs.mustaches.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.utils.*;
public class Pgbnr_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_pagebanner;}
	@Override public Pf_func New(int id, byte[] name) {return new Pgbnr_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) { // {{PAGEBANNER}} appears on page; WikidataPageBanner.hooks.php|addCustomBanner
		Xowe_wiki wiki = ctx.Wiki();
		Pgbnr_xtn_mgr xtn_mgr = wiki.Xtn_mgr().Xtn_pgbnr();
		Pgbnr_cfg cfg = xtn_mgr.Cfg();
		Xoa_ttl ttl = ctx.Page().Ttl();
		if (!cfg.Chk_pgbnr_allowed(ttl, wiki)) return;
		byte[] tooltip = ttl.Page_txt(), title = ttl.Page_txt(), toc = Bry_.Empty, origin_x = Bry_.Empty;
		boolean bottomtoc = false;;
		double data_pos_x = 0, data_pos_y = 0;
		List_adp icons_list = null;
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		int args_len = self.Args_len();
		for (int i = 0; i < args_len; ++i) {
			Arg_nde_tkn arg = self.Args_get_by_idx(i);
			byte[] key = Pf_func_.Eval_tkn(tmp_bfr, ctx, src, caller, arg.Key_tkn());
			byte[] val = Pf_func_.Eval_tkn(tmp_bfr, ctx, src, caller, arg.Val_tkn());
			int tid = arg_hash.Get_as_int_or(key, -1);
			if (tid == Arg__pgname)
				tooltip = title = val;
			if (tid == Arg__tooltip)	// note that this overrides pgname above
				tooltip = val;
			if (tid == Arg__bottomtoc	&& Bry_.Eq(val, Bry__yes))
				bottomtoc = true;
			if (tid == Arg__toc			&& Bry_.Eq(val, Bry__yes))						// REF.MW:addToc
				toc = Bry_.Empty;						// note that "" will be overwritten later by actual toc html
			if (	tid == -1							// handle "icon-*"; 
				&&	Bry_.Has_at_bgn(key, Bry__icon)		// if (substr($key, 0, 5) === 'icon-')
				&&	Bry_.Len(Bry__icon)	> 5				// if ( !isset( $iconname) )
				&&	Bry_.Len_gt_0(val)					// if ( !isset( $$value ) ) 
				) {										// REF.MW:addIcons
				tid = Arg__icon;
				if (icons_list == null) icons_list = List_adp_.new_();
				byte[] icon_key = Bry_.Mid(key, 5);
				byte[] icon_name = Xop_sanitizer.Escape_cls(icon_key);
				byte[] icon_title = icon_name;
				Xoa_ttl icon_url_ttl = wiki.Ttl_parse(val);
// TODO: get icon_url
				byte[] icon_url_bry = Bry_.Empty;
				if (icon_url_ttl == null)
					icon_url_bry = Bry__url_dflt;		// $iconUrl = Title::newFromText( $value ); if ( $iconUrl )
				else  {
					icon_url_bry = Bry_.Empty;			// $iconUrl->getLocalUrl();
					icon_title = ttl.Page_txt();
				}
				icons_list.Add(new Pgbnr_icon(icon_name, icon_title, icon_url_bry));
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
			if (tid == -1) Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown arg type; page=~{0} key=~{1} val=~{2}", "page", ctx.Page().Url_bry_safe(), key, val);
		}
		byte[] name = Eval_argx(ctx, src, caller, self);
		// Xoa_ttl file_ttl = wiki.Ttl_parse(name); SKIP?: MW creates title to auto-register page and image in imagelinks
		Pgbnr_itm itm = new Pgbnr_itm();
		ctx.Page().Html_data().Xtn__pgbnr_(itm);
		itm.Init_from_wtxt(name, tooltip, title, bottomtoc, toc, data_pos_x, data_pos_y, origin_x, icons_list == null ? Pgbnr_icon.Ary_empty : (Pgbnr_icon[])icons_list.To_ary_and_clear(Pgbnr_icon.class));
	}
	public static void Add_banner(Bry_bfr bfr, Xop_ctx ctx) {
		Xowe_wiki wiki = ctx.Wiki();
		Pgbnr_cfg cfg = wiki.Xtn_mgr().Xtn_pgbnr().Cfg();
		Xoa_ttl ttl = ctx.Page().Ttl();
		Pgbnr_itm itm = ctx.Page().Html_data().Xtn__pgbnr();
		byte[] banner_name = null, banner_html = null;
		if (itm != null) {	// {{PAGEBANNER}} exists in wikitext
			banner_name = itm.name;
			banner_html = Get_banner_html(wiki, cfg, banner_name, itm);
			if (banner_html == null) {	// no banner; try again using title from wikidata;
				banner_name = Get_wikidata_banner(ttl);
				banner_html = Get_banner_html(wiki, cfg, banner_name, itm);
			}
			if (banner_html != null) {	// only add banner and styling if valid banner generated
				if (itm.toc != null) {
					// $out->addModuleStyles( 'ext.WikidataPageBanner.toc.styles' );
				}
				bfr.Add(banner_html);
			}
		}
		else if (	ttl.Ns().Id_is_main()			// if the page uses no 'PAGEBANNER' invocation and if article page, insert default banner
				&&	cfg.Get__wpb_enable_default_banner()
				) {
			if (cfg.Chk_pgbnr_allowed(ttl, wiki)) { 
				banner_name = Get_wikidata_banner(ttl);
				if (banner_name == null) {
					banner_name = cfg.Get__wpb_image();
				}
				itm = new Pgbnr_itm();
				itm.name = banner_name;
				banner_html = Get_banner_html(wiki, cfg, banner_name, itm);
				if (banner_html != null) {	// NOTE: same as above
					bfr.Add(banner_html);
				}
			}
		}
	}
	public static byte[] Get_banner_html(Xowe_wiki wiki, Pgbnr_cfg cfg, byte[] banner_name, Pgbnr_itm itm) {
		byte[][] urls = Get_standard_size_urls(wiki, cfg, banner_name);
		if (urls == null) return null;
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		int urls_len = urls.length;
		int[] sizes = cfg.Get__wpb_standard_sizes();
		for (int i = 0; i < urls_len; ++i) {
			int size = sizes[i];
			if (i != 0) tmp_bfr.Add_byte_comma();
			tmp_bfr.Add(urls[i]).Add_byte_space().Add_int_variable(size).Add_byte(Byte_ascii.Ltr_w); //	$srcset[] = "$url {$size}w";
		}
		byte[] srcset = tmp_bfr.To_bry_and_clear();
		byte[] banner_url = urls.length == 0 ? Bry_.Empty : urls[urls_len - 1];	// gets largest url
		// Xoa_ttl banner_file = wiki.Ttl_parse(tmp_bfr.Add(wiki.Ns_mgr().Ns_file().Name_db_w_colon()).Add(banner_name).To_bry_and_clear());
		int maxWidth = 1;  // $file = wfFindFile( banner_file ); $options['maxWidth'] = $file->getWidth();
		byte[] banner_file = null;   // $bannerfile->getLocalUrl();
		itm.Init_from_html(maxWidth, banner_file, banner_url, srcset, cfg.Get__wpb_enable_heading_override());

		Mustache_render_ctx mctx = new Mustache_render_ctx().Init(itm);
		wiki.Xtn_mgr().Xtn_pgbnr().Template_root().Render(tmp_bfr, mctx);
		return tmp_bfr.To_bry_and_clear();
	}
	private static byte[][] Get_standard_size_urls(Xow_wiki wiki, Pgbnr_cfg cfg, byte[] file_name) {
		Ordered_hash hash = Ordered_hash_.New_bry();
		int[] sizes = cfg.Get__wpb_standard_sizes();
		int sizes_len = sizes.length;
		for (int i = 0; i < sizes_len; ++i) {
			byte[] url = Get_image_url(wiki, file_name, sizes[i]);
			if (url != null)
				hash.Add_if_dupe_use_1st(url, url);
		}
		return (byte[][])hash.To_ary_and_clear(byte[].class);
	}
	private static byte[] Get_image_url(Xow_wiki wiki, byte[] file_name, int width) {
		// Xoa_ttl file_ttl = wiki.Ttl_parse(file_name);
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
	private static byte[] Get_wikidata_banner(Xoa_ttl ttl) {
		return null;
	}
	private static final byte[] Bry__yes = Bry_.new_a7("yes"), Bry__icon = Bry_.new_a7("icon-"), Bry__url_dflt = Bry_.new_a7("#"), Bry__origin_x__left = Bry_.new_a7("wpb-left"), Bry__origin_x__right = Bry_.new_a7("wpb-right");
	private static final int Arg__pgname = 0, Arg__tooltip = 1, Arg__bottomtoc = 2, Arg__toc = 3, Arg__icon = 4, Arg__origin = 5;
	private static final Hash_adp_bry arg_hash = Hash_adp_bry.cs().Add_str_int("pgname", Arg__pgname)
		.Add_str_int("tooltip", Arg__tooltip).Add_str_int("bottomtoc", Arg__bottomtoc).Add_str_int("toc", Arg__toc).Add_str_int("origin", Arg__origin);
}
