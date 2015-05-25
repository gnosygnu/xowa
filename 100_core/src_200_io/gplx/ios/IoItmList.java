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
package gplx.ios; import gplx.*;
import gplx.lists.*; /*Ordered_hash_base*/
public class IoItmList extends Ordered_hash_base {
	public boolean Has(Io_url url) {return Has_base(MakeKey(url));}
	public void Add(IoItm_base itm) {
		if (ownerDir != null) itm.OwnerDir_set(ownerDir);
		Add_base(MakeKey(itm.Url()), itm);
	} 
	public void Del(Io_url url) {
		String key = MakeKey(url);
		IoItm_base itm = IoItm_base_.as_(Fetch_base(key)); if (itm == null) return;
		itm.OwnerDir_set(null);
		super.Del(key);
	}
	public Io_url[] XtoIoUrlAry() {
		int count = this.Count();
		Io_url[] rv = new Io_url[count];
		for (int i = 0; i < count; i++)
			rv[i] = IoItm_base_.as_(i).Url();
		return rv;
	}
	@Override public void Sort() {Sort_by(IoItmBase_comparer_nest._);}
	@Override protected Object Fetch_base(Object keyObj) {
		String key = MakeKey((String)keyObj);
		return super.Fetch_base(key);
	}
	@Override public void Del(Object keyObj) {
		String key = MakeKey((String)keyObj);
		super.Del(key);
	}
	String MakeKey(Io_url url) {
		String itmName = url.Type_dir() ? url.NameOnly() : url.NameAndExt();
		return MakeKey(itmName);
	}
	String MakeKey(String s) {
		return caseSensitive ? s : String_.Lower(s);
	}
	IoItmDir ownerDir; boolean caseSensitive;
	@gplx.Internal protected static IoItmList new_(IoItmDir v, boolean caseSensitive) {
		IoItmList rv = new IoItmList();
		rv.ownerDir = v; rv.caseSensitive = caseSensitive;
		return rv;
	}
	@gplx.Internal protected static IoItmList list_(boolean caseSensitive) {return new_(null, caseSensitive);}
}
class IoItmBase_comparer_nest implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		IoItm_base lhsItm = (IoItm_base)lhsObj, rhsItm = (IoItm_base)rhsObj;
		Io_url lhsUrl = lhsItm.Url(), rhsUrl = rhsItm.Url();
		return String_.Eq(lhsUrl.OwnerDir().Raw(), rhsUrl.OwnerDir().Raw())								// is same dir
			? CompareAble_.Compare_obj(lhsUrl.NameAndExt(), rhsUrl.NameAndExt())	// same dir: compare name
			: CompareAble_.Compare_obj(DepthOf(lhsItm), DepthOf(rhsItm));			// diff dir: compare by depth; ex: c:\fil.txt < c:\dir\fil.txt
	}
	int DepthOf(IoItm_base itm) {
		Io_url url = itm.Url();
		return String_.Count(url.OwnerDir().Raw(), url.Info().DirSpr()); // use OwnerDir, else dir.Raw will return extra dirSeparator
	}
	public static final IoItmBase_comparer_nest _ = new IoItmBase_comparer_nest(); IoItmBase_comparer_nest() {}
}
