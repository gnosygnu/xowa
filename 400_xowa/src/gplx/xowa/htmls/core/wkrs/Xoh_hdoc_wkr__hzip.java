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
	private final Xoh_stat_itm stat_itm = new Xoh_stat_itm();
	private Xoh_hzip_bfr bfr; private Xoh_hdoc_ctx hctx; private byte[] src;
	private Xoh_page hpg;
	public void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
		stat_itm.Clear();
	}
	public void On_txt		(int rng_bgn, int rng_end)									{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_escape	(int rng_bgn, int rng_end)									{hctx.Wkr_mkr().Mw__escape().Encode(bfr, this, hctx, hpg, Bool_.Y, src, null).Pool__rls();}
	public void On_hdr		(gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_parser parser)		{hctx.Wkr_mkr().Mw__hdr().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
	public void On_lnke		(gplx.xowa.htmls.core.wkrs.lnkes.Xoh_lnke_parser parser)	{hctx.Wkr_mkr().Mw__lnke().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_parser parser)	{hctx.Wkr_mkr().Mw__lnki().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
	public void On_img		(gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_parser parser)		{hctx.Wkr_mkr().Mw__img().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_parser parser)		{hctx.Wkr_mkr().Mw__thm().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_parser parser)	{hctx.Wkr_mkr().Mw__gly().Encode(bfr, this, hctx, hpg, Bool_.Y, src, parser).Pool__rls();}
}
