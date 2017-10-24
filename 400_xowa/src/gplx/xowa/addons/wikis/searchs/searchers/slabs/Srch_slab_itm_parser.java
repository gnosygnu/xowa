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
package gplx.xowa.addons.wikis.searchs.searchers.slabs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.core.brys.*;
public class Srch_slab_itm_parser {
	private final    List_adp itm_list = List_adp_.New();
	private final    Bry_rdr rdr = new Bry_rdr();
	public Srch_slab_itm[] Parse(byte[] raw) {	// EX: en.wikipedia.org|41|60;en.wiktionary.org|21|40;
		rdr.Init_by_src(raw);
		while (!rdr.Pos_is_eos()) {
			byte[] wiki = rdr.Read_bry_to(Byte_ascii.Pipe);				
			int bgn = rdr.Read_int_to(Byte_ascii.Pipe);
			int end = rdr.Read_int_to(Byte_ascii.Semic);
			Srch_slab_itm itm = new Srch_slab_itm(wiki, bgn, end);
			itm_list.Add(itm);
		}
		return (Srch_slab_itm[])itm_list.To_ary_and_clear(Srch_slab_itm.class);
	}
}
