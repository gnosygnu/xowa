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
import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.dbs.datas.*;
import gplx.core.brys.evals.*;
import gplx.xowa.wikis.domains.*;
public class Host_eval_itm {
	private final    Bry_eval_mgr mgr = Bry_eval_mgr.Dflt();
	private final    Host_eval_wkr wkr = new Host_eval_wkr();
	private final    Hash_adp host_hash = Hash_adp_.New();
	public Host_eval_itm() {
		mgr.Add_many(wkr);
	}
	public byte[] Eval_dir_name(Xow_domain_itm domain_itm) {
		wkr.Domain_itm_(domain_itm);
		return mgr.Eval(Bry_.new_u8("Xowa_~{host_regy|wiki_abrv}_latest"));
	}
	public String Eval_src_fil(Xobc_data_db data_db, int host_id, Xow_domain_itm domain, String file_name) {
		return Eval_src_dir(data_db, host_id, domain) + file_name;
	}
	public String Eval_src_dir(Xobc_data_db data_db, int host_id, Xow_domain_itm domain) {
		Xobc_host_regy_itm host_itm = (Xobc_host_regy_itm)host_hash.Get_by(host_id);
		if (host_itm == null) {
			host_itm = data_db.Tbl__host_regy().Select(host_id);
			host_hash.Add(host_id, host_itm);
		}
		wkr.Domain_itm_(domain);
		String host_dir = String_.new_u8(mgr.Eval(Bry_.new_u8(host_itm.Host_data_dir())));
		return String_.Format("http://{0}/{1}/", host_itm.Host_domain(), host_dir);
	}
}
