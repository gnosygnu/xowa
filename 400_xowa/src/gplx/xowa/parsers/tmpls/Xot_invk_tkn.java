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
import gplx.core.envs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.funcs.*;
import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.xtns.pfuncs.ttls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.caches.*; import gplx.xowa.wikis.data.tbls.*;		
public class Xot_invk_tkn extends Xop_tkn_itm_base implements Xot_invk {
	public Xot_invk_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return typeId;} private byte typeId = Xop_tkn_itm_.Tid_tmpl_invk;
	public void Tkn_tid_to_txt() {typeId = Xop_tkn_itm_.Tid_txt;}
	public Arg_nde_tkn Name_tkn() {return name_tkn;} public Xot_invk_tkn Name_tkn_(Arg_nde_tkn v) {name_tkn = v; return this;} Arg_nde_tkn name_tkn = Arg_nde_tkn.Null;
	public byte Defn_tid() {return defn_tid;} private byte defn_tid = Xot_defn_.Tid_null;
	public int Tmpl_subst_bgn() {return tmpl_subst_bgn;} private int tmpl_subst_bgn;
	public int Tmpl_subst_end() {return tmpl_subst_end;} private int tmpl_subst_end;
	public Xot_invk_tkn Tmpl_subst_props_(byte tid, int bgn, int end) {defn_tid = tid; tmpl_subst_bgn = bgn; tmpl_subst_end = end; return this;}
	public Xot_defn Tmpl_defn() {return tmpl_defn;} public Xot_invk_tkn Tmpl_defn_(Xot_defn v) {tmpl_defn = v; return this;} private Xot_defn tmpl_defn = Xot_defn_.Null;
	public boolean Frame_is_root() {return false;}
	public byte Frame_tid() {return scrib_tid;} public void Frame_tid_(byte v) {scrib_tid = v;} private byte scrib_tid;
	public byte[] Frame_ttl() {return frame_ttl;} public void Frame_ttl_(byte[] v) {frame_ttl = v;} private byte[] frame_ttl;
	public int Frame_lifetime() {return frame_lifetime;} public void Frame_lifetime_(int v) {frame_lifetime = v;} private int frame_lifetime;
	public boolean Rslt_is_redirect() {return rslt_is_redirect;} public void Rslt_is_redirect_(boolean v) {rslt_is_redirect = v;} private boolean rslt_is_redirect;
	@Override public void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr) {fmtr.Reg_tmpl(ctx, src, name_tkn, args_len, args);}
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		name_tkn.Tmpl_compile(ctx, src, prep_data);
		int args_len = this.Args_len();
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = args[i];
			Xop_tkn_itm key = nde.Key_tkn(); int key_subs_len = key.Subs_len();
			for (int j = 0; j < key_subs_len; j++)
				key.Subs_get(j).Tmpl_compile(ctx, src, prep_data);
			Xop_tkn_itm val = nde.Val_tkn(); int val_subs_len = val.Subs_len();
			for (int j = 0; j < val_subs_len; j++)
				val.Subs_get(j).Tmpl_compile(ctx, src, prep_data);
		}
	}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {	// this="{{t|{{{0}}}}}" caller="{{t|1}}"
		// init common
		boolean rv = false;
		Xowe_wiki wiki = ctx.Wiki();
		Xol_lang_itm lang = wiki.Lang();

		// init defn / name
		Xot_defn defn = tmpl_defn; 
		byte[] name_ary = defn.Name();
		byte[] name_ary_orig = Bry_.Empty;
		int name_bgn = 0, name_ary_len = 0; 
		Arg_itm_tkn name_key_tkn = name_tkn.Key_tkn();

		// init more
		byte[] argx_ary = Bry_.Empty;
		boolean subst_found = false;
		boolean name_had_subst = false;
		boolean template_prefix_found = false;

		// tmpl_name does not exist in db; may be dynamic, subst, transclusion, etc..
		if (defn == Xot_defn_.Null) {
			// dynamic tmpl; EX:{{{{{1}}}|a}}
			if (name_key_tkn.Itm_static() == Bool_.N_byte) {
				Bry_bfr name_tkn_bfr = Bry_bfr_.New_w_size(name_tkn.Src_end() - name_tkn.Src_bgn());
				if (defn_tid == Xot_defn_.Tid_subst)
					name_tkn_bfr.Add(Get_first_subst_itm(lang.Kwd_mgr()));
				name_tkn.Tmpl_evaluate(ctx, src, caller, name_tkn_bfr);
				name_ary = name_tkn_bfr.To_bry_and_clear();
			}
			// tmpl is static; note that dat_ary is still valid but rest of name may not be; EX: {{subst:name{{{1}}}}}
			else
				name_ary = Bry_.Mid(src, name_key_tkn.Dat_bgn(), name_key_tkn.Dat_end());
			name_had_subst = name_key_tkn.Dat_ary_had_subst();
			name_ary_orig = name_ary;	// cache name_ary_orig
			name_ary_len = name_ary.length;
			name_bgn = Bry_find_.Find_fwd_while_not_ws(name_ary, 0, name_ary_len);
			if (	name_ary_len == 0			// name is blank; can occur with failed inner tmpl; EX: {{ {{does not exist}} }}
				||	name_bgn == name_ary_len	// name is ws; EX: {{test| }} -> {{{{{1}}}}}is whitespace String; PAGE:en.d:wear_one's_heart_on_one's_sleeve; EX:{{t+|fr|avoir le cœur sur la main| }}
				) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "parser.tmpl:dynamic is blank; page=~{0}", ctx.Page().Url_bry_safe()); // downgraded from warning to note; PAGE:de.d:país DATE:2016-09-07
				return false;
			}
			if 		(name_ary[name_bgn] == Byte_ascii.Colon) {							// check 1st letter for transclusion
				return Transclude(ctx, wiki, bfr, Bool_.N, name_ary, caller, src);		// transclusion; EX: {{:Name of page}}
			}

			// ignore "{{Template:"; EX: {{Template:a}} is the same thing as {{a}}
			int tmpl_ns_len = wiki.Ns_mgr().Tmpls_get_w_colon(name_ary, name_bgn, name_ary_len);
			if (tmpl_ns_len != Bry_find_.Not_found) {
				name_ary = Bry_.Mid(name_ary, name_bgn + tmpl_ns_len, name_ary_len);
				name_ary_len = name_ary.length;
				name_bgn = 0;
				template_prefix_found = true;
			}
			byte[] ns_template_prefix = wiki.Ns_mgr().Ns_template().Name_db_w_colon(); int ns_template_prefix_len = ns_template_prefix.length;
			if (name_ary_len > ns_template_prefix_len && Bry_.Match(name_ary, name_bgn, name_bgn + ns_template_prefix_len, ns_template_prefix)) {
				name_ary = Bry_.Mid(name_ary, name_bgn + ns_template_prefix_len, name_ary_len);
				name_ary_len = name_ary.length;
				name_bgn = 0;
			}

			Object ns_eval = wiki.Ns_mgr().Names_get_w_colon(name_ary, 0, name_ary_len);	// match {{:Portal or {{:Wikipedia
			if (ns_eval != null && !template_prefix_found)									// do not transclude ns if Template prefix seen earlier; EX: {{Template:Wikipedia:A}} should not transclude "Wikipedia:A"; DATE:2013-04-03
				return SubEval(ctx, wiki, bfr, name_ary, caller, src);

			Xol_func_itm finder = new Xol_func_itm();	// TS.MEM: DATE:2016-07-12
			lang.Func_regy().Find_defn(finder, name_ary, name_bgn, name_ary_len);
			defn = finder.Func();
			int finder_tid = finder.Tid();
			int finder_colon_pos = finder.Colon_pos();
			int finder_subst_end = finder.Subst_end();

			int colon_pos = -1;
			switch (finder_tid) {
				case Xot_defn_.Tid_subst:	// subst is added verbatim; EX: {{subst:!}} -> {{subst:!}}; logic below is to handle printing of arg which could be standardized if src[] was available for tmpl
					bfr.Add(Xop_curly_bgn_lxr.Hook).Add(name_ary);
					for (int i = 0; i < args_len; i++) {
						Arg_nde_tkn nde = args[i];
						bfr.Add_byte(Byte_ascii.Pipe);						// add |
						bfr.Add_mid(src, nde.Src_bgn(), nde.Src_end());		// add entire arg; "k=v"; note that src must be added, not evaluated, else <nowiki> may be dropped and cause stack overflow; PAGE:ru.w:Близкие_друзья_(Сезон_2) DATE:2014-10-21
					}
					Xot_fmtr_prm.Instance.Print(bfr);
					bfr.Add(Xop_curly_end_lxr.Hook);
					return true;				// NOTE: nothing else to do; return
				case Xot_defn_.Tid_safesubst:
					name_ary = Bry_.Mid(name_ary, finder_subst_end, name_ary_len);			// chop off "safesubst:"
					name_ary_len = name_ary.length;
					if (defn != Xot_defn_.Null) {	// func found
						if (finder_colon_pos != -1) colon_pos = defn.Name().length;			// set colon_pos; SEE NOTE_1
					}
					subst_found = true;
					break;
				case Xot_defn_.Tid_func:
					if (defn.Defn_require_colon_arg()) {
						colon_pos =  Bry_find_.Find_fwd(name_ary, Byte_ascii.Colon);
						if (colon_pos == Bry_find_.Not_found)
							defn = Xot_defn_.Null;
					}						
					else {
						colon_pos = finder_colon_pos;
					}
					break;
				case Xot_defn_.Tid_raw:
				case Xot_defn_.Tid_msg:
					int raw_colon_pos = Bry_find_.Find_fwd(name_ary, Byte_ascii.Colon);
					if (raw_colon_pos == Bry_find_.Not_found) {}										// colon missing; EX: {{raw}}; noop and assume template name; DATE:2014-02-11
					else {																				// colon present;
						name_ary = Bry_.Mid(name_ary, finder_subst_end + 1, name_ary_len);				// chop off "raw"; +1 is for ":"; note that +1 is in bounds b/c raw_colon was found
						name_ary_len = name_ary.length;
						Object ns_eval2 = wiki.Ns_mgr().Names_get_w_colon(name_ary, 0, name_ary_len);	// match {{:Portal or {{:Wikipedia
						if (ns_eval2 != null) {
							Xow_ns ns_eval_itm = (Xow_ns)ns_eval2;
							int pre_len = ns_eval_itm.Name_enc().length;
							if (pre_len < name_ary_len && name_ary[pre_len] == Byte_ascii.Colon)
								return SubEval(ctx, wiki, bfr, name_ary, caller, src);
						}
					}
					switch (finder_tid) {
						case Xot_defn_.Tid_msg:
							defn = Xot_defn_.Null;	// null out defn to force template load below; DATE:2014-07-10
							break;
					}
					break;
			}
			if (subst_found) {// subst found; remove Template: if it exists; EX: {{safesubst:Template:A}} -> {{A}} not {{Template:A}}; EX:en.d:Kazakhstan; DATE:2014-03-25
				ns_template_prefix = wiki.Ns_mgr().Ns_template().Name_db_w_colon(); ns_template_prefix_len = ns_template_prefix.length;
				if (name_ary_len > ns_template_prefix_len && Bry_.Match(name_ary, name_bgn, name_bgn + ns_template_prefix_len, ns_template_prefix)) {
					name_ary = Bry_.Mid(name_ary, name_bgn + ns_template_prefix_len, name_ary_len);
					name_ary_len = name_ary.length;
					name_bgn = 0;
					template_prefix_found = true;
				}
			}
			if (colon_pos != -1) {	// func; separate name_ary into name_ary and arg_x
				argx_ary = Bry_.Trim(name_ary, colon_pos + 1, name_ary_len);	// trim bgn ws; needed for "{{formatnum:\n{{#expr:2}}\n}}"
				name_ary = Bry_.Mid(name_ary, 0, colon_pos);
			}
			if (defn == Xot_defn_.Null) {
				if (ctx.Tid_is_popup()) {	// popup && cur_tmpl > tmpl_max
					if (Popup_skip(ctx, name_ary, bfr)) return false;
				}
				defn = wiki.Cache_mgr().Defn_cache().Get_by_key(name_ary);
				if (defn == null) {
					if (name_ary_len != 0 ) {	// name_ary_len != 0 for direct template inclusions; PAGE:en.w:Human evolution and {{:Human evolution/Species chart}}; && ctx.Tmpl_whitelist().Has(name_ary)
						Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.Add(wiki.Ns_mgr().Ns_template().Name_db_w_colon(), name_ary));
						if (ttl == null) { // ttl is not valid; just output orig; REF.MW:Parser.php|braceSubstitution|if ( !$found ) $text = $frame->virtualBracketedImplode( '{{', '|', '}}', $titleWithSpaces, $args );
							if (subst_found || template_prefix_found) {	// if "subst:" or "Template:" found, use orig name; DATE:2014-03-31
								bfr.Add(Xop_curly_bgn_lxr.Hook).Add(name_ary_orig).Add(Xop_curly_end_lxr.Hook);
								return false;
							}								
							else {// output entire tmpl_src WITH args; used to output name only which broke pages; PAGE:en.w:Flag_of_Greenland; DATE:2016-06-21
								bfr.Add(Xop_curly_bgn_lxr.Hook);
								bfr.Add(name_ary);
								for (int i = 0; i < args_len; ++i) {
									Arg_nde_tkn nde = this.Args_get_by_idx(i);
									bfr.Add_byte(Byte_ascii.Pipe);
									// must evaluate args; "size={{{size|}}}" must become "size="; PAGE:en.w:Europe; en.w:Template:Country_data_Guernsey DATE:2016-10-13
									nde.Tmpl_compile(ctx, src, Xot_compile_data.Noop);
									nde.Tmpl_evaluate(ctx, src, caller, bfr);
								}
								bfr.Add(Xop_curly_end_lxr.Hook);
								return false;
							}
						}
						else {	// some templates produce null ttls; EX: "Citation needed{{subst"
							defn = wiki.Cache_mgr().Defn_cache().Get_by_key(ttl.Page_db());
							if (defn == null && ctx.Tmpl_load_enabled())
								defn = Xot_invk_tkn_.Load_defn(wiki, ctx, this, ttl, name_ary);
						}
					}
				}
				if (defn == null) defn = Xot_defn_.Null;
			}
		}
		if (	defn.Defn_tid() == Xot_defn_.Tid_null		// name is not a known defn
			&&	lang.Vnt_mgr().Enabled()) {					// lang has vnts
			Xowd_page_itm page = lang.Vnt_mgr().Convert_mgr().Convert_ttl(wiki, wiki.Ns_mgr().Ns_template(), name_ary);
			if (page != Xowd_page_itm.Null) {
				name_ary = page.Ttl_page_db();
				Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.Add(wiki.Ns_mgr().Ns_template().Name_db_w_colon(), name_ary));
				if (ttl == null) { // ttl is not valid; just output orig; REF.MW:Parser.php|braceSubstitution|if ( !$found ) $text = $frame->virtualBracketedImplode( '{{', '|', '}}', $titleWithSpaces, $args );
					bfr.Add(Xop_curly_bgn_lxr.Hook).Add(name_ary).Add(Xop_curly_end_lxr.Hook);
					return false;
				}
				defn = wiki.Cache_mgr().Defn_cache().Get_by_key(name_ary);
				if (defn == null && ctx.Tmpl_load_enabled())
					defn = Xot_invk_tkn_.Load_defn(wiki, ctx, this, ttl, name_ary);
				if (defn == null) defn = Xot_defn_.Null;
			}
		}
		Xot_defn_trace trace = ctx.Defn_trace(); int trg_bgn = bfr.Len();
		switch (defn.Defn_tid()) {
			case Xot_defn_.Tid_null:	// defn is unknown
				if (ignore_hash.Get_by_bry(name_ary) == null) {
					if (Pfunc_rel2abs.Rel2abs_ttl(name_ary, name_bgn, name_ary_len)) {// rel_path; EX: {{/../Peer page}}; DATE:2013-03-27
						Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
						name_ary = Pfunc_rel2abs.Rel2abs(tmp_bfr, wiki.Parser_mgr().Rel2abs_ary(), Bry_.Mid(name_ary, name_bgn, name_ary_len), ctx.Page().Ttl().Raw());
						tmp_bfr.Mkr_rls();
						return SubEval(ctx, wiki, bfr, name_ary, caller, src);				
					}
					if (subst_found)
						return Transclude(ctx, wiki, bfr, template_prefix_found, name_ary, caller, src);
					Xot_invk_tkn_.Print_not_found__w_template(bfr, wiki.Ns_mgr(), name_ary);
					return false;
				}
				break;
			case Xot_defn_.Tid_func:
				try {
					Xot_invk_tkn_.Eval_func(ctx, src, caller, this, bfr, defn, argx_ary);
					rv = true;
				}	catch (Exception e) {
					if (Env_.Mode_testing()) 
						throw Err_.new_exc(e, "xo", "failed to evaluate function", "page", ctx.Page().Ttl().Full_txt_w_ttl_case(), "defn", defn.Name(), "src", String_.new_u8(src, this.Src_bgn(), this.Src_end()));
					else {
						wiki.Appe().Usr_dlg().Warn_many("", "", "failed to evaluate function: page=~{0} defn=~{1} src=~{2} err=~{3}", ctx.Page().Ttl().Full_txt_w_ttl_case(), defn.Name(), Bry_.Replace_nl_w_tab(src, this.Src_bgn(), this.Src_end()), Err_.Message_gplx_log(e));
						rv = false;
					}
				}
				break;
			default:
				Xot_defn_tmpl defn_tmpl = (Xot_defn_tmpl)defn;
				if (defn_tmpl.Root() == null) defn_tmpl.Parse_tmpl(ctx);	// NOTE: defn_tmpl.Root can be null after clearing out cache; must be non-null else will fail in trace; DATE:2013-02-01
				Xot_invk invk_tmpl = Xot_defn_tmpl_.CopyNew(ctx, defn_tmpl, this, caller, src, name_ary);
				invk_tmpl.Frame_ttl_(defn_tmpl.Frame_ttl());	// set frame_ttl; needed for redirects; PAGE:en.w:Statutory_city; DATE:2014-08-22
				trace.Trace_bgn(ctx, src, name_ary, caller, invk_tmpl, defn);

				Bry_bfr rslt_bfr = wiki.Utl__bfr_mkr().Get_k004();
				try {
					Xot_invk_tkn_.Bld_key(invk_tmpl, name_ary, rslt_bfr);
					byte[] rslt_key = rslt_bfr.To_bry_and_clear();
					Object o = wiki.Cache_mgr().Tmpl_result_cache().Get_by(rslt_key);
					Xopg_tmpl_prepend_mgr prepend_mgr = ctx.Page().Tmpl_prepend_mgr().Bgn(bfr);
					if (o != null) {
						byte[] rslt = (byte[])o;
						prepend_mgr.End(ctx, bfr, rslt, rslt.length, Bool_.Y);
						bfr.Add(rslt);
					}
					else {
						rv = defn_tmpl.Tmpl_evaluate(Xop_ctx.New__sub(wiki, ctx, ctx.Page()), invk_tmpl, rslt_bfr); // create new ctx so __NOTOC__ only applies to template, not page; PAGE:de.w:13._Jahrhundert DATE:2017-06-17
						prepend_mgr.End(ctx, bfr, rslt_bfr.Bfr(), rslt_bfr.Len(), Bool_.Y);
						if (name_had_subst) {	// current invk had "subst:"; parse incoming invk again to remove effects of subst; PAGE:pt.w:Argentina DATE:2014-09-24
							byte[] tmp_src = rslt_bfr.To_bry_and_clear();
							rslt_bfr.Add(wiki.Parser_mgr().Main().Expand_tmpl(tmp_src));	// this could be cleaner / more optimized
						}
						if (Cache_enabled) {
							byte[] rslt_val = rslt_bfr.To_bry_and_clear();
							bfr.Add(rslt_val);
							Hash_adp cache = wiki.Cache_mgr().Tmpl_result_cache();
							cache.Del(rslt_key);
							cache.Add(rslt_key, rslt_val);
						}
						else
							bfr.Add_bfr_and_clear(rslt_bfr);
					}
					trace.Trace_end(trg_bgn, bfr);
				} finally {rslt_bfr.Mkr_rls();}
				break;
		}
		return rv;
	}
	private boolean Popup_skip(Xop_ctx ctx, byte[] ttl, Bry_bfr bfr) {
		boolean skip = false;
		skip = this.Src_end() - this.Src_bgn() > ctx.Tmpl_tkn_max();
		if (!skip) {
			gplx.xowa.htmls.modules.popups.keeplists.Xop_keeplist_wiki tmpl_keeplist = ctx.Tmpl_keeplist();
			if (tmpl_keeplist != null && tmpl_keeplist.Enabled()) {
				byte[] ttl_lower = Xoa_ttl.Replace_spaces(ctx.Wiki().Lang().Case_mgr().Case_build_lower(ttl));
				skip = !tmpl_keeplist.Match(ttl_lower);
				// if (skip) Tfds.Write_bry(ttl_lower);
			}
		}
		if (skip) {
			bfr.Add(gplx.xowa.parsers.miscs.Xop_comm_lxr.Xowa_skip_comment_bry); // add comment tkn; need something to separate ''{{lang|la|Ragusa}}'' else will become ''''; PAGE:en.w:Republic_of_Ragusa; DATE:2014-06-28
			return true; 
		}
		else
			return false;
	}
	private boolean Transclude(Xop_ctx ctx, Xowe_wiki wiki, Bry_bfr bfr, boolean template_prefix_found, byte[] name_ary, Xot_invk caller, byte[] src) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, name_ary); if (page_ttl == null) return false;	// ttl not valid; EX: {{:[[abc]]}}
		byte[] transclude_src = null;
		if (page_ttl.Ns().Id_is_tmpl()) {							// ttl is template; check tmpl_regy first before going to data_mgr
			Xot_defn_tmpl tmpl = (Xot_defn_tmpl)wiki.Cache_mgr().Defn_cache().Get_by_key(page_ttl.Page_db());
			if (tmpl != null) transclude_src = tmpl.Data_raw();
		}
		if (transclude_src == null && ctx.Tmpl_load_enabled()) {	// ttl is template not in cache, or some other ns; do load
			Xow_page_cache_itm cache_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm(page_ttl);
			if (	cache_itm != null
//					&&  Bry_.Eq(cache_itm.Ttl().Full_db(), ctx.Page().Page_ttl().Full_db())	// make sure that transcluded item is not same as page_ttl; DATE:2014-01-10
				) {
				transclude_src = cache_itm.Wtxt__direct();
				page_ttl = cache_itm.Ttl();
			}
		}
		if (transclude_src !=  null) {
			// NOTE: must use new page, not current, else transcluded page can cause inconsistent TOC state; PAGE:de.w:Game_of_Thrones DATE:2016-11-21
			Xot_defn_tmpl transclude_tmpl = ctx.Wiki().Parser_mgr().Main().Parse_text_to_defn_obj(Xop_ctx.New__sub(wiki, ctx, Xoae_page.New(wiki, page_ttl)), ctx.Tkn_mkr(), page_ttl.Ns(), page_ttl.Page_db(), transclude_src);
			return Eval_sub(ctx, transclude_tmpl, caller, src, bfr);
		}
		else {				
			Xot_invk_tkn_.Print_not_found__by_transclude(bfr, wiki.Ns_mgr(), template_prefix_found, name_ary);
			return false;
		}
	}
	private boolean Eval_sub(Xop_ctx ctx, Xot_defn_tmpl transclude_tmpl, Xot_invk caller, byte[] src, Bry_bfr doc) {
		boolean rv = false;
		Xot_invk tmp_tmpl = Xot_defn_tmpl_.CopyNew(ctx, transclude_tmpl, this, caller, src, transclude_tmpl.Name());
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Xopg_tmpl_prepend_mgr prepend_mgr = ctx.Page().Tmpl_prepend_mgr().Bgn(doc);
		rv = transclude_tmpl.Tmpl_evaluate(ctx, tmp_tmpl, tmp_bfr);
		prepend_mgr.End(ctx, doc, tmp_bfr.Bfr(), tmp_bfr.Len(), Bool_.Y);
		doc.Add_bfr_and_clear(tmp_bfr);
		return rv;
	}
	private boolean SubEval(Xop_ctx ctx, Xowe_wiki wiki, Bry_bfr bfr, byte[] name_ary, Xot_invk caller, byte[] src_for_tkn) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, name_ary); if (page_ttl == null) return false;	// ttl not valid; EX: {{:[[abc]]}}
		Xot_defn_tmpl transclude_tmpl = null;
		switch (page_ttl.Ns().Id()) {
			case Xow_ns_.Tid__template:	// ttl is template not in cache, or some other ns; do load
				Xot_defn_tmpl tmpl = (Xot_defn_tmpl)wiki.Cache_mgr().Defn_cache().Get_by_key(page_ttl.Page_db());
				if (tmpl != null) {
					if (tmpl.Root() == null) tmpl.Parse_tmpl(ctx);
					transclude_tmpl = tmpl;
				}
				break;
			case Xow_ns_.Tid__special:
				bfr.Add(Xop_tkn_.Lnki_bgn).Add_byte(Byte_ascii.Colon).Add(name_ary).Add(Xop_tkn_.Lnki_end);
				return true;
		}
		if (transclude_tmpl == null && ctx.Tmpl_load_enabled()) {	// ttl is template not in cache, or some other ns; do load
			Xow_page_cache_itm cache_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm(page_ttl);
			if (	cache_itm != null) {
				if (!Bry_.Eq(cache_itm.Ttl().Full_db(), ctx.Page().Ttl().Full_db())) {	// make sure that transcluded item is not same as page_ttl; DATE:2014-01-10
					transclude_tmpl = ctx.Wiki().Parser_mgr().Main().Parse_text_to_defn_obj(ctx, ctx.Tkn_mkr(), page_ttl.Ns(), page_ttl.Page_db(), cache_itm.Wtxt__direct());
					page_ttl = cache_itm.Ttl();
				}
			}
		}
		if (transclude_tmpl == null) {
			bfr.Add(Xop_tkn_.Lnki_bgn).Add(name_ary).Add(Xop_tkn_.Lnki_end);		// indicate template was not found; DATE:2014-02-12
			return false;
		}
		return Eval_sub(ctx, transclude_tmpl, caller, src_for_tkn, bfr);
	}
	public int Args_len() {return args_len;} private int args_len = 0;
	public Arg_nde_tkn Args_get_by_idx(int idx) {return args[idx];}
	public Arg_nde_tkn Args_eval_by_idx(byte[] src, int idx) {
		int cur = 0;
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = args[i];
			if (nde.KeyTkn_exists()) continue;
			if (cur++ == idx) return nde;
		}
		return null;
	}
	public Arg_nde_tkn Args_get_by_key(byte[] src, byte[] key) {
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = args[i];
			if (!nde.KeyTkn_exists()) continue;
			if (Bry_.Match(src, nde.Key_tkn().Dat_bgn(), nde.Key_tkn().Dat_end(), key)) return nde;	// NOTE: dat_ary is guaranteed to exist
		}
		return null;
	}
	public void Args_add(Xop_ctx ctx, Arg_nde_tkn arg) {
		int newLen = args_len + 1;
		if (newLen > args_max) {
			args_max = newLen * 2;
			args = Resize(args, args_len, args_max);
		}
		args[args_len] = arg;
		arg.Tkn_grp_(this, args_len);
		args_len = newLen;
	}	Arg_nde_tkn[] args = Arg_nde_tkn.Ary_empty; int args_max;
	private Arg_nde_tkn[] Resize(Arg_nde_tkn[] src, int cur_len, int new_len) {
		Arg_nde_tkn[] rv = new Arg_nde_tkn[new_len];
		Array_.Copy_to(src, 0, rv, 0, cur_len);
		return rv;
	}
	private byte[] Get_first_subst_itm(Xol_kwd_mgr kwd_mgr) {
		Xol_kwd_grp grp = kwd_mgr.Get_at(Xol_kwd_grp_.Id_subst); if (grp == null) return Bry_.Empty;
		Xol_kwd_itm[] itms = grp.Itms();
		return itms.length == 0 ? Bry_.Empty : itms[0].Val();
	}
	private static final    Hash_adp_bry ignore_hash = Hash_adp_bry.ci_a7().Add_str_obj("Citation needed{{subst", "").Add_str_obj("Clarify{{subst", "");	// ignore SafeSubst templates
	public static boolean Cache_enabled = false;
}
/*
NOTE_1: if (finder.Colon_pos() != -1) colon_pos = finder.Func().Name().length;
Two issues here:
1)	"if (finder.Colon_pos() != -1)"
	Colon_pos can either be -1 or >0
	EX: -1: safesubst:NAMESPACE
	EX: >0: safesubst:#expr:0
	if -1, don't do anything; this will leave colon_pos as -1 
2)	"finder.Func().Name().length"
	Colon_pos is >0, but refers to String before it was chopped
	EX: "safesubst:#expr:0" has Colon_pos of 15 but String is now "#expr:0"
	so, get colon_pos by taking the finder.Func().Name().length
	NOTE: whitespace does not matter b/c "safesubst: #expr:0" would never be a func;
*/