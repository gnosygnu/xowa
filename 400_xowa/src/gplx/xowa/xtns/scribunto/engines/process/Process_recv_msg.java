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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.core.primitives.*; import gplx.langs.phps.*;
public class Process_recv_msg {
	private Php_srl_parser parser = new Php_srl_parser();
	public Process_recv_msg() {
		arg_keys.Add("op"			, Byte_obj_val.new_(Arg_op));
		arg_keys.Add("values"		, Byte_obj_val.new_(Arg_values));
		arg_keys.Add("id"			, Byte_obj_val.new_(Arg_id));
		arg_keys.Add("args"			, Byte_obj_val.new_(Arg_args));
	}	private Hash_adp arg_keys = Hash_adp_.New(); private static final byte Arg_op = 0, Arg_values = 1, Arg_id = 2, Arg_args = 3;
	public String Op() {return op;} private String op;
	public String Call_id() {return call_id;} private String call_id;
	public Keyval[] Rslt_ary() {return rslt_ary;} private Keyval[] rslt_ary;
	public Keyval[] Values() {return values;} private Keyval[] values;
	public Keyval[] Call_args() {return call_args;} private Keyval[] call_args;
	public String Extract(byte[] rsp) {
		try {
			op = call_id = null;
			rslt_ary = values = call_args = null;
			Keyval[] root_ary = parser.Parse_as_kvs(rsp);
			rslt_ary = (Keyval[])root_ary[0].Val();
			int len = rslt_ary.length;
			for (int i = 0; i < len; i++) {
				Keyval kv = rslt_ary[i];
				String kv_key = kv.Key();
				Byte_obj_val bv = (Byte_obj_val)arg_keys.Get_by(kv_key);
				if	(bv != null) {
					switch (bv.Val()) {
						case Arg_op:		op = kv.Val_to_str_or_empty(); break;
						case Arg_values: 	values = (Keyval[])kv.Val(); break;
						case Arg_id:		call_id = kv.Val_to_str_or_empty(); break;
						case Arg_args:		call_args = (Keyval[])kv.Val(); break;
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
