/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.apps.site_cfgs;

import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BrySplit;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.core.net.Gfo_url_parser;
import gplx.langs.jsons.Json_itm;
import gplx.langs.jsons.Json_nde;
import gplx.xowa.Xow_wiki;
import gplx.xowa.apps.gfs.Gfs_php_converter;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.xwikis.Xow_xwiki_mgr;

class Xoa_site_cfg_itm__interwikimap extends Xoa_site_cfg_itm__base {
	private final BryWtr tmp_bfr = BryWtr.New();
	private final Gfo_url_parser url_parser = new Gfo_url_parser();
	public Xoa_site_cfg_itm__interwikimap() {
		this.Ctor(Xoa_site_cfg_loader__inet.Qarg__interwikimap);
	}
	@Override public void Parse_json_ary_itm(BryWtr bfr, Xow_wiki wiki, int i, Json_itm itm) {
		Json_nde nde = Json_nde.Cast(itm);
		if (i != 0) bfr.AddByteNl();
		byte[] iw_key = nde.Get_bry_or_null("prefix");	if (iw_key == null) throw ErrUtl.NewArgs("invalid interwiki", "key", iw_key);
		byte[] iw_url = nde.Get_bry_or_null("url");		if (iw_url == null) throw ErrUtl.NewArgs("invalid interwiki", "url", iw_key);
		bfr.Add(iw_key).AddBytePipe().Add(Gfs_php_converter.To_gfs(tmp_bfr, iw_url));
	}
	@Override public void Exec_csv(Xow_wiki wiki, int loader_tid, byte[] dsv_bry) {
		if (loader_tid == Xoa_site_cfg_loader_.Tid__fallback)
			Exec_csv__fallback(wiki);
		else {
			byte[][] lines = BrySplit.SplitLines(dsv_bry);
			int lines_len = lines.length;
			for (int i = 0; i < lines_len; ++i) {
				byte[] line = lines[i]; if (BryUtl.IsNullOrEmpty(line)) continue;	// ignore blank lines
				byte[][] flds = BrySplit.Split(line, AsciiByte.Pipe);
				byte[] url_fmt = flds[1];
				byte[] domain_bry = Xow_xwiki_mgr.Get_domain_from_url(url_parser, url_fmt);
				wiki.Xwiki_mgr().Add_by_site_interwikimap
					( flds[0], domain_bry, url_fmt
					, BryUtlByWtr.Replace(url_fmt, Arg0_xo, Arg0_wm) // NOTE: interwiki items are stored in wiki.core.xowa_cfg as https://en.wikipedia.org/wiki/~{0}
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
			case Xow_domain_tid_.Tid__wikimedia:	case Xow_domain_tid_.Tid__species:	case Xow_domain_tid_.Tid__meta:
			case Xow_domain_tid_.Tid__incubator:    case Xow_domain_tid_.Tid__wikimania: case Xow_domain_tid_.Tid__wikisource_org:
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
		if	(BryLni.Eq(wiki.Domain_bry(), Xow_domain_itm_.Bry__simplewiki))
			xwiki_mgr.Add_by_csv(Csv__enwiki);
	}
	private static final byte[] 
	  Csv__manual = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "1|commons;c|commons.wikimedia.org|Commons"
	, "1|m;metawikipedia|meta.wikipedia.org"
	, "1|species;wikispecies|species.wikimedia.org"
	, "1|d;wikidata|www.wikidata.org"
	, "1|mw;mediawikiwiki|www.mediawiki.org"
	, "1|wmf;wikimedia;foundation|foundation.wikimedia.org"
	, "1|incubator|incubator.wikimedia.org"
	, "1|wikimania|wikimania.wikimedia.org"
	, "1|sourceswiki|wikisource.org"
	, "0|oldwikisource|https://wikisource.org/wiki/~{0}|Old Wikisoure"
	, "0|mail|https://lists.wikimedia.org/mailman/listinfo/~{0}|Wikitech Mailing List"
	))
	, Csv__peers__lang = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "2|w;wikipedia|wikipedia"
	, "2|wikt;wiktionary|wiktionary"
	, "2|s;wikisource|wikisource"
	, "2|b;wikibooks|wikibooks"
	, "2|v;wikiversity|wikiversity"
	, "2|q;wikiquote|wikiquote"
	, "2|n;wikinews|wikinews"
	, "2|voy;wikivoyage|wikivoyage"
	))
	, Csv__peers__english = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "1|w|en.wikipedia.org"
	, "1|wikt|en.wiktionary.org"
	, "1|s|en.wikisource.org"
	, "1|b|en.wikibooks.org"
	, "1|v|en.wikiversity.org"
	, "1|q|en.wikiquote.org"
	, "1|n|en.wikinews.org"
	, "1|voy|en.wikivoyage.org"
	))
	, Csv__wikivoyage = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "0|commons|commons.wikimedia.org|Wikimedia Commons"
	, "2|wikipedia|wikipedia|Wikipedia"
	, "0|dmoz|http://www.dmoz.org/~{0}|DMOZ"
	))
	, Csv__enwiki = BryUtl.NewA7("2|w|wikipedia")
	;
	private static final byte[]
	  Arg0_xo = BryUtl.NewA7("~{0}")
	, Arg0_wm = BryUtl.NewA7("$1")
	;
}
