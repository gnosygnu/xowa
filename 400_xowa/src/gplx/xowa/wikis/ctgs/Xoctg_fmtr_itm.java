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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*; import gplx.xowa.htmls.core.htmls.*;
interface Xoctg_fmtr_itm extends gplx.core.brys.Bfr_arg {
	int Grp_end_idx();
	boolean Grp_end_at_col();
	int Col_idx(); void Col_idx_(int col_idx, int col_bgn);
	void Init_from_all(Xowe_wiki wiki, Xol_lang_itm lang, Xoh_wtr_ctx hctx, Xoctg_view_ctg ctg, Xoctg_fmtr_all mgr, Xoctg_view_grp itms_list, int itms_list_len);
	void Init_from_grp(byte[] grp_ttl, int i);
}
