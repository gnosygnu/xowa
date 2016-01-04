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
package gplx.xowa.htmls.core.wkrs.xndes.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.brys.*;
import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.xndes.dicts.*;
public interface Xohz_atr_itm {
	int Uid();		// EX: 1
	byte[] Key();	// EX: colspan=2
	void Ini_flag	(int flag_idx, List_adp flag_bldr_list);
	void Enc_flag	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr);
	void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr);
	void Dec_all	(Xoh_hdoc_ctx hctx, Bry_rdr rdr, Int_flag_bldr flag_bldr, Bry_bfr bfr);
}
