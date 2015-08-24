/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.langs.*;
public class Xow_abrv_xo_ {
	public static byte[] To_bry(byte[] domain_bry) {
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(domain_bry);
		return Xow_abrv_xo_.To_bry(domain_itm.Domain_bry(), domain_itm.Lang_orig_key(), domain_itm.Domain_type());
	}
	public static byte[] To_bry(byte[] domain_bry, byte[] lang_key, Xow_domain_type type) {	// en.wikipedia.org -> en.w			
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
		Xow_domain_type type = null;
		int dot_pos = Bry_finder.Find_fwd(src, Byte_ascii.Dot);
		if (dot_pos != Bry_finder.Not_found) {	// dot found; EX: "en.w"
			type = Xow_domain_type_.Get_abrv_as_itm(src, dot_pos + 1, src_len);
			if (type != null) {		// type found; EX: ".w"
				Xol_lang_itm lang = Xol_lang_itm_.Get_by_key(src, 0, dot_pos);
				if (lang != null)	// lang found; EX: "en."
					domain_bry = Bry_.Add(lang.Key(), type.Domain_bry());
			}
		}
		else {						// dot missing; EX: "c"
			type = Xow_domain_type_.Get_abrv_as_itm(src, 0, src_len);
			if (type != null) {		// type found; EX: "c"
				domain_bry = type.Domain_bry();
			}
		}
		return Xow_domain_itm_.parse(domain_bry);	// for consolidation's sake, parse abrv to domain_bry and pass to Xow_domain_itm_.parse_()
	}
}
