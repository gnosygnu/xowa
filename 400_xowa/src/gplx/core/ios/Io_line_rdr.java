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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.ios.streams.*;
public class Io_line_rdr {
	public Io_line_rdr (Gfo_usr_dlg usr_dlg, Io_url... urls) {this.usr_dlg = usr_dlg; this.urls = urls; if (urls.length == 0) bfr_state = Bfr_state_end;} Gfo_usr_dlg usr_dlg;
	public int Url_idx() {return url_idx;} private int url_idx;
	public Io_url[] Urls() {return urls;} Io_url[] urls;
	public void Reset_one(Io_url url) {
		this.Clear();
		urls[0] = url;
		url_idx = 0;
	}
	public byte Line_dlm() {return line_dlm;} public Io_line_rdr Line_dlm_(byte v) {line_dlm = v; return this;} private byte line_dlm = Byte_ascii.Nl;
	public byte[] Bfr() {return bfr;} private byte[] bfr;
	public int Bfr_len() {return bfr_len;} private int bfr_len;
	public byte Bfr_state() {return bfr_state;} private byte bfr_state = Bfr_state_bgn; static final byte Bfr_state_bgn = 0, Bfr_state_mid = 1, Bfr_state_end = 2;
	public int Bfr_last_read() {return bfr_last_read;} private int bfr_last_read;
	public void Bfr_last_read_add(int v) {bfr_last_read += v;}
	public int Load_len() {return load_len;} public Io_line_rdr Load_len_(int v) {load_len = v; return this;} private int load_len = 4096;
	long File_len() {return file_len;} long file_len = 0;
	long File_pos() {return file_pos;} long file_pos = 0;
	public boolean File_skip_line0() {return file_skip_line0;} public Io_line_rdr File_skip_line0_(boolean v) {file_skip_line0 = v; return this;} private boolean file_skip_line0;
	public int Itm_pos_bgn() {return itm_pos_bgn;} private int itm_pos_bgn = 0;
	public int Itm_pos_end() {return itm_pos_end;} private int itm_pos_end = 0;
	public int Key_pos_bgn() {return key_pos_bgn;} public Io_line_rdr Key_pos_bgn_(int v) {key_pos_bgn = v; return this;} private int key_pos_bgn = -1;
	public int Key_pos_end() {return key_pos_end;} public Io_line_rdr Key_pos_end_(int v) {key_pos_end = v; return this;} private int key_pos_end = -1;
	public Io_line_rdr_key_gen Key_gen() {return key_gen;} public Io_line_rdr Key_gen_(Io_line_rdr_key_gen v) {key_gen = v; return this;} Io_line_rdr_key_gen key_gen = Io_line_rdr_key_gen_.first_pipe;
	public void Truncate(int pos) {
		this.Read_next();
		int end = Bry_find_.Find_fwd(bfr, Byte_ascii.Null); if (end == -1) end = bfr.length;
		bfr = Bry_.Mid(bfr, pos, end);
		bfr_len = bfr.length;
		bfr_last_read = 0;
	}
	public boolean Read_next() {
		switch (bfr_state) {
			case Bfr_state_bgn:
				bfr_state = Bfr_state_mid;	// do not place after Load
				Open_fil();
				if (!Load()) return false;
				break;
			case Bfr_state_end:
				return false;
		}
		itm_pos_bgn = bfr_last_read; itm_pos_end = bfr_len; key_pos_bgn = key_pos_end = -1;
		while (true) {
			for (int i = bfr_last_read; i < bfr_len; i++) {
				if (bfr[i] == line_dlm) {
					itm_pos_end = i + 1;	// +1: include find
					bfr_last_read = itm_pos_end;
					key_gen.Gen(this);
					return true;
				}
			}
			if (Load())				// line_dlm not found; load more
				itm_pos_bgn = 0;
			else {					// nothing loaded; return;
				itm_pos_end = bfr_len;
				key_gen.Gen(this);	// call key_gen b/c there may be a stray line at end; EX: "\nb|c"; call key_gen to get "b"
				bfr_state = Bfr_state_end;
				return bfr_last_read < bfr_len;	// true if stray line at end; otherwise bfr_last_read == bfr_len and return false;
			}
		}
	}
	public boolean Match(byte[] ttl) {
		switch (bfr_state) {
			case Bfr_state_bgn:
				if (!Read_next()) return false;
				bfr_state = Bfr_state_mid;	// NOTE: must set back to mid; possible for 1st read to read entire buffer; EX: 8 MB bfr, but only 1 MB file;
				break;
			case Bfr_state_end:
				return false;
		}
		while (true) {
			int compare = Bry_.Compare(ttl, 0, ttl.length, bfr, key_pos_bgn, key_pos_end);
			if 		(compare == CompareAble_.Same) {	// eq; return true and move fwd; EX: "BA" and "BA"
				return true;
			}
			else if (compare <  CompareAble_.Same) {	// lt; return false; EX: ttl is "BA" but rdr is "BC"
				return false;
			}
			else {										// gt; keep reading; EX: ttl is "BC" but rdr is "BA"
				if (!this.Read_next()) return false;				
			}
		}
	}
	boolean Load() {
		int old_bfr_len = bfr_len - bfr_last_read;	// NOTE: preserve bytes between bfr_last_read and bfr_len; EX: "ab\nc"; preserve "c" for next ary
		if (file_done) {++url_idx;  if (url_idx == urls.length) {bfr_state = Bfr_state_end; return false;} Open_fil(); file_done = false;}
		byte[] load_ary = new byte[old_bfr_len + load_len]; int load_ary_len = load_ary.length; // NOTE: must go after file_done chk; otherwise small wikis will allocate another 32 MB bry for sort_memory_len and cause an OutOfMemory error; DATE:20130112
		int read_len = stream.Read(load_ary, old_bfr_len, load_ary_len - old_bfr_len);
		if (read_len == 0) {	// nothing read; return;
			++url_idx;
			if (url_idx < urls.length) {
				stream.Rls();
				Open_fil();
				return true;
			}
			else {
				stream.Rls();
				bfr_state = Bfr_state_end;
				return false;				
			}
		}
		if (read_len == file_len) {
			stream.Rls();
			file_done = true;
//				++url_idx;
//				bfr_state = Bfr_state_end;
		}
		if (old_bfr_len > 0) Array_.Copy_to(bfr, bfr_last_read, load_ary, 0, old_bfr_len);	// copy old_bfr over	
		file_pos += read_len;
		bfr = load_ary;
		bfr_last_read = 0;
		bfr_len = read_len == load_len ? load_ary_len : old_bfr_len + read_len;	// stream.Read() may return less bytes than load_ary at EOF; if so, don't shrink bfr; just mark bfr_len less
		return true;
	}	IoStream stream;
	private void Open_fil() {
		Io_url url = urls[url_idx];
		usr_dlg.Prog_many(GRP_KEY, "load", "loading dump file: ~{0}", url.NameAndExt());
		if (file_skip_line0) {
			byte[] stream_bry = Io_mgr.Instance.LoadFilBry(url);
			int stream_bry_len = stream_bry.length;
			int nl_pos = Bry_find_.Find_fwd(stream_bry, Byte_ascii.Nl, 0, stream_bry_len);
			if (nl_pos == Bry_find_.Not_found)
				stream_bry = Bry_.Empty;
			else
				stream_bry = Bry_.Mid(stream_bry, nl_pos + 1, stream_bry_len);
			stream = gplx.core.ios.streams.IoStream_.ary_(stream_bry);
		}
		else {
			stream = Io_mgr.Instance.OpenStreamRead(url);
		}
		file_pos = 0; file_len = stream.Len();
		file_done = false;
	}	boolean file_done = false;
	public void Clear() {
		bfr_state = Bfr_state_bgn;
		if (stream != null) stream.Rls();
		bfr = null;
	}
	public void Rls() {
		this.Clear();
		bfr = null;
	}
	static final String GRP_KEY = "xowa.bldr.line_rdr";
}
