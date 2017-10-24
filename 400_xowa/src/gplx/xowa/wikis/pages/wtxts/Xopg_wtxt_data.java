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
package gplx.xowa.wikis.pages.wtxts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_wtxt_data {
	public Xopg_toc_mgr				Toc()				{return toc;}	private final    Xopg_toc_mgr toc = new Xopg_toc_mgr(); 		
	public int						Ctgs__len()			{return ctg_hash == null ? 0 : ctg_hash.Len();} private Ordered_hash ctg_hash;
	public Xoa_ttl					Ctgs__get_at(int i) {return (Xoa_ttl)ctg_hash.Get_at(i);}
	public Xoa_ttl[]				Ctgs__to_ary()		{return ctg_hash == null ? new Xoa_ttl[0] : (Xoa_ttl[])ctg_hash.To_ary(Xoa_ttl.class);}
	public void Ctgs__add(Xoa_ttl ttl) {
		if (ctg_hash == null) ctg_hash = Ordered_hash_.New_bry();
		ctg_hash.Add_if_dupe_use_1st(ttl.Full_db(), ttl);
	}
	public void Clear() {
		if (ctg_hash != null) ctg_hash.Clear();
		toc.Clear();
	}
}
