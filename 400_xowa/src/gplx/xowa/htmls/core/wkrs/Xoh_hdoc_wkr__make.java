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
import gplx.langs.htmls.parsers.*;
import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_hdoc_wkr__make implements Xoh_hdoc_wkr {
	private Bry_bfr bfr; private Xoh_page hpg; private Xoh_hdoc_ctx hctx; private byte[] src;
	private final Xoh_hdr_make wkr__hdr = new Xoh_hdr_make();
	private final Xoh_img_bldr wkr__img = new Xoh_img_bldr();		
	public void On_new_page(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
	}
	public void On_escape	(int rng_bgn, int rng_end)								{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_txt		(int rng_bgn, int rng_end)								{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_space	(int rng_bgn, int rng_end)								{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_lnke		(gplx.xowa.htmls.core.wkrs.lnkes.Xoh_lnke_parser arg)	{bfr.Add_mid(src, arg.Rng_bgn(), arg.Rng_end());}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_parser arg)	{bfr.Add_mid(src, arg.Rng_bgn(), arg.Rng_end());}
	public void On_hdr		(gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_parser arg)		{wkr__hdr.Make(bfr, hpg, src, arg);}
	public void On_img		(gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_parser arg)		{wkr__img.Make_by_parse(bfr, hpg, hctx, src, arg);}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_parser arg)		{bfr.Add_mid(src, arg.Rng_bgn(), arg.Rng_end());}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_parser arg) {}
}
