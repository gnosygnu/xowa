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
import gplx.core.brys.*; import gplx.xowa.wikis.nss.*;
public class Xot_defn_tmpl implements Xot_defn {
	private boolean onlyinclude_parsed = false;
	public byte Defn_tid() {return Xot_defn_.Tid_tmpl;}
	public boolean Defn_require_colon_arg() {return false;}
	public int Cache_size() {return data_raw.length;}
	public byte[] Name() {return name;} private byte[] name; private byte[] full_name;
	public byte[] Frame_ttl() {return frame_ttl;} public void Frame_ttl_(byte[] v) {frame_ttl = v;} private byte[] frame_ttl;
	public byte[] Data_raw() {return data_raw;} private byte[] data_raw;
	public byte[] Data_mid() {return data_mid;} public Xot_defn_tmpl Data_mid_(byte[] v) {data_mid = v; return this;} private byte[] data_mid;
	public Xop_ctx Ctx() {return ctx;} public Xot_defn_tmpl Ctx_(Xop_ctx v) {ctx = v; return this;} private Xop_ctx ctx;
	public Xop_root_tkn Root() {return root;} private Xop_root_tkn root;
	public void Init_by_new(Xow_ns ns, byte[] name, byte[] data_raw, Xop_root_tkn root, boolean onlyInclude) {
		this.ns = ns; this.name = name; this.data_raw = data_raw; this.root = root; this.onlyInclude_exists = onlyInclude;
		ns_id = ns.Id();
		this.full_name = ns.Gen_ttl(name);
	}	private Xow_ns ns; int ns_id;
	public void Init_by_raw(Xop_root_tkn root, boolean onlyInclude_exists) {
		this.root = root; this.onlyInclude_exists = onlyInclude_exists;
	}
	byte[] Extract_onlyinclude(byte[] src, Bry_bfr_mkr bfr_mkr) {
		Bry_bfr bfr = bfr_mkr.Get_m001();
		int pos = 0;
		int src_len = src.length;
		while (true) {
			int find_bgn = Bry_find_.Find_fwd(src, Bry_onlyinclude_bgn, pos, src_len);
			if (find_bgn == Bry_find_.Not_found) {
				break;
			}
			int find_bgn_lhs = find_bgn + Bry_onlyinclude_bgn_len;
			int find_end = Bry_find_.Find_fwd(src, Bry_onlyinclude_end, find_bgn_lhs, src_len);
			if (find_end == Bry_find_.Not_found) {
				break;
			}
			bfr.Add_mid(src, find_bgn_lhs, find_end);
			pos = find_end + Bry_onlyinclude_end_len;
		}
		return bfr.To_bry_and_rls();
	}
	private static final    byte[] Bry_onlyinclude_bgn = Bry_.new_a7("<onlyinclude>"), Bry_onlyinclude_end = Bry_.new_a7("</onlyinclude>");
	private static int Bry_onlyinclude_bgn_len = Bry_onlyinclude_bgn.length, Bry_onlyinclude_end_len = Bry_onlyinclude_end.length;
	public void Rls() {
		if (root != null) root.Clear();
		root = null;
	}
	public void Parse_tmpl(Xop_ctx ctx) {ctx.Wiki().Parser_mgr().Main().Parse_text_to_defn(this, ctx, ctx.Tkn_mkr(), ns, name, data_raw);}
	public boolean Tmpl_evaluate(Xop_ctx ctx, Xot_invk caller, Bry_bfr bfr) {
		if (root == null) Parse_tmpl(ctx);
		Xoae_page page = ctx.Page();
		if (!ctx.Wiki().Parser_mgr().Tmpl_stack_add(full_name)) {
			bfr.Add_str_a7("<!-- template loop detected:" + gplx.langs.htmls.Gfh_utl.Escape_html_as_str(String_.new_u8(name)) + " -->");
			Xoa_app_.Usr_dlg().Log_many("", "", "template loop detected: url=~{0} name=~{1}", ctx.Page().Url().To_str(), name);
			return false;
		}
		boolean rv = true;
		if (onlyInclude_exists) {
			Xowe_wiki wiki = ctx.Wiki();
			if (!onlyinclude_parsed) {
				onlyinclude_parsed = true;
				byte[] new_data = Extract_onlyinclude(data_raw, wiki.Utl__bfr_mkr());
				Xop_ctx new_ctx = Xop_ctx.New__sub(wiki, ctx, page);	// COMMENT:changed from ctx.Page() to page; DATE:2016-07-11
				Xot_defn_tmpl tmpl = wiki.Parser_mgr().Main().Parse_text_to_defn_obj(new_ctx, new_ctx.Tkn_mkr(), wiki.Ns_mgr().Ns_template(), Bry_.Empty, new_data);
				tmpl.Root().Tmpl_compile(new_ctx, new_data, Xot_compile_data.Noop);
				data_raw = new_data;
				root = tmpl.Root();
			}
		}
		int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			boolean result = root.Subs_get(i).Tmpl_evaluate(ctx, data_raw, caller, bfr);
			if (!result) rv = false;
		}
		ctx.Wiki().Parser_mgr().Tmpl_stack_del();
		return rv;
	}
	public Xot_defn Clone(int id, byte[] name) {throw Err_.new_unimplemented();}
	boolean onlyInclude_exists;
}
