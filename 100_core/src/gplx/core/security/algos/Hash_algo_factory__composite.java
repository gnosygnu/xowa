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
package gplx.core.security.algos; import gplx.*; import gplx.core.*; import gplx.core.security.*;
public class Hash_algo_factory__composite implements Hash_algo_factory {
	private boolean dirty = true;
	private final    Ordered_hash hash = Ordered_hash_.New();
	private String[] algo_keys;
	public String[] Algo_keys() {
		if (dirty) {
			dirty = false;
			int len = hash.Len();
			algo_keys = new String[len];
			for (int i = 0; i < len; i++) {
				algo_keys[i] = ((Hash_algo)hash.Get_at(i)).Key();
			}
		}
		return algo_keys;
	}
	public Hash_algo New_hash_algo(String key) {
		Hash_algo rv = (Hash_algo)hash.Get_by(key);
		if (rv == null) {
			throw Err_.new_wo_type("hash_algo unknown; key=" + key);
		}
		return rv.Clone_hash_algo();
	}

	public Hash_algo_factory__composite Reg_many(Hash_algo_factory factory, String... algo_keys) {
		dirty = true;
		for (String algo_key : algo_keys) {
			if (hash.Has(algo_key)) {
				throw Err_.new_wo_type("hash_algo already registered; key=" + algo_key);
			}
			hash.Add(algo_key, factory.New_hash_algo(algo_key));
		}
		return this;
	}
	public Hash_algo_factory__composite Reg_one(Hash_algo_factory factory, String src_key, String trg_key) {
		if (hash.Has(trg_key)) {
			throw Err_.new_wo_type("hash_algo already registered; name=" + trg_key);
		}
		hash.Add(trg_key, factory.New_hash_algo(src_key));
		return this;
	}
}
