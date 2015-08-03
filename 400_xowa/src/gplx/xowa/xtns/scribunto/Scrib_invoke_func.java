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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.html.*;
import gplx.xowa.html.*;
import gplx.xowa.parsers.logs.*;
import gplx.xowa.xtns.pfuncs.*;
public class Scrib_invoke_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_invoke;}
	@Override public Pf_func New(int id, byte[] name) {return new Scrib_invoke_func().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {// {{#invoke:mod_name|prc_name|prc_args...}}
		Xowe_wiki wiki = ctx.Wiki();
		byte[] mod_name = Eval_argx(ctx, src, caller, self);
		if (Bry_.Len_eq_0(mod_name)) {Error(bfr, wiki.Msg_mgr(), Err_mod_missing); return;}		// EX: "{{#invoke:}}"
		int args_len = self.Args_len();
		byte[] fnc_name = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, null);
		Xop_log_invoke_wkr invoke_wkr = ctx.Xtn__scribunto__invoke_wkr();
		long log_time_bgn = 0;
		if (invoke_wkr != null) {
			log_time_bgn = Env_.TickCount();
			if (!invoke_wkr.Eval_bgn(ctx.Cur_page(), mod_name, fnc_name)) return;
		}
		Scrib_core core = Scrib_core.Core();
		if (core == null) {
			core = Scrib_core.Core_new_(ctx.App(), ctx).Init();
			core.When_page_changed(ctx.Cur_page());
		}
		byte[] mod_raw = null;
		Scrib_lua_mod mod = core.Mods_get(mod_name);
		if (mod == null) {
			Xow_ns module_ns = wiki.Ns_mgr().Ids_get_or_null(Scrib_xtn_mgr.Ns_id_module);
			Xoa_ttl mod_ttl = Xoa_ttl.parse_(wiki, Bry_.Add(module_ns.Name_db_w_colon(), mod_name));
			mod_raw = wiki.Cache_mgr().Page_cache().Get_or_load_as_src(mod_ttl);
			if (mod_raw == null) {Error(bfr, wiki.Msg_mgr(), Err_mod_missing); return;} // EX: "{{#invoke:missing_mod}}"
		}
		else
			mod_raw = mod.Text_bry();
		if (!core.Enabled()) {bfr.Add_mid(src, self.Src_bgn(), self.Src_end()); return;}
		try {
			core.Invoke(wiki, ctx, src, caller, self, bfr, mod_name, mod_raw, fnc_name);
			if (invoke_wkr != null)
				invoke_wkr.Eval_end(ctx.Cur_page(), mod_name, fnc_name, log_time_bgn);
		}
		catch (Exception e) {
			Error(bfr, wiki.Msg_mgr(), e);
			bfr.Add(Html_tag_.Comm_bgn).Add_str(Err_.Message_lang(e)).Add(Html_tag_.Comm_end);
			String invoke_error = String_.Replace(Err_.Message_gplx_log(e), "\n", "");	// NOTE: replace \n as error may have excerpt which will have \n
			Scrib_err_filter_mgr err_filter_mgr = invoke_wkr == null ? null : invoke_wkr.Err_filter_mgr();
			if (	err_filter_mgr == null																		// no err_filter_mgr defined;
				||	err_filter_mgr.Count_eq_0()																	// err_filter_mgr exists, but no definitions
				||	!err_filter_mgr.Match(String_.new_u8(mod_name), String_.new_u8(fnc_name), invoke_error))	// err_filter_mgr has defintion and it doesn't match current; print warn; DATE:2015-07-24					
				ctx.App().Usr_dlg().Warn_many("", "", "invoke failed: ~{0} ~{1} ~{2}", ctx.Cur_page().Ttl().Raw(), String_.new_u8(src, self.Src_bgn(), self.Src_end()), String_.Replace(Err_.Message_gplx_log(e), "\n", "\t"));
			Scrib_core.Core_invalidate_when_page_changes();	// NOTE: invalidate core when page changes, not for rest of page, else page with many errors will be very slow due to multiple invalidations; PAGE:th.d:all; DATE:2014-10-03
		}
	}
	public static void Error(Bry_bfr bfr, Xow_msg_mgr msg_mgr, Exception e) {Error(bfr, msg_mgr, Err_.cast_or_make(e).To_str__top_wo_args());}// NOTE: must use "short" error message to show in wikitext; DATE:2015-07-27
	public static void Error(Bry_bfr bfr, Xow_msg_mgr msg_mgr, String error) {
		byte[] script_error_msg = msg_mgr.Val_by_id(Xol_msg_itm_.Id_scribunto_parser_error);
		error_fmtr.Bld_bfr_many(bfr, script_error_msg, error);
	}
	private static final Bry_fmtr error_fmtr = Bry_fmtr.new_("<strong class='error'><span class='scribunto-error' id='mw-scribunto-error-0'>~{0}: ~{1}</span></strong>");	// <!--~{0}: ~{1}.-->
	public static final String Err_mod_missing = "No such module";
}
