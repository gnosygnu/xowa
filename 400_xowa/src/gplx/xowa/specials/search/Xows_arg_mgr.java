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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*;
class Xows_arg_mgr {
	private final Xows_paging_parser paging_parser = new Xows_paging_parser();
	public Xows_ns_mgr	Ns_mgr()		{return ns_mgr;} private final Xows_ns_mgr ns_mgr = new Xows_ns_mgr();
	public byte[]		Search_bry()	{return search_bry;} private byte[] search_bry;
	public int			Paging_idx()	{return paging_idx;} private int paging_idx;
	public byte			Sort_tid()		{return sort_tid;} private byte sort_tid;
	public byte[]		Cancel()		{return cancel;} private byte[] cancel;
	public Xows_paging_itm[] Paging_itms() {return paging_itms;} private Xows_paging_itm[] paging_itms;
	public Xows_arg_mgr Search_bry_(byte[] v) {search_bry = v; return this;} 
	public Xows_arg_mgr Clear() {
		ns_mgr.Clear();
		this.search_bry = null;
		this.paging_idx = 0;
		this.sort_tid = Xosrh_rslt_itm_sorter.Tid_none;
		this.cancel = null;
		return this;
	}
	public void Parse(Gfo_url_arg[] arg_ary) {
		if (arg_ary == null) return;
		int len = arg_ary.length;
		for (int i = 0; i < len; ++i) {
			Gfo_url_arg arg = arg_ary[i];
			byte[] key = arg.Key_bry();
			Object tid = url_args.Get_by(key);
			if (tid != null) {
				switch (((Byte_obj_val)tid).Val()) {
					case Arg_search: 		this.search_bry 	= Bry_.Replace(arg.Val_bry(), Byte_ascii.Plus, Byte_ascii.Space); break;
					case Arg_page_idx: 		this.paging_idx 	= Bry_.Xto_int_or(arg.Val_bry(), 0); break;
					case Arg_sort: 			this.sort_tid		= Xosrh_rslt_itm_sorter.parse_(String_.new_a7(arg.Val_bry())); break;			
					case Arg_cancel: 		this.cancel			= arg.Val_bry(); break;
					case Arg_paging: 		this.paging_itms	= paging_parser.Parse(arg.Val_bry()); break;
					default:				break;
				}
			}
			else {
				if (Bry_.Has_at_bgn(key, Ns_bry))		// check for ns*; EX: &ns0=1&ns8=1; NOTE: lowercase only
					ns_mgr.Add_by_parse(key, arg.Val_bry());
			}
		}
		ns_mgr.Add_main_if_empty();
	}
	private static final byte Arg_search = 0, Arg_page_idx = 1, Arg_sort = 2, Arg_cancel = 3, Arg_paging = 4;
	private static byte[] Ns_bry = Bry_.new_a7("ns");
	public static final byte[]
	  Arg_bry_page_index	= Bry_.new_a7("xowa_page_index")
	, Arg_bry_cancel		= Bry_.new_a7("cancel")
	;
	private static final Hash_adp_bry url_args = Hash_adp_bry.ci_ascii_()
		.Add_str_byte("xowa_paging", Arg_paging)
		.Add_bry_byte(Arg_bry_page_index, Arg_page_idx)
		.Add_str_byte("xowa_sort", Arg_sort)
		.Add_str_byte("search", Arg_search)
		.Add_bry_byte(Arg_bry_cancel, Arg_cancel)
	;
}
