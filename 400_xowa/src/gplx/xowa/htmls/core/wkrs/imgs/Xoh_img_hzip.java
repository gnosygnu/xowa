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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xoh_img_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final int[] flag_ary;
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_
	( 1, 1, 1, 1, 1, 1, 1, 1
	, 1, 1, 1, 2, 2);
	public Xoh_img_hzip() {
		this.flag_ary = flag_bldr.Val_ary();
	}
	public String Key() {return Xoh_hzip_dict_.Key__img;}
	public Xoh_img_bldr Bldr() {return bldr__img;} private Xoh_img_bldr bldr__img = new Xoh_img_bldr();
	public Xoh_img_hzip Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, Xoh_img_parser arg, boolean write_hdr) {
		// img_map: <img id="xowa_file_img_100" alt="" src="file:///J:/xowa/file/commons.wikimedia.org/orig/b/8/a/7/Solar_System_Template_Final.png" width="666" height="36" usemap="#imagemap_1_1">
		Xoh_anch_href_parser anch_href = arg.Anch_href_parser();
		Html_atr anch_title = arg.Anch_title_atr();
		Xoa_ttl anch_href_ttl = anch_href.Page_ttl();
		Xoh_img_xoimg_parser img_xoimg = arg.Img_xoimg_parser();
		Xoh_img_cls_parser img_cls = arg.Img_cls_parser();
		Xoh_img_src_parser img_src = arg.Img_src_parser();
		boolean anch_href_has_site = anch_href.Site_exists();
		boolean anch_href_is_file = anch_href_ttl.Ns().Id_is_file() && !anch_href_has_site;
		boolean img_alt__diff__anch_title = arg.Img_alt__diff__anch_title();
		flag_ary[ 0] = img_xoimg.Val_exists() ? 1 : 0;
		flag_ary[ 1] = anch_href_has_site ? 1 : 0;
		flag_ary[ 2] = anch_href_is_file ? 1 : 0;
		flag_ary[ 3] = anch_title.Val_exists() ? 1 : 0;
		flag_ary[ 4] = img_alt__diff__anch_title ? 1 : 0;
		flag_ary[ 5] = img_cls.Other_exists() ? 1 : 0;
		flag_ary[ 6] = img_src.Repo_is_commons() ? 1 : 0;
		flag_ary[ 7] = img_src.File_is_orig() ? 1 : 0;
		flag_ary[ 8] = arg.File_w__diff__img_w() ? 1 : 0;
		flag_ary[ 9] = img_src.File_time_exists() ? 1 : 0;
		flag_ary[10] = img_src.File_page_exists() ? 1 : 0;
		flag_ary[11] = arg.Anch_cls_parser().Tid();
		flag_ary[12] = img_cls.Cls_tid();
		if (write_hdr) bfr.Add(Xoh_hzip_dict_.Bry__img);
		Xoh_hzip_int_.Encode(3, bfr, flag_bldr.Encode());
		Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + arg.Img_w());
		Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + arg.Img_h());
		bfr.Add(anch_href_ttl.Page_db()).Add_byte(Xoh_hzip_dict_.Escape);
		if (!anch_href_is_file) {
			Xoh_lnki_dict_.Ns_encode(bfr, anch_href_ttl.Ns().Id());
			bfr.Add_mid(src, img_src.File_ttl_bgn(), img_src.File_ttl_end()).Add_byte(Xoh_hzip_dict_.Escape);
		}
		if (arg.File_w__diff__img_w())		Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_w());
		if (img_src.File_time_exists())		Xoh_hzip_int_.Encode(1, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_time());
		if (img_src.File_page_exists())		Xoh_hzip_int_.Encode(1, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_page());
		if (img_xoimg.Val_exists())			bfr.Add_mid(src, img_xoimg.Val_bgn(), img_xoimg.Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (anch_title.Val_exists())		bfr.Add_mid(src, anch_title.Val_bgn(), anch_title.Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (img_alt__diff__anch_title)		bfr.Add_mid(src, arg.Img_alt_atr().Val_bgn(), arg.Img_alt_atr().Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (img_cls.Other_exists())			bfr.Add_mid(src, img_cls.Other_bgn(), img_cls.Other_end()).Add_byte(Xoh_hzip_dict_.Escape);
		return this;
	}
	private final Xoh_img_xoimg_parser xoimg_parser = new Xoh_img_xoimg_parser();		
	public Bfr_arg__href Anch_href_arg() {return anch_href_arg;} private final Bfr_arg__href anch_href_arg = new Bfr_arg__href();
	private final Bry_obj_ref
	  anch_cls_mid		= Bry_obj_ref.New_empty()
	, anch_title_mid	= Bry_obj_ref.New_empty()
	, img_alt_mid		= Bry_obj_ref.New_empty()
	, img_src_mid		= Bry_obj_ref.New_empty()
	, img_cls_mid		= Bry_obj_ref.New_empty()
	;
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		// decode rdr
		int flag = rdr.Read_int_by_base85(3);
		flag_bldr.Decode(flag);
		int img_w = rdr.Read_int_by_base85(2) - Xoh_hzip_int_.Neg_1_adj;
		int img_h = rdr.Read_int_by_base85(2) - Xoh_hzip_int_.Neg_1_adj;
		byte[] page_db = rdr.Read_bry_to();
		boolean img_xoimg_exists				= flag_ary[ 0] == 1;
		boolean anch_href_has_site				= flag_ary[ 1] == 1;
		boolean anch_href_is_file				= flag_ary[ 2] == 1;
		boolean anch_title_exists				= flag_ary[ 3] == 1;
		boolean img_alt__diff__anch_title		= flag_ary[ 4] == 1;
		boolean img_cls_other_exists			= flag_ary[ 5] == 1;
		boolean repo_is_commons				= flag_ary[ 6] == 1;
		boolean file_is_orig					= flag_ary[ 7] == 1;
		int anch_cls						= flag_ary[11];
		int img_cls							= flag_ary[12];
		int anch_href_ns = -1; byte[] anch_href_ttl_bry = null;
		if (!anch_href_is_file) {
			anch_href_ns = Xoh_lnki_dict_.Ns_decode(rdr);
			anch_href_ttl_bry = rdr.Read_bry_to();
		}
		int file_time = -1; int file_page = -1;
		int img_xoimg_bgn = -1; int img_xoimg_end = -1;
		xoimg_parser.Clear();
		if (img_xoimg_exists) {
			img_xoimg_bgn = rdr.Pos();
			img_xoimg_end = rdr.Find_fwd_lr();
			xoimg_parser.Parse(rdr, src, img_xoimg_bgn, img_xoimg_end);
		}
		int anch_title_bgn = -1, anch_title_end = -1;
		if (anch_title_exists) {
			anch_title_bgn = rdr.Pos();
			anch_title_end = rdr.Find_fwd_lr();				
		}
		int img_alt_bgn = -1, img_alt_end = -1;
		if (anch_title_exists) {
			img_alt_bgn = anch_title_bgn;
			img_alt_end = anch_title_end;
		}
		if (img_alt__diff__anch_title) {
			img_alt_bgn = rdr.Pos();
			img_alt_end = rdr.Find_fwd_lr();
		}
		byte[] img_cls_other = Bry_.Empty;
		if (img_cls_other_exists)
			img_cls_other = Bry_.Mid(src, rdr.Pos(), rdr.Find_fwd_lr());
		int rv = rdr.Move_to(rdr.Pos());

		// transform values
		if (anch_href_is_file)
			anch_href_arg.Set_by_file(page_db);
		else {
			if (anch_href_has_site) {
			}
			Xoa_ttl anch_href_ttl = hctx.Wiki__ttl_parser().Ttl_parse(anch_href_ns, page_db);
			anch_href_arg.Set_by_page(anch_href_ttl.Full_db());
			page_db = anch_href_ttl_bry;
		}
		// NOTE: src must go underneath ttl
		Xof_url_bldr url_bldr = hctx.File__url_bldr();
		url_bldr.Init_by_root(repo_is_commons ? hctx.Fsys__file__comm() : hctx.Fsys__file__wiki(), Byte_ascii.Slash, false, false, Md5_depth);
		url_bldr.Init_by_itm(file_is_orig ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, page_db, Xof_file_wkr_.Md5(page_db), Xof_ext_.new_by_ttl_(page_db), img_w, file_time, file_page);
		byte[] img_src = url_bldr.Xto_bry();

		anch_cls_mid.Val_(Xoh_anch_cls_.To_val(anch_cls));
		anch_title_mid.Mid_(src, anch_title_bgn, anch_title_end);
		img_alt_mid.Mid_(src, img_alt_bgn, img_alt_end);
		img_src_mid.Val_(img_src);
		img_cls_mid.Val_(Xoh_img_cls_.To_val(img_cls, img_cls_other));

		bldr__img.Make(bfr, hpg, hctx, src, page_db, xoimg_parser, anch_href_arg, anch_cls_mid, anch_title_mid, img_w, img_h, img_src_mid, img_cls_mid, img_alt_mid);
		if (write_to_bfr) bldr__img.Wtr().Bfr_arg__add(bfr);

		return rv;
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_hzip rv = new Xoh_img_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	public static int Md5_depth = 2;
}
