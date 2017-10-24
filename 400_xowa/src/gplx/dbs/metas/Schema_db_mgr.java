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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
import gplx.dbs.metas.updates.*;
public class Schema_db_mgr {
	public Schema_loader_mgr Loader() {return loader;} public void Loader_(Schema_loader_mgr v) {loader = v;} private Schema_loader_mgr loader;
	public Schema_update_mgr Updater() {return updater;} private final    Schema_update_mgr updater = new Schema_update_mgr();
	public Dbmeta_tbl_mgr Tbl_mgr() {return tbl_mgr;} private final    Dbmeta_tbl_mgr tbl_mgr = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
	public void Init(Db_conn conn) {
		loader.Load(this, conn);
		updater.Update(this, conn);
	}
}
