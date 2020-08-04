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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store;

import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;

public class XomwEntityRetrievingTermLookup {
	private final    Wbase_doc_mgr entity_mgr;
	public XomwEntityRetrievingTermLookup(Wbase_doc_mgr entity_mgr) {
		this.entity_mgr = entity_mgr;
	}

	public byte[] getLabel_or_null(byte[] entityId, byte[] languageCode) {
		Wdata_doc entity = entity_mgr.Get_by_xid_or_null(entityId);
		if (entity == null) return null; // must check for null; PAGE:fr.w:Wikipédia:Ateliers_Bases/Recherche ISSUE#:773; DATE:2020-08-04
		return entity.Get_label_bry_or_null(languageCode);
	}
}
