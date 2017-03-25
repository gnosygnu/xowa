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
package gplx.xowa.mediawiki.includes.interwiki; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.site.*;
public class XomwInterwikiLookupAdapter implements XomwInterwikiLookup {
	/**
	* @var SiteLookup
	*/
	private final    XomwSiteLookup siteLookup;

	/**
	* @var Interwiki[]|null associative array mapping interwiki prefixes to Interwiki objects
	*/
	private Ordered_hash interwikiMap = Ordered_hash_.New_bry();

	public XomwInterwikiLookupAdapter (
		XomwSiteLookup siteLookup
		// Ordered_hash interwikiMap
	) {
		this.siteLookup = siteLookup;
	}

	/**
	* See InterwikiLookup::isValidInterwiki
	* It loads the whole interwiki map.
	*
	* @param String $prefix Interwiki prefix to use
	* @return boolean Whether it exists
	*/
	public boolean isValidInterwiki(byte[] prefix) {
		return XophpArray.array_key_exists(prefix, this.getInterwikiMap());
	}

	/**
	* See InterwikiLookup::fetch
	* It loads the whole interwiki map.
	*
	* @param String $prefix Interwiki prefix to use
	* @return Interwiki|null|boolean
	*/
	public XomwInterwiki fetch(byte[] prefix) {
		if (prefix == Bry_.Empty) {
			return null;
		}

		if (!this.isValidInterwiki(prefix)) {
			return null;
		}

		return (XomwInterwiki)this.interwikiMap.Get_by(prefix);
	}

	/**
	* See InterwikiLookup::getAllPrefixes
	*
	* @param String|null $local If set, limits output to local/non-local interwikis
	* @return String[] List of prefixes
	*/
	public byte[][] getAllPrefixes(boolean local) {
		if (!local) {
			XophpArray.array_keys_bry(this.getInterwikiMap());
		}
		List_adp res = List_adp_.New();
		Ordered_hash hash = this.getInterwikiMap();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			XomwInterwiki interwiki = (XomwInterwiki)hash.Get_at(i);
			if (interwiki.isLocal() == local) {
				res.Add(interwiki.interwikiId);
			}
		}
		return (byte[][])res.To_ary_and_clear(byte[].class);
	}

	//	/**
	//	* See InterwikiLookup::invalidateCache
	//	*
	//	* @param String $prefix
	//	*/
	//	public function invalidateCache($prefix) {
	//		if (!isset(this.interwikiMap[$prefix])) {
	//			return;
	//		}
	//		$globalId = this.interwikiMap[$prefix].getWikiID();
	//		unset(this.interwikiMap[$prefix]);
	//
	//		// Reload the interwiki
	//		site = this.siteLookup.getSites().getSite($globalId);
	//		interwikis = this.getSiteInterwikis(site);
	//		this.interwikiMap = array_merge(this.interwikiMap, [ interwikis[$prefix] ]);
	//	}

	/**
	* Load interwiki map to use as cache
	*/
	private Ordered_hash loadInterwikiMap() {
		Ordered_hash interwikiMap = Ordered_hash_.New();
		XomwSiteList siteList = this.siteLookup.getSites();
		int len = siteList.Len();
		for (int i = 0; i < len; i++) {
			XomwSite site = siteList.GetAt(i);
			XomwInterwiki[] interwikis = this.getSiteInterwikis(site);
			// interwikiMap = array_merge(interwikiMap, interwikis);
			for (XomwInterwiki interwiki : interwikis) {
				interwikiMap.Add(interwiki.interwikiId, interwiki);
			}
		}
		this.interwikiMap = interwikiMap;
		return interwikiMap;
	}

	/**
	* Get interwikiMap attribute, load if needed.
	*
	* @return Interwiki[]
	*/
	private Ordered_hash getInterwikiMap() {
		if (this.interwikiMap == null) {
			this.loadInterwikiMap();
		}
		return this.interwikiMap;
	}

	/**
	* Load interwikis for the given site
	*
	* @param Site site
	* @return Interwiki[]
	*/
	private XomwInterwiki[] getSiteInterwikis(XomwSite site) {
		Ordered_hash interwikis = Ordered_hash_.New();
		Ordered_hash hash = site.getInterwikiIds();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			String interwiki = (String)hash.Get_at(i);
			String url = site.getPageUrl();
			String path = null;
			if (Type_adp_.Eq_typeSafe(site, XomwMediaWikiSite.class)) {
				path = ((XomwMediaWikiSite)site).getFileUrl("api.php");
			} else {
				path = "";
			}
			boolean local = String_.Eq(site.getSource(), "local");
			// TODO: How to adapt trans?
			interwikis.Add(interwiki, new XomwInterwiki(
				Bry_.new_u8(interwiki),
				Bry_.new_u8(url),
				Bry_.new_u8(path),
				Bry_.new_u8(site.getGlobalId()),
				local
				, false
			));
		}
		return (XomwInterwiki[])interwikis.To_ary_and_clear(XomwInterwiki.class);
	}
}
