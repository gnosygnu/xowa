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
package gplx.core.security.algos;
import gplx.core.security.algos.gplx_crypto.Hash_algo__tth_192;
import gplx.core.security.algos.jre.Jre_hash_factory;
import gplx.types.errs.ErrUtl;
public class Hash_algo_ {
	public static Hash_algo New__md5()        {return Jre_hash_factory.Instance.New_hash_algo(Jre_hash_factory.Key__md5);}
	public static Hash_algo New__sha1()        {return Jre_hash_factory.Instance.New_hash_algo(Jre_hash_factory.Key__sha1);}
	public static Hash_algo New__sha2_256()    {return Jre_hash_factory.Instance.New_hash_algo(Jre_hash_factory.Key__sha2_256);}
	public static Hash_algo New__tth_192()    {return new Hash_algo__tth_192();}
	public static Hash_algo New_by_tid(byte tid) {
		switch (tid) {
			case Tid__md5:            return New__md5();
			case Tid__sha1:            return New__sha1();
			case Tid__sha2_256:        return New__sha2_256();
			case Tid__tth_192:        return New__tth_192();
			default:                throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static Hash_algo New(String key) {
		if        (key == Jre_hash_factory.Key__md5)      return New__md5();
		else if (key == Jre_hash_factory.Key__sha1)     return New__sha1();
		else if (key == Jre_hash_factory.Key__sha2_256)    return New__sha2_256();
		else if (key == Hash_algo__tth_192.KEY)         return New__tth_192();
		else throw ErrUtl.NewUnhandled(key);
	}
	public static final byte Tid__md5 = 0, Tid__sha1 = 1, Tid__sha2_256 = 2, Tid__tth_192 = 3;
}
