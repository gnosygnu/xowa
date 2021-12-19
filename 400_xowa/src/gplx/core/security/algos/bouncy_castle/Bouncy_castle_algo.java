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
package gplx.core.security.algos.bouncy_castle;
import gplx.types.basics.encoders.HexUtl;
import gplx.core.security.algos.Hash_algo;
import gplx.types.errs.ErrUtl;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.MessageDigest;
import java.security.Security;
public class Bouncy_castle_algo implements Hash_algo {
	private static boolean Provider_needs_init = true;
	private final MessageDigest md;
	public Bouncy_castle_algo(String key) {
		// register BounceCastleProvider
		if (Provider_needs_init) {
			Provider_needs_init = false;
			Security.addProvider(new BouncyCastleProvider());
		}

		// get digest
		try {
			this.key = key;
			this.md = MessageDigest.getInstance(key);
		}
		catch (Exception exc) {
			throw ErrUtl.NewArgs("unknown messageDigest; key=" + key);
		}
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Bouncy_castle_algo(key);}
	public void Update_digest(byte[] bry, int bgn, int end) {md.update(bry, bgn, end - bgn);}
	public byte[] To_hash_bry() {
		// get hash
		byte[] hash = md.digest();
		return HexUtl.EncodeBry(hash);
	}
}
