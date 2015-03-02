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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xowd_db_init_db_mgr {
	private final OrderedHash wkrs = OrderedHash_.new_();
	private void Add(Xowd_db_init_db_wkr wkr)		{wkrs.Add(wkr.Db_tid(), wkr);}
	public Xowd_db_init_db_wkr Get(byte tid)		{return (Xowd_db_init_db_wkr)wkrs.Fetch(tid);}
        public static final Xowd_db_init_db_mgr I = new Xowd_db_init_db_mgr();
	Xowd_db_init_db_mgr() {
		Add(gplx.xowa.html.hdumps.Xowd_db_init_wkr__html.I);
	}
}
