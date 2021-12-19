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
package gplx.xowa.xtns.wbases.claims;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.IntRef;
import org.junit.*;
import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wbase_claim_grp_tst {
	@Test public void Get_best__preferred() {
		Wbase_claim_grp_bldr bldr = new Wbase_claim_grp_bldr(123);
		bldr.Add("P1", Wbase_claim_rank_.Tid__preferred);
		bldr.Add("N1", Wbase_claim_rank_.Tid__normal);
		bldr.Add("P2", Wbase_claim_rank_.Tid__preferred);
		bldr.Add("N2", Wbase_claim_rank_.Tid__normal);
		bldr.Test__Get_best("P1", "P2");
	}
	@Test public void Get_best__normal_if_no_preferred() {
		Wbase_claim_grp_bldr bldr = new Wbase_claim_grp_bldr(123);
		bldr.Add("D1", Wbase_claim_rank_.Tid__deprecated);
		bldr.Add("D2", Wbase_claim_rank_.Tid__deprecated);
		bldr.Add("N1", Wbase_claim_rank_.Tid__normal);
		bldr.Add("N2", Wbase_claim_rank_.Tid__normal);
		bldr.Test__Get_best("N1", "N2");
	}
	@Test public void Get_best__preferred_after_normal() {
		Wbase_claim_grp_bldr bldr = new Wbase_claim_grp_bldr(123);
		bldr.Add("N1", Wbase_claim_rank_.Tid__normal);
		bldr.Add("N2", Wbase_claim_rank_.Tid__normal);
		bldr.Add("P1", Wbase_claim_rank_.Tid__preferred);
		bldr.Add("P2", Wbase_claim_rank_.Tid__preferred);
		bldr.Test__Get_best("P1", "P2");
	}
}
class Wbase_claim_grp_bldr {
	private final int pid;
	private final List_adp list = List_adp_.New();
	public Wbase_claim_grp_bldr(int pid) {
		this.pid = pid;
	}
	public void Add(String val, byte rank_tid) {
		Wbase_claim_string claim = new Wbase_claim_string(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(val));
		claim.Rank_tid_(rank_tid);
		list.Add(claim);
	}
	public void Test__Get_best(String... expd) {
		Wbase_claim_grp grp = new Wbase_claim_grp(IntRef.New(pid), (Wbase_claim_base[])list.ToAryAndClear(Wbase_claim_base.class));

		List_adp tmp_list = List_adp_.New();
		GfoTstr.EqLines(expd, To_string(grp.Get_best(tmp_list)));
	}
	private String[] To_string(Wbase_claim_base[] items) {
		int len = items.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Wbase_claim_string claim = (Wbase_claim_string)items[i];
			rv[i] = StringUtl.NewU8(claim.Val_bry());
		}
		return rv;
	}
}
