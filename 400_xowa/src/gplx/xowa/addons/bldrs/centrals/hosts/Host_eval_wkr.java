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
package gplx.xowa.addons.bldrs.centrals.hosts;
import gplx.core.brys.evals.Bry_eval_wkr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.wikis.domains.Xow_domain_itm;
public class Host_eval_wkr implements Bry_eval_wkr {
	private Xow_domain_itm domain_itm;
	public String Key() {return "host_regy";}
	public Host_eval_wkr Domain_itm_(Xow_domain_itm domain_itm) {this.domain_itm = domain_itm; return this;}
	public void Resolve(BryWtr rv, byte[] src, int args_bgn, int args_end) {
		// EX: "~{host_regy|wiki_abrv}" -> "enwiki"
		int type = hash.Get_as_byte_or(src, args_bgn, args_end, ByteUtl.MaxValue127);
		switch (type) {
			case Type__wiki_abrv:
				// handle wikidata, commonswiki separately; DATE:2016-10-20
				if		(StringUtl.Eq(domain_itm.Domain_str(), "www.wikidata.org"))
					rv.AddStrA7("wikidatawiki");
				else if (StringUtl.Eq(domain_itm.Domain_str(), "commons.wikimedia.org"))
					rv.AddStrA7("commonswiki");
				// do not use Abrv_mw(); all other wikis will be "generalized" to their language url; EX:"en.wiktionary.org" -> "enwiki" x> "enwiktionary"
				else {
					byte[] lang_key = domain_itm.Lang_orig_key();
					if (lang_key == BryUtl.Empty) lang_key = BryUtl.NewA7("en"); // handle species
					rv.Add(lang_key);
					rv.AddStrA7("wiki");
				}
				break;
			default:					throw ErrUtl.NewUnhandled(type);
		}
	}

	public static final byte Type__wiki_abrv = 0;
	private static final Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_str_byte("wiki_abrv", Type__wiki_abrv)
	;
}
