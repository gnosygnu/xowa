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
public class Xot_defn_tmpl_ {
	public static Xot_invk CopyNew(Xop_ctx ctx, Xot_defn_tmpl orig_defn, Xot_invk orig, Xot_invk caller, byte[] src, byte[] frame_ttl) {	// SEE:NOTE_1
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		byte[] orig_src = orig_defn.Data_raw();
		Xow_wiki wiki = ctx.Wiki();
		Xot_invk_temp rv = new Xot_invk_temp(orig.Defn_tid(), orig_src, orig.Name_tkn(), caller.Src_bgn(), caller.Src_end());
		frame_ttl = wiki.Lang().Case_mgr().Case_reuse_1st_upper(frame_ttl);	// NOTE: always uppercase 1st; EX:{{navbox -> "Template:Navbox"; PAGE:en.w:Achilles DATE:2014-06-21
		rv.Frame_ttl_(wiki.Ns_mgr().Ns_template().Gen_ttl(frame_ttl));		// NOTE: always prepend "Template:" to frame_ttl; DATE:2014-06-13
		int orig_args_len = orig.Args_len();
		boolean tmpl_args_parsing_orig = ctx.Tmpl_args_parsing();
		ctx.Tmpl_args_parsing_(true);
		for (int i = 0; i < orig_args_len; i++) {
			Arg_nde_tkn orig_arg = orig.Args_get_by_idx(i);
			Arg_nde_tkn copy_arg = tkn_mkr.ArgNde(-1, 0);
			if (orig_arg.KeyTkn_exists()) {
				Arg_itm_tkn key_tkn = orig_arg.Key_tkn();
				copy_arg.Key_tkn_(Make_itm(false, ctx, tkn_mkr, src, key_tkn, caller, orig_arg));
				rv.Args_addByKey(copy_arg.Key_tkn().Dat_ary(), copy_arg);	// NOTE: was originally Bry_.Mid(caller.Src(), key_tkn.Dat_bgn(), key_tkn.Dat_end()) which was wrong; caused {{{increment}}} instead of "increment"
			}
			copy_arg.Val_tkn_(Make_itm(true, ctx, tkn_mkr, src, orig_arg.Val_tkn(), caller, orig_arg));
			rv.Args_add(copy_arg);
		}
		ctx.Tmpl_args_parsing_(tmpl_args_parsing_orig);
		return rv;
	}
	private static Arg_itm_tkn Make_itm(boolean val_tkn, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, Arg_itm_tkn orig, Xot_invk caller, Arg_nde_tkn orig_arg) {
		int subs_len = orig.Subs_len();
		Bry_bfr arg_bfr = Bry_bfr.new_();
		for (int i = 0; i < subs_len; i++)
			orig.Subs_get(i).Tmpl_evaluate(ctx, src, caller, arg_bfr);
		Arg_itm_tkn rv = tkn_mkr.ArgItm(-1, -1);	// NOTE: was -1, 0; DATE:2013-04-10
		byte[] rv_ary = orig_arg.KeyTkn_exists() && val_tkn ? arg_bfr.XtoAryAndClearAndTrim() : arg_bfr.XtoAryAndClear();	// // NOTE: must trim if key_exists; DUPE:TRIM_IF_KEY; EX.WP:Coord in Chernobyl disaster, Sahara
		rv.Dat_ary_(rv_ary);
		return rv;
	}
}
/*
NOTE_1: Creates an invk_temp from an invk

page	{{test_1|a}}
test_1	{{test_2|{{{1|nil_1}}}}}
test_2	{{{1|nil_2}}}

page   : invk_temp gets created for {{test1|a}} where name=test1 and arg1=a
test_1 : invk_temp gets created for {{test_2|{{{1|nil_1}}}}}
1)	create the invk_tmp tkn, with name=test2
2)	copy the args and resolve; in this case -> {{test2|a}}
now we can use the invk_temp to call test_2 (and so on if needed)
*/