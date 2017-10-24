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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
class Wdata_visitor__lbl_gatherer implements Wbase_claim_visitor {
	private Wdata_lbl_mgr lbl_mgr;
	public Wdata_visitor__lbl_gatherer(Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr;}
	public void Visit_entity(Wbase_claim_entity itm) {
		if (itm.Entity_tid_is_qid())
			lbl_mgr.Queue_if_missing__qid(itm.Entity_id());
		else
			lbl_mgr.Queue_if_missing__pid(itm.Entity_id());
	}
	public void Visit_time(Wbase_claim_time itm) {
		byte[] ttl = Wdata_lbl_itm.Extract_ttl(itm.Calendar());
		itm.Calendar_ttl_(ttl);
		lbl_mgr.Queue_if_missing__ttl(ttl);
	}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		byte[] ttl = Wdata_lbl_itm.Extract_ttl(itm.Glb());
		itm.Glb_ttl_(ttl);
		lbl_mgr.Queue_if_missing__ttl(ttl);
	}
	public void Visit_str(Wbase_claim_string itm) {}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm) {}
	public void Visit_quantity(Wbase_claim_quantity itm) {}
	public void Visit_system(Wbase_claim_value itm) {}
}
