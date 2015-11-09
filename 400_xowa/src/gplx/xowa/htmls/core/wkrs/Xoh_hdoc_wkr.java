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
public interface Xoh_hdoc_wkr {
	Xoh_hdoc_ctx	Ctx();
	void On_new_page(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src, int src_bgn, int src_end);
	void On_escape	(int rng_bgn, int rng_end);
	void On_txt		(int rng_bgn, int rng_end);
	void On_space	(int rng_bgn, int rng_end);
	void On_hdr		(int tag_bgn, int tag_end, int level, int capt_bgn, int capt_end, byte[] anch);
	void On_lnke	(int tag_bgn, int tag_end, byte lnke_type, int autonumber_id, int href_bgn, int href_end);
	void On_lnki	(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_parser arg);
	void On_img		(gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_parser arg);
	void On_img_thm	(int rng_bgn, int rng_end);
}
