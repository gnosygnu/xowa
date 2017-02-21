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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.btries.*;
import gplx.core.net.qargs.*;
import gplx.langs.htmls.encoders.*;
public class Gfo_url_parser {
	private final    Btrie_slim_mgr protocols = Btrie_slim_mgr.ci_a7();	// ASCII:url_protocol; EX:"http:", "ftp:", etc
	private final    Btrie_rv trv = new Btrie_rv();
	private final    List_adp segs_list = List_adp_.New(), qargs_list = List_adp_.New();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(500);
	public Gfo_url_parser() {
		Init_protocols(Gfo_protocol_itm.Ary());
		Init_protocol_itm(Gfo_protocol_itm.Bry_relative, Gfo_protocol_itm.Tid_relative_1);
		Init_protocol_itm(Gfo_protocol_itm.Bry_file, Gfo_protocol_itm.Tid_file);
		Init_protocol_itm(gplx.xowa.parsers.lnkes.Xop_lnke_wkr.Bry_xowa_protocol, Gfo_protocol_itm.Tid_xowa);
	}
	public byte[] Relative_url_protocol_bry() {return Gfo_protocol_itm.Itm_https.Key_w_colon_bry();}	// NOTE: https b/c any WMF wiki will now default to WMF; DATE:2015-07-26
	private void Init_protocols(Gfo_protocol_itm... itms) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Gfo_protocol_itm itm = itms[i];
			Init_protocol_itm(itm.Key_w_colon_bry(), itm.Tid());
		}
	}
	public void Init_protocol_itm(byte[] key, byte protocol_tid) {protocols.Add_bry_byte(key, protocol_tid);}
	public void Parse_site_fast(Gfo_url_site_data site_data, byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn; boolean rel = false;
		if (pos + 1 < src_end && src[pos] == Byte_ascii.Slash && src[pos + 1] == Byte_ascii.Slash) {	// starts with "//"
			pos += 2;
			rel = true;
		}
		if (!rel) {	// search for ":"; NOTE: only search if not rel; i.e.: "//"
			int colon_pos = Bry_find_.Find_fwd(src, Byte_ascii.Colon, pos, src_end);		// no colon found; EX: "//a.org/b"; "a.org/b"
			if (colon_pos != Bry_find_.Not_found)											// colon found; EX: "http://" or "https://"
				pos = colon_pos + Int_.Const_dlm_len;
			if (pos < src_end && src[pos] == Byte_ascii.Slash) {							// skip slash after colon
				pos += 1;
				if (pos < src_end && src[pos] == Byte_ascii.Slash)							// skip 2nd slash after colon
					pos += 1;
			}
		}
		int slash_pos = Bry_find_.Find_fwd(src, Byte_ascii.Slash, pos, src_end);
		if (slash_pos == Bry_find_.Not_found)												// no terminating slash; EX: http://a.org
			slash_pos = src_end;
		slash_pos = Bry_.Trim_end_pos(src, slash_pos);
		site_data.Atrs_set(rel, pos, slash_pos);
	}
	public Gfo_url Parse(byte[] src) {return Parse(src, 0, src.length);}
	public Gfo_url Parse(byte[] src, int src_bgn, int src_end) {
		// protocol
		byte protocol_tid = protocols.Match_byte_or(trv, src, src_bgn, src_end, Gfo_protocol_itm.Tid_unknown);
		int pos = Bry_find_.Find_fwd_while(src, trv.Pos(), src_end, Byte_ascii.Slash);	// set pos after last slash; EX: "https://A" -> position before "A"
		byte[] protocol_bry = protocol_tid == Gfo_protocol_itm.Tid_unknown
			? null
			: Make_bry(false, src, src_bgn, pos);

		// loop chars and handle "/", "#", "?", and "%"
		boolean encoded = false;
		int src_zth = src_end - 1;
		int anch_bgn = -1, qarg_bgn = -1, seg_bgn = pos;
		for (int i = pos; i < src_end; ++i) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Slash:
					if (qarg_bgn == -1) {		// ignore slash in qargs
						segs_list.Add(Make_bry(encoded, src, seg_bgn, i));
						encoded = false;
						seg_bgn = i + 1;		// +1 to skip "/"
					}
					break;
				case Byte_ascii.Hash:			// set qarg to first #; also, ignore rest of String; EX: A#B#C -> B#C
					if (i == src_zth) continue;	// ignore # at EOS; EX: "A#"
					anch_bgn = i;
					i = src_end;
					break;
				case Byte_ascii.Question:		// set qarg to last "?"; EX: A?B?C -> C
					if (i == src_zth) continue;	// ignore ? at EOS; EX: "A?"
					qarg_bgn = i;
					break;
				case Byte_ascii.Percent:
					encoded = true;
					break;
			}
		}
		
		int seg_end = src_end;	// set seg_end to src_end; EX: "https://site/A" -> "A"; seg_end may be overriden if "#" or "?" exists

		// set anch
		byte[] anch = null;
		if (anch_bgn != -1) {
			seg_end = anch_bgn;	// set seg_end to anch_bgn; EX: "https://site/A#B" -> "A" x> "A#B"
			anch = Make_bry(encoded, src, anch_bgn + 1, src_end);	// +1 to skip "#"
		}

		// set qargs
		Gfo_qarg_itm[] qarg_ary = Gfo_qarg_itm.Ary_empty;
		if (qarg_bgn != -1) {
			int qarg_end = anch_bgn == -1 
				? src_end	// # missing; set to src_end; EX: "A?B=C" -> EOS
				: anch_bgn;	// # exists; set to anch_bgn; EX: "A?B=C#D" -> #
			qarg_ary = Make_qarg_ary(src, qarg_bgn, qarg_end);
			seg_end = qarg_ary.length == 0
				? src_end	// set seg_end to src_end if pseudo qarg; EX: "https://site/A?B" -> "A?B" x> "A"
				: qarg_bgn;	// set seg_end to qarg_bgn; EX: "https://site/A?B=C" -> "A" x> "A#B"; NOTE: overrides anch;  "A?B=C#D" -> "A"
		}

		// extract seg_end; note that there will always be a seg_end; if src ends with slash, then it will be ""; EX: "A/" -> "A", ""
		segs_list.Add(Make_bry(encoded, src, seg_bgn, seg_end));

		// build url and return it
		return new Gfo_url(src, protocol_tid, protocol_bry, (byte[][])segs_list.To_ary_and_clear(byte[].class), qarg_ary, anch);
	}
	private Gfo_qarg_itm[] Make_qarg_ary(byte[] src, int qarg_bgn, int qarg_end) {
		// init
		int key_bgn = qarg_bgn + 1;	// +1 to skip "?"
		byte[] key_bry = null;
		int val_bgn = -1;
		boolean encoded = false;

		// loop qarg for "&", "=", "%"
		int qarg_pos = qarg_bgn;
		while (true) {
			boolean b_is_last = qarg_pos == qarg_end;
			byte b = b_is_last ? Byte_ascii.Null : src[qarg_pos];
			boolean make_qarg = false;
			switch (b) {
				case Byte_ascii.Amp:	// "&" always makes qarg
					make_qarg = true;
					break;
				case Byte_ascii.Null:	// "EOS" makes qarg as long as "=" seen or at least one qarg; specifically, "A?B" shouldn't make qarg
					if (	val_bgn != -1				// "=" seen; EX: "?A=B"
						||	qargs_list.Count() > 0)		// at least one qarg exists; EX: "?A=B&C"
						make_qarg = true;
					break;
				case Byte_ascii.Eq:
					key_bry = Make_bry(encoded, src, key_bgn, qarg_pos);
					encoded = false;
					val_bgn = qarg_pos + 1;
					break;
				case Byte_ascii.Percent:
					encoded = true;
					break;
			}

			// make qarg
			if (make_qarg) {
				byte[] val_bry = null;
				if (key_bry == null)	// key missing; EX: "&A" -> "A,null"
					key_bry = Make_bry(encoded, src, key_bgn, qarg_pos);
				else					// key exists; EX: "&A=B" -> "A,B"
					val_bry = Make_bry(encoded, src, val_bgn, qarg_pos);
				encoded = false;
				qargs_list.Add(new Gfo_qarg_itm(key_bry, val_bry));

				// reset vars
				key_bry = null;
				key_bgn = qarg_pos + 1;
				val_bgn = -1;
			}
			if (b_is_last) break;
			++qarg_pos;
		}
		return (Gfo_qarg_itm[])qargs_list.To_ary_and_clear(Gfo_qarg_itm.class);
	} 
	private byte[] Make_bry(boolean encoded, byte[] src, int bgn, int end) {
		return encoded ? Gfo_url_encoder_.Xourl.Decode(tmp_bfr, Bool_.N, src, bgn, end).To_bry_and_clear() : Bry_.Mid(src, bgn, end);
	}

	public static final    byte[] Bry_double_slash = new byte[] {Byte_ascii.Slash, Byte_ascii.Slash};
}
