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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
public class Merge_prog_checkpoint {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Io_url url;
	private String resume_fil;
	private int resume_wkr = -1;
	private int resume_db = -1;
	public Merge_prog_checkpoint(Io_url url) {this.url = url;}
	public int Resume_db() {return resume_db;}
	public boolean Skip_fil(Io_url fil) {
		if (resume_fil == null) return false;	// not resume; do not skip
		boolean rv = String_.Eq(fil.NameAndExt(), resume_fil);
		if (rv) resume_fil = null;				// null out for next call
		return !rv;
	}
	public boolean Skip_wkr(byte wkr_tid) {
		if (resume_wkr == -1) return false;		// not resume; do not skip;
		boolean rv = wkr_tid == resume_wkr;
		if (rv) resume_wkr = -1;				// null out for next call
		return !rv;
	}
	public int Load() {
		byte[] bry = Io_mgr.Instance.LoadFilBryOrNull(url); if (bry == null) return 0;
		byte[][] parts = Bry_split_.Split(bry, Byte_ascii.Pipe);
		this.resume_fil = String_.new_u8(parts[0]);
		this.resume_wkr = Bry_.To_int(parts[1]);
		this.resume_db = Bry_.To_int(parts[2]);
		return Bry_.To_int(parts[3]);
	}
	public void Save(Io_url fil, byte wkr_tid, int db_id, int prog_count) {	// EX: file.xowa|0|4|1234
		if (fil == null) return;
		bfr.Add_str_u8(fil.NameAndExt()).Add_byte_pipe();
		bfr.Add_int_variable(wkr_tid).Add_byte_pipe();
		bfr.Add_int_variable(db_id).Add_byte_pipe();
		bfr.Add_int_variable(prog_count);
		Io_mgr.Instance.SaveFilBry(url, bfr.To_bry_and_clear());
	}
	public void Delete() {
		resume_fil = null;
		resume_wkr = -1;
		resume_db = -1;
		Io_mgr.Instance.DeleteFil(url);
	}
}
