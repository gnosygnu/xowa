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
package gplx.xowa.addons.wikis.pages.randoms.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.pages.randoms.dbs.*; import gplx.xowa.addons.wikis.pages.randoms.bldrs.*;
public class Rndm_mgr {
	public Rndm_mgr(Xow_wiki wiki) {
		this.db_mgr = new Rndm_db_mgr(wiki).Init();
	}
	public Rndm_db_mgr Db_mgr() {return db_mgr;} private Rndm_db_mgr db_mgr;

	public int Get_rndm_page(int qry_idx) {
		// 0|type:ns,ns_id:123,text:123|123
		int rng_end = db_mgr.Tbl__qry().Select_rng_end(qry_idx);
		Rndm_rng_itm rng_end_itm = db_mgr.Tbl__rng().Select_by_rng_idx_or_noop(qry_idx, rng_end);
		int rndm_num = RandomAdp_.new_().Next(rng_end_itm.Seq_end());
		Rndm_rng_itm rng_itm = db_mgr.Tbl__rng().Select_by_rndm_num_or_noop(qry_idx, rndm_num);
		int seq_idx = rndm_num - rng_itm.Seq_bgn();
		int page_id = db_mgr.Tbl__seq().Select_or_neg_1(qry_idx, rng_itm.Rng_idx(), seq_idx);
		Gfo_log_.Instance.Info("get_random_page", "qry_idx", qry_idx, "rng_end", rng_end, "rndm_num", rndm_num, "rng_idx", rng_itm.Rng_idx(), "seq_idx", seq_idx, "page_id", page_id);
		return page_id;
	}
	public Rndm_bldr_wkr New_bldr() {return new Rndm_bldr_wkr(db_mgr.Conn(), db_mgr.Tbl__qry(), db_mgr.Tbl__rng(), db_mgr.Tbl__seq());}

	public static final int Qry_idx__main = 0;	// all
}
