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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.stats.*;
import gplx.xowa.htmls.core.wkrs.*; import gplx.langs.htmls.parsers.*;
public class Xoh_hzip_mgr {
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Xoh_hzip_dict_.Escape);
	private final Xoh_hdoc_wkr__base hdoc_parser = new Xoh_hdoc_wkr__base(new Xoh_hdoc_wkr__hzip());
	private final Xoh_decode_ctx decode_ctx = new Xoh_decode_ctx();
	public void Init_by_app(Xoa_app app) {decode_ctx.Init_by_app(app);}
	public void Encode(Bry_bfr bfr, Xow_wiki wiki, byte[] page_url, byte[] src, Hzip_stat_itm stat_itm) {
		hdoc_parser.Parse(bfr, wiki, null, page_url, src);
	}
	public byte[] Decode(Bry_bfr bfr, Xow_wiki wiki, byte[] page_url, byte[] src) {
		bfr.Clear();
		((gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_hzip)Xoh_hzip_dict_.To_wkr(Xoh_hzip_dict_.Tid__lnki)).Ttl_parser_(wiki);
		int pos = 0, add_bgn = -1; int src_len = src.length;
		rdr.Ctor_by_page(page_url, src, src_len);
		decode_ctx.Init_by_page(wiki);
		while (pos < src_len) {
			if (src[pos] == Xoh_hzip_dict_.Escape) {
				if (add_bgn != -1) {bfr.Add_mid(src, add_bgn, pos); add_bgn = -1;}
				try {
					Xoh_hzip_wkr wkr = Xoh_hzip_dict_.To_wkr(src[pos + 1]);
					rdr.Init_by_hook(wkr.Key(), pos, pos + 2);
					wkr.Decode(bfr, decode_ctx, rdr, src, pos);
					pos = rdr.Pos();
				} catch (Exception e) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", Err_.Message_gplx_log(e));
					pos += 2;
				}
			}
			else {
				if (add_bgn == -1) add_bgn = pos;
				++pos;
			}
		}
		if (add_bgn != -1) bfr.Add_mid(src, add_bgn, src_len);
		return bfr.To_bry_and_clear();
	}
}
