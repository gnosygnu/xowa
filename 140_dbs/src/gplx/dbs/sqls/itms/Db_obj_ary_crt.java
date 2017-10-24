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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.core.criterias.*;
public class Db_obj_ary_crt implements gplx.core.criterias.Criteria {
	public byte Tid() {return Criteria_.Tid_db_obj_ary;}
	public Db_obj_ary_fld[] Flds() {return flds;} public Db_obj_ary_crt Flds_(Db_obj_ary_fld[] v) {this.flds = v; return this;} private Db_obj_ary_fld[] flds;
	public Object[][]		Vals() {return vals;} public void Vals_(Object[][] v) {this.vals = v;} private Object[][] vals;
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public boolean				Matches(Object obj) {return false;}
	public String			To_str() {return "";}
	public static Db_obj_ary_crt new_(Db_obj_ary_fld... flds) {return new Db_obj_ary_crt().Flds_(flds);}
	public static Db_obj_ary_crt new_by_type(byte type_tid, String... names) {
		int len = names.length;
		Db_obj_ary_fld[] flds = new Db_obj_ary_fld[len];
		for (int i = 0; i < len; i++)
			flds[i] = new Db_obj_ary_fld(names[i], type_tid);
		return new Db_obj_ary_crt().Flds_(flds);
	}
}
