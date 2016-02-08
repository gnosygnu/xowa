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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*;
public class Sql_val_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Null()					{fxt.Test__val(null									, "NULL");}
	@Test   public void Bool__n()				{fxt.Test__val(Bool_.N								, "0");}
	@Test   public void Bool__y()				{fxt.Test__val(Bool_.Y								, "1");}
	@Test   public void Byte()					{fxt.Test__val(Byte_.By_int(2)						, "2");}
	@Test   public void Short()					{fxt.Test__val(Short_.By_int(3)						, "3");}
	@Test   public void Int()					{fxt.Test__val(4									, "4");}
	@Test   public void Long()					{fxt.Test__val(5									, "5");}
	@Test   public void Float()					{fxt.Test__val(6.1f									, "6.1");}
	@Test   public void Double()				{fxt.Test__val(7.1d									, "7.1");}
	@Test   public void Decimal()				{fxt.Test__val(Decimal_adp_.float_(8)				, "'8'");}
	@Test   public void Str()					{fxt.Test__val("abc"								, "'abc'");}
	@Test   public void Str__apos_mid()			{fxt.Test__val("a'b"								, "'a''b'");}
	@Test   public void Str__apos_bgn()			{fxt.Test__val("'ab"								, "'''ab'");}
	@Test   public void Str__apos_end()			{fxt.Test__val("ab'"								, "'ab'''");}
	@Test   public void Str__apos_many()		{fxt.Test__val("a'b'c"								, "'a''b''c'");}
	@Test   public void Str__back()				{fxt.Test__val("a\\b"								, "'a\\b'");}
	@Test   public void Date()					{fxt.Test__val(DateAdp_.parse_gplx("2016-02-03")	, "'2016-02-03 00:00:00.000'");}
}
