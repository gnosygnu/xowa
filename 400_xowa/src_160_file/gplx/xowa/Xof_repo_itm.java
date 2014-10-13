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
import gplx.core.btries.*;
public class Xof_repo_itm implements GfoInvkAble {
	public Xof_repo_itm(Xoa_repo_mgr mgr, byte[] key) {this.mgr = mgr; this.key = key;} private Xoa_repo_mgr mgr;
	public byte[] Key() {return key;} private byte[] key;
	public boolean Wmf_fsys() {return wmf_fsys;} public Xof_repo_itm Wmf_fsys_(boolean v) {wmf_fsys = v; return this;} private boolean wmf_fsys;
	public boolean Wmf_api() {return wmf_api;} public Xof_repo_itm Wmf_api_(boolean v) {wmf_api = v; return this;} private boolean wmf_api;
	public boolean Tarball() {return tarball;} public Xof_repo_itm Tarball_(boolean v) {tarball = v; return this;} private boolean tarball;
	public byte Dir_spr() {return dir_spr;} private byte dir_spr;
	public byte[] Root() {return root;} private byte[] root;
	public String Root_str() {return root_str;} private String root_str;
	public Io_url Root_url() {return root_url;} Io_url root_url;
	public boolean Fsys_is_wnt() {return fsys_is_wnt;} public Xof_repo_itm Fsys_is_wnt_(boolean v) {fsys_is_wnt = v; return this;} private boolean fsys_is_wnt;
	public byte[] Root_http() {return root_http;} private byte[] root_http = Bry_.Empty;
	public byte[] Wiki_key() {return wiki_key;} public Xof_repo_itm Wiki_key_(byte[] v) {wiki_key = v; return this;} private byte[] wiki_key;
	public int Ttl_max() {return ttl_max;} public Xof_repo_itm Ttl_max_(int v) {ttl_max = v; return this;} private int ttl_max = 180;
	public byte[][] Mode_names() {return mode_names;} private byte[][] mode_names = new byte[][] {Mode_names_key[0], Mode_names_key[1]};
	public static final byte[][] Mode_names_key = new byte[][] {Bry_.new_utf8_("orig"), Bry_.new_utf8_("thumb")};
	public Xoft_rule_grp Ext_rules() {return ext_rules;} public Xof_repo_itm Ext_rules_(Xoft_rule_grp v) {ext_rules = v; return this;} private Xoft_rule_grp ext_rules;
	public int Dir_depth() {return dir_depth;} public Xof_repo_itm Dir_depth_(int v) {dir_depth = v; return this;} private int dir_depth = 4;
	public boolean Primary() {return primary;} public Xof_repo_itm Primary_(boolean v) {primary = v; return this;} private boolean primary;
	public Xof_repo_itm Root_str_(String v) {
		this.root_str = v;
		root = Bry_.new_utf8_(root_str);
		wmf_fsys = String_.HasAtBgn(root_str, "http") || String_.HasAtBgn(root_str, "ftp");
		if (wmf_fsys) {
			root_url = Io_url_.Null;
			dir_spr = Byte_ascii.Slash;
			wmf_api = true;
		}
		else {
			root_url = App_cmd_arg.Val_as_url_rel_url_or(root_str, mgr.App().Fsys_mgr().File_dir(), Io_url_.new_dir_(root_str), true);
			root = root_url.RawBry();
			dir_spr = root_url.Info().DirSpr_byte();
			root_http = mgr.App().Encoder_mgr().Fsys().Encode_http(root_url);
		}
		return this;
	}
	public static byte[] Ttl_invalid_fsys_chars(byte[] ttl) {
		int ttl_len = ttl.length;
		for (int i = 0; i < ttl_len; i++) {
			byte b = ttl[i];
			Object o = wnt_trie.Match_bgn_w_byte(b, ttl, i, ttl_len);
			if (o == null)		wnt_tmp_bfr.Add_byte(b);		// regular char; add orig byte
			else				wnt_tmp_bfr.Add((byte[])o);		// invalid char; add swap byte(s)
		}
		return wnt_tmp_bfr.Xto_bry_and_clear();
	}	private static final Bry_bfr wnt_tmp_bfr = Bry_bfr.reset_(255); private static final Btrie_slim_mgr wnt_trie = trie_make();
	public static byte[] Ttl_shorten_ttl(int ttl_max, byte[] ttl, byte[] md5, Xof_ext ext) {
		byte[] rv = ttl;
		int exceed_len = rv.length - ttl_max;
		if (exceed_len > 0) {
			wnt_tmp_bfr.Add_mid(rv, 0, ttl_max - 33);							// add truncated title;		33=_.length + md5.length
			wnt_tmp_bfr.Add_byte(Byte_ascii.Underline);							// add underline;			EX: "_"
			wnt_tmp_bfr.Add(md5);												// add md5;					EX: "abcdefghijklmnopqrstuvwxyz0123456"
			wnt_tmp_bfr.Add_byte(Byte_ascii.Dot);								// add dot;					EX: "."
			wnt_tmp_bfr.Add(ext.Ext());											// add ext;					EX: ".png"
			rv = wnt_tmp_bfr.Xto_bry_and_clear();
		}
		return rv;
	}
	public byte[] Gen_name_src(byte[] name) {
		if (!fsys_is_wnt || wmf_fsys) return name;
		int name_len = name.length;
		for (int i = 0; i < name_len; i++) {
			byte b = name[i];
			Object o = trie.Match_bgn_w_byte(b, name, i, name_len);
			if (o == null)		tmp_bfr.Add_byte(b);
			else				tmp_bfr.Add((byte[])o);
		}
		byte[] rv = tmp_bfr.Xto_bry_and_clear();
		return rv;
	}	private Bry_bfr tmp_bfr = Bry_bfr.reset_(300); Btrie_slim_mgr trie = trie_make();
	private static Btrie_slim_mgr trie_make() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs_();
		byte[] invalid = Op_sys.Wnt.Fsys_invalid_chars();
		byte[] underline = new byte[] {Byte_ascii.Underline};
		int len = invalid.length;
		for (int i = 0; i < len; i++)
			rv.Add_obj(new byte[] {invalid[i]}, underline);
		return rv;
	}
	public byte[] Gen_name_trg(byte[] bry, byte[] md5, Xof_ext ext) {
		byte[] rv = Gen_name_src(bry);
		byte[] ext_bry = ext.Ext();
		int exceed_len = rv.length - ttl_max;
		if (exceed_len > 0) {
			tmp_bfr.Add_mid(rv, 0, ttl_max - 33);							// add truncated title;		33=_.length + md5.length
			tmp_bfr.Add_byte(Byte_ascii.Underline);							// add underline;			EX: "_"
			tmp_bfr.Add(md5);												// add md5;					EX: "abcdefghijklmnopqrstuvwxyz0123456"
			tmp_bfr.Add_byte(Byte_ascii.Dot);
			tmp_bfr.Add(ext_bry);
			rv = tmp_bfr.Xto_bry_and_clear();
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))				return mgr;
		else if	(ctx.Match(k, Invk_fsys_))				fsys_is_wnt = String_.Eq(m.ReadStr("v"), "wnt");
		else if	(ctx.Match(k, Invk_primary_))			primary = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_ext_rules_))			Ext_rules_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_wmf_api_))			wmf_api = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_tarball_))			tarball = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_fsys_ = "fsys_", Invk_ext_rules_ = "ext_rules_", Invk_primary_ = "primary_", Invk_wmf_api_ = "wmf_api_", Invk_tarball_ = "tarball_";
	public Xof_repo_itm Ext_rules_(byte[] ext_rules_key) {ext_rules = mgr.App().File_mgr().Ext_rules().Get_or_new(ext_rules_key); return this;}
	public static final int Thumb_default_null = -1;
	public static final byte Mode_orig = 0, Mode_thumb = 1, Mode_nil = Byte_.MaxValue_127;
	public static final byte Repo_remote = 0, Repo_local = 1, Repo_unknown = 126, Repo_null = Byte_.MaxValue_127;
	public static final int Dir_depth_null = -1, Dir_depth_wmf = 2, Dir_depth_xowa = 4;
}
