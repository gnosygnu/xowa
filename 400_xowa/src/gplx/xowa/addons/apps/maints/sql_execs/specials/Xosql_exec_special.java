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
package gplx.xowa.addons.apps.maints.sql_execs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.maints.*; import gplx.xowa.addons.apps.maints.sql_execs.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
public class Xosql_exec_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		
		String domain = url_args.Read_str_or("domain", "[xowa.home]");
		String db = url_args.Read_str_or("db", "core");
		String sql = url_args.Read_str_or("sql", "SELECT * FROM xowa_cfg;");

		new Xosql_exec_html(domain, db, sql).Bld_page_by_mustache(wiki.App(), page, this);
	}
	Xosql_exec_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final Xow_special_page Prototype = new Xosql_exec_special(Xow_special_meta.New_xo("XowaSql", "SQL Exec"));
}
