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
package gplx.xowa.xtns.wbases;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.core.Wdata_alias_itm;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
public class Wdata_doc_bldr {
	private Ordered_hash descr_list, label_list, slink_list, alias_list, claim_list;
	public Wdata_doc_bldr() {this.Init();}
	public Wdata_doc_bldr Qid_(String v) {this.qid = BryUtl.NewA7(v); return this;} private byte[] qid;
	public Wdata_doc_bldr Add_claims(Wbase_claim_base... ary) {
		if (ary.length == 0) throw ErrUtl.NewArgs("claims must be greater than 0");
		Wbase_claim_base itm = ary[0];
		Wbase_claim_grp grp = new Wbase_claim_grp(IntRef.New(itm.Pid()), ary);
		claim_list.Add(grp.Id_ref(), grp);
		return this;
	}		
	public Wdata_doc_bldr Add_description(String lang, String text) {byte[] key = BryUtl.NewU8(lang); descr_list.Add(key, new Wdata_langtext_itm(key, BryUtl.NewU8(text))); return this;}
	public Wdata_doc_bldr Add_label(String lang, String text)		{byte[] key = BryUtl.NewU8(lang); label_list.Add(key, new Wdata_langtext_itm(key, BryUtl.NewU8(text))); return this;}
	public Wdata_doc_bldr Add_sitelink(String site, String link, String... bdgs) {
		byte[] key = BryUtl.NewU8(site);
		slink_list.Add(key, new Wdata_sitelink_itm(key, BryUtl.NewU8(link), BryUtl.Ary(bdgs)));
		return this;
	}
	public Wdata_doc_bldr Add_alias(String lang, String... ary){byte[] key = BryUtl.NewU8(lang); alias_list.Add(key, new Wdata_alias_itm	  (key, BryUtl.Ary(ary))); return this;}
	public Wdata_doc Xto_wdoc() {
		Wdata_doc rv = new Wdata_doc(null, null, qid).Ctor_by_test(slink_list, label_list, descr_list, alias_list, claim_list);
		this.Init();
		return rv;
	}
	private void Init() {
		descr_list = Ordered_hash_.New_bry(); label_list = Ordered_hash_.New_bry(); slink_list = Ordered_hash_.New_bry(); alias_list = Ordered_hash_.New_bry(); claim_list = Ordered_hash_.New();
	}
}
