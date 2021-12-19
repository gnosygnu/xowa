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
package gplx.core.security.files;
import gplx.core.security.algos.Hash_algo_;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Cksum_list {
	public Cksum_list(byte type, Cksum_itm[] itms, long itms_size) {
		this.Type = type; this.Itms = itms; this.Itms_size = itms_size;
	}
	public final byte Type;
	public final Cksum_itm[] Itms;
	public long Itms_size;
	public Io_url[] Itms_ary() {
		int len = Itms.length;
		Io_url[] rv = new Io_url[len];
		for (int i = 0; i < len; ++i)
			rv[i] = Itms[i].File_url;
		return rv;
	}

	public static Cksum_list Parse_by_fil(Io_url url) {
		byte tid = Get_hash_tid_by_ext(url.Ext());
		return Cksum_list.Parse(tid, url.OwnerDir(), Io_mgr.Instance.LoadFilBry(url));
	}
	public static Cksum_list Parse(byte type, Io_url owner_dir, byte[] bry) {
		List_adp list = List_adp_.New();

		byte[][] lines = BrySplit.SplitLines(bry);
		int len = lines.length;
		long itms_size = 0;
		for (int i = 0; i < len; ++i) {
			byte[] line = lines[i];	// EX: "d41d8cd98f00b204e9800998ecf8427e *file.txt"

			// get hash
			int space_pos = BryFind.FindFwd(line, AsciiByte.Space);
			if (space_pos == BryFind.NotFound) throw ErrUtl.NewArgs("checksum line does not have space", "line", line);
			byte[] hash = BryLni.Mid(line, 0, space_pos);

			// get file
			int file_bgn = space_pos + 1;
			if (line[file_bgn] == AsciiByte.Star) ++file_bgn;	// ignore leading *; EX: "*file.txt" -> "file.txt"
			byte[] file = BryLni.Mid(line, file_bgn);
			Io_url file_url = GenSubFil_nest(owner_dir, file);
			long file_size = Io_mgr.Instance.QueryFil(file_url).Size();
			itms_size += file_size;
			
			// add to list
			Cksum_itm itm = new Cksum_itm(hash, file_url, file_size);
			list.Add(itm);
		}
		return new Cksum_list(type, (Cksum_itm[])list.ToAryAndClear(Cksum_itm.class), itms_size);
	}
	private static Io_url GenSubFil_nest(Io_url dir, byte[] src) {	// split "a/b/c" or "a\b\c" -> [a, b, c]
		byte dir_spr = gplx.core.envs.Op_sys.Lnx.Fsys_dir_spr_byte();
		int dir_pos = BryFind.FindFwd(src, dir_spr);
		if (dir_pos == BryFind.NotFound) {
			dir_spr = gplx.core.envs.Op_sys.Wnt.Fsys_dir_spr_byte();
			dir_pos = BryFind.FindFwd(src, dir_spr);
			if (dir_pos == BryFind.NotFound)
				return dir.GenSubFil(StringUtl.NewU8(src));
		}
		byte[][] parts = BrySplit.Split(src, dir_spr);
		return dir.GenSubFil_nest(StringUtl.Ary(parts));
	}
	private static byte Get_hash_tid_by_ext(String ext) {
		if		(StringUtl.Eq(ext, ".md5"))		return Hash_algo_.Tid__md5;
		else if	(StringUtl.Eq(ext, ".sha1"))		return Hash_algo_.Tid__sha1;
		else if	(StringUtl.Eq(ext, ".sha256"))	return Hash_algo_.Tid__sha2_256;
		else									throw ErrUtl.NewUnhandled(ext);
	}
}
