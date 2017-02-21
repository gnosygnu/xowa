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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
public interface Merge2_trg_itm {
	int			Idx();
	Db_conn		Conn();
}
class Merge2_trg_itm__wiki implements Merge2_trg_itm {
	public Merge2_trg_itm__wiki(int idx, Db_conn conn) {this.idx = idx; this.conn = conn;}
	public int			Idx()	{return idx;}	private final    int idx;
	public Db_conn		Conn()	{return conn;}	private final    Db_conn conn;
}
