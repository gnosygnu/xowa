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
package gplx.xowa.htmls.core.wkrs.xndes.dicts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import org.junit.*; import gplx.core.brys.*;
public class Xoh_xnde_dict__tst {
	private final Xoh_xnde_dict__fxt fxt = new Xoh_xnde_dict__fxt();
	@Test  public void Basic() {
		fxt.Exec__add("a");
		fxt.Test__get_by_key_or_new("a", 0);
		fxt.Test__get_by_key_or_new("b", 1);
		byte[] dump = fxt.Dump_bldr().Add(0, "a").Add(1, "b").To_bry();
		fxt.Test__save(dump);
		fxt.Exec__clear();
//			fxt.Test__load(dump, fxt.Make__itm(0, "a"), fxt.Make__itm(1, "b"));
	}
}
class Xoh_xnde_dict__fxt {
	private final Xoh_xnde_dict_grp grp = new Xoh_xnde_dict_grp(1);
	private final Xoh_hzip_bfr bfr = new Xoh_hzip_bfr(32, Bool_.Y, Byte_ascii.Escape);
	private final Bry_rdr rdr = new Bry_rdr();
	public Xoh_xnde_dict__fxt() {
		this.dump_bldr = new Xoh_xnde_dict__dump_bldr(bfr);
	}
	public Xoh_xnde_dict__dump_bldr Dump_bldr() {return dump_bldr;} private final Xoh_xnde_dict__dump_bldr dump_bldr;
	public void Exec__add(String val)	{grp.Add(Bry_.new_u8(val));}
	public void Exec__clear()			{grp.Clear();}
	public void Test__get_by_key_or_new(String val, int expd_id) {
		byte[] val_bry = Bry_.new_u8(val);
		Xoh_xnde_dict_itm actl_itm = grp.Get_by_key_or_new(val_bry, 0, val_bry.length);
		Tfds.Eq_int(expd_id, actl_itm.Id());
		Tfds.Eq_bry(val_bry, actl_itm.Val());
	}
	public void Test__save(byte[] expd) {
		grp.Save(bfr);
		Tfds.Eq_bry(expd, bfr.To_bry_and_clear());
	}
	public void Test__load(byte[] dump, Xoh_xnde_dict_itm... itms) {
		rdr.Init_by_page(Bry_.Empty, dump, dump.length);
		grp.Load(rdr);
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; ++i) {
			Xoh_xnde_dict_itm expd_itm = itms[i];
			Xoh_xnde_dict_itm actl_itm = grp.Get_by_id_or_null(expd_itm.Id());
			Tfds.Eq_int(expd_itm.Id(), actl_itm.Id());
			Tfds.Eq_bry(expd_itm.Val(), actl_itm.Val());
		}			
	}
	public Xoh_xnde_dict_itm Make__itm(int id, String val) {return new Xoh_xnde_dict_itm(id, Bry_.new_u8(val));}
}
class Xoh_xnde_dict__dump_bldr {
	private final Xoh_hzip_bfr bfr;
	public Xoh_xnde_dict__dump_bldr(Xoh_hzip_bfr bfr) {this.bfr = bfr;}
	public Xoh_xnde_dict__dump_bldr Add(int id, String val) {
		bfr.Add_hzip_int(1, (byte)id);
		bfr.Add_str_u8(val);
		bfr.Add_byte_nl();
		return this;
	}
	public byte[] To_bry() {
		bfr.Add(Xoh_xnde_dict_grp.Bry__stop);
		return bfr.To_bry_and_clear();
	}
}
