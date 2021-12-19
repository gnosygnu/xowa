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
package gplx.xowa.xtns.scribunto.libs;
import gplx.core.security.algos.Hash_algo;
import gplx.core.security.algos.Hash_algo_factory__composite;
import gplx.core.security.algos.Hash_algo_factory__php_;
import gplx.core.security.algos.Hash_algo_utl;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Scrib_lib_hash implements Scrib_lib {
	public Scrib_lib_hash(Scrib_core core) {}
	public String Key() {return "mw.hash";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {
		procs.Init_by_lib(this, Proc_names);
		return this;
	}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_hash(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.hash.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private final Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_listAlgorithms:                return ListAlgorithms(args, rslt);
			case Proc_hashValue:                     return HashValue(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_listAlgorithms = 0, Proc_hashValue = 1;
	public static final String Invk_listAlgorithms = "listAlgorithms", Invk_hashValue = "hashValue";
	private static final String[] Proc_names = StringUtl.Ary(Invk_listAlgorithms, Invk_hashValue);
	public boolean ListAlgorithms(Scrib_proc_args args, Scrib_proc_rslt rslt) {// NOTE:listAlgorithms
		return rslt.Init_many_kvs(algo_keys);
	}
	public boolean HashValue(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// return null if algo_key and val are not given
		if (args.Len() != 2)
			return rslt.Init_obj(null);	// 2 args

		// get args
		String algo_key = args.Xstr_str_or_null(0);
		byte[] val = args.Xstr_bry_or_null(1);

		// get algo or fail
		Hash_algo algo = algo_factory.New_hash_algo(algo_key);
		if (algo == null) {
			throw ErrUtl.NewArgs("Hash_algo is unknown; key=" + algo_key);
		}
		return rslt.Init_obj(StringUtl.NewU8(Hash_algo_utl.Calc_hash_as_bry(algo, val)));
	}
	private static final Hash_algo_factory__composite algo_factory = Hash_algo_factory__php_.New();
	private static final KeyVal[] algo_keys = Make_algo_keys(algo_factory);
	private static KeyVal[] Make_algo_keys(Hash_algo_factory__composite factory) {
		String[] keys = factory.Algo_keys();
		int len = keys.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			rv[i] = KeyVal.NewInt(i + Scrib_core.Base_1, keys[i]);
		}
		return new KeyVal[] {KeyVal.NewInt(Scrib_core.Base_1, rv)}; // NOTE: this hierarchy is needed for Scribunto; see also Scrib_proc_rslt.Init_bry_ary
	}
}
