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
package gplx.core.security.algos.jre;
import gplx.core.security.algos.Hash_algo;
import gplx.core.security.algos.Hash_algo_factory;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
public class Jre_checksum_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Jre_checksum_algo(this, key);
	}

	public Checksum New_Checksum(String key) {
		if      (StringUtl.Eq(key, Key__adler32))  return new Adler32();
		else if (StringUtl.Eq(key, Key__crc32))    return new CRC32();
		else throw ErrUtl.NewUnhandled(key);
	}

	public static String 
		Key__adler32 = "adler32", Key__crc32 = "crc32"
	;
		public static final Jre_checksum_factory Instance = new Jre_checksum_factory(); Jre_checksum_factory() {}
}
