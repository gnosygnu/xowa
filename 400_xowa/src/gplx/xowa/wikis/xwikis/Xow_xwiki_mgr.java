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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.net.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.langs.*;	
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.parsers.*; import gplx.xowa.wikis.xwikis.sitelinks.*;
import gplx.xowa.wikis.xwikis.interwikis.*;
public class Xow_xwiki_mgr {
	private final    Ordered_hash list = Ordered_hash_.New_bry(); private final    Hash_adp_bry hash = Hash_adp_bry.ci_a7();
	private final    Xow_wiki wiki;
	public Xow_xwiki_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		this.xwiki_domain_tid = Xwiki_tid(wiki.Domain_tid());
	}
	public Xow_interwiki_map Interwiki_map() {return interwiki_map;} private final    Xow_interwiki_map interwiki_map = new Xow_interwiki_map(); // separate map for Scrib; DATE:2017-04-01
	public int	Xwiki_domain_tid() {return xwiki_domain_tid;} private int xwiki_domain_tid;
	public int	Len() {return list.Count();}
	public void Clear() {hash.Clear(); list.Clear();}
	public void Sort_by_key() {list.Sort();}
	public Xow_xwiki_itm Get_at(int i)								{return (Xow_xwiki_itm)list.Get_at(i);}
	public Xow_xwiki_itm Get_by_key(byte[] key)						{return (Xow_xwiki_itm)hash.Get_by_bry(key);}
	public Xow_xwiki_itm Get_by_mid(byte[] src, int bgn, int end)	{return (Xow_xwiki_itm)hash.Get_by_mid(src, bgn, end);}

	public Xow_xwiki_itm Add_by_atrs_offline(String key, String domain) {
		return Add_by_atrs(Bry_.new_a7(key), Bry_.new_a7(domain), null)
			.Offline_(true);// NOTE: need to mark offline in order to show in left sidebar
	}
	public Xow_xwiki_itm Add_by_site_interwikimap(byte[] key, byte[] domain_bry, byte[] url_xo, byte[] url_wm) {
		interwiki_map.Add(key, domain_bry, url_wm);
		return this.Add_by_atrs(key, domain_bry, url_xo);
	}

	public Xow_xwiki_itm Add_by_atrs(String key, String domain)		{return Add_by_atrs(Bry_.new_a7(key), Bry_.new_a7(domain), null);}
	public Xow_xwiki_itm Add_by_atrs(byte[] key, byte[] domain)		{return Add_by_atrs(key, domain, null);}
	public Xow_xwiki_itm Add_by_atrs(byte[] key, byte[] domain_bry, byte[] url_fmt) {
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
		Xow_xwiki_itm itm = Xow_xwiki_itm.new_(key, url_fmt, domain_itm.Lang_actl_uid(), domain_itm.Domain_type_id(), domain_bry, domain_itm.Abrv_wm());
		Add_itm(itm);
		return itm;
	}
	public void Add_by_sitelink_mgr() {Add_by_sitelink_mgr(wiki.Domain_tid());}
	public void Add_by_sitelink_mgr(int domain_tid) {
		String wiki_tid_name_str = String_.new_u8(Xow_domain_tid_.Get_type_as_bry(domain_tid));
		Xoa_sitelink_itm_mgr itm_mgr = wiki.App().Xwiki_mgr__sitelink_mgr().Itm_mgr();
		int len = itm_mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_itm itm = (Xoa_sitelink_itm)itm_mgr.Get_at(i);
			byte[] itm_key = itm.Key();	// EX: "fr" as in "[[fr:]]"
			byte[] domain_bry = Bry_.new_u8(String_.Format("{0}.{1}.org", String_.new_u8(itm_key), wiki_tid_name_str)); // EX: fr.wikipedia.org
			Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
			byte[] abrv_wm = domain_itm == null ? null : domain_itm.Abrv_wm();
			Xow_xwiki_itm xwiki = Xow_xwiki_itm.new_(itm_key, Bld_url_fmt(domain_bry), domain_itm.Lang_actl_uid(), domain_tid, domain_bry, abrv_wm);
			Add_itm(xwiki);
		}
	}
	public void Add_by_csv(byte[] src) {
		synchronized (list) {	// THREAD: parser stores state at app-level
			Xow_xwiki_itm_parser itm_parser = wiki.App().Xwiki_mgr__itm_parser();
			itm_parser.Init_by_wiki(wiki.Domain_itm());
			itm_parser.Load_by_bry(src);
			Ordered_hash xwiki_list = itm_parser.Xwiki_list();
			int len = xwiki_list.Count();
			for (int i = 0; i < len; ++i) {
				Xow_xwiki_itm itm = (Xow_xwiki_itm)xwiki_list.Get_at(i);
				Add_itm(itm);
			}
		}
	}
	public void Add_itm(Xow_xwiki_itm itm) {
		byte[] key_bry = itm.Key_bry();
		if (wiki.Ns_mgr().Names_get_or_null(key_bry) != null) return;// NOTE: do not add xwiki if key matches Srch_rslt_cbk; EX: en.wiktionary.org has ns of "Wiktionary"; do not add key of "wiktionary"; note that wikipedia does have an key to wiktionary
		list.Add_if_dupe_use_nth(key_bry, itm);		// NOTE: some wikis like commons will be added multiple times under different aliases (commons, c, commons.wikimedia.org); need to check domain and add only once DATE:2014-11-07
		hash.Add_if_dupe_use_nth(key_bry, itm);
	}
	private static int Xwiki_tid(int tid) {
		switch (tid) {
			case Xow_domain_tid_.Tid__commons: case Xow_domain_tid_.Tid__species: case Xow_domain_tid_.Tid__incubator:
			case Xow_domain_tid_.Tid__mediawiki: case Xow_domain_tid_.Tid__wmfblog: case Xow_domain_tid_.Tid__home:	
						return Xow_domain_tid_.Tid__wikipedia;	// set xwiki_tid to wikipedia; allows [[da:Page]] to point to da.wikipedia.org; PAGE:species:Puccinia; DATE:2014-09-14
			default:	return tid;
		}
	}
	public static byte[] Get_domain_from_url(Gfo_url_parser url_parser, byte[] url_fmt) { // extract "commons.wikimedia.org" from "http://commons.wikimedia.org/wiki/Category:~{0}"
		Gfo_url url = url_parser.Parse(url_fmt, 0, url_fmt.length);
		return url.Segs__get_at_1st();
	}
	public static byte[] Bld_url_fmt(byte[] domain_bry) {return Bry_.Add(gplx.core.net.Gfo_protocol_itm.Itm_https.Text_bry(), domain_bry, Bry__url_fmt_end);}
	private static final    byte[] Bry__url_fmt_end = Bry_.new_a7("/wiki/~{0}");
}
