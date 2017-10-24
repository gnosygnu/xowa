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
package gplx;
public interface Gfo_usr_dlg__log extends Gfo_invk {
	boolean Enabled(); void Enabled_(boolean v);
	boolean Queue_enabled(); void Queue_enabled_(boolean v);
	Io_url Log_dir(); void Log_dir_(Io_url v);
	Io_url Session_dir();
	Io_url Session_fil();
	void Log_msg_to_url_fmt(Io_url url, String fmt, Object... args);
	void Log_to_session(String txt);
	void Log_to_session_fmt(String fmt, Object... args);
	void Log_to_session_direct(String txt);
	void Log_to_err(String txt);
	void Log_term();
}
