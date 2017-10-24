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
public class Xoa_sitelink_grp implements gplx.CompareAble {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public Xoa_sitelink_grp(byte[] name, int sort) {
		this.name = name;
		this.sort = sort;
	}
	public byte[] Name() {return name;} private final byte[] name;
	public int Sort() {return sort;} private final int sort;
	public int Len() {return hash.Count();}
	public Xoa_sitelink_itm Get_at(int i) {return (Xoa_sitelink_itm)hash.Get_at(i);}
	public void Add(Xoa_sitelink_itm itm) {hash.Add(itm.Key(), itm);}
	public void Del(byte[] key) {hash.Del(key);}
	public void	Active_len__add() {++active_len;}
	public int	Active_len() {return active_len;} private int active_len;
	public void Reset() {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_itm itm = (Xoa_sitelink_itm)hash.Get_at(i);
			itm.Init_by_page(null, null, false, null);	// clear out pre-existing page names; needed b/c this struct is a singleton for entire wiki
		}
		active_len = 0;
	}
	public int compareTo(Object obj) {Xoa_sitelink_grp comp = (Xoa_sitelink_grp)obj; return Int_.Compare(sort, comp.sort);}
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_int_digits(1, Xoa_sitelink_mgr_parser.Tid__grp).Add_byte_pipe();
		bfr.Add(name).Add_byte_nl();
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_itm itm = (Xoa_sitelink_itm)hash.Get_at(i);
			itm.To_bfr(bfr);
		}
	}
}
