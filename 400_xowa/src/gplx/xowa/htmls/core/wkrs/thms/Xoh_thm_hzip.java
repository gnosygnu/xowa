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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.divs.*; 
public class Xoh_thm_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final int[] flag_ary;
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_ ( 2);
	private final Xoh_img_hzip img_hzip = new Xoh_img_hzip();
	private final Xoh_thm_bldr bldr = new Xoh_thm_bldr();
	private final Bry_obj_ref div_2_capt = Bry_obj_ref.New_empty();
	public Xoh_thm_hzip() {this.flag_ary = flag_bldr.Val_ary();}
	public String Key() {return Xoh_hzip_dict_.Key__thm;}
	public Xoh_thm_hzip Encode(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Hzip_stat_itm stat_itm, byte[] src, Xoh_thm_parser arg) {
		Xoh_thm_caption_parser div_2_capt_parser = arg.Capt_parser();
		flag_ary[ 0] = arg.Div_0_align();

		bfr.Add(Xoh_hzip_dict_.Bry__thm);
		Xoh_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		bfr.Add_mid(src, div_2_capt_parser.Capt_bgn(), div_2_capt_parser.Capt_end()).Add_byte(Xoh_hzip_dict_.Escape);
		img_hzip.Encode(bfr, stat_itm, src, arg.Img_parser(), Bool_.N);
		return this;
	}
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		int flag = rdr.Read_int_by_base85(1);
		int capt_bgn = rdr.Pos();
		int capt_end = rdr.Find_fwd_lr();
		int rv = rdr.Pos();

		flag_bldr.Decode(flag);
		int div_align = flag_ary[ 0];
		div_2_capt.Mid_(src, capt_bgn, capt_end);
		img_hzip.Decode(bfr, Bool_.N, hctx, hpg, rdr, src, rv);
		bldr.Make(bfr, hpg, hctx, src, div_align, img_hzip.Bldr(), img_hzip.Anch_href_arg(), div_2_capt);
		return rv;
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_thm_hzip rv = new Xoh_thm_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
