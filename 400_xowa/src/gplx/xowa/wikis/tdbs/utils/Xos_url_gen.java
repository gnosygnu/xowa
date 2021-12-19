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
package gplx.xowa.wikis.tdbs.utils;
import gplx.types.basics.utls.BryUtl;
import gplx.core.ios.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
public class Xos_url_gen implements Io_url_gen {
	public Xos_url_gen(Io_url root) {this.root = root;} Io_url root; int idx = 0;
	public byte[] Ext() {return ext;} public Xos_url_gen Ext_(byte[] v) {ext = v; return this;} private byte[] ext = BryUtl.NewA7(".csv");
	public Io_url Cur_url() {return cur_url;} Io_url cur_url;
	public Io_url Nxt_url() {cur_url = bld_fil_(root, idx++, ext); return cur_url;}
	public Io_url[] Prv_urls() {
		int len = idx + 1;
		Io_url[] rv = new Io_url[len];
		for (int i = 0; i < len; i++)
			rv[i] = bld_fil_(root, i, ext);
		return rv;
	}
	public void Del_all() {}
	public static Io_url bld_fil_(Io_url root, int idx, byte[] ext) {
		byte dir_spr = root.Info().DirSpr_byte();
		tmp_bfr.Add(root.RawBry());
		int cur_mod = 100000000, cur_idx = idx;
		while (cur_mod > 99) {
			int val = cur_idx / cur_mod;
			tmp_bfr.AddIntFixed(val, 2).AddByte(dir_spr);
			cur_idx -= (val * cur_mod);
			cur_mod /= 100;
		}
		tmp_bfr.AddIntFixed(idx, 10);
		tmp_bfr.Add(ext);
		return Io_url_.new_fil_(tmp_bfr.ToStrAndClear());
	}
	private static BryWtr tmp_bfr = BryWtr.NewAndReset(256);
}
