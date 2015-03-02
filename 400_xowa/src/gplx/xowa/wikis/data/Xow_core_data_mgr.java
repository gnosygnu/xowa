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
import gplx.dbs.cfgs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
import gplx.xowa.wikis.data.*;
public interface Xow_core_data_mgr {
	Xow_core_data_map		Map();
	boolean					Cfg__schema_is_1();
	int						Cfg__db_id();
	int						Dbs__len();
	Xowd_db_file			Dbs__get_at(int i);
	Xowd_db_file			Dbs__get_by_tid_nth_or_new(byte tid);
	Xowd_db_file			Dbs__get_db_core();
	Db_cfg_tbl				Tbl__cfg();
	Xowd_pg_regy_tbl		Tbl__pg();
}
