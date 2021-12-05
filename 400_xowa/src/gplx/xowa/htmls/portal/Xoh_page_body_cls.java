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
package gplx.xowa.htmls.portal; import gplx.*;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.wikis.nss.*;
public class Xoh_page_body_cls {	// REF.MW:Skin.php|getPageClasses
	public static byte[] Calc(Bry_bfr tmp_bfr, Xoa_ttl ttl, int page_tid) {
		tmp_bfr.Add(Bry_id_prefix).Add_int_variable(ttl.Ns().Id());						// ns-0; note that special is ns--1 DATE:2014-09-24
		Add_type(tmp_bfr, ttl);															// ns-special || ns-talk || ns-subject
		tmp_bfr.Add_byte_space().Add(Bry_page_prefix).Add(Escape_cls(ttl.Full_db()));	// page-Page_title
		if (page_tid == Xow_page_tid.Tid_json) {
			switch (ttl.Ns().Id()) {
				case Xow_ns_.Tid__main:
					tmp_bfr.Add_byte_space().Add(Bry_wb_entitypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_itempage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_itempage).Add_byte(AsciiByte.Dash).Add(ttl.Page_db());
					break;
				case Wdata_wiki_mgr.Ns_property:
					tmp_bfr.Add_byte_space().Add(Bry_wb_entitypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_propertypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_propertypage).Add_byte(AsciiByte.Dash).Add(ttl.Page_db());
					break;
				default:
					Gfo_usr_dlg_.Instance.Warn_many("", "", "unexpected ns for page_body_cls; ttl=~{0}", String_.new_u8(ttl.Raw()));
					break;
			}
		}
		return tmp_bfr.To_bry_and_clear();
	}
	private static void Add_type(Bry_bfr tmp_bfr, Xoa_ttl ttl) {
		tmp_bfr.Add_byte_space();
		if (ttl.Ns().Id_is_special()) {
			tmp_bfr.Add(Bry_type_special);	// MW_TODO: add " mw-special-$canonicalName"
		}
		else if (ttl.Ns().Id_is_talk())
			tmp_bfr.Add(Bry_type_talk);
		else
			tmp_bfr.Add(Bry_type_subject);
	}
	public static byte[] Escape_cls(byte[] src) {	// REF.MW:Sanitizer.php|escapeClass; return rtrim( preg_replace(array( '/(^[0-9\\-])|[\\x00-\\x20!"#$%&\'()*+,.\\/:;<=>?@[\\]^`{|}~]|\\xC2\\xA0/', '/_+/' ), '_', $class ), '_' );
		Bry_bfr trg_bfr = null;
		int src_len = src.length; int bgn = -1;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Space:
				case AsciiByte.Bang: case AsciiByte.Quote: case AsciiByte.Hash: case AsciiByte.Dollar: case AsciiByte.Percent:
				case AsciiByte.Amp: case AsciiByte.Apos: case AsciiByte.ParenBgn: case AsciiByte.ParenEnd: case AsciiByte.Star:
				case AsciiByte.Plus: case AsciiByte.Comma: case AsciiByte.Dot: case AsciiByte.Backslash: case AsciiByte.Slash:
				case AsciiByte.Colon: case AsciiByte.Semic: case AsciiByte.Gt: case AsciiByte.Eq: case AsciiByte.Lt:
				case AsciiByte.Question: case AsciiByte.At: case AsciiByte.BrackBgn: case AsciiByte.BrackEnd:
				case AsciiByte.Pow: case AsciiByte.Tick:
				case AsciiByte.CurlyBgn: case AsciiByte.Pipe: case AsciiByte.CurlyEnd: case AsciiByte.Tilde:
					if (trg_bfr == null)
						src[i] = AsciiByte.Underline;
					else {
						if (bgn != -1) {
							trg_bfr.Add_mid(src, bgn, i);
							bgn = -1;
						}
						trg_bfr.Add_byte(AsciiByte.Underline);
					}
					break;
				case AsciiByte.Underline:
					if (trg_bfr == null) {
						trg_bfr = Bry_bfr_.New_w_size(src_len);
						trg_bfr.Add_mid(src, 0, i);
					}
					if (bgn != -1) {
						trg_bfr.Add_mid(src, bgn, i);
						bgn = -1;
					}
					int repeat = 0;
					for (int j = i + 1; j < src_len; ++j) {
						if (src[j] == AsciiByte.Underline)
							++repeat;
						else
							break;
					}
					trg_bfr.Add_byte(AsciiByte.Underline);
					i += repeat;
					break;
				case -62:
					int next = i + 1;
					if (next < src_len && src[next] == -96) {	
						if (trg_bfr == null) {
							trg_bfr = Bry_bfr_.New_w_size(src_len);
							trg_bfr.Add_mid(src, 0, i);
						}
						trg_bfr.Add_byte(AsciiByte.Underline);
						++i;
						continue;
					}
					if (trg_bfr != null && bgn == -1)
						bgn = i;
					break;
				default:
					if (trg_bfr != null && bgn == -1)
						bgn = i;
					break;
			}
		}
		if (bgn != -1) trg_bfr.Add_mid(src, bgn, src_len);
		return trg_bfr == null ? src : trg_bfr.To_bry_and_clear();
	}
	private static final byte[]
	  Bry_id_prefix			= Bry_.new_a7("ns-")
	, Bry_type_special		= Bry_.new_a7("ns-special")
	, Bry_type_talk			= Bry_.new_a7("ns-talk")
	, Bry_type_subject		= Bry_.new_a7("ns-subject")
	, Bry_page_prefix		= Bry_.new_a7("page-")
	, Bry_wb_entitypage		= Bry_.new_a7("wb-entitypage")
	, Bry_wb_itempage		= Bry_.new_a7("wb-itempage")
	, Bry_wb_propertypage	= Bry_.new_a7("wb-propertypage")
	;
	public static int Page_tid_wikitext = 0, Page_tid_wikidata_qid = 1, Page_tid_wikidata_pid = 2;
}
