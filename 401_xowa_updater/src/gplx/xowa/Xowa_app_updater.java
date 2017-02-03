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
package gplx.xowa;
import gplx.Io_url_;
import gplx.core.envs.*;
public class Xowa_app_updater {
	public static void main(String[] args) {
		Env_.Init_swt(args, Xowa_app_updater.class);
		if (args.length == 0) {
			System.out.println("could not run app_updater; must pass 2 arguments: manifest url and update_root");
			return;
		}
		Xoa_manifest_view.Run(Io_url_.new_fil_(args[0]), Io_url_.new_dir_(args[1]));
	}
}
