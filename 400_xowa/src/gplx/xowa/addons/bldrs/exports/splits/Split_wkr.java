/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.bldrs.exports.splits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
public interface Split_wkr {
	void Split__init			(Split_ctx ctx, Xow_wiki wiki, Db_conn wkr_conn);
	void Split__pages_loaded	(Split_ctx ctx, int ns_id, int score_bgn, int score_end);
	void Split__trg__1st__new	(Split_ctx ctx, Db_conn trg_conn);
	void Split__trg__nth__new	(Split_ctx ctx, Db_conn trg_conn);
	void Split__trg__nth__rls	(Split_ctx ctx, Db_conn trg_conn);
	void Split__exec			(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Xowd_page_itm page, int page_id);
	void Split__term			(Split_ctx ctx);
}