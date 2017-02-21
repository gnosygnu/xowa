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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.htmls.hrefs.*;
public class Xow_wiki_props {
	// MW props for Pfunc_wiki_props and Scrib_lib; EX: {{SITENAME}}
	public byte[]            Site_name()          {return site_name;}          private byte[] site_name = Bry_.Empty;
	public byte[]            Server_name()        {return server_name;}        private byte[] server_name = Bry_.new_a7("localhost");
	public byte[]            Server()             {return server;}             private byte[] server = Bry_.new_a7("http://localhost");
	public byte[]            Article_path()       {return article_path;}       private byte[] article_path = Xoh_href_.Bry__wiki;
	public byte[]            Script_path()        {return script_path;}        private byte[] script_path = Bry_.new_a7("/wiki");
	public byte[]            Style_path()         {return style_path;}         private byte[] style_path = Bry_.new_a7("/wiki/skins");
	public byte[]            Content_language()   {return content_language;}   private byte[] content_language = Bry_.Empty;
	public byte[]            Direction_mark()     {return direction_mark;}     private byte[] direction_mark = Bry_.Empty;
	public byte[]            Current_version()    {return CURRENT_VERSION;}    private static final    byte[] CURRENT_VERSION = Bry_.new_a7("1.21wmf11"); // approximate level of compatibility

	// XO props
	public byte              Protocol_tid()       {return protocol_tid;}       private final    byte protocol_tid = gplx.core.net.Gfo_protocol_itm.Tid_https;	// NOTE: default protocol to https; handles external links like [//a.org]; may need to be changed for wikia or other non-WMF wikis; DATE:2015-07-27
	public byte[]            Main_page()          {return main_page;}          private byte[] main_page = Xoa_page_.Main_page_bry; // HACK: default to Main_Page b/c some code tries to do Xoa_ttl.Parse() which will not work with ""; DATE:2014-02-16
	public byte[]            Bldr_version()       {return bldr_version;}       private byte[] bldr_version = Bry_.Empty;
	public int               Css_version()        {return css_version;}        private int css_version = 1;
	public byte[]            Siteinfo_misc()      {return siteinfo_misc;}      private byte[] siteinfo_misc = Bry_.Empty;
	public byte[]            Siteinfo_mainpage()  {return siteinfo_mainpage;}  private byte[] siteinfo_mainpage = Bry_.Empty;
	public DateAdp           Modified_latest()    {return modified_latest;}    private DateAdp modified_latest;
	public String            Modified_latest__yyyy_MM_dd() {return modified_latest == null ? "" : modified_latest.XtoStr_fmt_yyyy_MM_dd();}

	// setters
	public void ContentLanguage_  (byte[] v) {content_language = v;} 
	public void Bldr_version_     (byte[] v) {bldr_version = v;}
	public void Main_page_        (byte[] v) {main_page = v;}
	public void Siteinfo_mainpage_(byte[] v) {siteinfo_mainpage = v;}
	public void Siteinfo_misc_(byte[] v) {
		if (v == null) return; // exit else will fail for personal wikis which don't have a siteinfo_misc
		this.siteinfo_misc = v;
		int pipe_0 = Bry_find_.Find_fwd(v, Byte_ascii.Pipe);
		if (pipe_0 != Bry_find_.Not_found)
			this.site_name = Bry_.Mid(siteinfo_misc, 0, pipe_0);
	}

	// Init_by_ctor initializes by domain_name, not by db
	public void Init_by_ctor(int domain_tid, byte[] domain_bry) {
		// initialize site_name to something based on domain_tid; EX: "Wikipedia"
		// note that "home" becomes "Home"; will be changed back to "home" in Init_by_load_2
		this.site_name = Bry_.new_a7(String_.UpperFirst(String_.new_a7(Xow_domain_tid_.Get_type_as_bry(domain_tid))));

		// server_name is domain; EX: "en.wikipedia.org"
		this.server_name = domain_bry;						

		// server_name is https: + domain EX: "https://en.wikipedia.org"
		this.server = Bry_.Add(gplx.core.net.Gfo_protocol_itm.Itm_https.Text_bry(), domain_bry);
	}
	// Init_by_load initializes by db; called after Init_by_ctor but before Init_by_load_2; should be replaced by Init_by_load_2, but leaving as separate proc b/c of "if (app.Bldr__running())"; DATE:2017-02-17
	public void Init_by_load(Xoa_app app, gplx.dbs.cfgs.Db_cfg_tbl cfg_tbl) {
		if (app.Bldr__running()) return;	// never load main_page during bldr; note that Init_by_load is called by bldr cmds like css; DATE:2015-07-24

		// load from xowa_cfg
		this.main_page       = cfg_tbl.Select_bry_or (Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__main_page, null);
		if	(main_page == null) {			// main_page not found
			Xoa_app_.Usr_dlg().Warn_many("", "", "mw_props.load; main_page not found; conn=~{0}", cfg_tbl.Conn().Conn_info().Db_api());
			this.main_page = Xoa_page_.Main_page_bry;
		}

		this.modified_latest = cfg_tbl.Select_date_or(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__modified_latest, null);
	}
	// Init_by_load_2 is called by Xodb_load_mgr_sql; note that it might be called during bldr; DATE:2017-02-17
	public void Init_by_load_2(byte[] main_page, byte[] bldr_version, byte[] siteinfo_misc, byte[] siteinfo_mainpage, DateAdp modified_latest) {
		this.main_page = main_page;
		if	(main_page == null) {			// main_page not found
			Xoa_app_.Usr_dlg().Warn_many("", "", "mw_props.load; main_page not found2");
			this.main_page = Xoa_page_.Main_page_bry;
		}

		this.bldr_version = bldr_version;
		this.siteinfo_mainpage = siteinfo_mainpage;
		this.modified_latest = modified_latest;

		// note that Siteinfo_misc_ must be called b/c site_name should come from xowa_cfg / dump.xml's siteinfo_misc, not Init_by_ctor's Get_type_as_bry
		// doing "this.siteinfo_misc = siteinfo_misc" will cause "home" to be "Home" and diag will fail; DATE:2017-02-21
		this.Siteinfo_misc_(siteinfo_misc);
	}
}
