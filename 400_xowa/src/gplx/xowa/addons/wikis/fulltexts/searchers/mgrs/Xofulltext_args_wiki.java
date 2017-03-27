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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
public class Xofulltext_args_wiki {
	public byte[] wiki;
	public byte[] ns_ids;
	public Hash_adp ns_hash = Hash_adp_.New();
	public int bgn;
	public int len;
	public int end() {return bgn + len;}
	public boolean expand_pages;
	public boolean expand_snips;
	public boolean show_all_snips;

	public Xofulltext_args_wiki(byte[] wiki) {
		this.wiki = wiki;
	}
	public void Init_by_json(String key, byte[] val) {
		if      (String_.Eq(key, "ns_ids")) {
			this.ns_ids = val;
			byte[][] ns_ary = Bry_split_.Split(ns_ids, Byte_ascii.Comma, true);
			for (byte[] ns_id : ns_ary) {
				int ns_int = Bry_.To_int(ns_id);
				ns_hash.Add_if_dupe_use_1st(ns_int, ns_int);
			}
		}
		else if (String_.Eq(key, "offsets"))          this.bgn = Bry_.To_int(val);
		else if (String_.Eq(key, "limits"))           this.len = Bry_.To_int(val);
		else if (String_.Eq(key, "expand_pages"))     this.expand_pages = Bry_.Eq(val, Bool_.Y_bry);
		else if (String_.Eq(key, "expand_snips"))     this.expand_snips = Bry_.Eq(val, Bool_.Y_bry);
		else if (String_.Eq(key, "show_all_snips"))   this.show_all_snips = Bry_.Eq(val, Bool_.Y_bry);
	}
}
