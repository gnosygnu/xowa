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
package gplx.core.security.algos.getopt;
import gplx.core.security.algos.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.getopt.util.hash.FNV1;
public class Getopt_algo implements Hash_algo {
	private final Getopt_factory factory;
	private final FNV1 hash;
	private final int pad_min;
	public Getopt_algo(Getopt_factory factory, String key) {
		this.key = key;
		this.factory = factory;
		this.pad_min = StringUtl.HasAtEnd(key, "32") ? 7 : 15;
		this.hash = factory.New_FNV1(key);
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Getopt_algo(factory, key);}
	public void Update_digest(byte[] bry, int bgn, int end) {hash.init(bry, bgn, end);}
	public byte[] To_hash_bry() {
		long val = hash.getHash();
		String rv = Long.toHexString(val);
		if (StringUtl.Len(rv) == pad_min)
			rv = "0" + rv;
		return BryUtl.NewU8(rv);
	}
}
