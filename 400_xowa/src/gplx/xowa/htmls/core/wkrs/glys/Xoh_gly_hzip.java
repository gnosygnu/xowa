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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xoh_gly_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__gly;}
	public Xoh_gly_hzip Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, Xoh_gly_grp_parser arg) {
		bfr.Add_mid(src, arg.Rng_bgn(), arg.Rng_end());
		return this;
	}
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		return hook_bgn;
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_gly_hzip rv = new Xoh_gly_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
//		private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_( 1, 1, 1, 1		, 1, 1, 1, 1	, 2, 1, 1, 1	, 1, 2, 2);	
//		private static final int // SERIALIZED
//		  Flag__file__w_diff_from_html			=  0
//		, Flag__file__time_exists				=  1
//		, Flag__file__page_exists				=  2
//		, Flag__file__is_orig					=  3
//		, Flag__file__repo_is_local				=  4
//		, Flag__file__src_exists				=  5
//		, Flag__img__cls_other_exists			=  6
//		, Flag__anch__ns_is_image				=  7
//		, Flag__anch__cls_tid					=  8	// none, image
//		, Flag__anch__ns_id_needs_saving		=  9
//		, Flag__img__alt_diff_from_anch_title	= 10
//		, Flag__anch__href_diff_file			= 11
//		, Flag__anch__title_missing				= 12
//		, Flag__img__cls_tid					= 13	// none, thumbimage, thumbborder
//		, Flag__anch__href_tid					= 14	// wiki, site, anch, inet
//		;
}
