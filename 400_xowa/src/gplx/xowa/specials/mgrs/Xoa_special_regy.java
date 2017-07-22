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
package gplx.xowa.specials.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.specials.*;
public class Xoa_special_regy {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();	// NOTE: case-sensitive; case-insensitive requires lang, but regy is at app level
	public Hash_adp_bry Safelist_pages() {return safelist_pages;} private final    Hash_adp_bry safelist_pages = Hash_adp_bry.ci_u8(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
	public int Len() {return hash.Len();}
	public Xow_special_page Get_at(int i)					{return (Xow_special_page)hash.Get_at(i);}
	public Xow_special_page Get_by_or_null(byte[] key)		{return (Xow_special_page)hash.Get_by(key);}
	public void Add(Xow_special_page page)	{
		hash.Add(page.Special__meta().Key_bry(), page);
		byte[][] aliases = page.Special__meta().Aliases();
		for (byte[] alias : aliases)
			hash.Add(alias, page);
	}
	public void Add_many(Xow_special_page... ary)	{for (Xow_special_page itm : ary) Add(itm);}
}
