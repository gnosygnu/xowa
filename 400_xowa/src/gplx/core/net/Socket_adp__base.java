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
package gplx.core.net; import gplx.*; import gplx.core.*;
import java.io.IOException;
import java.net.*;
public class Socket_adp__base implements Socket_adp {
		private final Socket socket;
	public Socket_adp__base(Socket socket) {this.socket = socket;}
		public Object Get_input_stream() {
				try {return socket.getInputStream();}
		catch (IOException e) {throw Err_.err_(e);}
			}
	public Object Get_output_stream() {
				try {return socket.getOutputStream();}
		catch (IOException e) {throw Err_.err_(e);}
			}
	public void Rls() {
				try {socket.close();} 
		catch (IOException e) {throw Err_.err_(e);}
			}
}
