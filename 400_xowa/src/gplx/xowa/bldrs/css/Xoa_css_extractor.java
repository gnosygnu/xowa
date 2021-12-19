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
package gplx.xowa.bldrs.css;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.core.envs.Env_;
import gplx.core.ios.IoEngine_xrg_downloadFil;
import gplx.core.net.Gfo_protocol_itm;
import gplx.core.net.Gfo_url;
import gplx.core.net.Gfo_url_parser;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xoa_app_;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.wikis.htmls.css.mgrs.Xowd_css_core_mgr;
import gplx.xowa.files.downloads.Xof_download_wkr;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.Xow_db_file_schema_props;
import gplx.xowa.wikis.data.Xow_db_mgr;
import gplx.xowa.wikis.data.Xowd_cfg_key_;
import gplx.xowa.wikis.data.fetchers.Xow_page_fetcher;
import gplx.xowa.wikis.data.fetchers.Xow_page_fetcher_wiki;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.nss.Xow_ns_;
public class Xoa_css_extractor {
	private Io_url home_css_dir;
	public IoEngine_xrg_downloadFil Download_xrg() {return download_xrg;} private IoEngine_xrg_downloadFil download_xrg = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty);	
	public Xoa_css_extractor Wiki_domain_(byte[] v) {wiki_domain = v; return this;} private byte[] wiki_domain; 
	public Xoa_css_extractor Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} private Gfo_usr_dlg usr_dlg;
	public Xoa_css_extractor Failover_dir_(Io_url v) {failover_dir = v; return this;} private Io_url failover_dir;
	public Xoa_css_extractor Wiki_html_dir_(Io_url v) {wiki_html_dir = v; return this;} private Io_url wiki_html_dir;
	public Xoa_css_extractor Mainpage_url_(String v) {mainpage_url = v; return this;} private String mainpage_url;
	public Xoa_css_extractor Protocol_prefix_(String v) {protocol_prefix = v; return this;} private String protocol_prefix = "https:";// NOTE: changed from http to https; DATE:2015-02-17
	public Xoa_css_extractor Page_fetcher_(Xow_page_fetcher v) {page_fetcher = v; return this;} private Xow_page_fetcher page_fetcher;
	public Xoa_css_extractor Css_img_downloader_(Xoa_css_img_downloader v) {this.css_img_downloader = v; return this;} private Xoa_css_img_downloader css_img_downloader;
	public Xoa_css_extractor Opt_download_css_common_(boolean v) {opt_download_css_common = v; return this;} private boolean opt_download_css_common;
	public Xoa_css_extractor Url_encoder_(Gfo_url_encoder v) {url_encoder = v; return this;} private Gfo_url_encoder url_encoder;
	public Xoa_css_extractor Wiki_code_(byte[] v) {this.wiki_code = v; return this;} private byte[] wiki_code = null;
	private byte[] mainpage_html; private boolean lang_is_ltr = true;
	private final Gfo_url_parser url_parser = new Gfo_url_parser();
	public void Init_by_app(Xoae_app app) {
		this.usr_dlg = app.Usr_dlg();
		this.home_css_dir = app.Usere().Fsys_mgr().Wiki_html_dir("home").GenSubDir("html");
		Xof_download_wkr download_wkr = app.Wmf_mgr().Download_wkr();
		this.download_xrg = download_wkr.Download_xrg();
		css_img_downloader = new Xoa_css_img_downloader().Ctor(usr_dlg, download_wkr, BryUtl.NewU8(protocol_prefix));
		failover_dir = app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("html", "css", "failover");
		url_encoder = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url;
	}
	public void Install(Xow_wiki wiki, String css_key) {
		try {
			this.wiki_html_dir = wiki.App().Fsys_mgr().Wiki_css_dir(wiki.Domain_str());	// EX: /xowa/user/anonymous/wiki/en.wikipedia.org
			Io_url css_comm_fil = wiki_html_dir.GenSubFil(Css_common_name);
			Io_url css_wiki_fil = wiki_html_dir.GenSubFil(Css_wiki_name);
			wiki.Html__wtr_mgr().Init_css_urls(wiki.App(), wiki.Domain_str(), css_comm_fil, css_wiki_fil);
			if (wiki.Domain_tid() == Xow_domain_tid_.Tid__home || Env_.Mode_testing()) return;		// NOTE: do not download if home_wiki; also needed for TEST
			if (Io_mgr.Instance.ExistsFil(css_wiki_fil)) return;											// css file exists; nothing to generate
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
			wiki.App().Usr_dlg().Warn_many("", "", "failed to get css; failing over; wiki='~{0}' err=~{1}", wiki.Domain_str(), ErrUtl.ToStrFull(e));
			Css_common_failover();		// only failover xowa_common.css; xowa_wiki.css comes from MediaWiki:Common.css / Vector.css
			wiki.Html__css_installing_(false);
		}
	}
	private void Install_by_wmf(Xowe_wiki wiki, Io_url wiki_html_dir) {
		opt_download_css_common = wiki.Appe().Cfg().Get_bool_app_or("xowa.bldr.import.download_xowa_common", true);	// CFG: Cfg__

		// do not download css if web_access disabled or wiki is other; DATE:2017-02-25
		boolean wiki_is_other = wiki.Domain_tid() == Xow_domain_tid_.Tid__other;
		if (	!gplx.core.ios.IoEngine_system.Web_access_enabled
			||  wiki_is_other)
			opt_download_css_common = false;	// if !web_access_enabled, don't download

		this.wiki_domain = wiki.Domain_bry();
		mainpage_url = "https://" + wiki.Domain_str();	// NOTE: cannot reuse protocol_prefix b/c "//" needs to be added manually; protocol_prefix is used for logo and images which have form of "//domain/image.png"; changed to https; DATE:2015-02-17
		if (page_fetcher == null) page_fetcher = new Xow_page_fetcher_wiki();
		page_fetcher.Wiki_(wiki);
		this.wiki_html_dir = wiki_html_dir;
		this.lang_is_ltr = wiki.Lang().Dir_ltr();
		this.wiki_code = wiki.Domain_abrv();

		// get mainpage; do not download css if wiki is other; DATE:2017-02-25
		mainpage_html = wiki_is_other ? BryUtl.Empty : Mainpage_download_html();

		// generate css
		Css_common_setup();
		Css_wiki_setup();
		Logo_setup();
	}
	private boolean Install_by_db(Xow_wiki wiki, Io_url wiki_html_dir, String css_key) {
		Xow_db_mgr core_db_mgr = wiki.Data__core_mgr();
		if (	core_db_mgr == null
			||	core_db_mgr.Props() == null
			||	core_db_mgr.Props().Schema_is_1()
			||	!core_db_mgr.Tbl__cfg().Select_yn_or(Xowd_cfg_key_.Grp__wiki_schema, Xow_db_file_schema_props.Key__tbl_css_core, BoolUtl.N)
			) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "css.db not found; wiki=~{0} css_dir=~{1}", wiki.Domain_str(), wiki_html_dir.Raw());
			return false;
		}
		Xow_db_file core_db = core_db_mgr.Db__core();
		return Xowd_css_core_mgr.Get(core_db.Tbl__css_core(), core_db.Tbl__css_file(), wiki_html_dir, css_key);
	}
	public void Css_common_setup() {
		if (opt_download_css_common)
			Css_common_download();
		else
			Css_common_failover();
	}
	private void Css_common_failover() {
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		if (home_css_dir != null)	// TEST:
			Io_mgr.Instance.CopyDirDeep(home_css_dir, trg_fil.OwnerDir()); // NOTE: copy dir first b/c xowa_commons.css will be replaced below
		Io_mgr.Instance.CopyFil(Css_common_failover_url(), trg_fil, true);
	}
	private void Css_common_download() {
		boolean css_stylesheet_common_missing = true;
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		css_stylesheet_common_missing = !Css_scrape_setup();
		if (css_stylesheet_common_missing)
			Io_mgr.Instance.CopyFil(Css_common_failover_url(), trg_fil, true);
		else 
			css_img_downloader.Chk(wiki_domain, trg_fil);
	}
	private Io_url Css_common_failover_url() {
		Io_url css_commons_url = failover_dir.GenSubDir("xowa_common_override").GenSubFil_ary("xowa_common_", StringUtl.NewU8(wiki_code), ".css");
		if (Io_mgr.Instance.ExistsFil(css_commons_url)) return css_commons_url;	// specific css exists for wiki; use it; EX: xowa_common_wiki_mediawikiwiki.css
		return failover_dir.GenSubFil(lang_is_ltr ? Css_common_name_ltr : Css_common_name_rtl);
	}
	public void Css_wiki_setup() {
		boolean css_stylesheet_wiki_missing = true;
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_wiki_name);
		if (Io_mgr.Instance.ExistsFil(trg_fil)) return;	// don't download if already there
		css_stylesheet_wiki_missing = !Css_wiki_generate(trg_fil);
		if (css_stylesheet_wiki_missing)
			Failover(trg_fil);
		else 
			css_img_downloader.Chk(wiki_domain, trg_fil);
	}
	private boolean Css_wiki_generate(Io_url trg_fil) {
		BryWtr bfr = BryWtr.New();
		Css_wiki_generate_section(bfr, Ttl_common_css);
		Css_wiki_generate_section(bfr, Ttl_vector_css);
		byte[] bry = bfr.ToBryAndClear();
		bry = BryUtlByWtr.Replace(bry, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab_ent, gplx.xowa.bldrs.xmls.Xob_xml_parser_.Bry_tab);
		Io_mgr.Instance.SaveFilBry(trg_fil, bry);
		return true;
	}	private static final byte[] Ttl_common_css = BryUtl.NewA7("Common.css"), Ttl_vector_css = BryUtl.NewA7("Vector.css");
	private boolean Css_wiki_generate_section(BryWtr bfr, byte[] ttl) {
		byte[] page = page_fetcher.Get_by(Xow_ns_.Tid__mediawiki, ttl);
		if (page == null) return false;
		if (bfr.Len() != 0) bfr.AddByteNl().AddByteNl();	// add "\n\n" between sections; !=0 checks against first
		Css_wiki_section_hdr.BldToBfrMany(bfr, ttl);			// add "/*XOWA:MediaWiki:Common.css*/\n"
		bfr.Add(page);											// add page
		return true;
	}	private static final BryFmtr Css_wiki_section_hdr = BryFmtr.New("/*XOWA:MediaWiki:~{ttl}*/\n", "ttl");
	public void Logo_setup() {
		boolean logo_missing = true;
		Io_url logo_url = wiki_html_dir.GenSubFil("logo.png");
		if (Io_mgr.Instance.ExistsFil(logo_url)) return;	// don't download if already there
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
		byte[] commons_src = Io_mgr.Instance.LoadFilBry(commons_file);
		int bgn_pos = BryFind.FindFwd(commons_src, Bry_mw_wiki_logo);				if (bgn_pos == BryFind.NotFound) return false;
		bgn_pos += Bry_mw_wiki_logo.length;
		int end_pos = BryFind.FindFwd(commons_src, AsciiByte.Quote, bgn_pos + 1);	if (end_pos == BryFind.NotFound) return false;
		byte[] src_bry = BryLni.Mid(commons_src, bgn_pos, end_pos);
		src_bry = Xob_url_fixer.Fix(wiki_domain, src_bry, src_bry.length);
		if (wiki_html_dir.Info().DirSpr_byte() == AsciiByte.Backslash)
			src_bry = BryUtl.Replace(src_bry, AsciiByte.Slash, AsciiByte.Backslash);
		Io_url src_fil = wiki_html_dir.GenSubFil(StringUtl.NewU8(src_bry));
		Io_mgr.Instance.CopyFil(src_fil, trg_fil, true);
		return true;
	}	private static final byte[] Bry_mw_wiki_logo = BryUtl.NewA7(".mw-wiki-logo{background-image:url(\"");
	private String Logo_find_src() {
		if (mainpage_html == null) return null;
		int main_page_html_len = mainpage_html.length;
		int logo_bgn = BryFind.FindFwd(mainpage_html, Logo_find_bgn, 0); 		if (logo_bgn == BryFind.NotFound) return null;
		logo_bgn += Logo_find_bgn.length;
		logo_bgn = BryFind.FindFwd(mainpage_html, Logo_find_end, logo_bgn);		if (logo_bgn == BryFind.NotFound) return null;
		logo_bgn += Logo_find_end.length;
		int logo_end = BryFind.FindFwd(mainpage_html, AsciiByte.ParenEnd, logo_bgn, main_page_html_len);	if (logo_bgn == BryFind.NotFound) return null;
		byte[] logo_bry = BryLni.Mid(mainpage_html, logo_bgn, logo_end);
		return protocol_prefix + StringUtl.NewU8(logo_bry);
	}
	private static final byte[] Logo_find_bgn = BryUtl.NewA7("<div id=\"p-logo\""), Logo_find_end = BryUtl.NewA7("background-image: url(");
	public boolean Mainpage_download() {
		mainpage_html = Mainpage_download_html();
		return mainpage_html != null;
	}
	private byte[] Mainpage_download_html() {
		String main_page_url_temp = mainpage_url;
		if (BryLni.Eq(wiki_domain, Xow_domain_itm_.Bry__wikidata))	// if wikidata, download css for a Q* page; Main_Page has less css; DATE:2014-09-30
			main_page_url_temp = main_page_url_temp + "/wiki/Q2";
		String log_msg = usr_dlg.Prog_many("", "main_page.download", "downloading main page for '~{0}'", main_page_url_temp);
		byte[] main_page_html = download_xrg.Prog_fmt_hdr_(log_msg).Exec_as_bry(main_page_url_temp);
		if (main_page_html == null) usr_dlg.Warn_many("", "", "failed to download main_page: src_url=~{0};", main_page_url_temp);
		return main_page_html;
	}	
	private void Failover(Io_url trg_fil) {
		usr_dlg.Note_many("", "", "copying failover file: trg_fil=~{0};", trg_fil.Raw());
		Io_mgr.Instance.CopyFil(failover_dir.GenSubFil(trg_fil.NameAndExt()), trg_fil, true);		
	}
	public boolean Css_scrape_setup() {
		Io_url trg_fil = wiki_html_dir.GenSubFil(Css_common_name);
		// if (Io_mgr.Instance.ExistsFil(trg_fil)) return;	// don't download if already there; DELETED: else main_page is not scraped for all stylesheet links; simple.d: fails; DATE:2014-02-11
		byte[] css_url = Css_scrape();
		if (css_url == null) {
			Css_common_failover();
			return false;
		}
		else {
			Io_mgr.Instance.SaveFilBry(trg_fil, css_url);
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
		List_adp rv = List_adp_.New();
		int raw_len = raw.length;
		int prv_pos = 0; 
		int css_find_bgn_len = Css_find_bgn.length;
		byte[] protocol_prefix_bry = BryUtl.NewU8(protocol_prefix);
		while (true) {
			int url_bgn = BryFind.FindFwd(raw, Css_find_bgn, prv_pos);	 				if (url_bgn == BryFind.NotFound) break;	// nothing left; stop
			url_bgn += css_find_bgn_len;
			int url_end = BryFind.FindFwd(raw, AsciiByte.Quote, url_bgn, raw_len); 	if (url_end == BryFind.NotFound) {usr_dlg.Warn_many("", "main_page.css_parse", "could not find css; pos='~{0}' text='~{1}'", url_bgn, StringUtl.NewU8ByLen(raw, url_bgn, url_bgn + 32)); break;}
			byte[] css_url_bry = BryLni.Mid(raw, url_bgn, url_end);
			css_url_bry = BryUtlByWtr.Replace(css_url_bry, Css_amp_find, Css_amp_repl);		// &amp; -> &
			css_url_bry = url_encoder.Decode(css_url_bry);								// %2C ->		%7C -> |
			css_url_bry = Xoa_css_extractor.Url_root_fix(wiki_domain, css_url_bry);
			Gfo_url gfo_url = url_parser.Parse(css_url_bry, 0, css_url_bry.length);
			if (	gfo_url.Protocol_tid() == Gfo_protocol_itm.Tid_relative_1			// if rel url, add protocol_prefix DATE:2015-08-01
				||	(Env_.Mode_testing() && gfo_url.Protocol_tid() == Gfo_protocol_itm.Tid_unknown))	// TEST:
				css_url_bry = BryUtl.Add(protocol_prefix_bry, css_url_bry);
			rv.Add(StringUtl.NewU8(css_url_bry));
			prv_pos = url_end;
		}
		return rv.ToStrAry();
	}	private static final byte[] Css_find_bgn = BryUtl.NewA7("<link rel=\"stylesheet\" href=\""), Css_amp_find = BryUtl.NewA7("&amp;"), Css_amp_repl = BryUtl.NewA7("&");
	private byte[] Css_scrape_download(String[] css_urls) {
		int css_urls_len = css_urls.length;
		BryWtr tmp_bfr = BryWtr.New();
		for (int i = 0; i < css_urls_len; i++) {
			String css_url = css_urls[i];
			usr_dlg.Prog_many("", "main_page.css_download", "downloading css for '~{0}'", css_url);
			download_xrg.Prog_fmt_hdr_(css_url);
			byte[] css_bry = download_xrg.Exec_as_bry(css_url); if (css_bry == null) continue;	// css not found; continue
			tmp_bfr.Add(Xoa_css_img_downloader.Bry_comment_bgn).AddStrU8(css_url).Add(Xoa_css_img_downloader.Bry_comment_end).AddByteNl();
			tmp_bfr.Add(css_bry).AddByteNl().AddByteNl();
		}
		return tmp_bfr.ToBryAndClear();
	}
	private static byte[] Url_root_fix(byte[] domain, byte[] url) {// DATE:2015-09-20
		if (url.length < 3) return url;	// need at least 2 chars
		if (	url[0] == AsciiByte.Slash	// starts with "/"	EX: "/w/api.php"
			&&	url[1] != AsciiByte.Slash	// but not "//";	EX: "//en.wikipedia.org"
			)
			return BryUtl.Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__https, domain, url);
		else
			return url;
	}
	public static final String Css_common_name = "xowa_common.css", Css_wiki_name = "xowa_wiki.css"
	, Css_common_name_ltr = "xowa_common_ltr.css", Css_common_name_rtl = "xowa_common_rtl.css";
}