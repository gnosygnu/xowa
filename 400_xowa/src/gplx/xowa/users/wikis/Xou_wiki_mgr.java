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
package gplx.xowa.users.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
class Xow_wiki_loader {
	public void Load(String mount) {
		/*
		load mount
		Xow_mount_links links = user.Load_links(mount_id);
		load data
		for (int i = 0; i < links_len; i++) {
			switch (link_tid) {
				case data:
				
					wiki.Db_mgr().Root_url() = Get_data(rel_id);
					break;
			}
		}
		*/
	}
}
class Xou_wiki_itm_source {
	public static final int Tid_user = 1, Tid_wmf = 2, Tid_wikia = 3;
}
class Xou_wiki_itm_path_layout {
	public static final int Tid_multiple = 1, Tid_root = 2;
}
class Xou_wiki_tbl {
	public static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE wiki_regy"
	, "( wiki_id               integer       NOT NULL        PRIMARY KEY"	// user_generated;
	, ", wiki_key              varchar(255)"								// unique; akin to domain; en.wikipedia.org
	, ", wiki_source           integer"										// 0=user; 1=wmf; 2=wikia;
	, ", wiki_ctg_type         integer"										//
	, ", wiki_search_type      integer"										//
//		, ", wiki_data_date        varchar(8)"									// 20140502
//		, ", wiki_data_root        varchar(255)"								// 
//		, ", wiki_file_date        varchar(8)"									// 20140502
//		, ", wiki_file_root        varchar(255)"								// 
//		, ", wiki_html_date        varchar(8)"									// 20140502
//		, ", wiki_html_root        varchar(255)"								// 
	, ", wiki_misc             varchar(255)"								// 20140502
	, ");"
	);
	public static final String Tbl_name = "user_wiki"
	, Fld_dir_id = "dir_id", Fld_dir_name = "dir_name"
	;
//		private static final Db_idx_itm
//			Idx_name     		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS cache_dir__name ON cache_dir (dir_name);")
//		;
}
/*
( wf_id             integer             NOT NULL        PRIMARY KEY -- 1,2,3, etc..; user-specified (not canonical)
, wf_key			varchar(255)		NOT NULL					-- system key; EX: "enwiki.css.20140601" UNIQUE; SYSTEM-GENERATED; CUSTOMIZABLE
, wf_name			varchar(255)		NOT NULL					-- ui name; EX: "English Wikipedia CSS as of 2014-06-01"; NOT UNIQUE; SYSTEM-GENERATED; CUSTOMIZABLE
, wf_data_domain	varchar(255)        NOT NULL                    -- domain; EX:en.wikipedia.org
, wf_data_date		varchar(255)        NOT NULL                    -- dump date; 20140502
, wf_data_source	varchar(255)        NOT NULL                    -- WMF;archive.org;XOWA;
, wf_url            varchar(255)        NOT NULL					-- url location; EX: "xowa-fs://~{xowa}/wiki/en.wikipedia.org/en.wikipedia.org.xowa"
, wf_sort			integer				NOT NULL					-- sort order; EX: "1", "2", "3", etc.. UNIQUE; SYSTEM-GENERATED; CUSTOMIZABLE
, wf_deleted        integer             NOT NULL					-- deleted flag

/xowa
/bin
/data
	/en.wikipedia.org
		/ file
			fasb.mount
			/ fsdb.main
				
			/ fsdb.user
		/ html
			xowa_common.css
			xowa_wiki.css
			/ imgs
		/ wiki
			en.wikipedia.org.xowa
			en.wikipedia.org.000.xowa
/user
*/
class Xou_wiki_part {
	public int Id() {return id;} public void Id_(int v) {id = v;} private int id;
	public boolean Deleted() {return deleted;} public void Deleted_(boolean v) {deleted = v;} private boolean deleted;
	public Xofs_url_itm Url() {return url;} private Xofs_url_itm url = new Xofs_url_itm();
	public String Domain() {return domain;} public void Domain_(String v) {domain = v;} private String domain;
	public String Version() {return version;} public void Version_(String v) {version = v;} private String version;
	public String Source() {return source;} public void Source_(String v) {source = v;} private String source;
	public DateAdp Make_date() {return make_date;} public void Make_date_(DateAdp v) {make_date = v;} private DateAdp make_date;
	public String Misc() {return misc;} public void Misc_(String v) {misc = v;} private String misc;
}
