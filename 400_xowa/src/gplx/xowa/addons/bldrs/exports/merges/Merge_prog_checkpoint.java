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
