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
import gplx.xowa.bldrs.*; import gplx.xowa.wikis.caches.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.xtns.pfuncs.ifs.*;
public class Xobc_parse_run extends Xob_itm_basic_base implements Xob_cmd, GfoInvkAble {
	public Xobc_parse_run(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY;} public static final String KEY = "parse.run";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		Xow_ns_mgr_.rebuild_(wiki.Lang(), wiki.Ns_mgr());	// rebuild; different lang will change namespaces; EX: de.wikisource.org will have Seite for File and none of {{#lst}} will work
		ctx = wiki.Ctx(); parser = wiki.Parser();
		msg_log = ctx.Msg_log();
		log_dump = new Xoad_wtr_dump(bldr.App().Fsys_mgr().Temp_dir().GenSubDir("parser_dump"));
		root = ctx.Tkn_mkr().Root(Bry_.Empty);
		img_dump = new Xobc_lnki_wkr_file(bldr, wiki);
		ctx.Lnki().File_wkr_(img_dump);
		math_dump = new Xobc_xnde_math_dump(bldr, wiki);
		math_dump.Wkr_bgn(bldr);
		ctg_dump = new Xobc_lnki_wkr_ctg(bldr, wiki);
		ctx.Xnde().File_wkr_(math_dump);
		ctx.Tmpl_load_enabled_(false);
		if (tmpl_on) Tmpl_load(wiki);
		img_dump.Wkr_bgn(bldr);
		cur_ns = wiki.Ns_mgr().Ns_main();
		redirect_mgr = wiki.Redirect_mgr();
		Xot_invk_tkn.Cache_enabled = true;
		bldr.App().Wiki_mgr().Wdata_mgr().Enabled_(false);
	}	private Xop_parser parser; Xop_ctx ctx; Gfo_msg_log msg_log; Xoad_wtr_dump log_dump; Xop_root_tkn root;
	public boolean Tmpl_on() {return tmpl_on;} public Xobc_parse_run Tmpl_on_(boolean v) {tmpl_on = v; return this;} private boolean tmpl_on;
	Xobc_lnki_wkr_file img_dump; Xobc_xnde_math_dump math_dump; Xobc_lnki_wkr_ctg ctg_dump;
	public void Cmd_run() {
		raw_parser.Init(bldr.Usr_dlg(), wiki, load_len);
//			compress_max = 50;
//			Parse_ns(wiki.Ns_mgr().Get_by_id(Xow_ns_.Id_help));
		compress_max = 10;
		Parse_ns(wiki.Ns_mgr().Ns_main());
		compress_max = 5;
		Parse_ns(wiki.Ns_mgr().Ns_category());
		compress_max = 20;
		Parse_ns(wiki.Ns_mgr().Ns_template());
//			ConsoleAdp._.WriteLine(Int_.XtoStr(Pfunc_ifexist.Count));
	}
	public void Cmd_end() {
		img_dump.Wkr_end();
		math_dump.Wkr_end();
		wiki.Cache_mgr().Free_mem_all();
	}
	public void Cmd_print() {}
	private void Parse_ns(Xow_ns ns) {
		cur_ns = ns;
		Io_url ns_dir = wiki.Fsys_mgr().Ns_dir().GenSubDir_nest(ns.Num_str(), Xow_dir_info_.Name_page);
		raw_parser.Init_ns(ns);
		ns_is_tmpl = ns.Id() == Xow_ns_.Id_template;
		Parse_dir(ns_dir);
		Free();
		log_dump.Flush();
	}	boolean ns_is_tmpl; int compress_max = 20, compress_idx = 0; Xow_ns cur_ns;
	private void Parse_dir(Io_url dir) {
		Io_url[] itms = Io_mgr._.QueryDir_args(dir).DirInclude_().ExecAsUrlAry();
		bldr.Usr_dlg().Prog_many(GRP_KEY, "load_dir", "loading dir: ~{0}", dir.NameAndExt());
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; i++) {
			Io_url itm = itms[i];
			if (itm.Type_dir()) Parse_dir(itm);
			else				Parse_fil(itm);
		}
	}
	private void Parse_fil(Io_url fil) {
//			if (String_.Compare(fil.NameOnly(), "0000000927") <= CompareAble_.Same) return;
		raw_parser.Reset_one(fil);
		while (raw_parser.Read(raw_page)) {
			Parse_page(raw_page);
		}
		if (++compress_idx >= compress_max) {
			Free();
			compress_idx = 0;
//				Io_mgr._.AppendFilStr("C:\\dump.txt", Xop_xnde_wkr.TEMP_TIMELINE.XtoStrAndClear());
		}
	}	private Xodb_page_raw_parser raw_parser = new Xodb_page_raw_parser(); Xodb_page raw_page = new Xodb_page();
	private void Free() {
		ctx.App().Free_mem(true);
		gplx.xowa.xtns.scribunto.Scrib_core.Core_invalidate();
		Env_.GarbageCollect();
//            Tfds.Write(ctx.App().Tmpl_result_cache().Count());
	}
	public void Parse_page(Xodb_page xml_page) {
		byte[] src = xml_page.Text(), ttl_bry = xml_page.Ttl_w_ns();
//			Io_mgr._.AppendFilStr("C:\\dump.txt", String_.new_utf8_(ttl_bry) + "\n");
//			if (String_.Eq(String_.new_utf8_(ttl_bry), "en:Iowa"))
//                Tfds.Write();
//			Tfds.Write(String_.new_utf8_(ttl_bry));
		if (cur_ns.Id() != Xow_ns_.Id_main)	// add ns to ttl_bry; EX: A -> Category:A
			ttl_bry = Bry_.Add(cur_ns.Name_db_w_colon(), ttl_bry);
		ctx.Cur_page().Ttl_(Xoa_ttl.parse_(wiki, ttl_bry));
		try {
			msg_log.Clear();
			if (ns_is_tmpl)
				parser.Parse_text_to_defn_obj(ctx, ctx.Tkn_mkr(), wiki.Ns_mgr().Ns_template(), ttl_bry, src);
			else {
				parser.Parse_page_all_clear(root, ctx, ctx.Tkn_mkr(), src);
				root.Clear();
			}
			pageCount++;
			redirect_mgr.Extract_redirect(src, src.length);
			if (msg_log.Ary_len() > 0) {
				pageError++;
				log_dump.Write(ttl_bry, pageCount, ctx.Msg_log());
			}
			if (ctx.Wiki().Cache_mgr().Tmpl_result_cache().Count() > 50000) {
//                    sm.Write_info("clearing tmpls");
				ctx.Wiki().Cache_mgr().Tmpl_result_cache().Clear();
			}
			ctx.App().Utl_bry_bfr_mkr().Clear_fail_check();
		}
		catch (Exception exc) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "parse", "failed to parse ~{0} error ~{1}", ctx.Cur_page().Url().X_to_full_str_safe(), Err_.Message_gplx_brief(exc));
			ctx.App().Utl_bry_bfr_mkr().Clear();
		}
	}	private Xop_redirect_mgr redirect_mgr;
	private void Tmpl_load(Xow_wiki wiki) {
		Xodb_page_raw_parser raw_parser = new Xodb_page_raw_parser();
		Io_url dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_parse_dump_templates.KEY, "dump");
		Io_url[] urls = Io_mgr._.QueryDir_fils(dir);
		if (urls.length == 0) {
			bldr.Usr_dlg().Note_many(GRP_KEY, "tmpl_load", "no templates found: ~{0}", dir.Raw());
			return;
		}
		Xow_ns ns_tmpl = wiki.Ns_mgr().Ns_template();
		raw_parser.Load(bldr.Usr_dlg(), wiki, ns_tmpl, urls, load_len);
		Xodb_page xml_page = new Xodb_page();
		Xow_defn_cache tmpl_regy = wiki.Cache_mgr().Defn_cache();
		while (raw_parser.Read(xml_page)) {
			Xot_defn_tmpl defn = new Xot_defn_tmpl();
			defn.Init_by_new(ns_tmpl, xml_page.Ttl_w_ns(), xml_page.Text(), null, false);	// NOTE: passing null, false; will be overriden later when Parse is called
			tmpl_regy.Add(defn, ns_tmpl.Case_match() );
		}
		bldr.Usr_dlg().Prog_none(GRP_KEY, "done", "tmpl_load done");
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_templates_on_))				tmpl_on = m.ReadBoolOrFalse("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_templates_on_ = "templates_on_";
	public int Load_len() {return load_len;} public Xobc_parse_run Load_len_(int v) {load_len = v; return this;} private int load_len = Io_mgr.Len_mb;
	public static int pageCount; int pageError;
	static final String GRP_KEY = "xowa.bldr.parse";
}
