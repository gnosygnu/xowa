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
public class Db_obj_ary_fld {
	public Db_obj_ary_fld(String name, int type_tid) {this.name = name; this.type_tid = type_tid;}
	public String Name() {return name;} public Db_obj_ary_fld Name_(String v) {name = v; return this;} private String name;
	public int Type_tid() {return type_tid;} public Db_obj_ary_fld Type_tid_(int v) {type_tid = v; return this;} private int type_tid;
}
