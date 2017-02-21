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
public class Process_server_mock implements Scrib_server {
	private List_adp rsps = List_adp_.New(); private int rsps_idx = 0;
	public void Init(String... process_args) {}
	public int Server_timeout() {return server_timeout;} public Scrib_server Server_timeout_(int v) {server_timeout = v; return this;} private int server_timeout = 8000;
	public int Server_timeout_polling() {return server_timeout_polling;} public Scrib_server Server_timeout_polling_(int v) {server_timeout_polling = v; return this;} private int server_timeout_polling = 1;
	public int Server_timeout_busy_wait() {return server_timeout_busy_wait;} public Scrib_server Server_timeout_busy_wait_(int v) {server_timeout_busy_wait = v; return this;} private int server_timeout_busy_wait = 250;
	public byte[] Server_comm(byte[] cmd, Object[] cmd_objs) {
		Server_send(cmd, cmd_objs);
		return Server_recv();
	}
	public void Server_send(byte[] cmd, Object[] cmd_objs) {
		this.cmd_objs = cmd_objs;
		log_rcvd.Add(String_.new_u8(cmd));
	}	Object[] cmd_objs;
	public byte[] Server_recv() {
		Process_server_mock_rcvd rcvd = (Process_server_mock_rcvd)rsps.Get_at(rsps_idx++);
		String rv = rcvd.Bld(cmd_objs);
		log_sent.Add(rv);
		return Bry_.new_u8(rv);
	}
	public void Term() {}
	public void Clear() {rsps.Clear(); rsps_idx = 0; log_rcvd.Clear(); log_sent.Clear();}
	public boolean Print_key() {return print_key;} public Process_server_mock Print_key_(boolean v) {print_key = v; return this;} private boolean print_key;
	public void Prep_add(String v) {rsps.Add(new Process_server_mock_rcvd_str(v));}
	public void Prep_add_dynamic_val() {rsps.Add(new Process_server_mock_rcvd_val(print_key));}
	public List_adp Log_rcvd() {return log_rcvd;} private List_adp log_rcvd = List_adp_.New();
	public List_adp Log_sent() {return log_sent;} private List_adp log_sent = List_adp_.New();
}
interface Process_server_mock_rcvd {
	String Bld(Object[] cmd_obs); 
}
class Process_server_mock_rcvd_str implements Process_server_mock_rcvd {
	public Process_server_mock_rcvd_str(String rcvd) {this.rcvd = rcvd;} private String rcvd;
	public String Bld(Object[] cmd_obs) {return rcvd;}
}
class Process_server_mock_rcvd_val implements Process_server_mock_rcvd {
	public Process_server_mock_rcvd_val(boolean print_key) {this.print_key = print_key;} private boolean print_key;
	public String Bld(Object[] cmd_objs) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Bld_recursive(tmp_bfr, 0, (Keyval[])cmd_objs[5]);
		byte[] values_str = tmp_bfr.To_bry_and_clear();
		tmp_bfr.Add(Bry_rv_bgn).Add_int_variable(values_str.length).Add(Bry_rv_mid).Add(values_str).Add(Bry_rv_end);
		return tmp_bfr.To_str_and_clear();
	}
	private void Bld_recursive(Bry_bfr bfr, int depth, Keyval[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Keyval kv = ary[i];
			Object kv_val = kv.Val();
			if (kv_val == null) {
				bfr.Add(gplx.langs.jsons.Json_itm_.Bry__null);
				continue;
			}
			Class<?> kv_val_type = kv_val.getClass();
			boolean kv_val_is_array = Type_adp_.Eq(kv_val_type, Keyval[].class);
			if (print_key && !kv_val_is_array)
				bfr.Add_str_u8(kv.Key()).Add_byte(Byte_ascii.Colon);
			if		(Type_adp_.Eq(kv_val_type, Bool_.Cls_ref_type))
				bfr.Add(Bool_.Cast(kv_val) ? gplx.langs.jsons.Json_itm_.Bry__true : gplx.langs.jsons.Json_itm_.Bry__false);
			else if	(kv_val_is_array) {
				Keyval[] sub = (Keyval[])kv_val;
				if (sub.length == 0) {bfr.Add_byte(Byte_ascii.Curly_bgn).Add_byte(Byte_ascii.Curly_end);}
				else {
					bfr.Add_byte_nl();
					bfr.Add_byte_repeat(Byte_ascii.Space, (depth + 1) * 2);
					Bld_recursive(bfr, depth + 1, (Keyval[])kv_val);
				}
			}
			else
				bfr.Add_str_u8(kv.Val_to_str_or_empty());
		}
	}

	private static final    byte[] Bry_rv_bgn = Bry_.new_a7("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:"), Bry_rv_mid = Bry_.new_a7(":\""), Bry_rv_end = Bry_.new_a7("\";}}");
}
