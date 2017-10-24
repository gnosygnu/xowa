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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
class Xob_lnki_temp_row implements CompareAble {
	public int Lnki_id() {return lnki_id;} private int lnki_id;
	public int Lnki_tier_id() {return lnki_tier_id;} private int lnki_tier_id;
	public int Lnki_page_id() {return lnki_page_id;} private int lnki_page_id;
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[] Lnki_commons_ttl() {return lnki_commons_ttl;} private byte[] lnki_commons_ttl;
	public byte Lnki_ext() {return lnki_ext;} private byte lnki_ext;
	public byte Lnki_type() {return lnki_type;} private byte lnki_type;
	public byte Lnki_src_tid() {return lnki_src_tid;} private byte lnki_src_tid;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h;
	public double Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double Lnki_time() {return lnki_time;} private double lnki_time;
	public int Lnki_page() {return lnki_page;} private int lnki_page;
	public void Load(Db_rdr rdr, int lnki_id) {
		this.lnki_id = lnki_id;
		this.lnki_tier_id = rdr.Read_int("lnki_tier_id");
		this.lnki_page_id = rdr.Read_int("lnki_page_id");
		this.lnki_ttl = rdr.Read_bry_by_str("lnki_ttl");
		this.lnki_commons_ttl = Bry_.new_u8_safe(rdr.Read_str("lnki_commons_ttl"));
		this.lnki_ext = rdr.Read_byte("lnki_ext");
		this.lnki_type = rdr.Read_byte("lnki_type");
		this.lnki_src_tid = rdr.Read_byte("lnki_src_tid");
		this.lnki_w = rdr.Read_int("lnki_w");
		this.lnki_h = rdr.Read_int("lnki_h");
		this.lnki_upright = rdr.Read_double("lnki_upright");
		this.lnki_time = rdr.Read_double("lnki_time");
		this.lnki_page = rdr.Read_int("lnki_page");
	}
	public int compareTo(Object obj) {
		Xob_lnki_temp_row comp = (Xob_lnki_temp_row)obj;
		return Int_.Compare(lnki_id, comp.lnki_id);
	}
}
