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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.langs.htmls.encoders.*;
public class Gfo_url_parser {
	private final Btrie_slim_mgr protocols = Btrie_slim_mgr.ci_a7();	// ASCII:url_protocol; EX:"http:", "ftp:", etc
	private final Bry_ary segs_ary = new Bry_ary(4), qargs = new Bry_ary(4);
	private final Url_encoder encoder = Url_encoder.new_html_href_mw_().Itms_raw_same_many(Byte_ascii.Underline);
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(500);
	public byte[] Relative_url_protocol_bry() {return Gfo_protocol_itm.Itm_https.Key_w_colon_bry();}	// NOTE: https b/c any WMF wiki will now default to WMF; DATE:2015-07-26
	public Gfo_url_parser() {
		Init_protocols(Gfo_protocol_itm.Ary());
		Init_protocol_itm(Gfo_protocol_itm.Bry_relative, Gfo_protocol_itm.Tid_relative_1);
		Init_protocol_itm(Gfo_protocol_itm.Bry_file, Gfo_protocol_itm.Tid_file);
		Init_protocol_itm(gplx.xowa.parsers.lnkes.Xop_lnke_wkr.Bry_xowa_protocol, Gfo_protocol_itm.Tid_xowa);
	}
	private void Init_protocols(Gfo_protocol_itm... itms) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Gfo_protocol_itm itm = itms[i];
			Init_protocol_itm(itm.Key_w_colon_bry(), itm.Tid());
		}
	}
	public void Init_protocol_itm(byte[] key, byte protocol_tid) {
		protocols.Add_bry_byte(key, protocol_tid);
	}
	public void Parse_site_fast(Gfo_url_site_data site_data, byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn; boolean rel = false;
		if (pos + 1 < src_end && src[pos] == Byte_ascii.Slash && src[pos + 1] == Byte_ascii.Slash) {	// starts with "//"
			pos += 2;
			rel = true;
		}
		if (!rel) {	// search for ":"; NOTE: only search if not rel; i.e.: "//"
			int colon_pos = Bry_find_.Find_fwd(src, Byte_ascii.Colon, pos, src_end);	// no colon found; EX: "//a.org/b"; "a.org/b"
			if (colon_pos != Bry_.NotFound)											// colon found; EX: "http://" or "https://"
				pos = colon_pos + Int_.Const_dlm_len;
			if (pos < src_end && src[pos] == Byte_ascii.Slash) {					// skip slash after colon
				pos += 1;
				if (pos < src_end && src[pos] == Byte_ascii.Slash)					// skip 2nd slash after colon
					pos += 1;
			}
		}
		int slash_pos = Bry_find_.Find_fwd(src, Byte_ascii.Slash, pos, src_end);
		if (slash_pos == Bry_.NotFound)												// no terminating slash; EX: http://a.org
			slash_pos = src_end;
		slash_pos = Bry_.Trim_end_pos(src, slash_pos);
		site_data.Atrs_set(rel, pos, slash_pos);
	}
	private static final int Area__path = 1, Area__qarg_key_1st = 2, Area__qarg_key_nth = 3, Area__qarg_val = 4, Area__anch = 5;
	private byte[] src; int src_bgn, src_end;
	private int area;
	private boolean encoded;
	private byte protocol_tid; private byte[] protocol_bry, anch;
	private int path_bgn, qarg_key_bgn, qarg_val_bgn, anch_bgn, anch_nth_bgn;
	public Gfo_url Parse(byte[] src) {return Parse(new Gfo_url(), src, 0, src.length);}
	public Gfo_url Parse(Gfo_url rv, byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_bgn = src_bgn; this.src_end = src_end;
		encoded = false;
		protocol_tid = Gfo_protocol_itm.Tid_null;
		protocol_bry = anch = null;
		path_bgn = qarg_key_bgn = qarg_val_bgn = anch_bgn = anch_nth_bgn = -1;
		segs_ary.Clear(); qargs.Clear();

		int pos = src_bgn;
		Object protocol_obj = protocols.Match_bgn(src, src_bgn, src_end);
		pos = protocols.Match_pos();
		pos = Bry_find_.Find_fwd_while(src, pos, src_end, Byte_ascii.Slash);
		if (protocol_obj == null) {
			this.protocol_tid = Gfo_protocol_itm.Tid_unknown;
		}
		else {
			this.protocol_tid = ((Byte_obj_val)protocol_obj).Val();
			this.protocol_bry = Make_bry(src_bgn, pos);
		}

		area = Area__path;
		path_bgn = pos;
		while (true) {
			if (pos == src_end) break;
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Slash:		pos = Parse_slash(pos, b); break;
				case Byte_ascii.Question:	pos = Parse_qarg_key_1st(pos, b); break;
				case Byte_ascii.Amp:		pos = Parse_qarg_key_nth(pos, b); break;
				case Byte_ascii.Eq:			pos = Parse_qarg_val(pos, b); break;
				case Byte_ascii.Hash:		pos = Parse_anch(pos, b); break;
				case Byte_ascii.Percent:	encoded = true; ++pos; break;
				default:
					++pos;
					break;
			}
		}
		End_area(pos, Byte_ascii.Null);
		rv.Ctor(src, protocol_tid, protocol_bry, segs_ary.To_ary(0), Make_qargs(), anch);
		return rv;
	}
	private int Parse_slash(int pos, byte b) {
		switch (area) {
			case Area__path:		return End_area(pos, b);
			default:				return pos + 1;
		}
	}
	private int Parse_anch(int pos, byte b) {
		switch (area) {
			case Area__path:
				End_area(pos, b);
				area = Area__anch;
				anch_bgn = pos + 1;
				break;
			case Area__anch:	// handle double; A#B#C -> "A#B", "C"
				Append_to_last_path(Byte_ascii.Hash, Make_bry(anch_bgn, pos));
				anch_bgn = pos + 1;
				break;
			case Area__qarg_val:
			case Area__qarg_key_1st:
			case Area__qarg_key_nth:
				if (anch_nth_bgn == -1)
					anch_nth_bgn = Bry_find_.Find_bwd(src, Byte_ascii.Hash, src_end);
				if (pos == anch_nth_bgn) {
					End_area(pos, b);
					area = Area__anch;
					anch_bgn = pos + 1;
				}
				break;
			default:
				break;
		}
		return pos + 1;
	}
	private int Parse_qarg_key_1st(int pos, byte b) {
		switch (area) {
			case Area__path:			// only valid way to start qarg; EX: A?B=C
				End_area(pos, b);
				area = Area__qarg_key_1st;
				qarg_key_bgn = pos + 1;
				break;
			case Area__qarg_key_1st:	// handle dupe; EX: A?B?C
			case Area__qarg_key_nth:	// handle dupe; EX: A?B=C&D?
			case Area__qarg_val:		// handle dupe; EX: A?B=?
				End_area(pos, b);
				Append_to_last_path__qargs();
				area = Area__qarg_key_1st;
				qarg_key_bgn = pos + 1;
				break;
		}
		return pos + 1;
	}
	private int Parse_qarg_key_nth(int pos, byte b) {
		switch (area) {
			case Area__path:			// ignore if qarg not started; EX: A&B
				break;
			case Area__qarg_key_1st:	// handle invalid; A?B&C
			case Area__qarg_key_nth:	// handle invalid; A?B=C&D&E=F
				End_area(pos, b);
				qargs.Add(null);
				area = Area__qarg_key_nth;
				qarg_key_bgn = pos + 1;
				break;
			case Area__qarg_val:
				End_area(pos, b);
				area = Area__qarg_key_nth;
				qarg_key_bgn = pos + 1;
				break;
		}
		return pos + 1;
	}
	private int Parse_qarg_val(int pos, byte b) {
		switch (area) {
			case Area__qarg_key_1st:
			case Area__qarg_key_nth:
				End_area(pos, b); break;
			default: break;
		}
		return pos + 1;
	}
	private int End_area(int pos, byte b) {
		switch (area) {
			case Area__path:
				segs_ary.Add(Make_bry(path_bgn, pos));
				path_bgn = pos + 1;
				break;
			case Area__qarg_key_1st:
			case Area__qarg_key_nth:
				if (b == Byte_ascii.Null && qargs.Len() == 0) // handle A?b but not A?b=c&d
					Append_to_last_path(Byte_ascii.Question, Make_bry(qarg_key_bgn, src_end));
				else {
					qargs.Add(Make_bry(qarg_key_bgn, pos));
					qarg_val_bgn = pos + 1;
					area = Area__qarg_val;
				}
				break;
			case Area__qarg_val:
				qargs.Add(Make_bry(qarg_val_bgn, pos));
				qarg_key_bgn = pos + 1;
				qarg_val_bgn = -1;
				area = Area__qarg_key_nth;
				break;
			case Area__anch:
				if (b == Byte_ascii.Null && anch_bgn == src_end) //  handle A# but not "A#B"
					Append_to_last_path(Byte_ascii.Hash, Make_bry(anch_bgn, src_end));
				else
					anch = Make_bry(anch_bgn, pos);
				break;
			default:
				break;
		}
		encoded = false;
		return pos + 1;
	}
	private byte[] Make_bry(int bgn, int end) {
		return encoded ? encoder.Decode(tmp_bfr, src, bgn, end) : Bry_.Mid(src, bgn, end);
	}
	private Gfo_qarg_itm[] Make_qargs() {
		int qargs_len = qargs.Len(); if (qargs_len == 0) return Gfo_qarg_itm.Ary_empty;
		if (qargs_len % 2 == 1) ++qargs_len;	// handle odd qargs; EX: ?A=B&C&D=E
		Gfo_qarg_itm[] rv = new Gfo_qarg_itm[qargs_len / 2];
		for (int i = 0; i < qargs_len; i += 2) {
			byte[] key = qargs.Get_at(i);
			int val_idx = i + 1;				
			byte[] val = val_idx < qargs_len ? qargs.Get_at(val_idx) : null;
			rv[i / 2] = new Gfo_qarg_itm(key, val);
		}
		return rv;
	}
	private void Append_to_last_path(byte b, byte[] append) {
		byte[] last_path = segs_ary.Get_at_last(); if (last_path == null) return;
		last_path = Bry_.Add_w_dlm(b, last_path, append);
		segs_ary.Set_at_last(last_path);
	}
	private void Append_to_last_path__qargs() {
		byte[] last_path = segs_ary.Get_at_last(); if (last_path == null) return;
		tmp_bfr.Add(last_path);
		int len = qargs.Len();
		if (len % 2 == 1) qargs.Add(null);	// handle odd qargs
		for (int i = 0; i < len; i += 2) {
			tmp_bfr.Add_byte(i == 0 ? Byte_ascii.Question : Byte_ascii.Amp);
			tmp_bfr.Add(qargs.Get_at(i));
			byte[] qarg_val = qargs.Get_at(i + 1);
			if (qarg_val != null)	// handle "null" added above
				tmp_bfr.Add_byte_eq().Add(qarg_val);
		}
		qargs.Clear();
		segs_ary.Set_at_last(tmp_bfr.To_bry_and_clear());
	}
	public static final byte[] Bry_double_slash = new byte[] {Byte_ascii.Slash, Byte_ascii.Slash};
}
