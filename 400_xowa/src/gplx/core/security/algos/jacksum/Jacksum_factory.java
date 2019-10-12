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
package gplx.core.security.algos.jacksum; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
public class Jacksum_factory implements Hash_algo_factory {
	public Hash_algo New_hash_algo(String key) {
		return new Jacksum_algo(key);
	}

public Object New_Checksum(String key) {
	return null;
}
	public static String // REF: /source/jonelo/jacksum/JacksumAPI.java
	  Key__tiger_128 = "tiger-128", Key__tiger_160 = "tiger-160", Key__tiger_192= "tiger-192", Key__gost = "gost"
	;
	public static final    Jacksum_factory Instance = new Jacksum_factory(); Jacksum_factory() {}
}
