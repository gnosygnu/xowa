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
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_hdoc_wkr__make implements Xoh_hdoc_wkr {
	private final Xoh_hdr_make wkr__hdr = new Xoh_hdr_make();
	private final Xoh_img_make wkr__img = new Xoh_img_make();
	private Bry_bfr bfr; private Xoh_page hpg; private byte[] src;
	public Xoh_hdoc_ctx	Ctx() {return ctx;} private final Xoh_hdoc_ctx ctx = new Xoh_hdoc_ctx();
	public void On_new_page(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.src = src; this.hpg = hpg;
		ctx.Ctor(wiki);
	}
	public void On_escape	(int rng_bgn, int rng_end) {bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_txt		(int rng_bgn, int rng_end) {bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_space	(int rng_bgn, int rng_end) {bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_hdr		(int rng_bgn, int rng_end, int level, int capt_bgn, int capt_end, byte[] anch) {
		wkr__hdr.Make(bfr, hpg, src, rng_bgn, rng_end, level, capt_bgn, capt_end, anch);
	}
	public void On_lnke		(int rng_bgn, int rng_end, byte lnke_type, int autonumber_id, int href_bgn, int href_end) {bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_parser arg) {bfr.Add_mid(src, arg.Rng_bgn(), arg.Rng_end());}
	public void On_img		(gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_parser arg) {wkr__img.Make(bfr, hpg, src, arg);}
	public void On_img_thm	(int rng_bgn, int rng_end) {bfr.Add_mid(src, rng_bgn, rng_end);}
}
