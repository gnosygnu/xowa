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
package gplx.xowa.specials;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
public class Xow_special_meta {
	public Xow_special_meta(int src, String key_str, String... aliases) {
		this.src = src; this.key_str = key_str;
		this.key_bry = BryUtl.NewU8(key_str);
		this.ttl_bry = BryUtl.Add(gplx.xowa.wikis.nss.Xow_ns_.Bry__special, AsciiByte.ColonBry, key_bry);
		this.ttl_str = StringUtl.NewU8(ttl_bry);
		this.aliases = BryUtl.Ary(aliases);
	}
	public int Src()				{return src;} private final int src;					// either MW or XOWA
	public String Key_str()			{return key_str;} private final String key_str;		// EX: AllPages
	public byte[] Key_bry()			{return key_bry;} private final byte[] key_bry;
	public String Ttl_str()			{return ttl_str;} private final String ttl_str;		// EX: Special:AllPages
	public byte[] Ttl_bry()			{return ttl_bry;} private final byte[] ttl_bry;
	public byte[][] Aliases()		{return aliases;} private final byte[][] aliases;	// EX: Special:RandomPage has Special:Random as alias
	public byte[] Display_ttl()		{return display_ttl;} private byte[] display_ttl; public Xow_special_meta Display_ttl_(String v) {display_ttl = BryUtl.NewU8(v); return this;}
	public String Url__home() {
		return StringUtl.Concat(gplx.xowa.wikis.domains.Xow_domain_itm_.Str__home, gplx.xowa.htmls.hrefs.Xoh_href_.Str__wiki, ttl_str);
	}

	public boolean Match_ttl(Xoa_ttl ttl) {
		return ttl.Ns().Id_is_special() && BryLni.Eq(ttl.Root_txt(), key_bry);
	}

	public static Xow_special_meta New_xo(String key, String display, String... aliases) {
		return new Xow_special_meta(Xow_special_meta_.Src__xowa, key, aliases).Display_ttl_(display);
	}
}
