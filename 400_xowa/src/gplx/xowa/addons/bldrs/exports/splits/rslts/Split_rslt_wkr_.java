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