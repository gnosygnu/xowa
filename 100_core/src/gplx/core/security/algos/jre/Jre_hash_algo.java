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
public class Jre_hash_algo implements Hash_algo {
	private final Jre_hash_factory factory;
	private final MessageDigest md;
	public Jre_hash_algo(Jre_hash_factory factory, String key) {
		this.factory = factory;
		this.key = key;
		this.md = factory.New_algo_under(key);
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Jre_hash_algo(factory, key);}
	public void Update_digest(byte[] bry, int bgn, int end) {md.update(bry, bgn, end - bgn);}
	public byte[] To_hash_bry() {
		byte[] md_bry = md.digest();
		return gplx.core.encoders.Hex_utl_.Encode_bry(md_bry);
	}
}
