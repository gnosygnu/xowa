/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.lists.Ordered_hash_base;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
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
		int count = this.Len();
		Io_url[] rv = new Io_url[count];
		for (int i = 0; i < count; i++)
			rv[i] = IoItm_base_.as_(i).Url();
		return rv;
	}
	@Override public void Sort() {SortBy(IoItmBase_comparer_nest.Instance);}
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
		return caseSensitive ? s : StringUtl.Lower(s);
	}
	IoItmDir ownerDir; boolean caseSensitive;
	public static IoItmList new_(IoItmDir v, boolean caseSensitive) {
		IoItmList rv = new IoItmList();
		rv.ownerDir = v; rv.caseSensitive = caseSensitive;
		return rv;
	}
	public static IoItmList list_(boolean caseSensitive) {return new_(null, caseSensitive);}
}
class IoItmBase_comparer_nest implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		IoItm_base lhsItm = (IoItm_base)lhsObj, rhsItm = (IoItm_base)rhsObj;
		Io_url lhsUrl = lhsItm.Url(), rhsUrl = rhsItm.Url();
		return StringUtl.Eq(lhsUrl.OwnerDir().Raw(), rhsUrl.OwnerDir().Raw())                                // is same dir
			? CompareAbleUtl.Compare_obj(lhsUrl.NameAndExt(), rhsUrl.NameAndExt())    // same dir: compare name
			: CompareAbleUtl.Compare_obj(DepthOf(lhsItm), DepthOf(rhsItm));            // diff dir: compare by depth; ex: c:\fil.txt < c:\dir\fil.txt
	}
	int DepthOf(IoItm_base itm) {
		Io_url url = itm.Url();
		return StringUtl.Count(url.OwnerDir().Raw(), url.Info().DirSpr()); // use OwnerDir, else dir.Raw will return extra dirSeparator
	}
	public static final IoItmBase_comparer_nest Instance = new IoItmBase_comparer_nest(); IoItmBase_comparer_nest() {}
}
