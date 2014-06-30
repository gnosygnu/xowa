/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public interface Gfo_log_wtr extends GfoInvkAble {
	Io_url Session_dir();
	Io_url Log_dir(); void Log_dir_(Io_url v);
	Io_url Session_fil();
	boolean Enabled(); void Enabled_(boolean v);
	boolean Queue_enabled(); void Queue_enabled_(boolean v);
	void Log_msg_to_url_fmt(Io_url url, String fmt, Object... args);
	void Log_msg_to_session(String txt);
	void Log_msg_to_session_fmt(String fmt, Object... args);
	void Log_msg_to_session_direct(String txt);
	void Log_err(String txt);
	void Init();
	void Term();
}
