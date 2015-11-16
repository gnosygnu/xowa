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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import gplx.core.gfo_regys.*;
public class GfsCore implements GfoInvkAble {
	public GfoInvkAble Root() {return root;}
	@gplx.Internal protected GfsRegy Root_as_regy() {return root;} GfsRegy root = GfsRegy.new_();
	public void Clear() {root.Clear();}
	public GfoMsgParser MsgParser() {return msgParser;} public GfsCore MsgParser_(GfoMsgParser v) {msgParser = v; return this;} GfoMsgParser msgParser;
	public void Del(String key) {root.Del(key);}
	public void AddLib(GfsLibIni... ary) {for (GfsLibIni itm : ary) itm.Ini(this);}
	public void AddCmd(GfoInvkAble invk, String key) {root.AddCmd(invk, key);}
	public void AddObj(GfoInvkAble invk, String key) {root.AddObj(invk, key);}
	public void AddDeep(GfoInvkAble invk, String... ary) {
		GfoInvkCmdMgrOwner cur = (GfoInvkCmdMgrOwner)((GfsRegyItm)root.Get_by(ary[0])).InvkAble();
		for (int i = 1; i < ary.length - 1; i++)
			cur = (GfoInvkCmdMgrOwner)cur.InvkMgr().Invk(GfsCtx.Instance, 0, ary[i], GfoMsg_.Null, cur);
		cur.InvkMgr().Add_cmd(ary[ary.length - 1], invk);
	}
	public String FetchKey(GfoInvkAble invk) {return root.FetchByType(invk).Key();}
	public Object ExecOne(GfsCtx ctx, GfoMsg msg) {return GfsCore_.Exec(ctx, root, msg, null, 0);}
	public Object ExecOne_to(GfsCtx ctx, GfoInvkAble invk, GfoMsg msg) {return GfsCore_.Exec(ctx, invk, msg, null, 0);}
	public Object ExecMany(GfsCtx ctx, GfoMsg rootMsg) {
		Object rv = null;
		for (int i = 0; i < rootMsg.Subs_count(); i++) {
			GfoMsg subMsg = (GfoMsg)rootMsg.Subs_getAt(i);
			rv = GfsCore_.Exec(ctx, root, subMsg, null, 0);
		}
		return rv;
	}
	public void ExecRegy(String key) {
		GfoRegyItm itm = GfoRegy.Instance.FetchOrNull(key);
		if (itm == null) {UsrDlg_.Instance.Warn(UsrMsg.new_("could not find script for key").Add("key", key)); return;}
		Io_url url = itm.Url();
		if (!Io_mgr.Instance.ExistsFil(url)) {
			UsrDlg_.Instance.Warn(UsrMsg.new_("script url does not exist").Add("key", key).Add("url", url));
			return;
		}
		this.ExecText(Io_mgr.Instance.LoadFilStr(url));
	}
	public Object ExecFile_ignoreMissing(Io_url url) {if (!Io_mgr.Instance.ExistsFil(url)) return null; return ExecText(Io_mgr.Instance.LoadFilStr(url));}
	public Object ExecFile(Io_url url) {return ExecText(Io_mgr.Instance.LoadFilStr(url));}
	public Object ExecFile_ignoreMissing(GfoInvkAble root, Io_url url) {
		if (!Io_mgr.Instance.ExistsFil(url)) return null; 
		if (msgParser == null) throw Err_.new_wo_type("msgParser is null");
		return Exec_bry(Io_mgr.Instance.LoadFilBry(url), root);
	}
	public Object Exec_bry(byte[] bry) {return Exec_bry(bry, root);}
	public Object Exec_bry(byte[] bry, GfoInvkAble root) {
		GfoMsg rootMsg = msgParser.ParseToMsg(String_.new_u8(bry));
		Object rv = null;
		GfsCtx ctx = GfsCtx.new_();
		for (int i = 0; i < rootMsg.Subs_count(); i++) {
			GfoMsg subMsg = (GfoMsg)rootMsg.Subs_getAt(i);
			rv = GfsCore_.Exec(ctx, root, subMsg, null, 0);
		}
		return rv;
	}
	public Object ExecText(String text) {
		if (msgParser == null) throw Err_.new_wo_type("msgParser is null");
		GfsCtx ctx = GfsCtx.new_();
		return ExecMany(ctx, msgParser.ParseToMsg(text));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ExecFil)) {
			Io_url url = m.ReadIoUrl("url");
			if (ctx.Deny()) return this;
			return ExecFile(url);
		}
		else	return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	public static final String Invk_ExecFil = "ExecFil";
        public static final GfsCore Instance = new GfsCore();
        @gplx.Internal protected static GfsCore new_() {return new GfsCore();}
}
