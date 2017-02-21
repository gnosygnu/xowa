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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*; import gplx.core.criterias.*;
public class Sql_where_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Eq()		{fxt.Test__where(Db_crt_.New_eq			("fld", 1)				, "fld = 1");}
	@Test   public void Eq_not()	{fxt.Test__where(Db_crt_.New_eq_not		("fld", 1)				, "fld != 1");}
	@Test   public void Eq_pre()	{fxt.Test__where(Db_crt_.New_eq			("a", "fld", 1)			, "a.fld = 1");}
	@Test   public void Lt()		{fxt.Test__where(Db_crt_.New_lt			("fld", 1)				, "fld < 1");}
	@Test   public void Lte()		{fxt.Test__where(Db_crt_.New_lte		("fld", 1)				, "fld <= 1");}
	@Test   public void Mt()		{fxt.Test__where(Db_crt_.New_mt			("fld", 1)				, "fld > 1");}
	@Test   public void Mte()		{fxt.Test__where(Db_crt_.New_mte		("fld", 1)				, "fld >= 1");}
	@Test   public void Between()	{fxt.Test__where(Db_crt_.New_between	("fld", 1, 3)			, "fld BETWEEN 1 AND 3");}
	@Test   public void In()		{fxt.Test__where(Db_crt_.New_in			("fld", 1, 2, 3)		, "fld IN (1, 2, 3)");}
	@Test   public void Like()		{fxt.Test__where(Db_crt_.New_like		("fld", "A%")			, "fld LIKE 'A%' ESCAPE '|'");}
	@Test  public void And__subs__2() {
		fxt.Test__where
		( Criteria_.And
		(	Db_crt_.New_eq("id", 1)
		,	Db_crt_.New_eq("name", "me")
		), "(id = 1 AND name = 'me')");
	}
	@Test  public void Or__subs__2() {
		fxt.Test__where
		( Criteria_.Or
		(	Db_crt_.New_eq("id", 1)
		,	Db_crt_.New_eq("name", "me")
		), "(id = 1 OR name = 'me')");
	}
	@Test  public void Nested() {
		fxt.Test__where
		( Criteria_.Or
		(	Db_crt_.New_eq("id", 1)
		,	Criteria_.And
		(		Db_crt_.New_eq("name", "me")
		,		Db_crt_.New_eq("id", 2))
		), "(id = 1 OR (name = 'me' AND id = 2))");
	}
}
