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
package gplx.xowa.mediawiki.extensions.Wikibase.repo.includes.specials;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_page;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.specials.Xow_special_meta;
import gplx.xowa.specials.Xow_special_page;
import gplx.xowa.xtns.wbases.core.Wbase_pid;
public class Wbase_entityPage implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {			
		if (url.Segs_ary().length != 4) { // EX: www.wikidata.org/wiki/Special:EntityPage/Q2
			throw ErrUtl.NewArgs("entityPage url must have format of 'domain/wiki/Special:EntityPage/entityId'", "url", url.To_bry(true, true));
		}
		byte[] entityId = url.Segs_ary()[3];
		byte[] pageId = Wbase_pid.Prepend_property_if_needed(entityId);

		Xowe_wiki wikie = (Xowe_wiki)wiki;
		wikie.Data_mgr().Redirect((Xoae_page)page, pageId);
	}
	Wbase_entityPage(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final String SPECIAL_KEY = "EntityPage";
	public static final Xow_special_page Prototype = new Wbase_entityPage(Xow_special_meta.New_xo(SPECIAL_KEY, "Entity Page"));
}
