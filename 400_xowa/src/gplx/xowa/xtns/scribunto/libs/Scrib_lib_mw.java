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
package gplx.xowa.xtns.scribunto.libs;
import gplx.core.envs.Env_;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryRef;
import gplx.core.primitives.Gfo_number_parser;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.funcs.Xol_func_itm;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_parser_tid_;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.tmpls.Arg_nde_tkn;
import gplx.xowa.parsers.tmpls.Xot_defn;
import gplx.xowa.parsers.tmpls.Xot_defn_;
import gplx.xowa.parsers.tmpls.Xot_defn_tmpl;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.parsers.tmpls.Xot_invk_mock;
import gplx.xowa.parsers.tmpls.Xot_invk_tkn_;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_frame_;
import gplx.xowa.xtns.scribunto.Scrib_fsys_mgr;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Scrib_lib_mw implements Scrib_lib {
	private Scrib_core core; private Scrib_fsys_mgr fsys_mgr;
	public Scrib_lib_mw(Scrib_core core) {this.core = core; this.fsys_mgr = core.Fsys_mgr();}
	public String Key() {return "mwInit";}
	public Scrib_lua_mod Mod() {return mod;} public void Mod_(Scrib_lua_mod v) {this.mod = v;} private Scrib_lua_mod mod;
	public boolean Allow_env_funcs() {return allow_env_funcs;} private boolean allow_env_funcs = true;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_mw(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		core.RegisterInterface(this, script_dir.GenSubFil("mwInit.lua"));	// DATE:2014-07-12
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.lua")
			, KeyVal.NewStr("allowEnvFuncs", allow_env_funcs));
		return mod;
	}
	public void Invoke_bgn(Xowe_wiki wiki, Xop_ctx ctx, byte[] new_src) {
		if (src != null)	// src exists; indicates that Invoke being called recursively; push existing src onto stack
			src_stack.Add(src);
		this.cur_wiki = wiki; this.ctx = ctx; this.src = new_src;
	}	private Xowe_wiki cur_wiki; private byte[] src; private Xop_ctx ctx; private List_adp src_stack = List_adp_.New();
	public void Invoke_end() {
		if (src_stack.Len() > 0)	// src_stack item exists; pop
			src = (byte[])List_adp_.Pop(src_stack);
		else						// entry point; set to null
			src = null;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_loadPackage:							return LoadPackage(args, rslt);
			case Proc_loadPHPLibrary:						return LoadPHPLibrary(args, rslt);
			case Proc_frameExists:							return FrameExists(args, rslt);
			case Proc_newChildFrame:						return NewChildFrame(args, rslt);
			case Proc_getExpandedArgument:					return GetExpandedArgument(args, rslt);
			case Proc_getAllExpandedArguments:				return GetAllExpandedArguments(args, rslt);
			case Proc_expandTemplate:						return ExpandTemplate(args, rslt);
			case Proc_callParserFunction:					return CallParserFunction(args, rslt);
			case Proc_preprocess:							return Preprocess(args, rslt);
			case Proc_incrementExpensiveFunctionCount:		return IncrementExpensiveFunctionCount(args, rslt);
			case Proc_isSubsting:							return IsSubsting(args, rslt);
			case Proc_getFrameTitle:						return GetFrameTitle(args, rslt);
			case Proc_setTTL:								return SetTTL(args, rslt);
			case Proc_parentFrameExists:					return ParentFrameExists(args, rslt);	// DEPRECATED:not in Scribunto anymore
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	public static final int
	  Proc_loadPackage = 0, Proc_loadPHPLibrary = 1
	, Proc_frameExists = 2, Proc_newChildFrame = 3
	, Proc_getExpandedArgument = 4, Proc_getAllExpandedArguments = 5
	, Proc_expandTemplate = 6, Proc_callParserFunction = 7, Proc_preprocess = 8
	, Proc_incrementExpensiveFunctionCount = 9, Proc_isSubsting = 10
	, Proc_getFrameTitle = 11, Proc_setTTL = 12
	, Proc_parentFrameExists = 13
	;
	public static final String 
	  Invk_loadPackage = "loadPackage", Invk_loadPHPLibrary = "loadPHPLibrary"
	, Invk_frameExists = "frameExists", Invk_newChildFrame = "newChildFrame"
	, Invk_getExpandedArgument = "getExpandedArgument", Invk_getAllExpandedArguments = "getAllExpandedArguments"
	, Invk_expandTemplate = "expandTemplate", Invk_callParserFunction = "callParserFunction", Invk_preprocess = "preprocess"
	, Invk_incrementExpensiveFunctionCount = "incrementExpensiveFunctionCount", Invk_isSubsting = "isSubsting"
	, Invk_getFrameTitle = "getFrameTitle", Invk_setTTL = "setTTL"
	, Invk_parentFrameExists = "parentFrameExists"
	;
	private static final String[] Proc_names = StringUtl.Ary
	( Invk_loadPackage, Invk_loadPHPLibrary
	, Invk_frameExists, Invk_newChildFrame
	, Invk_getExpandedArgument, Invk_getAllExpandedArguments
	, Invk_expandTemplate, Invk_callParserFunction, Invk_preprocess
	, Invk_incrementExpensiveFunctionCount, Invk_isSubsting
	, Invk_getFrameTitle, Invk_setTTL
	, Invk_parentFrameExists
	);
	public boolean LoadPackage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String mod_name = args.Pull_str(0);
		String mod_code = fsys_mgr.Get_or_null(mod_name);	// check if mod_name a file in /lualib/ directoryScribunto .lua file (in /lualib/)
		if (mod_code != null)
			return rslt.Init_obj(core.Interpreter().LoadString("@" + mod_name + ".lua", mod_code));
		Xoa_ttl ttl = Xoa_ttl.Parse(cur_wiki, BryUtl.NewU8(mod_name));// NOTE: should have Module: prefix
		if (ttl == null) return rslt.Init_ary_empty();
		byte[] page_db = cur_wiki.Cache_mgr().Page_cache().Get_src_else_load_or_null(ttl);
		if (page_db == null) return rslt.Init_ary_empty();
		Scrib_lua_mod mod = new Scrib_lua_mod(core, mod_name);
		return rslt.Init_obj(mod.LoadString(StringUtl.NewU8(page_db)));
	}
	public boolean LoadPHPLibrary(Scrib_proc_args args, Scrib_proc_rslt rslt) { // NOTE: noop; Scribunto uses this to load the Scribunto_*Library classses (EX: Scribunto_TitleLibrary); DATE:2015-01-21
		return rslt.Init_obj(null);
	}
	public boolean GetExpandedArgument(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Pull_str(0);
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		int frame_arg_adj = Scrib_frame_.Get_arg_adj(frame.Frame_tid());
		String idx_str = args.Pull_str(1);
		int idx_int = IntUtl.ParseOr(idx_str, IntUtl.MinValue);	// NOTE: should not receive int value < -1; idx >= 0
		BryWtr tmp_bfr = BryWtr.New();	// NOTE: do not make modular level variable, else random failures; DATE:2013-10-14
		if (idx_int != IntUtl.MinValue) {	// idx is integer
			Arg_nde_tkn nde = Get_arg(frame, idx_int, frame_arg_adj);
			//frame.Args_eval_by_idx(core.Ctx().Src(), idx_int); // NOTE: arg[0] is always MW function name; EX: {{#invoke:Mod_0|Func_0|Arg_1}}; arg_x = "Mod_0"; args[0] = "Func_0"; args[1] = "Arg_1"
			if (nde == null) return rslt.Init_obj(null);	// idx_str does not exist; [null] not []; PAGE:en.w:Sainte-Catherine,_Quebec DATE:2017-09-16
			nde.Val_tkn().Tmpl_evaluate(ctx, src, core.Frame_parent(), tmp_bfr);
			return rslt.Init_obj(tmp_bfr.ToStrAndClear());
		}
		else {
			Arg_nde_tkn nde = frame.Args_get_by_key(src, BryUtl.NewU8(idx_str));
			if (nde == null) return rslt.Init_obj(null);	// idx_str does not exist; [null] not []; PAGE:en.w:Sainte-Catherine,_Quebec DATE:2017-09-16
			nde.Val_tkn().Tmpl_evaluate(ctx, src, core.Frame_parent(), tmp_bfr);
			return rslt.Init_obj(tmp_bfr.ToStrAndClearAndTrim());	// NOTE: must trim if key_exists; DUPE:TRIM_IF_KEY
		}
	}
	private Arg_nde_tkn Get_arg(Xot_invk invk, int idx, int frame_arg_adj) {	// DUPE:MW_ARG_RETRIEVE
		int cur = List_adp_.Base1, len = invk.Args_len() - frame_arg_adj; 
		for (int i = 0; i < len; i++) {	// iterate over list to find nth *non-keyd* arg; SEE:NOTE_1
			Arg_nde_tkn nde = (Arg_nde_tkn)invk.Args_get_by_idx(i + frame_arg_adj);
			if (nde.KeyTkn_exists()) {	// BLOCK:ignore_args_with_empty_keys;
				if (Verify_arg_key(src, idx, nde))
					return nde;
				else
					continue;
			}
			if (idx == cur) return nde;
			else ++cur;
		}
		return null; // return null since index does not exist; EX: args[2] when {{#invoke:mod|test|3=abc}} PAGE:en.w:Sainte-Catherine,_Quebec DATE:2017-09-16
	}
	private static boolean Verify_arg_key(byte[] src, int idx, Arg_nde_tkn nde) {
		// NOTE: key must trim ws; EX: "1 =val_1"; PAGE:c:File:Torsåker_kyrka01.JPG; DATE:2017-10-23
		int key_int = BryFind.NotFound;
		byte[] key_dat_ary = nde.Key_tkn().Dat_ary();
		if (Env_.Mode_testing() && src == null)	// some tests will always pass a null src;
			key_int = BryUtl.ToIntOrTrimWs(key_dat_ary, 0, key_dat_ary.length, BryFind.NotFound);
		else {
			if (BryUtl.IsNullOrEmpty(key_dat_ary))	// should be called by current context;
				key_int = BryUtl.ToIntOrTrimWs(src, nde.Key_tkn().Src_bgn(), nde.Key_tkn().Src_end(), BryFind.NotFound);
			else							// will be called by parent context; note that this calls Xot_defn_tmpl_.Make_itm which sets a key_dat_ary; DATE:2013-09-23
				key_int = BryUtl.ToIntOrTrimWs(key_dat_ary, 0, key_dat_ary.length, BryFind.NotFound);
		}
		if (key_int == BryFind.NotFound)		// key is not-numeric
			return false;
		else								// key is numeric
			return idx == key_int;
	}
	public boolean GetAllExpandedArguments(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Pull_str(0);
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		byte frame_tid = frame.Frame_tid();
		Xot_invk parent_frame = Scrib_frame_.Get_parent(core, frame_tid);
		int frame_arg_adj = Scrib_frame_.Get_arg_adj(frame_tid);
		int args_len = frame.Args_len() - frame_arg_adj;
		if (args_len < 1) return rslt.Init_obj(KeyValUtl.AryEmpty);		// occurs when "frame:getParent().args" but no parent frame
		BryWtr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().GetB128();		// NOTE: do not make modular level variable, else random failures; DATE:2013-10-14
		List_adp rv = List_adp_.New();
		int arg_idx = 0;
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = frame.Args_get_by_idx(i + frame_arg_adj);
			if (nde.KeyTkn_exists()) {	// BLOCK:ignore_args_with_empty_keys;
				byte[] key_dat_ary = nde.Key_tkn().Dat_ary();
				int key_dat_len = BryUtl.Len(key_dat_ary);
				if (Env_.Mode_testing() && src == null)	 {
					if (key_dat_len == 0) continue;
				}
				else {
					if (key_dat_len == 0)	// should be called by current context;
						if (nde.Key_tkn().Src_end() - nde.Key_tkn().Src_bgn() == 0) continue;
				}
			}
			nde.Key_tkn().Tmpl_evaluate(ctx, src, parent_frame, tmp_bfr);
			int key_len = tmp_bfr.Len();
			boolean key_missing = key_len == 0;
			String key_as_str = null; int key_as_int = IntUtl.MinValue;
			boolean key_is_str = false;
			if (key_missing)	// key missing; EX: {{a|val}}
				key_as_int = ++arg_idx;// NOTE: MW requires a key; if none, then default to int index; NOTE: must be int, not String; NOTE: must be indexed to keyless args; EX: in "key1=val1,val2", "val2" must be "1" (1st keyless arg) not "2" (2nd arg); DATE:2013-11-09
			else {				// key exists; EX:{{a|key=val}}
				if (key_len > 0 && tmp_bfr.Bry()[0] != AsciiByte.Num0)	// do not convert zero-padded numbers to int; EX: "01" -> "01" x> 1; PAGE:ru.w:Красноказарменный_проезд; DATE:2016-11-23
					key_as_int = BryUtl.ToIntOr(tmp_bfr.Bry(), 0, tmp_bfr.Len(), IntUtl.MinValue);
				if (key_as_int == IntUtl.MinValue) {		// key is not int; create str
					key_as_str = tmp_bfr.ToStrAndClear();
					key_is_str = true;
				}
				else {									// key is int; must return int for key b/c lua treats table[1] different than table["1"]; DATE:2014-02-13
					tmp_bfr.Clear();					// must clear bfr, else key will be added to val;
				}
			}
//				ctx.Scribunto = BoolUtl.Y; // CHART
			nde.Val_tkn().Tmpl_evaluate(ctx, src, parent_frame, tmp_bfr);
//				ctx.Scribunto = BoolUtl.N;
			String val = key_missing ? tmp_bfr.ToStrAndClear() : tmp_bfr.ToStrAndClearAndTrim(); // NOTE: must trim if key_exists; DUPE:TRIM_IF_KEY
			KeyVal kv = key_is_str ? KeyVal.NewStr(key_as_str, val) : KeyVal.NewInt(key_as_int, val);
			rv.Add(kv);
		}
		tmp_bfr.MkrRls();
		return rslt.Init_obj((KeyVal[])rv.ToAry(KeyVal.class));
	}
	public boolean FrameExists(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Cast_str_or_null(0);
		if (frame_id == null) return rslt.Init_obj(false); // no args should not throw error; PAGE:fr.u:Projet:Laboratoire/Espaces_de_noms/Modèle/Liste_des_pages DATE:2017-05-28
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		return rslt.Init_obj(frame != null);
	}
	public boolean ParentFrameExists(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(!core.Frame_parent().Frame_is_root());
	}
	public boolean Preprocess(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Pull_str(0);
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		byte frame_tid = frame.Frame_tid();
		Xot_invk parent_frame = Scrib_frame_.Get_parent(core, frame_tid);
		String text_str = args.Pull_str(1);
		byte[] text_bry = BryUtl.NewU8(text_str);
		Xop_root_tkn tmp_root = ctx.Tkn_mkr().Root(text_bry);
		Xop_ctx tmp_ctx = Xop_ctx.New__sub__reuse_page(core.Ctx());
		int args_adj = Scrib_frame_.Get_arg_adj(frame_tid);
		int args_len = frame.Args_len() - args_adj;
		KeyVal[] kv_args = new KeyVal[args_len];
		BryWtr tmp_bfr = core.Wiki().Utl__bfr_mkr().GetB512();
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn arg = frame.Args_get_by_idx(i + args_adj);
			arg.Key_tkn().Tmpl_evaluate(ctx, src, frame, tmp_bfr);
			String key = tmp_bfr.ToStrAndClear();
			if (StringUtl.Eq(key, "")) key = IntUtl.ToStr(i);
			arg.Val_tkn().Tmpl_evaluate(ctx, src, parent_frame, tmp_bfr);	// NOTE: must evaluate against parent_frame; evaluating against current frame may cause stack-overflow; DATE:2013-04-04
			String val = tmp_bfr.ToStrAndClear();
			kv_args[i] = KeyVal.NewStr(key, val);
		}
		Xot_invk_mock mock_frame = Xot_invk_mock.preprocess_(BryUtl.NewU8(frame_id), kv_args);	// use frame_id for Frame_ttl; in lieu of a better candidate; DATE:2014-09-21
		tmp_ctx.Parse_tid_(Xop_parser_tid_.Tid__tmpl);	// default xnde names to template; needed for test, but should be in place; DATE:2014-06-27
		byte[] result = cur_wiki.Parser_mgr().Main().Expand_tmpl(tmp_root, tmp_ctx, mock_frame, tmp_ctx.Tkn_mkr(), text_bry);
		return rslt.Init_obj(result);
	}
	public boolean CallParserFunction(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Pull_str(0);
		int frame_tid = Scrib_frame_.Get_frame_tid(frame_id);
		Xot_invk parent_frame = frame_tid == Scrib_frame_.Tid_current ? core.Frame_current() : core.Frame_parent();
		byte[] fnc_name = args.Pull_bry(1);
		int fnc_name_len = fnc_name.length;
		BryRef argx_ref = BryRef.NewEmpty();
		BryRef fnc_name_ref = BryRef.New(fnc_name);
		KeyVal[] parser_func_args = CallParserFunction_parse_args(cur_wiki.Appe().Utl_num_parser(), argx_ref, fnc_name_ref, args.Ary());
		Xot_invk_mock frame = Xot_invk_mock.new_(parent_frame.Defn_tid(), 0, fnc_name, parser_func_args);	// pass something as frame_ttl; choosng fnc_name; DATE:2014-09-21

		Xol_func_itm finder = new Xol_func_itm();	// TS.MEM: DATE:2016-07-12
		cur_wiki.Lang().Func_regy().Find_defn(finder, fnc_name, 0, fnc_name_len);
		Xot_defn defn = finder.Func();

		if (defn == Xot_defn_.Null) throw ErrUtl.NewArgs("callParserFunction: function was not found", "function", StringUtl.NewU8(fnc_name));
		BryWtr bfr = cur_wiki.Utl__bfr_mkr().GetK004();
		Xop_ctx fnc_ctx = Xop_ctx.New__sub__reuse_page(core.Ctx());
		fnc_ctx.Parse_tid_(Xop_parser_tid_.Tid__tmpl);	// default xnde names to template; needed for test, but should be in place; DATE:2014-06-27
		Xot_invk_tkn_.Eval_func(fnc_ctx, src, parent_frame, frame, bfr, defn, argx_ref.Val());
		bfr.MkrRls();
		return rslt.Init_obj(bfr.ToStrAndClear());
	}
	private KeyVal[] CallParserFunction_parse_args(Gfo_number_parser num_parser, BryRef argx_ref, BryRef fnc_name_ref, KeyVal[] args) {
		List_adp rv = List_adp_.New();
		// flatten args
		int args_len = args.length;
		for (int i = 2; i < args_len; i++) {
			KeyVal arg = args[i];
			if (Is_kv_ary(arg)) {
				KeyVal[] arg_kv_ary = (KeyVal[])arg.Val();
				int arg_kv_ary_len = arg_kv_ary.length;
				for (int j = 0; j < arg_kv_ary_len; j++) {
					KeyVal sub_arg = arg_kv_ary[j];
					rv.Add(sub_arg);
				}
			}
			else
				rv.Add(arg);
		}
		rv.SortBy(Scrib_lib_mw_callParserFunction_sorter.Instance);
		// get argx
		byte[] fnc_name = fnc_name_ref.Val();
		int fnc_name_len = fnc_name.length;
		int fnc_name_colon_pos = BryFind.FindFwd(fnc_name, AsciiByte.Colon, 0, fnc_name_len);
		if (fnc_name_colon_pos == BryFind.NotFound) {
			if (rv.Len() > 0) {	// some parser_functions can pass 0 args; PAGE:en.w:Paris EX:{{#coordinates}} DATE:2016-10-12
				KeyVal arg_argx = (KeyVal)rv.GetAt(0);
				argx_ref.ValSet(arg_argx.ValToBry());
				rv.DelAt(0);
			}
		}
		else {
			argx_ref.ValSet(BryLni.Mid(fnc_name, fnc_name_colon_pos + 1, fnc_name_len));
			fnc_name = BryLni.Mid(fnc_name, 0, fnc_name_colon_pos);
			fnc_name_ref.ValSet(fnc_name);
		}
		return (KeyVal[])rv.ToAry(KeyVal.class);
	}
	private static boolean Is_kv_ary(KeyVal kv) {return ClassUtl.EqByObj(KeyVal[].class, kv.Val());}
	public boolean ExpandTemplate(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String ttl_str = args.Pull_str(1);
		byte[] ttl_bry = BryUtl.NewU8(ttl_str);
		Xoa_ttl ttl = Xoa_ttl.Parse(cur_wiki, ttl_bry);	// parse directly; handles titles where template is already part of title; EX: "Template:A"
		if (ttl == null) {
			return rslt.Init_fail("expandTemplate: invalid title \"" + ttl_str + "\"");	// NOTE: must return error if title is invalid; PAGE:en.w:Tetris DATE:2017-04-09
		}
		if (!ttl.ForceLiteralLink() && ttl.Ns().Id_is_main())	// title is not literal and is not prefixed with Template; parse again as template; EX: ":A" and "Template:A" are fine; "A" is parsed again as "Template:A"
			ttl = Xoa_ttl.Parse(cur_wiki, BryUtl.Add(cur_wiki.Ns_mgr().Ns_template().Name_db_w_colon(), ttl_bry));	// parse again, but add "Template:"
		KeyVal[] args_ary = args.Pull_kv_ary_safe(2);

		byte[] sub_src = null;
		// ttl is template; check tmpl_regy first before going to data_mgr
		if (ttl.Ns().Id_is_tmpl()) {
			Xot_defn_tmpl tmpl = (Xot_defn_tmpl)core.Wiki().Cache_mgr().Defn_cache().Get_by_key(ttl.Page_db());
			if (tmpl != null)
				sub_src = tmpl.Data_raw();
		}

		// ttl is not in template cache; load title
		if (sub_src == null)
			sub_src = core.Wiki().Cache_mgr().Page_cache().Get_src_else_load_or_null(ttl);

		// ttl does not exist; fail
		if (sub_src == null)
			return rslt.Init_fail("expandTemplate: template \"" + ttl_str + "\" does not exist");	// NOTE: must return error if template is missing; PAGE:en.w:Flag_of_Greenland DATE:2016-05-02

		Xot_invk_mock sub_frame = Xot_invk_mock.new_(core.Frame_current().Defn_tid(), 0, ttl.Full_txt(), args_ary);	// NOTE: (1) must have ns (Full); (2) must be txt (space, not underscore); EX:Template:Location map+; DATE:2014-09-21
		Xot_defn_tmpl transclude_tmpl = ctx.Wiki().Parser_mgr().Main().Parse_text_to_defn_obj(ctx, ctx.Tkn_mkr(), ttl.Ns(), ttl.Page_db(), sub_src);
		BryWtr sub_bfr = cur_wiki.Utl__bfr_mkr().GetK004();
		transclude_tmpl.Tmpl_evaluate(ctx, sub_frame, sub_bfr);
		return rslt.Init_obj(sub_bfr.ToStrAndRls());
	}

	public boolean IncrementExpensiveFunctionCount(Scrib_proc_args args, Scrib_proc_rslt rslt) {return rslt.Init_obj(KeyValUtl.AryEmpty);}	// NOTE: for now, always return null (XOWA does not care about expensive parser functions)
	public boolean IsSubsting(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		boolean is_substing = false;
		Xot_invk current_frame = core.Frame_current();
		if	(current_frame != null && Xot_defn_.Tid_is_substing(current_frame.Defn_tid()))		// check current frame first
			is_substing = true;
		else {																				// check owner frame next
			Xot_invk parent_frame = core.Frame_parent();
			if (parent_frame != null && Xot_defn_.Tid_is_substing(parent_frame.Defn_tid()))
				is_substing = true;
		}			
		return rslt.Init_obj(is_substing);
	}
	public boolean NewChildFrame(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Ordered_hash frame_list = core.Frame_created_list();
		int frame_list_len = frame_list.Len();
		if (frame_list_len > 100) throw ErrUtl.NewArgs("newChild: too many frames");
		String frame_id = args.Pull_str(0);
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		Object ttl_obj = args.Cast_obj_or_null(1);	// NOTE: callers must pass named title else title will be false; EX: frame:newChild{'current', 'title0'} -> false; frame:newChild{'current', title='title0'} -> 'title0'; DATE:2014-05-20
		Xoa_ttl ttl = null;
		if (ClassUtl.TypeByObj(ttl_obj) != String.class) {	 // title = false
			byte[] ttl_bry = frame.Frame_ttl();
			ttl = Xoa_ttl.Parse(core.Wiki(), ttl_bry);
		}
		else {
			ttl = Xoa_ttl.Parse(cur_wiki, BryUtl.NewU8((String)ttl_obj));
			if (ttl == null) throw ErrUtl.NewArgs("newChild: invalid title", "title", (String)ttl_obj);
		}
		KeyVal[] args_ary = args.Pull_kv_ary_safe(2);
		Xot_invk_mock new_frame = Xot_invk_mock.new_(core.Frame_current().Defn_tid(), 0, ttl.Full_txt(), args_ary); // NOTE: use spaces, not unders; REF.MW:$frame->getTitle()->getPrefixedText(); DATE:2014-08-14
		String new_frame_id = "frame" + IntUtl.ToStr(frame_list_len);
		frame_list.Add(new_frame_id, new_frame);
		return rslt.Init_obj(new_frame_id);
	}
	public boolean GetFrameTitle(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String frame_id = args.Pull_str(0);
		Xot_invk frame = Scrib_frame_.Get_frame(core, frame_id);
		return rslt.Init_obj(frame.Frame_ttl());
	}
	public boolean SetTTL(Scrib_proc_args args, Scrib_proc_rslt rslt) { // needed for {{cite web}} PAGE:en.w:A DATE:2014-07-12
		int timeToLive = args.Pull_int(0);
		Xot_invk current_frame = core.Frame_current();
		current_frame.Frame_lifetime_(timeToLive);
		return rslt.Init_ary_empty();
	}
}
class Scrib_lib_mw_callParserFunction_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		KeyVal lhs = (KeyVal)lhsObj;
		KeyVal rhs = (KeyVal)rhsObj;

		// handle null kv; PAGE:en.w:Abziri DATE:2017-11-29
		if		(lhs == null && rhs == null)
			return CompareAbleUtl.Same;
		else if (lhs == null)
			return CompareAbleUtl.More;
		else if (rhs == null)
			return CompareAbleUtl.Less;

		Object lhs_key = lhs.KeyAsObj();
		Object rhs_key = rhs.KeyAsObj();
		boolean lhs_is_int = ClassUtl.Eq(lhs_key.getClass(), IntUtl.ClsRefType);
		boolean rhs_is_int = ClassUtl.Eq(rhs_key.getClass(), IntUtl.ClsRefType);
		if (lhs_is_int != rhs_is_int)									// different types (int vs String or String vs int)
			return lhs_is_int ? CompareAbleUtl.Less : CompareAbleUtl.More;	// sort ints before strings
		if (lhs_is_int)													// both are ints
			return IntUtl.Compare(IntUtl.Cast(lhs_key), IntUtl.Cast(rhs_key));
		else															// both are strings
			return StringUtl.Compare(StringUtl.Cast(lhs_key), StringUtl.Cast(rhs_key));
	}
	public static final Scrib_lib_mw_callParserFunction_sorter Instance = new Scrib_lib_mw_callParserFunction_sorter(); Scrib_lib_mw_callParserFunction_sorter() {}
}
