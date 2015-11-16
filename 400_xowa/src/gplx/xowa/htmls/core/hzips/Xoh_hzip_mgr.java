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
import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.*;
public class Xoh_hzip_mgr {
	private final Xoh_hdoc_parser hdoc_parser = new Xoh_hdoc_parser(new Xoh_hdoc_wkr__hzip());		
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Xoh_hzip_dict_.Escape);
	public Xoh_hdoc_ctx Hctx() {return hctx;} private final Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	public void Init_by_app(Xoa_app app) {hctx.Init_by_app(app);}
	public void Encode(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		hctx.Init_by_page(wiki, hpg.Url_bry_safe());
		hdoc_parser.Parse(bfr, hpg, hctx, src);
	}
	public void Decode(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		byte[] page_url = hpg.Url_bry_safe();
		hctx.Init_by_page(wiki, page_url);
		int pos = 0, txt_bgn = -1, src_len = src.length;
		rdr.Init_by_page(page_url, src, src_len);
		while (pos < src_len) {
			if (src[pos] == Xoh_hzip_dict_.Escape) {
				if (txt_bgn != -1) {bfr.Add_mid(src, txt_bgn, pos); txt_bgn = -1;}	// handle pending txt
				int nxt_pos = pos + 1; if (nxt_pos == src_len) break;				// handle escape at end of document
				Xoh_hzip_wkr wkr = hctx.Mkr().Hzip__wkr(src[nxt_pos]);
				try {
					rdr.Init_by_hook(wkr.Key(), pos, pos + 2);
					wkr.Decode(bfr, Bool_.Y, hctx, hpg, rdr, src, pos);
					pos = rdr.Pos();
				} catch (Exception e) {
					wkr.Pool__rls();
					Err err = Err_.cast_or_make(e);
					if (!err.Logged()) Gfo_usr_dlg_.Instance.Warn_many("", "", Err_.Message_gplx_log(e), "page_url", page_url, "mid", Bry_.Mid_by_len_safe(src, pos, 255));
					pos += 2;	// 2: skip escape and hook
				}
			}
			else {
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
		}
		if (txt_bgn != -1) bfr.Add_mid(src, txt_bgn, src_len);
	}
}
