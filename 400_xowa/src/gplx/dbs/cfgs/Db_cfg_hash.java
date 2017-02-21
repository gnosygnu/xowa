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
package gplx.dbs.cfgs; import gplx.*; import gplx.dbs.*;
public class Db_cfg_hash {		
	private final    String grp; private final    Ordered_hash hash = Ordered_hash_.New();
	public Db_cfg_hash(String grp) {this.grp = grp;}
	public int			Len() {return hash.Count();}
	public Db_cfg_itm	Get_at(int i) {return (Db_cfg_itm)hash.Get_at(i);}
	public Db_cfg_itm	Get_by(String key) {
		Db_cfg_itm rv = (Db_cfg_itm)hash.Get_by(key);
		return rv == null ? Db_cfg_itm.Empty : rv;
	}
	public void Set(String key, String val) {hash.Del(key); Add(key, val);}
	public void Add(Db_cfg_itm itm) {hash.Add(itm.Key(), itm);}
	public void Add(String key, String val) {
		if (hash.Has(key)) throw Err_.new_wo_type("itm exists", "grp", grp, "key", key);
		Db_cfg_itm itm = new Db_cfg_itm(grp, key, val);
		Add(itm);
	}
}
