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
import org.junit.*;
public class Io_download_fmt_tst {
	Io_download_fmt_fxt fxt = new Io_download_fmt_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Fmt() {
		fxt.Clear().Ini("downloading ~{src_name}: ~{prog_left} left (@ ~{prog_rate}); ~{prog_done} of ~{src_len} (~{prog_pct}%)", "http://a.org/b.png", Io_mgr.Len_kb * 10);
		fxt.Now_add_f(1000).Prog_done_(1 * Io_mgr.Len_kb).Prog_pct_(1 * 1000).Prog_rate_(Io_mgr.Len_kb).Prog_left_(9 * 1000)
		.Prog_msg_("downloading b.png: 09s left (@ 1.000 KBps); 1.000 KB of 10.000 KB (10.00%)")
		.Download_(Io_mgr.Len_kb);
		fxt.Now_add_f(1000).Prog_done_(2 * Io_mgr.Len_kb).Prog_pct_(2 * 1000).Prog_rate_(Io_mgr.Len_kb).Prog_left_(8 * 1000)
		.Prog_msg_("downloading b.png: 08s left (@ 1.000 KBps); 2.000 KB of 10.000 KB (20.00%)")
		.Download_(Io_mgr.Len_kb)
		;
		fxt.Now_add_f(2000).Prog_done_(3 * Io_mgr.Len_kb).Prog_pct_(3 * 1000).Prog_rate_(768).Prog_left_(9333)
		.Prog_msg_("downloading b.png: 09s left (@ 768.000 Bps); 3.000 KB of 10.000 KB (30.00%)")
		.Download_(Io_mgr.Len_kb)
		;
	}
	@Test  public void Tilde() {
		fxt.Clear().Ini("a~b", "http://a.org/b.png", Io_mgr.Len_kb * 10);
	}
}
class Io_download_fmt_fxt {
	public Io_download_fmt_fxt Clear() {
		if (fmt == null) {
			fmt = new Io_download_fmt();
		}
		Env_.TickCount_Test = 0;
		prog_done = prog_rate = prog_pct = prog_left = -1;
		prog_msg = null;
		return this;
	}	Io_download_fmt fmt;
	public Io_download_fmt_fxt Now_add_f(int v) {Env_.TickCount_Test += v; return this;}
	public Io_download_fmt_fxt Prog_done_(int v) {prog_done = v; return this;} long prog_done = -1;
	public Io_download_fmt_fxt Prog_pct_ (int v) {prog_pct  = v; return this;} long prog_pct = -1;
	public Io_download_fmt_fxt Prog_rate_(int v) {prog_rate = v; return this;} long prog_rate = -1;
	public Io_download_fmt_fxt Prog_left_(int v) {prog_left = v; return this;} long prog_left = -1;
	public Io_download_fmt_fxt Prog_msg_(String v) {
		prog_msg = v; return this;
	}	String prog_msg;
	public Io_download_fmt_fxt Download_(int v) {
		fmt.Prog(v);
		if (prog_done != -1) Tfds.Eq(prog_done, fmt.Prog_done(), "prog_done");
		if (prog_pct  != -1) Tfds.Eq(prog_pct , fmt.Prog_pct(), "prog_pct");
		if (prog_rate != -1) Tfds.Eq(prog_rate, fmt.Prog_rate(), "prog_rate");
		if (prog_left != -1) Tfds.Eq(prog_left, fmt.Prog_left(), "prog_left");
		if (prog_msg != null) Tfds.Eq(prog_msg, fmt.Prog_msg(), "prog_msg");
		return this;
	}		
	public Io_download_fmt_fxt Ini(String prog_msg_hdr, String src_url, int src_len) {
		fmt.Init(src_url, prog_msg_hdr);
		fmt.Bgn(src_len);
		return this;
	}
}
