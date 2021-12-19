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
package gplx.xowa.parsers.utils;
import gplx.core.btries.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.parsers.amps.*; import gplx.core.log_msgs.*;
import gplx.langs.htmls.entitys.*;
public class Xop_sanitizer {
	private Btrie_slim_mgr trie = Btrie_slim_mgr.cs(), amp_trie;
	private Xop_amp_mgr amp_mgr;
	private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public Xop_sanitizer(Xop_amp_mgr amp_mgr, Gfo_msg_log msg_log) {
		this.amp_mgr = amp_mgr; this.amp_trie = amp_mgr.Amp_trie();
		trie_add("&"	, Tid_amp);
		trie_add(" "	, Tid_space);
		trie_add("%3A"	, Tid_colon);
		trie_add("%3a"	, Tid_colon);
		trie_add("%"	, Tid_percent);
	}
	private void trie_add(String hook, byte tid) {trie.Add_stub(hook, tid);}
	public byte[] Escape_id(byte[] src) {
		boolean dirty = Escape_id(src, 0, src.length, tmp_bfr);
		return dirty ? tmp_bfr.ToBryAndClear() : src;
	}
	public boolean Escape_id(byte[] src, int bgn, int end, BryWtr bfr) {
		boolean dirty = false;
		int pos = bgn;
		boolean loop = true;
		while (loop) {
			if (pos == end) break;
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, end);
			if (o == null) {
				if (dirty) bfr.AddByte(b);
				++pos;
			}
			else {
				if (!dirty) {
					bfr.AddMid(src, bgn, pos);
					dirty = true;
				}
				Btrie_itm_stub stub = (Btrie_itm_stub)o;
				switch (stub.Tid()) {
					case Tid_space:		bfr.AddByte(AsciiByte.Underline)	; ++pos		; break;
					case Tid_percent:	bfr.AddByte(AsciiByte.Percent)	; ++pos		; break;
					case Tid_colon:		bfr.AddByte(AsciiByte.Colon)		; pos += 3	; break;
					case Tid_amp:
						++pos;
						if (pos == end) {
							bfr.AddByte(AsciiByte.Amp);
							loop = false;
							continue;
						}
						b = src[pos];
						Object amp_obj = amp_trie.Match_bgn_w_byte(b, src, pos, end);
						if (amp_obj == null) {
							bfr.AddByte(AsciiByte.Amp);
							bfr.AddByte(b);
							++pos;
						}
						else {
							Gfh_entity_itm itm = (Gfh_entity_itm)amp_obj;
							byte itm_tid = itm.Tid();
							switch (itm_tid) {
								case Gfh_entity_itm.Tid_name_std:
								case Gfh_entity_itm.Tid_name_xowa:
									bfr.Add(itm.U8_bry());
									pos += itm.Key_name_len() + 1;	// 1 for trailing ";"; EX: for "&nbsp; ", (a) pos is at "&", (b) "nbsp" is Key_name_len, (c) ";" needs + 1 
									break;
								case Gfh_entity_itm.Tid_num_dec:
								case Gfh_entity_itm.Tid_num_hex:
									Xop_amp_mgr_rslt rv = new Xop_amp_mgr_rslt();
									amp_mgr.Parse_ncr(rv, itm_tid == Gfh_entity_itm.Tid_num_hex, src, end, pos - 1, pos + itm.Xml_name_bry().length);
									if (rv.Pass())
										bfr.AddU8Int(rv.Val());
									else
										bfr.AddByte(AsciiByte.Amp);
									pos = rv.Pos();
									break;
							}
						}
						break;
				}
			}
		}
		return dirty;
	}
//	static function escapeClass( $class ) {
//		// Convert ugly stuff to underscores and kill underscores in ugly places
//		return rtrim( preg_replace(
//			array( '/(^[0-9\\-])|[\\x00-\\x20!"#$%&\'()*+,.\\/:;<=>?@[\\]^`{|}~]|\\xC2\\xA0/', '/_+/' ),
//			'_',
//			$class ), '_' );
//	}
	public static byte[] Escape_cls(byte[] v) {
		return v;
	}
	static final byte Tid_amp = 1, Tid_space = 2, Tid_colon = 3, Tid_percent = 4;
}
