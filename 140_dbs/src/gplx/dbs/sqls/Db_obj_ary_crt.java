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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
import gplx.core.criterias.*;
public class Db_obj_ary_crt implements gplx.core.criterias.Criteria {
	public byte Tid() {return Criteria_.Tid_db_obj_ary;}
	public Db_fld[] Flds() {return flds;} public Db_obj_ary_crt Flds_(Db_fld[] v) {this.flds = v; return this;} private Db_fld[] flds;
	public Object[][]		Vals() {return vals;} public void Vals_(Object[][] v) {this.vals = v;} private Object[][] vals;
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public boolean				Matches(Object obj) {return false;}
	public String			XtoStr() {return "";}
	public static Db_obj_ary_crt new_(Db_fld... flds) {return new Db_obj_ary_crt().Flds_(flds);}
	public static Db_obj_ary_crt new_by_type(byte type_tid, String... names) {
		int len = names.length;
		Db_fld[] flds = new Db_fld[len];
		for (int i = 0; i < len; i++)
			flds[i] = new Db_fld(names[i], type_tid);
		return new Db_obj_ary_crt().Flds_(flds);
	}
}
