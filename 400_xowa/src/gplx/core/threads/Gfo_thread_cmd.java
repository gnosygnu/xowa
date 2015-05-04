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
package gplx.core.threads; import gplx.*; import gplx.core.*;
public interface Gfo_thread_cmd extends GfoInvkAble {
	void Cmd_ctor();
	String Async_key();
	int Async_sleep_interval();
	boolean Async_prog_enabled(); 
	void Async_prog_run(int async_sleep_sum);
	byte Async_init();
	boolean Async_term();
	void Async_run();
	boolean Async_running();
	Gfo_thread_cmd Async_next_cmd(); void Async_next_cmd_(Gfo_thread_cmd next);
}
