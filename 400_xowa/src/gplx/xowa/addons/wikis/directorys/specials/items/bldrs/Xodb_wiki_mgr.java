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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*;
public class Xodb_wiki_mgr {
	public Xodb_wiki_mgr(String domain) {
		this.domain = domain;
	}
	public String Domain() {return domain;} private final    String domain;
	public Xodb_wiki_db Dbs__get_core() {return dbs__core;} private Xodb_wiki_db dbs__core;
	public void Dbs__add(Xodb_wiki_db file) {
		if (file.Tid() == Xodb_wiki_db_tid.Tid__core)
			dbs__core = file;
	}
}
