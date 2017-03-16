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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.consoles.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.wikis.domains.*;
public class Xof_repo_itm implements Gfo_invk {
	private final    Xoa_fsys_mgr app_fsys; private final    Xof_rule_mgr ext_rule_mgr;
	public Xof_repo_itm(byte[] key, Xoa_fsys_mgr app_fsys, Xof_rule_mgr ext_rule_mgr, byte[] wiki_domain) {
		this.key = key; this.app_fsys = app_fsys; this.ext_rule_mgr = ext_rule_mgr;
		Wiki_domain_(wiki_domain);
	}
	public byte[]			Key()				{return key;}			private final    byte[] key;			// EX: "src_http_commons"
	public byte				Tid()				{return tid;}			private byte tid;						// EX: Xof_repo_tid_.Tid__remote
	public byte[]			Wiki_domain()		{return wiki_domain;}	private byte[] wiki_domain;				// EX: "commons.wikimedia.org"
	public byte[]			Wiki_abrv_xo()		{return wiki_abrv_xo;}	private byte[] wiki_abrv_xo;			// EX: "c"
	public byte				Dir_spr()			{return dir_spr;}		private byte dir_spr;					// EX: "/"
	public int				Dir_depth()			{return dir_depth;}		private int dir_depth = 4;				// EX: "/1/2/3/4" vs "/1/2"
	public byte[]			Root_bry()			{return root_bry;}		private byte[] root_bry;				// EX:
	public byte[]			Root_http()			{return root_http;}		private byte[] root_http = Bry_.Empty;	// EX: 
	public String			Root_str()			{return root_str;}		private String root_str;
	public boolean			Fsys_is_wnt()		{return fsys_is_wnt;}	private boolean fsys_is_wnt;
	public boolean			Shorten_ttl()		{return shorten_ttl;}	private boolean shorten_ttl = true;
	public boolean			Wmf_fsys()			{return wmf_fsys;}		private boolean wmf_fsys;
	public boolean			Wmf_api()			{return wmf_api;}		private boolean wmf_api;
	public boolean			Tarball()			{return tarball;}		private boolean tarball;
	public byte[][]			Mode_names()		{return mode_names;}	private byte[][] mode_names = new byte[][] {Xof_img_mode_.Names_ary[0], Xof_img_mode_.Names_ary[1]};
	public Xof_rule_grp		Ext_rules()			{return ext_rules;}		private Xof_rule_grp ext_rules;
	public boolean			Primary()			{return primary;}		private boolean primary;
	public void             Url_max_len_(int v) {url_max_len = v;}      private int url_max_len = 250;

	public Xof_repo_itm		Fsys_is_wnt_(boolean v) {fsys_is_wnt = v; return this;} 
	public Xof_repo_itm		Shorten_ttl_(boolean v) {shorten_ttl = v; return this;} 
	public Xof_repo_itm		Wmf_fsys_(boolean v) {wmf_fsys = v; return this;} 
	public Xof_repo_itm		Wmf_api_(boolean v) {wmf_api = v; return this;}
	public Xof_repo_itm		Tarball_(boolean v) {tarball = v; return this;} 
	public Xof_repo_itm		Ext_rules_(byte[] ext_rules_key) {ext_rules = ext_rule_mgr.Get_or_new(ext_rules_key); return this;}
	public Xof_repo_itm		Dir_depth_(int v) {dir_depth = v; return this;} 
	public Xof_repo_itm		Primary_(boolean v) {primary = v; return this;} 

	public void				Wiki_domain_(byte[] v) {
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
		this.root_str = root_str;
		return this;
	}
	public byte[] Gen_name_src(Bry_bfr tmp_bfr, byte[] name) {
		if (!fsys_is_wnt || wmf_fsys) return name;
		return Xof_itm_ttl_.Remove_invalid(tmp_bfr, name);
	}
	public byte[] Gen_name_trg(Bry_bfr tmp_bfr, byte[] bry, byte[] md5, Xof_ext ext) {
		byte[] rv = Gen_name_src(tmp_bfr, bry);
		if (shorten_ttl) {
			int max = url_max_len;
			if (fsys_is_wnt) {
				max = url_max_len          // 250 is approximate max of windows path
					- root_bry.length      // EX: "C:\xowa\"
					- 5                    // EX: "file\"
					- 6                    // EX: "thumb\"
					- dir_depth * 2        // EX: "0\1\2\3\"; *2  is for "\"
					- 17                   // 17 is length of "\1234px@1234-1234"
					- ext.Ext().length + 1 // EX: ".png"; +1 is for "."
					;
			}
			else {
				max = 180; // legacy val of max title; can probably be higher
			}
			return Xof_itm_ttl_.Shorten(tmp_bfr, rv, max, md5, ext.Ext());
		}
		else {
			return rv;
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))				throw Err_.new_unimplemented_w_msg("deprecated repo_itm.owner");
		else if	(ctx.Match(k, Invk_fsys_))				fsys_is_wnt = String_.Eq(m.ReadStr("v"), "wnt");
		else if	(ctx.Match(k, Invk_primary_))			primary = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_ext_rules_))			Ext_rules_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_wmf_api_))			wmf_api = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_tarball_))			tarball = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_tid_))				tid = Xof_repo_tid_.By_str(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_owner = "owner", Invk_fsys_ = "fsys_", Invk_ext_rules_ = "ext_rules_", Invk_primary_ = "primary_", Invk_wmf_api_ = "wmf_api_", Invk_tarball_ = "tarball_", Invk_tid_ = "tid_";
}
