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
package gplx.xowa.addons.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*;
import gplx.dbs.*;
public class Srch_link_tbl {
	public final    String tbl_name;
	public final    Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	public final    String fld_word_id, fld_page_id, fld_link_score;
	public final    boolean schema_is_1;
	public final    Db_conn conn;
	public Srch_link_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn; this.schema_is_1 = schema_is_1;
		String fld_prefix = "";
		if (schema_is_1)	{tbl_name = "search_title_page"; fld_prefix = "stp_";}
		else				{tbl_name = "search_link";}
		fld_word_id			= flds.Add_int(fld_prefix + "word_id");
		fld_page_id			= flds.Add_int(fld_prefix + "page_id");
		fld_link_score		= flds.Add_int_dflt(fld_prefix + Fld_link_score, 0);
	}
	public void Create_tbl()				{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__page_id()		{}	// TODO: conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "page_id", fld_page_id));}
	public void Create_idx__link_score()	{conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "word_id__link_score", fld_word_id, Fld_link_score));}

	public static final    Srch_link_tbl[] Ary_empty = new Srch_link_tbl[0];
	public static final String Fld_link_score = "link_score";
}
