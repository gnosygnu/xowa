/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
				byte[] lang_key = domain_itm.Lang_orig_key();
				if (lang_key == Bry_.Empty) lang_key = Bry_.new_a7("en"); // handle species 
				rv.Add(lang_key);
				rv.Add_str_a7("wiki");
				break;
			default:					throw Err_.new_unhandled_default(type);
		}
	}

	public static final byte Type__wiki_abrv = 0;
	private static final    Hash_adp_bry hash = Hash_adp_bry.cs()
	.Add_str_byte("wiki_abrv", Type__wiki_abrv)
	;
}
