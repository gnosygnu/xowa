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
package gplx.core.security.algos; import gplx.*; import gplx.core.*; import gplx.core.security.*;
import gplx.core.security.algos.bouncy_castle.*;
import gplx.core.security.algos.getopt.*;
import gplx.core.security.algos.gnu_crypto.*;
import gplx.core.security.algos.jre.*;
import gplx.core.security.algos.jacksum.*;
public class Hash_algo_factory__php_ {
	public static Hash_algo_factory__composite New() {
		Hash_algo_factory__composite rv = new Hash_algo_factory__composite();
		rv.Reg_many(Bouncy_castle_factory.Instance
			, Bouncy_castle_factory.Key__md2, Bouncy_castle_factory.Key__md4, Bouncy_castle_factory.Key__md5
			, Bouncy_castle_factory.Key__sha1, Bouncy_castle_factory.Key__sha224, Bouncy_castle_factory.Key__sha256, Bouncy_castle_factory.Key__sha384
			);
		rv.Reg_one(Bouncy_castle_factory.Instance, Bouncy_castle_factory.Key__sha_512_224, "sha512/224");
		rv.Reg_one(Bouncy_castle_factory.Instance, Bouncy_castle_factory.Key__sha_512_256, "sha512/256");
		rv.Reg_many(Bouncy_castle_factory.Instance
			, Bouncy_castle_factory.Key__sha512
			, Bouncy_castle_factory.Key__sha3_224, Bouncy_castle_factory.Key__sha3_256, Bouncy_castle_factory.Key__sha3_384, Bouncy_castle_factory.Key__sha3_512
			, Bouncy_castle_factory.Key__ripemd128, Bouncy_castle_factory.Key__ripemd160, Bouncy_castle_factory.Key__ripemd256, Bouncy_castle_factory.Key__ripemd320
			, Bouncy_castle_factory.Key__whirlpool
			);
		rv.Reg_one(Jacksum_factory.Instance, Jacksum_factory.Key__tiger_128, "tiger128,3");
		rv.Reg_one(Jacksum_factory.Instance, Jacksum_factory.Key__tiger_160, "tiger160,3");
		rv.Reg_one(Jacksum_factory.Instance, Jacksum_factory.Key__tiger_192, "tiger192,3");
		rv.Reg_many(Jacksum_factory.Instance, Jacksum_factory.Key__gost);
		rv.Reg_many(Jre_checksum_factory.Instance, Jre_checksum_factory.Key__adler32);
		// "crc" may be BZ2 CRC; https://stackoverflow.com/questions/40741707/php-hashcrc32-and-crc32-return-different-value
		rv.Reg_one(Jre_checksum_factory.Instance, Jre_checksum_factory.Key__crc32, "crc32b"); // PHP crc32b is the equivalent of Java CRC

		rv.Reg_many(Getopt_factory.Instance
			, Getopt_factory.Key__fnv132, Getopt_factory.Key__fnv164, Getopt_factory.Key__fnv1a32, Getopt_factory.Key__fnv1a64
			);
		rv.Reg_many(Gnu_haval_factory.Instance
			, Gnu_haval_factory.Key__haval128_3, Gnu_haval_factory.Key__haval160_3, Gnu_haval_factory.Key__haval192_3, Gnu_haval_factory.Key__haval224_3, Gnu_haval_factory.Key__haval256_3
			, Gnu_haval_factory.Key__haval128_4, Gnu_haval_factory.Key__haval160_4, Gnu_haval_factory.Key__haval192_4, Gnu_haval_factory.Key__haval224_4, Gnu_haval_factory.Key__haval256_4
			, Gnu_haval_factory.Key__haval128_5, Gnu_haval_factory.Key__haval160_5, Gnu_haval_factory.Key__haval192_5, Gnu_haval_factory.Key__haval224_5, Gnu_haval_factory.Key__haval256_5
			);
		return rv;
	}
}
/*
URL: https://en.wikipedia.org/w/index.php?title=Module:Sandbox/Gnosygnu&action=edit
SRC: mw.logObject(mw.hash.listAlgorithms())
OUT:
table#1 {
"md2",             -- bouncycastle
"md4",             -- bouncycastle
"md5",             -- bouncycastle
"sha1",            -- bouncycastle
"sha224",          -- bouncycastle
"sha256",          -- bouncycastle
"sha384",          -- bouncycastle
"sha512/224",      -- bouncycastle
"sha512/256",      -- bouncycastle
"sha512",          -- bouncycastle
"sha3-224",        -- bouncycastle
"sha3-256",        -- bouncycastle
"sha3-384",        -- bouncycastle
"sha3-512",        -- bouncycastle
"ripemd128",       -- bouncycastle
"ripemd160",       -- bouncycastle
"ripemd256",       -- bouncycastle
"ripemd320",       -- bouncycastle
"whirlpool",       -- bouncycastle
"tiger128,3",      -- jacksum
"tiger160,3",      -- jacksum
"tiger192,3",      -- jacksum
"tiger128,4",
"tiger160,4",
"tiger192,4",
"snefru",
"snefru256",
"gost",            -- jacksum; not in bouncycastle (tried GOST3411; GOST3411-2012-256; GOST3411-2012-512)
"gost-crypto",
"adler32",         -- jre
"crc32",
"crc32b",          -- jre
"fnv132",          -- getopt
"fnv1a32",         -- getopt
"fnv164",          -- getopt
"fnv1a64",         -- getopt
"joaat",
"haval128,3",      -- gnu-crypto
"haval160,3",      -- gnu-crypto
"haval192,3",      -- gnu-crypto
"haval224,3",      -- gnu-crypto
"haval256,3",      -- gnu-crypto
"haval128,4",      -- gnu-crypto
"haval160,4",      -- gnu-crypto
"haval192,4",      -- gnu-crypto
"haval224,4",      -- gnu-crypto
"haval256,4",      -- gnu-crypto
"haval128,5",      -- gnu-crypto
"haval160,5",      -- gnu-crypto
"haval192,5",      -- gnu-crypto
"haval224,5",      -- gnu-crypto
"haval256,5",      -- gnu-crypto
}
REF:
* https://www.bouncycastle.org/specifications.html
* https://www.gnu.org/software/gnu-crypto/manual/api/gnu/crypto/hash/BaseHash.html
*/
