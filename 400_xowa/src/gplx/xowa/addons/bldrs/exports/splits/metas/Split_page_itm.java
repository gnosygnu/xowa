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
package gplx.xowa.addons.bldrs.exports.splits.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_page_itm {
	private final    Split_page_list[] lists_ary;
	public Split_page_itm(boolean fsdb, int page_id) {
		this.page_id = page_id;
		this.lists_ary = new Split_page_list[Split_page_list_type_.Tid_max];
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public Split_page_list Get_by_or_null(byte type) {return lists_ary[type];}
	public Split_page_list Get_by_or_make(byte type) {
		Split_page_list rv = lists_ary[type];
		if (rv == null) {
			rv = new Split_page_list(type);
			lists_ary[type] = rv;
		}
		return rv;
	}
}
