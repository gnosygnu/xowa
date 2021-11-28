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
package gplx.core.security.algos.getopt; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
import org.getopt.util.hash.*;
public class Getopt_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Getopt_algo(this, key);
	}
	public FNV1 New_FNV1(String key) {
		if      (String_.Eq(key, Key__fnv132))  return new FNV132();
		else if (String_.Eq(key, Key__fnv164))  return new FNV164();
		else if (String_.Eq(key, Key__fnv1a32)) return new FNV1a32();
		else if (String_.Eq(key, Key__fnv1a64)) return new FNV1a64();
		else throw Err_.new_unhandled(key);
	}
	public static String
	  Key__fnv132 = "fnv132", Key__fnv164 = "fnv164", Key__fnv1a32 = "fnv1a32", Key__fnv1a64 = "fnv1a64"
	;
	public static final Getopt_factory Instance = new Getopt_factory(); Getopt_factory() {}
}
