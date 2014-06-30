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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Sql_fld_mgr_tst {
Sql_fld_mgr_fxt fxt = new Sql_fld_mgr_fxt();
	@Test  public void Basic() {
		fxt.Exec_parse(String_.Concat_lines_nl
		(	"ignore"
		,	"CREATE TABLE tbl_0 ("	
		,	"  `fld_2` int,"
		,	"  `fld_1` int,"
		,	"  `fld_0` int,"
		,	"  UNIQUE KEY idx_0 (fld_2)"
		,	");"
		));
		fxt.Test_count(3);
		fxt.Exec_get("fld_0",  2);
		fxt.Exec_get("fld_1",  1);
		fxt.Exec_get("fld_2",  0);
		fxt.Exec_get("fld_3", -1);
	}
}
class Sql_fld_mgr_fxt {
	Sql_fld_mgr fld_mgr = new Sql_fld_mgr();
	public void Exec_parse(String v) {fld_mgr.Parse(Bry_.new_ascii_(v));}
	public void Exec_get(String key, int expd) {
		Sql_fld_itm actl_itm = fld_mgr.Get_by_key(key);
		Tfds.Eq(expd, actl_itm == null ? Sql_fld_mgr.Not_found : actl_itm.Idx());
	}
	public void Test_count(int expd) {Tfds.Eq(expd, fld_mgr.Count());}
}
