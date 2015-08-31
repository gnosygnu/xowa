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
package gplx.xowa; import gplx.*;
public class Xowc_xtn_pages implements GfoInvkAble {
	public boolean Init_needed()		{return init_needed;}			private boolean init_needed = true;
	public int Ns_page_id()			{return ns_page_id;}			private int ns_page_id = Int_.Min_value;
	public int Ns_page_talk_id()	{return ns_page_talk_id;}		private int ns_page_talk_id = Int_.Min_value;
	public int Ns_index_id()		{return ns_index_id;}			private int ns_index_id = Int_.Min_value;
	public int Ns_index_talk_id()	{return ns_index_talk_id;}		private int ns_index_talk_id = Int_.Min_value;
	public void Ns_names_(byte[] page_name, byte[] page_talk_name, byte[] index_name, byte[] index_talk_name) {
		this.page_name = page_name; this.page_talk_name = page_talk_name; this.index_name = index_name; this.index_talk_name = index_talk_name;
	}	
	private byte[] 
	  page_name			= Default_ns_page_name
	, page_talk_name	= Default_ns_page_talk_name
	, index_name		= Default_ns_index_name
	, index_talk_name	= Default_ns_index_talk_name;
	public void Reset() {
		ns_page_id = ns_page_talk_id = ns_index_id = ns_index_talk_id = Int_.Min_value;
		init_needed = true;
	}
	public void Init(Xow_ns_mgr ns_mgr) {
		init_needed = false;
		int len = ns_mgr.Ords_len();
		for (int i = 0; i < len; i++) {	// Page / Index ns_ids are variable per wiki; iterate over ns, and set ns_id
			Xow_ns ns = ns_mgr.Ords_get_at(i); if (ns == null) continue;
			byte[] ns_name = ns.Name_enc();
			if		(Bry_.Eq(ns_name, page_name))		{ns_page_id = ns.Id(); ns_mgr.Ns_page_id_(ns_page_id);}
			else if (Bry_.Eq(ns_name, page_talk_name))	ns_page_talk_id = ns.Id();
			else if (Bry_.Eq(ns_name, index_name))		ns_index_id = ns.Id();
			else if (Bry_.Eq(ns_name, index_talk_name))	ns_index_talk_id = ns.Id();
		}
		int aliases_added = 0;
		aliases_added = Set_canonical(ns_mgr, aliases_added, ns_page_id			, Default_ns_page_name);
		aliases_added = Set_canonical(ns_mgr, aliases_added, ns_page_talk_id	, Default_ns_page_talk_name);
		aliases_added = Set_canonical(ns_mgr, aliases_added, ns_index_id		, Default_ns_index_name);
		aliases_added = Set_canonical(ns_mgr, aliases_added, ns_index_talk_id	, Default_ns_index_talk_name);
		if (aliases_added > 0)	// NOTE: will probably only be 0 for English Wikisource
			ns_mgr.Init_w_defaults();
	}
	private int Set_canonical(Xow_ns_mgr ns_mgr, int aliases_added, int id, byte[] name) {
		Xow_ns ns =  ns_mgr.Ids_get_or_null(id);
		if (	ns == null							// ns doesn't exist; should throw error;
			||	!Bry_.Eq(ns.Name_bry(), name)		// ns exists, but name doesn't match canonical
			) {
			ns_mgr.Aliases_add(id, String_.new_a7(name));					
			++aliases_added;
		}
		return aliases_added;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ns_names_))				Ns_names_(m.ReadBry("page"), m.ReadBry("page_talk"), m.ReadBry("index"), m.ReadBry("index_talk"));
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_ns_names_ = "ns_names_";
	public static final byte[] Xtn_key = Bry_.new_a7("pages");
	public static final int Ns_index_id_default = 102, Ns_page_id_default = 104;

	private static final byte[]
	  Default_ns_page_name			= Bry_.new_a7("Page")
	, Default_ns_page_talk_name		= Bry_.new_a7("Page_talk")
	, Default_ns_index_name			= Bry_.new_a7("Index")
	, Default_ns_index_talk_name	= Bry_.new_a7("Index_talk")
	;
}
