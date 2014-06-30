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
package gplx.xowa.bldrs.oimgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xob_bmk_mgr implements GfoInvkAble {
	private Xodb_xowa_cfg_tbl cfg_tbl; private Db_stmt cfg_stmt;
	private String cfg_grp;
	public byte Repo_prv() {return repo_prv;} private byte repo_prv;
	public int Ns_prv() {return ns_prv;} private int ns_prv;
	public byte[] Ttl_prv() {return ttl_prv;} private byte[] ttl_prv;
	private boolean repo_enable, ns_enable, ttl_enable;
	private boolean repo_dirty, ns_dirty, ttl_dirty;
	public Xob_bmk_mgr Init(Db_provider p, String grp, boolean repo_enable, boolean ns_enable, boolean ttl_enable) {
		this.cfg_grp = grp;
		cfg_tbl = new Xodb_xowa_cfg_tbl().Provider_(p);
		cfg_stmt = cfg_tbl.Update_stmt();
		this.repo_enable = repo_enable;
		this.ns_enable = ns_enable;
		this.ttl_enable = ttl_enable;
		return this;
	}
	public void Term() {
		this.Save();	// flush existing values
		cfg_stmt.Rls();
	}
	public void Update_repo_ttl(byte repo, byte[] ttl) {Update(repo, Null_ns, ttl);}
	public void Update(byte repo, int ns, byte[] ttl) {
		if (repo_enable) {
			if (repo_prv != repo) {
				repo_dirty = true;
				this.repo_prv = repo;
			}
		}
		if (ns_enable) {
			if (ns_prv != ns) {
				ns_dirty = true;
				this.ns_prv = ns;
			}
		}
		if (ttl_enable) {
			if (!Bry_.Eq(ttl_prv, ttl)) {
				ttl_dirty = true;
				this.ttl_prv = ttl;
			}
		}
	}
	public Xob_bmk_mgr Load() {
		if (repo_enable)
			repo_prv = Byte_.parse_(cfg_tbl.Select_val_or_make(cfg_grp, Cfg_repo_prv, "0"));
		if (ns_enable)
			ns_prv = Int_.parse_(cfg_tbl.Select_val_or_make(cfg_grp, Cfg_ns_prv, "0"));
		if (ttl_enable)
			ttl_prv = Bry_.new_utf8_(cfg_tbl.Select_val_or_make(cfg_grp, Cfg_ttl_prv, ""));
		repo_dirty = ns_dirty = ttl_dirty = false;
		return this;
	}
	public void Save() {
		if (repo_enable && repo_dirty) {
			Save(Cfg_repo_prv, Byte_.XtoStr(repo_prv));
			repo_dirty = false;
		}
		if (ns_enable && ns_dirty) {
			Save(Cfg_ns_prv, Int_.XtoStr(ns_prv));
			ns_dirty = false;
		}
		if (ttl_enable && ttl_dirty) {
			Save(Cfg_ttl_prv, String_.new_utf8_(ttl_prv));
			ttl_dirty = false;
		}
	}
	private void Save(String cfg_key, String cfg_val) {
		cfg_tbl.Update(cfg_stmt, cfg_grp, cfg_key, cfg_val);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repo_prv_))				repo_prv = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_ns_prv_))				ns_prv = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_ttl_prv_))				ttl_prv = m.ReadBry("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_repo_prv_ = "repo_prv_", Invk_ns_prv_ = "ns_prv_", Invk_ttl_prv_ = "ttl_prv_";
	private static final String Cfg_repo_prv = "repo_prv", Cfg_ns_prv = "ns_prv", Cfg_ttl_prv = "ttl_prv";
	private static final int Null_ns = -1;
//		public static final byte Null_repo = Byte_.MaxValue_127;
//		public static final byte[ Null_ttl = null;
}
