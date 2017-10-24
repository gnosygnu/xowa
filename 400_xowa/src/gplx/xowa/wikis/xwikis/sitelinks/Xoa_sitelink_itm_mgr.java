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
package gplx.xowa.wikis.xwikis.sitelinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.langs.*;
public class Xoa_sitelink_itm_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	private final Xoa_sitelink_grp default_grp;
	public Xoa_sitelink_itm_mgr(Xoa_sitelink_grp default_grp) {this.default_grp = default_grp;}
	public int Len() {return hash.Count();}
	public void Clear() {hash.Clear();}
	public void Add(Xoa_sitelink_itm itm)			{hash.Add(itm.Key(), itm);}
	public Xoa_sitelink_itm Get_at(int idx)			{return (Xoa_sitelink_itm)hash.Get_at(idx);}
	public Xoa_sitelink_itm Get_by(byte[] key)		{return (Xoa_sitelink_itm)hash.Get_by(key);}
	public Xoa_sitelink_itm Get_by_or_new(byte[] key) {
		Xoa_sitelink_itm rv = (Xoa_sitelink_itm)hash.Get_by(key);
		if (rv == null) {
			rv = new Xoa_sitelink_itm(default_grp, key, Bry_.Empty);
			hash.Add(key, rv);
		}
		return rv;
	}
}
