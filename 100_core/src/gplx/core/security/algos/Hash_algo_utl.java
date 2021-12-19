/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.security.algos;
import gplx.core.consoles.*; import gplx.core.progs.*; import gplx.core.ios.streams.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
public class Hash_algo_utl {
	public static String Calc_hash_as_str(Hash_algo algo, byte[] bry) {return StringUtl.NewU8(Calc_hash_as_bry(algo, bry));}
	public static byte[] Calc_hash_as_bry(Hash_algo algo, byte[] bry) {
		if (ClassUtl.IsAssignableFromByObj(algo, Hash_algo_w_prog.class)) {
			Hash_algo_w_prog algo_w_prog = (Hash_algo_w_prog)algo;
			return BryUtl.NewU8(algo_w_prog.Calc_hash_w_prog_as_str(IoStream_.ary_(bry), Console_adp_.Noop));
		}

		algo.Update_digest(bry, 0, bry.length);
		return algo.To_hash_bry();
	}
	public static String Calc_hash_w_prog_as_str(Hash_algo algo, IoStream stream, Console_adp console) {return StringUtl.NewU8(Calc_hash_w_prog_as_bry(algo, stream, console));}
	public static byte[] Calc_hash_w_prog_as_bry(Hash_algo algo, IoStream stream, Console_adp console) {
		if (ClassUtl.IsAssignableFromByObj(algo, Hash_algo_w_prog.class)) {
			Hash_algo_w_prog algo_w_prog = (Hash_algo_w_prog)algo;
			return BryUtl.NewU8(algo_w_prog.Calc_hash_w_prog_as_str(stream, console));
		}
		return Calc_hash_w_prog_as_bry(algo, stream, Gfo_prog_ui_.Noop);
	}
	public static byte[] Calc_hash_w_prog_as_bry(Hash_algo algo, IoStream stream, Gfo_prog_ui prog_ui) {
		int tmp_bry_len = 4096;
		byte[] tmp_bry = new byte[4096];

		// pos and len must be long, else will not hash files > 2 GB
		long pos = prog_ui.Prog_data_cur();
		long len = prog_ui.Prog_data_end();

		try {
			while (true) {
				int read = stream.Read(tmp_bry, 0, tmp_bry_len);    // read stream into tmp_bry
				if (read < 1) break;
				algo.Update_digest(tmp_bry, 0, read);
				if (prog_ui.Prog_notify_and_chk_if_suspended(pos, len)) return null;
				pos += read;
			}
		}
		finally {stream.Rls();}
		byte[] rv = algo.To_hash_bry();
		return rv;
	}
}
