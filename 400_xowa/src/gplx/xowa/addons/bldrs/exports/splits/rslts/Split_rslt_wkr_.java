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
package gplx.xowa.addons.bldrs.exports.splits.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
public class Split_rslt_wkr_ {
	public static final    Split_rslt_wkr Noop = new Split_rslt_wkr__noop();
}
class Split_rslt_wkr__noop implements Split_rslt_wkr {
	public byte Tid() {return Split_rslt_tid_.Tid_max;}
	public int Row_count() {return 0;}
	public long Obj_size() {return 0;}
	public void On__init(Split_rslt_mgr rslt_mgr, Db_conn wkr_conn) {}
	public void On__nth__new(int db_id) {}
	public void On__nth__rls() {}
	public void On_term() {}
}