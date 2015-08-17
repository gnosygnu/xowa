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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.ios.*; import gplx.xowa.html.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.files.downloads.*;
import gplx.core.net.*;
public class Xoa_css_extractor {	
	public IoEngine_xrg_downloadFil Download_xrg() {return download_xrg;} private IoEngine_xrg_downloadFil download_xrg = Io_mgr.I.DownloadFil_args("", Io_url_.Empty);	
	public Xoa_css_extractor Wiki_domain_(byte[] v) {wiki_domain = v; return this;} private byte[] wiki_domain; 
	public Xoa_css_extractor Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} private Gfo_usr_dlg usr_dlg;
	public Xoa_css_extractor Failover_dir_(Io_url v) {failover_dir = v; return this;} private Io_url failover_dir;
	public Xoa_css_extractor Wiki_html_dir_(Io_url v) {wiki_html_dir = v; return this;} private Io_url wiki_html_dir;
	public Xoa_css_extractor Mainpage_url_(String v) {mainpage_url = v; return this;} private String mainpage_url;
	public Xoa_css_extractor Protocol_prefix_(String v) {protocol_prefix = v; return this;} private String protocol_prefix = "https:";// NOTE: changed from http to https; DATE:2015-02-17
	public Xoa_css_extractor Page_fetcher_(Xow_page_fetcher v) {page_fetcher = v; return this;} private Xow_page_fetcher page_fetcher;
	public Xoa_css_extractor Css_img_downloader_(Xoa_css_img_downloader v) {this.css_img_downloader = v; return this;} private Xoa_css_img_downloader css_img_downloader;
	public Xoa_css_extractor Opt_download_css_common_(boolean v) {opt_download_css_common = v; return this;} private boolean opt_download_css_common;
	public Xoa_css_extractor Url_encoder_(Url_encoder v) {url_encoder = v; return this;} private Url_encoder url_encoder;
	public Xoa_css_extractor Wiki_code_(byte[] v) {this.wiki_code = v; return this;} private byte[] wiki_code = null;
	private byte[] mainpage_html; private boolean lang_is_ltr = true;
	private final Gfo_url_parser url_parser = new Gfo_url_parser();
	public void Init_by_app(Xoae_app app) {
		this.usr_dlg = app.Usr_dlg();
		Xof_download_wkr download_wkr = app.Wmf_mgr().Download_wkr();
		this.download_xrg = download_wkr.Download_xrg();
		css_img_downloader = new Xoa_css_img_downloader().Ctor(usr_dlg, download_wkr, Bry_.new_u8(protocol_prefix));
		failover_dir = app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("html", "css", "failover");
		url_encoder = Xoa_app_.Utl__encoder_mgr().Http_url();
	}
	public void Install(Xow_wiki wiki, String css_key) {
		try {
			this.wiki_html_dir = wiki.App().Fsys_mgr().Wiki_css_dir(wiki.Domain_str());	// EX: /xowa/user/anonymous/wiki/en.wikipedia.org
			Io_url css_comm_fil = wiki_html_dir.GenSubFil(Css_common_name);
			Io_url css_wiki_fil = wiki_html_dir.GenSubFil(Css_wiki_name);
			wiki.Html__page_wtr_mgr().Init_css_urls(css_comm_fil, css_wiki_fil);
			if (wiki.Domain_tid() == Xow_domain_type_.Tid_home || Env_.Mode_testing()) return;		// NOTE: do not download if home_wiki; also needed for TEST
			if (Io_mgr.I.ExistsFil(css_wiki_fil)) return;											// css file exists; nothing to generate
			if (wiki.Html__css_installing()) return;
			wiki.Html__css_installing_(true);
			wiki.App().Usr_dlg().Log_many("", "", "generating css for '~{0}'", wiki.Domain_str());
			if (css_key != null) {
				if (Install_by_db(wiki, wiki_html_dir, css_key)) return;
			}
			if (wiki.Type_is_edit())
				this.Install_by_wmf((Xowe_wiki)wiki, wiki_html_dir);
			wiki.Html__css_installing_(false);
		}
		catch (Exception e) {	// if error, failover; paranoia catch for outliers like bad network connectivity fail, or MediaWiki: message not existing; DATE:2013-11-21
			wiki.App().Usr_dlg().Warn_many("", "", "failed to get css; failing over; wiki='~{0}' err=~{1}", wiki.Domain_str(), Err_.Message_gplx_full(e));
			Css_common_failover();		// only failover xowa_common.css; xowa_wiki.css comes from MediaWiki:Common.css / Vector.css
			wiki.Html__css_installing_(false);
		}
	}
	private void Install_by_wmf(Xowe_wiki wiki, Io_url wiki_html_dir) {
		opt_download_css_common = wiki.Appe().Setup_mgr().Dump_mgr().Css_commons_download();
		if (!wiki.Appe().Usere().Cfg_mgr().Security_mgr().Web_access_enabled()) opt_download_css_common = false;	// if !web_access_enabled, don't download
		this.wiki_domain = wiki.Domain_bry();
		mainpage_url = "https://" + wiki.Domain_str();	// NOTE: cannot reuse protocol_prefix b/c "//" needs to be added manually; protocol_prefix is used for logo and images which have form of "//domain/image.png"; changed to https; DATE:2015-02-17
		if (page_fetcher == null) page_fetcher = new Xow_page_fetcher_wiki();
		page_fetcher.Wiki_(wiki);
		this.wiki_html_dir = wiki_html_dir;
		this.lang_is_ltr = wiki.Lang().Dir_ltr();
		this.wiki_code = wiki.Domain_abrv();
		mainpage_html = Mainpage_download_html();
		Css_common_setup();
		Css_wiki_setup();
		Logo_setup();
	}
	private boolean Install_by_db(Xow_wiki wiki, Io_url wiki_html_dir, String css_key) {
		Xowd_db_mgr core_db_mgr = wiki.Data__core_mgr();
		if (	core_db_mgr == null
			||	core_db_mgr.Props() == null
			||	core_db_mgr.Props().Schema_is_1()
			||	!core_db_mgr.Tbl__cfg().Select_yn_or(Xow_cfg_consts.Grp__wiki_schema, Xowd_db_file_schema_props.Key__tbl_css_core, Bool_.N)
			) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "css.db not found; wiki=~{0} css_dir=~{1}", wiki.Domain_str(), wiki_html_dir.Raw());
			return false;
		}
		Xowd_db_file core_db = core_db_mgr.Db__core();
		return gplx.xowa.html.css.Xowd_css_core_mgr.Get(core_db.Tbl__css_core(), core_db.Tbl__css_file(), wiki_html_dir, css_key);
	}
	public void Css_common_setup() {
		if (opt_download_css_common)
			Css_common_download();
		else
			Css_common_failover();
	}
	private void Css_common_failover() {
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		Io_mgr.I.CopyFil(Css_common_failover_url(), trg_fil, true);
		css_img_downloader.Chk(wiki_domain, trg_fil);
	}
	private void Css_common_download() {
		boolean css_stylesheet_common_missing = true;
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		css_stylesheet_common_missing = !Css_scrape_setup();
		if (css_stylesheet_common_missing)
			Io_mgr.I.CopyFil(Css_common_failover_url(), trg_fil, true);
		else 
			css_img_downloader.Chk(wiki_domain, trg_fil);
	}
	private Io_url Css_common_failover_url() {
		Io_url css_commons_url = failover_dir.GenSubDir("xowa_common_override").GenSubFil_ary("xowa_common_", String_.new_u8(wiki_code), ".css");
		if (Io_mgr.I.ExistsFil(css_commons_url)) return css_commons_url;	// specific css exists for wiki; use it; EX: xowa_common_wiki_mediawikiwiki.css
		return failover_dir.GenSubFil(lang_is_ltr ? Css_common_name_ltr : Css_common_name_rtl);
	}
	public void Css_wiki_setup() {
		boolean css_stylesheet_wiki_missing = true;
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_wiki_name);
		if (Io_mgr.I.ExistsFil(trg_fil)) return;	// don't download if already there
		css_stylesheet_wiki_missing = !Css_wiki_generate(trg_fil);
		if (css_stylesheet_wiki_missing)
			Failover(trg_fil);
		else 
			css_img_downloader.Chk(wiki_domain, trg_fil);
	}
	private boolean Css_wiki_generate(Io_url trg_fil) {
		Bry_bfr bfr = Bry_bfr.new_();
		Css_wiki_generate_section(bfr, Ttl_common_css);
		Css_wiki_generate_section(bfr, Ttl_vector_css);
		byte[] bry = bfr.Xto_bry_and_clear();
		bry = Bry_.Replace(bry, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab_ent, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab);
		Io_mgr.I.SaveFilBry(trg_fil, bry);
		return true;
	}	private static final byte[] Ttl_common_css = Bry_.new_a7("Common.css"), Ttl_vector_css = Bry_.new_a7("Vector.css");
	private boolean Css_wiki_generate_section(Bry_bfr bfr, byte[] ttl) {
		byte[] page = page_fetcher.Get_by(Xow_ns_.Id_mediawiki, ttl);
		if (page == null) return false;
		if (bfr.Len() != 0) bfr.Add_byte_nl().Add_byte_nl();	// add "\n\n" between sections; !=0 checks against first
		Css_wiki_section_hdr.Bld_bfr_many(bfr, ttl);			// add "/*XOWA:MediaWiki:Common.css*/\n"
		bfr.Add(page);											// add page
		return true;
	}	static final Bry_fmtr Css_wiki_section_hdr = Bry_fmtr.new_("/*XOWA:MediaWiki:~{ttl}*/\n", "ttl");
	public void Logo_setup() {
		boolean logo_missing = true;
		Io_url logo_url = wiki_html_dir.GenSubFil("logo.png");
		if (Io_mgr.I.ExistsFil(logo_url)) return;	// don't download if already there
		logo_missing = !Logo_download(logo_url);
		if (logo_missing)
			Failover(logo_url);
	}
	private boolean Logo_download(Io_url trg_fil) {
		String src_fil = Logo_find_src();
		if (src_fil == null) {
			if (Logo_copy_from_css(trg_fil)) return true;
			usr_dlg.Warn_many("", "", "failed to extract logo: trg_fil=~{0};", trg_fil.Raw());
			return false;
		}
		String log_msg = usr_dlg.Prog_many("", "", "downloading logo: '~{0}'", src_fil);
		boolean rv = download_xrg.Prog_fmt_hdr_(log_msg).Src_(src_fil).Trg_(trg_fil).Exec();
		if (!rv)
			usr_dlg.Warn_many("", "", "failed to download logo: src_url=~{0};", src_fil);
		return rv;
	}
	private boolean Logo_copy_from_css(Io_url trg_fil) {
		Io_url commons_file = wiki_html_dir.GenSubFil(Css_common_name);
		byte[] commons_src = Io_mgr.I.LoadFilBry(commons_file);
		int bgn_pos = Bry_finder.Find_fwd(commons_src, Bry_mw_wiki_logo);				if (bgn_pos == Bry_finder.Not_found) return false;
		bgn_pos += Bry_mw_wiki_logo.length;
		int end_pos = Bry_finder.Find_fwd(commons_src, Byte_ascii.Quote, bgn_pos + 1);	if (end_pos == Bry_finder.Not_found) return false;
		byte[] src_bry = Bry_.Mid(commons_src, bgn_pos, end_pos);
		src_bry = Xob_url_fixer.Fix(wiki_domain, src_bry, src_bry.length);
		if (wiki_html_dir.Info().DirSpr_byte() == Byte_ascii.Backslash)
			src_bry = Bry_.Replace(src_bry, Byte_ascii.Slash, Byte_ascii.Backslash);
		Io_url src_fil = wiki_html_dir.GenSubFil(String_.new_u8(src_bry));
		Io_mgr.I.CopyFil(src_fil, trg_fil, true);
		return true;
	}	private static final byte[] Bry_mw_wiki_logo = Bry_.new_a7(".mw-wiki-logo{background-image:url(\"");
	private String Logo_find_src() {
		if (mainpage_html == null) return null;
		int main_page_html_len = mainpage_html.length;
		int logo_bgn = Bry_finder.Find_fwd(mainpage_html, Logo_find_bgn, 0); 		if (logo_bgn == Bry_.NotFound) return null;
		logo_bgn += Logo_find_bgn.length;
		logo_bgn = Bry_finder.Find_fwd(mainpage_html, Logo_find_end, logo_bgn);		if (logo_bgn == Bry_.NotFound) return null;
		logo_bgn += Logo_find_end.length;
		int logo_end = Bry_finder.Find_fwd(mainpage_html, Byte_ascii.Paren_end, logo_bgn, main_page_html_len);	if (logo_bgn == Bry_.NotFound) return null;
		byte[] logo_bry = Bry_.Mid(mainpage_html, logo_bgn, logo_end);
		return protocol_prefix + String_.new_u8(logo_bry);
	}
	private static final byte[] Logo_find_bgn = Bry_.new_a7("<div id=\"p-logo\""), Logo_find_end = Bry_.new_a7("background-image: url(");
	public boolean Mainpage_download() {
		mainpage_html = Mainpage_download_html();
		return mainpage_html != null;
	}
	private byte[] Mainpage_download_html() {
		String main_page_url_temp = mainpage_url;
		if (Bry_.Eq(wiki_domain, Xow_domain_.Domain_bry_wikidata))	// if wikidata, download css for a Q* page; Main_Page has less css; DATE:2014-09-30
			main_page_url_temp = main_page_url_temp + "/wiki/Q2";
		String log_msg = usr_dlg.Prog_many("", "main_page.download", "downloading main page for '~{0}'", main_page_url_temp);
		byte[] main_page_html = download_xrg.Prog_fmt_hdr_(log_msg).Exec_as_bry(main_page_url_temp);
		if (main_page_html == null) usr_dlg.Warn_many("", "", "failed to download main_page: src_url=~{0};", main_page_url_temp);
		return main_page_html;
	}	
	private void Failover(Io_url trg_fil) {
		usr_dlg.Note_many("", "", "copying failover file: trg_fil=~{0};", trg_fil.Raw());
		Io_mgr.I.CopyFil(failover_dir.GenSubFil(trg_fil.NameAndExt()), trg_fil, true);		
	}
	public boolean Css_scrape_setup() {
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		// if (Io_mgr.I.ExistsFil(trg_fil)) return;	// don't download if already there; DELETED: else main_page is not scraped for all stylesheet links; simple.d: fails; DATE:2014-02-11
		byte[] css_url = Css_scrape();
		if (css_url == null) {
			Css_common_failover();
			return false;
		}
		else {
			Io_mgr.I.SaveFilBry(trg_fil, css_url);
			css_img_downloader.Chk(wiki_domain, trg_fil);
			return true;
		}
	}
	private byte[] Css_scrape() {
		if (mainpage_html == null) return null;
		String[] css_urls = Css_scrape_urls(mainpage_html); if (css_urls.length == 0) return null;
		return Css_scrape_download(css_urls);
	}
	private String[] Css_scrape_urls(byte[] raw) {
		List_adp rv = List_adp_.new_();
		int raw_len = raw.length;
		int prv_pos = 0; 
		int css_find_bgn_len = Css_find_bgn.length;
		byte[] protocol_prefix_bry = Bry_.new_u8(protocol_prefix);
		Gfo_url gfo_url = new Gfo_url();
		while (true) {
			int url_bgn = Bry_finder.Find_fwd(raw, Css_find_bgn, prv_pos);	 				if (url_bgn == Bry_.NotFound) break;	// nothing left; stop
			url_bgn += css_find_bgn_len;
			int url_end = Bry_finder.Find_fwd(raw, Byte_ascii.Quote, url_bgn, raw_len); 	if (url_end == Bry_.NotFound) {usr_dlg.Warn_many("", "main_page.css_parse", "could not find css; pos='~{0}' text='~{1}'", url_bgn, String_.new_u8_by_len(raw, url_bgn, url_bgn + 32)); break;}
			byte[] css_url_bry = Bry_.Mid(raw, url_bgn, url_end);
			css_url_bry = Bry_.Replace(css_url_bry, Css_amp_find, Css_amp_repl);		// &amp; -> &
			css_url_bry = url_encoder.Decode(css_url_bry);								// %2C ->		%7C -> |
			url_parser.Parse(gfo_url, css_url_bry, 0, css_url_bry.length);
			if (	gfo_url.Protocol_tid() == Gfo_protocol_itm.Tid_relative_1			// if rel url, add protocol_prefix DATE:2015-08-01
				||	(Env_.Mode_testing() && gfo_url.Protocol_tid() == Gfo_protocol_itm.Tid_unknown))	// TEST:
				css_url_bry = Bry_.Add(protocol_prefix_bry, css_url_bry);
			rv.Add(String_.new_u8(css_url_bry));
			prv_pos = url_end;
		}
		return rv.To_str_ary();
	}	private static final byte[] Css_find_bgn = Bry_.new_a7("<link rel=\"stylesheet\" href=\""), Css_amp_find = Bry_.new_a7("&amp;"), Css_amp_repl = Bry_.new_a7("&");
	private byte[] Css_scrape_download(String[] css_urls) {
		int css_urls_len = css_urls.length;
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		for (int i = 0; i < css_urls_len; i++) {
			String css_url = css_urls[i];
			usr_dlg.Prog_many("", "main_page.css_download", "downloading css for '~{0}'", css_url);
			download_xrg.Prog_fmt_hdr_(css_url);
			byte[] css_bry = download_xrg.Exec_as_bry(css_url); if (css_bry == null) continue;	// css not found; continue
			tmp_bfr.Add(Xoa_css_img_downloader.Bry_comment_bgn).Add_str(css_url).Add(Xoa_css_img_downloader.Bry_comment_end).Add_byte_nl();
			tmp_bfr.Add(css_bry).Add_byte_nl().Add_byte_nl();
		}
		return tmp_bfr.Xto_bry_and_clear();
	}
	public static final String Css_common_name = "xowa_common.css", Css_wiki_name = "xowa_wiki.css"
	, Css_common_name_ltr = "xowa_common_ltr.css", Css_common_name_rtl = "xowa_common_rtl.css";
}