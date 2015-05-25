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
public class IoEngine_xrg_xferFil {
	public boolean Type_move() {return move;} private boolean move = false;
	public Io_url Src() {return src;} Io_url src;
	public Io_url Trg() {return trg;} Io_url trg;
	public boolean Overwrite() {return overwrite;} public IoEngine_xrg_xferFil Overwrite_() {return Overwrite_(true);} public IoEngine_xrg_xferFil Overwrite_(boolean v) {overwrite = v; return this;} private boolean overwrite = false;
	public boolean ReadOnlyFails() {return readOnlyFails;} public IoEngine_xrg_xferFil ReadOnlyFails_off() {return ReadOnlyFails_(false);} public IoEngine_xrg_xferFil ReadOnlyFails_(boolean v) {readOnlyFails = v; return this;} private boolean readOnlyFails = true;
	public boolean MissingFails() {return missingFails;} public IoEngine_xrg_xferFil MissingFails_off() {return MissingFails_(false);} public IoEngine_xrg_xferFil MissingFails_(boolean v) {missingFails = v; return this;} private boolean missingFails = true;
	public void Exec() {IoEnginePool._.Get_by(src.Info().EngineKey()).XferFil(this);}
	public static IoEngine_xrg_xferFil move_(Io_url src, Io_url trg) {return new_(src, trg, true);}
	public static IoEngine_xrg_xferFil copy_(Io_url src, Io_url trg) {return new_(src, trg, false);}
	static IoEngine_xrg_xferFil new_(Io_url src, Io_url trg, boolean move) {
		IoEngine_xrg_xferFil rv = new IoEngine_xrg_xferFil();
		rv.src = src; rv.trg = trg; rv.move = move;
		return rv;
	}	IoEngine_xrg_xferFil() {}
}
