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
package gplx.xowa.addons.bldrs.exports.merges;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
public class Merge_prog_checkpoint {
	private final BryWtr bfr = BryWtr.New();
	private final Io_url url;
	private String resume_fil;
	private int resume_wkr = -1;
	private int resume_db = -1;
	public Merge_prog_checkpoint(Io_url url) {this.url = url;}
	public int Resume_db() {return resume_db;}
	public boolean Skip_fil(Io_url fil) {
		if (resume_fil == null) return false;	// not resume; do not skip
		boolean rv = StringUtl.Eq(fil.NameAndExt(), resume_fil);
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
		byte[][] parts = BrySplit.Split(bry, AsciiByte.Pipe);
		this.resume_fil = StringUtl.NewU8(parts[0]);
		this.resume_wkr = BryUtl.ToInt(parts[1]);
		this.resume_db = BryUtl.ToInt(parts[2]);
		return BryUtl.ToInt(parts[3]);
	}
	public void Save(Io_url fil, byte wkr_tid, int db_id, int prog_count) {	// EX: file.xowa|0|4|1234
		if (fil == null) return;
		bfr.AddStrU8(fil.NameAndExt()).AddBytePipe();
		bfr.AddIntVariable(wkr_tid).AddBytePipe();
		bfr.AddIntVariable(db_id).AddBytePipe();
		bfr.AddIntVariable(prog_count);
		Io_mgr.Instance.SaveFilBry(url, bfr.ToBryAndClear());
	}
	public void Delete() {
		resume_fil = null;
		resume_wkr = -1;
		resume_db = -1;
		Io_mgr.Instance.DeleteFil(url);
	}
}
