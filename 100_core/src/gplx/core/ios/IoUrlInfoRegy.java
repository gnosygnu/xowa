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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoUrlInfoRegy implements GfoInvkAble {
	public void Reg(IoUrlInfo info) {hash.Add_if_dupe_use_nth(info.Key(), info);}
	public IoUrlInfo Match(String raw) {
		if (String_.Len(raw) == 0) return IoUrlInfo_.Nil;
		for (int i = hash.Count(); i > 0; i--) {
			IoUrlInfo info = (IoUrlInfo)hash.Get_at(i - 1);
			if (info.Match(raw)) return info;
		}
		throw Err_.new_wo_type("could not match ioPathInfo", "raw", raw, "count", hash.Count());
	}
	public void Reset() {
		hash.Clear();
		Reg(IoUrlInfo_rel.new_(Op_sys.Cur().Tid_is_wnt() ? (IoUrlInfo)IoUrlInfo_wnt.Instance : (IoUrlInfo)IoUrlInfo_lnx.Instance));
		Reg(IoUrlInfo_.Mem);
		Reg(IoUrlInfo_lnx.Instance);
		Reg(IoUrlInfo_wnt.Instance);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Add)) {
			String srcDirStr = m.ReadStr("srcDir");
			String trgDirStr = m.ReadStr("trgDir");
			String engineKey = m.ReadStrOr("engineKey", IoEngine_.SysKey);
			if (ctx.Deny()) return this;
			IoUrlInfo_alias alias =  IoUrlInfo_alias.new_(srcDirStr, trgDirStr, engineKey);
			IoUrlInfoRegy.Instance.Reg(alias);
		}
		return this;
	}	public static final String Invk_Add = "Add";
	Ordered_hash hash = Ordered_hash_.New();
        public static final IoUrlInfoRegy Instance = new IoUrlInfoRegy();
	IoUrlInfoRegy() {
		this.Reset();
	}
}