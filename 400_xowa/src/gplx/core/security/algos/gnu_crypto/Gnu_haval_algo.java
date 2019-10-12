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
package gplx.core.security.algos.gnu_crypto; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
import gnu.crypto.hash.Haval;
import gnu.crypto.hash.Tiger;
public class Gnu_haval_algo implements Hash_algo {
	private final    Gnu_haval_factory factory;
	private final Haval haval;
	public Gnu_haval_algo(Gnu_haval_factory factory, String key) {
		this.factory = factory;
		this.key = key;
		this.haval = factory.New_Haval(key);
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Gnu_haval_algo(factory, key);}
	public void Update_digest(byte[] bry, int bgn, int end) {haval.update(bry, bgn, end);}
	public byte[] To_hash_bry() {
		byte[] rv = haval.digest();
		return gplx.core.encoders.Hex_utl_.Encode_bry(rv);
	}
}
