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
package gplx.core.security.files; import gplx.*; import gplx.core.*; import gplx.core.security.*;
public class Cksum_list {
	public Cksum_list(byte type, Cksum_itm[] itms, long itms_size) {
		this.Type = type; this.Itms = itms; this.Itms_size = itms_size;
	}
	public final    byte Type;
	public final    Cksum_itm[] Itms;
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

		byte[][] lines = Bry_split_.Split_lines(bry);
		int len = lines.length;
		long itms_size = 0;
		for (int i = 0; i < len; ++i) {
			byte[] line = lines[i];	// EX: "d41d8cd98f00b204e9800998ecf8427e *file.txt"

			// get hash
			int space_pos = Bry_find_.Find_fwd(line, Byte_ascii.Space);
			if (space_pos == Bry_find_.Not_found) throw Err_.new_("chsum", "checksum line does not have space", "line", line);
			byte[] hash = Bry_.Mid(line, 0, space_pos);

			// get file
			int file_bgn = space_pos + 1;
			if (line[file_bgn] == Byte_ascii.Star) ++file_bgn;	// ignore leading *; EX: "*file.txt" -> "file.txt"
			byte[] file = Bry_.Mid(line, file_bgn);
			Io_url file_url = GenSubFil_nest(owner_dir, file);
			long file_size = Io_mgr.Instance.QueryFil(file_url).Size();
			itms_size += file_size;
			
			// add to list
			Cksum_itm itm = new Cksum_itm(hash, file_url, file_size);
			list.Add(itm);
		}
		return new Cksum_list(type, (Cksum_itm[])list.To_ary_and_clear(Cksum_itm.class), itms_size);
	}
	private static Io_url GenSubFil_nest(Io_url dir, byte[] src) {	// split "a/b/c" or "a\b\c" -> [a, b, c]
		byte dir_spr = gplx.core.envs.Op_sys.Lnx.Fsys_dir_spr_byte();
		int dir_pos = Bry_find_.Find_fwd(src, dir_spr);
		if (dir_pos == Bry_find_.Not_found) {
			dir_spr = gplx.core.envs.Op_sys.Wnt.Fsys_dir_spr_byte();
			dir_pos = Bry_find_.Find_fwd(src, dir_spr);
			if (dir_pos == Bry_find_.Not_found)
				return dir.GenSubFil(String_.new_u8(src));
		}
		byte[][] parts = Bry_split_.Split(src, dir_spr);
		return dir.GenSubFil_nest(String_.Ary(parts));
	}
	private static byte Get_hash_tid_by_ext(String ext) {
		if		(String_.Eq(ext, ".md5"))		return Hash_algo_.Tid__md5;
		else if	(String_.Eq(ext, ".sha1"))		return Hash_algo_.Tid__sha1;
		else if	(String_.Eq(ext, ".sha256"))	return Hash_algo_.Tid__sha2_256;
		else									throw Err_.new_unhandled_default(ext);
	}
}
