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
package gplx.xowa.users.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.envs.*;
class Xofs_url_itm {
	public boolean Tid_is_xowa() {return tid_is_xowa;} public void Tid_is_xowa_(boolean v) {tid_is_xowa = v;} private boolean tid_is_xowa = true;
	public String Raw() {return raw;} public void Raw_(String v) {raw = v;} private String raw;
	public String Url() {return url;} public void Url_(String v) {url = v;} private String url;
}
class Xofs_url_itm_parser {
	private static final    byte[] Xowa_fs_protocol = Bry_.new_a7("xowa-fs://");
	private static final    int Xowa_fa_protocol_len = Xowa_fs_protocol.length;
	private Bry_bfr url_bfr = Bry_bfr_.Reset(16);
	private Hash_adp_bry names = Hash_adp_bry.cs();
	public byte Dir_spr() {return dir_spr;} public void Dir_spr_(byte v) {dir_spr = v;} private byte dir_spr = Op_sys.Cur().Fsys_dir_spr_byte();
	public void Names_add(String key_str, String val_str) {
		byte[] key_bry = Bry_.new_u8(key_str);
		byte[] val_bry = Bry_.new_u8(val_str);
		names.Add_if_dupe_use_nth(key_bry, val_bry);
	}
	public void Parse(Xofs_url_itm itm, String raw_str) {
		itm.Raw_(raw_str);
		byte[] raw = Bry_.new_u8(raw_str);
		if (!Bry_.Has_at_bgn(raw, Xowa_fs_protocol)) {	// raw does not start with "xowa-fs://"; mark as custom str and exit
			itm.Tid_is_xowa_(false);
			itm.Url_(raw_str);
			return;
		}
		itm.Tid_is_xowa_(true);
		url_bfr.Clear();
		int raw_len = raw.length;
		for (int i = Xowa_fa_protocol_len; i < raw_len; ++i) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Slash:			// "xowa-fs://" defines "/" as dir separator
					url_bfr.Add_byte(dir_spr);	// swap out to cur os dir_spr
					break;
				case Byte_ascii.Tilde:			// "xowa-fs://" defines "~{" as swap symbol
					int remaining = raw_len - i - 1;	// -1
					if (remaining > 0 && raw[i + 1] == Byte_ascii.Curly_bgn) {	// "~{"
						if (remaining > 2 && raw[i+2] == Byte_ascii.Tilde && raw[i+3] == Byte_ascii.Curly_bgn) { // "~{~{" -> "~{"
							url_bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Curly_bgn);
							i += 3;
						}
						else {
							int name_bgn = i + 2;	// skip "~{"
							int name_end = Bry_find_.Find_fwd(raw, Byte_ascii.Curly_end, name_bgn);
							byte[] name = (byte[])names.Get_by_mid(raw, name_bgn, name_end);
							if (name == null) throw Err_.new_wo_type("name not found", "raw", raw_str, "name", String_.new_u8(raw, name_bgn, name_end));
							url_bfr.Add(name);
							i = name_end;
						}
					}
					else {
						url_bfr.Add_byte(b);
					}
					break;
				default:
					url_bfr.Add_byte(b);
					break;
			}
		}
		itm.Url_(url_bfr.To_str_and_clear());
	}
}
