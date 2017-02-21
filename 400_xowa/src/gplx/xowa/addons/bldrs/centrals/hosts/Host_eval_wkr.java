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
package gplx.xowa.addons.bldrs.centrals.hosts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.brys.evals.*;
import gplx.xowa.wikis.domains.*;
public class Host_eval_wkr implements Bry_eval_wkr {
	private Xow_domain_itm domain_itm;
	public String Key() {return "host_regy";}
	public Host_eval_wkr Domain_itm_(Xow_domain_itm domain_itm) {this.domain_itm = domain_itm; return this;}
	public void Resolve(Bry_bfr rv, byte[] src, int args_bgn, int args_end) {
		// EX: "~{host_regy|wiki_abrv}" -> "enwiki"
		int type = hash.Get_as_byte_or(src, args_bgn, args_end, Byte_.Max_value_127);
		switch (type) {
			case Type__wiki_abrv:
				// handle wikidata, commonswiki separately; DATE:2016-10-20
				if		(String_.Eq(domain_itm.Domain_str(), "www.wikidata.org")) 
					rv.Add_str_a7("wikidatawiki");
				else if (String_.Eq(domain_itm.Domain_str(), "commons.wikimedia.org")) 
					rv.Add_str_a7("commonswiki");
				// do not use Abrv_mw(); all other wikis will be "generalized" to their language url; EX:"en.wiktionary.org" -> "enwiki" x> "enwiktionary"
				else {
					byte[] lang_key = domain_itm.Lang_orig_key();
					if (lang_key == Bry_.Empty) lang_key = Bry_.new_a7("en"); // handle species 
					rv.Add(lang_key);
					rv.Add_str_a7("wiki");
				}
				break;
			default:					throw Err_.new_unhandled_default(type);
		}
	}

	public static final byte Type__wiki_abrv = 0;
	private static final    Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_str_byte("wiki_abrv", Type__wiki_abrv)
	;
}
