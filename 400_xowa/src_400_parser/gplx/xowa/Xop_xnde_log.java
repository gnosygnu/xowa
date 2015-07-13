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
public class Xop_xnde_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "xnde");
	public static final Gfo_msg_itm
		  Dangling_xnde						= Gfo_msg_itm_.new_warn_(owner, "dangling_xnde")
		, End_tag_not_allowed				= Gfo_msg_itm_.new_note_(owner, "end_tag_not_allowed")
		, Escaped_xnde						= Gfo_msg_itm_.new_warn_(owner, "escaped_xnde")
		, Invalid_char						= Gfo_msg_itm_.new_warn_(owner, "invalid_char")
		, Xtn_end_not_found					= Gfo_msg_itm_.new_warn_(owner, "xtn_end_not_found")
		, Invalid_tbl_sub					= Gfo_msg_itm_.new_warn_(owner, "invalid_tbl_sub")
		, Invalid_nest						= Gfo_msg_itm_.new_warn_(owner, "invalid_nest")
		, No_inline							= Gfo_msg_itm_.new_warn_(owner, "no_inline")
		, Tbl_sub_already_opened			= Gfo_msg_itm_.new_warn_(owner, "tbl_sub_already_opened")
		, Auto_closing_section				= Gfo_msg_itm_.new_warn_(owner, "auto_closing_section")
		, Eos_while_closing_tag				= Gfo_msg_itm_.new_warn_(owner, "eos_while_closing_tag")
		, Sub_sup_swapped					= Gfo_msg_itm_.new_warn_(owner, "sub_sup_swapped")
		, Restricted_tag					= Gfo_msg_itm_.new_warn_(owner, "restricted_tag")
		;
}
