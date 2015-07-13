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
package gplx.xowa.wmfs; import gplx.*; import gplx.xowa.*;
import gplx.json.*;
import gplx.xowa.wmfs.data.*;
public class Xow_wmf_api_wkr__ns implements Xow_wmf_api_wkr {
	private final Json_parser parser = new Json_parser();
	private final Xowmf_site_tbl tbl_site; private final Xowmf_ns_tbl tbl_ns;
	private int site_id_next = 1;
	public Xow_wmf_api_wkr__ns(Xowmf_site_tbl tbl_site, Xowmf_ns_tbl tbl_ns) {
		this.tbl_site = tbl_site; this.tbl_ns = tbl_ns;
	}
	public boolean		Api_wiki_enabled(String wiki_domain) {return true;}	// run against all wikis
	public String	Api_qargs() {return "action=query&meta=siteinfo&siprop=namespaces&format=json";}
	public void		Api_init() {
		tbl_site.Conn().Txn_bgn();
		tbl_site.Delete_all();
		tbl_ns.Delete_all();
	}
	public boolean		Api_exec(String wiki_domain, byte[] rslt) {
		try {
			int site_id = ++site_id_next;
			tbl_site.Insert(site_id, wiki_domain);
			Json_doc jdoc = parser.Parse(rslt);
			Json_grp ns_grp = jdoc.Get_grp(Bry_jpath); if (ns_grp == null) throw Exc_.new_("wmf_api_wkr.ns:invalid json", "json", rslt);
			int ns_len = ns_grp.Subs_len();
			for (int i = 0; i < ns_len; ++i) {
				try {
					Json_itm_kv kv = (Json_itm_kv)ns_grp.Subs_get_at(i);
					Json_itm_nde nde = (Json_itm_nde)kv.Val();
					int ns_id = Bry_.Xto_int_or(Get_val_or_null(nde, Bry_id), Int_.MinValue);
					byte ns_case = Xow_ns_case_.parse_(String_.new_u8(Get_val_or_null(nde, Bry_case)));
					byte[] ns_name = Get_val_or_null(nde, Bry_name);
					byte[] ns_canonical = Get_val_or_null(nde, Bry_canonical);
					if (ns_canonical == null) ns_canonical = Bry_.Empty;	// main_ns has no canonical
					byte[] subpages = Get_val_or_null(nde, Bry_subpages);	
					byte[] content = Get_val_or_null(nde, Bry_content);	
					tbl_ns.Insert(site_id, ns_id, ns_case, subpages != null, content != null, ns_name, ns_canonical);
				} catch (Exception e) {
					Xoa_app_.Usr_dlg().Warn_many("", "", "wmf_api_wkr.ns:unknown; wiki=~{0} rslt=~{1} i=~{2} err=~{3}", wiki_domain, rslt, i, Err_.Message_gplx(e));
					continue;
				}
			}
			tbl_site.Conn().Txn_sav();
			return true;
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "wmf_api_wkr.ns:unknown; wiki=~{0} rslt=~{1} err=~{2}", wiki_domain, rslt, Err_.Message_gplx(e));
			return false;
		}
	}
	public void		Api_term() {
		tbl_site.Conn().Txn_end();
	}
	private byte[] Get_val_or_null(Json_itm_nde nde, byte[] key) {
		Json_itm sub = nde.Subs_get_by_key(key);
		Json_itm_kv sub_as_kv = (Json_itm_kv)sub;			
		return sub_as_kv == null ? null : sub_as_kv.Val().Data_bry();	// sub_as_kv == null when key is not present; note that "canonical" does not exist for Main ns
	}
	private static final byte[] Bry_query = Bry_.new_a7("query"), Bry_namespaces = Bry_.new_a7("namespaces")
	, Bry_id = Bry_.new_a7("id"), Bry_case = Bry_.new_a7("case"), Bry_name = Bry_.new_a7("*"), Bry_canonical = Bry_.new_a7("canonical")
		, Bry_subpages = Bry_.new_a7("subpages"), Bry_content = Bry_.new_a7("content");
	private static final byte[][] Bry_jpath = new byte[][] {Bry_query, Bry_namespaces};
}
