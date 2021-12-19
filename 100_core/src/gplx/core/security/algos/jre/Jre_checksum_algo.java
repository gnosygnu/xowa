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
import gplx.core.security.algos.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import java.util.zip.Checksum;
public class Jre_checksum_algo implements Hash_algo {
	private final Jre_checksum_factory factory;
	private final Checksum checksum;
	public Jre_checksum_algo(Jre_checksum_factory factory, String key) {
		this.factory = factory;
		this.key = key;
		this.checksum = factory.New_Checksum(key);
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Jre_checksum_algo(factory, key);}
	public void Update_digest(byte[] bry, int bgn, int end) {checksum.update(bry, bgn, end - bgn);}
	public byte[] To_hash_bry() {
		long val = checksum.getValue();
		String rv = Long.toHexString(val);
		return BryUtl.NewU8(rv.length() < 8 ? StringUtl.PadBgn(rv, 8, "0") : rv);
	}
}
