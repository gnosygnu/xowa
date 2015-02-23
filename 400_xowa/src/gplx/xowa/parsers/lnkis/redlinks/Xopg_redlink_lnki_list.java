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
package gplx.xowa.parsers.lnkis.redlinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.dbs.tbls.*; import gplx.xowa.users.*;
public class Xopg_redlink_lnki_list {
	private int lnki_idx = gplx.xowa.html.lnkis.Xoh_lnki_wtr.Lnki_id_min;	// NOTE: default to 1, not 0, b/c 0 is ignored by wtr; DATE:2014-10-09		
	public Xopg_redlink_lnki_list(boolean ttl_is_module) {			// never redlink in Module ns; particularly since Lua has multi-line comments for [[ ]]
		this.disabled = ttl_is_module;
	}
	public boolean		Disabled() {return disabled;} private final boolean disabled;
	public ListAdp	Lnki_list() {return lnki_list;} private final ListAdp lnki_list = ListAdp_.new_();
	public int		Thread_id() {return thread_id;} private int thread_id = 1;
	public void		Clear() {
		if (disabled) return;
		lnki_idx = 1;											// NOTE: must start at 1; html_wtr checks for > 0
		lnki_list.Clear();
		thread_id++;
	}
	public void Lnki_add(Xop_lnki_tkn lnki) {
		if (disabled) return;
		Xoa_ttl ttl = lnki.Ttl(); if (ttl == null) return;		// occurs for invalid links
		Xow_ns ns = ttl.Ns();
		if (	ns.Id_file_or_media()							// ignore files which will usually not be in local wiki (most are in commons), and whose html is built up separately
			||	(ns.Id_ctg() && !ttl.ForceLiteralLink())		// ignore ctgs which have their own html builder, unless it is literal; EX: [[:Category:A]]; DATE:2014-02-24
			||	ns.Id_special()									// ignore special, especially Search; EX: Special:Search/Earth
			||	ttl.Anch_bgn() == Xoa_ttl.Anch_bgn_anchor_only	// anchor only link; EX: [[#anchor]]
			||	ttl.Wik_itm() != null							// xwiki lnki; EX: simplewiki links in homewiki; [[simplewiki:Earth]]
			)
			return;				
		lnki.Html_id_(lnki_idx);
		lnki_list.Add(lnki);
		++lnki_idx;
	}
	public static final String Lnki_id_prefix = "xowa_lnki_";
}
