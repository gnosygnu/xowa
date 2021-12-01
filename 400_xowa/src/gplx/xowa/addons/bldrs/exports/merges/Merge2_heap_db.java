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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
public class Merge2_heap_db implements Merge2_trg_itm {
	public Merge2_heap_db(Split_tbl tbl, DbmetaFldList flds, int idx, Io_url url, Db_conn conn) {
		this.tbl = tbl; this.flds = flds; this.idx = idx;
		this.url = url; this.conn = conn; 
	}
	public Split_tbl		Tbl()	{return tbl;}	private final Split_tbl tbl;
	public DbmetaFldList Flds()	{return flds;}	private final DbmetaFldList flds;
	public int				Idx()	{return idx;}	private final int idx;
	public Io_url			Url()	{return url;}	private final Io_url url;
	public Db_conn			Conn()	{return conn;}	private final Db_conn conn;
}
