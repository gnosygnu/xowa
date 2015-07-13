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
package gplx.ios; import gplx.*;
public class IoItmAttrib {
	public boolean ReadOnly() {return readOnly;} public IoItmAttrib ReadOnly_() {return ReadOnly_(true);} public IoItmAttrib ReadOnly_(boolean val) {readOnly = val; return this;} private boolean readOnly;
	public boolean Hidden() {return hidden;} public IoItmAttrib Hidden_() {return Hidden_(true);} public IoItmAttrib Hidden_(boolean val) {hidden = val; return this;} private boolean hidden;
	public static IoItmAttrib readOnly_() {return new IoItmAttrib().ReadOnly_();}
	public static IoItmAttrib hidden_() {return new IoItmAttrib().Hidden_();}
	public static IoItmAttrib normal_() {return new IoItmAttrib().ReadOnly_(false).Hidden_(false);}
}
