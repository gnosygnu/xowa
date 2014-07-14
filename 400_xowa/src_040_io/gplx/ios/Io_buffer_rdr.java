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
package gplx.ios; import gplx.*;
import gplx.ios.*;/*IoStream*/
public class Io_buffer_rdr implements RlsAble {	//_20120115
	Io_buffer_rdr(Io_stream_rdr rdr, Io_url url, int bfr_len) {
		if (bfr_len <= 0) throw Err_.new_("bfr_len must be > 0").Add("bfr_len", bfr_len);
		bfr = new byte[bfr_len]; this.bfr_len = bfr_len;
		this.rdr = rdr;
		IoItmFil fil = Io_mgr._.QueryFil(url); if (!fil.Exists()) throw Err_.new_("fil does not exist").Add("url", url);
		fil_len = fil.Size();
		fil_pos = 0;
		fil_eof = false;
	}	Io_stream_rdr rdr;
	public byte[] Bfr() {return bfr;} private byte[] bfr;
	public int Bfr_len() {return bfr_len;} private int bfr_len;
	public long Fil_len() {return fil_len;} long fil_len;
	public long Fil_pos() {return fil_pos;} long fil_pos;
	public boolean Fil_eof() {return fil_eof;} private boolean fil_eof;
	public boolean Bfr_load_all() {return Bfr_load(0, bfr_len);}
	public boolean Bfr_load_from(int bfr_pos) {
		if (bfr_pos < 0 || bfr_pos > bfr_len) throw Err_.new_("invalid bfr_pos").Add("bfr_pos", bfr_pos).Add("bfr_len", bfr_len);
		for (int i = bfr_pos; i < bfr_len; i++)				// shift end of bfr to bgn; EX: bfr[10] and load_from(8); [8] -> [0]; [9] -> [1];
			bfr[i - bfr_pos] = bfr[i];
		return Bfr_load(bfr_len - bfr_pos, bfr_pos);		// fill rest of bfr; EX: [2]... will come from file
	}
	boolean Bfr_load(int bgn, int len) {
		int read = rdr.Read(bfr, bgn, len);
		if (read == gplx.ios.Io_stream_rdr_.Read_done) {fil_eof = true; return false;}
		fil_pos += read;
		bfr_len = bgn + read;
		if (read < len) fil_eof = true;
		return true;
	}
	public void Seek(long fil_pos) {
		this.fil_pos = fil_pos;
		rdr.Skip(fil_pos);
		this.Bfr_load_all();
	}
	public void Rls() {
		bfr = null;
		bfr_len = -1;
		if (rdr != null) rdr.Rls();
	}
	@gplx.Internal protected void DumpToFil(int bgn, int len, String urlStr, String msg) {
		String text = String_.new_utf8_len_safe_(bfr, bgn, len);
		Io_mgr._.AppendFilStr(Io_url_.new_any_(urlStr), msg + text + "\n");
	}
	public static Io_buffer_rdr new_(Io_stream_rdr rdr, int bfr_len) {
		Io_buffer_rdr rv = new Io_buffer_rdr(rdr, rdr.Url(), bfr_len);
		rdr.Open();
		rv.Bfr_load(0, bfr_len);
		return rv;
	}	Io_buffer_rdr() {}
	public static final Io_buffer_rdr Null = new Io_buffer_rdr();
}
