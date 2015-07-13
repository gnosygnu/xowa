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
public class Xop_ttl_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "ttl");
	public static final Gfo_msg_itm
	  Len_0								= Gfo_msg_itm_.new_warn_(owner, "Len_0")
	, Len_max							= Gfo_msg_itm_.new_warn_(owner, "Len_max")
	, Ttl_has_ns_but_no_page			= Gfo_msg_itm_.new_warn_(owner, "Ttl_has_ns_but_no_page")			
	, Ttl_is_ns_only					= Gfo_msg_itm_.new_warn_(owner, "Ttl_is_ns_only")
	, Amp_unknown						= Gfo_msg_itm_.new_warn_(owner, "Amp_unknown")
	, Comment_eos						= Gfo_msg_itm_.new_warn_(owner, "Comment_eos")
	, Invalid_char						= Gfo_msg_itm_.new_warn_(owner, "Invalid_char")
	;
}
class Xop_comment_log {		
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "comment");
	public static final Gfo_msg_itm
		  Eos								= Gfo_msg_itm_.new_warn_(owner, "eos")
		;
}
class Xop_lnki_log {		
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "lnki");
	public static final Gfo_msg_itm
	  Upright_val_is_invalid			= Gfo_msg_itm_.new_warn_(owner, "upright_val_is_invalid")
	, Escaped_lnki						= Gfo_msg_itm_.new_warn_(owner, "escaped_lnki")
	, Key_is_empty						= Gfo_msg_itm_.new_warn_(owner, "key_is_empty")
	, Ext_is_missing					= Gfo_msg_itm_.new_warn_(owner, "ext_is_missing")
	, Invalid_ttl						= Gfo_msg_itm_.new_warn_(owner, "invalid_ttl")
	;
}
class Xop_tmpl_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tmpl");
	public static final Gfo_msg_itm
		  Escaped_tmpl						= Gfo_msg_itm_.new_warn_(owner, "Escaped_tmpl")
		, Escaped_end						= Gfo_msg_itm_.new_warn_(owner, "Escaped_end")
		, Key_is_empty						= Gfo_msg_itm_.new_note_(owner, "Key_is_empty")
		, Dangling							= Gfo_msg_itm_.new_note_(owner, "Dangling_tmpl")
		, Tmpl_end_autoCloses_something		= Gfo_msg_itm_.new_note_(owner, "Tmpl_end_autoCloses_something")
		, Tmpl_is_empty						= Gfo_msg_itm_.new_note_(owner, "Tmpl_is_empty")
		;
}
class Xop_misc_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "super");
	public static final Gfo_msg_itm
		  Eos								= Gfo_msg_itm_.new_warn_(owner, "End_of_string")
		;
}
class Xot_prm_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tmpl_defn_arg");
	public static final Gfo_msg_itm
		  Dangling							= Gfo_msg_itm_.new_warn_(owner, "Dangling_tmpl_defn_arg")
		, Elem_without_tbl					= Gfo_msg_itm_.new_warn_(owner, "Elem_without_tbl")
		, Lkp_is_nil						= Gfo_msg_itm_.new_note_(owner, "Lkp_is_nil")
		, Lkp_and_pipe_are_nil				= Gfo_msg_itm_.new_warn_(owner, "Lkp_and_pipe_are_nil")
		, Prm_has_2_or_more					= Gfo_msg_itm_.new_note_(owner, "Prm_has_2_or_more")
		;
}
class Xop_curly_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "curly");
	public static final Gfo_msg_itm
		  Bgn_not_found						= Gfo_msg_itm_.new_warn_(owner, "Bgn_not_found")
		, End_should_not_autoclose_anything	= Gfo_msg_itm_.new_warn_(owner, "End_should_not_autoclose_anything")
		, Bgn_len_0							= Gfo_msg_itm_.new_warn_(owner, "Bgn_len_0")
		, Bgn_len_1							= Gfo_msg_itm_.new_warn_(owner, "Bgn_len_1")
		, Tmpl_is_empty						= Gfo_msg_itm_.new_warn_(owner, "Tmpl_is_empty")
		;
}
class Xop_redirect_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "redirect");
	public static final Gfo_msg_itm
		  False_match						= Gfo_msg_itm_.new_warn_(owner, "False_match")
		, Lnki_not_found					= Gfo_msg_itm_.new_warn_(owner, "Lnki_not_found")
		;
}
class Xop_tag_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tag");
	public static final Gfo_msg_itm
		  Invalid							= Gfo_msg_itm_.new_warn_(owner, "Invalid")
		;
}
//	class Pf_func_lang_log {
//		private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tmpl_func_lang");
//		public static final Gfo_msg_itm
//			  Arg_out_of_bounds					= Gfo_msg_itm_.new_warn_(owner, "Arg_out_of_bounds")
//			;
//	}
//	class Mwl_expr_log {
//		private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "expr");
//		public static final Gfo_msg_itm
//			  Divide_by_zero					= Gfo_msg_itm_.new_warn_(owner, "Divide_by_zero")
//			, Expr_len0							= Gfo_msg_itm_.new_warn_(owner, "Expr_len0")
//			;
//	}
