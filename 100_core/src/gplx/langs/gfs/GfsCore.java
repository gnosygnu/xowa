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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import gplx.core.gfo_regys.*;
public class GfsCore implements Gfo_invk {
	public Gfo_invk Root() {return root;}
	@gplx.Internal protected GfsRegy Root_as_regy() {return root;} GfsRegy root = GfsRegy.new_();
	public void Clear() {root.Clear();}
	public GfoMsgParser MsgParser() {return msgParser;} public GfsCore MsgParser_(GfoMsgParser v) {msgParser = v; return this;} GfoMsgParser msgParser;
	public void Del(String key) {root.Del(key);}
	public void AddLib(GfsLibIni... ary) {for (GfsLibIni itm : ary) itm.Ini(this);}
	public void AddCmd(Gfo_invk invk, String key) {root.AddCmd(invk, key);}
	public void AddObj(Gfo_invk invk, String key) {root.AddObj(invk, key);}
	public void AddDeep(Gfo_invk invk, String... ary) {
		Gfo_invk_cmd_mgr_owner cur = (Gfo_invk_cmd_mgr_owner)((GfsRegyItm)root.Get_by(ary[0])).InvkAble();
		for (int i = 1; i < ary.length - 1; i++)
			cur = (Gfo_invk_cmd_mgr_owner)cur.InvkMgr().Invk(GfsCtx.Instance, 0, ary[i], GfoMsg_.Null, cur);
		cur.InvkMgr().Add_cmd(ary[ary.length - 1], invk);
	}
	public String FetchKey(Gfo_invk invk) {return root.FetchByType(invk).Key();}
	public Object ExecOne(GfsCtx ctx, GfoMsg msg) {return GfsCore_.Exec(ctx, root, msg, null, 0);}
	public Object ExecOne_to(GfsCtx ctx, Gfo_invk invk, GfoMsg msg) {return GfsCore_.Exec(ctx, invk, msg, null, 0);}
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
	public Object ExecFile_ignoreMissing(Gfo_invk root, Io_url url) {
		if (!Io_mgr.Instance.ExistsFil(url)) return null; 
		if (msgParser == null) throw Err_.new_wo_type("msgParser is null");
		return Exec_bry(Io_mgr.Instance.LoadFilBry(url), root);
	}
	public Object Exec_bry(byte[] bry) {return Exec_bry(bry, root);}
	public Object Exec_bry(byte[] bry, Gfo_invk root) {
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
		else	return Gfo_invk_.Rv_unhandled;
//			return this;
	}	public static final    String Invk_ExecFil = "ExecFil";
        public static final    GfsCore Instance = new GfsCore();
        @gplx.Internal protected static GfsCore new_() {return new GfsCore();}
}
