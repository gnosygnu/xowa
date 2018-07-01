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
package gplx.xowa.xtns.wbases.mediawiki.client.config; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.mediawiki.*; import gplx.xowa.xtns.wbases.mediawiki.client.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.stores.*;
public class WikibaseClientDefault {
	private final    Hash_adp_bry settingCache = Hash_adp_bry.cs();
	public Object getSetting(byte[] key) {
		return settingCache.Get_by_bry(key);
	}
	private void addSetting(String key, Object val) {
		settingCache.Add(Bry_.new_u8(key), val);
	}
	public static WikibaseClientDefault New() {
		WikibaseClientDefault rv = new WikibaseClientDefault();
		rv.addSetting("injectRecentChanges", true);
		rv.addSetting("showExternalRecentChanges", true);
		rv.addSetting("sendEchoNotification", false);
		rv.addSetting("echoIcon", false);
		rv.addSetting("allowDataTransclusion", true);
		rv.addSetting("referencedEntityIdAccessLimit", 3);
		rv.addSetting("referencedEntityIdMaxDepth", 4);
		rv.addSetting("referencedEntityIdMaxReferencedEntityVisits", 50);
		rv.addSetting("allowLocalShortDesc", false);
		rv.addSetting("propagateChangesToRepo", true);
		rv.addSetting("useTermsTableSearchFields", true);
		rv.addSetting("forceWriteTermsTableSearchFields", false);
		rv.addSetting("allowArbitraryDataAccess", true);			
		rv.addSetting("entityAccessLimit", 250);
		rv.addSetting("allowDataAccessInUserLanguage", false);
		rv.addSetting("sharedCacheDuration", 60 * 60);
		rv.addSetting("fineGrainedLuaTracking", true);
		return rv;
	}
}
