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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import gplx.ios.*;
public class Xob_categorylinks_sql extends Xob_categorylinks_base {
	private Db_idx_mode idx_mode = Db_idx_mode.Itm_end;
	public Xob_categorylinks_sql(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	@Override public String Cmd_key() {return KEY;} public static final String KEY = "import.sql.categorylinks";
	@Override public Io_sort_cmd Make_sort_cmd(Sql_file_parser sql_parser) {return new Xob_categorylinks_sql_make(sql_parser, wiki, idx_mode);}
	public static final String Tbl_categorylinks = String_.Concat_lines_nl
	( "CREATE TABLE `categorylinks` ("
	, "  `cl_from` int(10) unsigned NOT NULL DEFAULT '0',"
	, "  `cl_to` varbinary(255) NOT NULL DEFAULT '',"
	, "  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',"
	, "  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
	, "  `cl_sortkey_prefix` varbinary(255) NOT NULL DEFAULT '',"
	, "  `cl_collation` varbinary(32) NOT NULL DEFAULT '',"
	, "  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',"
	, "  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`),"
	, ");"
	);
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_idx_mode_))					idx_mode = Db_idx_mode.Xto_itm(m.ReadStr("v"));
		else													return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_idx_mode_ = "idx_mode_";
}
