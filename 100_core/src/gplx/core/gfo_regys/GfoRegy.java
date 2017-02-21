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
package gplx.core.gfo_regys; import gplx.*; import gplx.core.*;
import gplx.langs.gfs.*; import gplx.core.type_xtns.*; import gplx.core.interfaces.*;
public class GfoRegy implements Gfo_invk {
	public int Count() {return hash.Count();}
	public Hash_adp Parsers() {return parsers;} Hash_adp parsers = Hash_adp_.New();
	public GfoRegyItm FetchOrNull(String key) {return (GfoRegyItm)hash.Get_by(key);}
	public Object FetchValOrFail(String key) {
		GfoRegyItm rv = (GfoRegyItm)hash.Get_by(key); if (rv == null) throw Err_.new_wo_type("regy does not have key", "key", key);
		return rv.Val();
	}
	public Object FetchValOrNull(String key) {return FetchValOr(key, null);}
	public Object FetchValOr(String key, Object or) {
		GfoRegyItm itm = FetchOrNull(key);
		return itm == null ? or : itm.Val();
	}
	public void Del(String key) {hash.Del(key);}
	public void RegObj(String key, Object val) {RegItm(key, val, GfoRegyItm.ValType_Obj, Io_url_.Empty);}
	public void RegDir(Io_url dirUrl, String match, boolean recur, String chopBgn, String chopEnd) {
		Io_url[] filUrls = Io_mgr.Instance.QueryDir_args(dirUrl).FilPath_(match).Recur_(recur).ExecAsUrlAry();
		if (filUrls.length == 0 && !Io_mgr.Instance.ExistsDir(dirUrl)) {UsrDlg_.Instance.Stop(UsrMsg.new_("dirUrl does not exist").Add("dirUrl", dirUrl.Xto_api())); return;}
		for (Io_url filUrl : filUrls) {
			String key = filUrl.NameAndExt();
			int pos = String_.Find_none;
			if (String_.EqNot(chopBgn, "")) {
				pos = String_.FindFwd(key, chopBgn);
				if		(pos == String_.Len(key) - 1)
					throw Err_.new_wo_type(Err_ChopBgn, "key", key, "chopBgn", chopBgn);
				else if (pos != String_.Find_none)
					key = String_.Mid(key, pos + 1);
			}
			if (String_.EqNot(chopEnd, "")) {
				pos = String_.FindBwd(key, chopEnd);
				if		(pos == 0)
					throw Err_.new_wo_type(Err_ChopEnd, "key", key, "chopEnd", chopEnd);
				else if (pos != String_.Find_none)
					key = String_.MidByLen(key, 0, pos);
			}
			if (hash.Has(key)) throw Err_.new_wo_type(Err_Dupe, "key", key, "filUrl", filUrl);
			RegItm(key, null, GfoRegyItm.ValType_Url, filUrl);
		}
	}
	public void RegObjByType(String key, String val, String type) {
		Object o = val;
		if (String_.EqNot(type, StringClassXtn.Key_const)) {
			ParseAble parser = (ParseAble)parsers.Get_by(type);
			if (parser == null) throw Err_.new_wo_type("could not find parser", "type", type, "key", key, "val", val);
			o = parser.ParseAsObj(val);
		}
		RegItm(key, o, GfoRegyItm.ValType_Obj, Io_url_.Empty);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_RegDir)) {
			Io_url dir = m.ReadIoUrl("dir");
			String match = m.ReadStrOr("match", "*.*");
			boolean recur = m.ReadBoolOr("recur", false);
			String chopBgn = m.ReadStrOr("chopBgn", "");
			String chopEnd = m.ReadStrOr("chopEnd", ".");
			if (ctx.Deny()) return this;
			RegDir(dir, match, recur, chopBgn, chopEnd);
		}
		else if	(ctx.Match(k, Invk_RegObj)) {
			String key = m.ReadStr("key");
			String val = m.ReadStr("val");
			String type = m.ReadStrOr("type", StringClassXtn.Key_const);
			if (ctx.Deny()) return this;
			RegObjByType(key, val, type);
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Invk_RegDir = "RegDir", Invk_RegObj = "RegObj";
	void RegItm(String key, Object val, int valType, Io_url url) {
		hash.Add_if_dupe_use_nth(key, new GfoRegyItm(key, val, valType, url));
	}
	Hash_adp hash = Hash_adp_.New();
	public static final    String Err_ChopBgn = "chopBgn results in null key", Err_ChopEnd = "chopEnd results in null key", Err_Dupe = "key already registered";
        public static final    GfoRegy Instance = new GfoRegy(); GfoRegy() {}
        @gplx.Internal protected static GfoRegy new_() {return new GfoRegy();}
}
