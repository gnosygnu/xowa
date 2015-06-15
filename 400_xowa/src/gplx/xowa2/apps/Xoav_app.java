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
package gplx.xowa2.apps; import gplx.*; import gplx.xowa2.*;
import gplx.ios.*;
import gplx.dbs.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.langs.cases.*; import gplx.intl.*; import gplx.xowa.users.data.*;
import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa2.apps.urls.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.bldrs.css.*; import gplx.xowa.html.wtrs.*;
import gplx.xowa.users.*;
import gplx.xowa.wikis.*;
import gplx.xowa.wmfs.*;
import gplx.xowa.urls.encoders.*;
public class Xoav_app implements Xoa_app {
	private Xoa_url_parser url_parser = new Xoa_url_parser();
	public Xoav_app(Gfo_usr_dlg usr_dlg, Xoa_app_type app_type, String plat_name, Io_url root_dir, Io_url file_dir, Io_url css_dir) {
		Xoa_app_.Usr_dlg_(usr_dlg); this.usr_dlg = usr_dlg; this.app_type = app_type;
		this.fsys_mgr = new Xoa_fsys_mgr(plat_name, root_dir, root_dir.GenSubDir("wiki"), file_dir, css_dir);
		this.file__cache_mgr = new Xof_cache_mgr(usr_dlg, null, null);
		this.file__img_mgr = new Xof_img_mgr();
		this.wiki_mgr = new Xoav_wiki_mgr(this, utl_case_mgr);
		this.utl_msg_log = Gfo_msg_log.Test();
		this.href_parser = new Xoh_href_parser(Xoa_app_.Utl__encoder_mgr().Href(), url_parser.Url_parser());
		this.html__lnki_bldr = new Xoh_lnki_bldr(this, href_parser);
		this.user = new Xouv_user("anonymous");
	}
	public Xou_user					User()						{return user;} private final Xouv_user user;
	public Xoa_app_type				App_type()					{return app_type;} private final Xoa_app_type app_type;
	public Xoa_fsys_mgr				Fsys_mgr()					{return fsys_mgr;} private final Xoa_fsys_mgr fsys_mgr;
	public Xoav_wiki_mgr			Wiki_mgr()					{return wiki_mgr;} private final Xoav_wiki_mgr wiki_mgr;
	public Xoa_wiki_mgr				Wiki_mgri()				{return wiki_mgr;}
	public Xof_cache_mgr			File__cache_mgr()			{return file__cache_mgr;} private final Xof_cache_mgr file__cache_mgr;
	public Xof_img_mgr				File__img_mgr()				{return file__img_mgr;} private final Xof_img_mgr file__img_mgr;
	public Io_download_fmt			File__download_fmt()		{return file__download_fmt;} private final Io_download_fmt file__download_fmt = new Io_download_fmt();
	public Xoh_href_parser			Html__href_parser()			{return href_parser;} private Xoh_href_parser href_parser;
	public Xoh_lnki_bldr			Html__lnki_bldr()			{return html__lnki_bldr;} private final Xoh_lnki_bldr html__lnki_bldr;
	public Xoa_css_extractor		Html__css_installer()		{return html__css_installer;} private final Xoa_css_extractor html__css_installer = new Xoa_css_extractor();
	public boolean						Xwiki_mgr__missing(byte[] domain)	{return wiki_mgr.Get_by_domain(domain) == null;}
	public Xowmf_mgr				Wmf_mgr()					{return wmf_mgr;} private final Xowmf_mgr wmf_mgr = new Xowmf_mgr();
	public Gfo_usr_dlg				Usr_dlg() {return usr_dlg;} public void Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; Xoa_app_.Usr_dlg_(usr_dlg);} private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Noop;
	public Bry_bfr_mkr				Utl__bfr_mkr()				{return Xoa_app_.Utl__bfr_mkr();}
	public Url_encoder_mgr			Utl__encoder_mgr()			{return Xoa_app_.Utl__encoder_mgr();}
	public Xoa_url_parser			Utl__url_parser()		{return utl_url_parser;} private final Xoa_url_parser utl_url_parser = new Xoa_url_parser();

	public Xop_amp_mgr Utl_amp_mgr() {return utl_amp_mgr;} private Xop_amp_mgr utl_amp_mgr = new Xop_amp_mgr();
	public Xol_case_mgr Utl_case_mgr() {return utl_case_mgr;} private Xol_case_mgr utl_case_mgr = Xol_case_mgr_.Utf8();
	public Url_encoder Utl_encoder_fsys() {return utl_encoder_fsys;} private Url_encoder utl_encoder_fsys = Url_encoder.new_fsys_lnx_();
	public Gfo_msg_log Utl_msg_log() {return utl_msg_log;} private Gfo_msg_log utl_msg_log;
	public Xoav_url_parser Utl_url_parser_xo() {return utl_url_parser_xo;} private Xoav_url_parser utl_url_parser_xo = new Xoav_url_parser();
	public Gfo_url_parser Utl_url_parser_gfo() {return utl_url_parser_gfo;} private final Gfo_url_parser utl_url_parser_gfo = new Gfo_url_parser();

	public void Init_by_app(Io_url user_db_url) {
		user.Init_db(this, wiki_mgr, user_db_url);
	}
}
