/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.wikis.nss.*;
public class Xot_defn_tmpl_ {
	public static Xot_invk CopyNew(Xop_ctx ctx, Xot_defn_tmpl orig_defn, Xot_invk orig, Xot_invk caller, byte[] src, byte[] frame_ttl) {	// SEE:NOTE_1
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		byte[] orig_src = orig_defn.Data_raw();
		Xowe_wiki wiki = ctx.Wiki();
		Xot_invk_temp rv = new Xot_invk_temp(orig.Defn_tid(), orig_src, orig.Name_tkn(), caller.Src_bgn(), caller.Src_end());
		frame_ttl = wiki.Lang().Case_mgr().Case_reuse_1st_upper(frame_ttl);	// NOTE: always uppercase 1st; EX:{{navbox -> "Template:Navbox"; PAGE:en.w:Achilles DATE:2014-06-21
		rv.Frame_ttl_(Bry_.Add(wiki.Ns_mgr().Ns_template().Name_db_w_colon(), Xoa_ttl.Replace_unders(frame_ttl)));		// NOTE: always prepend "Template:" to frame_ttl; DATE:2014-06-13; always use spaces; DATE:2014-08-14; must be local language; Russian "Шаблон" not English "Template"; PAGE:ru.w:Королевство_Нидерландов DATE:2016-11-23
		int orig_args_len = orig.Args_len();
		boolean tmpl_args_parsing_orig = ctx.Tmpl_args_parsing();
		ctx.Tmpl_args_parsing_(true);
		for (int i = 0; i < orig_args_len; i++) {
			Arg_nde_tkn orig_arg = orig.Args_get_by_idx(i);
			Arg_nde_tkn copy_arg = tkn_mkr.ArgNde(-1, 0);
			if (orig_arg.KeyTkn_exists()) {
				Arg_itm_tkn key_tkn = orig_arg.Key_tkn();
				copy_arg.Key_tkn_(Make_itm(false, ctx, tkn_mkr, src, key_tkn, caller, orig_arg));
				rv.Args_add_by_key(copy_arg.Key_tkn().Dat_ary(), copy_arg);	// NOTE: was originally Bry_.Mid(caller.Src(), key_tkn.Dat_bgn(), key_tkn.Dat_end()) which was wrong; caused {{{increment}}} instead of "increment"
			}
			else
				rv.Args_add_by_idx(copy_arg);	// NOTE: not a key, so add to idx_hash; DATE:2014-07-23
			copy_arg.Val_tkn_(Make_itm(true, ctx, tkn_mkr, src, orig_arg.Val_tkn(), caller, orig_arg));
			rv.Args_add(copy_arg);
		}
		ctx.Tmpl_args_parsing_(tmpl_args_parsing_orig);
		return rv;
	}
	private static Arg_itm_tkn Make_itm(boolean val_tkn, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, Arg_itm_tkn orig, Xot_invk caller, Arg_nde_tkn orig_arg) {
		int subs_len = orig.Subs_len();
		Bry_bfr arg_bfr = Bry_bfr_.New();
		for (int i = 0; i < subs_len; i++)
			orig.Subs_get(i).Tmpl_evaluate(ctx, src, caller, arg_bfr);
		Arg_itm_tkn rv = tkn_mkr.ArgItm(-1, -1);	// NOTE: was -1, 0; DATE:2013-04-10
		byte[] rv_ary = orig_arg.KeyTkn_exists() && val_tkn ? arg_bfr.To_bry_and_clear_and_trim() : arg_bfr.To_bry_and_clear();	// // NOTE: must trim if key_exists; DUPE:TRIM_IF_KEY; PAGE:en.w:Coord in Chernobyl disaster, Sahara
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