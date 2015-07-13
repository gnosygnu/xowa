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
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.dbs.metas.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.dbs.*;
public class Xowd_core_db_props {
	public Xowd_core_db_props(int schema, Xowd_db_layout layout_text, Xowd_db_layout layout_html, Xowd_db_layout layout_file, byte zip_tid_text, byte zip_tid_html) {
		this.schema = schema;
		this.layout_text = layout_text; this.layout_html = layout_html; this.layout_file = layout_file;
		this.zip_tid_text = zip_tid_text; this.zip_tid_html = zip_tid_html;
	}
	public int				Schema()				{return schema;}			private final int schema;
	public boolean    		Schema_is_1()			{return schema == 1;}
	public Xowd_db_layout	Layout_text()			{return layout_text;}		private final Xowd_db_layout layout_text;
	public Xowd_db_layout	Layout_html()			{return layout_html;}		private final Xowd_db_layout layout_html;
	public Xowd_db_layout	Layout_file()			{return layout_file;}		private final Xowd_db_layout layout_file;
	public byte				Zip_tid_text()			{return zip_tid_text;}		private final byte zip_tid_text;
	public byte				Zip_tid_html()			{return zip_tid_html;}		private final byte zip_tid_html;
	public void Cfg_save(Db_cfg_tbl tbl) {
		tbl.Conn().Txn_bgn();
		tbl.Insert_int		(Cfg_grp, Cfg_key__schema_version		, schema);
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_text			, layout_text.Name());
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_html			, layout_html.Name());
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_file			, layout_file.Name());
		tbl.Insert_byte		(Cfg_grp, Cfg_key__zip_tid_text			, zip_tid_text);
		tbl.Insert_byte		(Cfg_grp, Cfg_key__zip_tid_html			, zip_tid_html);
		tbl.Conn().Txn_end();
	}
	public static Xowd_core_db_props Cfg_load(Io_url url, Db_conn conn) {
		Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(conn, "xowa_cfg");
		return cfg_tbl.Select_int_or(Cfg_grp, Cfg_key__schema_version, 1) == 1
			? new Xowd_core_db_props(1, Xowd_db_layout.Itm_lot, Xowd_db_layout.Itm_lot, Xowd_db_layout.Itm_lot, cfg_tbl.Select_byte_or(Xowe_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_data_storage_format, Io_stream_.Tid_gzip), Io_stream_.Tid_gzip)
			: Cfg_load(cfg_tbl);
	}
	private static Xowd_core_db_props Cfg_load(Db_cfg_tbl tbl) {
		Db_cfg_hash cfg_hash = tbl.Select_as_hash(Cfg_grp);
		return new Xowd_core_db_props
		( cfg_hash.Get(Cfg_key__schema_version).To_int()
		, Xowd_db_layout.get_(cfg_hash.Get(Cfg_key__layout_text).To_str())
		, Xowd_db_layout.get_(cfg_hash.Get(Cfg_key__layout_html).To_str())
		, Xowd_db_layout.get_(cfg_hash.Get(Cfg_key__layout_file).To_str())
		, cfg_hash.Get(Cfg_key__zip_tid_text).To_byte()
		, cfg_hash.Get(Cfg_key__zip_tid_html).To_byte()
		);
	}
	private static final String Cfg_grp = Xow_cfg_consts.Grp__wiki_core
	, Cfg_key__schema_version		= "schema_version"
	, Cfg_key__layout_text			= "layout_text"
	, Cfg_key__layout_html			= "layout_html"
	, Cfg_key__layout_file			= "layout_file"
	, Cfg_key__zip_tid_text			= "zip_tid_text"
	, Cfg_key__zip_tid_html			= "zip_tid_html"
	;
	public static final Xowd_core_db_props Test = new Xowd_core_db_props(2, Xowd_db_layout.Itm_few, Xowd_db_layout.Itm_few, Xowd_db_layout.Itm_few, Io_stream_.Tid_raw, Io_stream_.Tid_raw);
}
