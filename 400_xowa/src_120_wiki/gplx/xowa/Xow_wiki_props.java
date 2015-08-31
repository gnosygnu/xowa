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
package gplx.xowa; import gplx.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.html.hrefs.*;
public class Xow_wiki_props implements GfoInvkAble {
	public byte[] Main_page() {return main_page;} private byte[] main_page = Xoa_page_.Main_page_bry;	// HACK: default to Main_Page b/c some code tries to do Xoa_ttl.parse() which will not work with ""; DATE:2014-02-16
	public Xow_wiki_props Main_page_(byte[] v) {main_page = v; return this;}
	public void Main_page_update(Xowe_wiki wiki) {
		siteinfo_mainpage = main_page;										// note that main_page came from <siteinfo>; store old value for record's sake
		main_page = Xow_mainpage_finder.Find_or(wiki, siteinfo_mainpage);	// get new main_page from mainpage_finder
	}
	public byte Protocol_tid() {return protocol_tid;} private final byte protocol_tid = gplx.core.net.Gfo_protocol_itm.Tid_https;	// NOTE: default protocol to https; handles external links like [//a.org]; may need to be changed for wikia or other non-WMF wikis; DATE:2015-07-27

	public byte[] Site_name() {return site_name;} private byte[] site_name = Bry_.Empty;
	public byte[] ServerName() {return serverName;} public Xow_wiki_props ServerName_(byte[] v) {serverName = v; server = Bry_.Add(bry_http, v); return this;} private byte[] serverName = Bry_.new_a7("localhost");
	public byte[] Server() {return server;} private byte[] server = Bry_.new_a7("http://localhost"); static final byte[] bry_http = Bry_.new_a7("http://");
	public byte[] ArticlePath() {return articlePath;} public Xow_wiki_props ArticlePath_(byte[] v) {articlePath = v; return this;} private byte[] articlePath = Xoh_href_.Bry__wiki;
	public byte[] ScriptPath() {return scriptPath;} public Xow_wiki_props ScriptPath_(byte[] v) {scriptPath = v; return this;} private byte[] scriptPath = Bry_.new_a7("/wiki");
	public byte[] StylePath() {return stylePath;} public Xow_wiki_props StylePath_(byte[] v) {stylePath = v; return this;} private byte[] stylePath = Bry_.new_a7("/wiki/skins");
	public byte[] ContentLanguage() {return contentLanguage;} public Xow_wiki_props ContentLanguage_(byte[] v) {contentLanguage = v; return this;} private byte[] contentLanguage = Bry_.Empty;
	public byte[] DirectionMark() {return directionMark;} public Xow_wiki_props DirectionMark_(byte[] v) {directionMark = v; return this;} private byte[] directionMark = Bry_.Empty;
	public byte[] Current_version() {return Current_version_const;}
	public byte[] Bldr_version() {return bldr_version;} public Xow_wiki_props Bldr_version_(byte[] v) {bldr_version = v; return this;} private byte[] bldr_version = Bry_.Empty;
	public int Css_version() {return css_version;} public Xow_wiki_props Css_version_(int v) {css_version = v; return this;} private int css_version = 1;
	public byte[] Siteinfo_misc() {return siteinfo_misc;}
	public byte[] Siteinfo_mainpage() {return siteinfo_mainpage;} private byte[] siteinfo_mainpage = Bry_.Empty;
	public DateAdp Modified_latest() {return modified_latest;} private DateAdp modified_latest;
	public Xow_wiki_props SiteName_(int v) {site_name = Bry_.new_a7(String_.UpperFirst(String_.new_a7(Xow_domain_type_.Get_type_as_bry(v)))); return this;} 
	public Xow_wiki_props Siteinfo_misc_(byte[] v) {
		siteinfo_misc = v;
		int pipe_0 = Bry_finder.Find_fwd(v, Byte_ascii.Pipe);
		if (pipe_0 != Bry_.NotFound)
			site_name = Bry_.Mid(siteinfo_misc, 0, pipe_0);
		return this;
	}	private byte[] siteinfo_misc = Bry_.Empty;
	public void Init_by_load(Xoa_app app, gplx.dbs.cfgs.Db_cfg_tbl cfg_tbl) {
		if (app.Bldr__running()) return;	// never load main_page during bldr; note that Init_by_load is called by bldr cmds like css; DATE:2015-07-24
		this.main_page = cfg_tbl.Select_bry_or(Xow_cfg_consts.Grp__wiki_init, Xow_cfg_consts.Key__init__main_page, null);
		if	(main_page == null) {			// main_page not found
			Xoa_app_.Usr_dlg().Warn_many("", "", "mw_props.load; main_page not found; conn=~{0}", cfg_tbl.Conn().Conn_info().Xto_api());
			this.main_page = Xoa_page_.Main_page_bry;
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_main_page_))						main_page = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_bldr_version_))					bldr_version = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_siteinfo_misc_))					Siteinfo_misc_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_siteinfo_mainpage_))				siteinfo_mainpage = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_css_version_))					css_version = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_modified_latest_))				modified_latest = m.ReadDate("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_main_page_ = "main_page_"
	, Invk_bldr_version = "bldr_version", Invk_bldr_version_ = "bldr_version_", Invk_siteinfo_misc_ = "siteinfo_misc_", Invk_siteinfo_mainpage_ = "siteinfo_mainpage_"
	, Invk_css_version_ = "css_version_"
	, Invk_modified_latest_ = "modified_latest_"
	;
	private static final byte[] Current_version_const = Bry_.new_a7("1.21wmf11");	// approximate level of compatibility
}
