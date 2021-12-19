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
package gplx.xowa.xtns.scribunto.engines.process;
import gplx.types.commons.KeyVal;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.wrappers.ByteVal;
import gplx.xowa.xtns.scribunto.*;
import gplx.langs.phps.*;
public class Process_recv_msg {
	private Php_srl_parser parser = new Php_srl_parser();
	public Process_recv_msg() {
		arg_keys.Add("op"			, ByteVal.New(Arg_op));
		arg_keys.Add("values"		, ByteVal.New(Arg_values));
		arg_keys.Add("id"			, ByteVal.New(Arg_id));
		arg_keys.Add("args"			, ByteVal.New(Arg_args));
	}	private Hash_adp arg_keys = Hash_adp_.New(); private static final byte Arg_op = 0, Arg_values = 1, Arg_id = 2, Arg_args = 3;
	public String Op() {return op;} private String op;
	public String Call_id() {return call_id;} private String call_id;
	public KeyVal[] Rslt_ary() {return rslt_ary;} private KeyVal[] rslt_ary;
	public KeyVal[] Values() {return values;} private KeyVal[] values;
	public KeyVal[] Call_args() {return call_args;} private KeyVal[] call_args;
	public String Extract(byte[] rsp) {
		try {
			op = call_id = null;
			rslt_ary = values = call_args = null;
			KeyVal[] root_ary = parser.Parse_as_kvs(rsp);
			rslt_ary = (KeyVal[])root_ary[0].Val();
			int len = rslt_ary.length;
			for (int i = 0; i < len; i++) {
				KeyVal kv = rslt_ary[i];
				String kv_key = kv.KeyToStr();
				ByteVal bv = (ByteVal)arg_keys.GetByOrNull(kv_key);
				if	(bv != null) {
					switch (bv.Val()) {
						case Arg_op:		op = kv.ValToStrOrEmpty(); break;
						case Arg_values: 	values = (KeyVal[])kv.Val(); break;
						case Arg_id:		call_id = kv.ValToStrOrEmpty(); break;
						case Arg_args:		call_args = (KeyVal[])kv.Val(); break;
					}
				}
			}
			return op;
		}
		catch (Exception e) {
			throw Scrib_xtn_mgr.err_(e, "failed to extract data", "rsp", rsp);
		}
	}
}
