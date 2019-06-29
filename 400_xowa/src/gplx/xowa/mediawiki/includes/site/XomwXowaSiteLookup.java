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
package gplx.xowa.mediawiki.includes.site; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.dbs.*;
public class XomwXowaSiteLookup implements XomwSiteLookup {
	private final    XomwSiteList sites = new XomwSiteList();
	public XomwSite getSite(byte[] globalId) {
		return (XomwSite)sites.getSite(String_.new_u8(globalId));
	}
	public XomwSiteList getSites() {
		throw Err_.new_unimplemented();
	}
	public XomwSite addSite(String type, String global_key, int id, boolean forward, String group, byte[] language, String source) {
		// REF:DBSiteStore
		XomwSite site = new XomwSite(type);
		site.setGlobalId(global_key);
		site.setInternalId(id);
		site.setForward(forward);
		site.setGroup(group);
		site.setLanguageCode(language);
		site.setSource(source);

//			while (rdr.Move_next()) {
//				int si_site = rdr.Read_int("si_site");
//				if (this.sites.hasInternalId(si_site)) {
//					XomwSite site = this.sites.getSiteByInternalId(si_site);
//					site.addLocalId(rdr.Read_str("si_type"), rdr.Read_str("si_key"));
//					this.sites.setSite(site);
//				}
//			}

		return site;
	}
}
