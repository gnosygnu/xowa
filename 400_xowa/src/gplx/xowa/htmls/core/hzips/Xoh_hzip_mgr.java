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
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.ttls.*;
public class Xoh_hzip_mgr implements Xoh_hzip_wkr {
	private final Xoh_hdoc_wkr hdoc_wkr = new Xoh_hdoc_wkr__hzip();
	private final Xoh_hdoc_parser hdoc_parser;
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Xoh_hzip_dict_.Escape);
	public Xoh_hzip_mgr() {this.hdoc_parser = new Xoh_hdoc_parser(hdoc_wkr);}
	public String Key() {return "root";}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Xoh_hdoc_ctx Hctx() {return hctx;} private final Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	public void Init_by_app(Xoa_app app) {hctx.Init_by_app(app);}
	public byte[] Encode_as_bry(Xoh_hzip_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {Encode(bfr, wiki, hpg, src); return bfr.To_bry_and_clear();}
	public Gfo_poolable_itm	Encode(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {throw Err_.new_unimplemented();}
	public void Encode(Xoh_hzip_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		hctx.Init_by_page(wiki, hpg.Url_bry_safe());
		hdoc_parser.Parse(bfr, hpg, hctx, src);
	}
	public void Decode(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		byte[] page_url = hpg.Url_bry_safe(); int src_len = src.length;
		hctx.Init_by_page(wiki, page_url);
		rdr.Init_by_page(page_url, src, src_len);
		Decode(bfr, hdoc_wkr, hctx, hpg, Bool_.Y, rdr, src, 0, src_len);
	}
	public int Decode(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, Bry_rdr rdr, byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn, txt_bgn = -1;
		while (true) {
			if (pos == src_end) break;			
			byte b = src[pos];
			Object o = hctx.Wkr_mkr().Get(b, src, pos, src_end);
			if (o == null) {
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
			else {
				if (txt_bgn != -1) {bfr.Add_mid(src, txt_bgn, pos); txt_bgn = -1;}	// handle pending txt
				Xoh_hzip_wkr wkr = (Xoh_hzip_wkr)o;
				try {
					rdr.Init_by_sect(wkr.Key(), pos, pos + 2);
					wkr.Decode(bfr, hdoc_wkr, hctx, hpg, Bool_.Y, rdr, src, pos, src_end);
					pos = rdr.Pos();
				} catch (Exception e) {
					gplx.langs.htmls.Html_utl.Log(e, "hzip decode failed", hpg.Url_bry_safe(), src, pos);
					pos += 2;	// 2: skip escape and hook
				}
				finally {wkr.Pool__rls();}
			}
		}
		if (txt_bgn != -1) bfr.Add_mid(src, txt_bgn, src_end);
		return src_end;
	}		
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_hzip_mgr rv = new Xoh_hzip_mgr(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
