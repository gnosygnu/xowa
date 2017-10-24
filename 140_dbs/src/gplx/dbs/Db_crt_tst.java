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
package gplx.dbs; import gplx.*;
import org.junit.*; import gplx.core.criterias.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public class Db_crt_tst {
	@Before public void setup() {
		row = GfoNde_.vals_(GfoFldList_.new_().Add("id", IntClassXtn.Instance).Add("name", StringClassXtn.Instance), Object_.Ary(1, "me"));
	}
	@Test  public void EqualTest() {
		crt = Db_crt_.New_eq("id", 1);
		tst_Match(true, row, crt);
	}
	@Test  public void EqualFalseTest() {
		crt = Db_crt_.New_eq("id", 2);
		tst_Match(false, row, crt);
	}
	@Test  public void AndCompositeTest() {
		crt = Criteria_.And(Db_crt_.New_eq("id", 1), Db_crt_.New_eq("name", "me"));
		tst_Match(true, row, crt);

		crt = Criteria_.And(Db_crt_.New_eq("id", 1), Db_crt_.New_eq("name", "you"));
		tst_Match(false, row, crt);
	}
	@Test  public void OrCompositeTest() {
		crt = Criteria_.Or(Db_crt_.New_eq("id", 1), Db_crt_.New_eq("name", "you"));
		tst_Match(true, row, crt);

		crt = Criteria_.Or(Db_crt_.New_eq("id", 2), Db_crt_.New_eq("name", "you"));
		tst_Match(false, row, crt);
	}

	void tst_Match(boolean epxd, GfoNde row, Criteria crt) {
		boolean actl = crt.Matches(row);
		Tfds.Eq(epxd, actl);
	}
	GfoNde row; Criteria crt;
}
