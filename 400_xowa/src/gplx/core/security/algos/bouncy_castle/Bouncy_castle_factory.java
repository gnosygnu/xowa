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
package gplx.core.security.algos.bouncy_castle; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
public class Bouncy_castle_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Bouncy_castle_algo(key);
	}
	public static String
	  Key__md2 = "md2", Key__md4 = "md4", Key__md5 = "md5"
	, Key__sha1 = "sha1", Key__sha224 = "sha224", Key__sha256 = "sha256", Key__sha384 = "sha384"
	, Key__sha_512_224 = "sha-512/224", Key__sha_512_256 = "sha-512/256", Key__sha512 = "sha512"
	, Key__sha3_224 = "sha3-224", Key__sha3_256 = "sha3-256", Key__sha3_384 = "sha3-384", Key__sha3_512 = "sha3-512"
	, Key__ripemd128 = "ripemd128", Key__ripemd160 = "ripemd160", Key__ripemd256 = "ripemd256", Key__ripemd320 = "ripemd320"
	, Key__whirlpool = "whirlpool"
	;
	public static final    Bouncy_castle_factory Instance = new Bouncy_castle_factory(); Bouncy_castle_factory() {}
}
