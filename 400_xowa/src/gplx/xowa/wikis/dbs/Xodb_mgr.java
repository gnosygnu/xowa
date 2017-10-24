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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.htmls.core.*;
public interface Xodb_mgr extends Gfo_invk {
	byte Tid();
	String Tid_name();
	byte Category_version();
	Xodb_load_mgr Load_mgr();
	Xodb_save_mgr Save_mgr();
	DateAdp Dump_date_query(); // used by maint_mgr
}
