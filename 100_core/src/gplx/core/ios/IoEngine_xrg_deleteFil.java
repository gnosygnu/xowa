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
public class IoEngine_xrg_deleteFil extends IoEngine_xrg_fil_affects1_base {
	@gplx.New public IoEngine_xrg_deleteFil Url_(Io_url val) {Url_set(val); return this;}
	public IoEngine_xrg_deleteFil ReadOnlyFails_off() {return ReadOnlyFails_(false);} public IoEngine_xrg_deleteFil ReadOnlyFails_(boolean v) {ReadOnlyFails_set(v); return this;}
	public IoEngine_xrg_deleteFil MissingFails_off() {return MissingFails_(false);} public IoEngine_xrg_deleteFil MissingFails_(boolean v) {MissingFails_set(v); return this;}
	@Override public void Exec() {IoEnginePool.Instance.Get_by(this.Url().Info().EngineKey()).DeleteFil_api(this);}
        public static IoEngine_xrg_deleteFil proto_() {return new IoEngine_xrg_deleteFil();}
	public static IoEngine_xrg_deleteFil new_(Io_url url) {
		IoEngine_xrg_deleteFil rv = new IoEngine_xrg_deleteFil();
		rv.Url_set(url);
		return rv;
	}	IoEngine_xrg_deleteFil() {}
}	
