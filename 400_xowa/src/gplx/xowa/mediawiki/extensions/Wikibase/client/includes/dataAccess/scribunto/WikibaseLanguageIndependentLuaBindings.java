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
package gplx.xowa.mediawiki.extensions.Wikibase.client.includes.dataAccess.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.includes.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.includes.dataAccess.*;
import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.stores.*;
import gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.config.*;
public class WikibaseLanguageIndependentLuaBindings {
	private final    EntityRetrievingTermLookup termLookup;
	private final    WikibaseClientDefault settings = WikibaseClientDefault.New();
	public WikibaseLanguageIndependentLuaBindings(Wbase_doc_mgr entity_mgr) {
		this.termLookup = new EntityRetrievingTermLookup(entity_mgr);
	}
	public byte[] getLabelByLanguage_or_null(byte[] prefixedEntityId, byte[] languageCode) {
		return termLookup.getLabel_or_null(prefixedEntityId, languageCode);
	}
	public Object getSetting(byte[] key) {
		return settings.getSetting(key);
	}
}
