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
class GfsRegy implements GfoInvkAble {
	public int Count() {return hash.Count();}
	public void Clear() {hash.Clear(); typeHash.Clear();}
	public boolean Has(String k) {return hash.Has(k);}
	public GfsRegyItm Get_at(int i) {return (GfsRegyItm)hash.Get_at(i);}
	public GfsRegyItm Get_by(String key) {return (GfsRegyItm)hash.Get_by(key);}
	public GfsRegyItm FetchByType(GfoInvkAble invk) {return (GfsRegyItm)typeHash.Get_by(ClassAdp_.FullNameOf_obj(invk));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Object rv = (GfsRegyItm)hash.Get_by(k); if (rv == null) throw Exc_.new_missing_key(k);
		return rv;
	}
	public void AddObj(GfoInvkAble invk, String key) {Add(key, invk, false);}
	public void AddCmd(GfoInvkAble invk, String key) {Add(key, invk, true);}
	public void Add(String key, GfoInvkAble invk, boolean typeCmd) {
		if (hash.Has(key)) return;
		GfsRegyItm regyItm = new GfsRegyItm().Key_(key).InvkAble_(invk).IsCmd_(typeCmd).TypeKey_(ClassAdp_.FullNameOf_obj(invk));
		hash.Add(key, regyItm);
		typeHash.Add_if_dupe_use_1st(regyItm.TypeKey(), regyItm);	// NOTE: changed to allow same Object to be added under different aliases (app, xowa) DATE:2014-06-09;
	}
	public void Del(String k) {
		GfsRegyItm itm =(GfsRegyItm)hash.Get_by(k);
		if (itm != null) typeHash.Del(itm.TypeKey());
		hash.Del(k);
	}
	Hash_adp typeHash = Hash_adp_.new_();
	Ordered_hash hash = Ordered_hash_.new_();
        public static GfsRegy new_() {return new GfsRegy();} GfsRegy() {}
}
class GfsRegyItm implements GfoInvkAble {
	public String Key() {return key;} public GfsRegyItm Key_(String v) {key = v; return this;} private String key;
	public String TypeKey() {return typeKey;} public GfsRegyItm TypeKey_(String v) {typeKey = v; return this;} private String typeKey;
	public boolean IsCmd() {return isCmd;} public GfsRegyItm IsCmd_(boolean v) {isCmd = v; return this;} private boolean isCmd;
	public GfoInvkAble InvkAble() {return invkAble;} public GfsRegyItm InvkAble_(GfoInvkAble v) {invkAble = v; return this;} GfoInvkAble invkAble;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return invkAble.Invk(ctx, ikey, k, m);}
	public static GfsRegyItm as_(Object obj) {return obj instanceof GfsRegyItm ? (GfsRegyItm)obj : null;}
}
