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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.net.*; import gplx.langs.jsons.*; import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*;
class Xoa_site_cfg_itm__interwikimap extends Xoa_site_cfg_itm__base {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Gfo_url_parser url_parser = new Gfo_url_parser();
	public Xoa_site_cfg_itm__interwikimap() {this.Ctor(Xoa_site_cfg_loader__inet.Qarg__interwikimap);}
	@Override public void Parse_json_ary_itm(Bry_bfr bfr, Xow_wiki wiki, int i, Json_itm itm) {
		Json_nde nde = Json_nde.cast(itm);
		if (i != 0) bfr.Add_byte_nl();
		byte[] iw_key = nde.Get_bry_or_null("prefix");	if (iw_key == null) throw Err_.new_("site_meta", "invalid interwiki", "key", iw_key);
		byte[] iw_url = nde.Get_bry_or_null("url");		if (iw_url == null) throw Err_.new_("site_meta", "invalid interwiki", "url", iw_key);
		bfr.Add(iw_key).Add_byte_pipe().Add(Gfs_php_converter.To_gfs(tmp_bfr, iw_url));
	}
	@Override public void Exec_csv(Xow_wiki wiki, int loader_tid, byte[] dsv_bry) {
		if (loader_tid == Xoa_site_cfg_loader_.Tid__fallback)
			Exec_csv__fallback(wiki);
		else {
			byte[][] lines = Bry_split_.Split_lines(dsv_bry);
			int lines_len = lines.length;
			for (int i = 0; i < lines_len; ++i) {
				byte[] line = lines[i]; if (Bry_.Len_eq_0(line)) continue;	// ignore blank lines
				byte[][] flds = Bry_split_.Split(line, Byte_ascii.Pipe);
				byte[] url_fmt = flds[1];
				byte[] domain_bry = Xow_xwiki_mgr.Get_domain_from_url(url_parser, url_fmt);
				wiki.Xwiki_mgr().Add_by_site_interwikimap
					( flds[0], domain_bry, url_fmt
					, Bry_.Replace(url_fmt, Arg0_xo, Arg0_wm) // NOTE: interwiki items are stored in wiki.core.xowa_cfg as https://en.wikipedia.org/wiki/~{0}
					);
			}
		}
	}
	private void Exec_csv__fallback(Xow_wiki wiki) {
		Xow_xwiki_mgr xwiki_mgr = wiki.Xwiki_mgr();
		int domain_tid = wiki.Domain_tid();
		xwiki_mgr.Add_by_csv(Csv__manual);	// adds manual xwikis that should exist in all wikis; EX: 'commons', 'wikidata', 'oldwikisource', 'wmf'
		switch (domain_tid) {
			case Xow_domain_tid_.Tid__wikipedia: case Xow_domain_tid_.Tid__wiktionary:	case Xow_domain_tid_.Tid__wikisource:	case Xow_domain_tid_.Tid__wikivoyage:
			case Xow_domain_tid_.Tid__wikiquote: case Xow_domain_tid_.Tid__wikibooks:	case Xow_domain_tid_.Tid__wikiversity:	case Xow_domain_tid_.Tid__wikinews:
				xwiki_mgr.Add_by_sitelink_mgr();									// lang: EX: [[fr:]]  -> fr.wikipedia.org
				xwiki_mgr.Add_by_csv(Csv__peers__lang);								// peer: EX: [[wikt]] -> en.wiktionary.org
				break;
			case Xow_domain_tid_.Tid__commons:		case Xow_domain_tid_.Tid__wikidata:
			case Xow_domain_tid_.Tid__wikimedia:	case Xow_domain_tid_.Tid__species:	case Xow_domain_tid_.Tid__meta: case Xow_domain_tid_.Tid__incubator:
			case Xow_domain_tid_.Tid__mediawiki:	case Xow_domain_tid_.Tid__wmfblog:
			case Xow_domain_tid_.Tid__home:			case Xow_domain_tid_.Tid__other:
				xwiki_mgr.Add_by_sitelink_mgr(Xow_domain_tid_.Tid__wikipedia);		// lang: hardcode to wikipedia; EX: "[[en:]] -> "en.wikipedia.org"
				xwiki_mgr.Add_by_csv(Csv__peers__english);							// peer: hardcode to english
				break;
		}

		// wikivoyage
		switch (domain_tid) {
			case Xow_domain_tid_.Tid__wikivoyage: case Xow_domain_tid_.Tid__home:	// NOTE: home needed for diagnostic; DATE:2015-10-13
				xwiki_mgr.Add_by_csv(Csv__wikivoyage);
				break;
		}

		// if simplewiki, add "w" -> "enwiki"
		if	(Bry_.Eq(wiki.Domain_bry(), Xow_domain_itm_.Bry__simplewiki))	
			xwiki_mgr.Add_by_csv(Csv__enwiki);
	}
	private static final    byte[] 
	  Csv__manual = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "1|commons;c|commons.wikimedia.org|Commons"
	, "1|m;metawikipedia|meta.wikipedia.org"
	, "1|species;wikispecies|species.wikimedia.org"
	, "1|d;wikidata|www.wikidata.org"
	, "1|mw;mediawikiwiki|www.mediawiki.org"
	, "1|wmf;wikimedia;foundation|wikimediafoundation.org"
	, "1|incubator|incubator.wikimedia.org"
	, "0|oldwikisource|https://wikisource.org/wiki/~{0}|Old Wikisoure"
	, "0|mail|https://lists.wikimedia.org/mailman/listinfo/~{0}|Wikitech Mailing List"
	))
	, Csv__peers__lang = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "2|w;wikipedia|wikipedia"
	, "2|wikt;wiktionary|wiktionary"
	, "2|s;wikisource|wikisource"
	, "2|b;wikibooks|wikibooks"
	, "2|v;wikiversity|wikiversity"
	, "2|q;wikiquote|wikiquote"
	, "2|n;wikinews|wikinews"
	, "2|voy;wikivoyage|wikivoyage"
	))
	, Csv__peers__english = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "1|w|en.wikipedia.org"
	, "1|wikt|en.wiktionary.org"
	, "1|s|en.wikisource.org"
	, "1|b|en.wikibooks.org"
	, "1|v|en.wikiversity.org"
	, "1|q|en.wikiquote.org"
	, "1|n|en.wikinews.org"
	, "1|voy|en.wikivoyage.org"
	))
	, Csv__wikivoyage = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "0|commons|commons.wikimedia.org|Wikimedia Commons"
	, "2|wikipedia|wikipedia|Wikipedia"
	, "0|dmoz|http://www.dmoz.org/~{0}|DMOZ"
	))
	, Csv__enwiki = Bry_.new_a7("2|w|wikipedia")
	;
	private static final    byte[]
	  Arg0_xo = Bry_.new_a7("~{0}")
	, Arg0_wm = Bry_.new_a7("$1")
	;
}

