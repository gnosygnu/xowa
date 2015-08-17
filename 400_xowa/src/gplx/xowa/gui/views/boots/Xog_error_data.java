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
package gplx.xowa.gui.views.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*; import gplx.xowa.gui.views.*;
public class Xog_error_data {
	public Xog_error_data(String full_msg, String err_details, String err_msg) {
		this.full_msg = full_msg;
		this.err_details = err_details;
		this.err_msg = err_msg;
	}
	public String Full_msg() {return full_msg;} private final String full_msg;
	public String Err_details() {return err_details;} private final String err_details;
	public String Err_msg() {return err_msg;} private final String err_msg;
}
