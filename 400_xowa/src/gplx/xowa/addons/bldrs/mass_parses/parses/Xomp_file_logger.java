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
package gplx.xowa.addons.bldrs.mass_parses.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*;
import gplx.xowa.files.*; import gplx.xowa.addons.bldrs.files.cmds.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.domains.*;
public class Xomp_file_logger implements Xop_file_logger {
	private final    Xob_lnki_temp_tbl tbl;
	private final    Xowe_wiki commons_wiki;
	private boolean ns_file_is_case_match_all = true;
	public Xomp_file_logger(Xowe_wiki wiki, Db_conn wkr_conn) {
		this.tbl = new Xob_lnki_temp_tbl(wkr_conn); wkr_conn.Meta_tbl_assert(tbl);
		this.commons_wiki = wiki.Appe().Wiki_mgr().Get_by_or_make(Xow_domain_itm_.Bry__commons);
		this.ns_file_is_case_match_all = wiki.Init_assert().Ns_mgr().Ns_file().Case_match() == Xow_ns_case_.Tid__all;	// NOTE: wiki must be init'd;
	}
	public void Bgn() {
		tbl.Insert_bgn();
	}
	public void Log_file(Xop_ctx ctx, Xop_lnki_tkn lnki, byte caller_tid) {
		if (lnki.Ttl().ForceLiteralLink()) return; // ignore literal links which create a link to file, but do not show the image; EX: [[:File:A.png|thumb|120px]] creates a link to File:A.png, regardless of other display-oriented args

		// get caller_tid / tttl
		if (lnki.Ns_id() == Xow_ns_.Tid__media) caller_tid = Xop_file_logger_.Tid__media;

		// get lnki_data
		byte[] ttl = lnki.Ttl().Page_db();
		Xof_ext ext = Xof_ext_.new_by_ttl_(ttl);
		byte[] ttl_commons = Xomp_file_logger.To_commons_ttl(ns_file_is_case_match_all, commons_wiki, ttl);

		// do insert
		tbl.Insert_cmd_by_batch(ctx.Page().Bldr__ns_ord(), ctx.Page().Db().Page().Id(), ttl, ttl_commons, Byte_.By_int(ext.Id()), lnki.Lnki_type(), caller_tid, lnki.W(), lnki.H(), lnki.Upright(), lnki.Time(), lnki.Page());
	}
	public void End() {
		tbl.Insert_end();
	}
	public static byte[] To_commons_ttl(boolean ns_file_is_case_match_all, Xowe_wiki commons_wiki, byte[] ttl_bry) { // handle case-sensitive wikis (en.d) vs case-insensitive commons
		if (!ns_file_is_case_match_all) return null;	// return "" if wiki matches common
		Xoa_ttl ttl = Xoa_ttl.Parse(commons_wiki, Xow_ns_.Tid__file, ttl_bry);
		byte[] rv = ttl.Page_db();
		return Bry_.Eq(rv, ttl_bry) ? null : rv;
	}
}
