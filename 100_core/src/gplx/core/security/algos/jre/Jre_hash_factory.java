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
package gplx.core.security.algos.jre; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Jre_hash_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Jre_hash_algo(this, key);
	}

	public MessageDigest New_algo_under(String key) {
		try {return MessageDigest.getInstance(key);}
		catch (NoSuchAlgorithmException e) {throw Err_.new_missing_key(key);}
	}

	public static String
	  Key__md5 = "md5", Key__sha1 = "sha1", Key__sha2_256 = "sha-256"
	;
	public static final Jre_hash_factory Instance = new Jre_hash_factory(); Jre_hash_factory() {}
}
