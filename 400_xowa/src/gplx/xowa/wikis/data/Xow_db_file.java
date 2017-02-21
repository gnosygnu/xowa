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
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.infos.*;
import gplx.xowa.wikis.data.site_stats.*;
import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
import gplx.xowa.addons.wikis.htmls.css.dbs.*;
import gplx.xowa.xtns.wbases.dbs.*;
public class Xow_db_file {
	protected Xow_db_file(Db_cfg_tbl cfg_tbl, Xowd_core_db_props props, Xob_info_session info_session, Xob_info_file info_file, Xow_db_file_schema_props schema_props, int id, byte tid, Io_url url, String ns_ids, int part_id, Guid_adp guid, Db_conn conn, byte cmd_mode) {
		this.id = id; this.tid = tid; this.url = url; this.ns_ids = ns_ids; this.part_id = part_id; this.guid = guid; this.db_props = props;
		this.conn = conn; this.cmd_mode = cmd_mode;
		this.url_rel = url.NameAndExt();			
		boolean schema_is_1 = props.Schema_is_1();
		this.tbl__cfg = cfg_tbl;
		this.tbl__db = new Xowd_xowa_db_tbl(conn, schema_is_1);
		this.tbl__ns = new Xowd_site_ns_tbl(conn, schema_is_1);
		this.tbl__site_stats = new Xowd_site_stats_tbl(conn, schema_is_1);
		this.tbl__page = new Xowd_page_tbl(conn, schema_is_1);
		this.tbl__text = new Xowd_text_tbl(conn, schema_is_1, props.Zip_tid_text());
		this.tbl__html = new Xowd_html_tbl(conn);
		this.tbl__css_core = new Xowd_css_core_tbl(conn);
		this.tbl__css_file = new Xowd_css_file_tbl(conn);
		this.tbl__cat_core = new Xowd_cat_core_tbl(conn, schema_is_1);
		this.tbl__cat_link = new Xowd_cat_link_tbl(conn, schema_is_1);
		this.tbl__wbase_qid = new Xowd_wbase_qid_tbl(conn, schema_is_1, schema_props == null ? Bool_.N : schema_props.Wbase__qid__src_ttl_has_spaces());
		this.tbl__wbase_pid = new Xowd_wbase_pid_tbl(conn, schema_is_1);
		this.tbl__wbase_prop = new Xowb_prop_tbl(conn);
		this.info_session = info_session;
		this.info_file = info_file;
		this.schema_props = schema_props;
	}
	public int							Id()				{return id;}				private final    int id;		// unique id in xowa_db
	public byte							Tid()				{return tid;}				private final    byte tid;
	public Db_conn						Conn()				{return conn;}				private final    Db_conn conn;
	public Io_url						Url()				{return url;}				private final    Io_url url;
	public String						Url_rel()			{return url_rel;}			private final    String url_rel;
	public Xowd_core_db_props			Db_props()			{return db_props;}			private final    Xowd_core_db_props db_props;
	public String						Ns_ids()			{return ns_ids;}			private final    String ns_ids;
	public int							Ns_id_or_fail()		{return Int_.parse(ns_ids);}
	public int							Part_id()			{return part_id;}			private final    int part_id;
	public Guid_adp						Guid()				{return guid;}				private final    Guid_adp guid;
	public byte							Cmd_mode()			{return cmd_mode;}			public Xow_db_file Cmd_mode_(byte v) {cmd_mode = v; return this;}		private byte cmd_mode;
	public long							File_len()			{return file_len;}			public Xow_db_file File_len_add(int v) {file_len += v; return this;}	private long file_len;
	public long							File_max()			{return file_max;}			public Xow_db_file File_max_(long v) {file_max = v; return this;}		private long file_max;
	public Db_cfg_tbl					Tbl__cfg()			{return tbl__cfg;}			private final    Db_cfg_tbl	tbl__cfg;
	public Xowd_xowa_db_tbl				Tbl__db()			{return tbl__db;}			private final    Xowd_xowa_db_tbl tbl__db;
	public Xowd_site_ns_tbl				Tbl__ns()			{return tbl__ns;}			private final    Xowd_site_ns_tbl tbl__ns;
	public Xowd_page_tbl				Tbl__page()			{return tbl__page;}			private Xowd_page_tbl tbl__page;
	public Xowd_text_tbl				Tbl__text()			{return tbl__text;}			private final    Xowd_text_tbl tbl__text;
	public Xowd_html_tbl				Tbl__html()			{return tbl__html;}			private final    Xowd_html_tbl tbl__html;
	public Xowd_css_core_tbl			Tbl__css_core()		{return tbl__css_core;}		private final    Xowd_css_core_tbl tbl__css_core;
	public Xowd_css_file_tbl			Tbl__css_file()		{return tbl__css_file;}		private final    Xowd_css_file_tbl tbl__css_file;
	public Xowd_cat_core_tbl			Tbl__cat_core()		{return tbl__cat_core;}		private final    Xowd_cat_core_tbl tbl__cat_core;
	public Xowd_cat_link_tbl			Tbl__cat_link()		{return tbl__cat_link;}		private final    Xowd_cat_link_tbl tbl__cat_link;
	public Xowd_site_stats_tbl			Tbl__site_stats()	{return tbl__site_stats;}	private final    Xowd_site_stats_tbl tbl__site_stats;
	public Xowd_wbase_qid_tbl			Tbl__wbase_qid()	{return tbl__wbase_qid;}	private final    Xowd_wbase_qid_tbl tbl__wbase_qid;
	public Xowd_wbase_pid_tbl			Tbl__wbase_pid()	{return tbl__wbase_pid;}	private final    Xowd_wbase_pid_tbl tbl__wbase_pid;
	public Xowb_prop_tbl				Tbl__wbase_prop()	{return tbl__wbase_prop;}	private final    Xowb_prop_tbl tbl__wbase_prop;
	public Xob_info_session				Info_session() {
		if (info_session == null)	// NOTE: null when load; !null when make
			info_session = Xob_info_session.Load(tbl__cfg);
		return info_session;
	}	private Xob_info_session info_session;
	public Xob_info_file				Info_file() {
		if (info_file == null)		// NOTE: null when load; !null when make
			info_file = Xob_info_file.Load(tbl__cfg);
		return info_file;
	}	private Xob_info_file info_file;
	public Xow_db_file_schema_props	Schema_props() {
		if (schema_props == null)
			schema_props = Xow_db_file_schema_props.load_(tbl__cfg, tid, this.Info_session().Version());	// NOTE: must call .Info_session
		return schema_props;
	}	private Xow_db_file_schema_props schema_props;

	public void	Rls() {conn.Rls_conn();}
	public Xowd_page_tbl Tbl__page__rebind() {
		this.tbl__page = new Xowd_page_tbl(tbl__page.Conn(), tbl__page.schema_is_1);
		return tbl__page;
	}

	public static final    Xow_db_file Null = null;
	public static Xow_db_file Make(Xob_info_session info_session, Xowd_core_db_props props, int id, byte tid, Io_url url, String ns_ids, int part_id, String core_file_name, Db_conn conn) {
		Guid_adp guid = Guid_adp_.New();
		Xob_info_file info_file = new Xob_info_file(id, Xow_db_file_.To_key(tid), ns_ids, part_id, guid, props.Schema(), core_file_name, url.NameAndExt());
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
		Xow_db_file rv = new Xow_db_file(cfg_tbl, props, info_session, info_file, Xow_db_file_schema_props.make_(), id, tid, url, ns_ids, part_id, guid, conn, Db_cmd_mode.Tid_create);
		cfg_tbl.Create_tbl();	// always create cfg in each db
		return rv;
	}
	public static Xow_db_file Load(Xowd_core_db_props props, int id, byte tid, Io_url url, String ns_ids, int part_id, Guid_adp guid) {
		Db_conn conn = Db_conn_bldr.Instance.Get(url);
		if (conn == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "wiki.db:missing db; tid=~{0} url=~{1}", Xow_db_file_.To_key(tid), url.Raw());
			conn = Db_conn_.Noop;
		}
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn); // NOTE: this loads the cfg tbl for the current db, not the core db
		return new Xow_db_file(cfg_tbl, props, null, null, null, id, tid, url, ns_ids, part_id, guid, conn, Db_cmd_mode.Tid_ignore);
	}
}
