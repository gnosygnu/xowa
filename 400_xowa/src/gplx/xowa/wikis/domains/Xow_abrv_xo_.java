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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*;
public class Xow_abrv_xo_ {
	public static byte[] To_bry(byte[] domain_bry) {
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
		return Xow_abrv_xo_.To_bry(domain_itm.Domain_bry(), domain_itm.Lang_orig_key(), domain_itm.Domain_type());
	}
	public static byte[] To_bry(byte[] domain_bry, byte[] lang_key, Xow_domain_tid type) {	// en.wikipedia.org -> en.w			
		byte[] type_abrv = type.Abrv();
		if		(type.Multi_lang())			// wikipedia,wiktionary,etc..
			return Bry_.Add(lang_key, Byte_ascii.Dot_bry, type_abrv);
		else if (type_abrv.length > 0)		// commons,wbase,species,etc..
			return type_abrv;
		else								// home;wikia;others
			return domain_bry;
	}
	public static Xow_domain_itm To_itm(byte[] src) {
		int src_len = src.length;
		byte[] domain_bry = src;	// default to src; handles unknown abrv like "a.wikia.com";"xowa";others
		Xow_domain_tid type = null;
		int dot_pos = Bry_find_.Find_fwd(src, Byte_ascii.Dot);
		if (dot_pos != Bry_find_.Not_found) {	// dot found; EX: "en.w"
			type = Xow_domain_tid_.Get_abrv_as_itm(src, dot_pos + 1, src_len);
			if (type != null) {		// type found; EX: ".w"
				Xol_lang_stub lang = Xol_lang_stub_.Get_by_key_or_null(src, 0, dot_pos);
				if (lang != null)	// lang found; EX: "en."
					domain_bry = Bry_.Add(lang.Key(), type.Domain_bry());
			}
		}
		else {						// dot missing; EX: "c"
			type = Xow_domain_tid_.Get_abrv_as_itm(src, 0, src_len);
			if (type != null) {		// type found; EX: "c"
				domain_bry = type.Domain_bry();
			}
		}
		return Xow_domain_itm_.parse(domain_bry);	// for consolidation's sake, parse abrv to domain_bry and pass to Xow_domain_itm_.parse()
	}
}
