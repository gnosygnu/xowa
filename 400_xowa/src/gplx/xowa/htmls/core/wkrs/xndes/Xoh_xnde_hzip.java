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
package gplx.xowa.htmls.core.wkrs.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.xndes.tags.*;
public class Xoh_xnde_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__xnde;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public int Tid() {return Xoh_hzip_dict_.Tid__xnde;}
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_xnde_parser parser = (Xoh_xnde_parser)data_obj;
		Xohz_tag tag = hctx.Hzip__xnde__regy().Get_by_key(parser.Name_bry());
		bfr.Add(hook);
		bfr.Add_byte((byte)tag.Uid());
		tag.Encode(hctx, bfr, src, parser);
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		byte uid = rdr.Read_byte();
		Xohz_tag tag = hctx.Hzip__xnde__regy().Get_by_uid(uid);
		tag.Decode(hctx, bfr, rdr, src, src_bgn, src_end);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_xnde_hzip rv = new Xoh_xnde_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
