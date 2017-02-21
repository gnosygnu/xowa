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
class GfsRegy implements Gfo_invk {
	public int Count() {return hash.Count();}
	public void Clear() {hash.Clear(); typeHash.Clear();}
	public boolean Has(String k) {return hash.Has(k);}
	public GfsRegyItm Get_at(int i) {return (GfsRegyItm)hash.Get_at(i);}
	public GfsRegyItm Get_by(String key) {return (GfsRegyItm)hash.Get_by(key);}
	public GfsRegyItm FetchByType(Gfo_invk invk) {return (GfsRegyItm)typeHash.Get_by(Type_adp_.FullNameOf_obj(invk));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Object rv = (GfsRegyItm)hash.Get_by(k); if (rv == null) throw Err_.new_missing_key(k);
		return rv;
	}
	public void AddObj(Gfo_invk invk, String key) {Add(key, invk, false);}
	public void AddCmd(Gfo_invk invk, String key) {Add(key, invk, true);}
	public void Add(String key, Gfo_invk invk, boolean typeCmd) {
		if (hash.Has(key)) return;
		GfsRegyItm regyItm = new GfsRegyItm().Key_(key).InvkAble_(invk).IsCmd_(typeCmd).TypeKey_(Type_adp_.FullNameOf_obj(invk));
		hash.Add(key, regyItm);
		typeHash.Add_if_dupe_use_1st(regyItm.TypeKey(), regyItm);	// NOTE: changed to allow same Object to be added under different aliases (app, xowa) DATE:2014-06-09;
	}
	public void Del(String k) {
		GfsRegyItm itm =(GfsRegyItm)hash.Get_by(k);
		if (itm != null) typeHash.Del(itm.TypeKey());
		hash.Del(k);
	}
	Hash_adp typeHash = Hash_adp_.New();
	Ordered_hash hash = Ordered_hash_.New();
        public static GfsRegy new_() {return new GfsRegy();} GfsRegy() {}
}
class GfsRegyItm implements Gfo_invk {
	public String Key() {return key;} public GfsRegyItm Key_(String v) {key = v; return this;} private String key;
	public String TypeKey() {return typeKey;} public GfsRegyItm TypeKey_(String v) {typeKey = v; return this;} private String typeKey;
	public boolean IsCmd() {return isCmd;} public GfsRegyItm IsCmd_(boolean v) {isCmd = v; return this;} private boolean isCmd;
	public Gfo_invk InvkAble() {return invkAble;} public GfsRegyItm InvkAble_(Gfo_invk v) {invkAble = v; return this;} Gfo_invk invkAble;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return invkAble.Invk(ctx, ikey, k, m);}
	public static GfsRegyItm as_(Object obj) {return obj instanceof GfsRegyItm ? (GfsRegyItm)obj : null;}
}
