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
package gplx.xowa.htmls.core.htmls.utls;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.langs.htmls.Gfh_utl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Xoh_anchor_kv_bldr {
	private byte[] base_url; private boolean has_qarg;
	private final BryWtr tmp_bfr = BryWtr.NewWithSize(16), apos_bfr = BryWtr.NewWithSize(16);
	public Xoh_anchor_kv_bldr Init_w_qarg(byte[] base_url) {return Init(BoolUtl.Y, base_url);}
	public Xoh_anchor_kv_bldr Init(boolean has_qarg, byte[] base_url) {
		this.has_qarg = has_qarg; this.base_url = base_url;
		tmp_bfr.Clear();
		tmp_bfr.Add(base_url);
		return this;
	}
	public Xoh_anchor_kv_bldr Add_int(byte[] key, int val) {
		tmp_bfr.AddByte(has_qarg ? AsciiByte.Amp : AsciiByte.Question);
		tmp_bfr.Add(key);
		tmp_bfr.AddByte(AsciiByte.Eq);
		tmp_bfr.AddIntVariable(val);
		return this;
	}
	public Xoh_anchor_kv_bldr Add_bry(byte[] key, byte[] bry) {
		tmp_bfr.AddByte(has_qarg ? AsciiByte.Amp : AsciiByte.Question);
		tmp_bfr.Add(key);
		tmp_bfr.AddByte(AsciiByte.Eq);
		tmp_bfr.Add(Gfh_utl.Escape_for_atr_val_as_bry(apos_bfr, AsciiByte.Apos, bry));
		return this;
	}
	public byte[] Bld_to_bry() {
		byte[] rv = tmp_bfr.ToBryAndClear();
		tmp_bfr.Add(base_url);
		return rv;
	}
}
