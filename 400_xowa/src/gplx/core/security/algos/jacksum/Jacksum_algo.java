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
package gplx.core.security.algos.jacksum;
import gplx.types.basics.encoders.HexUtl;
import gplx.core.security.algos.Hash_algo;
import gplx.types.errs.ErrUtl;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import java.security.NoSuchAlgorithmException;
public class Jacksum_algo implements Hash_algo {
		private final AbstractChecksum checksum;
			public Jacksum_algo(String key) {
		this.key = key;
		try {
			this.checksum = JacksumAPI.getChecksumInstance(key);
		} catch (NoSuchAlgorithmException nsae) {
			throw ErrUtl.NewArgs("jacksum algo doesn't exist: key=" + key);
		}
	}
	public String Key() {return key;} private final String key;
	public Hash_algo Clone_hash_algo() {return new Jacksum_algo(key);}
	public void Update_digest(byte[] bry, int bgn, int end) {checksum.update(bry, bgn, end - bgn);}
	public byte[] To_hash_bry() {
		byte[] rv = checksum.getByteArray();
		return HexUtl.EncodeBry(rv);
	}
	}
