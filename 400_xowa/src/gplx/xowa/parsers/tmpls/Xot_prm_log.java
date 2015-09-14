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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xot_prm_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tmpl_defn_arg");
	public static final Gfo_msg_itm
		  Dangling							= Gfo_msg_itm_.new_warn_(owner, "Dangling_tmpl_defn_arg")
		, Elem_without_tbl					= Gfo_msg_itm_.new_warn_(owner, "Elem_without_tbl")
		, Lkp_is_nil						= Gfo_msg_itm_.new_note_(owner, "Lkp_is_nil")
		, Lkp_and_pipe_are_nil				= Gfo_msg_itm_.new_warn_(owner, "Lkp_and_pipe_are_nil")
		, Prm_has_2_or_more					= Gfo_msg_itm_.new_note_(owner, "Prm_has_2_or_more")
		;
}
