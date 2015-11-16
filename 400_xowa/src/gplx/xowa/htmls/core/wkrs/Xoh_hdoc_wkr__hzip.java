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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.ttls.*;
public class Xoh_hdoc_wkr__hzip implements Xoh_hdoc_wkr {
	private final Hzip_stat_itm stat_itm = new Hzip_stat_itm();
	private Bry_bfr bfr; private Xoh_hdoc_ctx hctx; private byte[] src; private int src_end;
	public void On_new_page(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hctx = hctx; this.src = src; this.src_end = src_end;
		stat_itm.Clear();
	}
	public void On_txt		(int rng_bgn, int rng_end)								{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_escape	(int rng_bgn, int rng_end)								{hctx.Mkr().Escape__hzip().Encode(bfr, stat_itm).Pool__rls();}
	public void On_space	(int rng_bgn, int rng_end)								{hctx.Mkr().Space__hzip().Encode(bfr, stat_itm, src, src_end, rng_bgn, rng_end).Pool__rls();}
	public void On_hdr		(gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_parser arg)		{hctx.Mkr().Hdr__hzip().Encode(bfr, stat_itm, src, arg).Pool__rls();}
	public void On_lnke		(gplx.xowa.htmls.core.wkrs.lnkes.Xoh_lnke_parser arg)	{hctx.Mkr().Lnke__hzip().Encode(bfr, stat_itm, src, arg).Pool__rls();}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_parser arg)	{hctx.Mkr().Lnki__hzip().Encode(bfr, hctx, stat_itm, src, arg).Pool__rls();}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_parser arg)		{hctx.Mkr().Thm__hzip().Encode(bfr, this, stat_itm, src, arg).Pool__rls();}
	public void On_img		(gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_parser arg)		{hctx.Mkr().Img__hzip().Encode(bfr, stat_itm, src, arg, Bool_.Y).Pool__rls();}
}
