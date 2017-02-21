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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.core.encoders.*;
public class Process_stream_rdr {
	public Process_stream_rdr(byte[] bry_header, byte[] bry_body) {this.bry_header = bry_header; this.bry_body = bry_body;} private byte[] bry_header, bry_body;
	public IoStream_stream_rdr Rdr() {return rdr;} IoStream_stream_rdr rdr = new IoStream_stream_rdr();
	public byte[] Read() {
		int bytes_read = rdr.Read(bry_header, 0, 16); 
		if (bytes_read < 16) {
			if (bytes_read == -1) return null;	// stream closed; should only occur when shutting down
			else throw Err_.new_wo_type("failed to read header");
		}
		int body_len = Hex_utl_.Parse_or(bry_header, 0,8, -1); 	if (body_len == -1) throw Err_.new_wo_type("failed to read body_len");
		int chk_len= Hex_utl_.Parse_or(bry_header, 9, 16, -1);	if (chk_len == -1 || chk_len != (body_len * 2) - 1) throw Err_.new_wo_type("failed to read chk_len");
		byte[] trg_bry = (body_len > bry_body.length) ? new byte[body_len] : bry_body;
		return Read_body(trg_bry, body_len, rdr);
	}
	public byte[] Read_body(byte[] trg_bry, int src_len, IoStream rdr) {
		int src_len_orig = src_len;
		int src_pos = 0, trg_bgn = 0;
		boolean escaped = false, escaped_once = false;
		int adj = 0;
		while (src_pos < src_len_orig) {
			int read_len = rdr.Read(trg_bry, trg_bgn, src_len_orig - src_pos);	// NOTE: 1st requests 16k, but only get back 2k; 2nd request 14k and only gets back 2k; etc.
			for (int i = 0; i < read_len; i++) {
				int trg_idx = trg_bgn + i;
				byte b = trg_bry[trg_idx];
				if (escaped) {			// escaped mode; convert cur byte to appropriate byte
					switch (b) {
						case Byte_ascii.Ltr_n:		b = Byte_ascii.Nl; break;
						case Byte_ascii.Ltr_r: 		b = Byte_ascii.Cr; break; 
						case Byte_ascii.Backslash: 	b = Byte_ascii.Backslash; break;
						default: 					throw Err_.new_unhandled(b);
					}
				}
				else {					// regular mode
					if (b == Byte_ascii.Backslash) {
						escaped = true;	// flip flag
						escaped_once = true;
						--src_len;		// add 1 to src_len; EX: "a\\nb" has 4 chars in stream, but src_len is reported as 3
						++adj;			// add 1 to adj
						continue;		// skip section below; cur byte is backslash, and is discarded (next byte is important)
					}
				}
				if (adj > 0				// check if there is adj; if no adj, don't bother overwriting; if adj, then shift all characters backwards; SEE:NOTE_1
					|| escaped_once)	// NOTE: when trg_idx = 0, adj is always 0 but escaped can be true; occurs when straddling reads; EX: "a\nb\" -> 97
					trg_bry[trg_idx - adj] = b;
				escaped = false;
			}
			src_pos += read_len;
			trg_bgn += read_len;
		}
		return Bry_.Mid(trg_bry, 0, src_len);
	}
}
/*
NOTE_1:
EX: "a\nb\nc"
. has src_len of 5: MWServer.lua counts chars exactly
. has src_str of "a\\nb\\nc" (len of 7 characters: a, backslash, n, b, backslash, n, c); MWServer.lua converts \n -> \\n (or in ASCII, 10 -> 92 110)
. so, process is as follows
.. start :097 092 110 098 092 110 099	a \\ n b \\ n c)
.. pass_0:	097							a
.. pass_1:	097 092						\		escape mode entered; adj = 1
.. pass_2a: 097 092 110					n		n reached
.. pass_2b: 097 092 010							n converted to \n
.. pass_2c: 097 010 010							\n shifted down 1 to overwrite backslash
.. pass_3:	097 010 098 				b		b shifted down 1 to overwrite \n
.. pass_4:	097 010 098 092				\		escape mode entered; adj = 2
.. pass_5:	097 010 098 010				n		same as pass 2a-2c, except shift is 2
.. pass_6:	097 010 098 010 099			c
*/
