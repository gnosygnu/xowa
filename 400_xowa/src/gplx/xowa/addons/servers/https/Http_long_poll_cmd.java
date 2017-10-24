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
package gplx.xowa.addons.servers.https; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.servers.*;
import gplx.core.envs.*;
public class Http_long_poll_cmd implements gplx.xowa.htmls.bridges.Bridge_cmd_itm {
	private final    List_adp msgs = List_adp_.New();
	private String prv_guid;
	private long prv_start;
	private long prv_timeout;

	public void Init_by_app(Xoa_app app) {
		app.Gui__cbk_mgr().Reg(Xog_cbk_wkr__http.Instance);
	}
	public void Send_msg(String msg) {
		synchronized (msgs) {
			msgs.Add(msg);
		}
	}
	// NOTE: this class is a singleton and only supports one user; need to track multiple requests by having http_server track incoming users
	public String Exec(gplx.langs.jsons.Json_nde data) {
		// for each new request, update guid / start_time
		String cur_guid = data.Get_as_str_or("guid", "");
		long cur_timeout = data.Get_as_long_or("timeout", 5000);
		synchronized (msgs) {
			this.prv_guid = cur_guid;
			this.prv_start = System_.Ticks();
			this.prv_timeout = cur_timeout;
		}

		// check if already active; if so, return;
		while (true) {
			synchronized (msgs) {
				if (!String_.Eq(cur_guid, prv_guid))
					return String_.Format("long-poll ignored: new long-poll arrived: prv={0} cur={1}", prv_guid, cur_guid);

				if (System_.Ticks__elapsed_in_frac(prv_start) > prv_timeout)
					return String_.Format("long-poll ignored: old long-poll timed-out: guid={0}", cur_guid);
			}

			// get msgs in queue
			int msgs_len = 0;
			synchronized (msgs) {
				msgs_len = msgs.Len();
			}

			// no messages
			if (msgs_len == 0) {
				gplx.core.threads.Thread_adp_.Sleep(Sleep_interval);
				continue;
			}

			// message found; exit loop;
			break;
		}

		// return commands
		String[] rv = null;
		synchronized (msgs) {
			rv = msgs.To_str_ary_and_clear();
		}
		return String_.Concat_lines_nl(rv);
	}

	public byte[] Key() {return BRIDGE_KEY;} private static final    byte[] BRIDGE_KEY = Bry_.new_a7("long_poll");
        public static final    Http_long_poll_cmd Instance = new Http_long_poll_cmd(); Http_long_poll_cmd() {}

	private static final int 
	  Sleep_interval	= 100
	;
}
