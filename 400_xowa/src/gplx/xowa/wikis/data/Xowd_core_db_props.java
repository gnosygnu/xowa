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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.dbs.metas.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.wikis.dbs.*;
public class Xowd_core_db_props {
	public Xowd_core_db_props(int schema, Xow_db_layout layout_text, Xow_db_layout layout_html, Xow_db_layout layout_file
		, byte zip_tid_text, byte zip_tid_html, boolean hzip_enabled, boolean hzip_mode_is_b256) {
		this.schema = schema;
		this.layout_text = layout_text; this.layout_html = layout_html; this.layout_file = layout_file;
		this.zip_tid_text = zip_tid_text; this.zip_tid_html = zip_tid_html;
		this.hzip_enabled = hzip_enabled; this.hzip_mode_is_b256 = hzip_mode_is_b256;
	}
	public int				Schema()				{return schema;}			private final    int schema;
	public boolean    		Schema_is_1()			{return schema == 1;}
	public Xow_db_layout	Layout_text()			{return layout_text;}		private final    Xow_db_layout layout_text;
	public Xow_db_layout	Layout_html()			{return layout_html;}		private final    Xow_db_layout layout_html;
	public Xow_db_layout	Layout_file()			{return layout_file;}		private final    Xow_db_layout layout_file;
	public byte				Zip_tid_text()			{return zip_tid_text;}		private final    byte zip_tid_text;
	public byte				Zip_tid_html()			{return zip_tid_html;}		private final    byte zip_tid_html;
	public boolean				Hzip_enabled()			{return hzip_enabled;}		private final    boolean hzip_enabled;
	public boolean				Hzip_mode_is_b256()		{return hzip_mode_is_b256;}	private final    boolean hzip_mode_is_b256;
	public void Cfg_save(Db_cfg_tbl tbl) {
		tbl.Conn().Txn_bgn("make__core__cfg__save");
		tbl.Insert_int		(Cfg_grp, Cfg_key__schema_version		, schema);
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_text			, layout_text.Key());
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_html			, layout_html.Key());
		tbl.Insert_str		(Cfg_grp, Cfg_key__layout_file			, layout_file.Key());
		tbl.Insert_byte		(Cfg_grp, Cfg_key__zip_tid_text			, zip_tid_text);
		tbl.Insert_byte		(Cfg_grp, Cfg_key__zip_tid_html			, zip_tid_html);
		tbl.Insert_yn		(Cfg_grp, Cfg_key__hzip_enabled			, hzip_enabled);
		tbl.Insert_yn		(Cfg_grp, Cfg_key__hzip_mode_is_b256	, hzip_mode_is_b256);
		tbl.Conn().Txn_end();
	}
	public static Xowd_core_db_props Cfg_load(Db_conn conn) {return Cfg_load(conn, gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn));}
	public static Xowd_core_db_props Cfg_load(Db_conn conn, Db_cfg_tbl cfg_tbl) {			
		return cfg_tbl.Select_int_or(Cfg_grp, Cfg_key__schema_version, 1) == 1
			? new Xowd_core_db_props
			( 1, Xow_db_layout.Itm_lot, Xow_db_layout.Itm_lot, Xow_db_layout.Itm_lot, cfg_tbl.Select_byte_or(Xowe_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_data_storage_format
			, Io_stream_tid_.Tid__gzip), Io_stream_tid_.Tid__gzip, Bool_.Y, Bool_.N)
			: Cfg_load(cfg_tbl);
	}
	private static Xowd_core_db_props Cfg_load(Db_cfg_tbl tbl) {
		Db_cfg_hash cfg_hash = tbl.Select_as_hash(Cfg_grp);
		return new Xowd_core_db_props
		( cfg_hash.Get_by(Cfg_key__schema_version).To_int()
		, Xow_db_layout.Get_by_name(cfg_hash.Get_by(Cfg_key__layout_text).To_str())
		, Xow_db_layout.Get_by_name(cfg_hash.Get_by(Cfg_key__layout_html).To_str())
		, Xow_db_layout.Get_by_name(cfg_hash.Get_by(Cfg_key__layout_file).To_str())
		, cfg_hash.Get_by(Cfg_key__zip_tid_text).To_byte()
		, cfg_hash.Get_by(Cfg_key__zip_tid_html).To_byte()
		, cfg_hash.Get_by(Cfg_key__hzip_enabled).To_yn_or(Bool_.N)
		, cfg_hash.Get_by(Cfg_key__hzip_mode_is_b256).To_yn_or(Bool_.N)
		);
	}
	private static final String Cfg_grp = gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__wiki__core
	, Cfg_key__schema_version		= "schema_version"
	, Cfg_key__layout_text			= "layout_text"
	, Cfg_key__layout_html			= "layout_html"
	, Cfg_key__layout_file			= "layout_file"
	, Cfg_key__zip_tid_text			= "zip_tid_text"
	, Cfg_key__zip_tid_html			= "zip_tid_html"
	, Cfg_key__hzip_enabled			= "hzip_enabled"
	, Cfg_key__hzip_mode_is_b256	= "hzip_mode_is_b256"
	;
	public static final    Xowd_core_db_props Test = new Xowd_core_db_props(2, Xow_db_layout.Itm_few, Xow_db_layout.Itm_few, Xow_db_layout.Itm_few, Io_stream_tid_.Tid__raw, Io_stream_tid_.Tid__raw, Bool_.Y, Bool_.Y);
}
