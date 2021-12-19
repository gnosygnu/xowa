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
package gplx.core.stores;
import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValList;
import gplx.types.errs.ErrUtl;
public class GfoNdeRdr_ {
	public static GfoNdeRdr kvs_(KeyValList kvList) {
		GfoFldList flds = GfoFldList_.new_();
		int pairsLen = kvList.Len();
		Object[] vals = new Object[pairsLen];
		for (int i = 0; i < pairsLen; i++) {
			KeyVal pair = kvList.GetAt(i);
			flds.Add(pair.KeyToStr(), StringClassXtn.Instance);
			vals[i] = pair.ValToStrOrEmpty();
		}
		GfoNde nde = GfoNde_.vals_(flds, vals);
		return root_(nde, true);
	}
	public static GfoNdeRdr root_parseNot_(GfoNde root) {return root_(root, true);}
	public static GfoNdeRdr root_(GfoNde root, boolean parse) {
		DataRdr_mem rv = DataRdr_mem.new_(root, root.Flds(), root.Subs()); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr leaf_(GfoNde cur, boolean parse) {
		DataRdr_mem rv = DataRdr_mem.new_(cur, cur.Flds(), GfoNdeList_.Null); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr peers_(GfoNdeList peers, boolean parse) {
		GfoFldList flds = peers.Count() == 0 ? GfoFldList_.Null : peers.FetchAt_asGfoNde(0).Flds();
		DataRdr_mem rv = DataRdr_mem.new_(null, flds, peers); rv.Parse_set(parse);
		return rv;
	}
	public static GfoNdeRdr as_(Object obj) {return obj instanceof GfoNdeRdr ? (GfoNdeRdr)obj : null;}
	public static GfoNdeRdr cast(Object obj) {try {return (GfoNdeRdr)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, GfoNdeRdr.class, obj);}}
}
