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
public class GfoRegy implements GfoInvkAble {
	public int Count() {return hash.Count();}
	public HashAdp Parsers() {return parsers;} HashAdp parsers = HashAdp_.new_();
	public GfoRegyItm FetchOrNull(String key) {return (GfoRegyItm)hash.Fetch(key);}
	public Object FetchValOrFail(String key) {
		GfoRegyItm rv = (GfoRegyItm)hash.Fetch(key); if (rv == null) throw Err_.new_("regy does not have key").Add("key", key);
		return rv.Val();
	}
	public Object FetchValOrNull(String key) {return FetchValOr(key, null);}
	public Object FetchValOr(String key, Object or) {
		GfoRegyItm itm = FetchOrNull(key);
		return itm == null ? or : itm.Val();
	}
	public void Del(String key) {hash.Del(key);}
	public void RegObj(String key, Object val) {RegItm(key, val, GfoRegyItm.ValType_Obj, Io_url_.Null);}
	public void RegDir(Io_url dirUrl, String match, boolean recur, String chopBgn, String chopEnd) {
		Io_url[] filUrls = Io_mgr._.QueryDir_args(dirUrl).FilPath_(match).Recur_(recur).ExecAsUrlAry();
		if (filUrls.length == 0 && !Io_mgr._.ExistsDir(dirUrl)) {UsrDlg_._.Stop(UsrMsg.new_("dirUrl does not exist").Add("dirUrl", dirUrl.Xto_api())); return;}
		for (Io_url filUrl : filUrls) {
			String key = filUrl.NameAndExt();
			int pos = String_.Find_none;
			if (String_.EqNot(chopBgn, "")) {
				pos = String_.FindFwd(key, chopBgn);
				if		(pos == String_.Len(key) - 1)
					throw Err_.new_(Err_ChopBgn).Add("key", key).Add("chopBgn", chopBgn);
				else if (pos != String_.Find_none)
					key = String_.Mid(key, pos + 1);
			}
			if (String_.EqNot(chopEnd, "")) {
				pos = String_.FindBwd(key, chopEnd);
				if		(pos == 0)
					throw Err_.new_(Err_ChopEnd).Add("key", key).Add("chopEnd", chopEnd);
				else if (pos != String_.Find_none)
					key = String_.MidByLen(key, 0, pos);
			}
			if (hash.Has(key)) throw Err_.new_(Err_Dupe).Add("key", key).Add("filUrl", filUrl);
			RegItm(key, null, GfoRegyItm.ValType_Url, filUrl);
		}
	}
	public void RegObjByType(String key, String val, String type) {
		Object o = val;
		if (String_.EqNot(type, StringClassXtn.Key_const)) {
			ParseAble parser = (ParseAble)parsers.Fetch(type);
			if (parser == null) throw Err_.new_("could not find parser").Add("type", type).Add("key", key).Add("val", val);
			o = parser.ParseAsObj(val);
		}
		RegItm(key, o, GfoRegyItm.ValType_Obj, Io_url_.Null);
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
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_RegDir = "RegDir", Invk_RegObj = "RegObj";
	void RegItm(String key, Object val, int valType, Io_url url) {
		hash.AddReplace(key, new GfoRegyItm(key, val, valType, url));
	}
	HashAdp hash = HashAdp_.new_();
	public static final String Err_ChopBgn = "chopBgn results in null key", Err_ChopEnd = "chopEnd results in null key", Err_Dupe = "key already registered";
        public static final GfoRegy _ = new GfoRegy(); GfoRegy() {}
        @gplx.Internal protected static GfoRegy new_() {return new GfoRegy();}
}
