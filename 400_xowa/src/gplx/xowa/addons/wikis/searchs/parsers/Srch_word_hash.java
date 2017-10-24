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
package gplx.xowa.addons.wikis.searchs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
public class Srch_word_hash {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public void Clear()					{hash.Clear();}
	public int Len()					{return hash.Count();}
	public boolean Has(byte[] word)		{return hash.Has(word);}
	public Srch_word_itm Get_at(int i)	{return (Srch_word_itm)hash.Get_at(i);}
	public void Add(byte[] word) {
		Srch_word_itm itm = (Srch_word_itm)hash.Get_by(word);
		if (itm == null) {
			itm = new Srch_word_itm(word);
			hash.Add(word, itm);
		}
		itm.Count_add_1_();
	}
}
