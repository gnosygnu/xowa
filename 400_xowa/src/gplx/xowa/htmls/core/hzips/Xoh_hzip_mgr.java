/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.htmls.core.wkrs.tocs.*;
public class Xoh_hzip_mgr implements Xoh_hzip_wkr {
	private final    Xoh_hdoc_wkr hdoc_wkr = new Xoh_hdoc_wkr__hzip();
	private final    Xoh_hdoc_parser hdoc_parser;
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Xoh_hzip_dict_.Escape);
	private final    Xoh_page_bfr bfr_mgr = new Xoh_page_bfr();
	public Xoh_hzip_mgr() {this.hdoc_parser = new Xoh_hdoc_parser(hdoc_wkr);}
	public int Tid() {return Xoh_hzip_dict_.Tid__lnke;}
	public String Key() {return "root";}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Xoh_hdoc_ctx Hctx() {return hctx;} private final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	public void Init_by_app(Xoa_app app) {hctx.Init_by_app(app);}
	public byte[] Encode_as_bry(Xoh_hzip_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {Encode(bfr, wiki, hpg, src); return bfr.To_bry_and_clear();}
	public Gfo_poolable_itm	Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {throw Err_.new_unimplemented();}
	public void Encode(Xoh_hzip_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		hctx.Init_by_page(wiki, hpg);
		hdoc_parser.Parse(bfr, hpg, hctx, src);
	}
	public void Decode(Bry_bfr bfr, Xow_wiki wiki, Xoh_page hpg, byte[] src) {
		hctx.Init_by_page(wiki, hpg);
		rdr.Init_by_page(hpg.Url_bry_safe(), src, src.length);
		Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, 0, src.length, null);
	}
	public void Decode1(Bry_bfr html_bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int pos = src_bgn, txt_bgn = -1;
		boolean toc_enabled = !gplx.core.envs.Op_sys.Cur().Tid_is_drd() && !hctx.Mode_is_diff();
		Bry_bfr bfr = html_bfr;
		if (toc_enabled)
			bfr_mgr.Init(html_bfr);
		while (true) {
			if (pos == src_end) break;			
			byte b = src[pos];
			Object o = hctx.Pool_mgr__hzip().Get(b, src, pos, src_end);
			if (o == null) {
				if (txt_bgn == -1) txt_bgn = pos;
				++pos;
			}
			else {
				if (txt_bgn != -1) {bfr.Add_mid(src, txt_bgn, pos); txt_bgn = -1;}	// handle pending txt
				Xoh_hzip_wkr wkr = (Xoh_hzip_wkr)o;
				int hook_len = wkr.Hook().length;
				try {
					rdr.Init_by_sect(wkr.Key(), pos, pos + hook_len);
					Xoh_data_itm data = hctx.Pool_mgr__data().Get_by_tid(wkr.Tid());
					wkr.Decode1(bfr, hdoc_wkr, hctx, hpg, rdr, src, pos, src_end, data);
					Xoh_wtr_itm wtr = hctx.Pool_mgr__wtr().Get_by_tid(wkr.Tid());
					if (data != null && wtr != null) {
						wtr.Init_by_decode(hpg, hctx, src, data);
						wtr.Bfr_arg__add(bfr);
					}
					if (data != null) data.Pool__rls();
					if (wtr != null) wtr.Pool__rls();
					if (	data != null
						&&	toc_enabled 
						&&	data.Tid() == Xoh_hzip_dict_.Tid__toc) {
						bfr = bfr_mgr.Split_by_toc(((Xoh_toc_data)data).Toc_mode());	// NOTE: must go after wtr.Init_by_decode else toc_mode flag won't be set correctly
					}
					pos = rdr.Pos();
				} catch (Exception e) {
					gplx.langs.htmls.Gfh_utl.Log(e, "hzip decode failed", hpg.Url_bry_safe(), src, pos);
					pos += hook_len;
				}
				finally {wkr.Pool__rls();}
			}
		}
		if (txt_bgn != -1) bfr.Add_mid(src, txt_bgn, src_end);
		if (toc_enabled) bfr_mgr.Commit(hpg);
	}		
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_hzip_mgr rv = new Xoh_hzip_mgr(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
