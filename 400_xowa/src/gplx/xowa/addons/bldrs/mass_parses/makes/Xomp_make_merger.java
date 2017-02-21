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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
interface Xomp_make_merger {
	void	Merger__init(Xowe_wiki wiki, Xomp_wkr_db[] src_dbs);
	int		Merger__load(Xomp_mgr_db src_mgr_db, Xomp_wkr_db src_wkr_db, int uid_bgn, int uid_end);
	void	Merger__save();
	void	Merger__term();
}
