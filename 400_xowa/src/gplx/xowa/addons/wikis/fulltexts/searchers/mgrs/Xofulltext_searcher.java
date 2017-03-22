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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
public interface Xofulltext_searcher {
	boolean Type_is_lucene();
	void Search(Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_cache_qry qry, Xofulltext_args_qry qry_args, Xofulltext_args_wiki wiki_args);
}
