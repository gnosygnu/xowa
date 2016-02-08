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
import org.junit.*; import gplx.core.strings.*;
import gplx.core.criterias.*; /*Criteria_base*/
import gplx.core.ios.*; import gplx.dbs.sqls.*; import gplx.dbs.sqls.wtrs.*;
public class Sql_qry_wtr__iosql__tst {
	@Test  public void Type() {
		fld = IoItm_base_.Prop_Type;
		tst_Write("type = 1", ioCrt_(fld, Criteria_.eq_(IoItmDir.Type_Dir)));
		tst_Write("type = 2", ioCrt_(fld, Criteria_.eq_(IoItmFil.Type_Fil)));
	}
	@Test  public void Ext() {
		fld = IoItm_base_.Prop_Ext;
		tst_Write("ext = '.txt'", ioCrt_(fld, Criteria_.eq_(".txt")));
		tst_Write("ext IN ('.txt', '.xml', '.html')", ioCrt_(fld, Criteria_.in_(".txt", ".xml", ".html")));
		tst_Write("ext NOT IN ('.dll', '.exe')", ioCrt_(fld, Criteria_.inn_(".dll", ".exe")));
	}
	@Test  public void Title() {
		fld = IoItm_base_.Prop_Title;
		tst_Write("title = 'bin'", ioCrt_(fld, Criteria_.eq_("bin")));
		tst_Write("title NOT IN ('bin', 'obj')", ioCrt_(fld, Criteria_.inn_("bin", "obj")));
	}
	@Test  public void Url() {
		fld = IoItm_base_.Prop_Path;
		tst_Write("url = 'C:\\fil.txt'", ioCrt_(fld, Criteria_.eq_("C:\\fil.txt")));
		tst_Write("url IOMATCH '*.txt'", ioCrt_(fld, Criteria_ioMatch.parse(true, "*.txt", false)));
	}
	@Test  public void Binary() {
		// parentheses around lhs and rhs
		tst_Write(
			"(type = 1 OR type = 2)"
			, Criteria_.Or
			(	ioCrt_(IoItm_base_.Prop_Type, Criteria_.eq_(IoItmDir.Type_Dir)), ioCrt_(IoItm_base_.Prop_Type, Criteria_.eq_(IoItmFil.Type_Fil))
			));
	}
	Criteria ioCrt_(String fld, Criteria crt) {return Criteria_fld.new_(fld, crt);}
	String fld;
	private final Sql_wtr_ctx ctx = new Sql_wtr_ctx(false);
	void tst_Write(String expd, Criteria crt) {
		Sql_where_wtr where_wtr = ((Sql_core_wtr)Sql_qry_wtr_.Basic).Where_wtr();
		Bry_bfr bfr = Bry_bfr.new_();
		where_wtr.Bld_where_elem(bfr, ctx, crt);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
