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
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.infos.*;
public class Xowd_db_file {
	Xowd_db_file(Db_cfg_tbl cfg_tbl, Xob_info_session info_session, Xob_info_file info_file, Xowd_core_db_props props, Xowd_db_file_schema_props schema_props, int id, byte tid, Io_url url, String ns_ids, int part_id, Guid_adp guid, Db_conn conn, byte cmd_mode) {
		this.id = id; this.tid = tid; this.url = url; this.ns_ids = ns_ids; this.part_id = part_id; this.guid = guid;
		this.conn = conn; this.cmd_mode = cmd_mode;
		this.url_rel = url.NameAndExt();			
		boolean schema_is_1 = props.Schema_is_1();
		this.tbl__cfg = cfg_tbl;
		this.tbl__db = new Xowd_xowa_db_tbl(conn, schema_is_1);
		this.tbl__ns = new Xowd_site_ns_tbl(conn, schema_is_1);
		this.tbl__site_stats = new Xowd_site_stats_tbl(conn, schema_is_1);
		this.tbl__page = new Xowd_page_tbl(conn, schema_is_1);
		this.tbl__text = new Xowd_text_tbl(conn, schema_is_1, props.Zip_tid_text());
		this.tbl__html = new Xowd_html_tbl(conn, props.Zip_tid_html());
		this.tbl__css_core = new Xowd_css_core_tbl(conn);
		this.tbl__css_file = new Xowd_css_file_tbl(conn);
		this.tbl__cat_core = new Xowd_cat_core_tbl(conn, schema_is_1);
		this.tbl__cat_link = new Xowd_cat_link_tbl(conn, schema_is_1);
		this.tbl__wbase_qid = new Xowd_wbase_qid_tbl(conn, schema_is_1, schema_props.Wbase__qid__src_ttl_has_spaces());
		this.tbl__wbase_pid = new Xowd_wbase_pid_tbl(conn, schema_is_1);
		this.tbl__search_word = new Xowd_search_word_tbl(conn, schema_is_1, schema_props.Search__word__page_count_exists());
		this.tbl__search_link = new Xowd_search_link_tbl(conn, schema_is_1);
		this.info_session = info_session;
		this.info_file = info_file;
	}
	public int						Id()				{return id;}				private final int id;		// unique id in xowa_db
	public byte						Tid()				{return tid;}				private final byte tid;
	public Io_url					Url()				{return url;}				private final Io_url url;
	public String					Url_rel()			{return url_rel;}			private final String url_rel;
	public String					Ns_ids()			{return ns_ids;}			private final String ns_ids;
	public int						Part_id()			{return part_id;}			private final int part_id;
	public Guid_adp					Guid()				{return guid;}				private final Guid_adp guid;
	public Db_conn					Conn()				{return conn;}				private final Db_conn conn;
	public byte						Cmd_mode()			{return cmd_mode;}			public Xowd_db_file Cmd_mode_(byte v) {cmd_mode = v; return this;}		private byte cmd_mode;
	public long						File_len()			{return file_len;}			public Xowd_db_file File_len_add(int v) {file_len += v; return this;}	private long file_len;
	public long						File_max()			{return file_max;}			public Xowd_db_file File_max_(long v) {file_max = v; return this;}		private long file_max;
	public Xob_info_session			Info_session()		{return info_session;}		private final Xob_info_session info_session;
	public Xob_info_file			Info_file()			{return info_file;}			private final Xob_info_file info_file;
	public Db_cfg_tbl				Tbl__cfg()			{return tbl__cfg;}			private final Db_cfg_tbl	tbl__cfg;
	public Xowd_xowa_db_tbl			Tbl__db()			{return tbl__db;}			private final Xowd_xowa_db_tbl tbl__db;
	public Xowd_site_ns_tbl			Tbl__ns()			{return tbl__ns;}			private final Xowd_site_ns_tbl tbl__ns;
	public Xowd_page_tbl			Tbl__page()			{return tbl__page;}			private final Xowd_page_tbl	tbl__page;
	public Xowd_text_tbl			Tbl__text()			{return tbl__text;}			private final Xowd_text_tbl tbl__text;
	public Xowd_html_tbl			Tbl__html()			{return tbl__html;}			private final Xowd_html_tbl tbl__html;
	public Xowd_css_core_tbl		Tbl__css_core()		{return tbl__css_core;}		private final Xowd_css_core_tbl tbl__css_core;
	public Xowd_css_file_tbl		Tbl__css_file()		{return tbl__css_file;}		private final Xowd_css_file_tbl tbl__css_file;
	public Xowd_cat_core_tbl		Tbl__cat_core()		{return tbl__cat_core;}		private final Xowd_cat_core_tbl tbl__cat_core;
	public Xowd_cat_link_tbl		Tbl__cat_link()		{return tbl__cat_link;}		private final Xowd_cat_link_tbl tbl__cat_link;
	public Xowd_search_word_tbl		Tbl__search_word()	{return tbl__search_word;}	private final Xowd_search_word_tbl tbl__search_word;
	public Xowd_search_link_tbl		Tbl__search_link()	{return tbl__search_link;}	private final Xowd_search_link_tbl tbl__search_link;
	public Xowd_site_stats_tbl		Tbl__site_stats()	{return tbl__site_stats;}	private final Xowd_site_stats_tbl tbl__site_stats;
	public Xowd_wbase_qid_tbl		Tbl__wbase_qid()	{return tbl__wbase_qid;}	private final Xowd_wbase_qid_tbl tbl__wbase_qid;
	public Xowd_wbase_pid_tbl		Tbl__wbase_pid()	{return tbl__wbase_pid;}	private final Xowd_wbase_pid_tbl tbl__wbase_pid;
	public void						Rls()				{conn.Rls_conn();}

	public static final Xowd_db_file Null = null;
	public static Xowd_db_file make_(Xob_info_session info_session, Xowd_core_db_props props, int id, byte tid, Io_url url, String ns_ids, int part_id, String core_file_name, Db_conn conn) {
		Guid_adp guid = Guid_adp_.new_();
		Xob_info_file info_file = new Xob_info_file(id, Xowd_db_file_.To_key(tid), ns_ids, part_id, guid, props.Schema(), core_file_name, url.NameAndExt());
		Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(conn, "xowa_cfg");
		Xowd_db_file rv = new Xowd_db_file(cfg_tbl, info_session, info_file, props, Xowd_db_file_schema_props.make_(), id, tid, url, ns_ids, part_id, guid, conn, Db_cmd_mode.Tid_create);
		cfg_tbl.Create_tbl();	// always create cfg in each db
		return rv;
	}
	public static Xowd_db_file load_(Xowd_core_db_props props, int id, byte tid, Io_url url, String ns_ids, int part_id, Guid_adp guid) {
		Db_conn conn = Db_conn_bldr.I.Get(url);
		if (conn == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "wiki.db:missing db; tid=~{0} url=~{1}", Xowd_db_file_.To_key(tid), url.Raw());
			conn = Db_conn_.Noop;
		}
		Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(conn, "xowa_cfg");	// NOTE: this loads the cfg tbl for the current db, not the core db
		Xob_info_session info_session = Xob_info_session.Load(cfg_tbl);
		Xob_info_file info_file = Xob_info_file.Load(cfg_tbl);
		return new Xowd_db_file(cfg_tbl, info_session, info_file, props, Xowd_db_file_schema_props.load_(cfg_tbl, tid, info_session.Version()), id, tid, url, ns_ids, part_id, guid, conn, Db_cmd_mode.Tid_ignore);
	}
}
