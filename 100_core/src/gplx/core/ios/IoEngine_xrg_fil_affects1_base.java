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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoEngine_xrg_fil_affects1_base {
	public Io_url Url() {return url;} public void Url_set(Io_url v) {url = v;} Io_url url;
	public IoEngine_xrg_fil_affects1_base Url_(Io_url v) {url = v; return this;}
	public boolean MissingFails() {return missingFails;} public void MissingFails_set(boolean v) {missingFails = v;} private boolean missingFails = true;
	public boolean ReadOnlyFails() {return readOnlyFails;} public void ReadOnlyFails_set(boolean v) {readOnlyFails = v;} private boolean readOnlyFails = true;
	@gplx.Virtual public void Exec() {}
}
