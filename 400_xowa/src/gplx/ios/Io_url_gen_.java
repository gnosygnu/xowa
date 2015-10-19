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
public class Io_url_gen_ {
	public static Io_url_gen dir_(Io_url v) {return new Io_url_gen_dir(v);}
	public static Io_url_gen dir_(Io_url v, String fmt, int digits) {return new Io_url_gen_dir(v).Fmt_(fmt).Fmt_digits_(digits);}
	public static Io_url_gen fil_(Io_url v) {return new Io_url_gen_fil(v);}
}
class Io_url_gen_dir implements Io_url_gen {
	public String Fmt() {return fmt;} public Io_url_gen_dir Fmt_(String v) {fmt = v; return this;} private String fmt = "{0}.csv";
	public int Fmt_digits() {return fmt_digits;} public Io_url_gen_dir Fmt_digits_(int v) {fmt_digits = v; return this;} private int fmt_digits = 10;
	public Io_url Cur_url() {return cur_url;} Io_url cur_url;
	public Io_url Nxt_url() {cur_url = dir.GenSubFil(String_.Format(fmt, Int_.To_str_pad_bgn_zero(idx++, fmt_digits))); return cur_url;} private int idx = 0;
	public Io_url[] Prv_urls() {
		Io_url[] rv = new Io_url[idx];
		for (int i = 0; i < idx; i++) {
			rv[i] = dir.GenSubFil(String_.Format(fmt, Int_.To_str_pad_bgn_zero(i, fmt_digits)));
		}
		return rv;
	}
	public void Del_all() {if (Io_mgr.Instance.ExistsDir(dir)) Io_mgr.Instance.DeleteDirDeep(dir);}
	public Io_url_gen_dir(Io_url dir) {this.dir = dir;} Io_url dir;
}
class Io_url_gen_fil implements Io_url_gen {
	public Io_url Cur_url() {return cur_url;} Io_url cur_url;
	public Io_url Nxt_url() {return cur_url;}
	public Io_url[] Prv_urls() {return new Io_url[]{cur_url};}
	public void Del_all() {Io_mgr.Instance.DeleteFil_args(cur_url).MissingFails_off().Exec();}
	public Io_url_gen_fil(Io_url fil) {this.cur_url = fil;}
}
