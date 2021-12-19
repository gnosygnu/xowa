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
package gplx.core.security.algos.gnu_crypto;
import gnu.crypto.hash.Haval;
import gplx.core.security.algos.Hash_algo;
import gplx.core.security.algos.Hash_algo_factory;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Gnu_haval_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Gnu_haval_algo(this, key);
	}

	public Haval New_Haval(String key) {
		// parse key for size; EX: "128" in "haval128,3"
		int size = IntUtl.ParseOr(StringUtl.Mid(key, 5, 8), -1);
		switch (size) {
			case 128: size = Haval.HAVAL_128_BIT; break;
			case 160: size = Haval.HAVAL_160_BIT; break;
			case 192: size = Haval.HAVAL_192_BIT; break;
			case 224: size = Haval.HAVAL_224_BIT; break;
			case 256: size = Haval.HAVAL_256_BIT; break;
			default: throw ErrUtl.NewUnhandled(size);
		}

		// parse key for round; EX: "3" in "haval128,3"
		int round = IntUtl.ParseOr(StringUtl.Mid(key, 9, 10), -1);
		switch (round) {
			case 3: round = Haval.HAVAL_3_ROUND; break;
			case 4: round = Haval.HAVAL_4_ROUND; break;
			case 5: round = Haval.HAVAL_5_ROUND; break;
			default: throw ErrUtl.NewUnhandled(round);
		}
		return new Haval(size, round);
	}

	public static String
	  Key__haval128_3 = "haval128,3", Key__haval160_3 = "haval160,3", Key__haval192_3 = "haval192,3", Key__haval224_3 = "haval224,3", Key__haval256_3 = "haval256,3"
	, Key__haval128_4 = "haval128,4", Key__haval160_4 = "haval160,4", Key__haval192_4 = "haval192,4", Key__haval224_4 = "haval224,4", Key__haval256_4 = "haval256,4"
	, Key__haval128_5 = "haval128,5", Key__haval160_5 = "haval160,5", Key__haval192_5 = "haval192,5", Key__haval224_5 = "haval224,5", Key__haval256_5 = "haval256,5"
	;
	public static final Gnu_haval_factory Instance = new Gnu_haval_factory(); Gnu_haval_factory() {}
}
