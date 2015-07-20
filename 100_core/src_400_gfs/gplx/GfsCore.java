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
package gplx;
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
			cur = (GfoInvkCmdMgrOwner)cur.InvkMgr().Invk(GfsCtx._, 0, ary[i], GfoMsg_.Null, cur);
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
		GfoRegyItm itm = GfoRegy._.FetchOrNull(key);
		if (itm == null) {UsrDlg_._.Warn(UsrMsg.new_("could not find script for key").Add("key", key)); return;}
		Io_url url = itm.Url();
		if (!Io_mgr.I.ExistsFil(url)) {
			UsrDlg_._.Warn(UsrMsg.new_("script url does not exist").Add("key", key).Add("url", url));
			return;
		}
		this.ExecText(Io_mgr.I.LoadFilStr(url));
	}
	public Object ExecFile_ignoreMissing(Io_url url) {if (!Io_mgr.I.ExistsFil(url)) return null; return ExecText(Io_mgr.I.LoadFilStr(url));}
	public Object ExecFile(Io_url url) {return ExecText(Io_mgr.I.LoadFilStr(url));}
	public Object ExecFile_ignoreMissing(GfoInvkAble root, Io_url url) {
		if (!Io_mgr.I.ExistsFil(url)) return null; 
		if (msgParser == null) throw Err_.new_wo_type("msgParser is null");
		return Exec_bry(Io_mgr.I.LoadFilBry(url), root);
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
        public static final GfsCore _ = new GfsCore();
        @gplx.Internal protected static GfsCore new_() {return new GfsCore();}
}
class GfsCore_ {
	public static final String Arg_primitive = "v";
	public static Object Exec(GfsCtx ctx, GfoInvkAble owner_invk, GfoMsg owner_msg, Object owner_primitive, int depth) {
		if (owner_msg.Args_count() == 0 && owner_msg.Subs_count() == 0 && String_.Eq(owner_msg.Key(), "")) {UsrDlg_._.Warn("empty msg"); return GfoInvkAble_.Rv_unhandled;}
		if (owner_primitive != null) owner_msg.Parse_(false).Add(GfsCore_.Arg_primitive, owner_primitive);
		Object rv = owner_invk.Invk(ctx, 0, owner_msg.Key(), owner_msg);
		if		(rv == GfoInvkAble_.Rv_cancel)		return rv;
		else if (rv == GfoInvkAble_.Rv_unhandled)	{
			if (ctx.Fail_if_unhandled())
				throw Err_.new_wo_type("Object does not support key", "key", owner_msg.Key(), "ownerType", ClassAdp_.FullNameOf_obj(owner_invk));
			else {
				Gfo_usr_dlg usr_dlg = ctx.Usr_dlg();
				if (usr_dlg != null) usr_dlg.Warn_many(GRP_KEY, "unhandled_key", "Object does not support key: key=~{0} ownerType=~{1}", owner_msg.Key(), ClassAdp_.FullNameOf_obj(owner_invk));
				return GfoInvkAble_.Null;
			}
		}
		if (owner_msg.Subs_count() == 0) {					// msg is leaf
			GfsRegyItm regyItm = GfsRegyItm.as_(rv);
			if (regyItm == null) return rv;					// rv is primitive or other non-regy Object
			if (regyItm.IsCmd())							// rv is cmd; invk cmd
				return regyItm.InvkAble().Invk(ctx, 0, owner_msg.Key(), owner_msg);
			else											// rv is host
				return regyItm.InvkAble();
		}
		else {												// intermediate; cast to invk and call Exec
			GfoInvkAble invk = GfoInvkAble_.as_(rv);
			Object primitive = null;
			if (invk == null) {								// rv is primitive; find appropriate mgr
				Class<?> type = rv.getClass();
				if		(type == String.class)					invk = String_.Gfs;
				else if (Int_.TypeMatch(type))						invk = Int_.Gfs;
				else if (ClassAdp_.Eq(type, Bool_.Cls_ref_type))	invk = Bool_.Gfs;
				else throw Err_.new_wo_type("unknown primitive", "type", ClassAdp_.NameOf_type(type), "obj", Object_.Xto_str_strict_or_null_mark(rv));
				primitive = rv;
			}
			Object exec_rv = null;
			int len = owner_msg.Subs_count();
			for (int i = 0; i < len; i++)	// iterate over subs; needed for a{b;c;d;}
				exec_rv = Exec(ctx, invk, owner_msg.Subs_getAt(i), primitive, depth + 1); 
			return exec_rv;
		}
	}
	static final String GRP_KEY = "gplx.gfs_core";
}
//	class GfsRegyMgr : GfoInvkAble {
//		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			if		(ctx.Match(k, Invk_Add)) {
//				String libKey = m.ReadStr("libKey"), regKey = m.ReadStr("regKey");
//				if (ctx.Deny()) return this;
//				GfsRegyItm itm = regy.Get_by(libKey);
//				if (regy.Has(regKey)) {ctx.Write_warn("'{0}' already exists", regKey); return this;}
//				regy.Add(regKey, itm.InvkAble(), itm.Type_cmd());
//				ctx.Write_note("added '{0}' as '{1}'", regKey, libKey);
//			}
//			else if	(ctx.Match(k, Invk_Del)) {
//				String regKey = m.ReadStr("regKey");
//				if (ctx.Deny()) return this;
//				if (!regy.Has(regKey)) {ctx.Write_warn("{0} does not exist", regKey); return this;}
//				regy.Del(regKey);
//				ctx.Write_note("removed '{0}'", regKey);
//			}
//			else if	(ctx.Match(k, Invk_Load)) {
//				Io_url url = (Io_url)m.ReadObj("url", Io_url_.Parser);
//				if (ctx.Deny()) return this;
//				String loadText = Io_mgr.I.LoadFilStr(url);
//				GfoMsg loadMsg = core.MsgParser().ParseToMsg(loadText);
//				return core.Exec(ctx, loadMsg);
//			}
//			else return GfoInvkAble_.Rv_unhandled;
//			return this;
//		}	public static final String Invk_Add = "Add", Invk_Del = "Del", Invk_Load = "Load";
//		GfsCore core; GfsRegy regy;
//        public static GfsRegyMgr new_(GfsCore core, GfsRegy regy) {
//			GfsRegyMgr rv = new GfsRegyMgr();
//			rv.core = core; rv.regy = regy;
//			return rv;
//		}	GfsRegyMgr() {}
//	}
