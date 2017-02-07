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
package gplx.xowa.htmls.core.wkrs.addons.timelines; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_timeline_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid()		{return Xoh_hzip_dict_.Tid__timeline;}
	public String Key()		{return Xoh_hzip_dict_.Key__timeline;}
	public byte[] Hook()	{return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_timeline_data data = (Xoh_timeline_data)data_obj;

		// just add the entire thing; not worth trying to compress "<pre class='xowa-timeline'>"
		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());

		hctx.Hzip__stat().Timeline_add();
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int timeline_bgn = src_bgn;
		int timeline_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt, src_bgn, src_end);
		if (timeline_end == -1) {
			Gfo_log_.Instance.Warn("hzip.timeline.end_not_found", "page", hpg.Url_bry_safe(), "src_bgn", src_bgn);
			timeline_end = timeline_bgn;
		}
		else
			++timeline_end;

		Xoh_timeline_data data = (Xoh_timeline_data)data_itm;
		data.Init_by_decode(timeline_bgn, timeline_end);
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_timeline_hzip rv = new Xoh_timeline_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
