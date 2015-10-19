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
package gplx.xowa.bldrs.wtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.ios.*;
import gplx.xowa.wikis.nss.*;
public class Xob_tmp_wtr_wkr__ttl implements Xob_tmp_wtr_wkr {
	public Xob_tmp_wtr_wkr__ttl(Io_url temp_dir, int dump_fil_len) {this.temp_dir = temp_dir; this.dump_fil_len = dump_fil_len;} Io_url temp_dir; int dump_fil_len;
	public Xob_tmp_wtr Tmp_wtr_new(Xow_ns ns) {
		return Xob_tmp_wtr.new_(ns, Io_url_gen_.dir_(temp_dir.GenSubDir_nest(ns.Num_str(), "dump")), dump_fil_len);
	}
}
