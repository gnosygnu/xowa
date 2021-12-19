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
package gplx.dbs.sqls.wtrs;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import org.junit.Test;
public class Sql_val_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test public void Null()					{fxt.Test__val(null									, "NULL");}
	@Test public void Bool__n()				{fxt.Test__val(BoolUtl.N								, "0");}
	@Test public void Bool__y()				{fxt.Test__val(BoolUtl.Y								, "1");}
	@Test public void Byte()					{fxt.Test__val(ByteUtl.ByInt(2)						, "2");}
	@Test public void Int()					{fxt.Test__val(4									, "4");}
	@Test public void Long()					{fxt.Test__val(5									, "5");}
	@Test public void Float()					{fxt.Test__val(6.1f									, "6.1");}
	@Test public void Double()				{fxt.Test__val(7.1d									, "7.1");}
	@Test public void Decimal()				{fxt.Test__val(GfoDecimalUtl.NewByFloat(8)				, "'8'");}
	@Test public void Str()					{fxt.Test__val("abc"								, "'abc'");}
	@Test public void Str__apos_mid()			{fxt.Test__val("a'b"								, "'a''b'");}
	@Test public void Str__apos_bgn()			{fxt.Test__val("'ab"								, "'''ab'");}
	@Test public void Str__apos_end()			{fxt.Test__val("ab'"								, "'ab'''");}
	@Test public void Str__apos_many()		{fxt.Test__val("a'b'c"								, "'a''b''c'");}
	@Test public void Str__back()				{fxt.Test__val("a\\b"								, "'a\\b'");}
	@Test public void Date()					{fxt.Test__val(GfoDateUtl.ParseGplx("2016-02-03")	, "'2016-02-03 00:00:00.000'");}
}
