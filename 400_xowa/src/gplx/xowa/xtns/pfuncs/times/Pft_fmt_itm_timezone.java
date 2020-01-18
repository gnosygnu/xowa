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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*;
class Pft_fmt_itm_timezone_offset_4 implements Pft_fmt_itm {
	private final    boolean colon;
	public Pft_fmt_itm_timezone_offset_4(boolean colon) {this.colon = colon;}
	public int TypeId() {return Pft_fmt_itm_.Tid_timezone_offset_4;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		// get tz_secs
		int tz_secs = date.Timezone_offset();

		// add "+" or "-"
		if (tz_secs < 0) {
			bfr.Add_byte(Byte_ascii.Dash);
			tz_secs *= -1;
		}
		else {
			bfr.Add_byte(Byte_ascii.Plus);
		}

		// calc mins / hours
		int tz_mins = tz_secs / 60;

		// add bfr
		bfr.Add_int_fixed((tz_mins / 60), 2);
		if (colon)
			bfr.Add_byte(Byte_ascii.Colon);
		bfr.Add_int_fixed((tz_mins % 60), 2);
	}
}
class Pft_fmt_itm_timezone_id implements Pft_fmt_itm {
	public Pft_fmt_itm_timezone_id(boolean abrv) {}
	public int TypeId() {return Pft_fmt_itm_.Tid_timezone_id_full;}
	public void Fmt(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, DateAdp date, Pft_func_formatdate_bldr bldr) {
		bfr.Add_str_a7(date.Timezone_id());
	}
}
