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
public class Split_rslt_tid_ {
	public static final int Tid_max = 9;
	public static final int Tid__page = 0, Tid__html = 1, Tid__srch_word = 2, Tid__srch_link = 3, Tid__fsdb_bin = 4, Tid__fsdb_fil = 5, Tid__fsdb_thm = 6, Tid__fsdb_org = 7, Tid__rndm = 8;
	public static String To_str(int tid) {
		switch (tid) {
			case Tid__page:			return "page";
			default:				throw Err_.new_unhandled_default(tid);
		}
	}
}
