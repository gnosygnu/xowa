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
public class Cfg_nde_root implements GfoInvkAble {
	public int Root_len() {return root.Nde_subs_len();}
	public Cfg_nde_obj Root_get_at(int i) {return (Cfg_nde_obj)root.Nde_subs_get_at(i);} private Ordered_hash grps = Ordered_hash_.new_bry_();
	public Cfg_nde_obj Root_get(byte[] key) {return (Cfg_nde_obj)root.Nde_subs_get(key);}
	public Cfg_nde_obj Grps_get(byte[] key) {return (Cfg_nde_obj)grps.Get_by(key);}
	public Cfg_nde_root Root_(Cfg_nde_obj obj, byte[] typ, byte[][] atrs) {root = obj; grp_type = typ; grp_atrs = atrs; return this;} Cfg_nde_obj root; byte[] grp_type; byte[][] grp_atrs;
	public void Root_subs_del(byte[] grp_key, byte[] itm_key) {
		Cfg_nde_obj grp = (Cfg_nde_obj)grps.Get_by(grp_key); if (grp == null) return;
		grp.Nde_subs_del(itm_key);
	}
	public void Root_subs_add(byte[] grp_key, byte[] itm_typ, byte[] itm_key, byte[][] itm_atrs) {
		Cfg_nde_obj grp = (Cfg_nde_obj)grps.Get_by(grp_key);
		if (grp == null) {
			if (Bry_.Len_eq_0(grp_key))
				grp = root;
			else {
				grp = (Cfg_nde_obj)root.Nde_subs_make(grp_type, grp_key, grp_atrs);
				root.Nde_subs_add(grp_key, grp);
				grps.Add(grp_key, grp);
			}
		}
		Cfg_nde_obj itm = grp.Nde_subs_get(itm_key);
		if (itm == null) {
			itm = (Cfg_nde_obj)grp.Nde_subs_make(itm_typ, itm_key, itm_atrs);
			grp.Nde_subs_add(itm_key, itm);
			if (itm.Nde_typ_is_grp())	// created itm is grp; add to root's main registry of grps
				grps.Add(itm_key, itm);
		}
		else
			if (itm_atrs.length > 0) itm.Nde_atrs_set(itm_atrs);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set_bulk))			Set_bulk(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set_bulk = "set_bulk";
	public void Set_bulk(byte[] src) {
		int src_len = src.length, src_pos = 0, fld_bgn = 0, fld_idx = 0;
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		boolean fail = false;
		byte cur_cmd = Byte_.Zero;
		byte[] cur_grp_key = null, cur_itm_typ = null, cur_itm_key = null;
		List_adp cmds = List_adp_.new_();
		while (true) {
			boolean last = src_pos == src_len;
			byte b = last ? Byte_ascii.NewLine : src[src_pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:
							fail = true;
							if (src_pos - fld_bgn == 1) {
								byte cmd_byte = src[src_pos - 1];
								cur_cmd = Byte_.Zero;
								switch (cmd_byte) {
									case Byte_ascii.Plus:	cur_cmd = Cfg_nde_cmd.Cmd_add; fail = false; break;
									case Byte_ascii.Dash:	cur_cmd = Cfg_nde_cmd.Cmd_del; fail = false; break;
								}
							}
							if (fail) throw Err_mgr._.fmt_(GRP_KEY, "set_bulk.fld.cmd_invalid", "cmd is invalid: ~{0}", String_.new_u8(src, fld_bgn, src_pos));
							break;
						case 1: cur_grp_key = csv_parser.Load(src, fld_bgn, src_pos); break;
						case 2: cur_itm_typ = csv_parser.Load(src, fld_bgn, src_pos); break;
						case 3: cur_itm_key = csv_parser.Load(src, fld_bgn, src_pos); break;
					}
					++fld_idx;
					fld_bgn = src_pos + 1;
					break;
				case Byte_ascii.NewLine:
					if (!(fld_idx == 0 && fld_bgn == src_pos)) {
						Cfg_nde_cmd cmd = null;
						switch (cur_cmd) {
							case Cfg_nde_cmd.Cmd_add:
								byte[][] cur_itm_atrs = Bry_.Ary_empty;
								if		(fld_idx == 4) {
									byte[] cur_itm_atrs_raw = csv_parser.Load(src, fld_bgn, src_pos);
									cur_itm_atrs = Bry_.Split(cur_itm_atrs_raw, Byte_ascii.Tilde);
								}
								else if (fld_idx == 3) {
									cur_itm_key = csv_parser.Load(src, fld_bgn, src_pos);
								}
								cmd = new Cfg_nde_cmd(cur_cmd, cur_grp_key, cur_itm_typ, cur_itm_key, cur_itm_atrs);
								break;
							case Cfg_nde_cmd.Cmd_del:
								cur_itm_key = csv_parser.Load(src, fld_bgn, src_pos);
								cmd = new Cfg_nde_cmd(cur_cmd, cur_grp_key, Bry_.Empty, cur_itm_key, Bry_.Ary_empty);
								break;
						}
						cmds.Add(cmd);
					}
					cur_cmd = Byte_.Zero;
					cur_grp_key = cur_itm_typ = cur_itm_key = null;
					fld_idx = 0;
					fld_bgn = src_pos + 1;
					break;
			}
			if (last) break;
			++src_pos;
		}
		int len = cmds.Count();
		for (int i = 0; i < len; i++) {
			Cfg_nde_cmd cmd = (Cfg_nde_cmd)cmds.Get_at(i);
			switch (cmd.Cmd()) {
				case Cfg_nde_cmd.Cmd_add: Root_subs_add(cmd.Grp_key(), cmd.Itm_typ(), cmd.Itm_key(), cmd.Itm_atrs()); break;
				case Cfg_nde_cmd.Cmd_del: Root_subs_del(cmd.Grp_key(), cmd.Itm_key()); break;
			}
		}
	}
	static final String GRP_KEY = "gplx.cfg.root";
}
class Cfg_nde_cmd {
	public Cfg_nde_cmd(byte cmd, byte[] grp_key, byte[] itm_typ, byte[] itm_key, byte[][] itm_atrs) {this.cmd = cmd; this.grp_key = grp_key; this.itm_typ = itm_typ; this.itm_key = itm_key; this.itm_atrs = itm_atrs;}
	public byte Cmd() {return cmd;} private byte cmd;
	public byte[] Grp_key() {return grp_key;} private byte[] grp_key;
	public byte[] Itm_typ() {return itm_typ;} private byte[] itm_typ;
	public byte[] Itm_key() {return itm_key;} private byte[] itm_key;
	public byte[][] Itm_atrs() {return itm_atrs;} private byte[][] itm_atrs;
	public static final byte Cmd_add = 1, Cmd_del = 2; 
}
