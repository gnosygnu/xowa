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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.btries.*; import gplx.core.consoles.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.exts.*;	
import gplx.xowa.wikis.domains.*;
public class Xof_repo_itm implements GfoInvkAble {
	private final Xoa_fsys_mgr app_fsys; private final Xof_rule_mgr ext_rule_mgr; private final Bry_bfr tmp_bfr = Bry_bfr.reset_(300);
	public Xof_repo_itm(byte[] key, Xoa_fsys_mgr app_fsys, Xof_rule_mgr ext_rule_mgr, byte[] wiki_domain) {
		this.key = key; this.app_fsys = app_fsys; this.ext_rule_mgr = ext_rule_mgr;
		Wiki_domain_(wiki_domain);
	}
	public byte[]	Key()				{return key;} private final byte[] key;
	public byte[]	Wiki_domain()		{return wiki_domain;} private byte[] wiki_domain;
	public byte[]	Wiki_abrv_xo()		{return wiki_abrv_xo;} private byte[] wiki_abrv_xo;
	public byte[]	Root_bry()			{return root_bry;} private byte[] root_bry;
	public byte[]	Root_http()			{return root_http;} private byte[] root_http = Bry_.Empty;
	public byte		Dir_spr()			{return dir_spr;} private byte dir_spr;
	public boolean		Fsys_is_wnt() {return fsys_is_wnt;} public Xof_repo_itm Fsys_is_wnt_(boolean v) {fsys_is_wnt = v; return this;} private boolean fsys_is_wnt;
	public boolean		Wmf_fsys() {return wmf_fsys;} public Xof_repo_itm Wmf_fsys_(boolean v) {wmf_fsys = v; return this;} private boolean wmf_fsys;
	public boolean		Wmf_api() {return wmf_api;} public Xof_repo_itm Wmf_api_(boolean v) {wmf_api = v; return this;} private boolean wmf_api;
	public boolean		Tarball() {return tarball;} public Xof_repo_itm Tarball_(boolean v) {tarball = v; return this;} private boolean tarball;
	public byte[][] Mode_names() {return mode_names;} private byte[][] mode_names = new byte[][] {Xof_repo_itm_.Mode_names_key[0], Xof_repo_itm_.Mode_names_key[1]};
	public int		Dir_depth() {return dir_depth;} public Xof_repo_itm Dir_depth_(int v) {dir_depth = v; return this;} private int dir_depth = 4;
	public Xof_rule_grp Ext_rules() {return ext_rules;} private Xof_rule_grp ext_rules;
	public Xof_repo_itm Ext_rules_(byte[] ext_rules_key) {ext_rules = ext_rule_mgr.Get_or_new(ext_rules_key); return this;}
	public boolean		Primary() {return primary;} public Xof_repo_itm Primary_(boolean v) {primary = v; return this;} private boolean primary;
	public void		Wiki_domain_(byte[] v) {
		this.wiki_domain = v;
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(v);
		if (domain_itm == null) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "repo:invalid domain; raw=~{0}", v);
			this.wiki_abrv_xo = Bry_.Empty;
		}
		else
			this.wiki_abrv_xo = Xow_abrv_xo_.To_bry(v, domain_itm.Lang_actl_key(), domain_itm.Domain_type());
	}
	public Xof_repo_itm Root_str_(String root_str) {
		this.wmf_fsys = String_.Has_at_bgn(root_str, "http") || String_.Has_at_bgn(root_str, "ftp");
		if (wmf_fsys) {
			this.root_bry = Bry_.new_u8(root_str);
			this.dir_spr = Byte_ascii.Slash;
			this.wmf_api = true;
		}
		else {
			Io_url root_url = Gfo_cmd_arg_itm_.Val_as_url__rel_url_or(root_str, Bool_.Y, app_fsys.File_dir(), Io_url_.new_dir_(root_str));
			this.root_bry = root_url.RawBry();
			this.dir_spr = root_url.Info().DirSpr_byte();
			this.root_http = root_url.To_http_file_bry();
		}
		return this;
	}
	public byte[] Gen_name_src(byte[] name) {
		if (!fsys_is_wnt || wmf_fsys) return name;
		return Xof_repo_itm_.Ttl_invalid_fsys_chars(tmp_bfr, name);
	}
	public byte[] Gen_name_trg(byte[] bry, byte[] md5, Xof_ext ext) {
		byte[] rv = Gen_name_src(bry);
		return Xof_repo_itm_.Ttl_shorten_ttl(tmp_bfr, rv, ttl_max_len, md5, ext.Ext());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))				throw Err_.new_unimplemented_w_msg("deprecated repo_itm.owner");
		else if	(ctx.Match(k, Invk_fsys_))				fsys_is_wnt = String_.Eq(m.ReadStr("v"), "wnt");
		else if	(ctx.Match(k, Invk_primary_))			primary = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_ext_rules_))			Ext_rules_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_wmf_api_))			wmf_api = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_tarball_))			tarball = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_owner = "owner", Invk_fsys_ = "fsys_", Invk_ext_rules_ = "ext_rules_", Invk_primary_ = "primary_", Invk_wmf_api_ = "wmf_api_", Invk_tarball_ = "tarball_";
	private static final int ttl_max_len = 180;
}
