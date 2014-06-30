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
package gplx.php; import gplx.*;
public class Php_itm_quote implements Php_itm, Php_itm_sub, Php_key {
	public Php_itm_quote(byte[] v) {this.val_obj_bry = v;}	// NOTE: use Php_text_itm_parser to parse \" and related
	public byte Itm_tid() {return Php_itm_.Tid_quote;}
	public byte[] Val_obj_bry() {return val_obj_bry;} private byte[] val_obj_bry;
}
