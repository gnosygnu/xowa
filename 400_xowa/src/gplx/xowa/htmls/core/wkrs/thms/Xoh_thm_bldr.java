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
import gplx.core.primitives.*; import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_thm_bldr {
	private final Xoh_thm_wtr wtr = new Xoh_thm_wtr();
	private final byte[] div_2_magnify = Bry_.new_a7("bin/any/xowa/file/mediawiki.file/magnify-clip.png");
	public void Make(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int div_0_align, int div_1_width, boolean div_2_alt_exists, byte[] img_alt, Xoh_img_bldr img_bldr, Bfr_arg div_2_href, Bfr_arg div_2_capt) {
		wtr.Clear();
		wtr.Div_0_align_(div_0_align);
		wtr.Div_1_id_(img_bldr.Fsdb_itm().Html_uid());
		wtr.Div_1_width_(div_1_width);
		wtr.Div_1_img_(img_bldr.Wtr());
		wtr.Div_2_href_(div_2_href);
		wtr.Div_2_magnify_(hctx.Fsys__root(), div_2_magnify);
		wtr.Div_2_capt_(div_2_capt);
		wtr.Div_2_alt_(div_2_alt_exists, img_alt);
		wtr.Bfr_arg__add(bfr);
	}
}
